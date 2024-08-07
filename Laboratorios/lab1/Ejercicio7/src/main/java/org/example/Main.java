package org.example;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    calcularIMC();
                    break;
                case 2:
                    System.out.println("Fin");
                    return;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Calculadora de IMC ---");
        System.out.println("1. Calcular IMC");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void calcularIMC() {
        System.out.print("Ingrese su peso en kilogramos: ");
        double peso = scanner.nextDouble();

        System.out.print("Ingrese su altura en metros: ");
        double altura = scanner.nextDouble();

        Persona persona = new Persona(peso, altura);
        double imc = persona.calcularIMC();

        System.out.printf("Su IMC es: %.2f\n", imc);
        System.out.println("Clasificación: " + CalculadoraIMC.clasificarIMC(imc));
    }
}