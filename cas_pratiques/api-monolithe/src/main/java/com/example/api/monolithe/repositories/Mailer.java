package com.example.api.monolithe.repositories;

public interface Mailer {

    void envoyerEmail(String email, String message);
}
