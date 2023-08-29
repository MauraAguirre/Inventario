<%-- 
    Document   : edit
    Created on : 20 ago. 2023, 23:29:38
    Author     : MINEDUCYT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio..Categoria"%>
<%@page import="inventario.entidadesdelnegocio.Marcas"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>
<% Producto producto = (Producto) request.getAttribute("producto");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Producto</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Producto</h5>
            <form action="Producto" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=producto.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=producto.getNombre()%>" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtCodigo" type="text" name="codigo" value="<%=producto.getCodigo()%>" required class="validate" maxlength="30">
                        <label for="txtCodigo">Codigo</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="cantidad" value="<%=producto.getCantidad()%>" required  class="validate" maxlength="25">
                        <label for="txtCantidad">Cantidad</label>
                    </div>                     
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Categoria/select.jsp">                           
                            <jsp:param name="id" value="<%=producto.getCategoriaId() %>" />  
                        </jsp:include>  
                        <span id="slCategoria_error" style="color:red" class="helper-text"></span>
                    </div>
                        <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Marcas/select.jsp">                           
                            <jsp:param name="id" value="<%=producto.getMarcasId() %>" />  
                        </jsp:include>  
                        <span id="slMarcas_error" style="color:red" class="helper-text"></span>
                    </div>
                        <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Proveedores/select.jsp">                           
                            <jsp:param name="id" value="<%=producto.getProveedoresId() %>" />  
                        </jsp:include>  
                        <span id="slProveedores_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Producto" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                
                var slCategoria = document.getElementById("slCategoria");
                var slCategoria_error = document.getElementById("slCategoria_error");
                var slMarcas = document.getElementById("slMarcas");
                var slMarcas_error = document.getElementById("slMarcas_error");
                var slProveedores = document.getElementById("slProveedores");
                var slProveedores_error = document.getElementById("slProveedores_error");
                if (slCategoria.value == 0) {
                    slCategoria_error.innerHTML = "La Categoría es obligatorio";
                    result = false;
                } else {
                    slCategoria_error.innerHTML = "";
                }
                if (slMarcas.value == 0) {
                    slMarcas_error.innerHTML = "La Marca es Obligatoria es obligatorio";
                    result = false;
                } else {
                    slMarcas_error.innerHTML = "";
                }
                if (slProveedores.value == 0) {
                    slProveedores_error.innerHTML = "El Proveedor es obligatorio";
                    result = false;
                } else {
                    slProveedores_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>
