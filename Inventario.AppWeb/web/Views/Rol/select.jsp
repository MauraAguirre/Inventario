<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Rol"%>
<%@page import="inventario.accesoadatos.RolDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Rol> roles = RolDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slRol" name="RolId">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Rol rol : roles) {%>
    <option <%=(id == rol.getId()) ? "selected" : ""%>  value="<%=rol.getId()%>"><%= rol.getNombre()%></option>
    <%}%>
</select>
<label for="RolId">Rol</label>