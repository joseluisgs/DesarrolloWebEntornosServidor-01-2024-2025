# De Kotlin a C#: Un Tutorial Detallado

## Estructura de un Programa, `val` y `var` en Kotlin y su Equivalente en C#

### Kotlin
En Kotlin, la unidad básica es el archivo Kotlin (`.kt`). Aquí hay un ejemplo de un programa básico:

```kotlin
fun main() {
    println("Hello, World!")

    // Declaración de variables
    val readOnly: String = "Este es un valor inmutable" // `val` para inmutabilidad
    var mutable: Int = 10  // Variable mutable
}
```

### C#
En C#, la unidad básica es también la clase. Aquí hay un ejemplo equivalente en un archivo C# (`.cs`):

```csharp
using System;

public class Program
{
    public static void Main(string[] args)
    {
        Console.WriteLine("Hello, World!");

        // Declaración de variables
        const string readOnly = "Este es un valor inmutable"; // `const` para inmutabilidad
        int mutable = 10;  // Variable mutable
    }
}
```

### Notas
- `val` en Kotlin es equivalente a `const` en C# para valores inmutables.
- `var` en Kotlin es equivalente a las variables estándar en C#.

## Manejo de Tipos Nulos

### Kotlin
Kotlin tiene un sistema fuerte de tipos nulos para evitar `NullPointerException`.

```kotlin
val nullableString: String? = null  // Puede ser nulo
val length: Int? = nullableString?.length  // Safe call (llamada segura)
val defaultLength: Int = nullableString?.length ?: 0  // Operador Elvis (?:)
```

### C#
C# tiene un sistema de tipos nulos robusto a partir de C# 8.0.

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

### C#

```csharp
public class Animal
{
    private string name = "Animal";

    public string Name
    {
        get { return name; }
        set { name = value; }
    }

    public virtual void Sound()
    {
        Console.WriteLine("Some sound");
    }
}

public class Dog : Animal
{
    public override void Sound()
    {
        Console.WriteLine("Bark");
    }
}

public interface IPet
{
    void Play();
}

public class Cat : Animal, IPet
{
    public override void Sound()
    {
        Console.WriteLine("Meow");
    }

    public void Play()
    {
        Console.WriteLine("Playing with ball");
    }
}
```

### Notas
- En Kotlin, `open` permite que una clase o un método pueda ser sobrescrito. En C#, los métodos deben ser marcados con `virtual` para ser sobrescritos.
- Las interfaces en Kotlin usan la palabra clave `interface` y se implementan usando `:`, al igual que en C#.
- Las propiedades privadas en Kotlin requieren métodos `get` y `set` para acceso, al igual que en C#.

## Programación Funcional

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

### C#

```csharp
using System;
using System.Collections.Generic;
using System.Linq;

public class FunctionalExample
{
    public static void Main(string[] args)
    {
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

    public static List<T> FilterUsingPredicate<T>(List<T> list, Predicate<T> predicate)
    {
        return list.Where(predicate.Invoke).ToList();
    }
}
```

### Notas
- C# usa la sintaxis `delegate` o `Func<>` para definir lambdas.
- En C#, las lambdas se usan con la sintaxis `(parametros) => cuerpo`.
- C# tiene clases como `Action`, `Func` y `Predicate` en el paquete `System`.

## Programación con Colecciones y Streams

### Kotlin

```kotlin
val fruits = listOf("Apple", "Banana", "Cherry")
val upperCaseFruits = fruits.map { it.toUpperCase() }
println(upperCaseFruits)
```

### C#

```csharp
using System;
using System.Collections.Generic;
using System.Linq;

public class CollectionsExample
{
    public static void Main(string[] args)
    {
        List<string> fruits = new List<string> { "Apple", "Banana", "Cherry" };
        List<string> upperCaseFruits = fruits.Select(fruit => fruit.ToUpper()).ToList();
        Console.WriteLine(string.Join(", ", upperCaseFruits));
    }
}
```

## Excepciones y Control de Errores

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

### C#

```csharp
using System;

public class ExceptionExample
{
    public static void Main(string[] args)
    {
        try
        {
            int result = Divide(4, 0);
            Console.WriteLine(result);
        }
        catch (DivideByZeroException)
        {
            Console.WriteLine("Cannot divide by zero");
        }
    }

    public static int Divide(int a, int b)
    {
        return a / b;
    }
}
```

## Lectura y Escritura de Ficheros de Texto

### Kotlin

```kotlin
import java.io.File

fun main() {
    val text = File("input.txt").readText()
    println(text)
    
    File("output.txt").writeText("Hello, World")
}
```

### C#

```csharp
using System;
using System.IO;

public class FileExample
{
    public static void Main(string[] args)
    {
        string text = File.ReadAllText("input.txt");
        Console.WriteLine(text);

        File.WriteAllText("output.txt", "Hello, World");
    }
}
```

## Acceso a Bases de Datos Relacionales

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

### C#

```csharp
using System;
using System.Data;
using System.Data.SqlClient;

public class DatabaseExample
{
    public static void Main(string[] args)
    {
        string connectionString = "Server=localhost;Database=testdb;User Id=username;Password=password;";

        using (SqlConnection connection = new SqlConnection(connectionString))
        {
            connection.Open();
            using (SqlCommand command = new SqlCommand("SELECT * FROM users", connection))
            {
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
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
- C# tiene un sistema de tipos nulos para prevenir `NullReferenceExceptions`. En Kotlin, es importante usar los tipos nulos (`?` y `??`).

### Coroutines en Kotlin vs. Async/Await en C#
- Kotlin tiene coroutines para programación asíncrona. C# utiliza `async` y `await`.

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

### Ejemplo de Async/Await en C#

```csharp
using System;
using System.Threading.Tasks;

public class AsyncExample
{
    public static async Task Main(string[] args)
    {
        await Task.Run(async () =>
        {
            await Task.Delay(1000);
            Console.WriteLine("World!");
        });
        Console.WriteLine("Hello");
    }
}
```
