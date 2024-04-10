// package resources.backend.repos;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;
// import resources.backend.model.ERoleModel;
// import resources.backend.model.RoleModel;

// @Repository
// public interface RoleRepos extends JpaRepository<RoleModel, Long> {
//   Optional<RoleModel> findByName(ERoleModel name);
// }