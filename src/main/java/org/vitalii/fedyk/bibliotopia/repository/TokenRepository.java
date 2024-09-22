package org.vitalii.fedyk.bibliotopia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vitalii.fedyk.bibliotopia.entity.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
            select t\s
            from Token as t\s
            where t.user.id = :userId and t.loggedOut = false\s
            """)
    List<Token> findAllValidTokensByUser(@Param("userId") long userId);

    Optional<Token> findByAccessToken(String accessToken);

    Optional<Token> findByRefreshToken(String refreshToken);
}
