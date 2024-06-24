package com.datien.booksocialnetwork.auth;

import com.datien.booksocialnetwork.email.EmailService;
import com.datien.booksocialnetwork.email.EmailTemplateName;
import com.datien.booksocialnetwork.role.RoleRepository;
import com.datien.booksocialnetwork.security.JwtFilter;
import com.datien.booksocialnetwork.security.JwtService;
import com.datien.booksocialnetwork.user.Token;
import com.datien.booksocialnetwork.user.TokenRepository;
import com.datien.booksocialnetwork.user.User;
import com.datien.booksocialnetwork.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationEmail;

    public void register(RegistrationRequest dto) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("User Role Not Found"));

        var user = User.builder()
                .firstname(dto.getFirstname())
                .lastname(dto.getLastname())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var refreshToken = generateRefreshToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getUsername(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationEmail,
                refreshToken,
                "Account activation"
        );
    }

    public String generateRefreshToken(User user) {
        String activeCode = geneActiveCode(6);

        var refreshToken = Token.builder()
                .token(activeCode)
                .createdDate(LocalDateTime.now())
                .expiredDate(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(refreshToken);
        return activeCode;
    }

    private String geneActiveCode(int length) {

        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for(int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }


    public AuthenticationResponse authenticate(
            AuthenticationRequest request
    ) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());

        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void login(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Token not found!"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiredDate())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("The token has been expired, a nwe active code has been sent to your email. Please, check it again to get the code!");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
