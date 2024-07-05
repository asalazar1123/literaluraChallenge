package com.alura.literaluraChallenge.principal;



import com.alura.literaluraChallenge.entity.Autor;
import com.alura.literaluraChallenge.entity.DataLibro;
import com.alura.literaluraChallenge.entity.DataResultados;
import com.alura.literaluraChallenge.entity.Libro;
import com.alura.literaluraChallenge.repo.autor.AuthorRepository;
import com.alura.literaluraChallenge.repo.libro.BookRepository;
import com.alura.literaluraChallenge.service.ConsumoAPI;
import com.alura.literaluraChallenge.service.ConvierteDatos;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);

    private final String URL_BASE = "http://gutendex.com/books/";
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Principal(BookRepository libroRepository, AuthorRepository autorRepository){
        this.bookRepository = libroRepository;
        this.authorRepository = autorRepository;
    }
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                   *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
                    Escriba el numero de la accion que desea realizar:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma                                 
                    0 - Salir
                    
                    *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivosAnioDeterminado();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("--Cerrando Sesión--");
                default -> System.out.println("Opción NO valida");
            }
        }
    }

    private DataLibro getDatosLibro() {

        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        //System.out.println(json);
        DataResultados datos = conversor.obtenerDatos(json, DataResultados.class);
        return datos.libros().get(0);

    }


    private void buscarLibro() {
        try{
            DataLibro datos = getDatosLibro();
            Libro libro = new Libro(datos);

            Autor autor = authorRepository.findByNombre(datos.autores().get(0).nombre());

            if(autor != null){
                libro.addAutor(autor);
                libro.setAutor(autor);
            }else {
                authorRepository.save(libro.getAutor());
            }

            bookRepository.save(libro);
            System.out.println(libro);
        }catch (IndexOutOfBoundsException e){
            System.out.println("******  libro no encontrado </3 ******");
        }catch (Exception e){
            System.out.println("no se puede registrar un libro mas de una vez :C ");

        }

    }

    private void listarLibros() {
        List<Libro> libros = bookRepository.findAll();
        libros.forEach(System.out::println);

    }

    private void listarAutores() {
        List<Autor> autores = authorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void autoresVivosAnioDeterminado() {
        System.out.println("Ingrese el año por el que desea buscar: ");
        var anio = teclado.nextInt();

        List<Autor> autoresVivos = authorRepository.buscarAutoresVivosPorAnio(anio);

        if(autoresVivos.isEmpty()){
            System.out.println("ningun autor vivo encontrado en ese año");
        }else {
            autoresVivos.forEach(System.out::println);
        }

    }

    private void listarLibrosPorIdioma() {
        String opciones = """
                Escriba el idioma en el que desea los libros:
                es - español
                en - ingles              
                """;
        System.out.println(opciones);
        var opcion = teclado.nextLine();
        if(opcion.equalsIgnoreCase("es") || opcion.equalsIgnoreCase("en")){
            List<Libro> librosIdioma = bookRepository.findByIdiomaIgnoreCase(opcion);
            if(librosIdioma.isEmpty()){
                System.out.println("no se encontraron libros en el idioma indicado");
            }else{
                librosIdioma.forEach(System.out::println);
            }
        }else{
            System.out.println("opcion no valida");
        }

    }


}
