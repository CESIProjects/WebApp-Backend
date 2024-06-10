package resources.backend.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import resources.backend.entity.Favorite;
import resources.backend.model.FavoriteModel;
import resources.backend.repos.FavoriteRepos;
import resources.backend.util.NotFoundException;

@Service
public class FavoriteService {

    private final FavoriteRepos favoriteRepository;

    public FavoriteService(final FavoriteRepos favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }


    public List<FavoriteModel> findAll() {
        final List<Favorite> favorites = favoriteRepository.findAll(Sort.by("id"));

        return favorites.stream()
                .map(favorite -> mapToDTO(favorite, new FavoriteModel()))
                .toList();
    }

    public FavoriteModel get(final Long id) {
        return favoriteRepository.findById(id)
                .map(favorite -> mapToDTO(favorite, new FavoriteModel()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final FavoriteModel favoriteDTO) {
        final Favorite favorite = new Favorite();
        mapToEntity(favoriteDTO, favorite);

        return favoriteRepository.save(favorite).getId();
    }


    public void update(final Long id, final FavoriteModel favoriteDTO) {
        final Favorite favorite = favoriteRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        mapToEntity(favoriteDTO, favorite);
        favoriteRepository.save(favorite);
    }

    public void delete(final Long id) {
        favoriteRepository.deleteById(id);
    }

    private FavoriteModel mapToDTO(final Favorite favorite, final FavoriteModel favoriteDTO) {
        favoriteDTO.setId(favorite.getId());
        favoriteDTO.setPostId(favorite.getPostId());
        favoriteDTO.setUserId(favorite.getUserId());
        return favoriteDTO;
    }

    private Favorite mapToEntity(final FavoriteModel favoriteDTO, final Favorite favorite) {
        favorite.setPostId(favoriteDTO.getPostId());
        favorite.setUserId(favoriteDTO.getUserId());
        return favorite;
    }
}
