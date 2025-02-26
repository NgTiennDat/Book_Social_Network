package com.datien.booknetwork.book.service;

import com.datien.booknetwork.book.model.Book;
import com.datien.booknetwork.book.model.BookRequest;
import com.datien.booknetwork.book.model.BookResponse;
import com.datien.booknetwork.book.model.BorrowedBookResponse;
import com.datien.booknetwork.file.FileUtils;
import com.datien.booknetwork.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .synopsis(request.synopsis())
                .archived(false)
                .shareable(request.shareable())
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .createdBy(1)
                .build();
    }

    public BookResponse toBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .synopsis(book.getSynopsis())
                .rate(book.getRate())
                .cover(FileUtils.readFromLocation(book.getBookCover()))
                .owner(book.getOwner().fullname())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory) {
        return BorrowedBookResponse.builder()
                .id(bookTransactionHistory.getId())
                .title(bookTransactionHistory.getBook().getTitle())
                .authorName(bookTransactionHistory.getBook().getAuthorName())
                .isbn(bookTransactionHistory.getBook().getIsbn())
                .rate(bookTransactionHistory.getBook().getRate())
                .returned(bookTransactionHistory.isReturned())
                .returnApproved(bookTransactionHistory.isReturnApproved())
                .build();
    }
}
