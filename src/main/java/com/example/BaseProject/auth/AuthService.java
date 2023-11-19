package com.example.BaseProject.auth;

import com.example.BaseProject.Jwt.JwtService;
import com.example.BaseProject.dto.RegisterRequest;
import com.example.BaseProject.exceptions.SpringRedditException;
import com.example.BaseProject.model.NotificationEmail;
import com.example.BaseProject.service.MailContentBuilder;
import com.example.BaseProject.user.Role;
import com.example.BaseProject.user.User;
import com.example.BaseProject.model.VerificationToken;
import com.example.BaseProject.repositories.UserRepository;
import com.example.BaseProject.repositories.VerificationTokenRepository;
import com.example.BaseProject.service.MailService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final VerificationTokenRepository tokenRepository;
    private final MailService mailService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailContentBuilder mailContentBuilder;
    @Transactional
    public AuthResponse signup(RegisterRequest registerRequest) {
        if(repository.findByUsername(registerRequest.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .created(Instant.now())
                    .enabled(false)
                    .role(Role.USER)
                    .build();
            repository.save(user);
            String token = generateVerificationToken(user);
            String message = "Thanks you for signing up to Spring Reddit, " +
                    "please click on the below url to activate your account : " +
                    "http://localhost:3000/auth/accountVerification/" + token;
            mailService.sendMail(new NotificationEmail(user.getUsername() + " Please Activate your Account",
                    user.getEmail(), message
            ));
            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        } else throw new SpringRedditException("User " + registerRequest.getUsername() + " Already register in the system");
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        tokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
      Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
      verificationToken.orElseThrow(()->new SpringRedditException("Invalid Token"));
      fetchUserAndEnable(verificationToken.get());
    }
    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
       String username = verificationToken.getUser().getUsername();

      User user = repository.findByUsername(username).orElseThrow(()->new SpringRedditException("User not found with name "+ username));
       user.setEnabled(true);
       repository.save(user);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = repository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new SpringRedditException("User " + loginRequest.getUsername() + " could not be found"));
        if (user.isEnabled()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            String token = jwtService.getToken(user);
            return AuthResponse.builder()
                    .token(token)
                    .build();
        } else throw new SpringRedditException("User should activate email first");
    }
}
