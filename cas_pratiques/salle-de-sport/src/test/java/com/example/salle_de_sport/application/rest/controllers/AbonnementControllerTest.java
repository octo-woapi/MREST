package com.example.salle_de_sport.application.rest.controllers;

import com.example.salle_de_sport.application.rest.mappers.AbonnementApiMapper;
import com.example.salle_de_sport.application.rest.mappers.FormuleApiMapper;
import com.example.salle_de_sport.application.rest.mappers.PeriodeApiMapper;
import com.example.salle_de_sport.application.rest.mappers.SouscriptionAbonnementApiMapper;
import com.example.salle_de_sport.application.rest.models.AbonnementApi;
import com.example.salle_de_sport.application.rest.models.SouscriptionAbonnementApi;
import com.example.salle_de_sport.domain.models.Abonnement;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import com.example.salle_de_sport.domain.usecases.CreerUnAbonnement;
import com.example.salle_de_sport.domain.usecases.EnvoyerUnEmailDeSouscription;
import com.example.salle_de_sport.domain.usecases.RecuperDesSouscriptionAbonnements;
import com.example.salle_de_sport.domain.usecases.RecuperLeChiffreDAffaireDeLaFinDePeriode;
import com.example.salle_de_sport.domain.usecases.RecupererTousLesAbonnements;
import com.example.salle_de_sport.domain.usecases.RecupererUnAbonnement;
import com.example.salle_de_sport.domain.usecases.RenouvelerDesAbonnements;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.salle_de_sport.fixtures.AbonnementApiTestBuilder.unAbonnementApi;
import static com.example.salle_de_sport.fixtures.AbonnementTestBuilder.unAbonnement;
import static com.example.salle_de_sport.fixtures.FormuleTestBuilder.uneFormule;
import static com.example.salle_de_sport.fixtures.PeriodeTestBuilder.unePeriode;
import static com.example.salle_de_sport.fixtures.SouscriptionAbonnementApiTestBuilder.uneSouscriptionAbonnementApi;
import static com.example.salle_de_sport.fixtures.SouscriptionAbonnementTestBuilder.uneSouscriptionAbonnement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(AbonnementController.class)
@Import({
  AbonnementApiMapper.class,
  SouscriptionAbonnementApiMapper.class,
  FormuleApiMapper.class,
  PeriodeApiMapper.class
})
public class AbonnementControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RecupererUnAbonnement recupererUnAbonnement;
  @MockBean private RecupererTousLesAbonnements recupererTousLesAbonnements;
  @MockBean private CreerUnAbonnement creerUnAbonnement;
  @MockBean private RenouvelerDesAbonnements renouvelerDesAbonnements;
  @MockBean private EnvoyerUnEmailDeSouscription envoyerUnEmailDeSouscription;
  @MockBean private RecuperDesSouscriptionAbonnements recuperDesSouscriptionAbonnements;

  @MockBean
  private RecuperLeChiffreDAffaireDeLaFinDePeriode recuperLeChiffreDAffaireDeLaFinDePeriode;

  private JsonNode jsonNode;
  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void recupererUnAbonnement_devrait_renvoyer_un_abonnement() throws Exception {
    // Given
    Abonnement abonnement =
        unAbonnement()
            .avecEtudiant(true)
            .avecFormule(uneFormule().build())
            .avecPeriodes(List.of(unePeriode().build()))
            .build();

    AbonnementApi abonnementApiAttendu = unAbonnementApi().avecAbonnement(abonnement).build();

    given(recupererUnAbonnement.executer(1L)).willReturn(abonnement);

    // When
    MockHttpServletResponse response =
        mockMvc.perform(get("/abonnements/1")).andReturn().getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    AbonnementApi abonnementApiRenvoye = unAbonnementApi().avecJsonNode(jsonNode).build();

    assertThat(abonnementApiRenvoye).isEqualTo(abonnementApiAttendu);
  }

  @Test
  public void recupererTousLesAbonnements_devrait_renvoyer_tous_les_abonnements() throws Exception {
    // Given
    Abonnement premierAbonnement =
        unAbonnement()
            .avecEtudiant(true)
            .avecFormule(uneFormule().build())
            .avecPeriodes(List.of(unePeriode().build()))
            .avecPrix(160.0)
            .build();

    Abonnement deuxiemeAbonnement =
        unAbonnement()
            .avecId(2L)
            .avecEmail("user2@example.net")
            .avecFormule(uneFormule().avecId(2L).avecPrixDeBase(120.0).build())
            .avecPeriodes(List.of(unePeriode().avecId(2L).build()))
            .avecPrix(200.0)
            .build();

    List<AbonnementApi> abonnementApisAttendus =
        List.of(
            unAbonnementApi().avecAbonnement(premierAbonnement).build(),
            unAbonnementApi().avecAbonnement(deuxiemeAbonnement).build());

    given(recupererTousLesAbonnements.executer())
        .willReturn(List.of(premierAbonnement, deuxiemeAbonnement));

    // When
    MockHttpServletResponse response =
        mockMvc.perform(get("/abonnements")).andReturn().getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    List<AbonnementApi> abonnementApisRenvoyes =
        unAbonnementApi().avecJsonNode(jsonNode).buildListOf();

    assertThat(abonnementApisRenvoyes).isEqualTo(abonnementApisAttendus);
  }

  @Test
  void creerUnAbonnement_devrait_renvoyer_l_abonnement_cree_et_sauvegarde() throws Exception {
    // Given
    objectMapper = new ObjectMapper();

    Abonnement nouvelAbonnement =
        unAbonnement()
            .avecEtudiant(true)
            .avecFormule(uneFormule().build())
            .avecPeriodes(List.of(unePeriode().build()))
            .avecPrix(160.0)
            .build();

    AbonnementApi nouvelAbonnementApi = unAbonnementApi().avecAbonnement(nouvelAbonnement).build();

    given(creerUnAbonnement.executer(any())).willReturn(nouvelAbonnement);
    String jsonPayload = objectMapper.writeValueAsString(nouvelAbonnementApi);

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/abonnements").contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    AbonnementApi abonnementApiRenvoye = unAbonnementApi().avecJsonNode(jsonNode).build();

    assertThat(abonnementApiRenvoye).isEqualTo(nouvelAbonnementApi);
  }

  @Test
  void renouvelerDesAbonnements_devrait_renvoyer_tous_les_abonnements_renouveles()
      throws Exception {
    // Given
    String dateDeRenouvellement = "2022-06-01";

    Abonnement abonnementARenouveler =
        unAbonnement()
            .avecEtudiant(true)
            .avecDateDeDebut(dateDeRenouvellement)
            .avecFormule(uneFormule().build())
            .avecPeriodes(
                List.of(
                    unePeriode().avecDateDeDebut("2021-01-01").avecDateDeFin("2021-11-30").build(),
                    unePeriode()
                        .avecId(3L)
                        .avecDateDeDebut("2021-12-01")
                        .avecDateDeFin("2022-10-31")
                        .build()))
            .build();

    List<AbonnementApi> abonnementApisAttendus =
        List.of(unAbonnementApi().avecAbonnement(abonnementARenouveler).build());

    given(renouvelerDesAbonnements.executer(dateDeRenouvellement))
        .willReturn(List.of(abonnementARenouveler));

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/abonnements/renouvellement")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dateDeRenouvellement))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    List<AbonnementApi> abonnementApisRenvoyes =
        unAbonnementApi().avecJsonNode(jsonNode).buildListOf();

    assertThat(abonnementApisRenvoyes).isEqualTo(abonnementApisAttendus);
  }

  @Test
  void
      envoyerUnEmailDeSouscription_devrait_renvoyer_la_souscription_d_abonnement_envoyee_et_sauvegardee()
          throws Exception {
    // Given
    String emailDeSouscription = "user2@example.net";

    Abonnement abonnement =
        unAbonnement()
            .avecEtudiant(true)
            .avecFormule(uneFormule().build())
            .avecPeriodes(List.of(unePeriode().build()))
            .build();

    SouscriptionAbonnement souscriptionAbonnement =
        uneSouscriptionAbonnement()
            .avecEmailDeSouscription(emailDeSouscription)
            .avecAbonnement(abonnement)
            .build();

    SouscriptionAbonnementApi souscriptionAbonnementApiAtendue =
        uneSouscriptionAbonnementApi().avecSouscriptionAbonnement(souscriptionAbonnement).build();

    given(envoyerUnEmailDeSouscription.executer(1L, emailDeSouscription))
        .willReturn(souscriptionAbonnement);

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/abonnements/{id}/email-de-souscription", abonnement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(emailDeSouscription)))
            .andReturn()
            .getResponse();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    SouscriptionAbonnementApi souscriptionAbonnementApiRenvoyee =
        uneSouscriptionAbonnementApi().avecJsonNode(jsonNode).build();

    assertThat(souscriptionAbonnementApiRenvoyee).isEqualTo(souscriptionAbonnementApiAtendue);
  }

  @Test
  public void
      recuperDesSouscriptionAbonnements_devrait_renvoyer_toutes_les_souscriptions_liees_a_un_abonnement()
          throws Exception {
    // Given
    Abonnement abonnement =
        unAbonnement()
            .avecFormule(uneFormule().build())
            .avecPeriodes(List.of(unePeriode().build()))
            .build();
    SouscriptionAbonnement premiereSouscriptionAbonnement =
        uneSouscriptionAbonnement().avecAbonnement(abonnement).build();
    SouscriptionAbonnement deuxiemeSouscriptionAbonnement =
        uneSouscriptionAbonnement().avecId(2).avecAbonnement(abonnement).build();

    List<SouscriptionAbonnementApi> souscriptionAbonnementApisAtendues =
        List.of(
            uneSouscriptionAbonnementApi()
                .avecSouscriptionAbonnement(premiereSouscriptionAbonnement)
                .build(),
            uneSouscriptionAbonnementApi()
                .avecId(2)
                .avecSouscriptionAbonnement(deuxiemeSouscriptionAbonnement)
                .build());

    given(recuperDesSouscriptionAbonnements.executer(abonnement.getId()))
        .willReturn(List.of(premiereSouscriptionAbonnement, deuxiemeSouscriptionAbonnement));

    // When
    MockHttpServletResponse response =
        mockMvc.perform(get("/abonnements/1/email-de-souscription")).andReturn().getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    List<SouscriptionAbonnementApi> souscriptionAbonnementApisRenvoyees =
        uneSouscriptionAbonnementApi().avecJsonNode(jsonNode).buildOf();

    assertThat(souscriptionAbonnementApisRenvoyees).isEqualTo(souscriptionAbonnementApisAtendues);
  }

  @Test
  void
      recuperLeChiffreDAffaireDeLaFinDePeriode_devrait_renvoyer_le_chiffre_d_affaire_a_une_date_de_fin_de_periode()
          throws Exception {
    // Given
    String chiffreDAffaireAttendu = "320.0";
    String dateDeFinDeLaPeriode = "2021-11-01";

    given(recuperLeChiffreDAffaireDeLaFinDePeriode.executer(dateDeFinDeLaPeriode))
        .willReturn(Double.parseDouble(chiffreDAffaireAttendu));

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                get("/abonnements/chiffre-d-affaire")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("dateDeFinDeLaPeriode", dateDeFinDeLaPeriode))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);
    assertThat(response.getContentAsString()).isEqualTo(chiffreDAffaireAttendu);
  }
}
