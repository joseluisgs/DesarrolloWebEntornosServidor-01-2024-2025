package dev.joseluisgs;

import io.reactivex.rxjava3.core.Observable;

import java.util.concurrent.TimeUnit;

public class MainRx {
    public static void main(String[] args) throws InterruptedException {
        Observable<Long> intervalObservable = Observable.interval(1, TimeUnit.SECONDS);

        // A veces no sabes cuando se producirán los datos, si no reaccionamos a ello.

        intervalObservable
                .filter(x -> x % 2 == 0)
                .map(x -> x * 10)
                .take(10) // toma al menos al menos x valores
                .subscribe(
                        // Se ejecuta cada vez que llega un valor
                        value -> System.out.println("Consumido: " + value),
                        // Se ejecuta cuando se produce un error
                        error -> System.err.println("Se ha producido un error: " + error),
                        // Se ejecuta cuando se completa el flujo (no es obligatorio)
                        () -> System.out.println("Completado")
                );

        // Mantén el hilo principal vivo durante un tiempo para que pueda consumir los valores
        Thread.sleep(20000);
    }
}