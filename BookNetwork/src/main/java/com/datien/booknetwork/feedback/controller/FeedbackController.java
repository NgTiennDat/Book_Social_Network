package com.datien.booknetwork.feedback.controller;

import com.datien.booknetwork.base.PageResponse;
import com.datien.booknetwork.feedback.service.FeedbackService;
import com.datien.booknetwork.feedback.model.FeedbackRequest;
import com.datien.booknetwork.feedback.model.FeedbackResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedbacks")
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

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> allFeedBackByBook(
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.findAllFeedbackByBookId(bookId, page, size, connectedUser));
    }
}
