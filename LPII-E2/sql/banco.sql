CREATE DATABASE IF NOT EXISTS banco
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE banco;

DROP TABLE IF EXISTS pecas;
DROP TABLE IF EXISTS sinistros;
DROP TABLE IF EXISTS seguradoras;

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
    sinistro_segurado VARCHAR(80),
    nome VARCHAR(50),
    marca VARCHAR(20),
    preco DECIMAL(10,2),
    mao_obra_propria BOOLEAN,
    tipo_registro VARCHAR(20),
    tipo_peca_carro VARCHAR(20),
    tipo_peca_moto VARCHAR(20),
    dias_garantia INT,
    cor VARCHAR(20),
    FOREIGN KEY (sinistro_segurado) REFERENCES sinistros(segurado)
);
