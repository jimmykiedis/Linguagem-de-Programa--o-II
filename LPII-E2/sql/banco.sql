DROP TABLE IF EXISTS orcamentos;
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

CREATE TABLE orcamentos (
    data DATE,
    sinistro_segurado VARCHAR(80),
    seguradora_nome VARCHAR(50),
    PRIMARY KEY (sinistro_segurado, seguradora_nome),
    FOREIGN KEY (sinistro_segurado) REFERENCES sinistros(segurado),
    FOREIGN KEY (seguradora_nome) REFERENCES seguradoras(nome)
);

INSERT INTO seguradoras (nome, cidade, cobertura_percentual, possui_atendimento_24h, forma_pagamento_preferencial) VALUES
('Porto Seguro', 'Dourados', 90, TRUE, 'boleto'),
('Bradesco Seguros', 'Campo Grande', 80, TRUE, 'debito_automatico'),
('SulAmérica', 'Ponta Porã', 90, FALSE, 'pix'),
('Mapfre', 'Dourados', 85, TRUE, 'cartao'),
('Allianz', 'Campo Grande', 75, FALSE, 'boleto'),
('Tokio Marine', 'Ponta Porã', 90, TRUE, 'pix'),
('Azos Seguros', 'São Paulo', 99, TRUE, 'debito_automatico'),
('Liberty Seguros', 'Dourados', 85, FALSE, 'boleto'),
('Sompo Seguros', 'Maracajú', 75, FALSE, 'cartao'),
('HDI Seguros', 'Naviraí', 82, TRUE, 'pix');

INSERT INTO sinistros (segurado, telefone, cidade, grau_monta, perda_total) VALUES
('Leon', '67 99888-1100', 'Dourados', 'grande', FALSE),
('Scott', '67 98988-1101', 'Campo Grande', 'media', FALSE),
('Kennedy', '67 96898-1000', 'Ponta Porã', 'grande', TRUE),
('Chris', '67 90898-0100', 'Dourados', 'media', FALSE),
('Redfield', '66 91681-1198', 'Naviraí', 'grande', TRUE),
('Jill', '67 99234-8811', 'Campo Grande', 'pequena', FALSE),
('Claire', '67 98112-2290', 'Dourados', 'media', FALSE),
('Ada', '65 99677-4400', 'Ponta Porã', 'pequena', TRUE),
('Rebecca', '67 99123-4455', 'Maracajú', 'pequena', FALSE),
('Carlos', '67 98455-7712', 'Naviraí', 'media', FALSE);

INSERT INTO pecas (
    codigo,
    sinistro_segurado,
    nome,
    marca,
    preco,
    mao_obra_propria,
    tipo_registro,
    tipo_peca_carro,
    tipo_peca_moto,
    dias_garantia,
    cor
) VALUES
(11, 'Leon', 'Bloco do motor', 'oem', 245, FALSE, 'mecanica', NULL, 'cabecote', 180, NULL),
(12, 'Leon', 'Cabeçote', 'oem', 250, FALSE, 'mecanica', NULL, 'cabecote', 180, NULL),
(21, 'Scott', 'Parachoques dianteiro', 'original', 200, FALSE, 'lataria', 'parachoques', NULL, NULL, 'preto'),
(22, 'Scott', 'Capo', 'original', 180, FALSE, 'lataria', 'capo', NULL, NULL, 'cinza'),
(31, 'Kennedy', 'Pistao', 'oem', 180, TRUE, 'mecanica', NULL, 'pistao', 120, NULL),
(33, 'Kennedy', 'Biela', 'oem', 220, TRUE, 'mecanica', NULL, 'biela', 120, NULL),
(41, 'Chris', 'Painel frontal', 'oem', 135, TRUE, 'lataria', 'capo', NULL, NULL, 'cinza'),
(44, 'Chris', 'Porta dianteira', 'oem', 60, TRUE, 'lataria', 'portas', NULL, NULL, 'cinza'),
(51, 'Redfield', 'Escapamento', 'genuina', 150, TRUE, 'mecanica', NULL, 'escapamento', 365, NULL),
(55, 'Redfield', 'Biela reforcada', 'genuina', 230, TRUE, 'mecanica', NULL, 'biela', 365, NULL),
(61, 'Jill', 'Bomba de oleo', 'oem', 150, FALSE, 'mecanica', NULL, 'cabecote', 180, NULL),
(62, 'Jill', 'Mola', 'oem', 210, FALSE, 'mecanica', NULL, 'cabecote', 180, NULL),
(71, 'Claire', 'Retrovisor', 'genuina', 210, FALSE, 'lataria', 'farol', NULL, NULL, 'cinza'),
(72, 'Claire', 'Lanterna traseira', 'oem', 170, FALSE, 'lataria', 'lanternas', NULL, NULL, 'cinza'),
(81, 'Ada', 'Radiador', 'genuina', 160, TRUE, 'mecanica', NULL, 'cabecote', 365, NULL),
(82, 'Ada', 'Bieleta', 'genuina', 140, TRUE, 'mecanica', NULL, 'cabecote', 365, NULL),
(91, 'Rebecca', 'Farol', 'original', 140, FALSE, 'lataria', 'farol', NULL, NULL, 'prata'),
(92, 'Rebecca', 'Lanterna', 'oem', 200, TRUE, 'lataria', 'lanternas', NULL, NULL, 'preto'),
(101, 'Carlos', 'Barra estabilizadora', 'oem', 190, FALSE, 'mecanica', NULL, 'biela', 180, NULL),
(102, 'Carlos', 'Bucha estabilizadora', 'original', 320, TRUE, 'mecanica', NULL, 'biela', 365, NULL);

INSERT INTO orcamentos (data, sinistro_segurado, seguradora_nome) VALUES
('2026-06-22', 'Leon', 'Porto Seguro'),
('2026-06-23', 'Scott', 'Bradesco Seguros'),
('2026-06-24', 'Kennedy', 'SulAmérica'),
('2026-06-25', 'Chris', 'Mapfre'),
('2026-06-26', 'Redfield', 'Allianz'),
('2026-06-27', 'Jill', 'Tokio Marine'),
('2026-06-28', 'Claire', 'Azos Seguros'),
('2026-06-29', 'Ada', 'Liberty Seguros'),
('2026-06-30', 'Rebecca', 'Sompo Seguros'),
('2026-07-01', 'Carlos', 'HDI Seguros');
