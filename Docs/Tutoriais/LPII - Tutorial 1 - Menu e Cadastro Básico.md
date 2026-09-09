# LPII — Tutorial 1 — Menu do Sistema e Cadastro Básico
## Guia de Estudo Completo (Texto Original + Observações do Claude)

> ⚠️ **NOTA PARA OUTRAS IAs / OUTROS MODELOS QUE VENHAM A LER ESTE ARQUIVO:**
> Este documento corresponde ao material da **Etapa 1** da disciplina de Linguagem de Programação II (LPII), especificamente ao **Tutorial 1 — "Menu e Cadastro Básico"**, do curso de Sistemas de Informação da FACET/UFGD (Prof. Joinvile Batista Junior).
>
> Há, neste tutorial, **trechos de código-fonte (ex.: a classe `TesteBD` completa da Seção 1) que foram usados apenas como teste inicial de conexão com o banco de dados** e **não fazem parte da arquitetura final do projeto** (pacotes `persistência`, `entidades`, `interfaces`, `controles`) que é efetivamente desenvolvida a partir da Seção 2 em diante. Ou seja: **nem todo código deste arquivo foi aproveitado ou é necessário para a Etapa 2** do desenvolvimento do sistema "Clube de Amigos do Cinema". Caso este material seja usado como contexto/referência para dar continuidade ao projeto (Etapa 2, 3, 4 etc.), **não assuma que todo trecho aqui precisa ser reaproveitado literalmente** — é necessário avaliar, a cada novo tutorial, o que efetivamente compõe a base do projeto em evolução (`persistência.BD`, `entidades.Amigo`, `entidades.Ator`, `interfaces.JanelaAmigosCinema`, `interfaces.JanelaCadastroAmigos`, `controles.ControladorCadastroAmigos`) e o que foi apenas um exemplo isolado e descartável (`controle.TesteBD`).
>
> Este arquivo é um **material de estudo derivado**, não o tutorial oficial. O texto original do professor foi mantido integralmente; os blocos identificados como **"Observação do Claude"**, **"Esclarecimento do Claude"**, **"💡 O que o professor está tentando ensinar"** e **"🎯 Em resumo"** são acréscimos explicativos feitos por uma IA (Claude, da Anthropic) e não fazem parte do texto do professor.

---

## Como ler este documento

- Todo texto **sem indentação em citação** e todo bloco de código reproduzido tal como no PDF é **conteúdo original do tutorial**, sem alterações.
- Blocos como:
  > **Observação do Claude:** ...
  > **Esclarecimento do Claude:** ...
  > **💡 O que o professor está tentando ensinar:** ...
  > **🔎 Esclarecimento:** ...
  > **🎯 Em resumo:** ...

  são **acréscimos meus (Claude)**, claramente identificados, para ajudar na compreensão.
- Numeração de páginas do PDF original foi preservada como referência (ex.: `[p. 1/22]`).

---

## [p. 1/22] 1 – Instalando Softwares para Desenvolvimento e Testando Acesso ao Banco

> Nesta disciplina é desenvolvida de forma incremental uma aplicação desktop, que executará no seu computador sem acesso à web. A aplicação terá uma interface gráfica e terá acesso ao banco de dados relacional MySQL para persistência dos dados informados pelos usuários.
>
> Instale a versão mais atual do banco de dados relacional MySQL (MySQL Server 8.0).
> - pesquise: MySQL Community Downloads
>   - site: https://dev.mysql.com/downloads/
> - baixe e instale: mysql-installer-web-community-8.0.28.0
> - instale versão para desenvolvimento
>   - customize a instalação desabilitando pacotes para Visual Studio
>   - para ficar compatível com os tutoriais do LPII, forneça como senha: admin
> - baixe conector java para JDBC (Connector/J 8.0.28)
>   - baixe o executável: mysql-connector-java-8.0.28.jar
>   - armazene em uma pasta por posterior vinculação no NetBeans

> **💡 O que o professor está tentando ensinar:**
> Antes de programar qualquer coisa, é preciso preparar o ambiente: (1) ter um servidor de banco de dados MySQL rodando na máquina, e (2) ter o "driver JDBC" (o arquivo `.jar` do Connector/J), que é a peça de software que permite a um programa Java "falar" com o MySQL usando o protocolo JDBC (Java Database Connectivity). Sem esse `.jar`, o Java simplesmente não sabe como se conectar ao MySQL.

> **🔎 Esclarecimento:**
> A senha "admin" definida aqui para o usuário `root` do MySQL será usada depois, no código Java, na constante `SENHA`. Isso é importante: o professor está padronizando o ambiente de todos os alunos para que os tutoriais funcionem sem alterações (todo mundo com a mesma senha `admin` para `root`).

> A seguir, será ilustrado um teste de acesso ao banco de dados relacional MySQL.
>
> Passo 1 : Implantação e teste de conecxão do NetBeans com o conector MySQL no NetBeans
> - para acessar o banco de dados MySQL
>   - Services – Drivers – MySQL (Connector/J driver)
>     - mouse botão direito
> - Customize – Add
>   - procure localização do conector Java: mysql-connector-java-8.0.28.jar
> - Connect using …
>   - informe a senha: admin
>   - Test Connection

> **Observação do Claude:**
> O texto original tem uma pequena inconsistência de digitação: "conecxão" (em vez de "conexão"). Mantive exatamente como está no PDF, sem corrigir, conforme solicitado — apenas destaco aqui que é um erro de digitação do documento original, não um termo técnico novo.

> Passo 2 : Edite o script SQL de criação da tabela Pessoas e armazene um diretório sql (criar) no projeto

```sql
DROP TABLE IF EXISTS Pessoas;
CREATE TABLE Pessoas (
 CPF VARCHAR(15) NOT NULL PRIMARY KEY,
 Nome VARCHAR(50) NOT NULL);
```

> **Esclarecimento do Claude — linha a linha:**
> - `DROP TABLE IF EXISTS Pessoas;` → Remove a tabela `Pessoas` do banco, **caso ela já exista**. Isso evita erro ao tentar criar uma tabela que já existe (útil para poder executar o script várias vezes durante os testes, sempre "do zero").
> - `CREATE TABLE Pessoas (...)` → Cria a tabela `Pessoas` com duas colunas:
>   - `CPF VARCHAR(15) NOT NULL PRIMARY KEY` → coluna de texto (até 15 caracteres, cabendo o CPF formatado com pontos e hífen), que não pode ficar vazia (`NOT NULL`) e é a **chave primária** da tabela — ou seja, o valor que identifica unicamente cada linha (não pode haver dois registros com o mesmo CPF).
>   - `Nome VARCHAR(50) NOT NULL` → coluna de texto (até 50 caracteres) também obrigatória.
> - Se essas linhas fossem removidas, não haveria tabela para inserir/consultar dados — o restante do tutorial (que insere e lê registros de `Pessoas`) falharia com erro de "tabela não encontrada".

> **🎯 Em resumo:**
> Você deveria compreender, deste trecho, que **antes de qualquer código Java rodar, é preciso que a tabela já exista no banco de dados**, criada via um script SQL simples com `DROP TABLE` (por segurança/repetibilidade) seguido de `CREATE TABLE` definindo colunas, tipos e chave primária.

---

## [p. 2/22] Passo 3 : Criação da Base de Dados para Teste

> - NetBeans → Serviços → Servidor MySQL → Conectar
>   - informe senha da instalação do MySQL se solicitado
> - Servidor MySQL → Criar Banco de Dados
>   - Novo Nome do Banco de Dados : teste
> - Servidor MySQL → teste → Conectar
> - jdbc:mysql://localhost:3306/teste → teste → Tabelas → Executar Comando
>   - copiar e colar script de criação da tabela Pessoas
>   - executar utilizando o ícone

> **💡 O que o professor está tentando ensinar:**
> Este passo mostra como usar a interface gráfica do NetBeans para: (1) criar um banco de dados chamado `teste` dentro do servidor MySQL, e (2) executar o script SQL do passo anterior dentro desse banco, criando a tabela `Pessoas`. É basicamente a "interface visual" equivalente a digitar comandos SQL direto no terminal do MySQL.

> Passo 4 : Crie o projeto TesteBD no NetBeans
> - NetBeans → Arquivo → Novo Projeto
>   - Escolher Projeto
>     - Categorias : Java com Maven
>     - Projetos : Aplicação Java
>   - Nome e Localização
>     - Nome do Projeto : TesteBD
>     - Localização do Projeto : escolhar um bom diretório para armazenar seus projetos
>     - Criar Classe Principal : controle.TesteBD

> **Observação do Claude:**
> Há outro pequeno erro de digitação no original: "escolhar" (provavelmente "escolher"). Mantive fielmente sem corrigir.

> **🔎 Esclarecimento:**
> `controle.TesteBD` significa: pacote `controle`, classe `TesteBD`. Isso já cria automaticamente, no NetBeans, um arquivo `TesteBD.java` dentro de uma pasta/pacote chamado `controle`, com uma classe pública chamada `TesteBD` contendo um método `main` vazio — que será preenchido nos passos seguintes.

> Passo 5: Adicione o conector Java no arquivo de dependências do projeto (pom.xml)
> - após a tag `</properties>` acrescente a dependência do conector mysql caracterizando a sua versão:

```xml
<dependencies>
  <dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
  </dependency>
</dependencies>
```

> **Esclarecimento do Claude:**
> Este é um projeto **Maven** (ferramenta de gerenciamento de dependências/build do Java). O arquivo `pom.xml` descreve quais bibliotecas externas o projeto precisa. Ao declarar essa dependência (`groupId`, `artifactId`, `version`), o Maven baixa automaticamente o `.jar` do Connector/J e o disponibiliza para o código Java usar as classes de `java.sql.*` com o driver MySQL — é uma alternativa mais moderna e automatizada a "baixar o `.jar` manualmente e vincular no projeto", que era o que se fazia no Passo 1 (para projetos sem Maven).

> **🎯 Em resumo:**
> Você deveria compreender que existem duas formas de disponibilizar o driver JDBC do MySQL num projeto: manualmente (vinculando o `.jar` — Passo 1) ou via Maven, declarando a dependência no `pom.xml` (Passo 5). O projeto `TesteBD`, por ser "Java com Maven", usa a segunda forma.

> Agora, você colocará o código do projeto TesteBD no NetBeans, para testar uma a inserção e a leitura em uma tabela no banco de dados.

> **Observação do Claude:**
> "para testar uma a inserção" no original — provavelmente um erro de digitação para "para testar uma inserção" ou "para testar a inserção". Preservado como está.

### Classe TesteBD : importação e atributos

```java
package controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TesteBD {
    static final String URL_BD = "jdbc:mysql://localhost:3306/teste";
    static final String USUÁRIO = "root";
    static final String SENHA = "admin";
    private static Connection conexão = null;
    private static Statement comando = null;
```

> **Esclarecimento do Claude — linha a linha:**
> - `package controle;` → Declara que esta classe pertence ao pacote `controle` (bate com o que foi definido ao criar o projeto no Passo 4).
> - `import java.sql.Connection;` → Importa a interface `Connection`, que representa uma **conexão ativa** com o banco de dados.
> - `import java.sql.DriverManager;` → Importa `DriverManager`, uma classe utilitária que **cria conexões** (`getConnection`) usando o driver JDBC correto, de acordo com a URL fornecida.
> - `import java.sql.ResultSet;` → Importa `ResultSet`, que representa o **resultado de uma consulta SQL** (um "cursor" percorrendo linhas retornadas por um `SELECT`).
> - `import java.sql.SQLException;` → Importa a exceção lançada quando algo dá errado numa operação de banco de dados (ex.: erro de sintaxe SQL, conexão perdida, etc.).
> - `import java.sql.Statement;` → Importa `Statement`, objeto usado para **enviar comandos SQL** ao banco (sem parâmetros — diferente de `PreparedStatement`, que aparece mais adiante no tutorial, na Seção 5).
> - `public class TesteBD {` → Início da declaração da classe pública `TesteBD`.
> - `static final String URL_BD = "jdbc:mysql://localhost:3306/teste";` → Constante (`static final`, ou seja, um único valor compartilhado por toda a classe e que não pode ser alterado) com a **URL de conexão JDBC**: protocolo `jdbc:mysql://`, servidor `localhost`, porta `3306` (porta padrão do MySQL) e nome do banco `teste` (o banco criado no Passo 3).
> - `static final String USUÁRIO = "root";` → Nome de usuário do MySQL usado para conectar (usuário administrador padrão do MySQL).
> - `static final String SENHA = "admin";` → Senha correspondente, definida na instalação do MySQL (Seção 1).
> - `private static Connection conexão = null;` → Variável estática (compartilhada pela classe, não por instância) que vai guardar o objeto de conexão. Inicialmente `null` porque a conexão ainda não foi criada.
> - `private static Statement comando = null;` → Variável estática que vai guardar o objeto usado para executar comandos SQL. Também inicia como `null`.

> **🔎 Esclarecimento:**
> Observe que os nomes de variáveis usam **acentuação** (`USUÁRIO`, `conexão`) — isso é permitido em Java porque identificadores podem conter caracteres Unicode, incluindo acentos. Isso é uma escolha estilística do professor (nomear em português com acentos), não uma exigência da linguagem.

> **Esclarecimento do Claude:**
> Por que `static`? Porque o método `main` também é `static`, e métodos estáticos só podem acessar diretamente outros membros estáticos da mesma classe sem precisar instanciar um objeto. Como o tutorial não cria nenhum objeto `TesteBD` (só usa a classe via `main`), tudo é `static`.

> **🎯 Em resumo:**
> Você deveria compreender que, para conectar ao banco, o Java precisa de três informações básicas — URL de conexão, usuário e senha — armazenadas aqui como constantes, além de dois objetos-chave do JDBC: `Connection` (a conexão em si) e `Statement` (o "canal" para enviar comandos SQL).

---

## [p. 3/22] Classe TesteBD : métodos para criar e fechar conexão e comando

```java
public static void criaConexãoComando (){
    try {
        conexão = DriverManager.getConnection (URL_BD, USUÁRIO, SENHA);
        comando = conexão.createStatement ();
    } catch (SQLException exceção_sql) {
        System.out.println("Erro na criação da conexão");
        exceção_sql.printStackTrace ();}
}

public static void fechaComandoConexão () {
    try {
        comando.close();
        conexão.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `public static void criaConexãoComando (){` → Método público, estático, sem retorno (`void`), responsável por abrir a conexão e criar o `Statement`.
> - `try { ... } catch (SQLException exceção_sql) { ... }` → Estrutura de tratamento de exceções. Como operações de banco de dados podem falhar (rede fora do ar, senha errada, etc.), o Java **exige** que erros do tipo `SQLException` sejam tratados (capturados) ou declarados — aqui são capturados.
> - `conexão = DriverManager.getConnection (URL_BD, USUÁRIO, SENHA);` → Pede ao `DriverManager` que crie e retorne um objeto `Connection`, usando a URL, usuário e senha definidos antes. Esse objeto é guardado na variável estática `conexão`.
> - `comando = conexão.createStatement ();` → A partir da conexão já aberta, cria um objeto `Statement`, que será usado para enviar comandos SQL (`INSERT`, `SELECT`, `DELETE`, etc.).
> - `System.out.println("Erro na criação da conexão");` → Se algo falhar no `try`, imprime uma mensagem simples no console avisando do erro.
> - `exceção_sql.printStackTrace ();` → Imprime o **rastro completo do erro** (stack trace) no console — útil para depuração, mostrando exatamente onde e por que a exceção ocorreu.
> - `fechaComandoConexão()` → Método para fechar, na ordem correta, primeiro o `Statement` (`comando.close()`) e depois a `Connection` (`conexão.close()`). Fechar essas conexões é importante para **liberar recursos** do banco de dados e evitar conexões "penduradas".

> **🔎 Esclarecimento:**
> Se o código não chamasse `fechaComandoConexão()` ao final, a conexão ficaria aberta mesmo depois do programa não precisar mais dela, o que — em aplicações reais, com muitas conexões simultâneas — pode esgotar o limite de conexões do servidor MySQL.

> **💡 O que o professor está tentando ensinar:**
> A separação em dois métodos (`criaConexãoComando` e `fechaComandoConexão`) ensina o padrão básico de **ciclo de vida de uma conexão JDBC**: abrir no início do uso, fechar no final. Esse padrão se repete, de forma mais estruturada, na classe `BD` que aparece mais adiante no tutorial (Seção 3).

## Classe TesteBD : método main

> - removendo registros anteriores da tabela Pessoas
> - inserindo um registro na tabela com nome e CPF
> - consultando nome a partir do CPF e imprimindo o nome consultado

```java
public static void main(String[] args) {
    criaConexãoComando ();
    String sql;
    sql = "DELETE FROM Pessoas";
    try {
        comando.executeUpdate(sql);
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    sql = "INSERT INTO Pessoas (CPF, Nome) VALUES ('111.111.111-11', 'Ana Julia')";
    try {
        comando.executeUpdate(sql);
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    sql = "SELECT Nome FROM Pessoas WHERE CPF = '111.111.111-11'";
    ResultSet lista_resultados = null;
    try {
        lista_resultados = comando.executeQuery(sql);
        while (lista_resultados.next()) {
            System.out.println ("Nome: " + lista_resultados.getString("Nome"));
        }
        lista_resultados.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    fechaComandoConexão ();
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `criaConexãoComando ();` → Chama o método visto anteriormente, abrindo a conexão e criando o `Statement`.
> - `String sql;` → Declara uma variável de texto que vai guardar, em cada etapa, o comando SQL a ser executado. Ela é **reaproveitada** (recebe um novo valor) três vezes ao longo do método — uma escolha de estilo do professor, em vez de criar três variáveis diferentes.
> - `sql = "DELETE FROM Pessoas";` → Monta o comando SQL que apaga **todos** os registros da tabela `Pessoas` (sem `WHERE`, ou seja, sem filtro — apaga tudo).
> - `comando.executeUpdate(sql);` → Executa o comando SQL de "atualização" (que não retorna linhas — usado para `INSERT`, `UPDATE`, `DELETE`). O método `executeUpdate` retorna o número de linhas afetadas (aqui, esse retorno é ignorado).
> - `sql = "INSERT INTO Pessoas (CPF, Nome) VALUES ('111.111.111-11', 'Ana Julia')";` → Monta um comando `INSERT` que insere uma nova linha, com CPF `'111.111.111-11'` e Nome `'Ana Julia'`.
> - `sql = "SELECT Nome FROM Pessoas WHERE CPF = '111.111.111-11'";` → Monta uma consulta (`SELECT`) que busca a coluna `Nome`, filtrando pela linha cujo CPF seja exatamente esse.
> - `ResultSet lista_resultados = null;` → Declara a variável que vai guardar o resultado da consulta, inicialmente `null`.
> - `lista_resultados = comando.executeQuery(sql);` → Executa a consulta (`executeQuery`, diferente de `executeUpdate` porque **retorna linhas**) e guarda o resultado em `lista_resultados`.
> - `while (lista_resultados.next()) { ... }` → `ResultSet` funciona como um **cursor**: começa "antes" da primeira linha, e cada chamada de `.next()` avança para a próxima linha, retornando `true` se havia uma próxima linha (e `false` quando não há mais linhas — encerrando o `while`).
> - `System.out.println ("Nome: " + lista_resultados.getString("Nome"));` → Para a linha atual do cursor, pega o valor da coluna `"Nome"` como texto (`getString`) e imprime, concatenado com o rótulo `"Nome: "`.
> - `lista_resultados.close();` → Fecha o `ResultSet`, liberando os recursos associados a essa consulta.
> - `fechaComandoConexão ();` → Ao final de tudo, fecha o `Statement` e a `Connection`.

> **Observação do Claude:**
> Note que, do ponto de vista de **boas práticas de segurança**, montar comandos SQL concatenando strings diretamente (como `"...WHERE CPF = '111.111.111-11'"`) é vulnerável a **SQL Injection** quando os valores vêm de entrada do usuário. Este é só um teste com valores fixos ("hardcoded"), então não há risco real aqui — mas vale notar que, mais adiante no tutorial (Seção 5), o professor passa a usar `PreparedStatement` com parâmetros (`?`), que é a forma segura e recomendada de montar SQL com valores variáveis.

> Prof. Joinvile Batista Junior - Sistemas de Informação - FACET/UFGD

> O resultado gerado deverá ser:
> ```
> Nome: Ana Julia
> ```

> **🎯 Em resumo:**
> Você deveria compreender, desta Seção 1 inteira, o **ciclo básico de uma aplicação JDBC**: (1) abrir conexão, (2) executar comandos de escrita (`DELETE`/`INSERT`) via `executeUpdate`, (3) executar uma consulta (`SELECT`) via `executeQuery`, (4) percorrer o `ResultSet` linha a linha com `.next()` e extrair valores com `getString(...)`, e (5) fechar tudo ao final. Esse padrão se repete, de forma mais organizada, no restante do tutorial.

> **Observação do Claude (nota de arquitetura, ver aviso no topo do documento):**
> A classe `TesteBD` completa (Seção 1) é um **experimento isolado** — ela não pertence aos pacotes `persistência`, `entidades`, `interfaces` ou `controles` que formam o projeto real "Clube de Amigos do Cinema" desenvolvido a partir da Seção 2. Ela serve apenas para validar que o ambiente (MySQL + driver JDBC + NetBeans) está funcionando antes de começar o projeto de verdade.

---

## [p. 4/22] 2 - Estruturação do Projeto de Referência do Tutorial

> No projeto de referência deste tutorial, são criados quatro pacotes com as seguintes classes:
> - persistência : com a classe BD, que disponibiliza os métodos para criar e remover a conexão com o banco de dados;
> - entidades : com as classes Amigo e Ator, correspondentes às entidades sem referências;
> - interfaces (a palavra interface é uma palavra-chave em Java e não pode ser utilizada) : com as classes JanelaAmigosCinema, JanelaCadastroAmigos e JanelaCadastroAtores;
> - controles : com as classes ControladorCadastroAmigos e ControladorCadastroAtores.
>
> Cada controlador recebe solicitações e informações, que usuário fornece a partir de uma janela de cadastro de uma dada entidade, e interage com a respectiva entidade para verificar a viabilidade de inserir, alterar ou remover, e para solicitar a efetivação da respectiva operação no banco de dados.
>
> As demais janelas, serão ilustradas nos próximos três tutoriais: cadastro de filmes, cadastro das subclasses de filmes, cadastro e pesquisa de avaliações.
>
> Neste Tutorial, todos os atributos das entidades são lidos como textos. No Tutorial 2 serão ilustrados: (a) componentes adicionais utilizados para representar valores de enumerados (ex: estado_civil), seleção de item do tipo boolean (ex: ganhador_oscar) e os objetos com relacionamentos múltiplos. No Tutorial 3, será possível representar atributos de subclasses. No Tutorial 4, será ilustrado a pesquisa de avaliações, no banco de dados, a partir de atributos das entidades: Amigo, Filme e Avaliação.

> **💡 O que o professor está tentando ensinar:**
> Esta seção apresenta a **arquitetura em camadas** que será usada em todo o projeto — um padrão parecido com MVC (Model-View-Controller), mas com nomenclatura própria:
> - **`persistência`** → cuida só da conexão com o banco (o "encanamento").
> - **`entidades`** → representam os "objetos do mundo real" da aplicação (Amigo, Ator, e depois Filme, Avaliação) e também concentram, como métodos estáticos, o acesso direto ao banco de dados para aquela entidade específica.
> - **`interfaces`** → as janelas gráficas (Swing), responsáveis apenas por capturar entrada e mostrar saída ao usuário.
> - **`controles`** → a "cola" entre as janelas e as entidades: decide se uma operação (inserir, alterar, remover) é válida antes de mandar a entidade executá-la no banco.

> **🔎 Esclarecimento:**
> O parênteses "(a palavra interface é uma palavra-chave em Java e não pode ser utilizada)" explica por que o pacote se chama `interfaces` (no plural) e não `interface` (no singular) — porque `interface` é reservada pela linguagem Java para declarar tipos de interface (como em `public interface Comparable`). Se o professor tentasse nomear um pacote `interface`, o compilador Java daria erro de sintaxe.

> **🎯 Em resumo:**
> Você deveria compreender que o projeto terá 4 pacotes com responsabilidades bem separadas, e que este Tutorial 1 cobre apenas uma parte da aplicação completa: os cadastros básicos de Amigo e Ator, com atributos representados apenas como texto (sem enums visuais, boolean, subclasses, ou pesquisas — que virão nos tutoriais seguintes).

---

## [p. 4-5/22] 3 - Conexão com o banco de dados e Script de criação das Tabelas

> Para persistir os dados, informados pelos usuários, é necessária a criação (e posterior remoção) de uma conexão com a base de dados. A classe BD, do pacote persistência, provê essa funcionalidade:

```java
package persistência;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BD {
    static final String URL_BD = "jdbc:mysql://localhost/amigos";
    static final String USUÁRIO = "root";
    static final String SENHA = "admin";
    public static Connection conexão = null;

    public static void criaConexão () {
        try {
            conexão = DriverManager.getConnection (URL_BD, USUÁRIO, SENHA);
        } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    }

    public static void fechaConexão () {
        try {
            conexão.close();
        } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    }
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `package persistência;` → Esta classe pertence ao pacote `persistência` (o pacote responsável por lidar com o banco de dados no projeto real).
> - `static final String URL_BD = "jdbc:mysql://localhost/amigos";` → Note a diferença em relação à `TesteBD`: aqui não há porta explícita (`:3306`) — o driver usa a porta padrão do MySQL automaticamente — e o banco agora se chama `amigos` (não `teste`).
> - `public static Connection conexão = null;` → Diferente da `TesteBD`, aqui a variável `conexão` é **pública** (`public`), não privada. Isso é proposital: as classes de entidade (`Amigo`, `Ator`) vão acessar `BD.conexão` diretamente de fora da classe `BD`, para criar seus próprios `PreparedStatement`.
> - `criaConexão()` → Praticamente idêntico ao da `TesteBD`, mas **sem** criar um `Statement` — porque, neste projeto, os comandos SQL usam `PreparedStatement` (que é criado individualmente em cada método das entidades, como se verá na Seção 5), não um `Statement` genérico e compartilhado.
> - `fechaConexão()` → Fecha apenas a conexão (não há `Statement` compartilhado para fechar aqui).

> **Observação do Claude:**
> O texto original diz "Não podem ser criados a priori, como foi ilustrado na seção 1" (ver frase completa abaixo) — isso é uma explicação implícita de **por que** esta classe `BD` não cria um `Statement` fixo como a `TesteBD` fazia.

> Os comandos SQL utilizados na ilustração da aplicação desktop, são parametrizados (ver seção 5) e não podem ser criados a priori, como foi ilustrado na seção 1 para a aplicação TesteBD.

> **🔎 Esclarecimento:**
> "Parametrizados" aqui se refere ao uso de `PreparedStatement` com `?` no lugar de valores literais (ex.: `"SELECT Nome FROM Amigos WHERE Nome = ?"`), permitindo reaproveitar a mesma estrutura de comando SQL para valores diferentes, além de ser a forma segura de evitar SQL Injection. Por isso, não faz sentido criar **um único `Statement` fixo** para toda a classe `BD`, como se fazia na `TesteBD` — cada consulta parametrizada precisa do seu próprio `PreparedStatement`, montado no momento em que é usada.

> No subdiretório sql no diretório do projeto, é definido o script de criação das tabelas Amigos e Atores.

```sql
DROP TABLE IF EXISTS Amigos;
DROP TABLE IF EXISTS Atores;

CREATE TABLE Amigos (
 Nome VARCHAR(50) NOT NULL PRIMARY KEY,
 Apelido VARCHAR(30),
 Cidade VARCHAR(50) NOT NULL,
 Email VARCHAR(50) NOT NULL,
 Sexo INT NOT NULL,
 EstadoCivil INT NOT NULL,
 Whatsapp VARCHAR(30)) ;

CREATE TABLE Atores (
 Nome VARCHAR(50) NOT NULL PRIMARY KEY,
 AnoNascimento INT NOT NULL,
 Sexo VARCHAR(9) NOT NULL,
 GanhadorOscar BIT(1) NOT NULL);
```

> **Esclarecimento do Claude — linha a linha:**
> - `Nome VARCHAR(50) NOT NULL PRIMARY KEY` (tabela `Amigos`) → O **nome** é a chave primária desta tabela (não há uma coluna de ID numérico separado). Isso significa que não podem existir dois amigos cadastrados com o mesmo nome.
> - `Apelido VARCHAR(30)` → Sem `NOT NULL`, ou seja, **pode ficar em branco** (nulo) — é um campo opcional.
> - `Sexo INT NOT NULL` e `EstadoCivil INT NOT NULL` → Note que aqui são armazenados como **números inteiros** (`INT`), não como texto. Isso porque, no Java, `Sexo` e `EstadoCivil` são `enum` (tipos enumerados), e cada valor de um enum tem um índice numérico correspondente (chamado `ordinal()` em Java) — é esse índice que é gravado no banco.
> - Na tabela `Atores`: `Sexo VARCHAR(9) NOT NULL` → Aqui, diferente de `Amigos`, o `Sexo` do Ator é armazenado como **texto** (`VARCHAR`), não como número. Isso é uma escolha de projeto do professor — mais adiante o código relacionado ao Ator não é detalhado no tutorial (só é dito que "é semelhante"), então não há como confirmar 100% o motivo dessa diferença apenas com o texto fornecido.
> - `GanhadorOscar BIT(1) NOT NULL` → Um campo do tipo `BIT(1)`, que armazena um único bit (0 ou 1), correspondendo a um valor booleano (`boolean ganhador_oscar` em Java, como se vê na Seção 6).

> **Observação do Claude — possível inconsistência a observar:**
> O tutorial não apresenta explicitamente o código dos métodos estáticos de acesso ao banco para a entidade `Ator` (diz explicitamente, na Seção 6, que "sua implementação é equivalente à implementação da JanelaCadastroAmigos"). Por isso, a diferença de tipo entre `Sexo INT` (em `Amigos`) e `Sexo VARCHAR(9)` (em `Atores`) não pode ser plenamente explicada só com o texto disponível — é uma inferência minha, não uma afirmação do professor, que essa diferença é intencional e reflete uma implementação diferente (ainda que "equivalente" em estrutura) para a entidade `Ator`.

> De forma semelhante à utilizada na seção 1, crie o banco de dados amigos e execute o script para criar a tabela Amigos nesse banco.

> **🎯 Em resumo:**
> Você deveria compreender que a classe `BD` (pacote `persistência`) é a versão "definitiva" e mais simples da `TesteBD`: só cuida de abrir/fechar a conexão, sem `Statement` fixo, porque o projeto real usa `PreparedStatement`. E que as tabelas `Amigos` e `Atores` têm estruturas específicas, com `Nome` como chave primária e campos representando enums como números (no caso de `Amigos`) ou texto (no caso de `Atores`).

---

## [p. 5-6/22] 4 – Construindo a JanelaAmigosCinema no pacote interfaces

> A aplicação desktop, ilustrada neste tutorial, é intitulada Clube de Amigos do Cinema, em referência a amigos interessados em compartilhar informações sobre filmes preferidos. Essa aplicação suporta o cadastro de amigos, atores, filmes e avaliações (sobre filmes assistidos por alguém do clube) e a pesquisa de avaliações cadastradas por vários filtros opcionais, ou seja, o usuário pode selecionar os valores dos filtros de interesse, do conjunto de filtros disponibilizados para pesquisa.
>
> Nesta seção é ilustrada a construção da JanelaAmigosCinema, janela principal da aplicação desktop, com os menus utilizados para visualizar as demais janelas da aplicação.
>
> Criando o projeto AmigosCinema no NetBeans:
> - Arquivo → Novo Projeto
>   - Escolher Projeto
>     - Categorias : selecione Java
>     - Projetos : selecione Aplicação Java
>   - Nome e Localização
>     - Nome do Projeto : LPI-E1
>     - Localização do Projeto : escolha um diretório para armazenar seu projeto
>     - Criar Classe Principal : não assinalar

> **Observação do Claude:**
> O nome do projeto informado aqui é `LPI-E1` (sem o segundo "I" de "LPII"), embora o texto ao redor sempre se refira à disciplina como "LPII". Mantive exatamente como está no PDF — é possível que seja apenas uma convenção de nomenclatura do professor para "LP [Etapa] 1" (Linguagem de Programação, Etapa 1) e não um erro; mas também pode ser uma inconsistência de digitação do material. Não corrigi, apenas destaco.

> **🔎 Esclarecimento:**
> "Criar Classe Principal: não assinalar" significa que, ao criar o projeto, você **não** deve marcar a opção de gerar automaticamente uma classe com método `main`. Isso porque, aqui, a "classe principal" da aplicação será a própria janela (`JanelaAmigosCinema`), criada como um `Form JFrame` no passo seguinte — não uma classe `main` separada.

> Criando a janela principal da aplicação desktop JanelaAmigosCinema:
> - AmigosCinema → Novo → Form JFrame
>   - Nome e Localização
>     - Nome da Classe : JanelaAmigosCinema
>     - Pacote : interfaces
>
> As janelas são containers de componentes gráficos que herdam as características básicas (título, ícones comuns) da classe JFrame. A maioria dos componentes gráficos do pacote javax.swing inicia com um J, que não traz nenhum informação que distingua os componentes. Portanto, serão referenciados, nos tutoriais desta disciplina, sem o J inicial (neste caso: Frame). As janelas são representadas no NetBeans em duas abas principais:
> - Código-Fonte
> - Projeto : que contém o esboço do layout da janela

> **Observação do Claude:**
> O texto original tem uma pequena falha de concordância: "que não traz nenhum informação" (em vez de "nenhuma informação"). Preservado sem correção.

> **💡 O que o professor está tentando ensinar:**
> O professor está estabelecendo uma **convenção de nomenclatura** usada em todo o curso: como quase todo componente gráfico do Swing começa com "J" (`JFrame`, `JMenuBar`, `JMenu`, `JMenuItem`, `JButton`, `JLabel`, `JTextField`, `JComboBox`, `JPanel`, `JOptionPane`...), ele decidiu que, ao **falar sobre** esses componentes no texto do tutorial, vai omitir o "J" (chamando `JFrame` de "Frame", `JMenuBar` de "MenuBar" etc.). **Isso é só uma convenção de escrita/fala do professor** — no código Java real, o "J" continua existindo (`JFrame`, `JMenuItem`, etc.), como se vê nos próprios trechos de código do tutorial.

> Agora vamos apreender como criar os menus da janela principal da aplicação. Na realidade serão utilizados 3 tipos de componentes: barra de menus (MenuBar), no qual são associados menus (Menu), no qual são associados itens de menu (MenuItem). Observe na aba Navegador (se não estiver visível, selecione no NetBeans: Janela → Navegador), a hierarquia dos componentes que será criada para representar os menus na janela principal:

**[Imagem no PDF original: árvore de componentes no Navegador do NetBeans]**

> **Observação do Claude:**
> O PDF contém, neste ponto, uma captura de tela (imagem) mostrando a hierarquia no "Navegador" do NetBeans. Não reproduzo imagens — apenas descrevo o que a imagem mostra, com base no texto extraído dela:
> ```
> [JFrame] - Navigator
>  Form JanelaAmigosCinema
>    Other Components
>    [JFrame]
>      videolocadoraMenuBar [JMenuBar]
>        amigoMenu [JMenu]
>          cadastrar_amigoMenuItem [JMenuItem]
>        atorMenu [JMenu]
>          cadastrar_atorMenuItem [JMenuItem]
>        filmeMenu [JMenu]
>          cadastrar_filmeMenuItem [JMenuItem]
>        avaliaçãoMenu [JMenu]
>          cadastrar_avaliaçãoMenuItem [JMenuItem]
>          pesquisar_avaliaçãoMenuItem [JMenuItem]
> ```

> **Observação do Claude — possível inconsistência a observar:**
> Nesta imagem, a barra de menus aparece nomeada como `videolocadoraMenuBar`, mas em nenhum outro trecho do texto do tutorial esse nome ("videolocadora") é usado — o texto sempre fala em "Clube de Amigos do Cinema". Mais adiante, o próprio texto do tutorial instrui a nomear a variável como `amigos_cinemaMenuBar` (não `videolocadoraMenuBar`). Isso sugere que essa captura de tela pode ser de uma **versão anterior** do material (talvez reaproveitada de um exemplo de "locadora de filmes" de um tutorial anterior do professor) e não foi atualizada para refletir o nome final usado no texto. Preservei a transcrição da imagem fielmente, mas destaco essa divergência para você não se confundir ao seguir o tutorial: **use os nomes de variável indicados no texto** (`amigos_cinemaMenuBar`), não necessariamente os que aparecem nesta imagem específica.

---

## [p. 7/22] Continuação — abas do NetBeans e criação da barra de menus

> Quando a aba Projeto é selecionada, o NetBeans disponibiliza uma área central para visualização do esboço do layout da janela, e duas abas (localizadas à direita) para edição do layout da janela:
> - Paleta : disponibiliza os componentes que serão arrastados para a área central para compor o layout da janela agrupados em conjuntos de componentes afins
> - Propriedades : disponibiliza abas internas que serão utilizadas para configurar as propriedades do componente inserido no layout
>   - Propriedades : para alterar propriedades do componente (ex: título da janela)
>   - Eventos : para associar um tratador de evento a um dado componente (ex: método cadastrarAmigo a ser chamado quando o item de menu Cadastrar, vinculado ao menu Amigo é selecionado)
>   - Código: nome da variável que será associada ao componente sendo inserido na janela
>     - suponha que você criou o item de menu Cadastrar vinculado ao menu Amigo (vinculado à barra de menus)
>       - o Netbeans vai disponibilizar um nome de variável pouco significativo
>         - jMenuItem1
>       - altere para um nome que mantenha o tipo do componente, agregue a funcionalidade do componente e remova os caracteres irrelevantes (j e 1)
>         - cadastrar_amigoMenuItem

> **💡 O que o professor está tentando ensinar:**
> Esta é a lógica de **renomeação de componentes** no NetBeans: o NetBeans sempre gera nomes automáticos genéricos (`jMenuItem1`, `jButton1`, `jLabel3`...), mas o professor ensina uma convenção própria de nomenclatura — **"funcionalidade" + "Tipo do componente sem o J"**, em `camelCase`. Por exemplo: um item de menu que serve para "cadastrar amigo" se torna `cadastrar_amigoMenuItem` (funcionalidade "cadastrar_amigo" + tipo "MenuItem", sem o "j" inicial e sem o número).

> Para a criação do menus da janela principal é mais cômodo utilizar a aba Navegador para visualizar a hierarquia dos componentes sendo criados. Vamos começar criando a barra de menus amigos_cinemaMenuBar:
> - Navegador → botão direto do mouse em Frame → Adicionar da Paleta → Menus Swing → Barra de Menu
>   - será inserido como filho de Frame
>     - jMenuBar1
> - aba Propriedades → Código
>   - Nome da Variável : altere de jMenuBar1 para amigos_cinemaMenuBar

> **Observação do Claude:**
> "Para a criação do menus" (no original) deveria provavelmente ser "Para a criação dos menus" — pequeno erro gramatical preservado sem correção. Da mesma forma, "botão direto do mouse" (em várias partes do tutorial) provavelmente quer dizer "botão direito do mouse" — mantive fielmente como está escrito no PDF.

> Para criar o menu amigoMenu:
> - Navegador → botão direto do mouse em amigos_cinemaMenuBar → Adicionar Menu
>   - alguns menus serão inseridos como filhos de amigos_cinemaMenuBar
>   - remomeie cada menu para: Amigo, Filme e Avaliação (acrescente ou remova menus se necessário)
>   - exemplificando com o menu Amigo
>     - jMenu1
> - aba Propriedades → Código
>   - Nome da Variável : amigoMenu
> - aba Propriedades → Propriedades
>   - text : Amigo

> **Observação do Claude:**
> Note uma inconsistência importante: o texto diz "remomeie cada menu para: Amigo, Filme e Avaliação" — mas cita apenas 3 nomes, enquanto a árvore de componentes mostrada anteriormente (Seção 4, página 6) e o restante do tutorial mencionam **quatro** menus: `Amigo`, `Ator`, `Filme` e `Avaliação`. A frase "(acrescente ou remova menus se necessário)" parece reconhecer implicitamente essa necessidade de ajuste — ou seja, o professor sabe que a quantidade de menus criados automaticamente pode não bater exatamente com a quantidade final necessária, e orienta o aluno a ajustar manualmente. **Não corrigi o texto**, apenas destaco: você precisará de um menu `Ator` também, além dos três citados.

> **🔎 Esclarecimento:**
> "text : Amigo" na aba Propriedades → Propriedades se refere à propriedade `text` do componente `JMenu`, que é o **texto visível** exibido na barra de menus (o que o usuário lê e clica) — diferente do "Nome da Variável" (`amigoMenu`), que é o identificador usado **internamente no código**, mas nunca aparece na tela para o usuário.

---

## [p. 8/22] Criando o item de menu cadastrar_amigoItemMenu

> Para criar o item de menu cadastrar_amigoItemMenu:
> - Navegador → botão direto do mouse em amigoMenu → Adicionar da Paleta → Item de Menu
>   - um item de menu será inserido como filho de amigoMenu
>     - jItemMenu1
>       - aba Propriedades → Código
>         - Nome da Variável : cadastrar_amigoItemMenu
>       - aba Propriedades → Propriedades
>         - text : Cadastrar
>       - aba Propriedades → Eventos
>         - actionPerformed : cadastrarAmigo

> **Observação do Claude:**
> Aqui o nome da variável usado no texto ("cadastrar_amigoItemMenu") está com a ordem das palavras "Item" e "Menu" trocada em relação ao nome usado em quase todo o resto do tutorial ("cadastrar_amigoMenuItem", como no diagrama da página 6 e no código da página 8). Provavelmente é apenas uma inconsistência de digitação do professor entre "ItemMenu" e "MenuItem" — no código Java real do Swing, a classe correta é `JMenuItem` (não existe classe `JItemMenu`), então o nome de variável coerente com a convenção do professor seria `cadastrar_amigoMenuItem`. Mantive a transcrição fiel de ambas as formas como aparecem no PDF, mas alerto sobre essa inconsistência para que você não se confunda ao nomear suas variáveis.

> O método tratador de evento cadastrarAmigo será criado no código fonte da janela em construção para que você posteriormente implementar o seu corpo. Quando o usuário gerar um evento na janela principal da aplicação, selecionando o item de menu Cadastrar associado ao menu Amigo, o método tratador de evento cadastrarAmigo será chamado. Seu corpo é implementado com a criação de um objeto da classe ControladorCadastroAmigos que por sua vez irá criar e tornar visível a JanelaCadastroAmigos (seção 3).

> **Observação do Claude:**
> "para que você posteriormente implementar o seu corpo" — pequeno erro gramatical no original (faltaria "possa" antes de "implementar", ou seria "implementar" → "implemente"). Mantido sem correção.
>
> Também note: o texto diz "JanelaCadastroAmigos (seção 3)" — mas, pela estrutura do próprio documento, a `JanelaCadastroAmigos` é detalhadamente apresentada na **Seção 5** ("Construindo a JanelaCadastroAmigos"), não na Seção 3 (que trata da classe `BD` e do script SQL). Isso é, portanto, uma referência cruzada que parece estar desalinhada com a numeração final das seções do documento — possivelmente o tutorial passou por uma reorganização de seções após ser escrito originalmente, e essa referência não foi atualizada. Preservei o texto exatamente como está, mas você deve saber que a `JanelaCadastroAmigos` está de fato na Seção 5, mais adiante.

```java
private void cadastrarAmigo(java.awt.event.ActionEvent evt) { 
    new ControladorCadastroAmigos();
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `private void cadastrarAmigo(java.awt.event.ActionEvent evt) {` → Este é o método **tratador de evento** (event handler), gerado automaticamente pelo NetBeans quando você associa `actionPerformed : cadastrarAmigo` ao item de menu. O parâmetro `evt` (do tipo `ActionEvent`) representa a informação sobre o evento ocorrido (o clique/seleção do menu), embora, neste caso específico, `evt` não seja usado dentro do corpo do método.
> - `new ControladorCadastroAmigos();` → Cria um novo objeto da classe `ControladorCadastroAmigos`. Note que **o objeto criado não é guardado em nenhuma variável** — ele existe apenas temporariamente, o suficiente para seu construtor executar (que, como se vê a seguir, cria e exibe a janela de cadastro). Isso funciona porque toda a "ação" que o controlador precisa fazer (criar e mostrar a janela) acontece dentro do próprio construtor.

> O construtor da classe ControladorCadastroAmigos, do pacote controles, cria um objeto da JanelaCadastroAmigos (seção 3), repassando o seu objeto (this) para que a objeto da janela possa lhe repassar solicitações dos usuários, e ativando a visibilidade da janela.

> **Observação do Claude:**
> Novamente aparece "(seção 3)" como referência à `JanelaCadastroAmigos` — mesma observação de desalinhamento de numeração de seções feita acima. Também "para que a objeto da janela" contém um pequeno deslize gramatical ("a objeto" em vez de "o objeto" ou "que o objeto"). Mantido fielmente.

```java
public ControladorCadastroAmigos() {
    new JanelaCadastroAmigos(this).setVisible(true);
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `public ControladorCadastroAmigos() {` → Este é o **construtor** da classe `ControladorCadastroAmigos` (mesmo nome da classe, sem tipo de retorno).
> - `new JanelaCadastroAmigos(this).setVisible(true);` → Em uma única linha, três coisas acontecem:
>   1. `new JanelaCadastroAmigos(this)` → cria um novo objeto `JanelaCadastroAmigos`, passando `this` (o próprio objeto `ControladorCadastroAmigos` que está sendo construído) como argumento para o construtor da janela. Isso permite que a janela, mais tarde, chame métodos do controlador (como se vê na Seção 5.1).
>   2. `.setVisible(true)` → chama imediatamente o método `setVisible(true)` no objeto recém-criado, tornando a janela visível na tela.
>   3. O objeto `JanelaCadastroAmigos` criado **também não é guardado em variável** — ele "vive" na tela porque, sendo uma janela (`JFrame`) visível, o sistema operacional/Swing mantém uma referência a ela internamente enquanto estiver aberta.

> **🔎 Esclarecimento:**
> O uso de `this` aqui é um padrão de projeto simples de **injeção de dependência via construtor**: a janela recebe uma referência ao seu controlador para poder, mais tarde, chamar métodos como `controlador.inserirAmigo(amigo)` (visto na Seção 5.1) sempre que o usuário clicar em um botão de comando.

> Observe a ilustração da seleção do item de menu Cadastrar do menu Amigo na JanelaAmigosCinema:

**[Imagem no PDF original: janela "Clube Amigos do Cinema" com menu Amigo aberto mostrando o item "Cadastrar"]**

> O código relacionado com os componentes gráficos criados, bem como suas alterações de propriedades (realizadas a partir da aba Propriedades → Código), são inseridos no método initComponents, que criado diretamente pelo NetBeans e não pode ser editado pelo implementador da aplicação (delimitado por uma área em azul). As varíaveis correspondentes aos componentes criados são inseridas ao final do código fonte da janela em uma área azul que também não pode ser editada diretamente, somente sendo removidas ou alteradas a partir da aba Propriedades → Código.

> **Observação do Claude:**
> "que criado diretamente pelo NetBeans" parece faltar um "é" ("que **é** criado diretamente"). "As varíaveis" também tem erro de acentuação (deveria ser "As variáveis"). Ambos preservados sem correção, conforme solicitado.

> **💡 O que o professor está tentando ensinar:**
> Este é um ponto **muito importante** sobre como o NetBeans funciona internamente: quando você usa o editor visual de formulários (GUI Builder), ele gera automaticamente um método chamado `initComponents()` contendo todo o código de criação e configuração visual dos componentes. Esse método é protegido pelo NetBeans com uma área destacada em azul no editor de código, que **não pode ser editada manualmente** — qualquer alteração deve ser feita através da interface visual (abas Propriedades/Eventos/Código), nunca digitando diretamente no código gerado. Isso evita que edições manuais entrem em conflito com o que o editor visual espera gerar.

---

## [p. 9-10/22] Configurações finais da JanelaAmigosCinema

> As assinaturas (tipo de retorno, nome do método e parâmetros) de métodos criados como tratadores de eventos, também só podem ser removidas ou alteradas a partir da aba Propriedades → Eventos.

> **🔎 Esclarecimento:**
> Isso complementa o ponto anterior: não apenas o método `initComponents()` é protegido, mas a **assinatura** (cabeçalho) de qualquer método tratador de evento gerado pelo NetBeans (como `cadastrarAmigo(java.awt.event.ActionEvent evt)`) também não deve ser editada manualmente — só o **corpo** (miolo, entre `{` e `}`) desses métodos pode ser livremente programado pelo desenvolvedor.

> Alterando propriedades na JanelaAmigosCinema:
> - selecione a janela Frame na aba Navegador (ou na área do esboço do layout)
> - Propriedades → Propriedades
>   - defaultCloseOperation : DISPOSE
>   - title : Clube de Amigos do Cinema
>   - preferredSize : [400, 300]
> - Propriedades → Eventos
>   - windowClosed : terminarSistema

> **Esclarecimento do Claude:**
> - `defaultCloseOperation : DISPOSE` → Define o que acontece quando o usuário clica no "X" da janela: `DISPOSE` significa que a janela é destruída/liberada da memória, mas **não** necessariamente encerra todo o programa (diferente de `EXIT_ON_CLOSE`, que finalizaria a JVM inteira). Por isso, o professor precisa, manualmente, encerrar o programa via `System.exit(0)` no tratador de evento `terminarSistema` (visto a seguir) — mas apenas na janela principal.
> - `title : Clube de Amigos do Cinema` → Define o texto exibido na barra de título da janela.
> - `preferredSize : [400, 300]` → Define o tamanho preferido da janela em pixels (400 de largura por 300 de altura).
> - `windowClosed : terminarSistema` → Associa o evento "janela foi fechada" (que dispara **depois** que a janela já foi de fato fechada, diferente de `windowClosing`, que dispara antes) ao método `terminarSistema`.

> O tratador de evento terminarSistema é associado somente à janela principal da aplicação, para que quando for selecionado o ícone de fechamento da janela (X), além de remoção da visualização da janela, como ocorre com as demais janelas da aplicação sejam, adicionalmente, fechada a conexão com o banco de dados e encerrada a execução da aplicação. A seguir, é ilustrado o seu código:

```java
private void terminarSistema(java.awt.event.WindowEvent evt) { 
    BD.fechaConexão();
    System.exit(0);
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `private void terminarSistema(java.awt.event.WindowEvent evt) {` → Note que o tipo do parâmetro de evento aqui é `WindowEvent` (evento de janela), diferente do `ActionEvent` usado nos itens de menu — porque esse método está associado a um evento de **ciclo de vida da janela** (`windowClosed`), não a uma ação de clique.
> - `BD.fechaConexão();` → Chama o método estático `fechaConexão()` da classe `BD` (pacote `persistência`), fechando a conexão com o banco de dados antes de encerrar o programa. Isso evita deixar conexões "penduradas" no servidor MySQL quando o usuário fecha a aplicação.
> - `System.exit(0);` → Encerra **imediatamente** a execução de toda a Java Virtual Machine (JVM), finalizando o programa por completo. O argumento `0` indica "encerramento normal, sem erro" (por convenção do sistema operacional).

> **🔎 Esclarecimento:**
> Isso explica por que `defaultCloseOperation` foi definido como `DISPOSE` (e não `EXIT_ON_CLOSE`): o professor quer controle explícito sobre a sequência de encerramento (primeiro fechar o banco, depois sair), o que só é possível fazendo isso manualmente dentro de `terminarSistema`, e não deixando o Swing encerrar tudo automaticamente.

> No construtor da JanelaAmigosCinema é criada a conexão com o banco de dados:

```java
public JanelaAmigosCinema() {
    BD.criaConexão();
    initComponents();
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `BD.criaConexão();` → Ao criar a janela principal, a **primeira coisa** que acontece é abrir a conexão com o banco de dados — antes mesmo de montar os componentes visuais.
> - `initComponents();` → Chama o método (gerado automaticamente pelo NetBeans, protegido, área azul) que efetivamente cria e configura todos os componentes gráficos da janela (menus, barra de menus, etc.).

> **🎯 Em resumo:**
> Você deveria compreender o **ciclo de vida completo** da aplicação: a conexão com o banco é aberta uma única vez, no construtor da janela principal (`JanelaAmigosCinema`), e fechada uma única vez, quando essa mesma janela é fechada (`terminarSistema`) — sendo, então, seguida do encerramento total do programa via `System.exit(0)`.

> Para cada um dos itens de menu, associados aos menus Filme e Avaliação, serão associados tratadores de eventos que provisoriamente serão implementados para mostrar a informação de serviço indisponível. Por exemplo, a configuração do tratador de eventos para o item de menu Pesquisar do menu Avaliação:
> - selecione o item de menu pesquisar_avaliaçõesItemMenu na aba Navegador (ou na área do esboço do layout)
> - Propriedades → Eventos
>   - actionPerformed : pesquisarAvaliações

> **💡 O que o professor está tentando ensinar:**
> Como este Tutorial 1 só implementa efetivamente o cadastro de **Amigos** (e menciona que Ator é análogo), os menus de **Filme** e **Avaliação** — que serão implementados em tutoriais futuros — recebem, por enquanto, apenas um tratador de evento "provisório" que mostra uma mensagem dizendo que o serviço ainda não está disponível. Isso permite que a aplicação já tenha **todos os menus visíveis e funcionais** desde já (sem travar ou dar erro ao clicar), mesmo que a funcionalidade completa ainda não exista.

> **Observação do Claude:**
> Note a inconsistência de nomes: o texto se refere ao componente como `pesquisar_avaliaçõesItemMenu` (com "avaliações" no plural) nesta instrução, mas o método tratador de evento é `pesquisarAvaliações` (plural também) — porém, no código mostrado a seguir, o método é nomeado `pesquisarAvaliação` (singular). Vou destacar essa diferença de novo no ponto exato onde ela aparece.

> Os códigos do tratador de eventos pesquisarAvaliações, e do método auxiliar informarServiçoIndisponível, são ilustrados a seguir:

```java
private void informarServiçoIndisponível() {
    JOptionPane.showMessageDialog (this, "Serviço Indisponível", "Informação",
    JOptionPane.INFORMATION_MESSAGE);
}

private void pesquisarAvaliação(java.awt.event.ActionEvent evt) {
    informarServiçoIndisponível();
} 
```

> **Observação do Claude:**
> Aqui está a inconsistência mencionada: o texto chama o método de `pesquisarAvaliações` (com "s" no final, plural) na frase de instrução acima, mas o código mostra `pesquisarAvaliação` (sem "s", singular). Isso é, portanto, uma **divergência dentro do próprio tutorial original** — não uma correção minha. Se você for reproduzir este código, use o nome exatamente como aparece no bloco de código (`pesquisarAvaliação`), já que é o texto que efetivamente seria compilado.

> **Esclarecimento do Claude — linha a linha:**
> - `JOptionPane.showMessageDialog (this, "Serviço Indisponível", "Informação", JOptionPane.INFORMATION_MESSAGE);` → Chama o método estático `showMessageDialog` da classe `JOptionPane` (do pacote `javax.swing`), que exibe uma **caixa de diálogo simples** com uma mensagem. Os quatro argumentos, na ordem, são explicados no trecho seguinte do tutorial (próxima seção): a janela "mãe", o texto da mensagem, o título da janela auxiliar e o tipo de ícone/mensagem.
> - `pesquisarAvaliação(java.awt.event.ActionEvent evt) { informarServiçoIndisponível(); }` → O tratador de evento simplesmente delega toda a lógica para o método auxiliar `informarServiçoIndisponível()`, que é reutilizável por outros tratadores de evento provisórios (Filme, Cadastrar de Avaliação, etc.) sem duplicar código.

---

## [p. 10/22] Detalhamento do método informarServiçoIndisponível

> O método informarServiçoIndisponível, utiliza um método estático de OptionPane (omitindo o J inicial) que recebe os seguintes argumentos:
> - objeto da janela mãe da janela auxiliar : this (objeto da JanelaAmigosCinema);
> - string da mensagem a ser informada : "Serviço Indisponível";
> - título da janela auxiliar : "Informação"
> - tipo da mensagem a ser informada pela janela auxiliar :
>   - JOptionPane.INFORMATION_MESSAGE

> **🔎 Esclarecimento:**
> Aqui o professor detalha, em texto, exatamente os quatro parâmetros da chamada de `showMessageDialog` mostrada no código anterior — reforçando, com nomes descritivos, o que cada posição do método significa. "Janela mãe" é o termo usado pelo professor para se referir à janela **pai** (parent), que determina, entre outras coisas, onde a caixa de diálogo será centralizada na tela e qual janela ficará bloqueada enquanto o diálogo estiver aberto.

> Observe a ilustração da seleção do item de menu item de menu Pesquisar do menu Avaliação, seguida da ilustração da informação de Serviço Indisponível na janela auxiliar:

> **Observação do Claude:**
> "do item de menu item de menu" — repetição duplicada no texto original ("item de menu" aparece duas vezes seguidas). Mantido fielmente, sem correção.

**[Imagens no PDF original: duas janelas lado a lado — a janela principal com o menu Avaliação aberto (Cadastrar / Pesquisar), e a caixa de diálogo "Informação" com o texto "Serviço Indisponível" e um botão "OK"]**

> **🎯 Em resumo:**
> Você deveria compreender, desta parte da Seção 4, como: (1) configurar propriedades globais de uma janela `JFrame` (fechamento, título, tamanho); (2) implementar o encerramento correto de uma aplicação com conexão de banco de dados aberta; (3) usar `JOptionPane.showMessageDialog` para mostrar mensagens simples ao usuário; e (4) implementar "stubs" (implementações provisórias) para funcionalidades que ainda serão desenvolvidas em tutoriais futuros.

---

## [p. 10/22] 5 – Construindo a JanelaCadastroAmigos e as demais classes envolvidas

> Neste Tutorial é ilustrado o cadastro básico da entidade sem referências Amigo. A implementação do cadastro da entidade sem referências Ator é semelhante a da classe Amigo. Portanto, para a entidade Ator são ilustrados (seção 6) somente: (a) os enumerados e os atributos da classe Ator; e (b) a imagem da interface da JanelaCadastroAtores.

> **🔎 Esclarecimento:**
> "Entidade sem referências" quer dizer: uma entidade cujos atributos são todos "simples" (texto, número, enum) — **sem** relacionamento com outras entidades (como seria, por exemplo, um "Filme" que referencia um "Ator"). Isso é importante porque simplifica bastante o cadastro: não é preciso lidar com chaves estrangeiras ou seleção de objetos relacionados neste tutorial — isso será visto em tutoriais futuros.

> A classe Amigo, do pacote entidades, é definida com os seguintes enumerados e atributos:

```java
public class Amigo {

    public enum Sexo { masculino, feminino };
    public enum EstadoCivil {solteiro, casado, divorciado, viúvo};

    private String nome, apelido, cidade, email, whatsapp;
    private Sexo sexo;
    private EstadoCivil estadoCivil;
```

> **Esclarecimento do Claude — linha a linha:**
> - `public enum Sexo { masculino, feminino };` → Declara um **enum aninhado** dentro da classe `Amigo`, chamado `Sexo`, com exatamente dois valores possíveis: `masculino` e `feminino`. Sendo um enum aninhado e `public`, ele é acessado externamente como `Amigo.Sexo` (como se vê depois no `import entidades.Amigo.Sexo;`).
> - `public enum EstadoCivil {solteiro, casado, divorciado, viúvo};` → Outro enum aninhado, com quatro valores possíveis para o estado civil.
> - `private String nome, apelido, cidade, email, whatsapp;` → Declara, em uma única linha, cinco atributos do tipo `String` (texto), todos privados: nome, apelido, cidade, email e whatsapp.
> - `private Sexo sexo;` e `private EstadoCivil estadoCivil;` → Dois atributos cujo tipo é, respectivamente, os enums declarados acima.

> **🔎 Esclarecimento:**
> Em Java, cada valor de um `enum` tem automaticamente um índice inteiro correspondente à sua posição de declaração, obtido pelo método `.ordinal()` — começando em `0`. Assim: `Sexo.masculino` tem `ordinal() == 0` e `Sexo.feminino` tem `ordinal() == 1`. Isso é exatamente o que é gravado na coluna `Sexo INT` da tabela `Amigos` (ver Seção 3) — o índice, não o nome do enum.

> **💡 O que o professor está tentando ensinar:**
> Este trecho mostra como representar, em Java, valores que têm um **conjunto fixo e pequeno de opções válidas** (sexo, estado civil) usando `enum`, em vez de simplesmente usar `String` livre (o que permitiria valores inválidos como "talvez" ou erros de digitação).

---

## [p. 11/22] A interface visual da JanelaCadastroAmigos

> Na JanelaCadastrarAmigos, a representação do formulário de preenchimento dos atributos, com os dados preenchidos, é a seguinte:

**[Imagem no PDF original: janela "Cadastrar Amigos" com campos preenchidos — Amigos Cadastrados: "Francisco Silveira [Chico Bento]"; Nome: "Francisco Silveira"; Apelido: "Chico Bento"; Sexo: "M"; Estado Civil: "C"; Cidade: "Dourados"; Email: "francisco.silveira@gmail.com"; Whatsapp: "67-92222-2222"; botões Inserir, Consultar, Alterar, Remover, Limpar]**

> **Observação do Claude:**
> Note que o título desta subseção usa "JanelaCadastrarAmigos" (com "Cadastrar"), enquanto em praticamente todo o resto do tutorial o nome da classe é "JanelaCadastroAmigos" (com "Cadastro"). É apenas mais uma pequena inconsistência de digitação do documento original — o nome de classe correto e usado consistentemente no código é `JanelaCadastroAmigos`.

> A configuração da JanelaCadastroAmigos é a seguinte:
> - Propriedades → Propriedades
>   - defaultCloseOperation : DISPOSE
>   - title : Cadastrar Amigos
>   - preferredSize : [567, 313]

> Para a visualização dos rótulos associados ao campos a serem preenchidos é utilizado o componente Label. Ilustrando a configuração das propriedades do rótulo do nome:
> - arraste Label da Paleta → Controles Swing para o esboço do layout (ou utilize o Navegador para inserir como filho do Frame )
> - Propriedades → Código
>   - Nome da Variável : nomeLabel
> - Propriedades → Propriedades
>   - text : Nome

> **🔎 Esclarecimento:**
> `Label` aqui, seguindo a convenção do professor de omitir o "J", refere-se ao componente `JLabel` do Swing — um componente que apenas **exibe texto estático** (não editável pelo usuário), usado tipicamente para rotular outros campos (dizer "isto aqui é o campo Nome").

> A cada objeto inserido na JanelaCadastroAmigos algumas informações do objeto são mostradas no componente ComboBox da janela. Ilustrando a configuração de suas propriedades:
> - arraste ComboBox da Paleta → Controles Swing para o esboço do layout (ou utilize o Navegador para inserir como filho do Frame)
> - Propriedades → Código
>   - Nome da Variável : amigos_cadastradosComboBox
>   - TypeParameters : remova a palavra String
> - Propriedades → Propriedades
>   - maximumRowCount : 8
>   - model : selecione ...
>     - no model property selecione : Custom code
>     - preencha setModel com : new DefaultComboBoxModel (amigos_cadastrados)

> **Esclarecimento do Claude:**
> - `ComboBox` → refere-se ao componente `JComboBox` (uma "caixa de seleção" suspensa, tipo "dropdown"), que mostra uma lista de opções para o usuário escolher uma.
> - `TypeParameters : remova a palavra String` → O NetBeans, por padrão, cria o `JComboBox` como um `JComboBox<String>` (um combo que só guarda textos). O professor instrui a remover esse parâmetro de tipo genérico `String`, deixando o combo genérico (`JComboBox` sem tipo definido, ou tecnicamente com tipo `Object`), porque ele vai guardar **objetos** `Amigo` (visões reduzidas), não simples textos.
> - `maximumRowCount : 8` → Define quantas linhas (itens) são exibidas de uma vez quando a lista suspensa é aberta, antes de precisar de barra de rolagem.
> - `model : Custom code` com `new DefaultComboBoxModel (amigos_cadastrados)` → O "modelo" de um `JComboBox` é o objeto que efetivamente guarda a lista de itens exibidos. Em vez de usar o editor visual para digitar itens fixos, o professor escolhe "Custom code" para que o modelo seja **construído dinamicamente em código**, a partir do array `amigos_cadastrados` (visto no construtor da janela, a seguir).

> **🔎 Esclarecimento:**
> "A cada objeto inserido" quer dizer "sempre que um novo objeto (Amigo) é inserido" — ou seja, este ComboBox serve como uma lista de "amigos já cadastrados no banco", permitindo ao usuário selecionar um deles para consultar, alterar ou remover.

---

## [p. 12/22] Continuação — TextField, Painel e Botões

> Conforme ilustrado e explicado na página 15, a variável amigos_cadastrados deve ser definida no construtor da JanelaCadastroAmigos.

> **Observação do Claude:**
> Esta é uma referência de página **interna ao próprio PDF original** ("página 15"), preservada como está no texto. De fato, essa variável (`amigos_cadastrados`) aparece definida no construtor mostrado mais adiante neste mesmo guia (na seção correspondente à página 15 do PDF original).

> Para o preenchimento dos campos é utilizado o componente TextField. Ilustrando a configuração das propriedades do campo de texto do nome:
> - arraste Campo de Texto da Paleta → Controles Swing para o esboço do layout (ou utilize o Navegador para inserir como filho do Frame)
> - Propriedades → Código
>   - Nome da Variável : nomeTextField
> - Propriedades → Propriedades
>   - columns : 50
>   - text : string vazio
>   - preferredSize : [456, 20]
>
> Crie um par Label e Campo de Texto para cada um desses dados da entidade Amigo, com os seguintes rótulos: Apelido, Cidade, Email, Sexo, Estado Civil e Whatsapp.

> **Esclarecimento do Claude:**
> `TextField` (ou seja, `JTextField`) é o componente de **campo de texto editável** — onde o usuário efetivamente digita informações (diferente do `Label`, que é só exibição). A propriedade `columns : 50` sugere a largura do campo em número aproximado de caracteres, e `text : string vazio` significa que o campo começa sem nenhum valor pré-preenchido.
>
> Repare que o professor pede para repetir esse processo para **seis** rótulos adicionais (Apelido, Cidade, Email, Sexo, Estado Civil, Whatsapp), totalizando sete pares Label+TextField no formulário (incluindo o de Nome, já mostrado): Nome, Apelido, Cidade, Email, Sexo, Estado Civil e Whatsapp — exatamente os sete atributos de texto da classe `Amigo` (nome, apelido, cidade, email, sexo, estadoCivil, whatsapp).

> Os botões de comando devem encapsalados por um painel de container (Panel), para pode ser posicionados com um grupo no layout da janela. Ilustrando a configuração das propriedades do painel:
> - arraste Painel da Paleta → Contêineres para o esboço do layout (ou utilize o Navegador para inserir como filho do Frame)
> - Propriedades → Código
>   - Nome da Variável : comandosPanel
> - Propriedades → Propriedades
>   - border : Borda de Linha
>     - para visualizar o painel : após inserir todos os comandos você poderá alterar para
>       - border : Sem Borda
> - Navegador
>   - botão direito → Definir Layout : Layout de Fluxo (layout default do painel)

> **Observação do Claude:**
> "devem encapsalados" no original está faltando um verbo — provavelmente deveria ser "devem **ser** encapsulados" (e "encapsalados" parece ser um erro de digitação de "encapsulados"). Também "para pode ser posicionados" provavelmente deveria ser "para poderem ser posicionados". Mantidos fielmente, sem correção.

> **🔎 Esclarecimento:**
> "Painel" refere-se a `JPanel`, um contêiner genérico que agrupa outros componentes. Aqui, ele serve para agrupar os **botões de comando** (Inserir, Consultar, Alterar, Remover, Limpar) para que se comportem como um bloco só dentro do layout da janela principal, em vez de precisar posicionar cada botão individualmente no `GridBagLayout` da janela toda. O `border : Borda de Linha` é uma dica visual temporária, só para o desenvolvedor **ver os limites do painel** durante o design — depois, quando o layout já estiver correto, essa borda pode ser removida (`Sem Borda`) para não aparecer na aplicação final.

> Insira o botões de comando como filhos do comandosPanel no Navegador. Ilustrando com o comando para inserção:
> - arraste Botão da Paleta → Controles Swing para o esboço do layout (ou utilize o Navegador, para inserir como filho do comandosPanel)
> - Propriedades → Código
>   - Nome da Variável : inserirButton
> - Propriedades → Propriedades
>   - text : Inserir
>
> Repita a mesma operação para os botões de comando com os textos: Consultar, Alterar, Remover e Limpar.

> **Observação do Claude:**
> "Insira o botões" no original — deveria ser "Insira os botões" (concordância de número). Mantido sem correção.

> **🎯 Em resumo:**
> Você deveria compreender, desta parte, como construir visualmente o formulário completo de cadastro: sete pares Label+TextField para os atributos de texto, um ComboBox para listar amigos já cadastrados, e um Panel contendo cinco botões de comando (Inserir, Consultar, Alterar, Remover, Limpar) — cada um desses componentes recebendo um nome de variável significativo via aba Propriedades → Código.

---

## [p. 13-14/22] Layout: GridBagLayout e Personalizar Layout

> Existem várias opções de layout. O layout default do Frame é o Layout da Borda (Border Layout). Esse layout é muito limitado, permitindo definir a área da janela somente em cinco subáreas: centro, sul, norte, leste e oeste. Para ter um controle maior sobre a localização dos componentes na janela vamos utilizar o Layout de Conjunto de Grades (GridBagLayout), que é o layout mais customizável disponível:
> - no Navegador selecione Frame → botão direito do mouse → Definir Layout → Layout de Conjunto de Grades

> **🔎 Esclarecimento:**
> O `BorderLayout` (layout padrão de um `JFrame`) só permite posicionar componentes em 5 "zonas" fixas (`NORTH`, `SOUTH`, `EAST`, `WEST`, `CENTER`), o que é inviável para um formulário com muitos campos organizados em grade. Já o `GridBagLayout` é o layout manager mais flexível (e também mais complexo) do Swing, permitindo posicionar componentes em uma grade de linhas e colunas com controle fino de alinhamento, espaçamento e expansão — por isso o professor escolhe usá-lo aqui.

> Observe também o distanciamento entre os rótulos e os campos de texto, e entre os pares rótulo e campo de texto e os seus pares acima e abaixo, bem como o distanciamento do grupo de botões de comando. Organizar o layout dessa forma é bem simples utilizando o GridBagLayout. Clique na área do esboço do layout da janela e selecione, com o botão direito do mouse: Personalizar Layout. Você vai visualizar a janela Personalizar Layout:

**[Imagem no PDF original: janela "Customize Layout" do NetBeans, mostrando uma grade de 2 colunas x 10 linhas com os componentes do formulário posicionados, e painéis à esquerda para Anchor, Insets, Internal Padding, Size in Grid, Position in Grid, Fill, Weights]**

> **Esclarecimento do Claude:**
> Essa janela ("Customize Layout"/"Personalizar Layout") é uma ferramenta visual do próprio NetBeans para ajustar, sem escrever código manualmente, as propriedades internas do `GridBagLayout` (como `gridx`, `gridy`, `anchor`, `insets`, `weightx`, `weighty` — que são, "por trás", os parâmetros reais da classe Java `GridBagConstraints`). O professor está mostrando **como usar essa ferramenta visual**, em vez de escrever manualmente o código de configuração do `GridBagLayout`, que seria bem mais verboso.

> Essa janela tem duas regiões. Na região direita você tem os componentes gráficos posicionados em uma matriz com 2 colunas e 10 linhas. Na região esquerda você tem as funcionalidades que podem ser aplicadas a um componente ou a um grupo de componentes. Trabalhar com um grupo de componentes de uma dada coluna ou de uma dada linha facilita a organização do layout. As opções mais úteis são:
> - ancorar uma coluna de componentes à esquerda ou à direita
>   - selecionar os rótulos da coluna 0, e ancorá-los (Ancorar) à direita (de forma que a extremidade direita de todos eles esteja alinhada na vertical)
>   - selecionar os componentes à direita dos rótulos da coluna 1, e ancorá-los (Ancorar) à esquerda (de forma que a extremidade esquerda de todos eles esteja alinhada na vertical)
> - ajustar a distância de uma coluna dos componentes de uma dada coluna à direita ou à esquerda
>   - selecionar os rótulos da coluna 0, e a ajustar a distância dos componentes da coluna 1 (Insets)
> - ajustar a distância dos componentes de uma dada linha acima ou abaixo
>   - selecionar os componentes em uma dada linha (um par rótulo e seu componente associado, ou o painel de botões de comando) e ajustar a distância dos componentes da linha de cima e/ou da linha de baixo (Insets).
>
> Caso os componentes não estejam posicionados inicialmente na linha ou na coluna que você deseja, você pode arrastá-los e posicionar na linha e coluna desejada. Também pode inserir uma nova linha acima ou abaixo de uma linha existente, para posicionar um componente. Idem com as colunas.

> **💡 O que o professor está tentando ensinar:**
> Este trecho ensina a lógica visual de organização de um formulário em grade: os **rótulos** (coluna 0) ficam alinhados à direita (para que fiquem "colados" nos seus respectivos campos), e os **campos de entrada** (coluna 1) ficam alinhados à esquerda (formando uma coluna reta de início de campos). O "Insets" controla o espaçamento (margens internas) entre os componentes vizinhos — tanto na horizontal (entre rótulo e campo) quanto na vertical (entre uma linha e a próxima).

> Para complementar essa seção, serão ilustrados (em subseções) os métodos da JanelaCadastroAmigos. Vamos visualizar as importações, uma função auxiliar (método estático) de conversão de string para elemento de enumerado e o construtor da janela. O método construtor recebe o objeto do controlador, para armazená-lo e utilizá-lo quando um botão de comando for selecionado pelo usuário e tiver que repassar informações para o controlador decidir sobre a realização do comando solicitado.

---

## [p. 15/22] Código da JanelaCadastroAmigos: importações, conversor de enum e construtor

```java
package interfaces;

import javax.swing.JOptionPane;
import controles.ControladorCadastroAmigos;
import entidades.Amigo;
import entidades.Amigo.Sexo;
import entidades.Amigo.EstadoCivil;
import javax.swing.DefaultComboBoxModel;

public class JanelaCadastroAmigos extends javax.swing.JFrame {

    public static EstadoCivil converteStringParaEstadoCivil(String estado_civil_str) {
        switch(estado_civil_str){
            case "C": return EstadoCivil.casado;
            case "D": return EstadoCivil.divorciado;
            case "S": return EstadoCivil.solteiro;
            case "V": return EstadoCivil.viúvo;
            default: return null;
        }
    }

    ControladorCadastroAmigos controlador;
    Amigo[] amigos_cadastrados;

    public JanelaCadastroAmigos(ControladorCadastroAmigos controlador) {
        this.controlador = controlador;
        amigos_cadastrados = Amigo.getVisões();
        initComponents();
        limparCampos(null);
    }
```

> **Esclarecimento do Claude — linha a linha:**
> - `package interfaces;` → Esta classe pertence ao pacote `interfaces`.
> - `import javax.swing.JOptionPane;` → Necessário para exibir mensagens de erro (`informarErro`, visto mais adiante).
> - `import controles.ControladorCadastroAmigos;` → Importa a classe do controlador (de outro pacote), necessária porque a janela guarda uma referência a ele.
> - `import entidades.Amigo;`, `import entidades.Amigo.Sexo;`, `import entidades.Amigo.EstadoCivil;` → Importa a classe `Amigo` e seus dois enums aninhados (`Sexo` e `EstadoCivil`), permitindo usá-los sem precisar escrever `Amigo.Sexo` ou `Amigo.EstadoCivil` toda vez.
> - `import javax.swing.DefaultComboBoxModel;` → Necessário porque, na Seção anterior, o ComboBox foi configurado com `new DefaultComboBoxModel(amigos_cadastrados)`.
> - `public class JanelaCadastroAmigos extends javax.swing.JFrame {` → A classe **herda** de `JFrame`, confirmando que é, ela mesma, uma janela Swing completa (não apenas contém uma janela).
> - `public static EstadoCivil converteStringParaEstadoCivil(String estado_civil_str) {` → Método estático auxiliar que converte um texto (uma letra) no valor de enum correspondente.
>   - `switch(estado_civil_str){` → Estrutura `switch` sobre uma `String` (permitido desde o Java 7).
>   - `case "C": return EstadoCivil.casado;` → Se o texto for exatamente `"C"`, retorna o valor do enum `casado`. O mesmo padrão se repete para `"D"` → `divorciado`, `"S"` → `solteiro`, `"V"` → `viúvo`.
>   - `default: return null;` → Se o texto não corresponder a nenhuma das letras esperadas, retorna `null` (indicando entrada inválida).
> - `ControladorCadastroAmigos controlador;` → Atributo (variável de instância) que guarda a referência ao controlador que criou esta janela (recebido via construtor).
> - `Amigo[] amigos_cadastrados;` → Atributo do tipo **array de `Amigo`**, guardando a lista (visões reduzidas) de amigos já cadastrados, usada para popular o ComboBox.
> - `public JanelaCadastroAmigos(ControladorCadastroAmigos controlador) {` → Construtor que recebe o controlador como parâmetro.
>   - `this.controlador = controlador;` → Guarda o controlador recebido no atributo da classe (usa `this.` para diferenciar o atributo do parâmetro, que têm o mesmo nome).
>   - `amigos_cadastrados = Amigo.getVisões();` → Chama o método estático `getVisões()` da classe `Amigo` (visto a seguir) para buscar, no banco de dados, a lista de amigos já cadastrados (em formato reduzido).
>   - `initComponents();` → Chama o método gerado pelo NetBeans que efetivamente monta os componentes visuais — e é **aqui**, dentro desse método (na parte "Custom code" configurada visualmente), que o `DefaultComboBoxModel(amigos_cadastrados)` é de fato usado para popular o ComboBox, já que `amigos_cadastrados` precisa estar preenchido **antes** de `initComponents()` ser chamado.
>   - `limparCampos(null);` → Ao final do construtor, chama o método `limparCampos` (visto na Seção 5.5) passando `null` como argumento, para deixar todos os campos de texto vazios logo que a janela é aberta.

> **Observação do Claude:**
> Este é exatamente o ponto que o texto da página 12 (Seção 5) referenciava como "página 15" — a definição da variável `amigos_cadastrados` no construtor, confirmando que a referência cruzada estava correta.

> A função (método estático) getVisões, da classe Amigo retorna um conjunto de objetos reduzidos para serem mostrados no componente ComboBox, utilizado nesta interface para representar os objetos cadastrados da classe Amigo. Neste componente são mostrados somente os atributos nome e apelido da classe Amigo.

```java
public static Amigo[] getVisões() {
    String sql = "SELECT Nome, Apelido FROM Amigos";
    ResultSet lista_resultados = null;
    ArrayList<Amigo> visões = new ArrayList();
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) {
            String nome = lista_resultados.getString("Nome");
            String apelido = lista_resultados.getString("Apelido");
            visões.add(new Amigo (nome, apelido));
        }
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    return visões.toArray(new Amigo[visões.size()]);
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `String sql = "SELECT Nome, Apelido FROM Amigos";` → Monta a consulta SQL que busca **apenas** as colunas `Nome` e `Apelido` de todos os registros da tabela `Amigos` (sem `WHERE`, ou seja, todos os amigos).
> - `ArrayList<Amigo> visões = new ArrayList();` → Cria uma lista dinâmica (que pode crescer conforme necessário) para acumular os objetos `Amigo` "reduzidos" (visões) encontrados.
> - `PreparedStatement comando = BD.conexão.prepareStatement(sql);` → Aqui aparece o primeiro uso de `PreparedStatement` no tutorial: cria um comando preparado a partir da conexão estática pública `BD.conexão` (definida na Seção 3). Note que, embora esta consulta em particular **não tenha parâmetros** (não há `?` no SQL), o professor ainda usa `PreparedStatement` em vez de `Statement`, provavelmente por padronização com os demais métodos da classe (que, esses sim, têm parâmetros).
> - `lista_resultados = comando.executeQuery();` → Executa a consulta preparada (sem argumentos passados, pois `executeQuery()` aqui não recebe SQL — o SQL já foi definido em `prepareStatement`).
> - `while (lista_resultados.next()) { ... }` → Percorre cada linha do resultado.
>   - `String nome = lista_resultados.getString("Nome");` e `String apelido = lista_resultados.getString("Apelido");` → Extrai os valores das duas colunas da linha atual.
>   - `visões.add(new Amigo (nome, apelido));` → Cria um novo objeto `Amigo` usando um **construtor reduzido** (que recebe só nome e apelido — visto na próxima subseção) e adiciona à lista `visões`.
> - `return visões.toArray(new Amigo[visões.size()]);` → Converte a `ArrayList<Amigo>` de volta para um array comum `Amigo[]`, que é o tipo de retorno declarado do método. `new Amigo[visões.size()]` cria um array vazio do tamanho exato necessário, que o método `toArray` usa como "molde" para o array de retorno.

> **🔎 Esclarecimento:**
> Note que a palavra "visões", no plural, é usada tanto como nome da lista/array (`visões`) quanto como conceito ("visão" = versão reduzida de um objeto, mostrando só alguns atributos). Isso é consistente com o termo "visão" usado depois no método `getVisão()` (singular), que cria uma única visão reduzida.

> **🎯 Em resumo:**
> Você deveria compreender o conceito de **"visão" (view/projeção) de um objeto**: em vez de carregar o objeto `Amigo` completo (com todos os 7 atributos) só para preencher um ComboBox que mostra apenas "Nome [Apelido]", o método `getVisões()` busca do banco só as colunas necessárias e cria objetos `Amigo` parcialmente preenchidos (só nome e apelido, com os demais atributos permanecendo `null`), economizando processamento e tráfego de dados.

---

## [p. 16/22] O método limparCampos e a Seção 5.1 — Inserir Amigo

> O método limparCampos, ilustrado na seção 5.5, é criado como tratador de eventos para ser executado quando o usuário clica no botão Limpar. Por esse motivo esse método é definido pelo NetBeans com parâmetro evt para receber o evento como argumento. No entanto, esse método também pode ser chamado sem que nenhum evento tenha sido gerado; basta passar null como argumento para o parâmetro evt.

> **🔎 Esclarecimento:**
> Isso explica por que, no construtor da janela (visto acima), a chamada foi `limparCampos(null)` — como o método não usa efetivamente o valor de `evt` dentro do seu corpo (ver Seção 5.5), é seguro chamá-lo manualmente passando `null`, mesmo fora do contexto de um evento real de clique de botão gerado pelo Swing.

### 5.1 - O Tratamento de Eventos para Inserir um Amigo

> O tratador de eventos inserirAmigo, associado ao botão de comando Inserir, solicita a leitura dos campos informados na interface ao metodo obterAmigoInformado, que retorna um objeto da classe Amigo. Se todos os campos forem lidos corretamente, o objeto retornado não é nulo e, portanto, é repassado ao objeto da classe ControladorCadastroAmigos, que checa se a inserção é possível e coordena a sua execução no banco de dados. Caso não retorne mensagem de erro (em caso de insucesso), é obtida uma visão da classe Amigo (objeto reduzido somente com os atributos nome e apelido) que é inserida no componente ComboBox (que mostra os amigos cadastrados) e o último objeto cadastrado é selecionado para ser mostrado no ComboBox.

> **Observação do Claude:**
> "ao metodo obterAmigoInformado" — falta o acento em "método". Mantido sem correção, conforme regra de fidelidade ao texto original.

```java
private void inserirAmigo(java.awt.event.ActionEvent evt) { 
    Amigo amigo = obterAmigoInformado();
    String mensagem_erro = null;
    if (amigo != null) mensagem_erro = controlador.inserirAmigo(amigo);
    else mensagem_erro = "Algum atributo do amigo não foi informado";
    if (mensagem_erro == null) {
        Amigo visão = amigo.getVisão();
        amigos_cadastradosComboBox.addItem(visão);
        amigos_cadastradosComboBox.setSelectedItem(visão); 
    } else informarErro (mensagem_erro);
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `Amigo amigo = obterAmigoInformado();` → Chama o método auxiliar (visto mais abaixo) que lê todos os campos de texto do formulário e monta um objeto `Amigo` completo — ou retorna `null` se algum campo obrigatório estiver vazio/inválido.
> - `String mensagem_erro = null;` → Variável que vai guardar uma eventual mensagem de erro. Começa como `null`, que — em toda a lógica deste tutorial — **significa sucesso** (ausência de erro).
> - `if (amigo != null) mensagem_erro = controlador.inserirAmigo(amigo);` → Se a leitura dos campos foi bem-sucedida (objeto não nulo), delega a operação de inserção ao **controlador**, que decide se é válida (por exemplo, verificando se já existe um amigo com aquele nome) e, se for, executa a inserção no banco.
> - `else mensagem_erro = "Algum atributo do amigo não foi informado";` → Caso contrário (campos inválidos/vazios), define uma mensagem de erro fixa, sem nem chegar a consultar o controlador.
> - `if (mensagem_erro == null) { ... } else informarErro (mensagem_erro);` → Se, ao final, não há mensagem de erro (`null`), significa que a inserção foi bem-sucedida:
>   - `Amigo visão = amigo.getVisão();` → Cria uma "visão reduzida" (nome+apelido) do amigo recém-inserido.
>   - `amigos_cadastradosComboBox.addItem(visão);` → Adiciona essa visão à lista do ComboBox (sem precisar recarregar tudo do banco de novo).
>   - `amigos_cadastradosComboBox.setSelectedItem(visão);` → Seleciona automaticamente o item recém-adicionado no ComboBox, para feedback visual imediato ao usuário.
> - Se houver mensagem de erro, `informarErro(mensagem_erro)` é chamado para mostrar a mensagem ao usuário (método visto mais abaixo).

> **💡 O que o professor está tentando ensinar:**
> Este é o **padrão de tratamento de erro sem exceções** usado em toda a aplicação: em vez de lançar/capturar exceções Java para erros de regra de negócio (como "nome já cadastrado"), os métodos retornam `String` — `null` significa sucesso, qualquer outro texto é uma mensagem de erro a ser exibida ao usuário. Isso é uma escolha de design simples e eficaz para separar "erros de programação" (que usariam exceções) de "erros esperados de regra de negócio" (que usam esse padrão de retorno).

```java
public Amigo(String nome, String apelido) {
    this.nome = nome;
    this.apelido = apelido;
}

public Amigo getVisão() { return new Amigo (nome, apelido); }
```

> **Esclarecimento do Claude — linha a linha:**
> - `public Amigo(String nome, String apelido) { ... }` → Este é o **segundo construtor** da classe `Amigo` (sobrecarga de construtor — `constructor overloading`), que recebe só nome e apelido, deixando todos os demais atributos (cidade, email, sexo, estadoCivil, whatsapp) como `null` por padrão. É este construtor que é usado tanto em `getVisões()` (visto antes) quanto em `getVisão()` (aqui).
> - `public Amigo getVisão() { return new Amigo (nome, apelido); }` → Cria e retorna uma nova "visão reduzida" do objeto atual (`this`), usando o construtor reduzido acima, com base nos próprios atributos `nome` e `apelido` do objeto completo.

> **🔎 Esclarecimento:**
> Isso significa que a classe `Amigo` tem, na verdade, **dois construtores diferentes**: um completo (visto na Seção 5.4, com todos os 7 atributos) e um reduzido (aqui, só com nome e apelido) — o Java permite múltiplos construtores com listas de parâmetros diferentes, desde que o compilador consiga distinguir qual chamar com base na quantidade/tipo dos argumentos.

---

## [p. 16-17/22] O método obterAmigoInformado

> O método auxiliar obterAmigoInformado, obtém todas as informações preenchidas nos campos de texto do formulário de cadastro, e retorna o objeto da entidade Amigo, construído a partir das informações do usuário. Cada campo de texto é testado, para verificar se não está vazio. Como todos os campos são obrigatórios, qualquer campo vazio resultará no retorno de null em lugar do objeto.

---

## [p. 17/22]

> Para converter um string (com a letra inicial de um estado civil) para o correspondente elemento do enumerado EstadoCivil (da classe Amigo), é utilizado um método estático auxiliar. No caso do enumerado Sexo (da classe Amigo), foi implementado diretamente (no método ObterAmigoInformado) porque o enumerado tem menos elementos.

> **Observação do Claude:**
> "no método ObterAmigoInformado" — o nome do método aparece aqui com "O" maiúsculo, mas em todo o restante do texto e no código, o método é `obterAmigoInformado` (com "o" minúsculo). Preservado fielmente, mas é apenas uma inconsistência de capitalização no texto do professor, não uma segunda versão do método.

```java
private Amigo obterAmigoInformado() {
    String nome = nomeTextField.getText();
    if (nome.isEmpty()) return null;
    String apelido = apelidoTextField.getText();
    if (apelido.isEmpty()) apelido = null;
    String cidade = cidadeTextField.getText();
    if (cidade.isEmpty()) return null;
    String email = emailTextField.getText();
    if (email.isEmpty()) return null;
    String sexo_str = sexoTextField.getText();
    Sexo sexo = Sexo.feminino;
    if (sexo_str.equals("M")) sexo = Sexo.masculino;
    else if (!sexo_str.equals("F")) return null;
    String estado_civil_str = estado_civilTextField.getText();
    if (estado_civil_str.isEmpty()) return null;
    EstadoCivil estado_civil = converteStringParaEstadoCivil(estado_civil_str);
    if (estado_civil == null) return null;
    String whatsapp = whatsappTextField.getText();
    if (whatsapp.isEmpty()) whatsapp = null;
    return new Amigo(nome, apelido, cidade, email, sexo, estado_civil, whatsapp);
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `String nome = nomeTextField.getText();` → Lê o texto digitado no campo `nomeTextField` (método `.getText()`, padrão de todo `JTextField`).
> - `if (nome.isEmpty()) return null;` → Se o nome estiver vazio, o método **retorna imediatamente** `null` — nome é **obrigatório** (é a chave primária da entidade), então não faz sentido continuar lendo os outros campos.
> - `String apelido = apelidoTextField.getText(); if (apelido.isEmpty()) apelido = null;` → Diferente do nome, o apelido é **opcional**: se estiver vazio, em vez de abortar o método, apenas define a variável `apelido` como `null` (representando "sem apelido") e continua a execução.
> - `cidade` e `email` → Ambos são tratados como **obrigatórios**, igual ao nome: campo vazio causa retorno imediato de `null`.
> - `String sexo_str = sexoTextField.getText(); Sexo sexo = Sexo.feminino;` → Lê o texto do campo Sexo e, **por padrão**, assume `Sexo.feminino`.
> - `if (sexo_str.equals("M")) sexo = Sexo.masculino;` → Se o texto for exatamente `"M"`, sobrescreve para `Sexo.masculino`.
> - `else if (!sexo_str.equals("F")) return null;` → Caso contrário (não é "M"), verifica se **também não é** "F" — se não for nem "M" nem "F", é um valor inválido, e o método retorna `null`. Se for "F", a variável `sexo` permanece com o valor padrão já definido (`Sexo.feminino`), então nada precisa ser feito nesse caso.
> - `estado_civil_str` e `converteStringParaEstadoCivil(...)` → Lê o texto do campo Estado Civil e usa o método estático auxiliar (visto na página 15) para converter para o enum correspondente. Se o texto estiver vazio, retorna `null` direto; se não for uma letra válida (`C`, `D`, `S`, `V`), o método `converteStringParaEstadoCivil` retorna `null`, e esse `null` é detectado aqui (`if (estado_civil == null) return null;`), interrompendo o método.
> - `whatsapp` → Tratado como **opcional**, igual ao apelido: vazio se torna `null`, mas não interrompe a leitura.
> - `return new Amigo(nome, apelido, cidade, email, sexo, estado_civil, whatsapp);` → Se chegou até aqui, todos os campos obrigatórios foram validados com sucesso, e um objeto `Amigo` completo é criado e retornado, usando o **construtor completo** (com 7 parâmetros) — que, note, **não** foi mostrado explicitamente em nenhum bloco de código deste tutorial (só os dois construtores reduzidos foram mostrados: o de 0 argumentos implícito não existe, e o de 2 argumentos foi mostrado na Seção 5.1). O tutorial **presume implicitamente** a existência desse construtor completo de 7 argumentos, mas não reproduz seu código.

> **Observação do Claude (nota importante sobre lacuna do tutorial):**
> O construtor completo `Amigo(String nome, String apelido, String cidade, String email, Sexo sexo, EstadoCivil estado_civil, String whatsapp)`, usado na linha final deste método, **não é mostrado em nenhum lugar do texto fornecido do tutorial**. Isso é uma lacuna do material original (ou talvez estivesse em uma parte do PDF não capturada/reproduzida no texto disponível) — não é uma correção minha, apenas destaco que, ao seguir este tutorial, você **precisará escrever esse construtor por conta própria** com base nos nomes e tipos dos 7 atributos já declarados na classe `Amigo` (Seção 5), já que o professor não o exibe explicitamente.

> **🎯 Em resumo:**
> Você deveria compreender a lógica de **validação de formulário**: campos obrigatórios (nome, cidade, email, estado civil) abortam a leitura com `return null` se vazios; campos opcionais (apelido, whatsapp) apenas se tornam `null` mas não interrompem; e o campo Sexo usa um valor padrão (`feminino`) sobrescrito apenas se o texto for "M", com validação de que só "M" ou "F" são aceitos.

---

## [p. 17/22] O método informarErro

```java
private void informarErro (String mensagem) {
    JOptionPane.showMessageDialog (this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
}
```

> **Esclarecimento do Claude:**
> Muito semelhante ao `informarServiçoIndisponível` visto na Seção 4, mas: (1) a mensagem é um parâmetro variável (`mensagem`), não fixa; (2) o título é `"Erro"` em vez de `"Informação"`; e (3) o tipo do ícone/mensagem é `JOptionPane.ERROR_MESSAGE` (ícone de erro, geralmente um "X" vermelho) em vez de `INFORMATION_MESSAGE` (ícone de informação).

> Todos os métodos do controlador retornam uma mensagem de erro, caracterizando o insucesso ocorrido, ou retornam um string nulo, para sinalizar sucesso na execução da operação. A seguir a ilustração do método inserirAmigo da classe ControladorCadastroAmigos, do pacote controles:

```java
public String inserirAmigo (Amigo amigo) {
    Amigo amigo1 = Amigo.buscarAmigo(amigo.getNome());
    if (amigo1 == null) return Amigo.inserirAmigo(amigo);
    else return "Nome de amigo já cadastrado";
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `Amigo amigo1 = Amigo.buscarAmigo(amigo.getNome());` → Busca, no banco de dados, se **já existe** um amigo cadastrado com aquele mesmo nome (chamando o método estático `buscarAmigo` da entidade `Amigo`, visto a seguir).
> - `if (amigo1 == null) return Amigo.inserirAmigo(amigo);` → Se **não existe** (a busca retornou `null`), é seguro inserir: delega ao método estático `Amigo.inserirAmigo(amigo)` (da **entidade**, não do controlador — repare que têm o mesmo nome `inserirAmigo`, mas são métodos de classes diferentes!) e retorna o resultado dele (que será `null` em caso de sucesso, ou uma mensagem de erro de banco em caso de falha).
> - `else return "Nome de amigo já cadastrado";` → Se **já existe** um amigo com aquele nome, a inserção é rejeitada **antes mesmo de tentar no banco**, retornando essa mensagem de erro de regra de negócio.

> **🔎 Esclarecimento — atenção ao nome duplicado de método:**
> Existem **dois métodos diferentes chamados `inserirAmigo`**: um em `ControladorCadastroAmigos.inserirAmigo(Amigo)` (o do controlador, mostrado aqui, que **decide** se pode inserir) e outro em `Amigo.inserirAmigo(Amigo)` (da entidade, mostrado a seguir, que **efetivamente executa** o `INSERT` no banco). Isso é permitido em Java porque são métodos de classes diferentes — mas é importante não confundir os dois ao ler o código.

> Embora essa implementação seja simples, não conclua apressadamente que as implementações dos métodos dos controladores, por serem simples, possam ser incorporadas nas implementações das janelas da interface ou nas entidades. São os controladores que decidem sobre as regras de negócios da aplicação.
>
> Em uma biblioteca, por exemplo, o empréstimo de uma obra, envolve várias consistências: (a) se o consulente (usuário da biblioteca) está cadastrado; (b) se obra está cadastrada; (c) se existe um exemplar da obra disponível ou reservado para o consulente.

> **💡 O que o professor está tentando ensinar:**
> Este é um comentário pedagógico importante sobre **arquitetura de software**: mesmo que, neste tutorial (com a entidade simples `Amigo`), a lógica do controlador pareça trivial a ponto de "não parecer necessária" (poderia parecer mais simples colocar essa verificação direto na janela ou na entidade), o professor alerta que essa separação de responsabilidades é **fundamental** para aplicações mais complexas. Ele usa o exemplo de uma biblioteca (empréstimo de livros) para ilustrar que regras de negócio podem envolver **múltiplas verificações e múltiplas entidades**, e é justamente o controlador o lugar apropriado para coordenar essa lógica — evitando que a interface gráfica (que deveria só lidar com entrada/saída) ou as entidades (que deveriam só lidar com seus próprios dados) fiquem sobrecarregadas com regras de negócio.

---

## [p. 18/22]

> Após essas verificações, será necessário criar um empréstimo e remover a reserva para o consulente, quando for o caso. Observe que as interfaces só devem tratar de entrada e saída e as entidades só devem tratar dos seus dados. Portanto, a intermediação do controlador que acessa várias entidades para decidir sobre a viabilidade da operação é fundamental para isolar as funcionalidades de entrada e saídas das funcionalidades de manipulação dos dados das entidades.
>
> Imagine agora que você está implementando uma seguradora. As regras de negócio da seguradora são mais complexas e podem envolver a consistência de vários dados para decidir sobre a viabilidade da operação, bem como, a geração dos cálculos do seguro.

> **🎯 Em resumo:**
> Você deveria compreender o **princípio de separação de responsabilidades** (Separation of Concerns) que fundamenta toda a arquitetura do projeto: interfaces → entrada/saída; entidades → dados próprios; controladores → regras de negócio e coordenação entre entidades. Isso vale mesmo quando, num exemplo didático simples como este, a "regra de negócio" parece trivial demais para justificar uma camada separada.

> Os métodos de acesso à base de dados são definidos como estáticos (chamados diretamente pelo nome da classe) na classe entidade Amigo. A seguir a ilustração do método estático buscarAmigo da entidade Amigo, do pacote entidades:

```java
public static Amigo buscarAmigo (String nome) {
    String sql = "SELECT * FROM Amigos WHERE Nome = ?";
    ResultSet lista_resultados = null;
    Amigo amigo = null;
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setString(1, nome);
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) {
            amigo = new Amigo (nome,
                lista_resultados.getString("Apelido"),
                lista_resultados.getString("Cidade"),
                lista_resultados.getString("Email"),
                Sexo.values()[lista_resultados.getInt("Sexo")],
                EstadoCivil.values()[lista_resultados.getInt("EstadoCivil")],
                lista_resultados.getString("Whatsapp"));
        }
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {
        exceção_sql.printStackTrace ();
        amigo = null;
    }
    return amigo;
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `String sql = "SELECT * FROM Amigos WHERE Nome = ?";` → Aqui está o **primeiro exemplo completo de SQL parametrizado** no tutorial: o `?` é um "placeholder" (marcador de posição) que será substituído por um valor real antes da execução — evitando concatenar strings diretamente (e, portanto, evitando SQL Injection).
> - `PreparedStatement comando = BD.conexão.prepareStatement(sql);` → Prepara o comando SQL com o placeholder ainda não preenchido.
> - `comando.setString(1, nome);` → Substitui o **primeiro** `?` (índices começam em `1`, não em `0`, no JDBC) pelo valor da variável `nome`. O método `setString` é usado porque o tipo do parâmetro é texto.
> - `lista_resultados = comando.executeQuery();` → Executa a consulta já com o parâmetro preenchido.
> - Dentro do `while (lista_resultados.next())`: constrói um objeto `Amigo` usando o **construtor completo** (finalmente mostrado aqui, com 7 argumentos, embora de forma indireta — através da chamada, não da declaração do construtor em si):
>   - `nome` → usa o parâmetro já conhecido (não precisa ler de novo do resultado, já que foi usado no filtro `WHERE`).
>   - `lista_resultados.getString("Apelido")`, `getString("Cidade")`, `getString("Email")` → lê as colunas de texto diretamente.
>   - `Sexo.values()[lista_resultados.getInt("Sexo")]` → Esta linha é importante: `Sexo.values()` retorna um **array** com todos os valores do enum `Sexo`, na ordem de declaração (`{masculino, feminino}` → índice 0 = masculino, índice 1 = feminino). `lista_resultados.getInt("Sexo")` lê o número inteiro salvo no banco (o `ordinal()` gravado na Seção 5.1/3), e esse número é usado como **índice** para acessar o array `values()`, recuperando o valor de enum correspondente.
>   - O mesmo padrão se repete para `EstadoCivil.values()[lista_resultados.getInt("EstadoCivil")]`.
>   - `lista_resultados.getString("Whatsapp")` → lê o último campo, também como texto.
> - `catch (SQLException exceção_sql) { exceção_sql.printStackTrace (); amigo = null; }` → Se der erro, imprime o rastro do erro **e** garante que `amigo` seja `null` (mesmo que já fosse `null` por padrão — reforça explicitamente esse estado em caso de exceção).
> - `return amigo;` → Retorna o objeto encontrado, ou `null` se não foi encontrado nenhum registro (o `while` nunca executou) ou se houve erro.

> **🔎 Esclarecimento — a técnica `Enum.values()[índice]`:**
> Esta é a técnica padrão em Java para **reverter** a conversão enum→número feita ao salvar no banco (`.ordinal()`). Como `values()` retorna os valores na mesma ordem em que foram declarados no `enum`, e `ordinal()` retorna exatamente a posição (índice) dessa declaração, usar o `ordinal()` salvo como índice de `values()` recupera exatamente o mesmo valor de enum original. **Cuidado:** se a ordem de declaração dos valores no `enum` for alterada no código depois que já existirem dados salvos no banco, essa técnica vai recuperar o enum **errado** (porque os índices mudam de posição) — é uma armadilha comum ao se trabalhar dessa forma.

---

## [p. 18/22] O método estático inserirAmigo (entidade)

> A seguir a ilustração do método estático inserirAmigo da entidade Amigo, do pacote entidades:

```java
public static String inserirAmigo(Amigo amigo) {
    String sql = "INSERT INTO Amigos (Nome, Apelido, Cidade, Email, Sexo, EstadoCivil,"
        + " Whatsapp) VALUES (?,?,?,?,?,?,?)";
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setString(1, amigo.getNome()); 
        comando.setString(2, amigo.getApelido());
        comando.setString(3, amigo.getCidade());
        comando.setString(4, amigo.getEmail());
        comando.setInt(5, (amigo.getSexo().ordinal()));
        comando.setInt(6, amigo.getEstadoCivil().ordinal());
        comando.setString(7, amigo.getWhatsapp());
        comando.executeUpdate();
        comando.close();
        return null;
    } catch (SQLException exceção_sql) {
        exceção_sql.printStackTrace ();
        return "Erro na Inserção do Amigo no BD";
    }
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `String sql = "INSERT INTO Amigos (...) VALUES (?,?,?,?,?,?,?)";` → Monta o SQL de inserção com **sete** placeholders `?`, um para cada coluna da tabela `Amigos`. Note que a string SQL está dividida em duas linhas no código-fonte, concatenadas com `+` — apenas uma questão de formatação/legibilidade do código, sem efeito na string final.
> - `comando.setString(1, amigo.getNome());` até `comando.setString(4, amigo.getEmail());` → Preenche os 4 primeiros placeholders com valores de texto, na ordem: Nome, Apelido, Cidade, Email — usando os métodos "getter" (`getNome()`, `getApelido()` etc.) da própria classe `Amigo` (que, embora não mostrados explicitamente no texto, são presumidos existir, seguindo a convenção padrão de Java Beans).
> - `comando.setInt(5, (amigo.getSexo().ordinal()));` → Aqui, diferente dos campos de texto, usa-se `setInt` (não `setString`), porque a coluna `Sexo` no banco é `INT`. `amigo.getSexo()` retorna o valor do enum (`Sexo.masculino` ou `Sexo.feminino`), e `.ordinal()` converte esse valor para o número inteiro correspondente (0 ou 1), que é o que efetivamente é salvo.
> - `comando.setInt(6, amigo.getEstadoCivil().ordinal());` → Mesma lógica para o Estado Civil.
> - `comando.setString(7, amigo.getWhatsapp());` → Último campo, de volta a texto.
> - `comando.executeUpdate();` → Executa efetivamente o `INSERT` no banco (usando `executeUpdate`, já que é uma operação de escrita, sem retorno de linhas).
> - `return null;` → Retorna `null`, sinalizando **sucesso** (seguindo o padrão de retorno de erro já visto).
> - `catch (SQLException exceção_sql) { ...; return "Erro na Inserção do Amigo no BD"; }` → Em caso de erro (ex.: violação de chave primária, se o `INSERT` conflitar com alguma restrição do banco), imprime o rastro do erro e retorna uma mensagem de erro genérica.

> **🎯 Em resumo:**
> Você deveria compreender, desta parte, o padrão completo de **inserção parametrizada** via `PreparedStatement`: montar SQL com `?`, preencher cada posição com `setString`/`setInt` conforme o tipo da coluna (convertendo enums para inteiros via `.ordinal()`), executar com `executeUpdate()`, e retornar `null`/mensagem de erro conforme o padrão de tratamento de erro da aplicação.

---

## [p. 19/22] 5.2 - O Tratamento de Eventos para Consultar um Amigo

> Na implementação do tratador de eventos consultarAmigo associado ao botão de comando Consultar, a chave do amigo (nome) é lida no campo de texto rotulado por Nome. A partir da chave, o objeto da entidade Amigo é buscado diretamente a partir de um método estático da classe Amigo. Para consultas o controlador não é utilizado, porque não há necessidade de tomar nenhuma decisão a respeito da viabilidade da execução, como ocorre em situações de inserção, alteração ou remoção. Todos os métodos de acesso ao banco de dados são definidos como métodos estáticos da entidade associada à informação que será consultada, inserida, alterada ou removida no banco de dados.

> **Observação do Claude:**
> Este trecho contradiz, em parte, o texto real da implementação a seguir: a frase diz que "a chave do amigo (nome) é lida no campo de texto rotulado por Nome" — mas, no código real mostrado a seguir (`consultarAmigo`), a busca é feita a partir do **ComboBox de amigos cadastrados** (`amigos_cadastradosComboBox.getSelectedItem()`), **não** do campo de texto `nomeTextField`. Não corrigi o texto original, mas destaco essa aparente inconsistência: a explicação textual descreve uma abordagem, e o código implementa outra (usar a seleção do ComboBox, não digitar o nome manualmente no campo de texto).

> Observe que a utilização de nome como palavra-chave é aceitável numa aplicação restrita como um Clube de Amigos do Cinema, onde a quantidade de amigos é presumivelmente reduzida, mas não é adequado para um sistema que cadastre clientes ou pacientes, por exemplo. Nestes casos o CPF é a chave mais adequada porque podem existir pessoas com o mesmo nome.

> **💡 O que o professor está tentando ensinar:**
> Um ponto de **modelagem de dados** importante: usar `Nome` como chave primária só é aceitável em cenários de baixo risco de colisão de nomes (um clube pequeno de amigos). Em sistemas reais com muitos usuários (clientes, pacientes), usar um identificador único e não-repetível (como CPF) é obrigatório, já que nomes completos podem coincidir entre pessoas diferentes.

```java
private void consultarAmigo(java.awt.event.ActionEvent evt) { 
    Amigo visão = (Amigo) amigos_cadastradosComboBox.getSelectedItem ();
    Amigo amigo = null;
    String mensagem_erro = null;
    if (visão != null) {
        amigo = Amigo.buscarAmigo (visão.getNome());
        if (amigo == null) mensagem_erro = "Amigo não cadastrado";
    } else mensagem_erro = "Nenhum amigo selecionado";
    if (mensagem_erro == null) {
        nomeTextField.setText(amigo.getNome());
        String apelido = amigo.getApelido();
        if (apelido == null) apelido = "";
        apelidoTextField.setText(apelido);
        cidadeTextField.setText(amigo.getCidade());
        emailTextField.setText(amigo.getEmail());
        String whatsapp = amigo.getWhatsapp();
        if (whatsapp == null) whatsapp = "";
        whatsappTextField.setText(whatsapp);
        sexoTextField.setText(((amigo.getSexo() + "").toUpperCase().charAt(0) + ""));
        estado_civilTextField.setText(((amigo.getEstadoCivil() + "").toUpperCase().charAt(0)
            + ""));
    } else informarErro (mensagem_erro);
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `Amigo visão = (Amigo) amigos_cadastradosComboBox.getSelectedItem ();` → Lê o item atualmente selecionado no ComboBox. Como `getSelectedItem()` retorna genericamente `Object` (porque o ComboBox foi configurado sem tipo genérico, lembra do "remova a palavra String" na Seção 5?), é necessário um **cast** explícito para `Amigo`.
> - `if (visão != null) { amigo = Amigo.buscarAmigo (visão.getNome()); ... } else mensagem_erro = "Nenhum amigo selecionado";` → Se havia algum item selecionado no ComboBox, usa o nome dessa "visão reduzida" para buscar, no banco, o objeto `Amigo` **completo** correspondente. Se nada estava selecionado, define mensagem de erro direto.
> - `if (amigo == null) mensagem_erro = "Amigo não cadastrado";` → Situação (teoricamente rara, mas possível — por exemplo, se o amigo foi removido do banco por outro processo entre o momento em que o ComboBox foi populado e agora) em que a visão existia no ComboBox, mas o registro completo não foi encontrado no banco.
> - Se não houve erro, os campos de texto são preenchidos com os dados do amigo encontrado:
>   - `nomeTextField.setText(amigo.getNome());`, `cidadeTextField.setText(...)`, `emailTextField.setText(...)` → Preenchimento direto, já que esses campos são obrigatórios (nunca são `null`).
>   - `String apelido = amigo.getApelido(); if (apelido == null) apelido = "";` → Como apelido é **opcional** (pode ser `null` no objeto), converte `null` para string vazia `""` antes de colocar no campo de texto — porque `setText(null)` causaria comportamento inesperado ou erro.
>   - O mesmo padrão se repete para `whatsapp`.
>   - `sexoTextField.setText(((amigo.getSexo() + "").toUpperCase().charAt(0) + ""));` → Esta linha é mais complexa; decompondo:
>     - `amigo.getSexo()` → retorna o enum (ex.: `Sexo.masculino`).
>     - `+ ""` → concatenar com string vazia converte implicitamente o enum para sua representação textual, chamando automaticamente o método `.toString()` do enum (que, por padrão em Java, retorna o **nome** do valor, ex.: `"masculino"`).
>     - `.toUpperCase()` → converte para maiúsculas: `"MASCULINO"`.
>     - `.charAt(0)` → pega apenas o **primeiro caractere**: `'M'` (um `char`, não uma `String`).
>     - `+ ""` (novamente) → converte esse `char` de volta para uma `String` de um único caractere: `"M"`.
>     - Resultado final: o campo de texto Sexo é preenchido com a letra maiúscula inicial do valor do enum (`"M"` para masculino, `"F"` para feminino).
>   - O mesmo padrão (`+ "").toUpperCase().charAt(0) + ""`) se repete para `estado_civilTextField`, convertendo, por exemplo, `EstadoCivil.casado` em `"C"`.

> **🔎 Esclarecimento:**
> Essa técnica de `(enum + "").toUpperCase().charAt(0) + ""` é uma forma "manual" e um pouco indireta de obter a primeira letra maiúscula do nome de um enum. Ela funciona corretamente para os valores usados aqui (masculino/feminino → M/F; solteiro/casado/divorciado/viúvo → S/C/D/V), mas é importante notar que esse tipo de conversão é **frágil**: se, no futuro, alguém adicionar um novo valor de enum cuja primeira letra colida com uma já existente, essa técnica pode gerar ambiguidade. É o "espelho" da função `converteStringParaEstadoCivil` vista antes (que faz o caminho inverso: de letra para enum).

> **🎯 Em resumo:**
> Você deveria compreender como o processo de consulta funciona: seleciona-se um item no ComboBox (não digitando o nome), busca-se o objeto completo no banco a partir dessa seleção, e os campos de texto do formulário são preenchidos com os dados encontrados — havendo um cuidado especial para tratar valores opcionais (`null` → string vazia) e para converter enums de volta para a representação de uma letra usada na interface (M/F, S/C/D/V).

---

## [p. 19/22] 5.3 - O Tratamento de Eventos para Alterar um Amigo

> A implementação do tratador de eventos alterarAmigo associado ao botão de comando Alterar, é semelhante à implementação do tratador de eventos inserirAmigo.

```java
private void alterarAmigo(java.awt.event.ActionEvent evt) { 
    Amigo amigo = obterAmigoInformado();
    String mensagem_erro = null;
    if (amigo != null) mensagem_erro = controlador.alterarAmigo(amigo);
    else mensagem_erro = "Algum atributo do amigo não foi informado";
    if (mensagem_erro == null) {
        Amigo visão = getVisãoAlterada(amigo.getNome());
        if (visão != null) {
            visão.setApelido(amigo.getApelido());
            amigos_cadastradosComboBox.updateUI();
            amigos_cadastradosComboBox.setSelectedItem(visão);
        }
    } else informarErro (mensagem_erro);;
}
```

> **Esclarecimento do Claude — linha a linha:**
> - As primeiras linhas seguem exatamente o mesmo padrão de `inserirAmigo`: lê o formulário (`obterAmigoInformado()`), delega ao controlador (`controlador.alterarAmigo(amigo)`), e trata erro se algum campo estiver vazio.
> - `Amigo visão = getVisãoAlterada(amigo.getNome());` → Após uma alteração bem-sucedida, busca (não no banco, mas na lista local `amigos_cadastrados` já carregada em memória) a "visão" correspondente ao nome alterado, usando o método auxiliar `getVisãoAlterada` (visto a seguir).
> - `if (visão != null) { visão.setApelido(amigo.getApelido()); ... }` → Se a visão foi encontrada, atualiza o **apelido** dessa visão (já que apelido é o único atributo, além do nome, que aparece na visão reduzida exibida no ComboBox — os demais atributos alterados, como cidade ou email, não afetam o que é mostrado no ComboBox).
> - `amigos_cadastradosComboBox.updateUI();` → Força o ComboBox a **redesenhar** sua interface, refletindo a alteração feita no objeto de visão (já que apenas mudar um atributo do objeto, sem chamar algum método de atualização visual, não faria o Swing perceber a mudança automaticamente).
> - `amigos_cadastradosComboBox.setSelectedItem(visão);` → Mantém a visão alterada selecionada no ComboBox, como feedback visual.

> Para alterar um amigo, é necessário informar todos os dados do mesmo, podendo alterar qualquer atributo, menos a sua chave (nome). O usuário tem duas possibilidades: (a) consultar o amigo de interesse e alterar os atributos que deseja; ou (b) preencher todos os campos do formulário. Se optar pela segunda opção, o usuário poderá informar a chave do usuário, independentemente do usuário selecionado no usuários_cadastradosComboBox. Para previnir essa situação, é necessário procurar a visão cadastrada que corresponde à chave informada pelo usuário, para atualizar os demais atributos informados na visão. Para suportar essa funcionalidade, é utilizado o método auxiliar privado getVisãoAlterada:

> **Observação do Claude:**
> Este parágrafo contém um erro de nomenclatura: fala em "usuários_cadastradosComboBox" e "chave do usuário" / "usuário selecionado" — mas em todo o resto do tutorial, a entidade em questão é "Amigo", e o componente correto é `amigos_cadastradosComboBox` (não "usuários_cadastradosComboBox"). Isso sugere fortemente que este trecho de texto foi **copiado/adaptado de um tutorial anterior do professor** (talvez sobre cadastro de "Usuários", de outra disciplina ou versão do material) e não foi totalmente ajustado para a terminologia de "Amigo" usada neste tutorial. Mantive a transcrição fiel, mas alerto: entenda "usuário" aqui como "amigo" e "usuários_cadastradosComboBox" como `amigos_cadastradosComboBox`, para fazer sentido com o restante do código.

> **🔎 Esclarecimento (apesar da inconsistência de nomes acima):**
> A ideia central do parágrafo é válida e importante: como a chave (nome) do amigo é **editável** no campo de texto `nomeTextField` (mesmo quando o usuário está no fluxo de "alterar"), existe o risco de o usuário, ao preencher manualmente todo o formulário para alterar, digitar um nome **diferente** do que está selecionado no ComboBox. O método `getVisãoAlterada` existe justamente para lidar com esse cenário: ele procura, na lista local de visões, qual delas tem o nome que foi de fato enviado para alteração (que pode não ser o mesmo da seleção atual do ComboBox).

```java
private Amigo getVisãoAlterada(String nome) {
    for (Amigo visão : amigos_cadastrados) {
        if (visão.getNome().equals(nome)) return visão;
    }
    return null;
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `for (Amigo visão : amigos_cadastrados) {` → Um **for-each** (laço "para cada"), percorrendo todos os elementos do array `amigos_cadastrados` (a lista de visões carregada no construtor da janela).
> - `if (visão.getNome().equals(nome)) return visão;` → Compara o nome de cada visão com o nome procurado (usando `.equals()`, a forma correta de comparar `String`s em Java por conteúdo, não por referência com `==`). Se encontrar, retorna imediatamente essa visão.
> - `return null;` → Se o laço terminar sem encontrar (percorreu todo o array sem dar `return` dentro do `if`), retorna `null`.

> A seguir, a ilustração do método alterarAmigo da classe ControladorCadastroAmigos, do pacote controles:

```java
public String alterarAmigo(Amigo amigo) {
    Amigo amigo1 = Amigo.buscarAmigo(amigo.getNome());
    if (amigo1 != null) return Amigo.alterarAmigo(amigo);
    else return "Nome de amigo não cadastrado";
}
```

> **Esclarecimento do Claude:**
> Note a **lógica invertida** em relação ao `inserirAmigo` do controlador (Seção 5.1): lá, encontrar um amigo existente era um **erro** (não se pode inserir duplicado); aqui, **não** encontrar um amigo existente é o erro (não se pode alterar algo que não existe). O restante da estrutura é análogo: se a verificação passar, delega para o método estático da entidade (`Amigo.alterarAmigo`, mostrado a seguir), que executa o `UPDATE` de fato.

> A seguir, a ilustração do método estático alterarAmigo da entidade Amigo, do pacote entidades:

```java
public static String alterarAmigo (Amigo amigo) {
    String sql = "UPDATE Amigos SET Apelido = ?, Cidade = ?, Email = ?, Sexo = ?," 
        + " EstadoCivil = ?, Whatsapp = ? WHERE Nome = ?";
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql); 
        comando.setString(1, amigo.getApelido());
        comando.setString(2, amigo.getCidade());
        comando.setString(3, amigo.getEmail());
        comando.setInt(4, amigo.getSexo().ordinal());
        comando.setInt(5, amigo.getEstadoCivil().ordinal());;
        comando.setString(6, amigo.getWhatsapp());
        comando.setString(7, amigo.getNome()); 
        comando.executeUpdate();
        comando.close();
        return null;
    } catch (SQLException exceção_sql) {
        exceção_sql.printStackTrace ();
        return "Erro na Alteração do Amigo no BD";
    }
}
```

> **Esclarecimento do Claude — linha a linha:**
> - `String sql = "UPDATE Amigos SET Apelido = ?, ... WHERE Nome = ?";` → Um `UPDATE` que altera todas as colunas **exceto** `Nome` (que é a chave primária e não pode ser alterada — como o próprio texto explicou anteriormente), e usa `Nome` apenas na cláusula `WHERE`, para identificar **qual** registro alterar.
> - Note a ordem dos parâmetros: como o `Nome` aparece **por último** no SQL (na cláusula `WHERE`), ele também é preenchido por último (`comando.setString(7, amigo.getNome());`), ocupando a **sétima e última** posição de `?`, mesmo sendo semanticamente "a chave" — a ordem de preenchimento dos parâmetros deve sempre seguir a ordem em que os `?` aparecem no texto SQL, da esquerda para a direita.
> - As demais linhas seguem o mesmo padrão de `setString`/`setInt` já visto no método `inserirAmigo` (entidade).

> **Observação do Claude:**
> Há um ponto e vírgula duplicado (`;;`) no final da linha `comando.setInt(5, amigo.getEstadoCivil().ordinal());;` no código original. Isso é tecnicamente válido em Java (um `;` sozinho é uma "instrução vazia" válida, não gera erro de compilação), mas é uma imperfeição estilística do código original. Mantido fielmente, sem correção, conforme solicitado.

---

## [p. 20/22] 5.4 - O Tratamento de Eventos para Remover um Amigo

> Na implementação do tratador de eventos removerAmigo associado ao botão de comando Remover, a chave do amigo (nome) é lida no campo de texto rotulado por Nome:

> **Observação do Claude:**
> Assim como na Seção 5.2 (Consultar), esta frase descreve a leitura do nome a partir do "campo de texto rotulado por Nome", mas o código real (a seguir) usa a **seleção do ComboBox** (`amigos_cadastradosComboBox.getSelectedItem()`), não o campo de texto. É a mesma inconsistência textual já apontada antes — provavelmente um trecho de texto reaproveitado/copiado de outra parte do material sem ajuste total.

```java
private void removerAmigo(java.awt.event.ActionEvent evt) { 
    Amigo visão = (Amigo) amigos_cadastradosComboBox.getSelectedItem ();
    String mensagem_erro = null;
    if (visão != null) mensagem_erro = controlador.removerAmigo(visão.getNome());
    else mensagem_erro = "Nenhum amigo selecionado";
    if (mensagem_erro == null) {
        amigos_cadastradosComboBox.removeItem(visão);
        limparCampos();
    } else informarErro (mensagem_erro);;
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - `Amigo visão = (Amigo) amigos_cadastradosComboBox.getSelectedItem ();` → Igual à consulta: lê o item selecionado no ComboBox.
> - `if (visão != null) mensagem_erro = controlador.removerAmigo(visão.getNome());` → Se havia seleção, delega ao controlador a remoção, passando apenas o **nome** (chave) do amigo — não é necessário buscar o objeto completo para remover, já que a remoção só precisa da chave.
> - `if (mensagem_erro == null) { amigos_cadastradosComboBox.removeItem(visão); limparCampos(); }` → Se a remoção foi bem-sucedida, remove o item correspondente do ComboBox (sem precisar recarregar tudo do banco) e limpa os campos do formulário (chamando `limparCampos()`).

> **Observação do Claude:**
> Repare que aqui `limparCampos()` é chamado **sem argumento** (`limparCampos();`), enquanto em outros lugares do tutorial (no construtor da janela, Seção 5) foi chamado como `limparCampos(null)`. Isso é uma **inconsistência real de código**, não apenas de texto: olhando a assinatura do método `limparCampos` (vista na Seção 5.5, a seguir), ele é declarado como `limparCampos(java.awt.event.ActionEvent evt)` — ou seja, **espera um argumento**. Chamá-lo como `limparCampos()` (sem nenhum argumento) causaria, na realidade, um **erro de compilação** em Java, porque não existe uma versão sobrecarregada de `limparCampos` que aceite zero argumentos. Este é, portanto, um **erro técnico real presente no tutorial original**, que preservei integralmente conforme as suas instruções (não corrigir silenciosamente). Se você for implementar este código, **você precisará corrigir esta chamada** para `limparCampos(null)`, ou criar uma sobrecarga de `limparCampos()` sem parâmetros — caso contrário, o código não compilará.

> Novamente há também aqui um ponto e vírgula duplicado (`informarErro (mensagem_erro);;`) — mesma observação estilística de antes, sem efeito funcional.

> A seguir, a ilustração do método removerAmigo da classe ControladorCadastroAmigos, do pacote controles:

```java
public String removerAmigo (String nome) {
    Amigo amigo1 = Amigo.buscarAmigo(nome);
    if (amigo1 != null) return Amigo.removerAmigo(nome);
    else return "Nome de amigo não cadastrado";
}
```

> **Esclarecimento do Claude:**
> Mesma lógica de verificação da existência do registro antes de agir, já vista em `alterarAmigo` do controlador: só remove se o amigo **existir** no banco; caso contrário, retorna mensagem de erro.

> A seguir a ilustração do método estático removerAmigo da entidade Amigo, do pacote entidades:

```java
public static String removerAmigo (String nome) {
    String sql = "DELETE FROM Amigos WHERE Nome = ?";
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setString(1, nome);
        comando.executeUpdate();
        comando.close();
        return null;
    } catch (SQLException exceção_sql) {
        exceção_sql.printStackTrace ();
        return "Erro na Remoção do Amigo no BD";
    }
}
```

> **Esclarecimento do Claude:**
> Padrão idêntico aos métodos anteriores (`inserirAmigo`, `alterarAmigo` da entidade): SQL parametrizado, preenchimento do `?`, `executeUpdate()`, retorno `null`/mensagem de erro. Neste caso, o `DELETE` remove a linha cujo `Nome` corresponda exatamente ao parâmetro informado.

> **🎯 Em resumo:**
> Você deveria compreender que os **três métodos estáticos de escrita** da entidade `Amigo` (`inserirAmigo`, `alterarAmigo`, `removerAmigo`) seguem exatamente o mesmo padrão estrutural: montar SQL parametrizado com `?`, criar `PreparedStatement` a partir de `BD.conexão`, preencher os parâmetros na ordem correta, executar com `executeUpdate()`, fechar o comando e retornar `null` (sucesso) ou uma mensagem de erro específica (falha).

---

## [p. 21/22] 5.5 - O Tratamento de Eventos para Limpar Campos

> O tratador de eventos limparCampos é ilustrado a seguir.

```java
private void limparCampos(java.awt.event.ActionEvent evt) { 
    nomeTextField.setText("");
    apelidoTextField.setText("");
    cidadeTextField.setText("");
    emailTextField.setText("");
    sexoTextField.setText("");
    estado_civilTextField.setText("");
    whatsappTextField.setText("");
} 
```

> **Esclarecimento do Claude — linha a linha:**
> - Cada linha simplesmente chama `.setText("")` em um dos sete campos de texto do formulário, apagando qualquer conteúdo digitado (ou lido de uma consulta anterior) e deixando o campo vazio, preparado para uma nova operação de cadastro (Inserir) ou nova consulta.
> - Como já mencionado (página 16), o parâmetro `evt` **não é usado** dentro do corpo do método — ele existe apenas porque o NetBeans gera automaticamente essa assinatura para métodos associados a eventos `actionPerformed` de botões (é o motivo pelo qual o método pode ser chamado manualmente passando `null` para esse parâmetro, já visto no construtor da janela).

> **Observação do Claude (ligando de volta com a inconsistência da Seção 5.4):**
> Este bloco de código **confirma** a inconsistência já apontada: a assinatura real do método é `limparCampos(java.awt.event.ActionEvent evt)`, com **um parâmetro obrigatório**. Isso reforça que a chamada `limparCampos();` (sem argumentos), vista no método `removerAmigo` (Seção 5.4), de fato **não compilaria** como está escrita no tutorial original — seria necessário escrever `limparCampos(null);` naquele ponto também, para manter a consistência com esta assinatura.

> **🎯 Em resumo:**
> Você deveria compreender que `limparCampos` é o método mais simples de todos nesta janela: apenas esvazia os sete campos de texto do formulário, sendo reutilizado tanto como tratador de evento do botão "Limpar" quanto internamente, chamado manualmente em outros pontos do código (construtor da janela, remoção bem-sucedida) — desde que sempre se passe um argumento (mesmo que `null`) na chamada, dado que o método exige um parâmetro.

---

## [p. 22/22] 6 - Ilustrando uma Inserção na JanelaCadastroAtores

> A classe Ator, do pacote entidades, é definida com os seguintes enumerados e atributos:

```java
public class Ator {

    public enum Sexo { feminino, masculino };

    private String nome;
    private int ano_nascimento;
    private Sexo sexo;
    private boolean ganhador_oscar;
```

> **Esclarecimento do Claude — linha a linha:**
> - `public enum Sexo { feminino, masculino };` → Um enum `Sexo` **próprio** da classe `Ator` (não é o mesmo enum `Sexo` da classe `Amigo` — são dois enums diferentes, cada um aninhado em sua respectiva classe, apenas com o mesmo nome).
> - `private int ano_nascimento;` → Diferente de todos os atributos de `Amigo` (que eram `String` ou enum), este atributo é um `int` (número inteiro) — representando o ano de nascimento do ator.
> - `private boolean ganhador_oscar;` → Um atributo do tipo `boolean` (verdadeiro/falso), representando se o ator já ganhou um Oscar — que corresponde exatamente à coluna `GanhadorOscar BIT(1)` vista no script SQL da Seção 3.

> **Observação do Claude — atenção à ordem dos valores do enum Sexo:**
> Note que, na classe `Ator`, o enum `Sexo` é declarado como `{ feminino, masculino }` — ou seja, **feminino é o índice 0 e masculino é o índice 1**. Isso é **o inverso** da ordem usada no enum `Sexo` da classe `Amigo`, visto na Seção 5, que era `{ masculino, feminino }` (masculino = índice 0, feminino = índice 1). Esta é uma diferença real e importante entre as duas classes: se o código de acesso ao banco de dados da entidade `Ator` (não mostrado neste tutorial, mas presumido "equivalente" ao de `Amigo`) usar a mesma técnica de `Sexo.values()[índice]` vista na Seção 5.2/5, o resultado da conversão número↔enum será **diferente** entre `Amigo` e `Ator`, exatamente por causa dessa inversão na ordem de declaração. Isso não é um erro — é apenas uma escolha diferente do professor entre as duas classes —, mas é importante você não presumir que o "índice 0" significa a mesma coisa (masculino) nas duas entidades.

> Para a JanelaCadastroAtores é ilustrada somente a interface com um objeto preenchido, dado que a sua implementação é equivalente à implementação da JanelaCadastroAmigos.

**[Imagem no PDF original: janela "Cadastrar Atores" preenchida — Atores Cadastrados: "Kate Winslet [1975]"; Nome: "Kate Winslet"; Ano de Nascimento: "1975"; Sexo: "F"; Ganhador de Oscar: "S"; botões Inserir, Consultar, Alterar, Remover, Limpar]**

> **🔎 Esclarecimento:**
> O ComboBox de Atores mostra "Nome [AnoNascimento]" (ex.: "Kate Winslet [1975]") — uma "visão" análoga à de `Amigo` ("Nome [Apelido]"), mas usando o ano de nascimento no lugar do apelido, já que a entidade `Ator` não tem um atributo "apelido".
>
> O campo "Ganhador de Oscar" aparece na interface como um campo de texto simples, com valor `"S"` — sugerindo que, assim como Sexo e EstadoCivil em `Amigo`, o valor booleano `ganhador_oscar` também é representado, na interface deste Tutorial 1, como uma **letra digitada pelo usuário** (provavelmente "S" para Sim e "N" para Não), e não como um componente gráfico dedicado a booleanos (como um `JCheckBox`). Isso é consistente com a frase da Seção 2 do tutorial, que informa que "Neste Tutorial, todos os atributos das entidades são lidos como textos" e que "No Tutorial 2 serão ilustrados: (...) seleção de item do tipo boolean (ex: ganhador_oscar)" — ou seja, o uso de um componente visual apropriado para booleanos (como um checkbox) é uma melhoria que **só será implementada no Tutorial 2**, não neste.

> **🎯 Em resumo:**
> Você deveria compreender que a classe `Ator` segue a mesma estrutura geral de `Amigo` (enumerado de Sexo, atributos simples, cadastro via janela com ComboBox + campos de texto + botões), mas com diferenças específicas: usa `int` para ano de nascimento, `boolean` para ganhador_oscar, e a ordem dos valores do enum `Sexo` é invertida em relação à classe `Amigo`. Como o próprio tutorial afirma que a implementação completa é "equivalente", o texto fornecido **não detalha** o código de `JanelaCadastroAtores`, `ControladorCadastroAtores` nem os métodos estáticos de acesso a banco da entidade `Ator` — apenas a estrutura de atributos e a imagem da interface preenchida.

---

## Encerramento do Guia

Este guia cobriu, na íntegra e na ordem original, todo o conteúdo textual e todos os trechos de código do **Tutorial 1 de LPII — "Menu e Cadastro Básico"**, das páginas 1 a 22, incluindo:
1. Instalação do ambiente e teste isolado de conexão JDBC (classe `TesteBD`, Seção 1);
2. Estruturação em pacotes do projeto de referência (Seção 2);
3. Classe `BD` de conexão e script SQL das tabelas `Amigos`/`Atores` (Seção 3);
4. Construção da janela principal `JanelaAmigosCinema` com menus, barra de menus e itens de menu, incluindo o tratamento de fechamento da aplicação (Seção 4);
5. Construção completa da `JanelaCadastroAmigos`, incluindo entidade `Amigo`, layout `GridBagLayout`, e os cinco tratadores de evento (Inserir, Consultar, Alterar, Remover, Limpar), além dos métodos estáticos correspondentes nas classes `ControladorCadastroAmigos` e `Amigo` (Seção 5, com subseções 5.1 a 5.5);
6. Estrutura da entidade `Ator` e ilustração da interface `JanelaCadastroAtores` (Seção 6).

Como destacado no aviso no início deste documento, **o código da Seção 1 (`TesteBD`) é isolado e não faz parte da arquitetura final do projeto** — foi apenas um teste de conectividade. A base real do projeto "Clube de Amigos do Cinema", que continuará sendo expandida nos Tutoriais 2, 3 e 4, está nas Seções 2 a 6.
