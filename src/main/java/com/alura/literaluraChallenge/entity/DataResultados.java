package com.alura.literaluraChallenge.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataResultados(
        @JsonAlias("results") List<DataLibro> libros
) {
}