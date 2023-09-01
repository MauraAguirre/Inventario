<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Marcas"%>
<%@page import="inventario.accesoadatos.MarcasDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Marcas> marcas = MarcasDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slMacas" name="MarcasId">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Marcas marca : marcas) {%>
    <option <%=(id == marca.getId()) ? "selected" : ""%>  value="<%=marca.getId()%>"><%= marca.getNombre()%></option>
    <%}%>
</select>
<label for="MarcasId">Marcas</label>