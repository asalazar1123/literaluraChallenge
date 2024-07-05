package com.alura.literaluraChallenge.repo.libro;

import com.alura.literaluraChallenge.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository <Libro, Long>{
    List<Libro> findByIdiomaIgnoreCase(String idioma);

}