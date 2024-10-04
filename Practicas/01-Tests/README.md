# Ejercicio de Tests

Queremos relizar una aplicación que gestione clientes de un banco.

Del cliente nos interesa saber:
- ID (único)
- Nombre
- DNI/NIF
- Cuenta Bancaria (compuesto por IBAN y saldo siempre mayor o igual a 0,00€)
- Tarjeta (compuesto por el número de la tarjeta y fecha de caducidad como MM/YY)

Debemos realizar un servicio de gestión de clientes con las operaciones de:
- Obtener todos
- Obtener en base al DNI/NIF
- Salvar
- Actualizar
- Borrar

Este servicio debe está cacheado con una caché como máximo de 5 elementos (no más). Además hará uso de un repositorio para salvar los clientes (en este caso no usaremos BBDD por lo que puedes simularlo en memoria). Por otro lado, hará uso de tres validadores, uno para DNI/NIF para saber si es correcto, otro para la cuenta basado en el IBAN (basado en su dígito de control) y su saldo; y otro para la tarjeta bancaria (número y fecha). Se debe tener en cuenta además que el nombre debe tener más de dos caracteres. 

Se deben lanzar excepciones o errores de dominio tipificados si no se encuentra el cliente (al buscarlo, actualizarlo o eliminarlo) o ante cualquier problema con alguno de los validadores. Los validadores devolverán el objeto validado si es correcto.

Se usará un logger configurado de la siguiente manera:
- Todos los mensajes de debug hacia arriba saldrán por pantalla.
- Todos los mensajes de error irán a un fichero de log, que se creará con la fecha de ejecución.

Se debe testear todo unitariamente y en los casos que se requieran usando tests con dobles (Mocks usando Mockito).

Cualquier duda se resolverá por Discord y en clase.

Se recomienda inyección de dependencias y diseño por composición aplicando SOLID.

## Entrega

Proyecto en clase con un 100 de cobertura con casos incorrectos e incorrectos y el uso de logger