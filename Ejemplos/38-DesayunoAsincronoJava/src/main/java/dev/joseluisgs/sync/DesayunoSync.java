package dev.joseluisgs.sync;

import dev.joseluisgs.alimentos.*;

import java.util.concurrent.TimeUnit;

public class DesayunoSync {

    public static void main(String[] args) throws InterruptedException {

        var timeInit = System.currentTimeMillis();

        var taza = servirCafe();
        System.out.println("El café está listo");

        var huevos = freirHuevos(2);
        System.out.println("Los huevos están listos");

        var beicon = freirBeicon(3);
        System.out.println("El beicon está listo");

        var tostada = tostarPan(2);
        ponerMantequilla(tostada);
        ponerMermelada(tostada);
        System.out.println("Las tostadas están listas");

        var zumoNaranja = servirZumoNaranja();
        System.out.println("El zumo de naranja está listo");
        System.out.println("¡El desayuno está listo!");

        var timeEnd = System.currentTimeMillis();

        System.out.println("Tiempo total: " + (timeEnd - timeInit) + " ms");
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

    private static Tostada tostarPan(int rebanadas) {
        for (int rebanada = 0; rebanada < rebanadas; rebanada++) {
            System.out.println("Poniendo una rebanada de pan en la tostadora");
        }
        System.out.println("Comenzando a tostar...");
        try {
            TimeUnit.SECONDS.sleep(3); // Simula la espera
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sacando la tostada de la tostadora");

        return new Tostada();
    }

    private static Beicon freirBeicon(int lonchas) throws InterruptedException {
        System.out.println("Poniendo " + lonchas + " lonchas de beicon en la sartén");
        System.out.println("Cocinando la primera loncha del beicon...");
        try {
            TimeUnit.SECONDS.sleep(3); // Simula la        } catch (InterruptedException e) {
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
        System.out.println("Poniendo el baicon en el plato");

        return new Beicon();
    }

    private static Huevo freirHuevos(int cantidad) {
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
    }

    private static Cafe servirCafe() {
        System.out.println("Sirviendo café");
        return new Cafe();
    }
}
