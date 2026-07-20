package controlador;

import modelo.entidad.*;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.ColaClientes;
import modelo.repositorio.NodoArbol;

import java.util.ArrayList;

public class Tienda {

    //Atributos
    private ArbolProductos inventario;
    private ColaClientes colaClientes;

    //Constructor
    public Tienda(){
        inventario = new ArbolProductos();
        colaClientes = new ColaClientes();
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
        colaClientes.insertar(clienteCarritoLleno);
    }

    public boolean agregarProductoAlCarrito(Cliente cliente, String nombreProducto, int cantidad) {
        return cliente.comprarProducto(nombreProducto, cantidad, this.inventario);
    }
}
