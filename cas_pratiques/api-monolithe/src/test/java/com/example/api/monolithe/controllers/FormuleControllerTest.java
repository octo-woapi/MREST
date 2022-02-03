package com.example.api.monolithe.controllers;

import com.example.api.monolithe.assemblers.FormuleModelAssembler;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.helpers.FormuleEtLiens;
import com.example.api.monolithe.helpers.Liens;
import com.example.api.monolithe.repositories.FormuleRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(FormuleController.class)
@Import(FormuleModelAssembler.class)
public class FormuleControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockBean private FormuleRepository formuleRepository;

  private JsonNode jsonNode;
  private Iterator<JsonNode> jsonNodeIterator;
  private FormuleEtLiens formuleEtLiens;
  private Formule formuleTrouvee;
  private Liens liensTrouves;

  @Test
  void recuperUneFormule_devrait_renvoyer_une_formule() throws Exception {
    // Given
    Formule formuleAttendue = new Formule(1L, 6, 500);
    given(formuleRepository.findById(1L)).willReturn(Optional.of(formuleAttendue));

    // When
    MockHttpServletResponse response =
        mockMvc.perform(get("/formules/1")).andReturn().getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    formuleEtLiens = new FormuleEtLiens(jsonNode);
    formuleTrouvee = formuleEtLiens.getFormule();
    liensTrouves = formuleEtLiens.getLiens();

    assertThat(formuleTrouvee).isEqualTo(formuleAttendue);
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules/1");
    assertThat(liensTrouves.getFormules().getHref()).isEqualTo("http://localhost/formules");
  }

  @Test
  public void recupererToutesLesFormules_devrait_renvoyer_toutes_les_formules_avec_leurs_liens()
      throws Exception {
    // Given
    Formule premiereFormuleAttendue = new Formule(1L, 50, 6);
    Formule secondeFormuleAttendue = new Formule(2L, 100, 12);
    given(formuleRepository.findAll())
        .willReturn(List.of(premiereFormuleAttendue, secondeFormuleAttendue));

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(get("/formules").accept(MediaTypes.HAL_JSON_VALUE))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());

    liensTrouves = new Liens(jsonNode.get("_links"));
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules");

    jsonNodeIterator = jsonNode.at("/_embedded/formuleList").elements();

    formuleEtLiens = new FormuleEtLiens(jsonNodeIterator.next());
    formuleTrouvee = formuleEtLiens.getFormule();
    liensTrouves = formuleEtLiens.getLiens();

    assertThat(formuleTrouvee).isEqualTo(premiereFormuleAttendue);
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules/1");
    assertThat(liensTrouves.getFormules().getHref()).isEqualTo("http://localhost/formules");

    formuleEtLiens = new FormuleEtLiens(jsonNodeIterator.next());
    formuleTrouvee = formuleEtLiens.getFormule();
    liensTrouves = formuleEtLiens.getLiens();

    assertThat(formuleTrouvee).isEqualTo(secondeFormuleAttendue);
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules/2");
    assertThat(liensTrouves.getFormules().getHref()).isEqualTo("http://localhost/formules");
  }

  @Test
  void creerUneFormule_devrait_renvoyer_la_formule_sauvegardee() throws Exception {
    // Given
    ObjectMapper objectMapper = new ObjectMapper();
    Formule nouvelleFormule = new Formule(150, 24);
    Formule formuleAttendue = new Formule(3L, 150, 24);
    given(formuleRepository.save(any())).willReturn(formuleAttendue);

    // When
    MockHttpServletResponse response =
        mockMvc
            .perform(
                post("/formules")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(nouvelleFormule)))
            .andReturn()
            .getResponse();

    // Then
    assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());
    formuleEtLiens = new FormuleEtLiens(jsonNode);
    formuleTrouvee = formuleEtLiens.getFormule();
    liensTrouves = formuleEtLiens.getLiens();

    assertThat(formuleTrouvee).isEqualTo(formuleAttendue);
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules/3");
    assertThat(liensTrouves.getFormules().getHref()).isEqualTo("http://localhost/formules");
  }

  @Test
  void modifierLePrix_devrait_renvoyer_la_formule_modifiee() throws Exception {
    // Given
    Formule formuleActuelle = new Formule(1L, 100, 12);
    Integer nouveauPrix = 90;
    Formule formuleAttendue = new Formule(1L, nouveauPrix, 12);
    given(formuleRepository.findById(1L)).willReturn(Optional.of(formuleActuelle));
    given(formuleRepository.save(any())).willReturn(formuleAttendue);

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
    assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

    jsonNode = new ObjectMapper().readTree(response.getContentAsString());
    formuleEtLiens = new FormuleEtLiens(jsonNode);
    formuleTrouvee = formuleEtLiens.getFormule();
    liensTrouves = formuleEtLiens.getLiens();

    assertThat(formuleTrouvee).isEqualTo(formuleAttendue);
    assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/formules/1");
    assertThat(liensTrouves.getFormules().getHref()).isEqualTo("http://localhost/formules");
  }
}
