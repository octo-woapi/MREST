package com.example.formule_service.infrastructure.database;

import com.example.formule_service.infrastructure.database.repositories.FormuleRepository;
import com.example.formule_service.domain.models.Formule;
import com.example.formule_service.domain.usecases.FormulePersistence;
import com.example.formule_service.infrastructure.database.mappers.FormuleMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FormuleDatabaseJdbc implements FormulePersistence {

  private final FormuleRepository formuleRepository;
  private final FormuleMapper formuleMapper;

  public FormuleDatabaseJdbc(FormuleRepository formuleRepository, FormuleMapper formuleMapper) {
    this.formuleRepository = formuleRepository;
    this.formuleMapper = formuleMapper;
  }

  @Override
  public Optional<Formule> recupererUneFormule(Long id) {
    return formuleRepository.findById(id).map(formuleMapper::convertirEnFormule);
  }

  @Override
  public List<Formule> recupererToutesLesFormules() {
    return formuleRepository.findAll().stream().map(formuleMapper::convertirEnFormule).toList();
  }

  @Override
  public Formule creerUneFormule(final Formule formule) {
    return formuleMapper.convertirEnFormule(
        formuleRepository.save(formuleMapper.convertirEnFormuleEntity(formule)));
  }

  @Override
  public Formule modifierUneFormule(Formule formule) {
    return formuleMapper.convertirEnFormule(
        formuleRepository.save(formuleMapper.convertirEnFormuleEntity(formule)));
  }
}
