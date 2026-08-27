# LPII-E2

Projeto desenvolvido para a disciplina de Linguagem de Programação II, ministrada pelo professor Joivile Batista.

O trabalho foi implementado em Java 17, com MySQL 8.0.28 para persistência de dados, e tem como objetivo exercitar os conceitos de Programação Orientada a Objetos em Java, com a interface gráfica construída no Apache NetBeans.

## Sobre o projeto

Este repositório reúne a etapa 2 do trabalho, com foco em:

- cadastro de seguradoras;
- cadastro de sinistros;
- cadastro de peças;
- uso de entidades, controladores e interfaces gráficas;
- conexão centralizada com o banco de dados;
- apoio a ações de inserir, alterar, consultar, remover e limpar nos formulários.

O código principal da etapa está organizado no diretório `LPII-E2`, e a base de informações usada para conferir a estrutura do projeto pode ser encontrada em `Docs/Checklists e Revisões/LPII - Checklist da Etapa 2 - LPII-E2.md`.

## Tecnologias utilizadas

- Java 17
- Maven
- MySQL 8.0.28
- Apache NetBeans
- JDBC com `mysql-connector-java`

## Como baixar o MySQL Connector/J 8.0.28

O projeto usa o driver `mysql-connector-java-8.0.28`. Para baixá-lo, acesse a página oficial de arquivos do MySQL Connector/J:

- [MySQL Connector/J (Archived Versions)](https://downloads.mysql.com/archives/c-j/)

Na página, selecione:

- `Product Version`: `8.0.28`
- `Operating System`: `Platform Independent`

Depois, baixe o arquivo:

- `mysql-connector-java-8.0.28.zip`

Esse arquivo contém o driver JDBC usado pelo projeto.

### Como adicionar no NetBeans

1. Extraia o arquivo `mysql-connector-java-8.0.28.zip`.
2. No NetBeans, vá em `Services`.
3. Abra `Drivers`.
4. Localize o driver `MySQL (Connector/J driver)`.
5. Clique com o botão direito sobre ele.
6. Selecione `Customize`.
7. Clique em `Add`.
8. Procure e selecione o arquivo `mysql-connector-java-8.0.28.jar`.
9. Clique em `Connect using`.
10. Informe a senha do MySQL, no caso deste projeto: `admin`.
11. Clique em `Test Connection` para validar a conexão.

Depois disso, o NetBeans deve reconhecer o conector MySQL e permitir o acesso ao banco do projeto.

## Estrutura principal

- `LPII-E2/src/entidades` - classes de domínio do sistema
- `LPII-E2/src/controles` - controladores das telas
- `LPII-E2/src/interfaces` - janelas Swing do projeto
- `LPII-E2/src/persistência` - classe de conexão com o banco
- `LPII-E2/sql/banco.sql` - script base do banco do projeto
- `Docs/injector_banco_limpo.sql` - script para recriar e popular o banco com dados iniciais

## Como executar

1. Instale o Java 17 e o MySQL 8.0.28.
2. Baixe e configure o `mysql-connector-java-8.0.28.zip` conforme a seção anterior.
3. Abra o projeto `LPII-E2` no Apache NetBeans.
4. Confirme as credenciais de acesso ao banco na classe `LPII-E2/src/persistência/BD.java`.
5. Execute a classe principal `interfaces.JanelaSistema`.

## Como preparar o banco de dados

O arquivo `Docs/injector_banco_limpo.sql` pode ser usado para recriar o banco `banco` do zero e inserir os registros iniciais.

### Pelo cliente MySQL

1. Abra o MySQL Workbench, DBeaver ou outro cliente SQL.
2. Conecte-se ao servidor MySQL.
3. Execute o conteúdo de `Docs/injector_banco_limpo.sql`.
4. Aguarde o script concluir a criação do banco, das tabelas e dos dados de exemplo.

### Pelo terminal

Se preferir usar linha de comando, execute o script no seu ambiente MySQL:

```bash
mysql -u root -p < Docs/injector_banco_limpo.sql
```

Se o usuário do seu MySQL não for `root`, ajuste o comando conforme o seu ambiente.

No PowerShell do VS Code, você também pode usar este comando, apontando para o caminho correto do `mysql.exe`:

```powershell
Get-Content ".\Docs\injector_banco_limpo.sql" | & "C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p
```

Se o MySQL estiver instalado em outro diretório, substitua o caminho do `mysql.exe` pelo caminho real da sua instalação.

## O que o injector faz

O `Docs/injector_banco_limpo.sql`:

- remove o banco `banco` caso ele já exista;
- cria novamente o schema;
- cria as tabelas `seguradoras`, `sinistros`, `pecas` e `orcamentos`;
- insere dados iniciais para testes e demonstração do sistema.

Isso é útil quando você quer começar com uma base limpa e previsível, principalmente para validar as telas e os relacionamentos entre as entidades.

## Observações importantes

- O projeto foi pensado para ser aberto e executado no Apache NetBeans.
- A janela principal do sistema é `interfaces.JanelaSistema`.
- O banco usado pela aplicação é o schema `banco`.
- Antes de executar a aplicação, confira se o MySQL está rodando e se o usuário informado na classe de conexão tem permissão para acessar o schema.

## Referência da etapa

Para conferir os requisitos e o que a etapa precisa cobrir, consulte:

- `Docs/Checklists e Revisões/LPII - Checklist da Etapa 2 - LPII-E2.md`
