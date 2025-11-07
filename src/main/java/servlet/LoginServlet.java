package servlet;

/*
 * autor: Eric Campoverde
 * Fecha y versión: 10/11/2025  Versión: 1.1
 * Descripción: Control de inicio y cierre de sesión usando cookie "username"
 * para el /login.html si hay cookie se va a mostrar bienvenida, si no, reenvía a login.jsp
 * el doPOST /login valida y setea cookie, luego muestra mensaje de éxito
 */

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;

@WebServlet({"/login", "/login.html", "/logout"})
public class LoginServlet extends HttpServlet {

    // Credenciales
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "12345";

    // obtiene el valor de la cookie "username" si este existe
    private Optional<String> getUsernameCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies() != null ? req.getCookies() : new Cookie[0];
        return Arrays.stream(cookies)
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Detectar si se pidió /logout
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            // invalidar cookie "username"
            Cookie c = new Cookie("username", "");
            // expira de inmediato, tiempo 0 seg
            c.setMaxAge(0);
            c.setHttpOnly(true);
            c.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            resp.addCookie(c);
            // redirigir a login
            resp.sendRedirect(req.getContextPath() + "/login.html");
            return;
        }

        // Si hay cookie, mostramos una mini-bienvenida o se podría redirigir a productos
        Optional<String> cookieOptional = getUsernameCookie(req);

        if (cookieOptional.isPresent()) {
            // Usuario ya logueado
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Login - Bienvenido</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/estiloslogin.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='login-container'>");
                out.println("<h1 class='login-title'>Bienvenido, " + cookieOptional.get() + "</h1>");
                out.println("<div class='helper-links'>");
                out.println("<a href='" + req.getContextPath() + "/productos.html'>Ir a productos</a> · ");
                out.println("<a href='" + req.getContextPath() + "/logout'>Cerrar sesión</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            // No logueado, entonces mostrar formulario (JSP)
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Recibir credenciales del formulario
        String username = req.getParameter("user");
        String password = req.getParameter("password");

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            // Credenciales válidas
            Cookie cookie = new Cookie("username", username);
            cookie.setHttpOnly(true); // evita lectura vía JS
            cookie.setMaxAge(60 * 60 * 2); // 2 horas
            cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
            resp.addCookie(cookie);

            // Respuesta simple de éxito
            resp.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Login Correcto</title>");
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/estiloslogin.css'>");
                out.println("</head>");
                out.println("<body>");
                out.println("<div class='login-container'>");
                out.println("<h1 class='login-title'>¡Bienvenido, " + username + "!</h1>");
                out.println("<div class='helper-links'>");
                out.println("<a href='" + req.getContextPath() + "/productos.html'>Ir a productos</a> · ");
                out.println("<a href='" + req.getContextPath() + "/logout'>Cerrar sesión</a>");
                out.println("</div>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            // Credenciales inválidas
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos, no tiene acceso o revise las credenciales");
        }
    }
}
