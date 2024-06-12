package resources.backend.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import resources.backend.entity.Favorites;
import resources.backend.model.FavoritesModel;
import resources.backend.repos.FavoritesRepos;
import resources.backend.util.NotFoundException;

@Service
public class FavoritesService {

    private final FavoritesRepos favoritesRepository;

    public FavoritesService(final FavoritesRepos favoriteRepository) {
        this.favoritesRepository = favoriteRepository;
    }


    public List<FavoritesModel> findAll() {
        final List<Favorites> favorites = favoritesRepository.findAll(Sort.by("id"));

        return favorites.stream()
                .map(favorite -> mapToDTO(favorite, new FavoritesModel()))
                .toList();
    }

    public FavoritesModel get(final Long id) {
        return favoritesRepository.findById(id)
                .map(favorite -> mapToDTO(favorite, new FavoritesModel()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(FavoritesModel favoriteDTO) {
        Favorites favorite = new Favorites();
        mapToEntity(favoriteDTO, favorite);
        return favoritesRepository.save(favorite).getId();
    }


    public void update(final Long id, final FavoritesModel favoriteDTO) {
        final Favorites favorite = favoritesRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        mapToEntity(favoriteDTO, favorite);
        favoritesRepository.save(favorite);
    }

    public void delete(final Long id) {
        favoritesRepository.deleteById(id);
    }

    private FavoritesModel mapToDTO(final Favorites favorite, final FavoritesModel favoriteDTO) {
        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setPostId(favorite.getPostId());
        favoriteDTO.setUserId(favorite.getUserId());
        return favoriteDTO;
    }

    private Favorites mapToEntity(final FavoritesModel favoriteDTO, final Favorites favorite) {
        favorite.setPostId(favoriteDTO.getPostId());
        favorite.setUserId(favoriteDTO.getUserId());
        return favorite;
    }
}
