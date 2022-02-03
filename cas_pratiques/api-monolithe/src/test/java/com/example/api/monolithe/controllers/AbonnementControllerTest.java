package com.example.api.monolithe.controllers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import com.example.api.monolithe.assemblers.AbonnementModelAssembler;
import com.example.api.monolithe.assemblers.SouscriptionAbonnementModelAssembler;
import com.example.api.monolithe.entities.Abonnement;
import com.example.api.monolithe.entities.Formule;
import com.example.api.monolithe.entities.SouscriptionAbonnement;
import com.example.api.monolithe.helpers.AbonnementEtLiens;
import com.example.api.monolithe.helpers.Liens;
import com.example.api.monolithe.helpers.SouscriptionAbonnementEtLiens;
import com.example.api.monolithe.repositories.AbonnementRepository;
import com.example.api.monolithe.repositories.FakeMailer;
import com.example.api.monolithe.repositories.FormuleRepository;
import com.example.api.monolithe.repositories.SouscriptionAbonnementRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(AbonnementController.class)
@Import({AbonnementModelAssembler.class,
        SouscriptionAbonnementModelAssembler.class,
        FakeMailer.class
})
public class AbonnementControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private AbonnementRepository abonnementRepository;
    @MockBean private FormuleRepository formuleRepository;
    @MockBean private SouscriptionAbonnementRepository souscriptionAbonnementRepository;

    @Mock private Appender<ILoggingEvent> loggingAppender;
    @Captor private ArgumentCaptor<ILoggingEvent> loggingEventArgumentCaptor;

    private JsonNode jsonNode;
    private Iterator<JsonNode> jsonNodeIterator;

    private AbonnementEtLiens abonnementEtLiens;
    private SouscriptionAbonnementEtLiens souscriptionAbonnementEtLiens;

    private Abonnement abonnementTrouve;
    private SouscriptionAbonnement souscriptionAbonnementTrouvee;
    private Liens liensTrouves;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        ((Logger) LoggerFactory.getLogger(FakeMailer.class)).addAppender(loggingAppender);
    }

    @AfterEach
    void tearDown() {
        ((Logger) LoggerFactory.getLogger(FakeMailer.class)).detachAppender(loggingAppender);
    }

    @Test
    void recupererUnAbonnement_devrait_renvoyer_un_abonnement() throws Exception {
        // Given
        Abonnement abonnementAttendu =
                new Abonnement("user@example.net", true, "2022-01-01",
                         new Formule(2L,100,12)
                );
        abonnementAttendu.setId(1L);

        given(abonnementRepository.findById(1L))
                .willReturn(Optional.of(abonnementAttendu));

        // When
        MockHttpServletResponse response =
                mockMvc.perform(get("/abonnements/1"))
                        .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

        jsonNode = new ObjectMapper().readTree(response.getContentAsString());

        abonnementEtLiens = new AbonnementEtLiens(jsonNode);
        abonnementTrouve = abonnementEtLiens.getAbonnement();
        liensTrouves = abonnementEtLiens.getLiens();

        assertThat(abonnementTrouve).isEqualTo(abonnementAttendu);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/1");
        assertThat(liensTrouves.getAbonnements().getHref()).isEqualTo("http://localhost/abonnements");
    }

    @Test
    public void recupererTousLesAbonnements_devrait_renvoyer_tous_les_abonnements_avec_leurs_liens() throws Exception {
        // Given
        Abonnement premierAbonnementAttendu =
                new Abonnement("user@example.net", true, "2022-01-01",
                        new Formule(2L,100,12)
                );
        premierAbonnementAttendu.setId(1L);
        Abonnement secondAbonnementAttendu =
                new Abonnement("user2@example.net", false, "2022-02-01",
                        new Formule(3L,120,12)
                );
        secondAbonnementAttendu.setId(2L);
        given(abonnementRepository.findAll())
                .willReturn(List.of(premierAbonnementAttendu, secondAbonnementAttendu));

        // When
        MockHttpServletResponse response =
                mockMvc
                        .perform(
                                get("/abonnements")
                                        .accept(MediaTypes.HAL_JSON_VALUE))
                        .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

        jsonNode = new ObjectMapper().readTree(response.getContentAsString());

        liensTrouves = new Liens(jsonNode.get("_links"));
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements");

        jsonNodeIterator = jsonNode.at("/_embedded/abonnementList").elements();

        abonnementEtLiens = new AbonnementEtLiens(jsonNodeIterator.next());
        abonnementTrouve = abonnementEtLiens.getAbonnement();
        liensTrouves = abonnementEtLiens.getLiens();

        assertThat(abonnementTrouve).isEqualTo(premierAbonnementAttendu);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/1");
        assertThat(liensTrouves.getAbonnements().getHref()).isEqualTo("http://localhost/abonnements");

        abonnementEtLiens = new AbonnementEtLiens(jsonNodeIterator.next());
        abonnementTrouve = abonnementEtLiens.getAbonnement();
        liensTrouves = abonnementEtLiens.getLiens();

        assertThat(abonnementTrouve).isEqualTo(secondAbonnementAttendu);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/2");
        assertThat(liensTrouves.getAbonnements().getHref()).isEqualTo("http://localhost/abonnements");
    }

    @Test
    void creerUnAbonnement_devrait_renvoyer_l_abonnement_sauvegarde() throws Exception {
        // Given
        objectMapper = new ObjectMapper();
        Formule formuleChoisie = new Formule(2L,100,12);

        Abonnement nouvelAbonnement =
                new Abonnement("user@example.net", true, "2022-01-01",
                        formuleChoisie);
        Abonnement abonnementAttendu =
                new Abonnement("user@example.net", true, "2022-01-01",
                        formuleChoisie);
        abonnementAttendu.setId(4L);

        given(formuleRepository.findById(2L)).willReturn(Optional.of(formuleChoisie));
        given(abonnementRepository.save(any())).willReturn(abonnementAttendu);
        String jsonPayload = objectMapper.writeValueAsString(nouvelAbonnement);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(
                        post("/abonnements")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonPayload))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

        jsonNode = new ObjectMapper().readTree(response.getContentAsString());
        abonnementEtLiens = new AbonnementEtLiens(jsonNode);
        abonnementTrouve = abonnementEtLiens.getAbonnement();
        liensTrouves = abonnementEtLiens.getLiens();

        assertThat(abonnementTrouve).isEqualTo(abonnementAttendu);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/4");
        assertThat(liensTrouves.getAbonnements().getHref()).isEqualTo("http://localhost/abonnements");
    }

    @Test
    void renouveller_devrait_renvoyer_tous_les_abonnements_renouvelles() throws Exception {
        // Given
        String dateDeRenouvellement = "2022-06-01";

        Abonnement premierAbonnement =
                new Abonnement("user@example.net", true, "2021-01-01",
                        new Formule(2L,100,12)
                );
        premierAbonnement.setId(1L);
        Abonnement secondAbonnement =
                new Abonnement("user2@example.net", false, "2022-01-01",
                        new Formule(3L,120,12)
                );
        secondAbonnement.setId(2L);

        Abonnement abonnementRenouvelleAttendu =
                new Abonnement("user@example.net", true, dateDeRenouvellement,
                        new Formule(2L,100,12)
                );
        abonnementRenouvelleAttendu.setId(1L);

        given(abonnementRepository.findAll())
                .willReturn(List.of(premierAbonnement, secondAbonnement));
        given(abonnementRepository.saveAll(any()))
                .willReturn(List.of(abonnementRenouvelleAttendu));

        // When
        MockHttpServletResponse response = mockMvc
                .perform(
                        put("/abonnements/renouvellement")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dateDeRenouvellement))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

        jsonNode = new ObjectMapper().readTree(response.getContentAsString());

        liensTrouves = new Liens(jsonNode.get("_links"));
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements");

        jsonNodeIterator = jsonNode.at("/_embedded/abonnementList").elements();

        abonnementEtLiens = new AbonnementEtLiens(jsonNodeIterator.next());
        abonnementTrouve = abonnementEtLiens.getAbonnement();
        liensTrouves = abonnementEtLiens.getLiens();

        assertThat(abonnementTrouve).isEqualTo(abonnementRenouvelleAttendu);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/1");
        assertThat(liensTrouves.getAbonnements().getHref()).isEqualTo("http://localhost/abonnements");
    }

    @Test
    void envoyerEmailDeSouscription_devrait_renvoyer_la_souscription_d_abonnements_sauvegardee() throws Exception {
        // Given
        String emailDeSouscription = "user2@example.net";

        String premierMessageLogger = "Envoie d'un e-mail à " + emailDeSouscription;
        String deuxiemeMessageLogger =
                "Contenu : Bienvenu(e) chez CraftGym, profite bien de ton abonnement Formule 12 mois à 100 euros.";

        Abonnement abonnement =
                new Abonnement("user@example.net", true, "2022-01-01",
                        new Formule(2L,100,12)
                );
        abonnement.setId(1L);
        SouscriptionAbonnement souscriptionAbonnement = new SouscriptionAbonnement(1L, emailDeSouscription, abonnement);

        given(abonnementRepository.findById(1L))
                .willReturn(Optional.of(abonnement));
        given(souscriptionAbonnementRepository.save(any()))
                .willReturn(souscriptionAbonnement);

        // When
        MockHttpServletResponse response = mockMvc
                .perform(
                        post("/abonnements/{id}/email-de-souscription", abonnement.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(emailDeSouscription)))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentType()).isEqualTo(MediaTypes.HAL_JSON_VALUE);

        jsonNode = new ObjectMapper().readTree(response.getContentAsString());

        souscriptionAbonnementEtLiens = new SouscriptionAbonnementEtLiens(jsonNode);
        souscriptionAbonnementTrouvee = souscriptionAbonnementEtLiens.getSouscriptionAbonnement();
        liensTrouves = souscriptionAbonnementEtLiens.getLiens();

        assertThat(souscriptionAbonnementTrouvee).isEqualTo(souscriptionAbonnement);
        assertThat(liensTrouves.getSelf().getHref()).isEqualTo("http://localhost/abonnements/1/email-de-souscription");

        verify(loggingAppender, times(2)).doAppend(loggingEventArgumentCaptor.capture());
        List<ILoggingEvent> events = loggingEventArgumentCaptor.getAllValues();
        assertThat(events.get(0).getLevel()).isEqualTo(Level.INFO);
        assertThat(events.get(0).getFormattedMessage()).isEqualTo(premierMessageLogger);

        assertThat(events.get(1).getLevel()).isEqualTo(Level.INFO);
        assertThat(events.get(1).getFormattedMessage()).isEqualTo(deuxiemeMessageLogger);
    }

    @Test
    void recuperChiffreDAffaire_devrait_renvoyer_le_chiffre_d_affaire_a_une_date_de_fin_de_periode() throws Exception {
        // Given
        String chiffreDAffaireAttendu = "320.0";
        String dateDeFinDePeriode = "2021-11-01";

        Abonnement premierAbonnement =
                new Abonnement( "user@example.net", true, "2021-01-01",
                        new Formule(2L,100,12)
                );
        premierAbonnement.setId(1L);
        Abonnement secondAbonnement =
                new Abonnement("user2@example.net", false, "2021-01-01",
                        new Formule(3L,120,12)
                );
        secondAbonnement.setId(2L);
        Abonnement dernierAbonnement =
                new Abonnement("user2@example.net", false, "2021-06-01",
                        new Formule(3L,120,6)
                );
        dernierAbonnement.setId(3L);

        given(abonnementRepository.findAll())
                .willReturn(List.of(premierAbonnement, secondAbonnement, dernierAbonnement));

        // When
        MockHttpServletResponse response = mockMvc
                .perform(
                        get("/abonnements/chiffre-d-affaire")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("dateDeFinDePeriode", dateDeFinDePeriode))
                .andReturn().getResponse();

        // Then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(chiffreDAffaireAttendu);
    }

}
