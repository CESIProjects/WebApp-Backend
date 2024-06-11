package resources.backend.config.jwt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import resources.backend.service.UserDetailsServiceImpl;

class AuthTokenFilterTest {

    private AuthTokenFilter authTokenFilter;
    private JwtUtils jwtUtils;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        jwtUtils = mock(JwtUtils.class);
        userDetailsService = mock(UserDetailsServiceImpl.class);
        authTokenFilter = new AuthTokenFilter(jwtUtils, userDetailsService);
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "invalidJwtToken";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.validateJwtToken(jwtToken)).thenReturn(false);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_UsernameNotFound() throws ServletException, IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "validJwtToken";
        String username = "testUser";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.validateJwtToken(jwtToken)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenThrow(new UsernameNotFoundException("User not found"));

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        FilterChain filterChain = mock(FilterChain.class);

        String jwtToken = "validJwtToken";
        String username = "testUser";
        UserDetails userDetails = mock(UserDetails.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(jwtUtils.validateJwtToken(jwtToken)).thenReturn(true);
        when(jwtUtils.getUserNameFromJwtToken(jwtToken)).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        // Act
        authTokenFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtUtils).validateJwtToken(jwtToken);
        verify(jwtUtils).getUserNameFromJwtToken(jwtToken);
        verify(userDetailsService).loadUserByUsername(username);
        verify(filterChain).doFilter(request, response);
    }
}
