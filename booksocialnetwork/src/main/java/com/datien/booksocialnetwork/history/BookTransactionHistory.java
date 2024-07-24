package com.datien.booksocialnetwork.history;


import com.datien.booksocialnetwork.base.BaseEntity;
import com.datien.booksocialnetwork.book.model.Book;
import com.datien.booksocialnetwork.user.User;
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
public class BookTransactionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "book_id"
    )
    private Book book;

    private boolean returned;
    private boolean returnApproved;
}
