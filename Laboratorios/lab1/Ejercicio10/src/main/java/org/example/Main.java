package org.example;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Inventario inventario = new Inventario();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();
            procesarOpcion(opcion);
        } while (opcion != 5);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Sistema de Inventario de Tienda ---");
        System.out.println("1. Agregar producto al inventario");
        System.out.println("2. Vender producto");
        System.out.println("3. Calcular valor total del inventario");
        System.out.println("4. Mostrar inventario");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarProducto();
                break;
            case 2:
                venderProducto();
                break;
            case 3:
                System.out.printf("Valor total del inventario: $%.2f\n", inventario.calcularValorTotal());
                break;
            case 4:
                inventario.mostrarInventario();
                break;
            case 5:
                System.out.println("Fin");
                return;
            default:
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
        }
    }

    private static void agregarProducto() {
        System.out.println("Productos:");
        System.out.println("1. Teléfono");
        System.out.println("2. Laptop");
        System.out.println("3. Otro producto");
        System.out.print("Seleccione el tipo de producto:");
        int tipoProducto = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();
        System.out.print("Ingrese la cantidad en stock: ");
        int cantidadEnStock = scanner.nextInt();
        scanner.nextLine();

        Producto nuevoProducto;

        switch (tipoProducto) {
            case 1:
                System.out.print("Ingrese la marca del teléfono: ");
                String marca = scanner.nextLine();
                System.out.print("Ingrese el sistema operativo: ");
                String sistemaOperativo = scanner.nextLine();
                nuevoProducto = new Telefono(nombre, precio, cantidadEnStock, marca, sistemaOperativo);
                break;
            case 2:
                System.out.print("Ingrese el procesador de la laptop: ");
                String procesador = scanner.nextLine();
                System.out.print("Ingrese la cantidad de RAM (en GB): ");
                int ram = scanner.nextInt();
                nuevoProducto = new Laptop(nombre, precio, cantidadEnStock, procesador, ram);
                break;
            default:
                nuevoProducto = new Producto(nombre, precio, cantidadEnStock);
                break;
        }

        inventario.agregarProducto(nuevoProducto);
        System.out.println("Producto agregado al inventario.");
    }

    private static void venderProducto() {
        System.out.print("Ingrese el nombre del producto a vender: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la cantidad a vender: ");
        int cantidad = scanner.nextInt();

        try {
            inventario.venderProducto(nombre, cantidad);
            System.out.println("Venta realizada con éxito.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}