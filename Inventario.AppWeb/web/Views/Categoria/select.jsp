<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Categoria"%>
<%@page import="inventario.accesoadatos.CategoriaDAL"%>
<%@page import="java.util.ArrayList"%>

<% ArrayList<Categoria> categorias = CategoriaDAL.obtenerTodos();
    int id = Integer.parseInt(request.getParameter("id"));
%>
<select id="slCategoria" name="CategoriaId">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Categoria categoria : categorias) {%>
        <option <%=(id == categoria.getId()) ? "selected" : "" %>  value="<%=categoria.getId()%>"><%= categoria.getNombre()%></option>
    <%}%>
</select>
<label for="CategoriaId">Categoria</label>