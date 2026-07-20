
package modelo.repositorio;

import modelo.entidad.Producto;

import java.util.ArrayList;

public class ArbolProductos {

    //Atributos
    private NodoArbol raiz;

    //Métodos
    //Constructor
    public ArbolProductos() {
        raiz = null;
    }

    //getters
    public NodoArbol getRaiz(){
        return raiz;
    }

    //Setters
    public void setRaiz(NodoArbol raiz){
        this.raiz = raiz;
    }

    private boolean estaVacio(){
        return raiz == null;
    }

    public boolean insertar(Producto nuevoProducto){
        NodoArbol nodo = new NodoArbol(nuevoProducto);

        if(estaVacio()){
            setRaiz(nodo);
            return true;
        }

        NodoArbol temp = raiz;
        NodoArbol padreTemp = temp;
        String llaveNueva = nuevoProducto.getNombre();

        while(temp != null){
            padreTemp = temp;
            String llaveActual = temp.getProducto().getNombre();
            int comparacion = llaveNueva.compareToIgnoreCase(llaveActual);
            if (comparacion < 0){
                temp = temp.getIzq();
            }else if(comparacion > 0){
                temp = temp.getDer();
            }else{
                temp.getProducto().setCantidad(temp.getProducto().getCantidad() + nuevoProducto.getCantidad());
                return true;
            }
        }

        String llavePadre = padreTemp.getProducto().getNombre();
        if(llaveNueva.compareToIgnoreCase(llavePadre) < 0){
            padreTemp.setIzq(nodo);
        }else{
            padreTemp.setDer(nodo);
        }
        return true;
    }

    public NodoArbol buscar(String nombreBuscado){
        if(estaVacio()){
            return null;
        }

        NodoArbol temp = raiz;

        while(temp != null) {
            int comparacion = nombreBuscado.compareToIgnoreCase(temp.getProducto().getNombre());
            if(comparacion == 0){
                return temp;
            }else if(comparacion < 0){
                temp = temp.getIzq();
            }else{
                temp = temp.getDer();
            }

        }
        return null;
    }

    public NodoArbol buscarPadre(String nombreBuscado){
        if(estaVacio() || nombreBuscado.compareToIgnoreCase(raiz.getProducto().getNombre()) == 0){
            return null;
        }

        NodoArbol temp = raiz;
        NodoArbol padreTemp = temp;
        while(temp != null) {
            int comparacion = nombreBuscado.compareToIgnoreCase(temp.getProducto().getNombre());
            if (comparacion == 0) {
                return padreTemp;
            }
            padreTemp = temp;
            if (comparacion < 0) {
                temp = temp.getIzq();
            } else {
                temp = temp.getDer();
            }
        }
        return null;
    }

    private NodoArbol buscarSucesor(NodoArbol nodo){
        NodoArbol temp = nodo.getDer();
        NodoArbol sucesor = nodo;
        NodoArbol padreSucesor = sucesor;
        while(temp != null){
            padreSucesor = sucesor;
            sucesor = temp;
            temp = temp.getIzq();
        }
        if(sucesor != nodo.getDer()){
            padreSucesor.setIzq(sucesor.getDer());
            sucesor.setDer(nodo.getDer());
        }
        return sucesor;
    }

    private void enOrdenRec(NodoArbol raiz, ArrayList<Producto> productos){
        if (raiz != null){
            enOrdenRec(raiz.getIzq(), productos);
            productos.add(raiz.getProducto());
            enOrdenRec(raiz.getDer(), productos);
        }
    }

    public ArrayList<Producto> enOrden(){
        ArrayList<Producto> productos = new ArrayList<>();
        if(!estaVacio()) {
            enOrdenRec(raiz, productos);
        }
        return productos;
    }

    public NodoArbol eliminar(String nombreBuscado){
        if(estaVacio()){
            return null;
        }

        NodoArbol nodo = buscar(nombreBuscado);
        if (nodo == null) return null;
        if (nodo == raiz) {
            if (nodo.getIzq() == null && nodo.getDer() == null) setRaiz(null);
            else if(nodo.getDer() == null) setRaiz(nodo.getIzq());
            else if (nodo.getIzq() == null) setRaiz(nodo.getDer());
            else{
                NodoArbol sucesor = buscarSucesor(raiz);
                sucesor.setIzq(raiz.getIzq());
                setRaiz(sucesor);
            }
            return nodo;
        }
        NodoArbol padre = buscarPadre(nombreBuscado);
        if (nodo.getIzq() == null && nodo.getDer() == null){
            if(nodo == padre.getIzq()) padre.setIzq(null);
            else padre.setDer(null);
        } else if (nodo.getDer() == null){
            if(nodo == padre.getIzq()) padre.setIzq(nodo.getIzq());
            else padre.setDer(nodo.getIzq());
        }else if (nodo.getIzq() == null){
            if(nodo == padre.getIzq()) padre.setIzq(nodo.getDer());
            else padre.setDer(nodo.getDer());
        }else{
            NodoArbol sucesor = buscarSucesor(nodo);
            sucesor.setIzq(nodo.getIzq());
            if(nodo == padre.getIzq()) padre.setIzq(sucesor);
            else padre.setDer(sucesor);
        }
        return nodo;
    }
}
