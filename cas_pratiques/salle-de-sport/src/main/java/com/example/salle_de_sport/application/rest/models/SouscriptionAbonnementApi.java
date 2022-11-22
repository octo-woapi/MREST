package com.example.salle_de_sport.application.rest.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class SouscriptionAbonnementApi {

    private Long id;
    private String email;
    private AbonnementApi abonnementApi;

}
