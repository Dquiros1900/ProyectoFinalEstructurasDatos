package modelo.repositorio;

public class MapaInicial {

    // Método definitivo acordado
    public static void cargarGrafoBasico(Grafo grafo, String ubicacionTienda) {
        // Aseguramos que la ubicación de la tienda exista como vértice inicial
        grafo.agregarVertice(ubicacionTienda);

        // Vértices iniciales fijos (en minúscula y sin acentos)
        grafo.agregarVertice("san jose");
        grafo.agregarVertice("heredia");
        grafo.agregarVertice("cartago");
        grafo.agregarVertice("alajuela");

        // Aristas iniciales fijos (origen, destino, peso)
        grafo.agregarArista("san jose", "heredia", 10);
        grafo.agregarArista("san jose", "alajuela", 15);
        grafo.agregarArista("san jose", "cartago", 20);
        grafo.agregarArista("heredia", "alajuela", 12);
        grafo.agregarArista("cartago", "heredia", 18);
    }
}