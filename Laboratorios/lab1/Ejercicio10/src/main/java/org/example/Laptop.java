package org.example;

public class Laptop extends Producto {
    private String procesador;
    private int ram;

    public Laptop(String nombre, double precio, int cantidadEnStock, String procesador, int ram) {
        super(nombre, precio, cantidadEnStock);
        this.procesador = procesador;
        this.ram = ram;
    }

    @Override
    public String getDetalles() {
        return String.format("%s, Procesador: %s, RAM: %d GB",
                super.getDetalles(), procesador, ram);
    }
}
