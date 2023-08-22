
package inventario.appweb.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import inventario.accesoadatos.ProductoDAL;
import inventario.accesoadatos.CategoriaDAL;
import inventario.accesoadatos.MarcasDAL;
import inventario.accesoadatos.ProveedoresDAL;


import inventario.entidadesdelnegocio.Producto ;
import inventario.entidadesdelnegocio.Categoria ;
import inventario.entidadesdelnegocio.Marcas ;
import inventario.entidadesdelnegocio.Proveedores;

import inventario.appweb.utils.*;


@WebServlet(name = "ProductoServlet", urlPatterns = {"/Producto"})
public class ProductoServlet extends HttpServlet {

    private Producto obtenerProducto (HttpServletRequest request) {
        String accion = Utilidad.getParameter(request, "accion", "index");
        Producto producto = new Producto();
        producto.setNombre(Utilidad.getParameter(request, "nombre", ""));
        producto.setCodigo(Utilidad.getParameter(request, "codigo", ""));
        producto.setCategoriaId(Integer.parseInt(Utilidad.getParameter(request, "categoriaId", "0")));
        producto.setMarcasId(Integer.parseInt(Utilidad.getParameter(request, "marcasId", "0")));
        producto.setProveedoresId(Integer.parseInt(Utilidad.getParameter(request, "proveedoresId", "0")));
        producto.setCantidad(Integer.parseInt(Utilidad.getParameter(request, "cantidad", "")));
        producto.setIngreso(Integer.parseInt(Utilidad.getParameter(request, "ingreso", "")));
        producto.setSalida(Integer.parseInt(Utilidad.getParameter(request, "salida", "")));
        producto.setTotal(Integer.parseInt(Utilidad.getParameter(request, "total", "")));
        
        
        if (accion.equals("index")) {
            producto.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            producto.setTop_aux(producto.getTop_aux() == 0 ? Integer.MAX_VALUE : producto.getTop_aux());
        
        
        } else {
            producto.setId(Integer.parseInt(Utilidad.getParameter(request, "id", "0")));
        }
        return producto;
    }
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Producto  producto  = new Producto();
            producto.setTop_aux(10);
            ArrayList<Producto> productos = ProductoDAL.buscarIncluirRelaciones(producto);
            request.setAttribute("producto", productos);
            request.setAttribute("top_aux", producto.getTop_aux());
            request.getRequestDispatcher("Views/Producto/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Producto producto = obtenerProducto(request);
            ArrayList<Producto> productos = ProductoDAL.buscarIncluirRelaciones(producto);
            request.setAttribute("productos", producto);
            request.setAttribute("top_aux", producto.getTop_aux());
            request.getRequestDispatcher("Views/Producto/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
     private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("Views/Producto/create.jsp").forward(request, response);
    }

    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Producto producto = obtenerProducto(request);
            int result =ProductoDAL.crear(producto);
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
            Producto producto = obtenerProducto(request);
            Producto producto_result = ProductoDAL.obtenerPorId(producto);
            if (producto_result.getId() > 0) {
                Categoria categoria = new Categoria();
                categoria.setId(producto_result.getCategoriaId());
                producto_result.setCategoria(CategoriaDAL.obtenerPorId(categoria));
                request.setAttribute("usuario", producto_result);
            } else {
                Utilidad.enviarError("El Id:" + producto_result.getId() + " no existe en la tabla de Producto", request, response);
            }
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
        //preguntar como poner las demas llaves fornaneas
    }

    
     private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Producto/edit.jsp").forward(request, response);
    }

    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Producto producto = obtenerProducto(request);
            int result = ProductoDAL.modificar(producto);
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
        request.getRequestDispatcher("Views/Producto/details.jsp").forward(request, response);
    }

    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestObtenerPorId(request, response);
        request.getRequestDispatcher("Views/Producto/delete.jsp").forward(request, response);
    }

    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Producto producto = obtenerProducto(request);
            int result = ProductoDAL.eliminar(producto);
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
    

}
