package com.datien.booksocialnetwork.feedback;

import jakarta.validation.constraints.*;
import lombok.Data;


public record FeedbackRequest(
        @Positive(message = "200")
        @Max(value = 5, message = "201")
        @Min(value = 0, message = "202")
        Double note,
        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,
        @NotNull(message = "202")
        Integer bookId
) {
}
