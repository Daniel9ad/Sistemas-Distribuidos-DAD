package org.example;

public class Telefono extends Producto {
    private String marca;
    private String sistemaOperativo;

    public Telefono(String nombre, double precio, int cantidadEnStock, String marca, String sistemaOperativo) {
        super(nombre, precio, cantidadEnStock);
        this.marca = marca;
        this.sistemaOperativo = sistemaOperativo;
    }

    @Override
    public String getDetalles() {
        return String.format("%s, Marca: %s, Sistema Operativo: %s",
                super.getDetalles(), marca, sistemaOperativo);
    }
}
