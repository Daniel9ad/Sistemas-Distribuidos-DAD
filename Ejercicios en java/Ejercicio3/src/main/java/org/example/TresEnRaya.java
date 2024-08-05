package org.example;

import java.util.Scanner;

class TresEnRaya {
    private char[][] tablero;
    private char jugadorActual;

    public TresEnRaya() {
        tablero = new char[3][3];
        jugadorActual = 'X';
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    public void jugar() {
        Scanner scanner = new Scanner(System.in);
        boolean juegoTerminado = false;

        while (!juegoTerminado) {
            mostrarTablero();
            System.out.println("Turno del jugador " + jugadorActual);
            System.out.print("Ingrese fila (0-2): ");
            int fila = scanner.nextInt();
            System.out.print("Ingrese columna (0-2): ");
            int columna = scanner.nextInt();

            if (movimientoValido(fila, columna)) {
                tablero[fila][columna] = jugadorActual;
                if (hayGanador()) {
                    mostrarTablero();
                    System.out.println("¡El jugador " + jugadorActual + " ha ganado!");
                    juegoTerminado = true;
                } else if (tableroLleno()) {
                    mostrarTablero();
                    System.out.println("¡Empate!");
                    juegoTerminado = true;
                } else {
                    jugadorActual = (jugadorActual == 'X') ? 'O' : 'X';
                }
            } else {
                System.out.println("Movimiento inválido. Intente de nuevo.");
            }
        }
        scanner.close();
    }

    private void mostrarTablero() {
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + tablero[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------");
    }

    private boolean movimientoValido(int fila, int columna) {
        return fila >= 0 && fila < 3 && columna >= 0 && columna < 3 && tablero[fila][columna] == ' ';
    }

    private boolean hayGanador() {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugadorActual && tablero[i][1] == jugadorActual && tablero[i][2] == jugadorActual) {
                return true;
            }
            if (tablero[0][i] == jugadorActual && tablero[1][i] == jugadorActual && tablero[2][i] == jugadorActual) {
                return true;
            }
        }
        if (tablero[0][0] == jugadorActual && tablero[1][1] == jugadorActual && tablero[2][2] == jugadorActual) {
            return true;
        }
        if (tablero[0][2] == jugadorActual && tablero[1][1] == jugadorActual && tablero[2][0] == jugadorActual) {
            return true;
        }
        return false;
    }

    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}