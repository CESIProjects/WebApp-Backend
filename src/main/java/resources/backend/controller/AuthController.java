package resources.backend.controller;

import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import resources.backend.config.jwt.JwtUtils;
import resources.backend.model.ERoleModel;
import resources.backend.model.UserModel;
import resources.backend.payload.request.LoginRequest;
import resources.backend.payload.request.PasswordUpdateRequest;
import resources.backend.payload.request.SignupRequest;
import resources.backend.payload.response.JwtResponse;
import resources.backend.payload.response.MessageResponse;
import resources.backend.repos.UserRepos;
import resources.backend.service.UserDetailsImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepos userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepos userRepository,
                          PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/updatePassword/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<MessageResponse> updatePassword(@PathVariable Long id, @Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        return userRepository.findById(id).map(user -> {
            if (!encoder.matches(passwordUpdateRequest.getOldPassword(), user.getPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Old password is incorrect!"));
            }
            
            // Update the password
            user.setPassword(encoder.encode(passwordUpdateRequest.getNewPassword()));
            userRepository.save(user);
            
            return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<?> getUserById(long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getUsernameById/{userId}")
    public ResponseEntity<?> getUsernameById(@PathVariable long userId) {
        return userRepository.findById(userId)
                .map(user -> ResponseEntity.ok(user.getUsername()))
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<MessageResponse> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: User does not exist!"));
        }
        
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Could not delete user!"));
        }
    }
    
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.joining());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                role));
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        boolean usernameTaken = userRepository.existsByUsername(signUpRequest.getUsername());
        boolean emailTaken = userRepository.existsByEmail(signUpRequest.getEmail());

        if (usernameTaken) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (emailTaken) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        ERoleModel role;
        try {
            role = ERoleModel.valueOf(signUpRequest.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid role specified!"));
        }

        try {
            UserModel user = new UserModel(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    role);

            userRepository.save(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(new MessageResponse("Error: User registration failed. Please try again."));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}