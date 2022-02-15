package com.example.salle_de_sport.application.rest.mappers;

import com.example.salle_de_sport.application.rest.models.SouscriptionAbonnementApi;
import com.example.salle_de_sport.domain.models.SouscriptionAbonnement;
import org.springframework.stereotype.Component;

@Component
public class SouscriptionAbonnementApiMapper {

    private final AbonnementApiMapper abonnementApiMapper;

    public SouscriptionAbonnementApiMapper(AbonnementApiMapper abonnementApiMapper) {
        this.abonnementApiMapper = abonnementApiMapper;
    }

    public SouscriptionAbonnementApi convertirEnSouscriptionAbonnementApi(SouscriptionAbonnement souscriptionAbonnement) {
        return new SouscriptionAbonnementApi(
                souscriptionAbonnement.getId(),
                souscriptionAbonnement.getEmail(),
                abonnementApiMapper.convertirEnAbonnementApi(
                        souscriptionAbonnement.getAbonnement()));
    }
}
