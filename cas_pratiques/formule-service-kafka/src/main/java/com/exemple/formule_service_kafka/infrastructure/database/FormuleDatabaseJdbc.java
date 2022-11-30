package com.exemple.formule_service_kafka.infrastructure.database;

import com.exemple.formule_service_kafka.domain.models.Formule;
import com.exemple.formule_service_kafka.domain.usecases.FormulePersistence;
import com.exemple.formule_service_kafka.infrastructure.database.mappers.FormuleMapper;
import com.exemple.formule_service_kafka.infrastructure.database.repositories.FormuleRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FormuleDatabaseJdbc implements FormulePersistence {

  private final FormuleRepository formuleRepository;
  private final FormuleMapper formuleMapper;

  public FormuleDatabaseJdbc(FormuleRepository formuleRepository, FormuleMapper formuleMapper) {
    this.formuleRepository = formuleRepository;
    this.formuleMapper = formuleMapper;
  }

  @Override
  public Optional<Formule> recupererUneFormule(UUID id) {
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
