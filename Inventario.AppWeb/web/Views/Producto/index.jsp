
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="inventario.entidadesdelnegocio.Categoria"%>
<%@page import="inventario.entidadesdelnegocio.Marcas"%>
<%@page import="inventario.entidadesdelnegocio.Proveedores"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Producto> productos = (ArrayList<Producto>) request.getAttribute("productos");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (productos == null) {
        productos = new ArrayList();
    } else if (productos.size() > numReg) {
        double divNumPage = (double) productos.size() / (double) numReg;
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
        <title>Lista de Productos</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Productos</h5>
            <form action="Producto" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="Nombre">
                        <label for="txtNombre">Nombre</label>
                    </div>  
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtCodigo" type="text" name="codigo">
                        <label for="txtCodigo">Codigo</label>
                    </div>           
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Categoria/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Marcas/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Proveedores/select.jsp">                           
                            <jsp:param name="id" value="0" />  
                        </jsp:include>                        
                    </div>
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtCantidad" type="text" name="cantidad">
                        <label for="txtCantidad">Cantidad</label>
                    </div>
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtIngreso" type="text" name="ingreso">
                        <label for="txtIngreso">Ingreso</label>
                    </div>
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtSalida" type="text" name="salida">
                        <label for="txtSalida">Salida</label>
                    </div>
                    
                    <div class="input-field col l4 s12">
                        <input  id="txtTotal" type="text" name="total">
                        <label for="txtTotal">Total</label>
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
                        <a href="Producto?accion=create" class="waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
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
                                    <th>Codigo</th> 
                                    <th>Categoria/th>  
                                    <th>Marcas</th>  
                                    <th>Proveedores</th>   
                                    <th>Cantidad</th>  
                                    <th>Ingreso</th> 
                                    <th>Salida</th> 
                                    <th>Total</th> 
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (Producto producto : producto) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=producto.getNombre()%></td>  
                                    <td><%=producto.getCodigo()%></td>
                                    <td><%=producto.getCategoria().getNombre()%></td> 
                                    <td><%=producto.getMarca().getNombre()%></td> 
                                    <td><%=producto.getProveedores().getNombre()%></td> 
                                    <td><%=producto.getCantidad()%></td>
                                    <td><%=producto.getIngreso()%></td>
                                    <td><%=producto.getSalida()%></td>
                                    <td><%=producto.getTotal()%></td>
                                    <td>
                                        <div style="display:flex">
                                             <a href="Producto?accion=edit&id=<%=producto.getId()%>" title="Modificar" class="waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="Producto?accion=details&id=<%=producto.getId()%>" title="Ver" class="waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="Producto?accion=delete&id=<%=producto.getId()%>" title="Eliminar" class="waves-effect waves-light btn red">
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
