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
            procesarOpcion(opcion);
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Tienda de Productos Electrónicos ---");
        System.out.println("1. Agregar producto al inventario");
        System.out.println("2. Mostrar inventario");
        System.out.println("3. Calcular precio total del inventario");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                agregarProducto();
                break;
            case 2:
                inventario.mostrarInventario();
                break;
            case 3:
                System.out.printf("Precio total del inventario: $%.2f\n", inventario.calcularPrecioTotal());
                break;
            case 4:
                System.out.println("Fin");
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private static void agregarProducto() {
        System.out.println("Productos");
        System.out.println("1. Teléfono Móvil");
        System.out.println("2. Laptop");
        System.out.print("Seleccione el tipo de producto:");
        int tipoProducto = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el precio del producto: ");
        double precio = scanner.nextDouble();
        scanner.nextLine();

        if (tipoProducto == 1) {
            System.out.print("Ingrese el sistema operativo: ");
            String sistemaOperativo = scanner.nextLine();
            inventario.agregarProducto(new TelefonoMovil(nombre, precio, sistemaOperativo));
        } else if (tipoProducto == 2) {
            System.out.print("Ingrese la cantidad de RAM (en GB): ");
            int ram = scanner.nextInt();
            inventario.agregarProducto(new Laptop(nombre, precio, ram));
        } else {
            System.out.println("Tipo de producto no válido.");
        }
    }
}