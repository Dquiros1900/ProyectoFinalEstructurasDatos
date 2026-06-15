package listaEnlazada;

public class ListaProductos {
    //Atributos
    private NodoProducto primero;
    //Constructor
    public ListaProductos() {
        primero = null;
    }

    //Operaciones

    public boolean estaVacia (){
        return primero == null;
    }

    public void insertarInicio(Producto producto){
        NodoProducto nodo = new NodoProducto(producto);
        nodo.setSiguiente(primero);
        primero = nodo;
    }

    public void insertarFinal(Producto producto){
        NodoProducto nodo = new NodoProducto(producto);
        if (estaVacia()){
            primero = nodo;
            return;
        }
        NodoProducto temp = primero;
        while(temp.getSiguiente()!= null) temp = temp.getSiguiente();
        temp.setSiguiente(nodo);
    }

    public NodoProducto buscar(String nombreBuscado){
        if(estaVacia()){
            return null;
        }
        NodoProducto temp = primero;
        while(temp != null){
            if(temp.getProducto().getNombre().equalsIgnoreCase(nombreBuscado)) return temp;
            temp = temp.getSiguiente();
        }
        return null;
    }

    public NodoProducto eliminar(String nombreBuscado){
        if(estaVacia()){
            return null;
        }
        if (primero.getProducto().getNombre().equalsIgnoreCase(nombreBuscado)) {
            NodoProducto aux = primero;
            primero = primero.getSiguiente();
            return aux;
        }
        NodoProducto temp = primero;
        NodoProducto anterior = temp;
        while(temp != null && !temp.getProducto().getNombre().equalsIgnoreCase(nombreBuscado)){
            anterior = temp;
            temp = temp.getSiguiente();
        }
        if (temp == null){
            return null;
        }
        anterior.setSiguiente(temp.getSiguiente());
        return temp;

    }

    public String generarReporte(){
        if(estaVacia()){
            return "\nEl inventario está vacío";
        }
        StringBuilder reporteCompleto = new StringBuilder("\n=== INVENTARIO ACTUAL ===\n");
        NodoProducto temp = primero;
        while(temp != null){
            reporteCompleto.append(temp.getProducto().toString()).append("\n");
            temp = temp.getSiguiente();
        }
        return reporteCompleto.toString();
    }

    public String generarReporteCostos(){
        StringBuilder reporteCostos = new StringBuilder("\n=== REPORTE DE COSTOS DE INVENTARIO ===\n");
        double totalAcumulado = 0;
        NodoProducto temp = primero;

        while(temp != null){
            double subtotal = (temp.getProducto().getPrecio() * temp.getProducto().getCantidad());

            reporteCostos.append("producto: ").append(temp.getProducto().getNombre())
                    .append(" | Costo Invertido: ").append(subtotal).append("\n");

            totalAcumulado += subtotal;
            temp = temp.getSiguiente();
        }

        reporteCostos.append("Total acumulado: ").append(totalAcumulado);
        return reporteCostos.toString();
    }
}
