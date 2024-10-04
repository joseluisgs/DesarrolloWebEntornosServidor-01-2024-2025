package dev.joseluisgs;

import dev.joseluisgs.models.Tenista;
import dev.joseluisgs.repository.TenistasRepository;
import dev.joseluisgs.storage.TenistasStorageCsv;
import io.reactivex.rxjava3.disposables.Disposable;

import java.io.File;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var storage = new TenistasStorageCsv();
        var repositorio = new TenistasRepository();


        // Leer el fichero y meterlo en la base de datos
        Disposable d1 = storage.importTenistas(new File("data01.csv"))
                .subscribe(
                        repositorio::save,
                        error -> System.err.println("Error al importar los tenistas: " + error),
                        () -> System.out.println("Importación completada CSV 1")
                );

        Disposable d2 = storage.importTenistas(new File("data02.csv"))
                .subscribe(
                        repositorio::save,
                        error -> System.err.println("Error al importar los tenistas: " + error),
                        () -> System.out.println("Importación completada de CSV2")
                );

        // Suscribimos a las notificaciones para mostrarlos en consola
        Disposable notificaciones = repositorio.getNotificaciones()
                .subscribe(lista -> System.out.println("Nueva actualización: " + lista));
        Disposable listaActualizada = repositorio.getListaActualizada()
                .subscribe(lista -> System.out.println("Lista actualizada: " + lista));


        // Esperar a que el Observable complete su trabajo antes de finalizar el programa
        while (!d1.isDisposed() || !d2.isDisposed()) {
            Thread.sleep(1000);
        }

        // Liberar recursos
        d1.dispose();
        d2.dispose();

        // Actualizamos el tenista con id 1
        var tenista = repositorio.findById(1L);
        var tenistaActualizado = new Tenista(tenista.id(), "Joseluis García", "España", 185, 75, 2000, tenista.mano(), tenista.fechaNacimiento());
        repositorio.update(1L, tenistaActualizado);
        Thread.sleep(1000);
        // Borramos el tenista con id 2
        repositorio.deleteById(2L);
        Thread.sleep(1000);


        notificaciones.dispose();
        listaActualizada.dispose();
    }
}