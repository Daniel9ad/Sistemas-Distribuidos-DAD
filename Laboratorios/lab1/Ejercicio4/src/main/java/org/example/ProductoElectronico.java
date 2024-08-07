package org.example;

public abstract class ProductoElectronico {
    protected String nombre;
    protected double precio;

    public ProductoElectronico(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public abstract void mostrarDetalles();

    public double getPrecio() {
        return precio;
    }
}
