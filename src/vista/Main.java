package vista;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import modelo.entidad.Producto;
import modelo.entidad.Cliente;
import controlador.Tienda;

public class Main {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static String [] menuPrincipal = {"1.Acceder como administrador", "2.Acceder como cliente", "0.Salir"};
    // Menú de administrador actualizado con las opciones del grafo
    static String [] menuAdministrador = {
        "1.Registrar nuevo producto", 
        "2.Ver inventario completo", 
        "3.Atender siguiente cliente (facturar)", 
        "4.Insertar vértice (Ubicación)", 
        "5.Insertar arista (Conexión)", 
        "6.Mostrar mapa/grafo", 
        "0.Volver"
    };
    static String [] menuCliente = {"1.Registrarse y realizar compra", "0.Volver"};

    public static void main(String[] args) throws IOException{
        Tienda tienda = new Tienda();
        ejecutarMenu(0, tienda);
    }

    // --- Rutinas genéricas para solicitar datos ---

    static byte solicitarOpcion() throws IOException{
        byte opcion = 0;
        boolean opcionValida = false;
        do{
            System.out.println("\nPor favor ingrese una opcion: ");
            try{
                opcion = Byte.parseByte(in.readLine().trim());
                opcionValida = true;
            }catch(NumberFormatException e){
                System.out.println("Error, Debe de ingresar una de las opciones del menú");
            }
        }while(!opcionValida);
        return opcion;
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

    static LocalDate solicitarFecha() throws IOException{
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

    // --- NUEVO: Solicitar Ubicación ---
    static String solicitarUbicacion() throws IOException {
        String ubicacion = solicitarTexto("Ingrese la ubicación (Ej: heredia, san jose):");

        ubicacion = ubicacion.toLowerCase().trim();

        // Descompone caracteres acentuados en letra base + marca diacrítica
        String normalizado = java.text.Normalizer.normalize(ubicacion, java.text.Normalizer.Form.NFD);

        // La expresión regular \\p{InCombiningDiacriticalMarks} matchea todos los acentos sueltos
        ubicacion = normalizado.replaceAll("\\p{InCombiningDiacriticalMarks}", "");

        return ubicacion;
    }
    // Modificado para pedir ubicación y llamar al nuevo constructor de Cliente
    static Cliente solicitarDatosCliente() throws IOException{
        String nombreCliente = solicitarTexto("Ingrese su nombre completo: ");
        int prioridadCliente = validarPrioridad();
        String ubicacionCliente = solicitarUbicacion();
        return new Cliente(nombreCliente, prioridadCliente, ubicacionCliente);
    }

    // --- Rutinas de ejecucion del menú ---
    
    static void ejecutarMenu(int estadoActual, Tienda tienda) throws IOException{
        byte opcion;
        do{
            filtrarPorEstado(estadoActual);
            opcion = solicitarOpcion();
            estadoActual = procesarOpcion(estadoActual, opcion, tienda);
        }while(estadoActual != -1);
    }

    static void imprimirMenu(String [] arregloOpciones){
        System.out.println("\n========================================");
        for(int i = 0; i < arregloOpciones.length; i++){
            System.out.println(arregloOpciones[i]);
        }
        System.out.println("========================================");
    }

    static int procesarOpcion(int estadoActual,byte opcion, Tienda tienda)throws IOException{
        int siguienteEstado = estadoActual;
        switch(estadoActual){
            case 0:
                siguienteEstado = procesarMenuPrincipal(opcion);
                break;
            case 1:
                siguienteEstado = procesarMenuAdminstrador(opcion, tienda);
                break;
            case 2:
                siguienteEstado = procesarMenuUsuario(opcion, tienda);
                break;
            default:
                System.out.println("\nError, Debe ingresar una de las opciones del menú");
                break;
        }
        return siguienteEstado;
    }

    static int procesarMenuPrincipal(byte opcion){
        if(opcion == 0){
            return -1;
        }else if(opcion == 1){
            return 1;
        }else if(opcion == 2){
            return 2;
        }else {
            System.out.println("\nError, seleccione una opción válida");
            return 0;
        }
    }

    // Modificado para procesar las nuevas opciones del grafo
    static int procesarMenuAdminstrador(byte opcion, Tienda tienda)throws IOException{
        switch(opcion) {
            case 0:
                return 0;
            case 1:
                registrarProducto(tienda);
                return 1;
            case 2:
                mostrarInventario(tienda);
                return 1;
            case 3:
                atenderCliente(tienda);
                return 1;
            case 4:
                insertarVertice(tienda);
                return 1;
            case 5:
                insertarArista(tienda);
                return 1;
            case 6:
                mostrarGrafo(tienda);
                return 1;
            default:
                System.out.println("\nError, seleccione una opción válida");
                return 1;
        }
    }

    static int procesarMenuUsuario(byte opcion, Tienda tienda) throws IOException{
        if (opcion == 0){
            return 0;
        }else if(opcion == 1){
            Cliente clienteNuevo = solicitarDatosCliente();
            gestionarCompra(clienteNuevo, tienda);
            return 2;
        }else{
            System.out.println("\nError, seleccione una opcion válida");
            return 2;
        }
    }

    static void filtrarPorEstado(int estadoActual){
        if(estadoActual == 0) {
            System.out.println("\n          SISTEMA DE VENTAS EN LINEA (INICIO)   ");
            imprimirMenu(menuPrincipal);
        }else if (estadoActual == 1){
            System.out.println("\n          PANEL DE ADMINISTRADOR        ");
            imprimirMenu(menuAdministrador);
        }else if (estadoActual == 2){
            System.out.println("\n          PANEL DE CLIENTE              ");
            imprimirMenu(menuCliente);
        }
    }

    // --- Rutinas específicas de gestion del inventario y clientes ---

    static void registrarProducto(Tienda tienda) throws IOException{
        String nombreProducto = solicitarTexto("Por favor ingrese el nombre del producto: ");
        double precioProducto = solicitarDecimal("Por favor ingrese el precio del producto: ");
        String categoria = solicitarTexto("Por favor ingrese el categoria: ");
        int cantidad = solicitarEntero("Por favor ingrese la cantidad del producto: ");
        ArrayList<String>rutaImagen = solicitarRutaImagen();
        LocalDate fechaVencimiento = verificarCaducidad();

        Producto nuevoProducto = new Producto(nombreProducto, precioProducto, categoria, fechaVencimiento, cantidad, rutaImagen);
        boolean estaRegistrado = tienda.registrarProducto(nuevoProducto);

        if(estaRegistrado){
            System.out.println("Producto registrado exitosamente");
        }else{
            System.out.println("Error al registrar el producto, intente nuevamente");
        }
    }

    static void mostrarInventario(Tienda tienda){
        ArrayList<Producto> listaInventario = tienda.consultarInventario();
        if(!listaInventario.isEmpty()){
            for(Producto producto: listaInventario){
                System.out.println(producto.toString());
            }
        }else{
            System.out.println("El inventario esta vacio");
        }
    }

    // Modificado para verificar la conectividad antes de facturar
    static void atenderCliente(Tienda tienda){
        if (!tienda.hayClientesEnEspera()) {
            System.out.println("\nNo hay clientes pendientes en la fila.");
            return;
        }
        
        if (!tienda.siguienteClienteConectado()) {
            System.out.println("\nError: La ubicación del próximo cliente no está conectada al mapa de la tienda.");
            System.out.println("El cliente permanecerá en la fila hasta que se agregue una ruta válida.");
            return;
        }

        Cliente proximoTurno = tienda.atenderSiguientePedido();

        if(proximoTurno != null){
            System.out.println("------------------------------");
            System.out.println("--- DETALLE DE FACTURACIÓN ---");
            System.out.println("Cliente: " + proximoTurno.getNombre());
            System.out.println("Prioridad de atención: " +  proximoTurno.getPrioridad());
            System.out.println("Destino: " + proximoTurno.getUbicacion());
            System.out.println("-------------------------------");

            System.out.println("\n --- PRODUCTOS ADQUIRIDOS ---");
            ArrayList<Producto> listaProductos = proximoTurno.getCarrito().obtenerProductos();
            for(Producto producto: listaProductos){
                System.out.println(producto.toString());
            }
            System.out.println("--------------------------------");
            System.out.println("Costo total: $" + proximoTurno.getCarrito().calcularTotalCarrito());
            
            System.out.println("\n--- RUTA DE ENTREGA ---");
            System.out.println(tienda.generarReporteRuta(proximoTurno));
        }
    }

    static void gestionarCompra(Cliente cliente, Tienda tienda)throws IOException{
        boolean continuarComprando = true;
        while(continuarComprando){
            System.out.println("---MENÚ DE COMPRAS---");
            System.out.println("1. Agregar producto al carrito");
            System.out.println("2. Finalizar compra e ingresar a la cola de espera");
            int opcionCompra = solicitarEntero("Seleccione una opcion: ");
            if(opcionCompra == 1){
                String nombreBuscado = solicitarTexto("Ingrese el nombre del producto que desea buscar");
                int cantidadPorComprar = solicitarEntero("Ingrese la cantidad que desea comprar: ");
                boolean exito = tienda.agregarProductoAlCarrito(cliente, nombreBuscado, cantidadPorComprar);
                if(exito){
                    System.out.println("¡Producto agregado con éxito al carrito!");
                } else {
                    System.out.println("Error: El producto no existe o no hay suficiente stock disponible.");
                }
            }else if(opcionCompra == 2){
                tienda.recibirPedido(cliente);
                System.out.println("Pedido finalizado correctamente. Ha ingresado a la cola de espera.");
                continuarComprando = false;
            }else{
                System.out.println("Error, seleccione una opcion válida");
            }
        }
    }

    // --- NUEVO: Métodos de integración con el Grafo ---
    
    static void insertarVertice(Tienda tienda) throws IOException {
        String vertice = solicitarUbicacion();
        boolean exito = tienda.insertarVertice(vertice);
        if(exito){
            System.out.println("Vértice '" + vertice + "' registrado en el sistema.");
        }else{
            System.out.println("Error la ubicación no puede estar vacía");
        }
    }

    static void insertarArista(Tienda tienda) throws IOException {
        System.out.println("--- CREACIÓN DE RUTA ---");
        String origen = solicitarTexto("Ingrese la ubicación de origen: ").toLowerCase();
        String destino = solicitarTexto("Ingrese la ubicación de destino: ").toLowerCase();
        int peso = solicitarEntero("Ingrese la distancia (peso) de la ruta: ");
        boolean exito = tienda.insertarArista(origen, destino, peso);
        if(exito){
            System.out.println("Ruta entre '" + origen + "' y '" + destino + "' registrada.");
        }else{
            System.out.println("Error: Datos inválidos. Verifique que las ubicaciones no estén vacías, que sean distintas, y que la distancia sea mayor a cero.");
        }

    }

    static void mostrarGrafo(Tienda tienda) {
        System.out.println("\n--- MAPA DE RUTAS (GRAFO) ---");
        tienda.mostrarGrafo();
    }

    // --- Validaciones ---

    static int validarPrioridad() throws IOException{
        int prioridad;
        boolean prioridadValida = false;
        do{
            prioridad = solicitarEntero("\nIngrese su nivel de prioridad (1: Básico, 2: Afiliado, 3: Premium): ");
            if(prioridad < 1 || prioridad > 3 ){
                System.out.println("La prioridad debe de ser un número entre 1 y 3");
            }else{
                prioridadValida = true;
            }
        }while(!prioridadValida);
        return prioridad;
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
}
