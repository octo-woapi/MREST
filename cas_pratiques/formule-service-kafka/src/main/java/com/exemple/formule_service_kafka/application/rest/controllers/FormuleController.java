package com.exemple.formule_service_kafka.application.rest.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.exemple.formule_service_kafka.application.rest.mappers.FormuleApiMapper;
import com.exemple.formule_service_kafka.application.rest.models.FormuleApi;
import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.domain.usecases.CreerUneFormule;
import com.exemple.formule_service_kafka.domain.usecases.ModifierLePrixDuneFormule;
import com.exemple.formule_service_kafka.domain.usecases.RecupererToutesLesFormules;
import com.exemple.formule_service_kafka.domain.usecases.RecupererUneFormule;
import com.exemple.formule_service_kafka.infrastructure.kafka.config.KafkaConfiguration;
import com.exemple.formule_service_kafka.infrastructure.kafka.models.KafkaMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class FormuleController {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final KafkaConfiguration kafkaConfiguration;
  private final CountDownLatch countDownLatch;

  private final FormuleApiMapper formuleApiMapper;

  private final RecupererUneFormule recupererUneFormule;
  private final RecupererToutesLesFormules recupererToutesLesFormules;
  private final CreerUneFormule creerUneFormule;
  private final ModifierLePrixDuneFormule modifierLePrixDuneFormule;

  @Operation(summary = ("Récupération d'une formule depuis son ID"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La formule a été trouvée",
        content = @Content(schema = @Schema(implementation = FormuleApi.class))),
    @ApiResponse(
        responseCode = "404",
        description = "La formule n'a pas été trouvée",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @GetMapping(value = "/formules/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FormuleApi> recuperUneFormule(@PathVariable UUID id) {
    return ResponseEntity.status(OK)
        .body(formuleApiMapper.convertirEnFormuleApi(recupererUneFormule.executer(id)));
  }

  @Operation(summary = ("Récupération de toutes les formules"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "La liste de toutes les formules trouvées",
        content = @Content(schema = @Schema(allOf = FormuleApi.class))),
  })
  @GetMapping(value = "/formules", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<FormuleApi>> recupererToutesLesFormules() throws Exception {
    IntStream.range(0, kafkaConfiguration.getMessagesPerRequest())
        .forEach(
            identifier ->
                this.kafkaTemplate.send(
                    kafkaConfiguration.getTopicName(),
                    String.valueOf(identifier),
                    new KafkaMessage("Boucle temporaire sur la lecture des formules", identifier)));
    countDownLatch.await(60, TimeUnit.SECONDS);
    log.info("Tous les messages ont été envoyés");

    return ResponseEntity.status(OK)
        .body(
            recupererToutesLesFormules.executer().stream()
                .map(formuleApiMapper::convertirEnFormuleApi)
                .toList());
  }

  @Operation(summary = ("Création d'une formule"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "La formule a été créée",
        content = @Content(schema = @Schema(implementation = FormuleApi.class))),
  })
  @PostMapping(value = "/formules", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FormuleApi> creerUneFormule(@RequestBody FormuleApi formuleApi) {
    Formule formule = formuleApiMapper.convertirEnFormule(formuleApi);
    return ResponseEntity.status(CREATED)
        .body(formuleApiMapper.convertirEnFormuleApi(creerUneFormule.executer(formule)));
  }

  @Operation(summary = ("Modification du prix d'une formule"))
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Le prix de la formule a été modifié",
        content = @Content(schema = @Schema(implementation = FormuleApi.class))),
    @ApiResponse(
        responseCode = "404",
        description = "La formule n'a pas été trouvée",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
  })
  @PutMapping(value = "/formules/{id}/prix", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<FormuleApi> modifierLePrixDuneFormule(
      @PathVariable UUID id, @RequestBody Double nouveauPrix) {
    return ResponseEntity.status(OK)
        .body(
            formuleApiMapper.convertirEnFormuleApi(
                modifierLePrixDuneFormule.executer(id, nouveauPrix)));
  }
}
