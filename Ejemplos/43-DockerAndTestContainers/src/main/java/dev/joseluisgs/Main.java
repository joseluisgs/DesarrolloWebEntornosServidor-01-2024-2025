package dev.joseluisgs;

import dev.joseluisgs.config.ConfigProperties;
import dev.joseluisgs.database.DataBaseManager;
import dev.joseluisgs.model.Persona;
import dev.joseluisgs.repository.PersonasRepositoryImpl;
import dev.joseluisgs.services.PersonasServiceImpl;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hola Test Containers");

        var repository = new PersonasRepositoryImpl(
                DataBaseManager.getInstance(
                        new ConfigProperties("application.properties")
                )
        );
        var service = new PersonasServiceImpl(repository);

        System.out.println("Obteniendo personas...");
        service.getAll().peek(list -> System.out.println("Valor correcto: " + list));

        System.out.println("Obteniendo persona por id...");
        service.getById(1)
                .peek(persona -> System.out.println("Persona encontrada: " + persona))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Obteniendo persona por id No existente...");
        service.getById(100)
                //.peek(persona -> System.out.println("Valor correcto: " + persona))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Insertando persona...");
        var persona = Persona.builder()
                .nombre("Main")
                .build();

        service.create(persona)
                .peek(persona1 -> System.out.println("Persona creada: " + persona1))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Obteniendo personas...");
        service.getAll().peek(list -> System.out.println("Valor correcto: " + list));

        System.out.println("Actualizando persona...");
        persona.setNombre("Main Actualizado");

        service.update(persona.getId(), persona)
                .peek(persona1 -> System.out.println("Persona actualizada: " + persona1))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Actualizando persona No existente...");
        service.update(100, persona)
                .peek(persona1 -> System.out.println("Persona actualizada: " + persona1))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Obteniendo personas...");
        service.getAll().peek(list -> System.out.println("Valor correcto: " + list));

        System.out.println("Borrando persona...");
        service.delete(persona.getId())
                .peek(persona1 -> System.out.println("Persona borrada: " + persona1))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Borrando persona No existente...");
        service.delete(100)
                .peek(persona1 -> System.out.println("Persona borrada: " + persona1))
                .peekLeft(error -> System.out.println("Error: " + error.getMessage()));

        System.out.println("Obteniendo personas...");
        service.getAll().peek(list -> System.out.println("Valor correcto: " + list));


    }
}