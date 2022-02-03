package com.example.api.monolithe.helpers;

import com.example.api.monolithe.entities.Formule;
import com.fasterxml.jackson.databind.JsonNode;

public class FormuleEtLiens {

    private Formule formule;
    private Liens liens;

    public FormuleEtLiens(JsonNode jsonNode) {
        this.formule = new Formule(
            jsonNode.get("id").asLong(),
            jsonNode.get("prixDeBase").asInt(),
            jsonNode.get("nbrMois").asInt());
        this.liens = new Liens(jsonNode.get("_links"));
    }

    public Formule getFormule() {
        return this.formule;
    }

    public Liens getLiens() {
        return this.liens;
    }
}
