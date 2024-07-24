package com.datien.booksocialnetwork.feedback.controller;

import com.datien.booksocialnetwork.base.PageResponse;
import com.datien.booksocialnetwork.feedback.model.FeedbackRequest;
import com.datien.booksocialnetwork.feedback.model.FeedbackResponse;
import com.datien.booksocialnetwork.feedback.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback")
public class FeedbackController {

    private final FeedbackService service;
    @PostMapping
    public ResponseEntity<Integer> addFeedback(
            @RequestBody @Valid FeedbackRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.addFeedback(request, connectedUser));
    }
    @GetMapping("/feedback/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> allFeedbackByBookId(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllFeedbackByBookId(bookId, page, size, connectedUser));
    }

}
