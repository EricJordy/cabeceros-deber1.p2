package models;
/*
 * autor: Eric Campoverde
 * Fecha y versión: 06/11/2025  Versión: 1.0
 * Descripción: POJO que representa un Producto del catálogo.
 * Atributos: id, nombre, tipo, precio; incluye constructor vacío, completo y getters/setters
 */

public class Producto {

    // Identificador único del producto
    private Long id;
    // Nombre del producto
    private String nombre;
    // Tipo del producto
    private String tipo;
    // Precio del producto
    private double precio;

    // Constructor vacio o por defecto
    public Producto() {
    }

    // Constructor completo para inicializar todos los campos
    public Producto(Long id, String nombre, String tipo, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
    }

    // ------- Getter y Seters para los campos ----
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
