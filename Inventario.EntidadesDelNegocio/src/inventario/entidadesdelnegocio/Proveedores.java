package inventario.entidadesdelnegocio;

import java.util.ArrayList;

public class Proveedores {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<Producto> productos;


    public Proveedores() {
    }

    public Proveedores(int id, String nombre, int top_aux) {
        this.id = id;
        this.nombre = nombre;
        this.top_aux = top_aux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

}
