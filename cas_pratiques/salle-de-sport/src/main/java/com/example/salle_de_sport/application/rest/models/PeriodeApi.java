package com.example.salle_de_sport.application.rest.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class PeriodeApi {

    private Long id;
    private String dateDeDebut;
    private String dateDeFin;

}
