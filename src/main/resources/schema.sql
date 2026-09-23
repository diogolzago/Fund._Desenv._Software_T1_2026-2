CREATE TABLE if NOT EXISTS autores (
    id              BIGSERIAL PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    data_nascimento DATE
);

CREATE TABLE IF NOT EXISTS livros(
    id              BIGSERIAL PRIMARY KEY,
    titulo          VARCHAR(100) NOT NULL,
    autor_id        BIGINT NOT NULL,
    ano             INT,
    CONSTRAINT fk_livros_autor FOREIGN KEY (autor_id) REFERENCES autores(id)
);

CREATE TABLE IF NOT EXISTS usuarios(
    id              BIGSERIAL PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS usuario_livros_lidos(
    usuario_id      BIGINT NOT NULL REFERENCES usuarios(id),
    livro_id        BIGINT NOT NULL REFERENCES livros(id),
    PRIMARY KEY (usuario_id, livro_id) 
);

INSERT INTO autores (id, nome, data_nascimento) VALUES
(1, 'Machado de Assis',  '1839-06-21'),
(2, 'Clarice Lispector', '1920-12-10'),
(3, 'Jorge Amado',       '1912-08-10'),
(4, 'Graciliano Ramos',  '1892-10-27')
ON CONFLICT (id) DO NOTHING;

INSERT INTO livros (id, titulo, autor_id, ano) VALUES
(1, 'Dom Casmurro',                     1, 1899),
(2, 'Memórias Póstumas de Brás Cubas',  1, 1881),
(3, 'A Hora da Estrela',                2, 1977),
(4, 'Capitães da Areia',                3, 1937),
(5, 'Gabriela, Cravo e Canela',         3, 1958),
(6, 'Vidas Secas',                      4, 1938)
ON CONFLICT (id) DO NOTHING;

