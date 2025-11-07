package service;
/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción: Implementación simple en memoria del servicio de productos.
 */

import models.Producto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Implementa el contrato de ProductoService
public class ProductoServiceImplement implements ProductoService{
    @Override
    public List<Producto> listar() {

        // Devolvemos una lista fija
        return Arrays.asList(new Producto(1L,"laptop", "Computaciòn",501.50),
                new Producto(2L,"Mouse","Computaciòn",11.99),
                new Producto(3L,"Cocineta", "Cocina",280.15));
    }
}
