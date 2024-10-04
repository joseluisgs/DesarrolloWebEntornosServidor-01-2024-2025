package dev.joseluisgs.server.repositories.alumnado;


import dev.joseluisgs.common.models.Alumno;
import dev.joseluisgs.server.repositories.crud.CrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

// Cogemos la interfaz Crud la contextualizamos a nuestro tipo y añadimos metodos sin falta
// Por ejeplo un FibByNombre
public interface AlumnosRepository extends CrudRepository<Alumno, Long> {
    // Buscar por nombre
    Flux<Alumno> findByNombre(String nombre);

    Mono<Alumno> findByUuid(UUID uuid);
}
