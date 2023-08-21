package inventario.appweb.utils;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import inventario.entidadesdelnegocio.*;

public class SessionUser {
    public static void autenticarEmpleado(HttpServletRequest request, Empleado pEmpleado) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600);
        session.setAttribute("auth", true);
        session.setAttribute("emple", pEmpleado.getUsuario());
        session.setAttribute("rol", pEmpleado.getRol().getNombre()); 
    }
    
    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }
    
    public static String getEmpleado(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String emple = "";
        if (SessionUser.isAuth(request)) { 
            emple = session.getAttribute("empleado") != null ? (String) session.getAttribute("empleado") : "";
        }
        return emple;
    }
    
    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) {
            user = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return user;
    }
    
    public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionUser.isAuth(request)) {
            pIAuthorize.authorize();
        } else {
            response.sendRedirect("Empleado?accion=Usuario");
        }
    }
    
    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
}
