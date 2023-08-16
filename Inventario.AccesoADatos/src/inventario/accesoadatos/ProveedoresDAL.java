package inventario.accesoadatos;

import java.util.*;
import java.sql.*;
import inventario.entidadesdelnegocio.*;
        
public class ProveedoresDAL {
    
     
     static String obtenerCampos() {
        return "prov.Id, prov.Nombre";
    }
    
    private static String obtenerSelect(Proveedores pProveedores) {
        String sql;
        sql = "SELECT ";
        if (pProveedores.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pProveedores.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Proveedores prov");
        return sql;
    }
    
    private static String agregarOrderBy(Proveedores pProveedores) {
        String sql = " ORDER BY prov.Id DESC";
        if (pProveedores.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pProveedores.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Proveedores pProveedores) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO  Proveedores (Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pProveedores.getNombre());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int modificar(Proveedores pProveedores) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Proveedores SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pProveedores.getNombre());
                ps.setInt(2, pProveedores.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    }
    
    public static int eliminar(Proveedores pProveedores) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Proveedores WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pProveedores.getId());
                result = ps.executeUpdate();
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return result;
    } 
    
    static int asignarDatosResultSet(Proveedores pProveedores, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pProveedores.setId(pResultSet.getInt(pIndex));
        pIndex++;
       pProveedores.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Proveedores> pProveedores) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Proveedores proveedores = new Proveedores(); 
                asignarDatosResultSet(proveedores, resultSet, 0);
                pProveedores.add(proveedores);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Proveedores obtenerPorId(Proveedores pProveedores) throws Exception {
        Proveedores proveedores = new Proveedores();
        ArrayList<Proveedores> proveedor = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pProveedores);
            sql += " WHERE prov.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pProveedores.getId());
                obtenerDatos(ps, proveedor);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (proveedor.size() > 0) {
            proveedores = proveedor.get(0);
        }
        
        return proveedores;
    }
    
    public static ArrayList<Proveedores> obtenerTodos() throws Exception {
        ArrayList<Proveedores> proveedor = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Proveedores());
            sql += agregarOrderBy(new Proveedores());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, proveedor);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return proveedor;
    }
    
    static void querySelect(Proveedores pProveedores, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pProveedores.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" prov.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pProveedores.getId()); 
            }
        }

        if (pProveedores.getNombre() != null && pProveedores.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" prov.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pProveedores.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Proveedores> buscar(Proveedores pProveedores) throws Exception {
        ArrayList<Proveedores> proveedor = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pProveedores);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pProveedores, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pProveedores);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pProveedores, utilQuery);
                obtenerDatos(ps, proveedor);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return proveedor;
    }

    
    
}