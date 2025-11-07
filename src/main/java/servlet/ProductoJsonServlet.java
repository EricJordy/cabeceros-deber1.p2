package servlet;

/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción:
 * Servlet que devuelve el listado de productos en formato JSON.
 * Atiende la ruta /productojson para ser consumido desde la vista HTML.
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

@WebServlet("/productojson")
public class ProductoJsonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtenemos los productos del servicio (en memoria)
        ProductoService service = new ProductoServiceImplement();
        List<Producto> productos = service.listar();

        // Indicamos JSON como tipo de respuesta
        resp.setContentType("application/json;charset=UTF-8");

        // Escribimos un JSON sencillo (sin librerías externas)
        try (PrintWriter out = resp.getWriter()) {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < productos.size(); i++) {
                Producto p = productos.get(i);
                sb.append("{")
                        .append("\"id\":").append(p.getId()).append(",")
                        .append("\"nombre\":\"").append(escape(p.getNombre())).append("\",")
                        .append("\"tipo\":\"").append(escape(p.getTipo())).append("\",")
                        .append("\"precio\":").append(p.getPrecio())
                        .append("}");
                if (i < productos.size() - 1) sb.append(",");
            }
            sb.append("]");
            out.print(sb.toString());
        }
    }

    // Pequeña ayuda para escapar comillas en cadenas JSON
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
