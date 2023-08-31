package inventario.accesoadatos;

import inventario.entidadesdelnegocio.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoriaDAL {
    static String obtenerCampos() {
        return "ca.Id, ca.Nombre";
    }
    
    private static String obtenerSelect(Categoria pCategoria) {
        String sql;
        sql = "SELECT ";
        if (pCategoria.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pCategoria.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Categoria ca");
        return sql;
    }
    
    private static String agregarOrderBy(Categoria pCategoria) {
        String sql = " ORDER BY ca.Id DESC";
        if (pCategoria.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pCategoria.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO  Categoria (Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pCategoria.getNombre());
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
    
    public static int modificar(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Categoria SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pCategoria.getNombre());
                ps.setInt(2, pCategoria.getId());
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
    
    public static int eliminar(Categoria pCategoria) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Categoria WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pCategoria.getId());
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
    
    static int asignarDatosResultSet(Categoria pCategoria, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pCategoria.setId(pResultSet.getInt(pIndex));
        pIndex++;
       pCategoria.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Categoria> pCategoria) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Categoria categoria = new Categoria(); 
                asignarDatosResultSet(categoria, resultSet, 0);
                pCategoria.add(categoria);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Categoria obtenerPorId(Categoria pCategoria) throws Exception {
        Categoria categorias = new Categoria();
        ArrayList<Categoria> categoria = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pCategoria);
            sql += " WHERE ca.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pCategoria.getId());
                obtenerDatos(ps, categoria);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (categoria.size() > 0) {
            categorias = categoria.get(0);
        }
        
        return categorias;
    }
    
    public static ArrayList<Categoria> obtenerTodos() throws Exception {
        ArrayList<Categoria> categoria = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Categoria());
            sql += agregarOrderBy(new Categoria());
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                obtenerDatos(ps, categoria);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        
        return categoria;
    }
    
    static void querySelect(Categoria pCategoria, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pCategoria.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" ca.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pCategoria.getId()); 
            }
        }

        if (pCategoria.getNombre() != null && pCategoria.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" ca.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pCategoria.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Categoria> buscar(Categoria pCategoria) throws Exception {
        ArrayList<Categoria> categoria = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pCategoria);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pCategoria, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pCategoria);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pCategoria, utilQuery);
                obtenerDatos(ps, categoria);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return categoria;
    }
}
