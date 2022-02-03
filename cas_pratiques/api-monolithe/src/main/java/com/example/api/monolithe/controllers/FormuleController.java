package com.example.api.monolithe.controllers;

import com.example.api.monolithe.assemblers.FormuleModelAssembler;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.exceptions.FormuleNonTrouveeException;
import com.example.api.monolithe.repositories.FormuleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class FormuleController {

  private final FormuleRepository repository;
  private final FormuleModelAssembler assembler;

  FormuleController(FormuleRepository repository, FormuleModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  @Operation(summary = ("Récupération d'une formule depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La formule a été trouvée",
        content = @Content(schema = @Schema(implementation = Formule.class))),
    @ApiResponse(
        responseCode = "404",
        description = "La formule n'a pas été trouvée",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "/formules/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  public EntityModel<Formule> recuperUneFormule(@PathVariable Long id) {
    Formule formule = repository.findById(id).orElseThrow(() -> new FormuleNonTrouveeException(id));

    return assembler.toModel(formule);
  }

  @Operation(summary = ("Récupération de toutes les formules"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste de toutes les formules trouvées",
        content = @Content(schema = @Schema(allOf = Formule.class))),
  })
  @GetMapping(value = "/formules", produces = MediaTypes.HAL_JSON_VALUE)
  public CollectionModel<EntityModel<Formule>> recupererToutesLesFormules() {
    List<EntityModel<Formule>> formules =
        repository.findAll().stream().map(assembler::toModel).toList();

    return CollectionModel.of(
        formules,
        linkTo(methodOn(FormuleController.class).recupererToutesLesFormules()).withSelfRel());
  }

  @Operation(summary = ("Création d'une formule"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "La formule a été créée",
        content = @Content(schema = @Schema(implementation = Formule.class))),
  })
  @PostMapping(value = "/formules", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<Formule>> creerUneFormule(@RequestBody Formule formule) {
    Formule formuleSaved = repository.save(formule);

    return ResponseEntity.created(
            linkTo(methodOn(FormuleController.class).recuperUneFormule(formuleSaved.getId()))
                .toUri())
        .body(assembler.toModel(formuleSaved));
  }

  @Operation(summary = ("Modification du prix d'une formule"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Le prix de la formule a été modifié",
        content = @Content(schema = @Schema(implementation = Formule.class))),
    @ApiResponse(
        responseCode = "404",
        description = "La formule n'a pas été trouvée",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @PutMapping(value = "/formules/{id}/prix", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<Formule>> modifierLePrix(
      @PathVariable Long id, @RequestBody int nouveauPrix) {
    Formule formule = repository.findById(id).orElseThrow(() -> new FormuleNonTrouveeException(id));

    formule.setPrixDeBase(nouveauPrix);
    return ResponseEntity.ok(assembler.toModel(repository.save(formule)));
  }
}
