<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Empleado"%>
<% Empleado empleado = (Empleado) request.getAttribute("empleado");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Empleado</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Empleado</h5>
            <form action="Empleado" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=empleado.getId()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre" value="<%=empleado.getNombre()%>" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="apellido" value="<%=empleado.getApellido()%>" required class="validate" maxlength="30">
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtUsuario" type="text" name="usuario" value="<%=empleado.getUsuario()%>" required  class="validate" maxlength="25">
                        <label for="txtUsuario">Usuario</label>
                    </div>
                    
                   
                    <div class="input-field col l4 s12">
                        <input  id="txtRolId" type="text" name="RolId" value="<%=empleado.getRolId()%>" required  class="validate" maxlength="25">
                        <label for="txtRolId">RolId</label>
                    </div>
                    
                    
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Empleado" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
                        
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;                
                var slContacto = document.getElementById("slContacto");
                var slContacto_error = document.getElementById("slContacto_error");
                if (slContacto.value == 0) {
                    slContacto_error.innerHTML = "El Contacto es obligatorio";
                    result = false;
                } else {
                    slContacto_error.innerHTML = "";
                }

                return result;
            }
        </script>
    </body>
</html>