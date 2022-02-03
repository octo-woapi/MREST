package com.example.api.monolithe.controllers;

import com.example.api.monolithe.assemblers.AbonnementModelAssembler;
import com.example.api.monolithe.assemblers.SouscriptionAbonnementModelAssembler;
import com.example.api.monolithe.entities.Abonnement;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.entities.SouscriptionAbonnement;
import com.example.api.monolithe.exceptions.AbonnementNonTrouveException;
import com.example.api.monolithe.exceptions.SouscriptionAbonnementNonTrouveeException;
import com.example.api.monolithe.repositories.AbonnementRepository;
import com.example.api.monolithe.repositories.FormuleRepository;
import com.example.api.monolithe.repositories.Mailer;
import com.example.api.monolithe.repositories.SouscriptionAbonnementRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AbonnementController {

  private final AbonnementRepository abonnementRepository;
  private final FormuleRepository formuleRepository;
  private final SouscriptionAbonnementRepository souscriptionAbonnementRepository;

  private final AbonnementModelAssembler assembler;
  private final SouscriptionAbonnementModelAssembler souscriptionAbonnementAssembler;

  private final Mailer mailer;

  AbonnementController(
      AbonnementRepository abonnementRepository,
      FormuleRepository formuleRepository,
      SouscriptionAbonnementRepository souscriptionAbonnementRepository,
      AbonnementModelAssembler assembler,
      SouscriptionAbonnementModelAssembler souscriptionAbonnementAssembler,
      Mailer mailer) {
    this.abonnementRepository = abonnementRepository;
    this.formuleRepository = formuleRepository;
    this.souscriptionAbonnementRepository = souscriptionAbonnementRepository;
    this.assembler = assembler;
    this.souscriptionAbonnementAssembler = souscriptionAbonnementAssembler;
    this.mailer = mailer;
  }

  @Operation(summary = ("Récupération d'un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "L'abonnement a été trouvé",
        content = @Content(schema = @Schema(implementation = Abonnement.class))),
    @ApiResponse(
        responseCode = "404",
        description = "L'abonnement n'a pas été trouvé",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "/abonnements/{id}", produces = MediaTypes.HAL_JSON_VALUE)
  public EntityModel<Abonnement> recupererUnAbonnement(@PathVariable Long id) {
    Abonnement abonnement =
        abonnementRepository.findById(id).orElseThrow(() -> new AbonnementNonTrouveException(id));

    return assembler.toModel(abonnement);
  }

  @Operation(summary = ("Récupération de tous les abonnements"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des abonnements trouvés",
        content = @Content(schema = @Schema(allOf = Abonnement.class))),
  })
  @GetMapping(value = "/abonnements", produces = MediaTypes.HAL_JSON_VALUE)
  public CollectionModel<EntityModel<Abonnement>> recupererTousLesAbonnements() {
    List<EntityModel<Abonnement>> abonnements =
        abonnementRepository.findAll().stream().map(assembler::toModel).toList();

    return CollectionModel.of(
        abonnements,
        linkTo(methodOn(AbonnementController.class).recupererTousLesAbonnements()).withSelfRel());
  }

  @Operation(summary = ("Création d'un abonnement à partir d'une formule existante"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "L'abonnement a été créé",
        content = @Content(schema = @Schema(implementation = Abonnement.class))),
    @ApiResponse(responseCode = "404", description = "La formule n'a pas été trouvée")
  })
  @PostMapping(value = "/abonnements", produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<Abonnement>> creerUnAbonnement(
      @RequestBody Abonnement abonnement) {
    Formule formule =
        formuleRepository
            .findById(abonnement.getFormuleChoisie().getId())
            .orElseThrow(
                () -> new AbonnementNonTrouveException(abonnement.getFormuleChoisie().getId()));

    abonnement.setFormuleChoisie(formule);
    Abonnement abonnementSaved = abonnementRepository.save(abonnement);
    return ResponseEntity.created(
            linkTo(
                    methodOn(AbonnementController.class)
                        .recupererUnAbonnement(abonnementSaved.getId()))
                .toUri())
        .body(assembler.toModel(abonnementSaved));
  }

  @Operation(
      summary = ("Renouvellement des abonnements concernés à partir d'une date de fin de période"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des abonnements qui ont été renouvellés",
        content = @Content(schema = @Schema(allOf = Abonnement.class))),
  })
  @PutMapping(value = "/abonnements/renouvellement", produces = MediaTypes.HAL_JSON_VALUE)
  public CollectionModel<EntityModel<Abonnement>> renouveller(
      @Parameter(description = "La date de fin de période d'abonnement", example = "2022-06-01", required = true)
          @RequestBody
          String dateDeRenouvellement) {
    List<Abonnement> abonnementsFinisAPartirDe =
        abonnementRepository.findAll().stream()
            .filter(abonnement -> abonnement.seraFiniALaDateDonnee(dateDeRenouvellement))
            .map(Abonnement::renouveller)
            .toList();

    List<EntityModel<Abonnement>> abonnementsUpdated =
        abonnementRepository.saveAll(abonnementsFinisAPartirDe).stream()
            .map(assembler::toModel)
            .toList();

    return CollectionModel.of(
        abonnementsUpdated,
        linkTo(methodOn(AbonnementController.class).recupererTousLesAbonnements()).withSelfRel());
  }

  @Operation(summary = ("Envoie d'un e-mail de souscription à un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "La souscription à un abonnement qui a été envoyée",
        content = @Content(schema = @Schema(implementation = SouscriptionAbonnement.class))),
    @ApiResponse(responseCode = "404", description = "L'abonnement n'a pas été trouvé")
  })
  @PostMapping(
      value = "/abonnements/{id}/email-de-souscription",
      produces = MediaTypes.HAL_JSON_VALUE)
  public ResponseEntity<EntityModel<SouscriptionAbonnement>> envoyerEmailDeSouscription(
      @PathVariable Long id, @RequestBody String email) {
    String emailAUtiliser = email.replaceAll("\"", "");

    Abonnement abonnement =
        abonnementRepository.findById(id).orElseThrow(() -> new AbonnementNonTrouveException(id));

    mailer.envoyerEmail(
        emailAUtiliser,
        String.format(
            "Bienvenu(e) chez CraftGym, profite bien de ton abonnement %s.",
            abonnement.getFormuleChoisie().getDescription()));

    SouscriptionAbonnement souscriptionAbonnementSaved =
        souscriptionAbonnementRepository.save(
            new SouscriptionAbonnement(emailAUtiliser, abonnement));
    return ResponseEntity.created(
            linkTo(
                    methodOn(AbonnementController.class)
                        .recuperEmailDeSouscriptions(abonnement.getId()))
                .toUri())
        .body(souscriptionAbonnementAssembler.toModel(souscriptionAbonnementSaved));
  }

  @Operation(summary = ("Récupération de toutes les souscriptions à un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des souscriptions à un abonnement qui ont été sauvegardées",
        content = @Content(schema = @Schema(allOf = SouscriptionAbonnement.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Les souscriptions à cet abonnement n'ont pas été trouvées")
  })
  @GetMapping(
      value = "/abonnements/{id}/email-de-souscription",
      produces = MediaTypes.HAL_JSON_VALUE)
  public CollectionModel<EntityModel<SouscriptionAbonnement>> recuperEmailDeSouscriptions(
      @PathVariable Long id) {
    List<SouscriptionAbonnement> souscriptionAbonnements =
        souscriptionAbonnementRepository
            .findAllByAbonnementId(id)
            .orElseThrow(() -> new SouscriptionAbonnementNonTrouveeException(id));

    List<EntityModel<SouscriptionAbonnement>> entityModels =
        souscriptionAbonnements.stream().map(souscriptionAbonnementAssembler::toModel).toList();

    return CollectionModel.of(
        entityModels,
        linkTo(methodOn(AbonnementController.class).recuperEmailDeSouscriptions(id)).withSelfRel());
  }

  @Operation(summary = ("Récupération du chiffres d'affaire à partir d'une date de fin de période"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Le chiffre d'affaire calculé",
        content =
            @Content(
                schema = @Schema(implementation = String.class),
                examples = @ExampleObject(value = "320.0")))
  })
  @GetMapping(value = "/abonnements/chiffre-d-affaire", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Double> recuperChiffreDAffaire(@RequestParam String dateDeFinDePeriode) {
    List<Abonnement> abonnements = abonnementRepository.findAll();
    double totalSalesToDate =
        abonnementRepository.findAll().stream()
            .filter(abonnement -> abonnement.estEnCours(dateDeFinDePeriode))
            .mapToDouble(abonnement -> Double.parseDouble(abonnement.getPrix()))
            .sum();
    return ResponseEntity.ok(totalSalesToDate);
  }
}
