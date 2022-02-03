package com.example.api.monolithe.assemblers;

import com.example.api.monolithe.controllers.AbonnementController;
import com.example.api.monolithe.entities.Abonnement;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AbonnementModelAssembler implements RepresentationModelAssembler<Abonnement, EntityModel<Abonnement>> {

    @Override
    public EntityModel<Abonnement> toModel(Abonnement abonnement) {
        return EntityModel.of(abonnement,
                linkTo(methodOn(AbonnementController.class).recupererUnAbonnement(abonnement.getId())).withSelfRel(),
                linkTo(methodOn(AbonnementController.class).recupererTousLesAbonnements()).withRel("abonnements"));
    }
}
