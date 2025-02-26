package com.datien.booknetwork.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


//    @Query("""
//           SELECT token
//           FROM Token token
//           INNER JOIN User u On token.user.id = u.id
//           WHERE u.id = :userId
//           AND (token.expired = false OR token.revoked = false)
//           """)
//    List<Token> findAllValidTokenByUser(Integer userId);

    Optional<Token> findByToken(String token);
}
