package com.example.api.monolithe.assemblers;

import com.example.api.monolithe.controllers.AbonnementController;
import com.example.api.monolithe.entities.SouscriptionAbonnement;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SouscriptionAbonnementModelAssembler implements RepresentationModelAssembler<SouscriptionAbonnement, EntityModel<SouscriptionAbonnement>> {

    @Override
    public EntityModel<SouscriptionAbonnement> toModel(SouscriptionAbonnement souscriptionAbonnement) {
        return EntityModel.of(souscriptionAbonnement,
                linkTo(methodOn(AbonnementController.class).recuperEmailDeSouscriptions(
                        souscriptionAbonnement.getAbonnement().getId())).withSelfRel());
    }
}
