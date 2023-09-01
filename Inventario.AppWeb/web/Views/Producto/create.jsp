
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Categoria"%>
<%@page import="inventario.entidadesdelnegocio.Marcas"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Crear Nuevo Producto</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Crear Producto</h5>
            <form action="Producto" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">                
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtCodigo" type="text" name="codigo" required class="validate" maxlength="30">
                        <label for="txtCodigo">Codigo</label>
                    </div>                                         
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Categoria/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slCategoria_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Marcas/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slMarcas_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Proveedores/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>  
                        <span id="slProveedores_error" style="color:red" class="helper-text"></span>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="cantidad" required class="validate" maxlength="25">
                        <label for="txtCantidad">Cantidad</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtPassword" type="text" name="ingreso" required class="validate"  maxlength="32">
                        <label for="txtPassword">Ingreso</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtSalida" type="text" name="salida" required class="validate" maxlength="25">
                        <label for="txtSalida">Salida</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" name="total" required class="validate"  maxlength="32">
                        <label for="txtTotal">Total</label>
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
                var slMarcas = document.getElementById("slMarcas");
                var slProveedores = document.getElementById("slProveedores");
                var slCategoria_error = document.getElementById("slCategoria_error");
                var slMarcas_error = document.getElementById("slMarcas_error");
                var slProveedores = document.getElementById("slProveedores_error");

                if (slCategoria.value == 0) {
                    slCategoria_error.innerHTML = "La Categoria es obligatoria";
                    result = false;
                } else {
                    slCategoria_error.innerHTML = "";
                }
                if (slMarcas.value == 0) {
                    slMarcas_error.innerHTML = "La Marca es obligatoria";
                    result = false;
                } else {
                    slMarcas_error.innerHTML = "";
                }
                if (slProveedores.value == 0) {
                    slProveedores.innerHTML = "El Proveedor es obligatorio";
                    result = false;
                } else {
                    slProveedores.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>
