package resources.backend.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import resources.backend.model.UserModel;

@Repository
public interface UserRepos extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}