# LPII - Checklist da Etapa 2 - LPII-E2

Checklist focado apenas no código do `LPII-E2`.

## 1. Estrutura do projeto

- [x] Existe a pasta `LPII-E2`.
- [x] Existe o diretório `src`.
- [x] Existe o diretório `sql`.
- [x] Existe o arquivo `pom.xml`.
- [ ] Existe o arquivo `nbactions.xml`.
- [x] Não há `src/main` nem `src/java` no projeto.

## 2. Persistência

- [x] Existe `src/persistência/BD.java`.
- [x] A classe `BD` usa o banco `banco`.
- [x] Há conexão e fechamento de conexão centralizados.

## 3. Entidades

- [x] Existe `src/entidades/Seguradora.java`.
- [x] Existe `src/entidades/Sinistro.java`.
- [x] Existe `src/entidades/Pecas.java`.

### Seguradora

- [x] Possui atributo-chave `nome`.
- [x] Possui atributo `cidade`.
- [x] Possui atributo numérico `cobertura_percentual`.
- [x] Possui atributo booleano `possui_atendimento_24h`.
- [x] Possui atributo enumerado `forma_pagamento_preferencial`.
- [x] Possui `getVisoes()` para ComboBox.
- [x] Possui `buscarSeguradora()`, `inserirSeguradora()`, `alterarSeguradora()` e `removerSeguradora()`.

### Sinistro

- [x] Possui atributo-chave `numero`.
- [x] Possui atributo `cliente`.
- [x] Possui atributo `telefone`.
- [x] Possui `getVisoes()` para ComboBox.
- [x] Possui `buscarSinistro()`, `inserirSinistro()`, `alterarSinistro()` e `removerSinistro()`.

### Pecas

- [x] Possui atributo-chave `codigo`.
- [x] Possui atributo `nome`.
- [x] Possui atributo enumerado `categoria`.
- [x] Possui atributo numérico `preco`.
- [x] Possui atributo enumerado `tipo`.
- [x] Possui atributo `cor`.
- [x] Possui atributo booleano `mao_obra_propria`.
- [x] Possui `getVisoes()` para ComboBox.
- [x] Possui `buscarPecas()`, `inserirPecas()`, `alterarPecas()` e `removerPecas()`.

## 4. Controladores

- [x] Existe `src/controles/ControladorCadastroSeguradoras.java`.
- [x] Existe `src/controles/ControladorCadastroSinistros.java`.
- [x] Existe `src/controles/ControladorCadastroPecas.java`.
- [x] Os controladores abrem suas respectivas janelas.
- [x] Os controladores validam existência antes de inserir, alterar e remover.

## 5. Interfaces

- [x] Existe `src/interfaces/JanelaSistema.java`.
- [x] Existe `src/interfaces/JanelaCadastroSeguradoras.java`.
- [x] Existe `src/interfaces/JanelaCadastroSinistros.java`.
- [x] Existe `src/interfaces/JanelaCadastroPecas.java`.
- [x] As janelas usam `JComboBox` para visões dos objetos cadastrados.
- [x] As janelas usam `JCheckBox` para atributos booleanos.
- [x] As janelas usam `ComboBox` para enumerados.
- [x] As janelas têm ações de inserir, alterar, consultar, remover e limpar.
- [x] A janela principal abre os cadastros disponíveis.

## 6. Regras de nomenclatura observadas no código

- [x] Pacotes com nomes em minúsculas.
- [x] Classes de entidades com primeira letra maiúscula.
- [x] Classes de controladores com prefixo `ControladorCadastro`.
- [x] Janelas com prefixo `JanelaCadastro`.
- [x] A janela principal usa `JanelaSistema`.
- [x] Métodos seguem padrão camelCase.

## 7. Funcionalidade ainda a revisar no código

- [ ] Conferir se os nomes das colunas no banco batem com os nomes usados nos `SELECT` e `UPDATE`.
- [ ] Confirmar a execução real das telas no fluxo completo.
- [ ] Validar se o projeto cobre exatamente todos os prints exigidos pela etapa.

## 8. Passos posteriores

- [ ] Gerar `spec.pdf`.
- [ ] Gerar `fontes.pdf`.
- [ ] Gerar `saida.pdf`.
- [ ] Gerar `banco.pdf`.
- [ ] Montar o arquivo `.zip` final.

