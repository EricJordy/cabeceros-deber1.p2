package servlet;
/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción: Servlet que lista productos en tabla muestra dos rutas:
 *  - /productos.html -- es lo que vemos en html
 *  - /productos.xls  -- trabjamos en un Excel
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
<<<<<<< HEAD
=======
import jakarta.servlet.http.Cookie;
>>>>>>> 8395a65 (uso de cookies)
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoService;
import service.ProductoServiceImplement;
<<<<<<< HEAD

import java.io.IOException;
import java.io.PrintWriter;
=======
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
>>>>>>> 8395a65 (uso de cookies)
import java.util.List;

// Anotación que mapea dos URLs a este mismo servlet
@WebServlet({"/productos.xls","/productos.html"})
public class ProductoXlsServlet extends HttpServlet {

<<<<<<< HEAD
    // Sobrescribimos get para atender ambas rutas
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Instanciamos el servicio que provee los productos
        ProductoService service = new ProductoServiceImplement();
        // Obtenemos la lista de productos a renderizar
        List<Producto> productos = service.listar();

        resp.setContentType("text/html;charset=UTF-8");
        // Leemos el servletPath para inferir si la petición fue .xls
        String servletPath=req.getServletPath();
        // determinamos si la salida será Excel
        boolean esXls=servletPath.endsWith(".xls");

        // Si es .xls cambiamos content-type y agregamos cabecera de descarga
        if (esXls){
            resp.setContentType("application/vnd.ms-excel");
            // Forzamos descarga con un nombre de archivo por defecto
            resp.setHeader("Content-Disposition", "attachment; filename=productos.xls");
        }
        try (PrintWriter out = resp.getWriter()) {
            // Si no es Excel generamos la estructura HTML
            if (!esXls) {
                // Estructura base de documento HTML
                out.print("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Listado dre Productos</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Listado de productos</h1>");
                out.println("<p><a href=\"" + req.getContextPath() + "/productos.xls" + "\">exportar a excel</a></p>");
                out.println("<p><a href=\"" + req.getContextPath() + "/productojson" + "\">mostrar json</a></p>");
            }

            // Iniciamos tabla para HTML y para el Excel
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>id</th>");
            out.println("<th>nombre</th>");
            out.println("<th>tipo</th>");
            out.println("<th>precio</th>");
            out.println("</tr>");

            // Recorremos y pintamos cada producto como una fila
            productos.forEach(p->{
                out.println("<tr>");
                out.println("<td>"+p.getId()+"</td>");
                out.println("<td>"+p.getNombre()+"</td>");
                out.println("<td>"+p.getTipo()+"</td>");
                out.println("<td>"+p.getPrecio()+"</td>");
                out.println("</tr>");
            });
            out.println("</table>");

            // Si no es Excel cerramos la estructura HTML
            if (!esXls) {
                out.println("</body>");
                out.println("</html>");
            }

        }
    }
}
=======
    // indica si existe cookie "username" ya logueado
    private boolean estaLogueado(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        return Arrays.stream(cookies).anyMatch(c -> "username".equals(c.getName()) && c.getValue() != null && !c.getValue().isBlank());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Servicio que provee productos
        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        // Detectamos si es HTML o Excel
        String servletPath = req.getServletPath();
        boolean esXls = servletPath.endsWith(".xls");

        // Verificamos login por cookie "username"
        boolean logueado = estaLogueado(req);

        // Configuramos content-type
        if (esXls) {
            resp.setContentType("application/vnd.ms-excel");
            resp.setHeader("Content-Disposition", "attachment; filename=productos.xls");
        } else {
            resp.setContentType("text/html;charset=UTF-8");
        }

        // Imprimimos la salida
        try (PrintWriter out = resp.getWriter()) {

            // Estructura HTML en este caso, si esq no es excel
            if (!esXls) {
                out.print("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='utf-8'>");
                out.println("<title>Listado de Productos</title>");
                // los estilos css
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/estilos.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='container'>");
                out.println("<h1>Listado de productos</h1>");
                out.println("<div class='nav-links'>");
                out.println("<a class='btn' href='" + req.getContextPath() + "/productos.xls'>Exportar a Excel</a>");
                out.println("<a class='btn-vino' href='" + req.getContextPath() + "/login.html'> Login </a>");
                out.println("<a class='btn' href='" + req.getContextPath() + "/logout'>Cerrar sesión</a>");
                out.println("</div>");

                // Mensaje de estado de login
                out.println("<div id='login-info'>");
                if (logueado) {
                    out.println("<span id='welcome-message'>Estás logueado, puedes ver precios. <a href='" + req.getContextPath() + "/logout'>Cerrar sesión</a></span>");
                } else {
                    out.println("<span id='welcome-message'>Estás como invitado. <a href='" + req.getContextPath() + "/login.html'>Inicia sesión</a> para ver precios.</span>");
                }
                out.println("</div>");
            }

            // Tabla común para HTML y Excel de precio condicional
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Id</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Tipo</th>");
            if (logueado) {
                out.println("<th class='th-precio'>Precio</th>");
            }
            out.println("</tr>");

            for (Producto p : productos) {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>");
                out.println("<td>" + p.getNombre() + "</td>");
                out.println("<td>" + p.getTipo() + "</td>");
                if (logueado) {
                    out.println("<td class='td-precio'>$ " + String.format("%.2f", p.getPrecio()) + "</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");

            // se cierra HTML
            if (!esXls) {
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}
>>>>>>> 8395a65 (uso de cookies)
