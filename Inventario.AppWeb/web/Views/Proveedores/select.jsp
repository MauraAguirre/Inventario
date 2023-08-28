<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>
<%@page import="inventario.accesoadatos.ProveedoresDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Proveedores> proveedores = ProveedoresDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slProveedores" name="idProveedores">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Proveedores proveedores : proveedores) {%>
    <option <%=(id == proveedores.getId()) ? "selected" : ""%>  value="<%=proveedores.getId()%>"><%= proveedores.getNombre()%></option>
    <%}%>
</select>
<label for="idProveedores">Proveedores</label>