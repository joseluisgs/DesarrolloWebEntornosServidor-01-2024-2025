# Figuras Asíncrono y Reactivo

En el directorio data tienes un varios csv de muestra de Figuras. Con la siguiente estructura:
- COD: En formato UUID v4
- NOMBRE: Cadena de caracteres
- MODELO: Solo tiene estos valores: MARVEL, DISNEY, ANIME, DEPORTE, MUSICA u OTROS
- PRECIO: Moneda con dos decimales.
- FECHA_LANZAMIENTO: Fecha en formato YYYY-MM-DD siguiendo ISO-8601

Antes de procesarlos ten en cuenta que puede haber errores en los campos, no te voy a decir donde pero descúbrelo y elige como solventarlo.

Debes hacer dos versiones, una de manera asíncrona y otra de manera reactiva. En ambos casos debes cargar los datos en una base de datos H2 en fichero, llamada "figuras", teniendo en cuenta que los datos de conexión deben leerse de un fichero de propiedades y que debe estar cacheada el pool de conexiones como el servicio.

***Vamos a trabajar totalmente asíncrono***

- La base de datos debe tener como pool Hikary con cache.
- Debemos trabajar con la tabla figura con la siguiente estructura:
   - ID: autonumérico y clave primaria.
  - cod: UUID, no nulo, y se puede generar automáticamente un valor por defecto si no se le pasa.
  - MyId: Long que puede será generado por el IdGenerator (sí, sé que no tiene sentido teniendo el otro, pero debemos practicar cosas ;))
  - nombre: cadena de caracteres de máximo 255.
  - modelo, solo puede ser MARVEL, DISNEY, ANIME, DEPORTE, MUSICA u OTROS
  - precio: un número real
  - fecha_lanzamiento: es un tipo de fecha.
  - created_at: marca de tiempo que toma por valor si no se le pasa la fecha completa actual al crearse la entidad
  - updated_at: marca de tiempo que toma por valor si no se le pasa la fecha completa al crearse la entidad o actualizarse.


Debes crear un repositorio CRUD totalmente completo para la gestión de Figuras. Además, de las operaciones CRUD normales, debes incluir una que se llame findByModelo, que deberá filtrar por modelo.

Además, debes usar un servicio totalmente asíncrono que haga uso de este repositorio e implemente una caché totalmente de acceso exclusivo de 10 elementos máximo.

Este servicio hará uso de excepciones o errores personalizados de no chequeadas si no se puede realizar las operaciones indicadas.

Este servicio tendrá un método backup que exporta los datos en JSON a una ruta pasada de manera asíncrona, solo si esta es válida, si no producirá una excepción personalizada y un método import para importarlos de manera asíncrona desde el CSV.

Para importar y exportar los datos, se recomienda hacer un servicio asíncrono de almacenamiento con los métodos de importar y exportar los datos y excepciones personalizadas.

Ten en cuenta que si aplicas en patrón Singleton, este tiene que estar protegido en entornos multihilo.

Además, tendremos un servicio de notificaciones que actuará mandando una notificación cuando se cree una figura, se actualice o se borre. En la notificación debe tener un tipo: CREACION, ACTUALIZACION o BORRADO, un mensaje y una fecha. 

Se debe mostrar un ejemplo de cada uno de los métodos del servicio en el main con los casos de ejecución correcta e incorrecta. Además, en el main, las salidas deben estar localizadas tanto en fechas como moneda a ESPAÑA.

Se debe ademas sacar las consultas en el main de manera asíncrona:
- Figura más cara.
- Media de precio de Figuras.
- Figuras agrupados por modelos.
- Número de Figuras por modelos.
- Precio medio de las Figuras de Marvel.
- Precio medio de las figuras agrupadas por modelos.
- Figuras que han sido lanzados en 2023.
- Número de Figuras de Naruto.
- Listado de Figuras de Naruto.

Finalmente se pide testear todos los caso correctos o incorrectos de los métodos de:
- Caché
- Repositorio
- Servicio de Almacenamiento
- Servicio de Notificaciones
- Servicio de Funkos
  
Se recomienda usar un Logger en todo el proceso.

***Vamos a trabajar totalmente reactivamente***

Se trata de repetir el proceso anterior pero de manera reactiva. Se debe usar Project Reactor/RxJava y R2DBC.

La base de datos debe tener como pool R2DBC con cache.

Tendremos que hacer un repositorio CRUD totalmente completo para la gestión de Figuras. Además, de las operaciones CRUD normales, debes incluir una que se llame findByModelo, que deberá filtrar por modelo.

Además, debes usar un servicio totalmente reactiva que haga uso de este repositorio e implemente una caché totalmente de acceso exclusivo de 10 elementos máximo.

Este servicio hará uso de excepciones o errores personalizados de no chequeadas si no se puede realizar las operaciones indicadas.

Todo lo demás es igual que en el caso asíncrono.

No se te olviden los test y documentar el código.

