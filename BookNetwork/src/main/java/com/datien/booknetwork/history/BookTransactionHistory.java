package com.datien.booknetwork.history;

import com.datien.booknetwork.base.BaseEntity;
import com.datien.booknetwork.book.model.Book;
import com.datien.booknetwork.user.User;
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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
