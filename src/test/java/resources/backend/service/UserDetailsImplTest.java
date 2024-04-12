package resources.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import resources.backend.model.UserModel;
import resources.backend.repos.UserRepos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDetailsImplTest {

    @Mock
    private UserRepos userRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsImpl;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize userModel
        userModel = new UserModel();
        userModel.setUsername("username");
        userModel.setEmail("email@example.com");
        userModel.setPassword("password");

        // Stub methods
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));
    }

    @Test
    void testGetAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", authorities, userRepository);
        assertEquals(authorities, userDetailsImpl.getAuthorities());
    }

    @Test
    void testGetId() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertEquals(1L, userDetailsImpl.getId());
    }

    @Test
    void testGetUsername() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertEquals("username", userDetailsImpl.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        assertTrue(userDetailsImpl.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl userDetailsImpl1 = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        UserDetailsImpl userDetailsImpl2 = new UserDetailsImpl(1L, "username", "email@example.com", "password", new ArrayList<>(), userRepository);
        UserDetailsImpl userDetailsImpl3 = new UserDetailsImpl(2L, "username2", "email2@example.com", "password", new ArrayList<>(), userRepository);

        assertTrue(userDetailsImpl1.equals(userDetailsImpl2));
        assertFalse(userDetailsImpl1.equals(userDetailsImpl3));
    }

    @Test
    void testFindAll() {
        List<UserModel> userList = new ArrayList<>();
        userList.add(new UserModel("user1", "user1@example.com", "password1", null));
        userList.add(new UserModel("user2", "user2@example.com", "password2", null));

        // Stub the userRepository to return the list of UserModel objects
        when(userRepository.findAll()).thenReturn(userList);

        List<UserModel> result = userDetailsImpl.findAll();

        // Assert that the size of the result matches the size of userList
        assertEquals(userList.size(), result.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getUsername(), result.get(i).getUsername());
            assertEquals(userList.get(i).getEmail(), result.get(i).getEmail());
        }
    }

    @Test
    void testGet() {
        UserModel userModel = new UserModel("user1", "user1@example.com", "password1", null);

        // Stub the userRepository to return an Optional containing the UserModel
        when(userRepository.findById(1L)).thenReturn(Optional.of(userModel));

        UserModel result = userDetailsImpl.get(1L);

        assertEquals(userModel.getUsername(), result.getUsername());
        assertEquals(userModel.getEmail(), result.getEmail());
    }

    @Test
    void testCreateUser() {
        Long id = userDetailsImpl.createUser(userModel);
        assertEquals(userModel.getId(), id);
    }

    @Test
    void testUpdateUser() {
        UserModel existingUser = new UserModel();
        existingUser.setId(1L);
        existingUser.setUsername("username");
        existingUser.setEmail("email@example.com");
        existingUser.setPassword("password");

        userDetailsImpl.updateUser(1L, userModel);

        assertEquals(userModel.getUsername(), existingUser.getUsername());
        assertEquals(userModel.getEmail(), existingUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // Test delete user
        userDetailsImpl.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
