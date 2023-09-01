<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Empleado"%>
<%@page import="inventario.entidadesdelnegocio.Rol"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Empleado> empleados = (ArrayList<Empleado>) request.getAttribute("empleados");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (empleados == null) {
        empleados = new ArrayList();
    } else if (empleados.size() > numReg) {
        double divNumPage = (double) empleados.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Lista de Empleados</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Empleado</h5>
            <form action="Empleado" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="apellido">
                        <label for="txtApellido">Apellido</label>
                    </div> 
                                        
                    <div class="input-field col l4 s12">   
                        <select id="slEstatus" name="estatus">
                            <option value="0">SELECCIONAR</option>
                            <option value="<%=Empleado.EstatusEmpleado.ACTIVO%>">ACTIVO</option>
                            <option value="<%=Empleado.EstatusEmpleado.INACTIVO%>">INACTIVO</option>
                        </select>       
                        <label for="slEstatus">Estatus</label>
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">search</i>Buscar</button>
                        <a href="Empleado?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>Nombre</th>  
                                    <th>Apellido</th>  
                                    <th>Usuario</th>  
                                    <th>Clave</th>   
                                    <th>RolId</th>   
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Empleado empleado : empleados) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                        String estatus = "";
                                        switch (empleado.getEstatus()) {
                                            case Empleado.EstatusEmpleado.ACTIVO:
                                                estatus = "ACTIVO";
                                                break;
                                            case Empleado.EstatusEmpleado.INACTIVO:
                                                estatus = "INACTIVO";
                                                break;
                                            default:
                                                estatus = "";
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=empleado.getNombre()%></td>  
                                    <td><%=empleado.getApellido()%></td>
                                    <td><%=empleado.getUsuario()%></td>  
                                    <td><%=empleado%></td>
                                    <td><%=empleado.getRol().getNombre()%></td> 
                                    <td>
                                        <div style="display:flex">
                                             <a href="Empleado?accion=edit&id=<%=empleado.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Empleado?accion=details&id=<%=empleado.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="Empleado?accion=delete&id=<%=empleado.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>             
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>