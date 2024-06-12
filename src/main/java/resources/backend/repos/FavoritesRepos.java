package resources.backend.repos;

import org.springframework.data.jpa.repository.JpaRepository;


import resources.backend.entity.Favorites;

public interface FavoritesRepos extends JpaRepository<Favorites, Long>{
}
