DROP TABLE IF EXISTS Seguradoras;
DROP TABLE IF EXISTS Pecas;

CREATE TABLE Seguradoras (
    nome VARCHAR(50) PRIMARY KEY,
    cidade VARCHAR(50),
    cobertura_percentual INT,
    possui_atendimento_24h BOOLEAN,
    forma_pagamento_preferencial VARCHAR(20)
);

CREATE TABLE Pecas (
    codigo INT PRIMARY KEY,
    sinistro_numero INT,
    nome VARCHAR(50),
    categoria VARCHAR(20),
    preco DECIMAL(10,2),
    tipo VARCHAR(20),
    dias_garantia_cor VARCHAR(20),
    mao_obra_propria BOOLEAN,
    FOREIGN KEY (sinistro_numero) REFERENCES Sinistros(numero)
);