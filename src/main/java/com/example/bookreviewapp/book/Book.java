package com.example.bookreviewapp.book;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "books")
public class Book {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @NaturalId
    private String isbn;

    private String author;

    private String genre;

    private String thumbnailUrl;

    private String description;

    private Long pages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) &&
                Objects.equals(author, book.author) && Objects.equals(genre, book.genre) &&
                Objects.equals(thumbnailUrl, book.thumbnailUrl) && Objects.equals(description, book.description) &&
                Objects.equals(pages, book.pages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, author, genre, thumbnailUrl, description, pages);
    }
}
