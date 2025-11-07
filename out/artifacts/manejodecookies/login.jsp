<%--
  autor: Eric Campoverde
  Fecha y versión: 10/11/2025  Versión: 1.1
  Descripción: Formulario de inicio de sesión. Si el login es correcto, se crea la cookie "username".
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Inicio de Sesión</title>
    <!-- Estilos minimal azul/vino para login -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estiloslogin.css">
</head>
<body>
<div class="login-container">
    <h1 class="login-title">Iniciar sesión</h1>
    <form class="login-form" action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="user">Usuario</label>
            <input type="text" id="user" name="user" placeholder="Usuario10" required>
        </div>

        <div class="form-group">
            <label for="password">Contraseña</label>
            <input type="password" id="password" name="password" placeholder="*****" required>
        </div>

        <button type="submit" class="submit-btn">Entrar</button>
    </form>
    <div class="helper-links">
        <a href="${pageContext.request.contextPath}/productos.html">Ver productos</a>
    </div>
</div>
</body>
</html>
