package inventario.entidadesdelnegocio;

public class Empleado {
     private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String clave;
    private int Rold;

    public Empleado() {
    }

    public Empleado(int id, String nombre, String apellido, String usuario, String clave, int Rold) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
        this.Rold = Rold;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRold() {
        return Rold;
    }

    public void setRold(int Rold) {
        this.Rold = Rold;
    }
    
    
}
