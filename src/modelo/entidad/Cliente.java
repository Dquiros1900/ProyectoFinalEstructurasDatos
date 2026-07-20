package modelo.entidad;

import modelo.repositorio.ListaProductos;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.NodoArbol;

public class Cliente {

    //Atributos
    private String nombre;
    private int prioridad;
    private ListaProductos carrito;
    //Contructor
    public Cliente(String nombre, int prioridad){
        this.nombre = nombre;
        this.prioridad = prioridad;
        carrito = new ListaProductos();
    }

    public boolean comprarProducto(String nombreProducto, int cantidadDeseada, ArbolProductos inventario){
        NodoArbol nodoTienda = inventario.buscar(nombreProducto);
        if (nodoTienda == null){
            return false;
        }
        Producto productoTienda = nodoTienda.getProducto();

        if (productoTienda.getCantidad() < cantidadDeseada){
            return false;
        }

        productoTienda.setCantidad(productoTienda.getCantidad() - cantidadDeseada);

        Producto copiaCarrito = new Producto(
                productoTienda.getNombre(),
                productoTienda.getPrecio(),
                productoTienda.getCategoria(),
                productoTienda.getFechaVencimiento(),
                cantidadDeseada,
                productoTienda.getListaImagenes());

        carrito.insertarFinal(copiaCarrito);
        return true;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public ListaProductos getCarrito() {
        return carrito;
    }

}
