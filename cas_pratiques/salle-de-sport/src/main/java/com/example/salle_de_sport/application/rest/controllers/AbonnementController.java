package com.example.salle_de_sport.application.rest.controllers;

import com.example.salle_de_sport.application.rest.mappers.AbonnementApiMapper;
import com.example.salle_de_sport.application.rest.mappers.SouscriptionAbonnementApiMapper;
import com.example.salle_de_sport.application.rest.models.AbonnementApi;
import com.example.salle_de_sport.application.rest.models.SouscriptionAbonnementApi;
import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.usecases.CreerUnAbonnement;
import com.example.salle_de_sport.domain.usecases.EnvoyerUnEmailDeSouscription;
import com.example.salle_de_sport.domain.usecases.RecuperDesSouscriptionAbonnements;
import com.example.salle_de_sport.domain.usecases.RecuperLeChiffreDAffaireDeLaFinDePeriode;
import com.example.salle_de_sport.domain.usecases.RecupererTousLesAbonnements;
import com.example.salle_de_sport.domain.usecases.RecupererUnAbonnement;
import com.example.salle_de_sport.domain.usecases.RenouvelerDesAbonnements;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class AbonnementController {

  private final AbonnementApiMapper abonnementApiMapper;
  private final SouscriptionAbonnementApiMapper souscriptionAbonnementApiMapper;

  private final RecupererUnAbonnement recupererUnAbonnement;
  private final RecupererTousLesAbonnements recupererTousLesAbonnements;
  private final CreerUnAbonnement creerUnAbonnement;
  private final RenouvelerDesAbonnements renouvelerDesAbonnements;
  private final EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription;
  private final RecuperDesSouscriptionAbonnements recuperDesSouscriptionAbonnements;
  private final RecuperLeChiffreDAffaireDeLaFinDePeriode recuperLeChiffreDAffaireDeLaFinDePeriode;

  AbonnementController(
      AbonnementApiMapper abonnementApiMapper,
      SouscriptionAbonnementApiMapper souscriptionAbonnementApiMapper,
      RecupererUnAbonnement recupererUnAbonnement,
      RecupererTousLesAbonnements recupererTousLesAbonnements,
      CreerUnAbonnement creerUnAbonnement,
      RenouvelerDesAbonnements renouvelerDesAbonnements,
      EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription,
      RecuperDesSouscriptionAbonnements recuperDesSouscriptionAbonnements,
      RecuperLeChiffreDAffaireDeLaFinDePeriode recuperLeChiffreDAffaireDeLaFinDePeriode) {

    this.abonnementApiMapper = abonnementApiMapper;
    this.souscriptionAbonnementApiMapper = souscriptionAbonnementApiMapper;
    this.recupererUnAbonnement = recupererUnAbonnement;
    this.recupererTousLesAbonnements = recupererTousLesAbonnements;
    this.creerUnAbonnement = creerUnAbonnement;
    this.renouvelerDesAbonnements = renouvelerDesAbonnements;
    this.envoyerUnEmailDeSouscription = envoyerUnEmailDeSouscription;
    this.recuperDesSouscriptionAbonnements = recuperDesSouscriptionAbonnements;
    this.recuperLeChiffreDAffaireDeLaFinDePeriode = recuperLeChiffreDAffaireDeLaFinDePeriode;
  }

  @Operation(summary = ("Récupération d'un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "L'abonnement a été trouvé",
        content = @Content(schema = @Schema(implementation = AbonnementApi.class))),
    @ApiResponse(responseCode = "404", description = "L'abonnement n'a pas été trouvé")
  })
  @GetMapping(value = "/abonnements/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AbonnementApi> recupererUnAbonnement(@PathVariable Long id) {
    return ResponseEntity.status(OK)
        .body(abonnementApiMapper.convertirEnAbonnementApi(recupererUnAbonnement.executer(id)));
  }

  @Operation(summary = ("Récupération de tous les abonnements"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des abonnements trouvés",
        content = @Content(schema = @Schema(allOf = AbonnementApi.class))),
  })
  @GetMapping(value = "/abonnements", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AbonnementApi>> recupererTousLesAbonnements() {
    return ResponseEntity.status(OK)
        .body(
            recupererTousLesAbonnements.executer().stream()
                .map(abonnementApiMapper::convertirEnAbonnementApi)
                .toList());
  }

  @Operation(summary = ("Création d'un abonnement à partir d'une formule existante"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "L'abonnement a été créé",
        content = @Content(schema = @Schema(implementation = AbonnementApi.class))),
    @ApiResponse(responseCode = "404", description = "La formule n'a pas été trouvée")
  })
  @PostMapping(value = "/abonnements", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<AbonnementApi> creerUnAbonnement(@RequestBody AbonnementApi abonnementApi) {
    Abonnement abonnement = abonnementApiMapper.convertirEnAbonnement(abonnementApi);
    return ResponseEntity.status(CREATED)
        .body(abonnementApiMapper.convertirEnAbonnementApi(creerUnAbonnement.executer(abonnement)));
  }

  @Operation(
      summary = ("Renouvellement des abonnements concernés à partir d'une date de fin de période"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des abonnements qui ont été renouvelés",
        content = @Content(schema = @Schema(allOf = AbonnementApi.class))),
  })
  @PutMapping(value = "/abonnements/renouvellement", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AbonnementApi>> renouvelerDesAbonnements(
      @Parameter(
              description = "La date de fin de période d'abonnement",
              example = "2022-06-01",
              required = true)
          @RequestBody
          String dateDeRenouvellement) {
    return ResponseEntity.status(OK)
        .body(
            renouvelerDesAbonnements.executer(dateDeRenouvellement).stream()
                .map(abonnementApiMapper::convertirEnAbonnementApi)
                .toList());
  }

  @Operation(summary = ("Envoie d'un e-mail de souscription à un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "La souscription à un abonnement qui a été envoyée",
        content = @Content(schema = @Schema(implementation = SouscriptionAbonnementApi.class))),
    @ApiResponse(responseCode = "404", description = "L'abonnement n'a pas été trouvé")
  })
  @PostMapping(
      value = "/abonnements/{id}/email-de-souscription",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SouscriptionAbonnementApi> envoyerUnEmailDeSouscription(
      @PathVariable Long id, @RequestBody String email) {
    String emailAUtiliser = email.replaceAll("\"", "");

    return ResponseEntity.status(CREATED)
        .body(
            souscriptionAbonnementApiMapper.convertirEnSouscriptionAbonnementApi(
                envoyerUnEmailDeSouscription.executer(id, emailAUtiliser)));
  }

  @Operation(summary = ("Récupération de toutes les souscriptions à un abonnement depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste des souscriptions à un abonnement qui ont été sauvegardées",
        content = @Content(schema = @Schema(allOf = SouscriptionAbonnementApi.class))),
    @ApiResponse(
        responseCode = "404",
        description = "Les souscriptions à cet abonnement n'ont pas été trouvées")
  })
  @GetMapping(
      value = "/abonnements/{id}/email-de-souscription",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<SouscriptionAbonnementApi>> recuperDesSouscriptionAbonnements(
      @PathVariable Long id) {
    return ResponseEntity.status(OK)
        .body(
            recuperDesSouscriptionAbonnements.executer(id).stream()
                .map(souscriptionAbonnementApiMapper::convertirEnSouscriptionAbonnementApi)
                .toList());
  }

  @Operation(summary = ("Récupération du chiffres d'affaire à partir d'une date de fin de période"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Le chiffre d'affaire calculé",
        content =
            @Content(
                schema = @Schema(implementation = Double.class),
                examples = @ExampleObject(value = "320.0")))
  })
  @GetMapping(value = "/abonnements/chiffre-d-affaire", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Double> recuperLeChiffreDAffaireDeLaFinDePeriode(
      @RequestParam String dateDeFinDeLaPeriode) {
    return ResponseEntity.ok(
        recuperLeChiffreDAffaireDeLaFinDePeriode.executer(dateDeFinDeLaPeriode));
  }
}
