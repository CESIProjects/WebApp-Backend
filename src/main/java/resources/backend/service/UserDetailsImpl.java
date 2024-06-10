package resources.backend.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import resources.backend.entity.User;
import resources.backend.model.UserModel;
import resources.backend.model.ERoleModel;
import resources.backend.repos.UserRepos;
import resources.backend.util.NotFoundException;

import java.util.List;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

  private Long id;
  private String username;
  private String email;
  private ERoleModel role;

  @JsonIgnore
  private String password;

  private  transient UserRepos userRepository;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String username, String email, String password, ERoleModel role,
          Collection<? extends GrantedAuthority> authorities, final UserRepos userRepository) {
      this.id = id;
      this.username = username;
      this.email = email;
      this.password = password;
      this.role = role;
      this.authorities = authorities;
      this.userRepository = userRepository;
  }

  public static UserDetailsImpl build(UserModel user, UserRepos userRepository) {
      GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());
      return new UserDetailsImpl(
              user.getId(),
              user.getUsername(),
              user.getEmail(),
              user.getPassword(),
              user.getRole(),
              Collections.singletonList(authority),
              userRepository);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public List<UserModel> findAll() {
    return userRepository.findAll()
        .stream()
        .map(this::mapToDTO)
        .toList();
  }

  public UserModel get(final Long id) {
    UserModel user = userRepository.findById(id)
        .orElseThrow(NotFoundException::new);
    return mapToDTO(user);
  }

  public Long createUser(UserModel userDTO) {
    User user = mapToEntity(userDTO);
    UserModel userModel = mapToDTO(user);
    UserModel savedUser = userRepository.save(userModel);
    return savedUser.getId();
  }

  public void updateUser(final Long id, final UserModel userDTO) {
    UserModel user = userRepository.findById(id)
            .orElseThrow(NotFoundException::new);
    mapToEntity(userDTO, user);
    userRepository.save(user);
  }


  public void deleteUser(final Long id) {
      userRepository.deleteById(id);
  }

  private UserModel mapToDTO(final User user) {
      UserModel userDTO = new UserModel();
      userDTO.setId(user.getId());
      userDTO.setUsername(user.getUsername());
      userDTO.setEmail(user.getEmail());
      return userDTO;
  }

  private UserModel mapToDTO(final UserModel userModel) {
    UserModel userDTO = new UserModel();
    userDTO.setId(userModel.getId());
    userDTO.setUsername(userModel.getUsername());
    userDTO.setEmail(userModel.getEmail());
    return userDTO;
  }

  private User mapToEntity(final UserModel userDTO) {
      User user = new User(username, email, password, email);
      user.setUsername(userDTO.getUsername());
      user.setEmail(userDTO.getEmail());
      user.setPassword(userDTO.getPassword());
      return user;
  }

  private void mapToEntity(final UserModel userDTO, final UserModel user) {
    user.setUsername(userDTO.getUsername());
    user.setEmail(userDTO.getEmail());
  }

  // Getters
  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public ERoleModel getRole() {
    return role;
  }
  
  // Setters
  public void setRole(ERoleModel role) {
    this.role = role;
  }

  // Boolean verification methods
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }

  @Override
  public int hashCode() {
      return Objects.hash(id);
  }
}