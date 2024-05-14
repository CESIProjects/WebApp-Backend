package resources.backend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import resources.backend.config.jwt.JwtUtils;
import resources.backend.model.UserModel;
import resources.backend.payload.request.LoginRequest;
import resources.backend.payload.request.PasswordUpdateRequest;
import resources.backend.payload.request.SignupRequest;
import resources.backend.payload.response.JwtResponse;
import resources.backend.payload.response.MessageResponse;
import resources.backend.repos.UserRepos;
import resources.backend.service.UserDetailsImpl;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserRepos userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthController authController;

    @Test
    void testUpdatePassword() {
        PasswordUpdateRequest request = new PasswordUpdateRequest();
        request.setOldPassword("oldpassword");
        request.setNewPassword("newpassword");

        UserModel user = new UserModel();
        user.setPassword("encodedpassword");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(encoder.matches(request.getOldPassword(), user.getPassword())).thenReturn(true);

        ResponseEntity<?> response = authController.updatePassword(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password updated successfully!", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    void testDeleteUser() {
        when(userRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = authController.deleteUser(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully!", ((MessageResponse) response.getBody()).getMessage());
    }

    @Test
    void testAuthenticateUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
    
        // Stub the behavior of authenticationManager.authenticate()
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication); // Return a valid Authentication object
    
        // Stub the behavior of jwtUtils.generateJwtToken()
        when(jwtUtils.generateJwtToken(any())).thenReturn("jwtToken");
    
        // Stub the behavior of UserDetailsImpl
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getId()).thenReturn(1L);
        when(userDetails.getUsername()).thenReturn("username");
        when(userDetails.getEmail()).thenReturn("user@example.com");
        
        // Return an empty collection instead of null for getAuthorities()
        when(userDetails.getAuthorities()).thenReturn(Collections.emptyList());
    
        // Call the controller method
        ResponseEntity<?> response = authController.authenticateUser(loginRequest);
    
        // Perform assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("jwtToken", ((JwtResponse) response.getBody()).getAccessToken());
    }    

    @Test
    void testRegisterUser() {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("newuser");
        signUpRequest.setEmail("newuser@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setRole("ROLE_USER");

        ResponseEntity<?> response = authController.registerUser(signUpRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully!", ((MessageResponse) response.getBody()).getMessage());
    }
}
