CREATE TABLE IF NOT EXISTS "users"
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(50) UNIQUE ,
    password  VARCHAR(512),
    email     VARCHAR(128),
    role      VARCHAR(16) DEFAULT 'ROLE_USER',
    "enabled" BOOLEAN     DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS "notes"
(
    id             BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users (id),
    title          VARCHAR(128),
    content        VARCHAR(512)
);