
SELECT 'CREATE DATABASE nombre_de_la_base_de_datos'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'personas');

DROP TABLE IF EXISTS "personas";

-- Cuiddado con las secuencias, si se borran se pierde el autoincremento, ponemos el start a 4 para que empiece en 4
-- Esto es así porque estamos insertando 4 personas de ejemplo
CREATE SEQUENCE personas_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 6 CACHE 1;

CREATE TABLE "public"."personas"
(
    "id"           bigint           DEFAULT nextval('personas_id_seq') NOT NULL,
    "nombre"       character varying(255),
    "uuid"         uuid                                                 NOT NULL,
    "created_at"   timestamp        DEFAULT CURRENT_TIMESTAMP           NOT NULL,
    "updated_at"   timestamp        DEFAULT CURRENT_TIMESTAMP           NOT NULL,
    CONSTRAINT "productos_pkey" PRIMARY KEY ("id"),
    CONSTRAINT "productos_uuid_key" UNIQUE ("uuid")
) WITH (oids = false);

INSERT INTO "personas" (id, nombre, uuid, created_at, updated_at)
VALUES  (1, 'Persona 1', 'd69cf3db-b77d-4181-b3cd-5ca8107fb6a9', '2024-10-02 11:43:24.722473', '2024-10-02 11:43:24.722473' ),
        (2, 'Persona 2', '19135792-b778-441f-871e-d6e6096e0ddc', '2024-10-02 11:43:24.722473', '2024-10-02 11:43:24.722473' ),
        (3, 'Persona 3', 'd69cf3db-b77d-4181-b3cd-5ca8107fb6a8', '2024-10-02 11:43:24.722473', '2024-10-02 11:43:24.722473' ),
        (4, 'Persona 4', '19135792-b778-441f-871e-d6e6096e0ddd', '2024-10-02 11:43:24.722473', '2024-10-02 11:43:24.722473' );