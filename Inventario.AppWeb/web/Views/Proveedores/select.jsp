<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>
<%@page import="inventario.accesoadatos.ProveedoresDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Proveedores> proveedores = ProveedoresDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slProveedores" name="ProveedoresId">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Proveedores proveedor : proveedores) {%>
    <option <%=(id == proveedor.getId()) ? "selected" : ""%>  value="<%=proveedor.getId()%>"><%= proveedor.getNombre()%></option>
    <%}%>
</select>
<label for="ProveedoresId">Proveedores</label>