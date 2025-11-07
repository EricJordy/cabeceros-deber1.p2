package service;
/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción: Interfaz del servicio de productos
 * Define el comportamiento esperado para listar todos los productos
 */

import models.Producto;
import java.util.List;

public interface ProductoService {

    // Método que devuelve todos los productos disponibles
    List<Producto> listar();
}
