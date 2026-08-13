package controlador;

import modelo.entidad.*;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.ColaClientes;
import modelo.repositorio.Grafo;
import modelo.repositorio.NodoArbol;

import java.util.ArrayList;
import java.util.List;

public class Tienda {

    //Atributos
    private ArbolProductos inventario;
    private ColaClientes colaClientes;
    private String ubicacion;
    private Grafo grafo;

    //Constructor
    public Tienda(){
        inventario = new ArbolProductos();
        colaClientes = new ColaClientes();
        this.ubicacion = "san jose";
        this.grafo = new Grafo();
        cargarMapaBase();
    }

    //Métodos del administrador
    public boolean registrarProducto(Producto nuevoProducto){
        return inventario.insertar(nuevoProducto);
    }

    public  ArrayList<Producto>consultarInventario(){
        return inventario.enOrden();
    }

    public Cliente atenderSiguientePedido(){
        if(!hayClientesEnEspera()){
            return null;
        }else if(!siguienteClienteConectado()){
            return null;
        }
        return colaClientes.eliminar();
    }

    public String generarReporteRuta(Cliente cliente) {
        String ubicacionCliente = cliente.getUbicacion();
        String ubicacionTienda = this.ubicacion;

        List<String> camino = grafo.obtenerCaminoMasCorto(ubicacionTienda, ubicacionCliente);
        int distancia = grafo.obtenerDistanciaMasCorta(ubicacionTienda, ubicacionCliente);

        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE ENTREGA ===").append(System.lineSeparator());
        reporte.append("Desde: ").append(ubicacionTienda)
                .append(" | Hasta: ").append(ubicacionCliente)
                .append(System.lineSeparator());

        if (camino.isEmpty()) {
            reporte.append("No existe un camino disponible en este momento.")
                    .append(System.lineSeparator());
        } else {
            reporte.append("Camino más corto: ")
                    .append(String.join(" -> ", camino))
                    .append(System.lineSeparator());
            reporte.append("Distancia total: ")
                    .append(distancia)
                    .append(System.lineSeparator());
        }

        return reporte.toString();
    }


    //Métodos del cliente

    public Producto buscarProductoEnInventario(String nombreBuscado){
        NodoArbol resultadoBusqueda = inventario.buscar(nombreBuscado);
        if(resultadoBusqueda != null){
            return resultadoBusqueda.getProducto();
        }
        return null;
    }

    public void recibirPedido(Cliente clienteCarritoLleno){
        if(clienteCarritoLleno == null){
            return;
        }
        if(esUbicacionValida(clienteCarritoLleno.getUbicacion())){
         grafo.agregarVertice(normalizar(clienteCarritoLleno.getUbicacion()));
        }
        colaClientes.insertar(clienteCarritoLleno);
    }

    public boolean agregarProductoAlCarrito(Cliente cliente, String nombreProducto, int cantidad) {
        return cliente.comprarProducto(nombreProducto, cantidad, this.inventario);
    }

    //Métodos del mapa

    public  boolean insertarVertice(String vertice){
        if(!esUbicacionValida(vertice)){
            return false;
        }
        grafo.agregarVertice(normalizar(vertice));
        return true;
    }

    public boolean insertarArista(String origen, String destino, int peso){
        if(!esUbicacionValida(origen)){
            return false;
        }else if(!esUbicacionValida(destino)){
            return false;
        }
        else if(peso <= 0){
            return false;
        }else if((normalizar(origen).equals(normalizar(destino)))){
            return false;
        }
        grafo.agregarArista(normalizar(origen), normalizar(destino), peso);
        return true;
    }

    public void mostrarGrafo(){
        grafo.mostrarGrafo();
    }

    public Cliente verSiguienteCliente(){
        return colaClientes.verFrente();
    }

    public boolean hayClientesEnEspera(){
        return !colaClientes.estaVacia();
    }

    private void cargarMapaBase(){
        grafo.agregarArista("san jose", "heredia", 10);
        grafo.agregarArista("san jose", "alajuela", 15);
        grafo.agregarArista("san jose", "cartago", 20);
        grafo.agregarArista("heredia", "alajuela", 12);
        grafo.agregarArista("cartago", "heredia", 18);
    }

    //Métodos de lógica de conectividad y ruta

    public boolean siguienteClienteConectado(){
        if(!hayClientesEnEspera()){
            return false;
        }
        Cliente cliente = verSiguienteCliente();
        String ubicacionCliente = cliente.getUbicacion();
        String ubicacionTienda = this.ubicacion;
        return grafo.existeCamino(ubicacionCliente, ubicacionTienda);
    }

    //Validaciónes

    private boolean esUbicacionValida(String texto){
        if(texto == null){
            return false;
        }
        return !texto.trim().isEmpty();
    }

    private String normalizar(String texto){
        return texto.trim().toLowerCase();
    }
    //Setters y getters


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        if (esUbicacionValida(ubicacion)) {
            this.ubicacion = normalizar(ubicacion);
        }
    }
}
