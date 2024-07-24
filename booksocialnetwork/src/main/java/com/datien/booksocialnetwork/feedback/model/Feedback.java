package com.datien.booksocialnetwork.feedback.model;


import com.datien.booksocialnetwork.base.BaseEntity;
import com.datien.booksocialnetwork.book.model.Book;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback extends BaseEntity {

    @Column
    private Double note; // from 1 to 5 stars
    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "book_id"
    )
    private Book book;

}
