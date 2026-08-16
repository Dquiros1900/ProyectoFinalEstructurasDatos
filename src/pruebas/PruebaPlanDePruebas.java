package pruebas;

import controlador.Tienda;
import modelo.entidad.Cliente;

public class PruebaPlanDePruebas {

    static int pasadas = 0;
    static int fallidas = 0;

    public static void main(String[] args) {
        Tienda tienda = new Tienda();

        System.out.println("===== G-01: Carga de datos base =====");
        verificar("G-01", "vertice san jose existe", tienda.existeVertice("san jose"));
        verificar("G-01", "vertice heredia existe", tienda.existeVertice("heredia"));
        verificar("G-01", "vertice cartago existe", tienda.existeVertice("cartago"));
        verificar("G-01", "vertice alajuela existe", tienda.existeVertice("alajuela"));

        System.out.println("===== D-03: Cola de clientes vacia =====");
        verificar("D-03", "no hay clientes en espera", !tienda.hayClientesEnEspera());
        verificar("D-03", "atender con cola vacia devuelve null", tienda.atenderSiguientePedido() == null);

        System.out.println("===== D-02: Cliente con ruta valida =====");
        Cliente conectado = new Cliente("daniel", 3, "heredia");
        tienda.recibirPedido(conectado);
        verificar("D-02", "siguiente cliente conectado", tienda.siguienteClienteConectado());
        Cliente atendido = tienda.atenderSiguientePedido();
        verificar("D-02", "cliente atendido no es null", atendido != null);
        String reporte = tienda.generarReporteRuta(conectado);
        verificar("D-02", "reporte contiene camino san jose -> heredia", reporte.contains("san jose -> heredia"));
        verificar("D-02", "reporte contiene distancia 10", reporte.contains("Distancia total: 10"));

        System.out.println("===== G-02 y D-01: Cliente en ubicacion nueva (escazu) =====");
        Cliente desconectado = new Cliente("carlos", 1, "escazu");
        tienda.recibirPedido(desconectado);
        verificar("G-02", "escazu se agrego como vertice automaticamente", tienda.existeVertice("escazu"));
        verificar("D-01", "cliente desconectado no se puede atender", !tienda.siguienteClienteConectado());
        verificar("D-01", "atender devuelve null", tienda.atenderSiguientePedido() == null);
        verificar("D-01", "el cliente sigue en la cola", tienda.hayClientesEnEspera());

        System.out.println("===== G-03: Conexion de nueva ruta =====");
        verificar("G-03", "arista san jose-escazu aceptada", tienda.insertarArista("san jose", "escazu", 7));
        verificar("G-03", "el cliente queda conectado", tienda.siguienteClienteConectado());
        Cliente atendido2 = tienda.atenderSiguientePedido();
        verificar("G-03", "cliente atendido tras conectar", atendido2 != null);
        String reporte2 = tienda.generarReporteRuta(desconectado);
        verificar("G-03", "reporte incluye escazu", reporte2.contains("escazu"));
        verificar("G-03", "distancia 7", reporte2.contains("Distancia total: 7"));

        System.out.println("===== M-03 (nivel Tienda): validacion de datos =====");
        verificar("M-03", "vertice en blanco rechazado", !tienda.insertarVertice("   "));
        verificar("M-03", "peso negativo rechazado", !tienda.insertarArista("a", "b", -5));
        verificar("M-03", "arista hacia si misma rechazada", !tienda.insertarArista("escazu", "escazu", 5));

        System.out.println("===== M-01 y M-02: nivel menu (manuales) =====");
        System.out.println("M-01 y M-02 viven en el Main de Camilo: ya estan 'Comprobado' en el plan.");

        System.out.println();
        System.out.println("RESULTADO: " + pasadas + " pasadas, " + fallidas + " fallidas.");
    }

    static void verificar(String id, String descripcion, boolean condicion) {
        if (condicion) {
            pasadas++;
            System.out.println("[PASS] " + id + " - " + descripcion);
        } else {
            fallidas++;
            System.out.println("[FAIL] " + id + " - " + descripcion);
        }
    }
}