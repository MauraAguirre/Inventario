package inventario.accesoadatos;

import inventario.entidadesdelnegocio.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import inventario.entidadesdelnegocio.*;
import java.util.HashMap;


public class EmpleadoDAL {
    static String obtenerCampos() {
        return "em.Id, em.Nombre, em.Apellido, em.Usuario, em.Clave, em.RolId";
    }
    
    private static String obtenerSelect(Empleado pEmpleado) {
        String sql;
        sql = "SELECT ";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {            
            sql += "TOP " + pEmpleado.getTop_aux() + " ";
        }
        sql += (obtenerCampos() + " FROM Empleado e");
        return sql;
    }
    
    private static String agregarOrderBy(Empleado pEmpleado) {
        String sql = " ORDER BY e.Id DESC";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEmpleado.getTop_aux() + " ";
        }
        return sql;
    }
    
    public static int crear(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Empleado(Nombre, Apellido, Usuario, Clave, RolId) VALUES(?,?,?,?,?)";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, pEmpleado.getNombre());
                    ps.setString(2, pEmpleado.getApellido()); 
                    ps.setString(3, pEmpleado.getUsuario());
                    ps.setString(4, pEmpleado.getClave());
                    ps.setInt(5, pEmpleado.getRolId());
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
    
    public static int modificar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
            try (Connection conn = ComunDB.obtenerConexion();) {                
                sql = "UPDATE Empleado SET Nombre =?, Apellido =?, Usuario =?, Clave =?, RolId =? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                   ps.setString(1, pEmpleado.getNombre());
                    ps.setString(2, pEmpleado.getApellido()); 
                    ps.setString(3, pEmpleado.getUsuario());
                    ps.setString(4, pEmpleado.getClave());
                    ps.setInt(5, pEmpleado.getRolId());
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
    
    public static int eliminar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Empleado WHERE Id=?"; 
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
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
    
    
       static int asignarDatosResultSet(Empleado pEmpleado, ResultSet pResultSet, int pIndex) throws Exception {
        pIndex++;
        pEmpleado.setId(pResultSet.getInt(pIndex)); 
        pIndex++;
        pEmpleado.setNombre(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setApellido(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setUsuario(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setClave(pResultSet.getString(pIndex)); 
        pIndex++;
        pEmpleado.setRolId(pResultSet.getInt(pIndex)); //preguntar si el smallint es = a byte
        
        return pIndex;
        
    }
    
    private static void obtenerDatos(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) { 
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                asignarDatosResultSet(empleado, resultSet, 0);
                pEmpleado.add(empleado);
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex;
        }
    }
   
    private static void obtenerDatosIncluirRelaciones(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
        try (ResultSet resultSet = ComunDB.obtenerResultSet(pPS);) {
            HashMap<Integer, Rol> rolMap = new HashMap(); 
            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                int index = asignarDatosResultSet(empleado, resultSet, 0);
                if (rolMap.containsKey(empleado.getRolId()) == false) {
                    Rol rol = new Rol();
                    RolDAL.asignarDatosResultSet(rol, resultSet, index);
                    rolMap.put(rol.getId(), rol); 
                    empleado.setRol(rol); 
                } else {
                    empleado.setRol(rolMap.get(empleado.getRolId())); 
                }
                
                
                pEmpleado.add(empleado); 
            }
            resultSet.close();
        } catch (SQLException ex) {
            throw ex; 
        }
    }
}
