package modelo.repositorio;

public class Vertice {

    // Atributos
    private final String nombre;
    private final int distancia;

    // Métodos
    // Constructor
    public Vertice(String nombre, int distancia) {
        this.nombre = nombre;
        this.distancia = distancia;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getDistancia() {
        return distancia;
    }
}