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
    
    //-----------//
      public static Producto obtenerPorId(Producto pProducto) throws Exception {
        Producto producto = new Producto();
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pProducto);
            sql += " WHERE pro.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pProducto.getId());
                obtenerDatos(ps, productos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (!productos.isEmpty()) {
            producto = productos.get(0);
        }
        return producto;
    }
    
    public static ArrayList<Producto> obtenerTodos() throws Exception {
        ArrayList<Producto> productos = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Producto()); 
            sql += agregarOrderBy(new Producto());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, productos);
                ps.close();
            } catch (SQLException ex) {
                throw ex; 
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return productos;
    }
    
    static void querySelect(Producto pProducto, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pProducto.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" pro.Id=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getId());
            }
        }

        if (pProducto.getMarcasId()> 0) {
            pUtilQuery.AgregarNumWhere(" pro.MarcasId=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getMarcasId());
            }
        }
        
        if (pProducto.getProveedoresId()> 0) {
            pUtilQuery.AgregarNumWhere(" pro.ProveedoresId=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getProveedoresId());
            }
        }
        
        if (pProducto.getCategoriaId()> 0) {
            pUtilQuery.AgregarNumWhere(" pro.CategoriaId=? ");
            if (statement != null) {
                statement.setInt(pUtilQuery.getNumWhere(), pProducto.getCategoriaId());
            }
        }
        
        if (pProducto.getNombre() != null && pProducto.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" pro.Nombre LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pProducto.getNombre() + "%");
            }
        }
        
        if (pProducto.getCodigo() != null && pProducto.getCodigo().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" pro.Codigo LIKE ? ");
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pProducto.getCodigo() + "%");
            }
        }

             
    }
    
    public static ArrayList<Producto> buscar(Producto pProducto) throws Exception {
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pProducto);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pProducto, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pProducto);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pProducto, utilQuery);
                obtenerDatos(ps, productos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        return productos;
    }
    
    public static ArrayList<Producto> buscarIncluirRelaciones(Producto pProducto) throws Exception {
        ArrayList<Producto> productos = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = "SELECT ";
            if (pProducto.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pProducto.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ", ";
            sql += MarcasDAL.obtenerCampos();
            sql += ", ";
            sql += ProveedoresDAL.obtenerCampos();
            sql += ", ";
            sql += CategoriaDAL.obtenerCampos();
            sql += " FROM Producto pro";
            sql += " INNER JOIN Marcas marc on (pro.MarcasId = marc.Id)";
            sql += " INNER JOIN Proveedores prov on (pro.ProveedoresId = prov.Id)";
            sql += " INNER JOIN Categoria ca on (pro.CategoriaId = ca.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pProducto, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pProducto);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pProducto, utilQuery);
                obtenerDatosIncluirRelaciones(ps, productos);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return productos;
    }
    
}
