package com.datien.booknetwork.token;

import com.datien.booknetwork.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    private String token;
//    private boolean expired;
//    private boolean revoked;

//    @Enumerated(EnumType.STRING)
//    private TokenType tokenType = TokenType.BEARER;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(
            name = "user_id", nullable = false
    )
    private User user;
}
