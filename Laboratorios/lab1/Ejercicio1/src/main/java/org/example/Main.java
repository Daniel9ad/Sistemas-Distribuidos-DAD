package org.example;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Rectangulo rectangulo = new Rectangulo();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            procesarOpcion(opcion);
        } while (opcion != 4);
    }

    private static void mostrarMenu() {
        System.out.println("1. Ingresar dimensiones del rectángulo");
        System.out.println("2. Calcular área");
        System.out.println("3. Calcular perímetro");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1:
                ingresarDimensiones();
                break;
            case 2:
                calcularArea();
                break;
            case 3:
                calcularPerimetro();
                break;
            case 4:
                System.out.println("Fin");
                break;
            default:
                System.out.println("Opción no válida");
        }
    }

    private static void ingresarDimensiones() {
        System.out.print("Ingrese el largo del rectángulo: ");
        double largo = scanner.nextDouble();
        System.out.print("Ingrese el ancho del rectángulo: ");
        double ancho = scanner.nextDouble();
        rectangulo.setDimensiones(largo, ancho);
        System.out.println("Dimensiones ingresadas correctamente.");
    }

    private static void calcularArea() {
        System.out.printf("El área del rectángulo es: %.2f\n", rectangulo.calcularArea());
    }

    private static void calcularPerimetro() {
        System.out.printf("El perímetro del rectángulo es: %.2f\n", rectangulo.calcularPerimetro());
    }
}