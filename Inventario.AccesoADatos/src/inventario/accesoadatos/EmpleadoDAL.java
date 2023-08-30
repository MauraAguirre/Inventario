package inventario.accesoadatos;

import static inventario.accesoadatos.MarcasDAL.querySelect;
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
        sql += (obtenerCampos() + " FROM Usuario u");
        return sql;
    }
    
    private static String agregarOrderBy(Empleado pEmpleado) {
        String sql = " ORDER BY u.Id DESC";
        if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.MYSQL) {
            sql += " LIMIT " + pEmpleado.getTop_aux() + " ";
        }
        return sql;
    }
    
    private static boolean existeLogin(Empleado pEmpleado) throws Exception {
        boolean existe = false;
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE u.Id<>? AND u.Login=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
                ps.setString(2, pEmpleado.getUsuario());
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            Empleado empleado = empleados.get(0);
            if (empleado.getId() > 0 && empleado.getUsuario().equals(empleado.getUsuario())) {
                existe = true;
            }
        }
        return existe;
    }
    
    public static int crear(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pEmpleado);
        if (existe == false) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "INSERT INTO Empleado(Nombre, Apellido, Usuario, Clave, RolId) VALUES(?,?,?,?,?,?,?)";
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
        } else {
            result = 0;
            throw new RuntimeException("Este usuario ya existe");
        }
        return result;
    }
    
    public static int modificar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        boolean existe = existeLogin(pEmpleado);
        if (existe == false) {
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
        } else {
            result = 0;
            throw new RuntimeException("Este usuario ya existe");
        }
        return result;
    }
    
    public static int eliminar(Empleado pEmpleado) throws Exception {
        int result;
        String sql;
        try (Connection conn = ComunDB.obtenerConexion();) { 
            sql = "DELETE FROM Usuario WHERE Id=?"; 
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
    
    private static void obtenerDatosIncluirRol(PreparedStatement pPS, ArrayList<Empleado> pEmpleado) throws Exception {
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
    
    public static Empleado obtenerPorId(Empleado pEmpleado) throws Exception {
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE u.Id=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setInt(1, pEmpleado.getId());
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            empleado = empleados.get(0);
        }
        return empleado;
    } 
    
        static void querySelect(Empleado pEmpleado, ComunDB.utilQuery pUtilQuery) throws SQLException {
        PreparedStatement statement = pUtilQuery.getStatement();
        if (pEmpleado.getId() > 0) {
            pUtilQuery.AgregarNumWhere(" marc.Id=? ");
            if (statement != null) { 
                statement.setInt(pUtilQuery.getNumWhere(), pEmpleado.getId()); 
            }
        }

        if (pEmpleado.getNombre() != null && pEmpleado.getNombre().trim().isEmpty() == false) {
            pUtilQuery.AgregarNumWhere(" marc.Nombre LIKE ? "); 
            if (statement != null) {
                statement.setString(pUtilQuery.getNumWhere(), "%" + pEmpleado.getNombre() + "%"); 
            }
        }
    }
    
    public static ArrayList<Empleado> buscar(Empleado pEmpleado) throws Exception {
        ArrayList<Empleado> empleados = new ArrayList();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0); 
            querySelect(pEmpleado, utilQuery);
            sql = utilQuery.getSQL(); 
            sql += agregarOrderBy(pEmpleado);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0); 
                querySelect(pEmpleado, utilQuery);
                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        }
        catch (SQLException ex) {
            throw ex;
        }
        return empleados;
    }
    
    public static Empleado login(Empleado pEmpleado) throws Exception {
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados = new ArrayList();
        String password = pEmpleado.getClave();
        try (Connection conn = ComunDB.obtenerConexion();) {
            String sql = obtenerSelect(pEmpleado);
            sql += " WHERE u.Login=? AND u.Password=? AND u.Estatus=?";
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                ps.setString(1, pEmpleado.getUsuario());
                ps.setString(2, password);
                ps.setByte(3, Empleado.EstatusEmpleado.ACTIVO);
                ps.setByte(3, Empleado.EstatusEmpleado.INACTIVO);

                obtenerDatos(ps, empleados);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } 
        catch (SQLException ex) {
            throw ex;
        }
        if (empleados.size() > 0) {
            empleado = empleados.get(0);
        }
        return empleado;
    }
    
    public static int cambiarPassword(Empleado pEmpleado, String pPasswordAnt) throws Exception {
        int result;
        String sql;
        Empleado empleadoAnt = new Empleado();
        empleadoAnt.setUsuario(pEmpleado.getUsuario());
        empleadoAnt.setClave(pPasswordAnt);
        Empleado empleadoAut = login(empleadoAnt);

        if (empleadoAut.getId() > 0 && empleadoAut.getUsuario().equals(pEmpleado.getUsuario())) {
            try (Connection conn = ComunDB.obtenerConexion();) {
                sql = "UPDATE Usuario SET Password=? WHERE Id=?";
                try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                    ps.setString(1, pEmpleado.getClave()); 
                    ps.setInt(2, pEmpleado.getId());
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
        } else {
            result = 0;
            throw new RuntimeException("El password actual es incorrecto");
        }
        return result;
    }
    
   public static ArrayList<Empleado> buscarIncluirRol(Empleado pEmpleado) throws Exception {
        ArrayList<Empleado> empleado = new ArrayList();
       try (Connection conn = ComunDB.obtenerConexion();) {
           String sql = "SELECT ";
            if (pEmpleado.getTop_aux() > 0 && ComunDB.TIPODB == ComunDB.TipoDB.SQLSERVER) {
                sql += "TOP " + pEmpleado.getTop_aux() + " "; 
            }
            sql += obtenerCampos();
            sql += ",";
           sql += RolDAL.obtenerCampos();
            sql += " FROM Empleado e";
            sql += " JOIN Rol r on (e.RolId=r.Id)";
            ComunDB comundb = new ComunDB();
            ComunDB.utilQuery utilQuery = comundb.new utilQuery(sql, null, 0);
            querySelect(pEmpleado, utilQuery);
            sql = utilQuery.getSQL();
            sql += agregarOrderBy(pEmpleado);
            try (PreparedStatement ps = ComunDB.createPreparedStatement(conn, sql);) {
                utilQuery.setStatement(ps);
                utilQuery.setSQL(null);
                utilQuery.setNumWhere(0);
                querySelect(pEmpleado, utilQuery);
                obtenerDatosIncluirRol(ps, empleado);
                ps.close();
            } catch (SQLException ex) {
                throw ex;
            }
            conn.close();
        } catch (SQLException ex) {
            throw ex;
        }
        return empleado;
 
       }
   }




