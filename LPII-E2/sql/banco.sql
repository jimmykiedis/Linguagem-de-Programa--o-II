DROP DATABASE IF EXISTS banco;
CREATE DATABASE banco CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE banco;

CREATE TABLE seguradoras (
    nome VARCHAR(50) PRIMARY KEY,
    cidade VARCHAR(50),
    cobertura_percentual INT,
    possui_atendimento_24h BOOLEAN,
    forma_pagamento_preferencial VARCHAR(20)
);

CREATE TABLE sinistros (
    segurado VARCHAR(80) PRIMARY KEY,
    telefone VARCHAR(20),
    cidade VARCHAR(50),
    grau_monta VARCHAR(20),
    perda_total BOOLEAN
);

CREATE TABLE pecas (
    codigo INT PRIMARY KEY,
    nome VARCHAR(50),
    marca VARCHAR(20),
    preco DECIMAL(10,2),
    mao_obra_propria BOOLEAN,
    tipo_peca_mecanica VARCHAR(20),
    tipo_peca_lataria VARCHAR(20),
    dias_garantia INT,
    cor VARCHAR(20)
);

CREATE TABLE pecas_sinistros (
    peca_codigo INT NOT NULL,
    sinistro_segurado VARCHAR(80) NOT NULL,
    PRIMARY KEY (peca_codigo, sinistro_segurado),
    FOREIGN KEY (peca_codigo) REFERENCES pecas(codigo) ON DELETE CASCADE,
    FOREIGN KEY (sinistro_segurado) REFERENCES sinistros(segurado) ON DELETE CASCADE
);
