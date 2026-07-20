package modelo.repositorio;

import modelo.entidad.Cliente;
import java.util.ArrayList;

public class ColaClientes {
    //Atributos
    private ArrayList<Cliente> clientes;

    // Constructor
    public ColaClientes(){
        clientes = new ArrayList<>();
    }

    public boolean estaVacia(){
        return clientes.isEmpty();
    }

    public void insertar(Cliente unCliente){
        boolean insertado = false;
        int i = 0;
        while(i < clientes.size()){
            if(clientes.get(i).getPrioridad() < unCliente.getPrioridad()){
                clientes.add(i, unCliente);
                insertado = true;
                break;
            }
            i++;
        }
        if(!insertado){
            clientes.add(unCliente);
        }
    }

    public Cliente eliminar(){
        if (estaVacia()){
            return null;
        }
        return clientes.removeFirst();
    }

    public Cliente verFrente(){
        if (estaVacia()){
            return null;
        }
        return clientes.getFirst();
    }
}