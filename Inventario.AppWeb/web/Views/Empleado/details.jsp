<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Empleado"%>
<% Empleado empleados = (Empleado) request.getAttribute("empleados");%>

<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de la Empleado</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de la Empleado</h5>
             <div class="row">
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" value="<%=empleados.getNombre()%>" disabled>
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" value="<%=empleados.getApellido()%>" disabled>
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtUsuario" type="text" value="<%=empleados.getUsuario()%>" disabled>
                        <label for="txtUsuario">Usuario</label>
                    </div>      
                    <div class="input-field col l4 s12">
                        <input  id="txtClave" type="text" value="<%=empleados.getClave()%>" disabled>
                        <label for="txtClave">Clave</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input id="txtRolId" type="text" value="<%=empleados.getRolId().getNombre()%>" disabled>
                        <label for="txtRolId">RolId</label>
                    </div> 
                </div>

                <div class="row">
                    <div class="col l12 s12">
                         <a href="Empleado?accion=edit&id=<%=empleados.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>            
                        <a href="Empleado" class="waves-effect waves-light btn blue"><i class="material-icons right">cancel</i>Cancelar</a>                          
                    </div>
                </div>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />
    </body>
</html>