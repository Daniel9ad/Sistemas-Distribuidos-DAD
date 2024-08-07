package org.example;

public class Rectangulo {
    private double largo;
    private double ancho;

    public Rectangulo() {
        this.largo = 0;
        this.ancho = 0;
    }

    public void setDimensiones(double largo, double ancho) {
        this.largo = largo;
        this.ancho = ancho;
    }

    public double calcularArea() {
        return largo * ancho;
    }

    public double calcularPerimetro() {
        return 2 * (largo + ancho);
    }
}
