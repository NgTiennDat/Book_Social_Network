package com.datien.booksocialnetwork.feedback;

import com.datien.booksocialnetwork.base.PageResponse;
import com.datien.booksocialnetwork.book.Book;
import com.datien.booksocialnetwork.book.BookRepository;
import com.datien.booksocialnetwork.exception.OperationNotPermittedException;
import com.datien.booksocialnetwork.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeedbackService {

    private final BookRepository bookRepository;
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public FeedbackService(BookRepository bookRepository, FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.bookRepository = bookRepository;
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    public Integer addFeedback(FeedbackRequest request, Authentication connectedUser) {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new OperationNotPermittedException("No book with ID: " + request.bookId()));
        if(book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("The requested book is either archived or not shareable");
        }
        User user = (User) connectedUser.getPrincipal();
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not add feedback to your own book.");
        }
        Feedback newFeedback = feedbackMapper.toFeedback(request);
        feedbackRepository.save(newFeedback);
        return request.bookId();
    }


    public PageResponse<FeedbackResponse> findAllFeedbackByBookId(
            Integer bookId,
            int page,
            int size,
            Authentication connectedUser
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        User user = (User) connectedUser.getPrincipal();
        Page<Feedback> allFeedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponses = allFeedbacks.stream()
                .map(feedback -> feedbackMapper.toFeedbackResponse(feedback, user.getId()))
                .toList();
        return new PageResponse<>(
                feedbackResponses,
                allFeedbacks.getNumber(),
                allFeedbacks.getSize(),
                allFeedbacks.getTotalElements(),
                allFeedbacks.getTotalPages(),
                allFeedbacks.isFirst(),
                allFeedbacks.isLast()
        );
    }
}
