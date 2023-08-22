
package inventario.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import inventario.accesoadatos.RolDAL;
import inventario.accesoadatos.EmpleadoDAL;
import inventario.appweb.utils.*;
import inventario.entidadesdelnegocio.Rol;
import inventario.entidadesdelnegocio.Empleado;

@WebServlet(name = "EmpleadoServlet", urlPatterns = {"/Empleado"})
public class EmpleadoServlet extends HttpServlet {

     private Empleado obtenerEmpleado(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Empleado emple = new Empleado();
        emple.setNombre(Utilidad.getParameter(request, "nombre", ""));
        emple.setApellido(Utilidad.getParameter(request, "apellido", ""));
        emple.setUsuario(Utilidad.getParameter(request, "usuario", ""));
        emple.setRolId(Integer.parseInt(Utilidad.getParameter(request, "idRol", "0")));
        emple.setEstatus(Byte.parseByte(Utilidad.getParameter(request, "estatus", "0")));
      
         if (accion.equals("index")) {
            emple.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            emple.setTop_aux(emple.getTop_aux() == 0 ? Integer.MAX_VALUE : emple.getTop_aux());
        }
        
        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpass")) {
            emple.setClave(Utilidad.getParameter(request, "password", ""));
            emple.setConfirmPassword_aux(Utilidad.getParameter(request, "confirmPassword_aux", ""));
            if (accion.equals("cambiarpass")) {
                emple.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
            }
        } else {
            emple.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        return emple;
    }
      private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = new Empleado();
            emple.setTop_aux(10);
            ArrayList<Empleado> empleados = EmpleadoDAL.buscarIncluirRol(emple);
            request.setAttribute("empleados", empleados);
            request.setAttribute("top_aux", emple.getTop_aux());
            request.getRequestDispatcher("Views/Empleado/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
        private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            ArrayList<Empleado> empleados = EmpleadoDAL.buscarIncluirEmpleado(emple);
            request.setAttribute("empleados", empleados);
            request.setAttribute("top_aux", emple.getTop_aux());
            request.getRequestDispatcher("Views/Empleado/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
 private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Empleado/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            int result = EmpleadoDAL.crear(emple);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro realizar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            Empleado emple_result = EmpleadoDAL.obtenerPorId(emple);
            if (emple_result.getId() > 0) {
                Rol rol = new Rol();
                rol.setId(emple_result.getRolId());
                emple_result.setRol(RolDAL.obtenerPorId(rol));
                request.setAttribute("empleado", emple_result);
            } else {
                Utilidad.enviarError("El Id:" + emple_result.getId() + " no existe en la tabla de Empleado", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empleado/edit.jsp").forward(request, response);
    }
     private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            int result = EmpleadoDAL.modificar(emple);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
     private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Empleado/details.jsp").forward(request, response);
    }
 private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Usuario/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            int result = EmpleadoDAL.eliminar(emple);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUser.cerrarSession(request);
        request.getRequestDispatcher("Views/Usuario/login.jsp").forward(request, response);
    }

    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Empleado emple = obtenerEmpleado(request);
            Empleado emple_auth = EmpleadoDAL.login(emple);
            if (emple_auth.getId() != 0 && emple_auth.getUsuario().equals(emple.getUsuario())) {
                Rol rol = new Rol();
                rol.setId(emple_auth.getRolId());
                emple_auth.setRol(RolDAL.obtenerPorId(rol));
                SessionUser.autenticarEmpleado(request, emple_auth);
                response.sendRedirect("Home");
            } else {
                request.setAttribute("error", "Credenciales incorrectas");
                request.setAttribute("accion", "login");
                doGetRequestLogin(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("error", ex.getMessage());
        }
    }
}
