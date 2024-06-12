package resources.backend.controller.rest;
import jakarta.validation.Valid;
import resources.backend.model.FavoritesModel;
import resources.backend.service.FavoritesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/api/favorites", produces = MediaType.APPLICATION_JSON_VALUE)

public class FavoritesRestController {

    private final FavoritesService favoritesService ;

    @Autowired
    public FavoritesRestController(FavoritesService favoriteService) {
        this.favoritesService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<FavoritesModel>> getAllFavorite() {
        return ResponseEntity.ok(favoritesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoritesModel> getFavorite(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(favoritesService.get(id));
    }


    @PostMapping("/post")
    public ResponseEntity<FavoritesModel> createFavorite(@Valid @RequestBody FavoritesModel favoriteDTO) {
        Long favoriId = favoritesService.create(favoriteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritesService.get(favoriId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoritesModel> updateFavorite(@PathVariable(name = "id") final Long id,
        @Valid @RequestBody FavoritesModel favoriteDTO) {
        favoritesService.update(id, favoriteDTO);
        // Retrieve the updated favorite and return it
        return ResponseEntity.ok(favoritesService.get(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable(name = "id") final Long id) {
        favoritesService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
