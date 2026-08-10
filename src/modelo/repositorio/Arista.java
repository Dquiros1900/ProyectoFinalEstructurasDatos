package modelo.repositorio;

public class Arista {

    // Atributos
    private final String destino;
    private final int peso;

    // Métodos
    // Constructor
    public Arista(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    // Getters
    public String getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}