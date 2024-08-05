package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        int n = 0;

        do {
            System.out.println("\nMenú de opciones:");
            System.out.println("1. Introducir n");
            System.out.println("2. Calcular el Fibonacci");
            System.out.println("3. Calcular el factorial");
            System.out.println("4. Calcular la sumatoria");
            System.out.println("0. Salir");
            System.out.print("Elija una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Introduzca n: ");
                    n = scanner.nextInt();
                    break;
                case 2:
                    if (n==0){
                        System.out.print("Introduzca n para Fibonacci: ");
                        n = scanner.nextInt();
                    }
                    System.out.println("Fibonacci(" + n + ") = " + fibonacci(n));
                    break;
                case 3:
                    if (n == 0) {
                        System.out.print("Introduzca n para factorial: ");
                        n = scanner.nextInt();
                    }
                    System.out.println("Factorial(" + n + ") = " + factorial(n));
                    break;
                case 4:
                    if (n == 0 ){
                        System.out.print("Introduzca n para sumatoria: ");
                        n = scanner.nextInt();
                    }
                    System.out.println("Sumatoria(" + n + ") = " + sumatoria(n));
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    public static long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    public static long sumatoria(int n) {
        return (n * (n + 1)) / 2;
    }
}