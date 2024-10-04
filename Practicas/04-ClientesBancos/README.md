# Clientes Bancos

## Objetivo
El objetivo de esta práctica es realizar un programa Java que permita gestionar los clientes de un banco. Sabiendo que un `Cliente` es un usuario que puede o no tener una o varias `Tarjetas` de crédito asociadas.

Un usuario tiene id, name, username y email.
Una tarjeta tiene asociado un número, titular, fecha de caducidad (YYYY-MM-DD)

Debemos hacer un servicio que permita hacer un CRUD sobre clientes, teniendo en cuenta que:
- Debe estar cacheeado siguiendo una política de caché LRU de un tamaño máximo determinado.
- Los usuarios son gestionados a través de una API REST mediante el endpoint: https://jsonplaceholder.typicode.com/users
- Las tarjetas son gestionadas en una base de datos PostgreSQL en un contenedor.
- Además, tendremos una base de datos local en SQLite que almacenará los datos de los clientes, teniendo una tasa de refresco que se indicará.

El servicio debe proporcionar errores o excepciones en caso de que no se pueda realizar alguna operación o exista algún problema con ellas.

Además: 
- Deberá validar los datos de usuarios y tarjetas antes de almacenarlos.
- Deberá importar y exportar datos en JSON de clientes completos.
- Deberá importar y exportar usuarios en CSV.
- Deberá importar y exportar tarjetas en CSV.
- Tendrá un sistema de notificaciones que informará sobre cualquier cambio en Clientes que se produzca.

Toda la configuración debe estar parametrizada en un fichero de configuración del tipo `application.properties` y `.env`

Se debe programar toda entrada y salida de información de manera asíncrona obligatoriamente  y solo en los casos que consideres y justifiques de manera reactiva.

Se recomienda usar un sistema de logs para registrar las operaciones realizadas.

No olvides que debe testearse completamente todos los componentes de la aplicación. 

Se tiene que documentar el código y el proyecto en un README.md

Se debe seguir GitFlow y hacer un PR por cada tarea.

Además se deberá desplegar toda la infraestructura con Docker y en el caso de nuestra app, usando un docker multi-stage build. Para los test con base de datos se debe usar TestContainers. Además la app debe estar publicada en DockerHub a partir de nuestro repositorio de GitHub.

## Entrega
Se ha de presentar una memoria de al menos 15 páginas explicando cada elemento de la arquitectura y los algoritmos propuestos para resolver el problema. 

Se debe hacer una presentación de 15 minutos en video de Youtube explicando la solución propuesta.

En clase se realizará una exposición de 20 minutos por grupo explicando la solución propuesta con un PowerPoint y ejecutar un ejemplo de uso de la aplicación.