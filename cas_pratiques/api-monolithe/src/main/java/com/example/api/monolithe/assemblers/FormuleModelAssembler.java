package com.example.api.monolithe.assemblers;

import com.example.api.monolithe.controllers.FormuleController;
import com.example.api.monolithe.entities.Formule;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FormuleModelAssembler implements RepresentationModelAssembler<Formule, EntityModel<Formule>> {

    @Override
    public EntityModel<Formule> toModel(Formule formule) {
        return EntityModel.of(formule,
                linkTo(methodOn(FormuleController.class).recuperUneFormule(formule.getId())).withSelfRel(),
                linkTo(methodOn(FormuleController.class).recupererToutesLesFormules()).withRel("formules"));
    }
}
