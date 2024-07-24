package com.datien.booksocialnetwork.feedback.service;

import com.datien.booksocialnetwork.book.model.Book;
import com.datien.booksocialnetwork.feedback.model.FeedbackRequest;
import com.datien.booksocialnetwork.feedback.model.FeedbackResponse;
import com.datien.booksocialnetwork.feedback.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest request) {
        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .build())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getId(), id))
                .build();
    }
}
