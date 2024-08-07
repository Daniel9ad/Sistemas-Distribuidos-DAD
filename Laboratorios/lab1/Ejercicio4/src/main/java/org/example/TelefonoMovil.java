package org.example;

public class TelefonoMovil extends ProductoElectronico {
    private String sistemaOperativo;

    public TelefonoMovil(String nombre, double precio, String sistemaOperativo) {
        super(nombre, precio);
        this.sistemaOperativo = sistemaOperativo;
    }

    @Override
    public void mostrarDetalles() {
        System.out.printf("Teléfono Móvil: %s, Precio: $%.2f, SO: %s\n", nombre, precio, sistemaOperativo);
    }
}
