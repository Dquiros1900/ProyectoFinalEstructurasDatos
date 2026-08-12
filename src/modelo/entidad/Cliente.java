package modelo.entidad;

import modelo.repositorio.ListaProductos;
import modelo.repositorio.ArbolProductos;
import modelo.repositorio.NodoArbol;

public class Cliente implements Comparable<Cliente> {
    private String nombre;
    private int prioridad;
    private String ubicacion; // Nuevo atributo

    // Constructor actualizado según requerimientos
    public Cliente(String nombre, int prioridad, String ubicacion) {
        this.nombre = nombre;
        this.prioridad = prioridad;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    // Método nuevo
    public String getUbicacion() {
        return ubicacion;
    }

    // Método nuevo
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public int compareTo(Cliente otro) {
        return Integer.compare(this.prioridad, otro.prioridad);
    }
}