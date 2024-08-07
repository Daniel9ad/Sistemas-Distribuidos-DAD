package org.example;

public class Persona {
    private double peso;
    private double altura;

    public Persona(double peso, double altura) {
        this.peso = peso;
        this.altura = altura;
    }

    public double calcularIMC() {
        return peso / (altura * altura);
    }
}
