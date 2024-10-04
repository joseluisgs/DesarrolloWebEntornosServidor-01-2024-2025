# De Java a C#: Un Tutorial Detallado

## Estructura de un Programa, `final` y Variables en Java y su Equivalente en C#

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

### C#
En C#, la unidad básica es también la clase. Aquí hay un ejemplo equivalente en un archivo C# (`.cs`):

```csharp
using System;

public class Program {
    public static void Main(string[] args) {
        Console.WriteLine("Hello, World!");

        // Declaración de variables
        const string readOnly = "Este es un valor inmutable"; // `const` para inmutabilidad
        int mutable = 10;  // Variable mutable
    }
}
```

### Notas
- `final` en Java es equivalente a `const` en C# para valores inmutables.
- Las variables regulares en Java son equivalentes a las variables regulares en C#.

## Manejo de Tipos Nulos

### Java
En Java, no se tiene un sistema de tipos nulos similar; se debe verificar manualmente si una referencia es nula.

```java
String nullableString = null;  // Puede ser nulo
Integer length = (nullableString != null) ? nullableString.length() : null;  // Safe call
int defaultLength = (nullableString != null) ? nullableString.length() : 0;  // Operador Elvis
```

### C#
C# tiene un sistema de tipos nulos más robusto a partir de C# 8.0.

```csharp
string? nullableString = null;  // Puede ser nulo
int? length = nullableString?.Length;  // Safe call (llamada segura)
int defaultLength = nullableString?.Length ?? 0;  // Operador Elvis (??)
```

### Notas
- En C#, `?` después del tipo indica que la variable puede ser nula.
- El operador `?.` permite hacer llamadas seguras en C#.
- El operador Elvis `??` se utiliza para proporcionar un valor por defecto en caso de que una expresión sea nula.

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

### C#

```csharp
public class Animal {
    private string name = "Animal";

    public string Name {
        get { return name; }
        set { name = value; }
    }

    public virtual void Sound() {
        Console.WriteLine("Some sound");
    }
}

public class Dog : Animal {
    public override void Sound() {
        Console.WriteLine("Bark");
    }
}

public interface IPet {
    void Play();
}

public class Cat : Animal, IPet {
    public override void Sound() {
        Console.WriteLine("Meow");
    }

    public void Play() {
        Console.WriteLine("Playing with ball");
    }
}
```

### Notas
- En C#, los getters y setters se definen dentro de las propiedades.
- `virtual` en C# permite que un método pueda ser sobrescrito en una clase derivada, similar a `@Override` en Java.
- Las interfaces en C# usan la palabra clave `interface` y se implementan usando `:`.

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

### C#

```csharp
using System;
using System.Collections.Generic;
using System.Linq;

public class FunctionalExample {
    public static void Main(string[] args) {
        List<int> numbers = new List<int> { 1, 2, 3, 4, 5 };
        List<int> filtered = numbers.Where(n => n % 2 == 0).ToList();
        Console.WriteLine(string.Join(", ", filtered));
        
        // Definición de lambdas
        Action<string> printMessage = message => Console.WriteLine(message);
        printMessage("Hello, World!");
        
        Func<int, int, int> sum = (a, b) => a + b;
        Console.WriteLine(sum(5, 3));
        
        // Función de orden superior
        List<int> greaterThanTwo = FilterUsingPredicate(numbers, n => n > 2);
        Console.WriteLine(string.Join(", ", greaterThanTwo));
    }

    public static List<T> FilterUsingPredicate<T>(List<T> list, Predicate<T> predicate) {
        return list.Where(predicate.Invoke).ToList();
    }
}
```

### Notas
- C# usa la sintaxis `delegate` o `Func<>` para definir lambdas.
- En C#, las lambdas se usan con la sintaxis `(parametros) => cuerpo`.
- C# tiene clases como `Action`, `Func` y `Predicate` en el paquete `System`.

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

### C#

```csharp
using System;
using System.Collections.Generic;
using System.Linq;

public class CollectionsExample {
    public static void Main(string[] args) {
        List<string> fruits = new List<string> { "Apple", "Banana", "Cherry" };
        List<string> upperCaseFruits = fruits.Select(fruit => fruit.ToUpper()).ToList();
        Console.WriteLine(string.Join(", ", upperCaseFruits));
    }
}
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

### C#

```csharp
using System;

public class ExceptionExample {
    public static void Main(string[] args) {
        try {
            int result = Divide(4, 0);
            Console.WriteLine(result);
        } catch (DivideByZeroException) {
            Console.WriteLine("Cannot divide by zero");
        }
    }

    public static int Divide(int a, int b) {
        return a / b;
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

### C#

```csharp
using System;
using System.IO;

public class FileExample {
    public static void Main(string[] args) {
        string text = File.ReadAllText("input.txt");
        Console.WriteLine(text);

        File.WriteAllText("output.txt", "Hello, World");
    }
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

### C#

```csharp
using System;
using System.Data;
using System.Data.SqlClient;

public class DatabaseExample {
    public static void Main(string[] args) {
        string connectionString = "Server=localhost;Database=testdb;User Id=username;Password=password;";

        using (SqlConnection connection = new SqlConnection(connectionString)) {
            connection.Open();
            using (SqlCommand command = new SqlCommand("SELECT * FROM users", connection)) {
                using (SqlDataReader reader = command.ExecuteReader()) {
                    while (reader.Read()) {
                        Console.WriteLine(reader["name"]);
                    }
                }
            }
        }
    }
}
```

## Consideraciones Adicionales

### Manejo de Nulos
- C# tiene un sistema de tipos nulos para prevenir `NullReferenceExceptions`. En Java, es importante manejar los posibles nulos manualmente.

### Coroutines en Kotlin vs. Async/Await en C#
- Kotlin tiene coroutines para programación asíncrona. C# utiliza `async` y `await`.

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

### Ejemplo de Async/Await en C#

```csharp
using System;
using System.Threading.Tasks;

public class AsyncExample {
    public static async Task Main(string[] args) {
        await Task.Run(async () => {
            await Task.Delay(1000);
            Console.WriteLine("World!");
        });
        Console.WriteLine("Hello");
    }
}
```
