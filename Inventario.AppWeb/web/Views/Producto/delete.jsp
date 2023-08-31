
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio..Categoria"%>
<%@page import="inventario.entidadesdelnegocio.Marcas"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>
<% Producto producto = (Producto) request.getAttribute("producto");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Eliminar Producto</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Eliminar Producto</h5>
            <form action="Producto" method="post">  
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=producto.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=producto.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtCodigo" type="text" value="<%=producto.getCodigo()%>" disabled>
                        <label for="txtCodigo">Codigo</label>
                    </div>   
                    
                    <div class="input-field col l4 s12">
                        <input id="txtCategoria" type="text" value="<%=producto.getCategoria().getNombre() %>" disabled>
                        <label for="txtCategoria">Categoria</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input id="txtMarca" type="text" value="<%=producto.getMarca().getNombre() %>" disabled>
                        <label for="txtMarca">Marca</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input id="txtProveedores" type="text" value="<%=producto.getProveedores().getNombre() %>" disabled>
                        <label for="txtProveedores">Proveedores</label>
                    </div> 
                        <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" value="<%=producto.getCantidad()%>" disabled>
                        <label for="txtCantidad">Cantidad</label>
                    </div>  
                        <div class="input-field col l4 s12">
                        <input  id="txtIngreso" type="text" value="<%=producto.getIngreso()%>" disabled>
                        <label for="txtIngreso">Ingreso</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtSalida" type="text" value="<%=producto.getSalida()%>" disabled>
                        <label for="txtSalida">Salida</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" value="<%=producto.getTotal()%>" disabled>
                        <label for="txtTotal">Total</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">delete</i>Eliminar</button>
                        <a href="Producto" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />         
    </body>
</html>
