package dev.joseluisgs.repository;

import dev.joseluisgs.models.Tenista;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenistasRepository {
    private final Logger logger = LoggerFactory.getLogger(TenistasRepository.class);
    private final Map<Long, Tenista> db = new HashMap<>();
    private FluxSink<List<Tenista>> listaActualizada;
    private FluxSink<String> notificaciones;

    public Flux<List<Tenista>> getListaActualizada() {
        return Flux.<List<Tenista>>create(emitter -> this.listaActualizada = emitter).share();
    }

    public Flux<String> getNotificaciones() {
        return Flux.<String>create(emitter -> this.notificaciones = emitter).share();
    }


    public List<Tenista> findAll() {
        logger.debug("Finding all tenistas");
        if (db.isEmpty()) {
            return List.of();
        } else {
            return List.copyOf(db.values());
        }
    }

    public Tenista findById(Long id) {
        logger.debug("Finding tenista by id: {}", id);
        return db.get(id);
    }

    public Tenista save(Tenista tenista) {
        logger.debug("Saving tenista: {}", tenista);
        var id = Long.valueOf(db.size() + 1);
        var newTenista = new Tenista(id, tenista.nombre(), tenista.pais(), tenista.altura(), tenista.peso(), tenista.puntos(), tenista.mano(), tenista.fechaNacimiento());
        db.put(id, newTenista);
        listaActualizada.next(findAll());
        notificaciones.next("Nuevo tenista añadido: " + newTenista.nombre());
        return newTenista;
    }

    public Tenista update(Long id, Tenista tenista) {
        logger.debug("Updating tenista by id: {}", id);
        if (db.containsKey(id)) {
            var newTenista = new Tenista(id, tenista.nombre(), tenista.pais(), tenista.altura(), tenista.peso(), tenista.puntos(), tenista.mano(), tenista.fechaNacimiento());
            db.put(id, newTenista);
            listaActualizada.next(findAll());
            notificaciones.next("Tenista actualizado: " + newTenista.nombre());
            return newTenista;
        } else {
            return null;
        }
    }

    public Tenista deleteById(Long id) {
        logger.debug("Deleting tenista by id: {}", id);
        var tenista = db.remove(id);
        if (tenista != null) {
            listaActualizada.next(findAll());
            notificaciones.next("Tenista eliminado: " + tenista.nombre());
            return tenista;
        }
        return null;
    }
}
