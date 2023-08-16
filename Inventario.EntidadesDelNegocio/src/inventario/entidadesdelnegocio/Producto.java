package inventario.entidadesdelnegocio;
    
public class Producto {
          private int id;
    private String nombre;
    private String codigo;
    private int categoriaId;
    private int marcasId;
    private int proveedoresId;
    private int cantidad;
    private int ingreso;
    private int salida;
    private int total;
    private int top_aux;
    private Categoria categoria;
    private Marcas marca;
    private Proveedores proveedores;
    


    public Producto() {
    }
    
    public Producto(int id, String nombre, String codigo, int categoriaId, int marcasId, int proveedoresId, int cantidad, int ingreso, int salida, int total, int top_aux, Categoria categoria, Marcas marca, Proveedores proveedores) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.categoriaId = categoriaId;
        this.marcasId = marcasId;
        this.proveedoresId = proveedoresId;
        this.cantidad = cantidad;
        this.ingreso = ingreso;
        this.salida = salida;
        this.total = total;
        this.top_aux = top_aux;
        this.categoria = categoria;
        this.marca = marca;
        this.proveedores = proveedores;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getMarcasId() {
        return marcasId;
    }

    public void setMarcasId(int marcasId) {
        this.marcasId = marcasId;
    }

    public int getProveedoresId() {
        return proveedoresId;
    }

    public void setProveedoresId(int proveedoresId) {
        this.proveedoresId = proveedoresId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIngreso() {
        return ingreso;
    }

    public void setIngreso(int ingreso) {
        this.ingreso = ingreso;
    }

    public int getSalida() {
        return salida;
    }

    public void setSalida(int salida) {
        this.salida = salida;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Marcas getMarca() {
        return marca;
    }

    public void setMarca(Marcas marca) {
        this.marca = marca;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

}
