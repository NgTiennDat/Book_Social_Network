package com.datien.booksocialnetwork.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String token;

    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;
    private LocalDateTime validatedAt;

    @ManyToOne
    @JoinColumn(
            name = "userId", nullable = false
    )
    private User user;

}
