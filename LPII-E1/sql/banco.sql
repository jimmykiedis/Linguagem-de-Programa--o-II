DROP TABLE IF EXISTS Seguradoras;
DROP TABLE IF EXISTS Pecas;

CREATE TABLE Seguradoras (
    Nome VARCHAR(50) NOT NULL PRIMARY KEY,
    Cidade VARCHAR(50),
    CoberturaPercentual DECIMAL(5,2) NOT NULL
);

CREATE TABLE Pecas (
    Codigo INT NOT NULL PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Categoria VARCHAR(50) NOT NULL,
    Preco DECIMAL(10,2) NOT NULL,
    Tipo VARCHAR(50) NOT NULL,
    Cor VARCHAR(15) NOT NULL,
    MaoDeObra BOOLEAN NOT NULL
);

INSERT INTO Seguradoras (Nome, Cidade, CoberturaPercentual) VALUES
('Porto Seguro', 'Dourados', 90),
('Bradesco Seguros', 'Campo Grande', 80),
('SulAmérica', 'Ponta Porã', 90),
('Mapfre', 'Dourados', 85),
('Allianz', 'Campo Grande', 75),
('Tokio Marine', 'Ponta Porã', 90),
('Azos Seguros', 'São Paulo', 99),
('Liberty Seguros', 'Dourados', 85),
('Sompo Seguros', 'Maracajú', 75),
('HDI Seguros', 'Naviraí', 82);

INSERT INTO Pecas (Codigo, Nome, Categoria, Preco, Tipo, Cor, MaoDeObra) VALUES
(11, 'bloco', 'OEM', 245, 'motor', '90', FALSE),
(12, 'cabeçote', 'OEM', 250, 'motor', '180', FALSE),
(21, 'parachoque', 'Original', 200, 'externo', 'preto', FALSE),
(22, 'capô', 'Original', 180, 'externo', 'cinza', FALSE),
(31, 'painel', 'OEM', 180, 'interno', '180', TRUE),
(33, 'pedais', 'OEM', 220, 'interno', '180', TRUE),
(41, 'painel', 'OEM', 135, 'interno', 'cinza', TRUE),
(44, 'capô', 'OEM', 60, 'externo', 'cinza', TRUE),
(51, 'pistão', 'Genuína', 150, 'motor', '365', TRUE),
(55, 'biela', 'Genuína', 230, 'motor', '365', TRUE),
(61, 'bomba oleo', 'OEM', 150, 'motor', '180', FALSE),
(62, 'mola', 'OEM', 210, 'suspensão', '180', FALSE),
(71, 'retrovisor', 'Genuína', 210, 'externo', 'cinza', FALSE),
(72, 'porta dianteira', 'OEM', 170, 'externo', 'cinza', FALSE),
(81, 'radiador', 'Genuína', 160, 'motor', '365', TRUE),
(82, 'bieleta', 'Genuína', 140, 'suspensão', '365', TRUE),
(91, 'farol', 'Original', 140, 'externo', 'prata', FALSE),
(92, 'lanterna', 'OEM', 200, 'externo', 'preto', TRUE),
(101, 'barra estabilizadora', 'OEM', 190, 'suspensão', '180', FALSE),
(102, 'bucha estabilizadora', 'Original', 320, 'suspensão', '365', TRUE);
