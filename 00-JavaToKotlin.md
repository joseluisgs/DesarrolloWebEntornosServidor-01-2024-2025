- [De Java a Kotlin: Un Tutorial Detallado](#de-java-a-kotlin-un-tutorial-detallado)
  - [Estructura de un Programa, `final` y Variables en Java y su Equivalente en Kotlin](#estructura-de-un-programa-final-y-variables-en-java-y-su-equivalente-en-kotlin)
    - [Java](#java)
    - [Kotlin](#kotlin)
    - [Notas](#notas)
  - [Manejo de Tipos Nulos](#manejo-de-tipos-nulos)
    - [Java](#java-1)
    - [Kotlin](#kotlin-1)
    - [Notas](#notas-1)
  - [Programación Orientada a Objetos: Visibilidad, Propiedades y Métodos, Propiedades Finales, Herencia, Interfaces](#programación-orientada-a-objetos-visibilidad-propiedades-y-métodos-propiedades-finales-herencia-interfaces)
    - [Java](#java-2)
    - [Kotlin](#kotlin-2)
    - [Notas](#notas-2)
  - [Programación Funcional](#programación-funcional)
    - [Java](#java-3)
    - [Kotlin](#kotlin-3)
    - [Notas](#notas-3)
  - [Programación con Colecciones y Streams](#programación-con-colecciones-y-streams)
    - [Java](#java-4)
    - [Kotlin](#kotlin-4)
  - [Excepciones y Control de Errores](#excepciones-y-control-de-errores)
    - [Java](#java-5)
    - [Kotlin](#kotlin-5)
  - [Lectura y Escritura de Ficheros de Texto](#lectura-y-escritura-de-ficheros-de-texto)
    - [Java](#java-6)
    - [Kotlin](#kotlin-6)
  - [Acceso a Bases de Datos Relacionales](#acceso-a-bases-de-datos-relacionales)
    - [Java](#java-7)
    - [Kotlin](#kotlin-7)
  - [Consideraciones Adicionales](#consideraciones-adicionales)
    - [Manejo de Nulos](#manejo-de-nulos)
    - [Coroutines en Kotlin vs. Threads en Java](#coroutines-en-kotlin-vs-threads-en-java)
    - [Ejemplo de Threads en Java](#ejemplo-de-threads-en-java)
    - [Ejemplo de Coroutines en Kotlin](#ejemplo-de-coroutines-en-kotlin)


# De Java a Kotlin: Un Tutorial Detallado

## Estructura de un Programa, `final` y Variables en Java y su Equivalente en Kotlin

### Java
En Java, la unidad básica es la clase. Aquí hay un ejemplo de un programa básico:

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // Declaración de variables
        final String readOnly = "Este es un valor inmutable"; // `final` para inmutabilidad
        int mutable = 10;  // Variable mutable
    }
}
```

### Kotlin
En Kotlin, la unidad básica es el archivo Kotlin (`.kt`). Aquí hay un ejemplo de un programa equivalente:

```kotlin
fun main() {
    println("Hello, World!")
}

// Declaración de variables
val readOnly: String = "Este es un valor inmutable" // `val` para inmutabilidad
var mutable: Int = 10  // Variable mutable
```

### Notas
- `final` en Java es equivalente a `val` en Kotlin.
- Las variables regulares en Java son equivalentes a `var` en Kotlin.

## Manejo de Tipos Nulos

### Java
En Java, no se tiene un sistema de tipos nulos similar; se debe verificar manualmente si una referencia es nula.

```java
String nullableString = null;  // Puede ser nulo
Integer length = (nullableString != null) ? nullableString.length() : null;  // Safe call
int defaultLength = (nullableString != null) ? nullableString.length() : 0;  // Operador Elvis
```

### Kotlin
Kotlin tiene un sistema fuerte de tipos nulos para evitar `NullPointerException`.

```kotlin
val nullableString: String? = null  // Puede ser nulo
val length: Int? = nullableString?.length  // Safe call (llamada segura)
val defaultLength: Int = nullableString?.length ?: 0  // Operador Elvis (?:)
```

### Notas
- En Kotlin, `?` después del tipo indica que la variable puede ser nula.
- El operador `?.` permite hacer llamadas seguras en Kotlin.
- El operador Elvis `?:` se utiliza para proporcionar un valor por defecto en caso de que una expresión sea nula.

## Programación Orientada a Objetos: Visibilidad, Propiedades y Métodos, Propiedades Finales, Herencia, Interfaces

### Java

```java
class Animal {
    private String name = "Animal";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Bark");
    }
}

interface Pet {
    void play();
}

class Cat extends Animal implements Pet {
    @Override
    public void sound() {
        System.out.println("Meow");
    }

    @Override
    public void play() {
        System.out.println("Playing with ball");
    }
}
```

### Kotlin
```kotlin
open class Animal {
    var name: String = "Animal"
    open fun sound() {
        println("Some sound")
    }
}

class Dog : Animal() {
    override fun sound() {
        println("Bark")
    }
}

interface Pet {
    fun play()
}

class Cat : Animal(), Pet {
    override fun sound() {
        println("Meow")
    }

    override fun play() {
        println("Playing with ball")
    }
}
```

### Notas
- `open` en Kotlin permite que una clase o un método pueda ser sobrescrito. En Java, todas las clases y métodos son extensibles por defecto.
- Las propiedades privadas en Java requieren métodos `get` y `set` para acceso, que pueden ser generados automáticamente en Kotlin.

## Programación Funcional

### Java

```java
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionalExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> filtered = numbers.stream()
                                        .filter(n -> n % 2 == 0)
                                        .collect(Collectors.toList());
        System.out.println(filtered);
        
        // Definición de lambdas
        Consumer<String> printMessage = message -> System.out.println(message);
        printMessage.accept("Hello, World!");
        
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        System.out.println(sum.apply(5, 3));
        
        // Función de orden superior
        List<Integer> greaterThanTwo = filterUsingPredicate(numbers, n -> n > 2);
        System.out.println(greaterThanTwo);
    }

    public static <T> List<T> filterUsingPredicate(List<T> list, Predicate<T> predicate) {
        return list.stream().filter(predicate).collect(Collectors.toList());
    }
}
```

### Kotlin
```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
val filtered = numbers.filter { it % 2 == 0 }
println(filtered)

// Definición de lambdas
val printMessage = { message: String -> println(message) }
val sum: (Int, Int) -> Int = { a, b -> a + b }

// Función de orden superior
fun <T> Iterable<T>.filterUsingPredicate(predicate: (T) -> Boolean): List<T> {
    return this.filter(predicate)
}

val greaterThanTwo = numbers.filterUsingPredicate { it > 2 }
println(greaterThanTwo)
```

### Notas
- Kotlin usa la sintaxis `{ parametros -> cuerpo }` para definir lambdas.
- En Java, las lambdas se introdujeron en Java 8 y usan la sintaxis `(parametros) -> cuerpo`.
- Java tiene clases funcionales como `Consumer`, `BiFunction`, y `Predicate` en el paquete `java.util.function`.

## Programación con Colecciones y Streams

### Java
```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionsExample {
    public static void main(String[] args) {
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
        List<String> upperCaseFruits = fruits.stream()
                                             .map(String::toUpperCase)
                                             .collect(Collectors.toList());
        System.out.println(upperCaseFruits);
    }
}
```

### Kotlin
```kotlin
val fruits = listOf("Apple", "Banana", "Cherry")
val upperCaseFruits = fruits.map { it.toUpperCase() }
println(upperCaseFruits)
```

## Excepciones y Control de Errores

### Java
```java
public class ExceptionExample {
    public static void main(String[] args) {
        try {
            int result = divide(4, 0);
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        }
    }

    public static int divide(int a, int b) {
        return a / b;
    }
}
```

### Kotlin
```kotlin
fun divide(a: Int, b: Int): Int {
    return try {
        a / b
    } catch (e: ArithmeticException) {
        println("Cannot divide by zero")
        0
    }
}
```

## Lectura y Escritura de Ficheros de Texto

### Java
```java
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileExample {
    public static void main(String[] args) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get("input.txt")));
        System.out.println(text);

        Files.write(Paths.get("output.txt"), "Hello, World".getBytes());
    }
}
```

### Kotlin
```kotlin
import java.io.File

fun main() {
    val text = File("input.txt").readText()
    println(text)
    
    File("output.txt").writeText("Hello, World")
}
```

## Acceso a Bases de Datos Relacionales

### Java
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseExample {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "username", "password")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### Kotlin
```kotlin
import java.sql.DriverManager

fun main() {
    val connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "username", "password")
    connection.use {
        val statement = it.createStatement()
        val resultSet = statement.executeQuery("SELECT * FROM users")
        while (resultSet.next()) {
            println(resultSet.getString("name"))
        }
    }
}
```

## Consideraciones Adicionales

### Manejo de Nulos
- Kotlin tiene un sistema de tipos nulos para prevenir `NullPointerExceptions`. En Java, es importante manejar los posibles nulos manualmente.

### Coroutines en Kotlin vs. Threads en Java
- Kotlin tiene coroutines para programación asíncrona. Java utiliza `Thread` y `CompletableFuture`.

### Ejemplo de Threads en Java
```java
public class ThreadExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println("World!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
        System.out.println("Hello");
        thread.join();
    }
}
```

### Ejemplo de Coroutines en Kotlin
```kotlin
import kotlinx.coroutines.*

fun main() {
    runBlocking {
        launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
    }
}
```