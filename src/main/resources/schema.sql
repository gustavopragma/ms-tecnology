CREATE TABLE IF NOT EXISTS technologies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(90) NOT NULL
);

CREATE TABLE IF NOT EXISTS capability_technologies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_capability BIGINT NOT NULL,
    id_technology BIGINT NOT NULL,
    CONSTRAINT fk_capability_technologies_technology FOREIGN KEY (id_technology) REFERENCES technologies(id),
    CONSTRAINT uq_capability_technology UNIQUE (id_capability, id_technology)
);