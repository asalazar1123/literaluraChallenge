package com.alura.literaluraChallenge.repo.autor;
import com.alura.literaluraChallenge.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface AuthorRepository extends JpaRepository<Autor, Long> {
    Autor findByNombre(String nombre);

    @Query("select a from Autor a where a.anioNacimiento <=:anio and a.anioFallecimiento >=:anio")
    List<Autor> buscarAutoresVivosPorAnio(int anio);
}
