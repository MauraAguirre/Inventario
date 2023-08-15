package inventario.accesoadatos;

import inventario.entidadesdelnegocio.Marcas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MarcasDAL {
    
    static String obtenerCampos() {
        return "marc.Id, marc.Nombre";
    }
    
    private static String obtenerSelect(Marcas pMarcas) {
        String sql;
        sql = "SELECT ";
        if (pMarcas.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pMarcas.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Marcas m");
        return sql;
    }
    
    private static String agregarOrderBy(Marcas pMarcas) {
        String sql = " ORDER BY marc.Id DESC";
        if (pMarcas.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pMarcas.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Marcas pMarcas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "INSERT INTO  Marcas (Nombre) VALUES(?)";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pMarcas.getNombre());
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
    
    public static int modificar(Marcas pMarcas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "UPDATE Marcas SET Nombre=? WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pMarcas.getNombre());
                ps.setInt(2, pMarcas.getId());
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
    
    public static int eliminar(Marcas pMarcas) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) {
            sql = "DELETE FROM Marcas WHERE Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMarcas.getId());
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
    
    static int asignarDatosResultSet(Marcas pMarcas, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pMarcas.setId(pResultSet.getInt(pIndex));
        pIndex++;
       pMarcas.setNombre(pResultSet.getString(pIndex));
        return pIndex;
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Marcas> pMarcas) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            while (resultSet.next()) {
                Marcas marcas = new Marcas(); 
                asignarDatosResultSet(marcas, resultSet, 0);
                pMarcas.add(marcas);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
    
    public static Marcas obtenerPorId(Marcas pMarcas) throws Exception {
        Marcas marcas = new Marcas();
        ArrayList<Marcas> marca = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) { 
            String sql = obtenerSelect(pMarcas);
            sql += " WHERE marc.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pMarcas.getId());
                obtenerDatos(ps, marca);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        
        if (marca.size() > 0) {
            marcas = marca.get(0);
        }
        
        return marcas;
    }
    
    public static ArrayList<Marcas> obtenerTodos() throws Exception {
        ArrayList<Marcas> proveedor = new ArrayList<>();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(new Marcas());
            sql += agregarOrderBy(new Marcas());
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
    
    static void querySelect(Marcas pMarcas, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pMarcas.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" marc.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pMarcas.getId()); 
            }
        }

        if (pMarcas.getNombre() != null && pMarcas.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" r.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pMarcas.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Marcas> buscar(Marcas pMarcas) throws Exception {
        ArrayList<Marcas> marca = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pMarcas);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pMarcas, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pMarcas);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pMarcas, utilQuery);
                obtenerDatos(ps, marca);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return marca;
    }
}
