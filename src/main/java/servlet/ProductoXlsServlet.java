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
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Producto;
import service.ProductoService;
import service.ProductoServiceImplement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Anotación que mapea dos URLs a este mismo servlet
@WebServlet({"/productos.xls","/productos.html"})
public class ProductoXlsServlet extends HttpServlet {

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
