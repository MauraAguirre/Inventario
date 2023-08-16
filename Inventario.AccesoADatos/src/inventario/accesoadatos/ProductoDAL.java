package inventario.accesoadatos;

import java.util.*;
import java.sql.*;
import inventario.entidadesdelnegocio.*;

public class ProductoDAL {
    
    static String obtenerCampos() {
        return "pro.Id,  pro.Nombre, pro.Codigo, pro.CategoriaId, pro.MarcasId, pro.ProveedoresId, pro.Cantidad, pro.Ingreso, pro.Salida, pro.Total";
    }
    
    private static String obtenerSelect(Producto pProducto) {
        String sql;
        sql = "SELECT ";
        if (pProducto.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
             sql += "TOP " + pProducto.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Producto pro");
        return sql;
    }
    
    private static String agregarOrderBy(Producto pProducto) {
        String sql = " ORDER BY pro.Id DESC";
        if (pProducto.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pProducto.getTop_aux() + " ";
        }
        return sql;
    }
    
    
    public static int crear(Producto pProducto) throws Exception {
        int result;
        String sql;
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Producto(Nombre, Codigo, CategoriaId, MarcasId, ProveedoresId, Cantidad, Ingreso, Salida) VALUES(?,?,?,?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, pProducto.getNombre());
                    ps.setString(2, pProducto.getCodigo()); 
                    ps.setInt(3, pProducto.getCategoriaId());
                    ps.setInt(4, pProducto.getMarcasId());
                    ps.setInt(5, pProducto.getProveedoresId());
                    ps.setInt(6, pProducto.getCantidad());
                    ps.setInt(7, pProducto.getIngreso());
                    ps.setInt(8, pProducto.getSalida());
           
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            }
            catch (SQLException ex) {
                throw ex;
            }
        return result;
    }
    
    public static int modificar(Producto pProducto) throws Exception {
        int result;
        String sql;
            try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Producto SET Nombre =?, Codigo =?, CategoriaId =?, MarcasId =?, ProveedoresId =?, Cantidad =?, Ingreso=?, Salida=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                   ps.setString(1, pProducto.getNombre());
                    ps.setString(2, pProducto.getCodigo()); 
                    ps.setInt(3, pProducto.getCategoriaId());
                    ps.setInt(4, pProducto.getMarcasId());
                    ps.setInt(5, pProducto.getProveedoresId());
                    ps.setInt(6, pProducto.getCantidad());
                    ps.setInt(7, pProducto.getIngreso());
                    ps.setInt(8, pProducto.getSalida());
                    ps.setInt(9, pProducto.getId());
                    result = ps.executeUpdate();
                    ps.close();
                } catch (SQLException ex) {
                    throw ex;
                }
                conn.close();
            } 
            catch (SQLException ex) {
                throw ex;
            }
       
        return result;
    }
    
    public static int eliminar(Producto pProducto) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Producto WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pProducto.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    
       static int asignarDatosResultSet(Producto pProducto, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pProducto.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pProducto.setCodigo(pResultSet.getString(pIndex)); 
        pIndex++;
        pProducto.setCategoriaId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setMarcasId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setProveedoresId(pResultSet.getInt(pIndex)); //preguntar si el smallint es = a byte
        pIndex++;
        pProducto.setCantidad(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setIngreso(pResultSet.getInt(pIndex));
        pIndex++;
        pProducto.setSalida(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setTotal(pResultSet.getInt(pIndex));
        
        return pIndex;
        
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Producto producto = new Producto();
                asignarDatosResultSet(producto, resultSet, 0);
                pProducto.add(producto);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
   
    private static void obtenerDatosIncluirRelaciones(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Marcas> marcaMap = new HashMap(); 
            HashMap<Integer, Proveedores> proveedorMap = new HashMap();
            HashMap<Integer, Categoria> categoriaMap = new HashMap();
            while (resultSet.next()) {
                Producto producto = new Producto();
                int index = asignarDatosResultSet(producto, resultSet, 0);
                if (marcaMap.containsKey(producto.getMarcasId()) == false) {
                    Marcas marca = new Marcas();
                    MarcasDAL.asignarDatosResultSet(marca, resultSet, index);
                    marcaMap.put(marca.getId(), marca); 
                    producto.setMarca(marca); 
                } else {
                    producto.setMarca(marcaMap.get(producto.getMarcasId())); 
                }
                
                if (proveedorMap.containsKey(producto.getProveedoresId()) == false) {
                    Proveedores proveedor = new Proveedores();
                    ProveedoresDAL.asignarDatosResultSet(proveedor, resultSet, index+3);
                    proveedorMap.put(proveedor.getId(), proveedor); 
                    producto.setProveedores(proveedor); 
                } else {
                    producto.setProveedores(proveedorMap.get(producto.getProveedoresId())); 
                }
                
                if (categoriaMap.containsKey(producto.getCategoriaId()) == false) {
                    Categoria categoria = new Categoria();
                    CategoriaDAL.asignarDatosResultSet(categoria, resultSet, index+8);
                    categoriaMap.put(categoria.getId(), categoria); 
                    producto.setCategoria(categoria); 
                } else {
                    producto.setCategoria(categoriaMap.get(producto.getCategoriaId())); 
                }
                
                pProducto.add(producto); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
}
