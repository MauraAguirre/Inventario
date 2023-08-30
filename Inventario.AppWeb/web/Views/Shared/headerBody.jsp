<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Inventario.appweb.utils.*"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>

<nav>
    <div class="nav-wrapper blue">
        <a href="Home" class="brand-logo">App Empresas</a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>       
        <ul class="right hide-on-med-and-down">  
            <% if (SessionUser.isAuth(request)) {  %>
            <li><a href="Home">Inicio</a></li>
            <li><a href="Categoria">Categoria</a></li>
            <li><a href="Empleado">Empleado</a></li>
            <li><a href="Marcas">Marcas</a></li>
            <li><a href="Producto">Producto</a></li>
            <li><a href="Empleado?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="Empleado?accion=login">Cerrar sesión</a></li>
            <%}%>
        </ul>
    </div>
</nav>

<ul class="sidenav" id="mobile-demo">
     <% if (SessionUser.isAuth(request)) {  %>
            <li><a href="Home">Inicio</a></li>
            <li><a href="Categoria">Categoria</a></li>
            <li><a href="Empleado">Empleado</a></li>
            <li><a href="Marcas">Marcas</a></li>
            <li><a href="Producto">Producto</a></li>
            <li><a href="Empleado?accion=cambiarpass">Cambiar password</a></li>
            <li><a href="Empleado?accion=login">Cerrar sesión</a></li>
     <%}%>
</ul>
