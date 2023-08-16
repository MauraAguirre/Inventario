package inventario.entidadesdelnegocio;

public class Empleado {
     private int id;
    private String nombre;
    private String apellido;
    private String usuario;
    private String clave;
    private int RolId;
    private int top_aux;
    private Rol rol;
    private byte estatus;
    private String confirmPassword_aux;


    public Empleado() {
    }

    public Empleado(int id, String nombre, String apellido, String usuario, String clave, int RolId, int top_aux, Rol rol, byte estatus, String confirmPassword_aux) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.clave = clave;
        this.RolId = RolId;
        this.top_aux = top_aux;
        this.rol = rol;
        this.estatus = estatus;
        this.confirmPassword_aux = confirmPassword_aux;
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

    public int getRolId() {
        return RolId;
    }

    public void setRolId(int RolId) {
        this.RolId = RolId;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public byte getEtatus() {
        return estatus;
    }

    public void setStatus(byte estatus) {
        this.estatus = estatus;
    }

    public String getConfirmPassword_aux() {
        return confirmPassword_aux;
    }

    public void setConfirmPassword_aux(String confirmPassword_aux) {
        this.confirmPassword_aux = confirmPassword_aux;
    }
    
    public class EstatusEmpleado {
        public static final byte ACTIVO = 1;
        public static final byte INACTIVO = 2;

    }

   
}
