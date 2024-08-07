package org.example;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void venderProducto(String nombre, int cantidad) {
        for (Producto producto : productos) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                producto.vender(cantidad);
                return;
            }
        }
        throw new IllegalArgumentException("Producto no encontrado en el inventario.");
    }

    public double calcularValorTotal() {
        return productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidadEnStock())
                .sum();
    }

    public void mostrarInventario() {
        System.out.println("Inventario actual:");
        for (Producto producto : productos) {
            System.out.println(producto.getDetalles());
        }
    }
}