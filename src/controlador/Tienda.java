package controlador;

import modelo.entidad.*;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.ColaClientes;
import modelo.repositorio.Grafo;
import modelo.repositorio.NodoArbol;

import java.util.ArrayList;

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

    public ArrayList<Producto> consultarInventario(){
        return inventario.enOrden();
    }

    public Cliente atenderSiguientePedido(){
        return colaClientes.eliminar();
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
        // TODO: Descomentar cuando Kenny agregue getUbicacion() a Cliente
        // grafo.agregarVertice(clienteCarritoLleno.getUbicacion());
    }

    public boolean agregarProductoAlCarrito(Cliente cliente, String nombreProducto, int cantidad) {
        return cliente.comprarProducto(nombreProducto, cantidad, this.inventario);
    }

    //Métodos del mapa

    public  void insertarVertice(String vertice){
        grafo.agregarVertice(vertice);
    }

    public void insertarArista(String origen, String destino, int peso){
        grafo.agregarArista(origen, destino, peso);
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

    //Setters y getters


    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
