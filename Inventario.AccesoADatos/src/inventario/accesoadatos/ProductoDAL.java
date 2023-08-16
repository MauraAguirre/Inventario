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
                    ps.setint(3, pProducto.getCategoriaId());
                    ps.setint(4, pProducto.getMarcasId());
                    ps.setint(5, pProducto.getProveedoresId());
                    ps.setint(6, pProducto.getCantidad());
                    ps.setint(7, pProducto.getIngreso());
                    ps.setint(8, pProducto.getSalida());
           
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
                    ps.setint(3, pProducto.getCategoriaId());
                    ps.setint(4, pProducto.getMarcasId());
                    ps.setint(5, pProducto.getProveedoresId());
                    ps.setint(6, pProducto.getCantidad());
                    ps.setint(7, pProducto.getIngreso());
                    ps.setint(8, pProducto.getSalida());
                    ps.setint(9, pProducto.getId());
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
        pProducto.setProveedoresId(pResultSet.getByte(pIndex)); //preguntar si el smallint es = a byte
        pIndex++;
        pProducto.setCantidad(pResultSet.getInt(pIndex)); 
        pIndex++;
        pProducto.setIngreso(pResultSet.getInt(pIndex));
        pIndex++;
        pProducto.setSalida(pResultSet.getInt(pIndex)); 
       /* pIndex++;
        pProducto.setTotal(pResultSet.getInt(pIndex)); asi  iria el total?
        */
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
    //Preguntar si es uno para cada llaveprimaria, y porque da error 
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Producto> pProducto) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Categoria> categoriaMap = new HashMap(); 
            while (resultSet.next()) {
                Producto producto = new Producto();
                int index = asignarDatosResultSet(producto, resultSet, 0);
                if (categoriaMap.containsKey(producto.getCategoriaId()) == false) {
                    Categoria categoria = new Categoria();
                    CategoriaDAL.asignarDatosResultSet(categoria, resultSet, index); //este error se arreglara cuando se progame CategoriaDAL
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
