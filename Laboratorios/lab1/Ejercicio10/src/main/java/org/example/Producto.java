package org.example;

public class Producto {
    protected String nombre;
    protected double precio;
    protected int cantidadEnStock;

    public Producto(String nombre, double precio, int cantidadEnStock) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void vender(int cantidad) {
        if (cantidad <= cantidadEnStock) {
            cantidadEnStock -= cantidad;
        } else {
            throw new IllegalArgumentException("No hay suficiente stock para vender.");
        }
    }

    public void agregarStock(int cantidad) {
        cantidadEnStock += cantidad;
    }

    public String getDetalles() {
        return String.format("Nombre: %s, Precio: $%.2f, Cantidad en stock: %d",
                nombre, precio, cantidadEnStock);
    }
}