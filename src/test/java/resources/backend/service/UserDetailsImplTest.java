package resources.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import resources.backend.model.UserModel;
import resources.backend.repos.UserRepos;
import resources.backend.model.ERoleModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {
    @Mock
    private UserRepos userRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsImpl;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        // Initialize userModel
        userModel = new UserModel();
        userModel.setUsername("username");
        userModel.setEmail("email@example.com");
        userModel.setPassword("password");
        userModel.setRole(ERoleModel.ROLE_USER);
        
        // Initialize userDetailsImpl
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", ERoleModel.ROLE_USER, null, userRepository);
    }

    // Getters
    @Test
    void testGetAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        userDetailsImpl = new UserDetailsImpl(1L, "username", "email@example.com", "password", ERoleModel.ROLE_USER, authorities, userRepository);
        assertEquals(authorities, userDetailsImpl.getAuthorities());
    }

    @Test
    void testGetId() {
        assertEquals(1L, userDetailsImpl.getId());
    }

    @Test
    void testGetUsername() {
        assertEquals("username", userDetailsImpl.getUsername());
    }

    @Test
    void testGetRole() {
        ERoleModel actualRole = userDetailsImpl.getRole();
        assertEquals(ERoleModel.ROLE_USER, actualRole);
    }

    // Setters
    @Test
    void testSetRole() {
        userDetailsImpl.setRole(ERoleModel.ROLE_MOD);
        assertEquals(ERoleModel.ROLE_MOD, userDetailsImpl.getRole());
    }

    // Boolean verification methods
    @Test
    void testIsAccountNonExpired() {
        assertTrue(userDetailsImpl.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(userDetailsImpl.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(userDetailsImpl.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(userDetailsImpl.isEnabled());
    }

    @Test
    void testEquals() {
        UserDetailsImpl userDetailsImpl2 = new UserDetailsImpl(1L, "username", "email@example.com", "password", ERoleModel.ROLE_USER, new ArrayList<>(), userRepository);
        UserDetailsImpl userDetailsImpl3 = new UserDetailsImpl(2L, "username2", "email2@example.com", "password", ERoleModel.ROLE_USER, new ArrayList<>(), userRepository);

        assertTrue(userDetailsImpl.equals(userDetailsImpl2));
        assertFalse(userDetailsImpl.equals(userDetailsImpl3));
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
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);
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

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
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
