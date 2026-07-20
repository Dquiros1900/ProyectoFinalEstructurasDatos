package modelo.repositorio;

import modelo.entidad.Producto;

public class NodoProducto {

    // Atributos
    private Producto producto;
    private NodoProducto siguiente;

    // Metodos

    //Constructor
    public NodoProducto(Producto producto) {
        this.producto = producto;
        siguiente = null;
    }

    //Getters
    public Producto getProducto() {

        return producto;
    }

    public NodoProducto getSiguiente() {

        return siguiente;
    }

    //Setters

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setSiguiente(NodoProducto siguiente) {

        this.siguiente = siguiente;
    }

}
