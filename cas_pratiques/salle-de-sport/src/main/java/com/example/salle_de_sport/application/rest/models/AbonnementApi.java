package com.example.salle_de_sport.application.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class AbonnementApi {

    private Long id;
    private String email;
    @JsonProperty("estEtudiant")
    private boolean estEtudiant;
    private String dateDeDebut;
    private FormuleApi formuleApiChoisie;
    private Double prix;

    private List<PeriodeApi> periodesApi = new ArrayList<>();

}
