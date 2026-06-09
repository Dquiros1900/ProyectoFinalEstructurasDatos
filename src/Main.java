import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import listaEnlazada.*;

public class Main {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static String [] arregloOpciones = {"1.Registrar nuevo producto", "2.Buscar producto", "3.Eliminar producto", "4.Ver reporte general", "5.Ver reporte financiero", "0.Salir"};
    static String [] arregloPrioridades = {"1.Lote de Alta Prioridad / Exhibición (Se agregará al INICIO de la lista)", "2.Lote Regular (Se agregará al FINAL de la lista)"};

    public static void main(String[] args) throws IOException{
        ListaProductos inventario = new ListaProductos();
        ejecutarMenu(inventario);
    }

    static void ejecutarMenu(ListaProductos inventario) throws IOException{
        int opcion = -1;
        do{
            imprimirMenu(arregloOpciones);
            opcion = solicitarEntero("\nSeleccione una opción del menú");
            procesarOpcion(opcion, inventario);
        }while(opcion != 0);
    }

    static void imprimirMenu(String [] arregloOpciones){
        System.out.println("\n--- MENÚ DEL SISTEMA ---");
        for(int i = 0; i < arregloOpciones.length; i++){
            System.out.println(arregloOpciones[i]);
        }
    }

    static String solicitarTexto(String mensaje) throws IOException {
        System.out.println(mensaje);
        return in.readLine().trim();
    }

    static int solicitarEntero(String mensaje) throws IOException {
        int numero = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            try {
                numero = Integer.parseInt(in.readLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("\nError, debe ingresar un número entero válido.\n");
            }
        } while (!valido);
        return numero;
    }

    static double solicitarDecimal(String mensaje) throws IOException {
        double numero = 0;
        boolean valido = false;
        do {
            System.out.println(mensaje);
            try {
                numero = Double.parseDouble(in.readLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("\nError, debe ingresar un número decimal válido.\n");
            }
        } while (!valido);
        return numero;
    }

    static void procesarOpcion(int opcion, ListaProductos inventario) throws IOException {
        switch (opcion) {
            case 1:
                System.out.println("\n--- REGISTRAR NUEVO PRODUCTO ---");
                registrarProducto(inventario);
                break;
            case 2:
                System.out.println("\n--- BUSCAR PRODUCTO ---");
                buscarProducto(inventario);
                break;
            case 3:
                System.out.println("\n--- ELIMINAR PRODUCTO ---");
                eliminarProducto(inventario);
                break;
            case 4:
                System.out.println("\n--- REPORTE GENERAL DE INVENTARIO ---");
                System.out.println(inventario.generarReporte());
                break;
            case 5:
                System.out.println("\n--- REPORTE FINANCIERO ---");
                System.out.println(inventario.generarReporteCostos());
                break;
            default:
                break;
        }
    }

    //Rutinas asociadas a registrar un producto
    static void registrarProducto(ListaProductos inventario) throws IOException{
        String nombreProducto = solicitarTexto("Ingrese el nombre del producto");
        double precioProducto = solicitarDecimal("Ingrese el precio del producto");
        String categoriaProducto = solicitarTexto("Ingrese la categoria del producto");
        int cantidadProducto = solicitarEntero("Ingrese la cantidad inicial del inventario");
        ArrayList<String> rutaImagen = solicitarRutaImagen();
        LocalDate fechaVencimiento = verificarCaducidad();
        Producto producto = new Producto(nombreProducto, precioProducto, categoriaProducto,fechaVencimiento, cantidadProducto, rutaImagen);
        solicitarPrioridadProducto(producto, inventario);

    }

    static ArrayList<String> solicitarRutaImagen()  throws IOException{
        ArrayList<String> rutaImagen = new ArrayList<>();
        int opcionElegida;
        do {
            String rutaSolicitada = solicitarTexto("Ingrese la ruta de la imagen (ej: src/imagenes/prod.png): ");
            rutaImagen.add(rutaSolicitada);
            opcionElegida = solicitarEntero("\n¿Desea agregar otra ruta de imagen para este producto? (1. Sí / 2. No).");
        }while(opcionElegida == 1);
        return rutaImagen;
    }

    static  LocalDate solicitarFecha() throws IOException{
        DateTimeFormatter formateador =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaFormatoDate = null;
        boolean fechaValida = false;
        String fechaFormatoTexto = "";
        do{
            fechaFormatoTexto = solicitarTexto("Ingrese la fecha de vencimiento (dd/MM/yyyy): ");
            try{
                fechaFormatoDate = LocalDate.parse(fechaFormatoTexto, formateador);
                fechaValida = true;
            }catch(DateTimeParseException e){
                System.out.println("\nError: Fecha inválida o formato incorrecto. Por favor, asegúrese de usar el formato día/mes/año con números reales.\n");
            }
        }while(!fechaValida);
        return fechaFormatoDate;
    }

    static LocalDate verificarCaducidad() throws IOException{
        LocalDate fechaVencimiento = null;
        int opcionFecha = solicitarEntero("\n¿El producto cuenta con fecha de caducidad? 1. Sí / 2. No");
        if(opcionFecha == 1) {
            fechaVencimiento = solicitarFecha();
            return fechaVencimiento;
        }
        return null;
    }

    static void solicitarPrioridadProducto(Producto producto, ListaProductos inventario) throws IOException{
        int prioridad;
        boolean prioridadValida = false;
        do {
            System.out.println("\n--- UBICACIÓN DEL PRODUCTO ---\n");
            imprimirMenu(arregloPrioridades);
            prioridad = solicitarEntero("\n¿Cómo desea registrar este ingreso en el almacén?");
            if (prioridad == 1) {
                inventario.insertarInicio(producto);
                prioridadValida = true;
                System.out.println("\n¡Producto registrado con Alta Prioridad (Inicio)!\n");
            } else if (prioridad == 2) {
                inventario.insertarFinal(producto);
                prioridadValida = true;
                System.out.println("\n¡Producto registrado en Catálogo Regular (Final)!\n");
            } else {
                System.out.println("\nError: Ingrese una prioridad válida (1 o 2).\n");
            }
        }while(!prioridadValida);
    }

    static void buscarProducto(ListaProductos inventario) throws IOException{
        String nombreBuscado = solicitarTexto("\nIngrese el producto que desea buscar: ");
        NodoProducto resultadoBusqueda = inventario.buscar(nombreBuscado);
        if(resultadoBusqueda != null){
            System.out.println(resultadoBusqueda.getProducto());
        }else{
            System.out.println("\nEl producto no se encuentra registrado en el inventario\n");
        }
    }

    static void eliminarProducto(ListaProductos inventario) throws IOException{
        String productoPorEliminar = solicitarTexto("\nIngrese el producto que desea eliminar: ");
        NodoProducto resultadoEliminar = inventario.eliminar(productoPorEliminar);
        if(resultadoEliminar != null){
            System.out.println("\nProducto eliminado correctamente\n");
        }else{
            System.out.println("\nEl producto no se encuentra registrado en el inventario\n");
        }
    }
}
