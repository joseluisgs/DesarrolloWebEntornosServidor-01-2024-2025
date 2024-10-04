package dev.joseluisgs.storage;

import dev.joseluisgs.models.Mano;
import dev.joseluisgs.models.Tenista;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.List;


public class TenistasStorageCsv {
    private final Logger logger = LoggerFactory.getLogger(TenistasStorageCsv.class);

    public Flux<Tenista> importTenistas(File file) {
        logger.debug("Importing tenistas from file: {}", file.getAbsolutePath());
        return Flux.<Tenista>create(emitter -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                reader.lines()
                        .skip(1) // Skip header
                        .forEach(line -> {
                            Tenista tenista = parseLine(List.of(line.split(",")));
                            emitter.next(tenista);
                        });
                emitter.complete();
            } catch (Exception e) {
                emitter.error(e);
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private Tenista parseLine(List<String> parts) {
        return new Tenista(
                Long.parseLong(parts.get(0)),
                parts.get(1),
                parts.get(2),
                Integer.parseInt(parts.get(3)),
                Integer.parseInt(parts.get(4)),
                Integer.parseInt(parts.get(5)),
                parts.get(6).equals("DIESTRO") ? Mano.DIESTRO : Mano.ZURDO,
                LocalDate.parse(parts.get(7))
        );
    }
}
