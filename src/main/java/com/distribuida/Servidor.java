package com.distribuida;

import com.distribuida.db.Author;
import com.distribuida.db.Book;
import com.distribuida.service.AuthorService;
import com.distribuida.service.BookService;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Servidor {

    static SeContainer container;

    public static void main(String[] args) {
        container = SeContainerInitializer.newInstance().initialize();

        rutearBook();
        rutearAuthor();

    }

    public static void rutearAuthor() {
        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<AuthorService> obje = container.select(AuthorService.class);
        AuthorService servicioAuthor = obje.get();

        get("/authors", (req, res) -> {
                    List<Author> authors = servicioAuthor.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("authors", authors);
                    return engine.render(new ModelAndView(model, "Author"));
                }
        );

        get("/author/delete/:id", (req, res) -> {
                    Long id = Long.parseLong(req.params(":id"));
                    servicioAuthor.deleteAuthor(id);
                    res.redirect("/authors");
                    return null;
                }
        );

        get("/author/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    Author author = new Author();
                    model.put("author", author);
                    return engine.render(new ModelAndView(model, "CrearAuthor"));
                }
        );

        post("/author/add", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");

                    String[] datos = cadena[0].split("=");
                    author.setFirstName(datos[1]);

                    datos = cadena[1].split("=");
                    author.setLastName(datos[1]);
                    author.setId(null);
                    servicioAuthor.createAuthor(author);

                    res.redirect("/authors");
                    return null;
                }
        );

        get("/author/formularioModificar/:id", (req, res) -> {
                    Author author = new Author();
                    Long id = Long.parseLong(req.params("id"));
                    author = servicioAuthor.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("author", author);
                    return engine.render(new ModelAndView(model, "ActualizarAuthor"));
                }
        );


        post("/author/modificar", (req, res) -> {
                    Author author = new Author();

                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");
                    author.setId(Long.parseLong(datos[1]));

                    datos = cadena[1].split("=");
                    author.setFirstName(datos[1]);

                    datos = cadena[2].split("=");
                    author.setLastName(datos[1]);

                    servicioAuthor.updateAuthor(author.getId(), author);
                    res.redirect("/authors");
                    return null;
                }
        );


    }


    public static void rutearBook() {

        ThymeleafTemplateEngine engine = new ThymeleafTemplateEngine();
        Instance<BookService> obj = container.select(BookService.class);
        Instance<AuthorService> obj2 = container.select(AuthorService.class);
        BookService bookService = obj.get();
        AuthorService srvAuthor = obj2.get();


        get("/books", (req, res) -> {
                    List<Book> books = bookService.findAll();
                    Map<String, Object> model = new HashMap<>();
                    model.put("books", books);
                    return engine.render(new ModelAndView(model, "Book"));
                }
        );
//
        get("/book/borrar/:id", (req, res) -> {
                    int id = Integer.parseInt(req.params(":id"));
                    bookService.deleteBook(id);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/book/formularioInsertar", (req, res) -> {
                    Map<String, Object> model = new HashMap<>();
                    List<Author> authors = srvAuthor.findAll();
                    Book book = new Book();
                    model.put("book", book);
                    model.put("authors", authors);
                    return engine.render(new ModelAndView(model, "CrearBook"));
                }
        );


        post("/book/add", (req, res) -> {
                    Book book = new Book();
                    Long id;
                    double price;
                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");


                    String[] datos = cadena[0].split("=");

                    datos = cadena[0].split("=");
                    book.setIsbn(datos[1]);

                    datos = cadena[1].split("=");
                    book.setTitle(datos[1]);

                    datos = cadena[2].split("=");
                    price = Double.valueOf((datos[1]));
                    book.setPrice(price);

                    datos = cadena[3].split("=");
                    id = Long.parseLong(datos[1]);
                    Author author = new Author();
                    author.setId(id);
                    book.setAuthor(author);

                    bookService.createBook(book);
                    res.redirect("/books");
                    return null;
                }
        );

        get("/book/formularioModificar/:id", (req, res) -> {
                    Book book;
                    List<Author> authors = srvAuthor.findAll();
                    int id = Integer.parseInt(req.params(":id"));
                    book = bookService.findById(id);
                    Map<String, Object> model = new HashMap<>();
                    model.put("book", book);
                    model.put("authors", authors);
                    model.put("id_author_current", book.getAuthor().getId());
                    return engine.render(new ModelAndView(model, "ActualizarBook"));
                }
        );


        post("book/modificar", (req, res) -> {
                    Book book = new Book();

                    Long id;
                    double price;
                    String body = req.body();
                    System.out.println(body);
                    String[] cadena = body.split("&");

                    String[] datos = cadena[0].split("=");

                    datos = cadena[0].split("=");
                    book.setId(Integer.parseInt(datos[1]));

                    datos = cadena[1].split("=");
                    book.setIsbn(datos[1]);

                    datos = cadena[2].split("=");
                    book.setTitle(datos[1]);

                    datos = cadena[3].split("=");
                    price = Double.valueOf((datos[1]));
                    book.setPrice(price);

                    datos = cadena[4].split("=");
                    id = Long.parseLong(datos[1]);
                    Author author = new Author();
                    author.setId(id);
                    datos = cadena[5].split("=");
                    Long id_current_author = Long.parseLong(datos[1]);

                    if (id == 0) {
                        author.setId(id_current_author);
                    }

                    book.setAuthor(author);
                    bookService.updateBook(book.getId(), book);
                    res.redirect("/books");
                    return null;
                }
        );
    }

}
