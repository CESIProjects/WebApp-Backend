package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import resources.backend.entity.Favorite;
public interface FavoriteRepos extends JpaRepository<Favorite, Long>{
}
