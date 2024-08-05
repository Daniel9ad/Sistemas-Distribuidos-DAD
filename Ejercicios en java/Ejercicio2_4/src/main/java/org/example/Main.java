package org.example;

import java.sql.*;
import java.util.Scanner;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Biblioteca biblioteca;
    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = Conexion.getConnection();
            biblioteca = new Biblioteca("Biblioteca Central", 500.0, conn);

            int opcion;
            do {
                mostrarMenu();
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        crearArmario();
                        break;
                    case 2:
                        crearLibro();
                        break;
                    case 0:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } while (opcion != 0);

        } finally {
            Conexion.closeConnection();
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de opciones ---");
        System.out.println("1. Crear armario y añadirlo a la biblioteca");
        System.out.println("2. Crear libro y añadirlo a un armario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void crearArmario() {
        System.out.print("Ingrese el código del armario: ");
        String codigo = scanner.nextLine();
        System.out.print("¿El armario es de madera o metálico? (m/t): ");
        String tipo = scanner.nextLine().toLowerCase();

        Armario nuevoArmario;
        if (tipo.equals("m")) {
            nuevoArmario = new ArmarioMadera(codigo);
        } else if (tipo.equals("t")) {
            nuevoArmario = new ArmarioMetalico(codigo);
        } else {
            System.out.println("Tipo de armario no válido. No se ha creado el armario.");
            return;
        }
        try{
            nuevoArmario.guardarEnBD(conn, 1);
        }catch (SQLException e){
            System.out.println("Error");
        }
        biblioteca.agregarArmario(nuevoArmario);
        System.out.println("Armario creado y añadido a la biblioteca con éxito.");
    }

    private static void crearLibro() {
        System.out.print("Ingrese el nombre del libro: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();
        System.out.print("Ingrese la editorial del libro: ");
        String editorial = scanner.nextLine();
        System.out.print("Ingrese el año de publicación del libro: ");
        int anio = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        Libro nuevoLibro = new Libro(nombre, autor, editorial, anio);
        System.out.println("Libro creado con éxito.");

        System.out.println("Añadir a un armario");
        List<Armario> armarios = biblioteca.getArmarios();
        for (Armario armario : armarios) {
            System.out.println(armario.getCodigo());
            List<Libro> libros = armario.getLibros();
            for (Libro libro: libros){
                System.out.println("Libro: "+libro.getNombre());
            }
        }
        System.out.print("Ingrese el código del armario: ");
        String codigo = scanner.nextLine();
        try {
            nuevoLibro.guardarEnBD(conn, codigo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Libro guardado con éxito.");
    }
}