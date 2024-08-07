package org.example;

public class Laptop extends ProductoElectronico {
    private int ram;

    public Laptop(String nombre, double precio, int ram) {
        super(nombre, precio);
        this.ram = ram;
    }

    @Override
    public void mostrarDetalles() {
        System.out.printf("Laptop: %s, Precio: $%.2f, RAM: %dGB\n", nombre, precio, ram);
    }
}
