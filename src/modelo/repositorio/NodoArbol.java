package modelo.repositorio;
import modelo.entidad.Producto;

public class NodoArbol {
    // Atributos
    private Producto producto;
    private NodoArbol izq;
    private NodoArbol der;

    // Métodos
    //Constructor
    public NodoArbol(Producto producto) {
        this.producto = producto;
        izq = der = null;
    }

    //Getters


    public Producto getProducto() {
        return producto;
    }

    public NodoArbol getIzq() {
        return izq;
    }

    public NodoArbol getDer() {
        return der;
    }

    //Setters


    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setIzq(NodoArbol izq) {
        this.izq = izq;
    }

    public void setDer(NodoArbol der) {
        this.der = der;
    }
}
