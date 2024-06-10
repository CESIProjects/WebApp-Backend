package resources.backend.controller.rest;
import jakarta.validation.Valid;
import resources.backend.model.FavoriteModel;
import resources.backend.service.FavoriteService;

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

public class FavoriteRestController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteRestController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping
    public ResponseEntity<List<FavoriteModel>> getAllFavorite() {
        return ResponseEntity.ok(favoriteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteModel> getFavorite(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(favoriteService.get(id));
    }


    @PostMapping
    public ResponseEntity<FavoriteModel> createFavorite(@Valid @RequestBody FavoriteModel favoriteDTO) {
        Long favoriteId = favoriteService.create(favoriteDTO);
        // Retrieve the newly created favorite and return it with status code 201
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.get(favoriteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FavoriteModel> updateFavorite(@PathVariable(name = "id") final Long id,
        @Valid @RequestBody FavoriteModel favoriteDTO) {
        favoriteService.update(id, favoriteDTO);
        // Retrieve the updated favorite and return it
        return ResponseEntity.ok(favoriteService.get(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable(name = "id") final Long id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }



}
