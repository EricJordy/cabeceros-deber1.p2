package servlet;
/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción: Servlet que imprime en HTML los principales datos del HttpServletRequest:
 * método, URI, URL, context-path, servlet-path, IP/puerto del servidor,
 * esquema, host y armado de URLs, además de listar todas las cabeceras.
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

// Mapeo simple para cabeceros-deber1.p2-request
@WebServlet("/cabeceros-deber1.p2-request")
public class CabecerasHttpRequestServlet extends HttpServlet {

    // Implementación del método get que renderiza una página con datos del request
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        // Método HTTP con el que llega la petición
        String metodoHttp = req.getMethod();
        // Componentes de ruta solicitada
        String requestUri = req.getRequestURI();
        String requestUrl = req.getRequestURL().toString();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        // Datos de red del servidor que atiende
        String ip= req.getLocalAddr();
        int port = req.getLocalPort();
        String scheme = req.getScheme();
        String host = req.getHeader("host");
        // Construcción manual de dos URL`s
        String url=scheme+"://"+host+":"+contextPath+servletPath;
        String url2=scheme+"://"+ip+":"+port+contextPath+servletPath;
        // IP del cliente remoto que hizo la petición
        String ipCLiente=req.getRemoteAddr();

        try (PrintWriter out = resp.getWriter()) {
            // Estructura HTML estándar
            out.print("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<title>Cabeceras Http Request</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Cabeceras HTTP Request!</h1>");
            out.println("<ul>");
            out.println("<li>Obtenniendo el método"+ metodoHttp+"</li>");
            out.println("<li>Request uri: "+ requestUri +"</li>");
            out.println("<li>Request url: "+ requestUrl+"</li>");
            out.println("<li>Context Path: "+ contextPath+"</li>");
            out.println("<li>Servlet Path: "+ servletPath+ "</li>");
            out.println("<li>IP "+ ip +"</li>");
            out.println("<li>Port :"+port+"</li>");
            out.println("<li>Scheme :"+ scheme+"</li>");
            out.println("<li>Host :"+ host+"</li>");
            out.println("<li>URL :"+ url+"</li>");
            out.println("<li>URL2 :"+ url2+"</li>");
            out.println("<li>REMOTE CLIENTE :"+ ipCLiente+"</li>");

            // Iteración por todas las cabeceras recibidas
            Enumeration<String> headerNames=req.getHeaderNames();
            // Recorremos y mostramos "cabecera: valor" por cada entrada
            while(headerNames.hasMoreElements()){
                String cabecera = headerNames.nextElement();
                out.println("<li>" +cabecera + ": " +req.getHeader(cabecera)+"</li>");
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
