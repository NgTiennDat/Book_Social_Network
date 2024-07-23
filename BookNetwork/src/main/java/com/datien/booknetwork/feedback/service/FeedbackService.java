package com.datien.booknetwork.feedback.service;

import com.datien.booknetwork.base.PageResponse;
import com.datien.booknetwork.book.model.Book;
import com.datien.booknetwork.book.repository.BookRepository;
import com.datien.booknetwork.exception.OperationNotPermittedException;
import com.datien.booknetwork.feedback.model.Feedback;
import com.datien.booknetwork.feedback.model.FeedbackRequest;
import com.datien.booknetwork.feedback.model.FeedbackResponse;
import com.datien.booknetwork.feedback.reposiroty.FeedbackRepository;
import com.datien.booknetwork.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Integer addFeedback(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new OperationNotPermittedException("No book with ID: " + request.bookId()));

        if(!book.isShareable() || book.isArchived()) {
            throw new OperationNotPermittedException("The book is either archived or not shareable.");
        }
        User user = (User) connectedUser.getPrincipal();
        if(Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not feedback to your book. It just about the reader!");
        }

        Feedback feedback = feedbackMapper.toFeedback(request);
        feedbackRepository.save(feedback);
        return feedback.getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbackByBookId(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = feedbacks.stream()
                .map(
                        feedback -> feedbackMapper.toFeedbackResponse(feedback, user.getId())
                )
                .toList();

        return new PageResponse<>(
                feedbackResponses,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
