package com.datien.booknetwork.feedback.model;

import com.datien.booknetwork.base.BaseEntity;
import com.datien.booknetwork.book.model.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Feedback extends BaseEntity {

    private Double note; // 1- 5 stars
    private String comment;

    @ManyToOne
    @JoinColumn(
            name = "book_id"
    )
    private Book book;

}
