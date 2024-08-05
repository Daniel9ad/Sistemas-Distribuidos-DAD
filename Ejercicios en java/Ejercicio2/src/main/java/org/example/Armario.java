package org.example;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

abstract class Armario {
    protected String codigo;
    protected List<Libro> libros;

    public Armario(String codigo) {
        this.codigo = codigo;
        this.libros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public String getCodigo(){
        return this.codigo;
    }

    public void setLibros(List<Libro> libros){
        this.libros = libros;
    }

    public List<Libro> getLibros(){
        return this.libros;
    }

    public abstract void guardarEnBD(Connection conn, int bibliotecaId) throws SQLException;
}
