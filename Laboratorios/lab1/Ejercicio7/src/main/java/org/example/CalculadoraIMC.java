package org.example;

public class CalculadoraIMC {
    public static String clasificarIMC(double imc) {
        if (imc < 18.5) {
            return "Bajo peso";
        } else if (imc < 25) {
            return "Peso normal";
        } else if (imc < 30) {
            return "Sobrepeso";
        } else if (imc < 35) {
            return "Obesidad grado I";
        } else if (imc < 40) {
            return "Obesidad grado II";
        } else {
            return "Obesidad grado III";
        }
    }
}
