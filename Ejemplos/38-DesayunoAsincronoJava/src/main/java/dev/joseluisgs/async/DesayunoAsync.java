package dev.joseluisgs.async;

import dev.joseluisgs.alimentos.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class DesayunoAsync {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var timeInit = System.currentTimeMillis();

        // Cafe taza = servirCafe();
        var taza = servirCafe();
        System.out.println("El café está listo");

        // CompletableFuture<Huevo> huevosTask = freirHuevosAsync(2);
        var huevosTask = freirHuevosAsync(2);
        var beiconTask = freirBeiconAsync(3);
        var tostadaTask = prepararTostadaConMantequillaYMeremeladaAsync(2);

        // Esperar a que todas las tareas se completen
        CompletableFuture<Void> todasLasTareas = CompletableFuture.allOf(huevosTask, beiconTask, tostadaTask);

        // Manejar las tareas completadas
        todasLasTareas.thenRun(() -> {
            try {
                if (huevosTask.isDone()) {
                    System.out.println("Los huevos están listos");
                }
                if (beiconTask.isDone()) {
                    System.out.println("El beicon está listo");
                }
                if (tostadaTask.isDone()) {
                    System.out.println("Las tostadas están listas");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).join();

        Zumo zumoNaranja = servirZumoNaranja();
        System.out.println("El zumo de naranja está listo");
        System.out.println("¡El desayuno está listo!");

        var timeEnd = System.currentTimeMillis();
        System.out.println("Tiempo total: " + (timeEnd - timeInit) + " ms");
    }

    static CompletableFuture<Tostada> prepararTostadaConMantequillaYMeremeladaAsync(int rebanadas) {
        // Composición de acciones sincronas una vez completada la tarea
        return tostarPanAsync(rebanadas)
                .thenApply(tostada -> {
                    ponerMantequilla(tostada);
                    ponerMermelada(tostada);
                    return tostada;
                });
    }

    private static Zumo servirZumoNaranja() {
        System.out.println("Sirviendo zumo de naranja");
        return new Zumo();
    }

    private static void ponerMermelada(Tostada tostada) {
        System.out.println("Poniendo mermelada en la tostada");
    }

    private static void ponerMantequilla(Tostada tostada) {
        System.out.println("Poniendo mantequilla en la tostada");
    }

    private static CompletableFuture<Tostada> tostarPanAsync(int rebanadas) {
        return CompletableFuture.supplyAsync(() -> {
            for (int rebanada = 0; rebanada < rebanadas; rebanada++) {
                System.out.println("Poniendo una rebanada de pan en la tostadora");
            }
            System.out.println("Comenzando a tostar...");
            try {
                TimeUnit.SECONDS.sleep(3); // Simula la espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Sacando la tostada de la tostadora");
            return new Tostada();
        });
    }

    private static CompletableFuture<Beicon> freirBeiconAsync(int lonchas) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Poniendo " + lonchas + " lonchas de beicon en la sartén");
            System.out.println("Cocinando la primera loncha del beicon...");
            try {
                TimeUnit.SECONDS.sleep(3); // Simula la espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int rebanada = 0; rebanada < lonchas; rebanada++) {
                System.out.println("Dando la vuelta a una loncha de beicon");
            }
            System.out.println("Cocinando el segundo loncha del beicon...");
            try {
                TimeUnit.SECONDS.sleep(3); // Simula la espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Poniendo el beicon en el plato");
            return new Beicon();
        });
    }

    private static CompletableFuture<Huevo> freirHuevosAsync(int cantidad) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calentando la sartén para los huevos...");
            try {
                TimeUnit.SECONDS.sleep(3); // Simula la espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Rompiendo " + cantidad + " huevos");
            System.out.println("Cocinando los huevos...");
            try {
                TimeUnit.SECONDS.sleep(3); // Simula la espera
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Poniendo los huevos en el plato");
            return new Huevo();
        });
    }

    private static Cafe servirCafe() {
        System.out.println("Sirviendo café");
        return new Cafe();
    }
}
