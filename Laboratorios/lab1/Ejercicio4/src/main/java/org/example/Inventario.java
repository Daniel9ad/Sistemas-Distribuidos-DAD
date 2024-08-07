package org.example;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<ProductoElectronico> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(ProductoElectronico producto) {
        productos.add(producto);
        System.out.println("Producto agregado al inventario.");
    }

    public void mostrarInventario() {
        if (productos.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            System.out.println("Inventario actual:");
            for (ProductoElectronico producto : productos) {
                producto.mostrarDetalles();
            }
        }
    }

    public double calcularPrecioTotal() {
        return productos.stream().mapToDouble(ProductoElectronico::getPrecio).sum();
    }
}
