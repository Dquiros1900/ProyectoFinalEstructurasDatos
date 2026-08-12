package modelo.entidad;

import modelo.repositorio.ListaProductos;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.NodoArbol;

public class Cliente implements Comparable<Cliente> {

    // --- ATRIBUTOS ---
    private String nombre;
    private int prioridad;
    private String ubicacion; // Atributo nuevo requerido
    private ListaProductos carrito; // Restaurado a su tipo original

    // --- CONSTRUCTOR DEFINITIVO ---
    public Cliente(String nombre, int prioridad, String ubicacion) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.ubicacion = ubicacion;
        this.carrito = new ListaProductos();
    }

    // --- LÓGICA ORIGINAL DEL AVANCE 2 INTACTA ---
    public boolean comprarProducto(String nombreProducto, int cantidadDeseada, ArbolProductos inventario) {
        NodoArbol nodoTienda = inventario.buscar(nombreProducto);
        if (nodoTienda == null) {
            return false;
        }
        Producto productoTienda = nodoTienda.getProducto();

        if (productoTienda.getCantidad() < cantidadDeseada) {
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

    // --- GETTERS Y SETTERS ORIGINALES ---
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

    // --- GETTERS Y SETTERS NUEVOS REQUERIDOS ---
    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    // --- SOPORTE PARA COMPARACIÓN (Opcional pero recomendado para colas) ---
    @Override
    public int compareTo(Cliente otro) {
        return Integer.compare(this.prioridad, otro.prioridad);
    }
}