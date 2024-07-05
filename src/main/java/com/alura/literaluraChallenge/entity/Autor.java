package com.alura.literaluraChallenge.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int anioNacimiento;
    private int anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DataAutor dataAutor){
        this.nombre = dataAutor.nombre();
        this.anioNacimiento = dataAutor.anioNacimiento();
        this.anioFallecimiento = dataAutor.anioFallecimiento();
    }

    @Override
    public String toString() {
        return "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + anioNacimiento +
                "Fecha de fallecimiento: " + anioFallecimiento +
                "Libros: " + libros.stream().map(Libro::getTitulo).toList() + "\n";

    }

}
