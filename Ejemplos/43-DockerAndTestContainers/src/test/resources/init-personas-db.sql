CREATE TABLE personas
(
    id         SERIAL PRIMARY KEY,
    uuid       UUID         NOT NULL,
    nombre     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP    NOT NULL
);

INSERT INTO personas (uuid, nombre, created_at, updated_at)
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Test 01', NOW(), NOW());