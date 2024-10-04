# Leer y procesar los ficheros de AMET.
Las columnas son:
Localidad, Provincia, Temperatura Máxima, Hora de Temperatura Máxima, Temperatura Mínima, Hora de la Temperatura Mínima y Precipitación.

El día viene en el propio nombre del fichero.

Nuestro programa debe trabajar para un numero "infinito" de ficheros, es decir, no sabemos cuantos ficheros vamos a tener que procesar, pero estos se pasan por argumentos.

Se debe poder ejecutar el programa de la siguiente forma:
```bash
java -jar amet.jar fichero1.csv fichero2.csv fichero3.csv ...
```

Debes aseguarte que los ficheros tiene la nomeclatura correcta, es decir, que el nombre del fichero es una fecha en formato "yyyymmdd.csv". Además, ten cuidado a leerlos, por su condificación, debes ajustar la codificación para que salgan bien al leerlos.

**SE DEBE USAR LA ASINCRONIA EN TODO MOMENTO QUE SEA POSIBLE**

1. Crear un CRUD completo para mediciones en una base de datos en fichero tipo H2 o SQLite. Los datos de conexión deben estar encapsulados en un manejador y leídos de un fichero de propiedades o de entorno.

2. Obtener las siguiente información tras recuperar los datos de la base de datos y mostrar por pantalla, usando la api de colecciones en Java o Kotlin:
  -  ¿Dónde se dio la temperatura máxima y mínima total en cada uno de los días?.
  -  Máxima temperatura agrupado por provincias y día.
  -  Mínima temperatura agrupado por provincias y día.
  -  Medía de temperatura agrupado por provincias y día.
  -  Precipitación máxima por días y dónde se dio.
  -  Precipitación media por provincias y día.
  -  Lugares donde ha llovido agrupado por provincias y día.
  -  Lugar donde más ha llovido.
  -  Datos de las provincia de Madrid (debe funcionar para cualquier provincia, ver abajo)
     -  Por cada día:
        -  Temperatura máxima, mínima y dónde ha sido.
        -  Temperatura media máxima.
        -  Temperatura media mínima.
        -  Precipitación máxima y dónde ha sido.
        -  Precipitación media.


3. Exportar los datos de una provincia dada (por ejemplo Madrid) a un fichero json llamado "provincia.json".

Debes resolver este problema dos veces:
- Kotlin y conrrutinas
- Java y CompletableFuture

No puedes usar ninguna librería para el manejo de las bases de datos. Debes hacerlo a mano (no uses el manejador del profe).

Usa el logger siempre que puedas.

***RECUERDA QUE SIN TEST NO ES VALIDO***

***Fecha de entrega 27/09/2024 en clase.***

Dudas por Discord
