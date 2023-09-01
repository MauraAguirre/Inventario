package inventario.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import inventario.accesoadatos.ProveedoresDAL;
import inventario.appweb.utils.SessionUser;
import inventario.appweb.utils.Utilidad;
import inventario.entidadesdelnegocio.Proveedores;


@WebServlet(name = "ProveedoresServlet", urlPatterns = {"/Proveedor"})
public class ProveedorServlet extends HttpServlet {

    private Proveedores obtenerProveedores(HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Proveedores proveedores = new Proveedores();
        if (accion.equals("create") == false) {
            proveedores.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }

        proveedores.setNombre(Utilidad.getParameter(request, "nombre", ""));
        if (accion.equals("index")) {
            proveedores.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            proveedores.setTop_aux(proveedores.getTop_aux() == 0 ? Integer.MAX_VALUE : proveedores.getTop_aux());
        }
        
        return proveedores;
    }
    
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = new Proveedores();
            proveedor.setTop_aux(10);
            ArrayList<Proveedores> proveedores = ProveedoresDAL.buscar(proveedor);
            request.setAttribute("proveedores", proveedores);
            request.setAttribute("top_aux", proveedor.getTop_aux());             
            request.getRequestDispatcher("Views/Proveedores/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = obtenerProveedores(request);
            ArrayList<Proveedores> proveedores = ProveedoresDAL.buscar(proveedor);
            request.setAttribute("proveedores", proveedores);
            request.setAttribute("top_aux", proveedor.getTop_aux());
            request.getRequestDispatcher("Views/Proveedores/index.jsp").forward(request, response);
        } catch (Exception ex) { 
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Proveedores/create.jsp").forward(request, response);
    }
    
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = obtenerProveedores(request);
            int result = ProveedoresDAL.crear(proveedor);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void requestObtenerPorId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = obtenerProveedores(request);
            Proveedores proveedor_result = ProveedoresDAL.obtenerPorId(proveedor);
            if (proveedor_result.getId() > 0) {
                request.setAttribute("proveedor", proveedor_result);
            } else {
                Utilidad.enviarError("El Id:" + proveedor.getId() + " no existe en la tabla de Proveedores", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Proveedores/edit.jsp").forward(request, response);
    }
    
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = obtenerProveedores(request);
            int result = ProveedoresDAL.modificar(proveedor);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);
            } else {
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Proveedores/details.jsp").forward(request, response);
    }
    
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Proveedores/delete.jsp").forward(request, response);
    }
    
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Proveedores proveedor = obtenerProveedores(request);
            int result = ProveedoresDAL.eliminar(proveedor);
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


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doGetRequestDelete(request, response);
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    doGetRequestDetails(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    doPostRequestIndex(request, response);
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    doPostRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    doPostRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    doPostRequestDelete(request, response);
                    break;
                default:
                    request.setAttribute("accion", accion);
                    doGetRequestIndex(request, response);
            }
        });
    }

}
