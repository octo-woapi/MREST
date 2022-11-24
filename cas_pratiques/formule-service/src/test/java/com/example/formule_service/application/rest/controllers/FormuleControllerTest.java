package com.example.formule_service.application.rest.controllers;

import com.example.formule_service.application.rest.mappers.FormuleApiMapper;
import com.example.formule_service.application.rest.models.FormuleApi;
import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.domain.usecases.CreerUneFormule;
import com.example.formule_service.domain.usecases.ModifierLePrixDuneFormule;
import com.example.formule_service.domain.usecases.RecupererToutesLesFormules;
import com.example.formule_service.domain.usecases.RecupererUneFormule;
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

import static com.example.formule_service.fixtures.FormuleApiTestBuilder.uneFormuleApi;
import static com.example.formule_service.fixtures.FormuleTestBuilder.uneFormule;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(FormuleController.class)
@Import(FormuleApiMapper.class)
public class FormuleControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private RecupererUneFormule recupererUneFormule;
  @MockBean private RecupererToutesLesFormules recupererToutesLesFormules;
  @MockBean private CreerUneFormule creerUneFormule;
  @MockBean private ModifierLePrixDuneFormule modifierLePrixDuneFormule;

  private JsonNode jsonNode;

  @Test
  void recuperUneFormule_devrait_renvoyer_une_formule() throws Exception {
    // Given
    Formule formule = uneFormule().build();
    FormuleApi formuleApiAtttendue = uneFormuleApi().avecFormule(formule).build();
    given(recupererUneFormule.executer(1L)).willReturn(formule);

    // When
    MockHttpServletResponse response =
        mockMvc.perform(get("/formules/1")).andReturn().getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    FormuleApi formuleApiRenvoyee = uneFormuleApi().avecJsonNode(jsonNode).build();

    assertThat(formuleApiRenvoyee).isEqualTo(formuleApiAtttendue);
  }

  @Test
  public void recupererToutesLesFormules_devrait_renvoyer_toutes_les_formules() throws Exception {
    // Given
    Formule premiereFormule = uneFormule().build();
    Formule deuxiemeFormule = uneFormule().avecId(2L).build();
    List<FormuleApi> formuleApisAttendues =
        List.of(
            uneFormuleApi().avecFormule(premiereFormule).build(),
            uneFormuleApi().avecFormule(deuxiemeFormule).build());

    given(recupererToutesLesFormules.executer())
        .willReturn(List.of(premiereFormule, deuxiemeFormule));

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(get("/formules").accept(MediaType.APPLICATION_JSON_VALUE))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    List<FormuleApi> formuleApisRenvoyees = uneFormuleApi().avecJsonNode(jsonNode).buildOf();

    assertThat(formuleApisRenvoyees).isEqualTo(formuleApisAttendues);
  }

  @Test
  void creerUneFormule_devrait_renvoyer_la_formule_cree_et_sauvegardee() throws Exception {
    // Given
    Formule nouvelleFormule = uneFormule().build();
    FormuleApi nouvelleFormuleApi = uneFormuleApi().avecFormule(nouvelleFormule).build();
    FormuleApi formuleApiAttendue = uneFormuleApi().avecFormule(nouvelleFormule).build();

    given(creerUneFormule.executer(any())).willReturn(nouvelleFormule);

    String jsonPayload = new ObjectMapper().writeValueAsString(nouvelleFormuleApi);

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(post("/formules").contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    FormuleApi formuleApiRenvoyee = uneFormuleApi().avecJsonNode(jsonNode).build();

    assertThat(formuleApiRenvoyee).isEqualTo(formuleApiAttendue);
  }

  @Test
  void modifierLePrixDuneFormule_devrait_renvoyer_la_formule_modifiee() throws Exception {
    // Given
    double nouveauPrix = 75.0;
    Formule formuleActuelle = uneFormule().build();
    Formule formuleModifiee = uneFormule().avecPrixDeBase(nouveauPrix).build();
    FormuleApi formuleApiAttendue = uneFormuleApi().avecFormule(formuleModifiee).build();

    given(modifierLePrixDuneFormule.executer(formuleActuelle.getId(), nouveauPrix))
        .willReturn(formuleModifiee);

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                put("/formules/{id}/prix", formuleActuelle.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(String.valueOf(nouveauPrix)))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    FormuleApi formuleApiRenvoyee = uneFormuleApi().avecJsonNode(jsonNode).build();

    assertThat(formuleApiRenvoyee).isEqualTo(formuleApiAttendue);
  }
}
