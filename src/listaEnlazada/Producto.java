package listaEnlazada;

import java.time.LocalDate;
import java.util.ArrayList;

public class Producto {

    //Atributos
    private String nombre;
    private double precio;
    private String categoria;
    private LocalDate fechaVencimiento;
    private int cantidad;
    private ArrayList<String> listaImagenes;

    //Métodos

    //Constructores
    public Producto() {
        this.listaImagenes = new ArrayList<>();
    }

    public Producto(String nombre, double precio, String categoria, LocalDate fechaVencimiento, int cantidad, ArrayList<String> listaImagenes) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaVencimiento = fechaVencimiento;
        this.cantidad = cantidad;
        this.listaImagenes = listaImagenes;
    }

    //Procesos
    public void agregarImagen(String rutaImagen){
        listaImagenes.add(rutaImagen);
    }

    //Getters
    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public ArrayList<String> getListaImagenes() {
        return listaImagenes;
    }

    //Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String toString(){
        String textoFecha = (this.fechaVencimiento == null) ? "No aplica / No perecedero" : this.fechaVencimiento.toString();
        return  "\nNombre del producto: " + nombre +
                "\nPrecio del producto: " + "$" + precio +
                "\nCategoría del producto: " + categoria +
                "\nFecha de vencimiento: " + textoFecha +
                "\nCantidad: " + cantidad +
                "\nImágenes asociadas al producto: " + listaImagenes;
    }
}
