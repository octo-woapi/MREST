package com.example.api.monolithe.helpers;

import com.example.api.monolithe.entities.Abonnement;
import com.example.api.monolithe.entities.Formule;
import com.fasterxml.jackson.databind.JsonNode;

public class AbonnementEtLiens {

    private Abonnement abonnement;
    private Liens liens;

    public AbonnementEtLiens(JsonNode jsonNode) {
        this.abonnement = new Abonnement(
            jsonNode.get("email").asText(),
            jsonNode.get("estEtudiant").asBoolean(),
            jsonNode.get("dateDeDebut").asText(),
            toFormuleChoisie(jsonNode.get("formuleChoisie")));
        this.abonnement.setId(jsonNode.get("id").asLong());
        this.abonnement.setPrix(jsonNode.get("prix").asText());

        this.liens = new Liens(jsonNode.get("_links"));
    }

    private Formule toFormuleChoisie(JsonNode jsonNode) {
        return new Formule(
                jsonNode.get("id").asLong(),
                jsonNode.get("prixDeBase").asInt(),
                jsonNode.get("nbrMois").asInt());
    }

    public Abonnement getAbonnement() {
        return this.abonnement;
    }

    public Liens getLiens() {
        return this.liens;
    }
}
