package com.example.salle_de_sport.domain.usecases;

public interface MessageriePersistence {

    void envoyerUnEmail(String emailAUtiliser, String formatted);
}
