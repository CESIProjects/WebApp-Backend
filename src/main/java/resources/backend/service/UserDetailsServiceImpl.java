package resources.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import resources.backend.model.UserModel;
import resources.backend.repos.UserRepos;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepos userRepository;

  public UserDetailsServiceImpl(UserRepos userRepository) {
      this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user, userRepository);
  }
}