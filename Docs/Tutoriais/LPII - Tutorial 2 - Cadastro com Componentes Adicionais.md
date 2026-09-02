# LPII — Tutorial 2 — Cadastro com Componentes Adicionais
### Material de Estudo Detalhado (Texto Original + Observações do Claude)

> **Observação do Claude:**
> Este documento segue rigorosamente o texto original do tutorial (Prof. Joinvile Batista Junior — Sistemas de Informação — FACET/UFGD), preservando todas as palavras, frases, parágrafos e trechos de código exatamente como aparecem no PDF. Tudo que **eu** (Claude) adiciono está marcado explicitamente em blocos de citação com os títulos `Observação do Claude`, `Esclarecimento`, `💡 O que o professor está tentando ensinar` e `🎯 Em resumo`. Qualquer texto fora desses blocos é reprodução literal do tutorial.

---

## 1 — Introdução

> Neste tutorial, serão introduzidos: (a) novos componentes gráficos; (b) utilização de ComboBox com valores fixos; e (c) a representação de múltiplos referências entre objetos.
>
> Atributos poderão ser melhor representados com a utilização dos seguintes componentes gráficos:
> - CheckBox : para representar atributos booleanos;
> - grupo de botões de rádio (RadioButton) : para representar enumerados com pouco elementos (máximo de 5);
> - ComboBox com valores fixos : para representar enumerados com pouco elementos (acima de 5).
>
> O componente List tem uma função semelhante ao ComboBox, suportando a inserção e remoção de elementos, mas mostrando vários elementos ao mesmo tempo; diferentemente do ComboBox que precisa ser aberto para mostrar seus elementos. O List será utilizado para mostrar os relacionamentos de um objeto com vários objetos de uma outra entidade.
>
> As seções 2 e 3 deste tutorial, são utilizadas para caracterizar a atualização das janelas do Tutorial 1, para a representação mais adequada de atributos baseados em enumerados com poucos valores ou booleanos.
>
> Na seção 4, será descrita a janela de cadastro da entidade Filme que tem um relacionamento de muitos para muitos (n:n) com a entidade Ator. Observe que: (a) em um filme atuam vários atores; e (b) um ator pode atuar em vários filmes.
>
> Na seção 5, serão comentadas as diferenças que ocorrem em relacionamentos de um para muitos (1:n), utilizando como exemplo a relação entre as entidades Montadora e Veículo. Observe que: (a) uma montadora fabrica vários veículos; mas (b) um veículo é fabricado por uma única montadora.

> **💡 O que o professor está tentando ensinar:**
> Esta introdução é um "mapa" do tutorial. O professor está avisando que este tutorial não trata mais só de telas simples de cadastro (como no Tutorial 1), mas de duas coisas novas:
> 1. **Como representar melhor tipos de dados especiais na interface gráfica** — em vez de usar um simples campo de texto para tudo, agora atributos booleanos (verdadeiro/falso) e atributos enumerados (um valor escolhido dentre um conjunto fixo de opções) ganham componentes visuais próprios: CheckBox, RadioButton e ComboBox.
> 2. **Como representar relacionamentos entre entidades no banco de dados e na interface**, cobrindo dois casos clássicos de modelagem: relacionamento **muitos-para-muitos (n:n)** (Filme ↔ Ator) e relacionamento **um-para-muitos (1:n)** (Montadora → Veículo).

> **🔎 Esclarecimento:**
> Um "enumerado" (ou `enum`, em Java) é um tipo de dado que só pode assumir um valor dentre uma lista fixa e pré-definida de opções — por exemplo, o "sexo" só pode ser feminino ou masculino; o "estado civil" só pode ser solteiro, casado, divorciado ou viúvo. A regra prática que o professor estabelece aqui é: se o enumerado tem **poucos elementos (até 5)**, usa-se RadioButton (todos os botões visíveis ao mesmo tempo); se tem **mais de 5 elementos**, usa-se ComboBox (que exige um clique para abrir e escolher, economizando espaço na tela). Já o CheckBox é reservado para atributos que só têm dois estados possíveis e que representam diretamente um "sim/não" (booleano), como "Ganhador de Oscar".

> **🎯 Em resumo:**
> Depois de ler esta introdução, você deve saber que este tutorial ensina (1) a trocar campos de texto genéricos por componentes gráficos apropriados a cada tipo de atributo (booleano ou enumerado), e (2) a modelar e programar relacionamentos n:n e 1:n entre entidades, tanto no banco de dados (SQL) quanto nas classes Java e nas janelas correspondentes.

---

## 2 — Atualizando a JanelaCadastroAmigos

> A JanelaCadastroAmigos será atualizada com grupo de botões de rádio para representar os atributos sexo e estado_civil. A visualização da janela é mostrada a seguir.

**(Imagem no tutorial original: tela "Cadastrar Amigos" mostrando os campos Nome, Apelido, Sexo — com radio buttons "feminino"/"masculino" — Estado Civil — com radio buttons "solteiro"/"casado"/"divorciado"/"viúvo" —, Cidade, Email, Whatsapp, e os botões Inserir, Consultar, Alterar, Remover, Limpar.)**

> Inicialmente vamos comentar como configurar o componente RadioButton no NetBeans. A seguir, vamos comentar as alterações nos métodos: obterAmigoInformado, consultarAmigo e limparCampos.

> **💡 O que o professor está tentando ensinar:**
> Esta seção mostra, na prática, como o Tutorial 1 (que provavelmente usava um simples `TextField` para o sexo e o estado civil, digitados como texto livre) é evoluído para usar componentes gráficos apropriados. A ordem pedagógica escolhida é: primeiro configurar o componente visualmente no NetBeans (ferramenta de design de interface), depois ajustar o código dos três métodos que interagem com esses campos: ler o que o usuário preencheu (`obterAmigoInformado`), mostrar dados já cadastrados (`consultarAmigo`) e apagar os campos da tela (`limparCampos`).

### 2.1 - Configurando os Botões de Radio (RadioButton)

> Cada elemento de um enumerado é representado por um componente RadioButton. Para os elementos do enumerado sejam agrupados no layout da janela, eles devem ser encapsulados por um componente Panel, da mesma forma que os componentes Button utilizados para representar os comandos de: inserção, consulta, etc.
>
> Para que o grupo de botões de rádio tenha a função de ativar somente um elemento por vez, todos os botões deverão ser vinculados a um componente ButtonGroup. Na aba do Navegador, que mostrar a hierarquia dos componentes da página, o componente ButtonGroup fica vinculado ao diretório Other Components, caracterizando desta forma que esse componente não tem representação gráfica.
>
> A configuração das propriedades específicas de cada RadioButton será exemplificada para o atributo sexo. Para o sexo feminino o primeiro RadioButton será configurado da seguinte forma: (a) text: feminino; (b) buttonGroup: sexoButtonGroup (criado previamente). Para o sexo masculino, a configuração é equivalente, acrescentando a propriedade mnemonic: \u0001. Quando o enumerado tem mais de 2 elementos, a configuração de mnemonic prossegue com \u0002, etc.

> **🔎 Esclarecimento:**
> O texto do professor mistura dois conceitos distintos e é importante separá-los:
> 1. **Panel** — é usado apenas como um "container visual" para agrupar os RadioButtons na tela, deixando-os visualmente organizados lado a lado (como já era feito com os botões "Inserir", "Consultar" etc.). Isso é puramente estético/organizacional.
> 2. **ButtonGroup** — é o componente que garante o comportamento **lógico** de "só um selecionado por vez" (mutuamente exclusivos). Diferente do Panel, o ButtonGroup **não aparece na tela** (não tem representação gráfica); ele existe só no código, por isso o NetBeans o lista dentro de "Other Components" (Outros Componentes) na aba do Navegador, e não junto dos componentes visuais.
>
> É perfeitamente possível ter um Panel sem ButtonGroup (os botões apareceriam agrupados visualmente, mas o usuário poderia marcar vários ao mesmo tempo) e vice-versa. Aqui os dois são usados juntos: o Panel cuida do layout, o ButtonGroup cuida da exclusividade da seleção.

> **🔎 Esclarecimento:**
> A propriedade `mnemonic` aqui não está sendo usada em seu sentido mais comum de "atalho de teclado" (como Alt+letra). O professor está reaproveitando essa propriedade (que aceita um valor inteiro/código de caractere) como um **índice numérico** para identificar qual posição do enumerado aquele RadioButton representa:
> - RadioButton "feminino" → mnemonic não definido explicitamente (equivale a 0, primeiro elemento do enum);
> - RadioButton "masculino" → mnemonic `\u0001` (valor 1, segundo elemento do enum);
> - se houvesse um terceiro elemento, seria `\u0002` (valor 2), e assim por diante.
>
> `\u0001` é a notação Unicode para o caractere de código 1 (não é o caractere "1" que aparece na tela — é o valor numérico 1 codificado como caractere). Essa é a "ponte" que permite, mais adiante (seção 2.2), transformar a seleção do RadioButton diretamente em um índice do array de valores do enum, usando `Sexo.values()[indice]`.

> **💡 O que o professor está tentando ensinar:**
> A técnica central desta seção é: **usar o `mnemonic` de cada RadioButton como o índice do valor correspondente no enum**, para depois recuperar rapidamente qual opção foi selecionada, sem precisar escrever uma cadeia de `if`/`else` comparando o texto de cada botão.

> **🎯 Em resumo:**
> Você deve entender que: Panel agrupa visualmente, ButtonGroup impõe exclusividade lógica de seleção, e a propriedade `mnemonic` de cada RadioButton é reaproveitada para guardar o índice (posição) do valor do enum que aquele botão representa, começando em 0.

---

### 2.2 - Atualizando o método obterAmigoInformado

> O método obterAmigoInformado é atualizado para a leitura dos enumerados associados aos atributos sexo e estado_civil.

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
    Sexo sexo = null;
    if (sexoButtonGroup.getSelection() != null)
        sexo = Sexo.values()[sexoButtonGroup.getSelection().getMnemonic()];
    else return null;
    EstadoCivil estado_civil = null;
    if (estado_civilButtonGroup.getSelection() != null) {
        estado_civil = EstadoCivil.values()
            [estado_civilButtonGroup.getSelection().getMnemonic()];
    } else return null;
    String whatsapp = whatsappTextField.getText();
    if (whatsapp.isEmpty()) whatsapp = null;
    return new Amigo(nome, apelido, cidade, email, sexo, estado_civil, whatsapp);
}
```

> O método getSelection da classe ButtonGroup retorna o objeto RaddioButton selecionado. O método getMnemonic retorna o inteiro associado ao RaddioButton. Antes de obter o mnemônico é necessário testar se algum ButtonGroup foi selecionado.

> **Observação do Claude — explicação linha por linha:**
>
> - `private Amigo obterAmigoInformado() {` — declara um método privado (só usado dentro da própria classe da janela) que não recebe parâmetros e devolve um objeto `Amigo` (ou `null` se algo estiver faltando/inválido).
> - `String nome = nomeTextField.getText();` — lê o texto digitado no campo Nome.
> - `if (nome.isEmpty()) return null;` — se o nome estiver vazio, o método é interrompido imediatamente e devolve `null`, sinalizando que os dados informados são inválidos (nome é obrigatório).
> - As quatro linhas seguintes (`apelido`, `cidade`, `email`) repetem um padrão parecido, mas com uma diferença importante: **apelido** é opcional — se estiver vazio, em vez de abortar o método, ele é convertido para `null` (ausência de valor) e o processamento continua; já **cidade** e **email** são obrigatórios, seguindo o mesmo padrão do nome (retornam `null` se vazios).
> - `Sexo sexo = null;` — declara a variável `sexo`, do tipo enum `Sexo`, inicialmente sem valor.
> - `if (sexoButtonGroup.getSelection() != null)` — verifica se **algum** RadioButton do grupo `sexoButtonGroup` está selecionado. `getSelection()` devolve o botão marcado (como um objeto genérico do tipo `ButtonModel`) ou `null` se nenhum estiver marcado.
> - `sexo = Sexo.values()[sexoButtonGroup.getSelection().getMnemonic()];` — esta é a linha-chave da técnica explicada na seção anterior: `Sexo.values()` devolve um array com todos os valores possíveis do enum `Sexo` (na ordem em que foram declarados no `enum`); `sexoButtonGroup.getSelection().getMnemonic()` devolve o valor inteiro (mnemonic) configurado no RadioButton selecionado — 0 para feminino, 1 para masculino; esse número é usado como **índice** do array, obtendo diretamente o valor do enum correspondente.
> - `else return null;` — se nenhum RadioButton de sexo estiver selecionado, o método é interrompido e devolve `null` (sexo é obrigatório).
> - O bloco de `estado_civil` segue exatamente a mesma lógica, só que usando o enum `EstadoCivil` e o grupo `estado_civilButtonGroup`. Aqui o professor usou chaves `{ }` explícitas (diferente do bloco de `sexo`, que não usou), mas o comportamento é idêntico — isso é apenas uma diferença de estilo de escrita, não muda a lógica.
> - `String whatsapp = whatsappTextField.getText(); if (whatsapp.isEmpty()) whatsapp = null;` — o campo whatsapp é opcional, seguindo o mesmo padrão do apelido.
> - `return new Amigo(nome, apelido, cidade, email, sexo, estado_civil, whatsapp);` — por fim, cria e devolve um novo objeto `Amigo` com todos os dados coletados.

> **🔎 Esclarecimento:**
> O texto original do tutorial escreve "RaddioButton" (com dois "d") em vez de "RadioButton". Isso é preservado aqui por fidelidade ao texto original, mas trata-se aparentemente de um erro de digitação do professor — o nome correto do componente Swing é `JRadioButton`/`RadioButton`.

> **Observação do Claude:**
> Vale notar um detalhe técnico que o tutorial não menciona explicitamente: o método `getSelection()` de `ButtonGroup` devolve um objeto do tipo `ButtonModel` (não o `JRadioButton` em si), mas essa interface também expõe o método `getMnemonic()`, o que é o motivo pelo qual o código funciona mesmo sem fazer um "cast" explícito para `JRadioButton`.

> **🎯 Em resumo:**
> Você deve saber reproduzir o padrão: testar se o `ButtonGroup` tem seleção (`getSelection() != null`), e se tiver, usar `getMnemonic()` como índice em `EnumX.values()[indice]` para obter o valor do enum correspondente ao RadioButton marcado. Esse padrão evita comparações manuais de texto e é reutilizado para qualquer atributo enumerado representado por grupo de RadioButtons.

---

### 2.3 - Atualizando o método consultarAmigo

> O método obterAmigoInformado é atualizado para a informar os valores dos enumerados associados aos atributos sexo e estado_civil.

> **Observação do Claude:**
> Aqui parece haver um pequeno deslize do professor no enunciado: o título da subseção 2.3 e o próprio parágrafo dizem "O método obterAmigoInformado é atualizado...", mas o código logo abaixo é, na verdade, o do método **consultarAmigo** (nome que também aparece no título da seção "2.3 - Atualizando o método consultarAmigo"). Preservo o texto original tal como está, mas deixo registrado que a frase provavelmente deveria dizer "O método **consultarAmigo** é atualizado...".

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
        selecionarSexoRadioButton(amigo.getSexo().ordinal());
        selecionarEstadoCivilRadioButton(amigo.getEstadoCivil().ordinal());
        String whatsapp = amigo.getWhatsapp();
        if (whatsapp == null) whatsapp = "";
        whatsappTextField.setText(whatsapp);
    } else informarErro (mensagem_erro);
} 
```

> **Observação do Claude — explicação linha por linha:**
>
> - `private void consultarAmigo(java.awt.event.ActionEvent evt) {` — este é um **tratador de eventos** (event handler): é chamado automaticamente pelo Swing quando o usuário clica no botão "Consultar". O parâmetro `evt` carrega informações sobre o evento de clique (geralmente não é muito utilizado dentro do método).
> - `Amigo visão = (Amigo) amigos_cadastradosComboBox.getSelectedItem ();` — obtém o item atualmente selecionado no ComboBox de amigos cadastrados. O `(Amigo)` é um **cast** (conversão de tipo): o método `getSelectedItem()` devolve um `Object` genérico, então é preciso "avisar" o compilador de que, na prática, aquele objeto é do tipo `Amigo` (mais especificamente, uma "visão" — uma versão resumida do objeto, usada só para preencher o combo, como será detalhado adiante no tutorial).
> - `Amigo amigo = null;` e `String mensagem_erro = null;` — declaram variáveis auxiliares, inicialmente vazias, que serão preenchidas conforme o fluxo do método.
> - `if (visão != null) { ... } else mensagem_erro = "Nenhum amigo selecionado";` — se havia algum item selecionado no combo, tenta buscar o amigo completo no banco/lista (`Amigo.buscarAmigo(visão.getNome())`); se não havia seleção nenhuma, define a mensagem de erro apropriada.
> - `amigo = Amigo.buscarAmigo (visão.getNome());` — busca o objeto `Amigo` completo, usando o nome (chave de busca) daquela "visão" selecionada.
> - `if (amigo == null) mensagem_erro = "Amigo não cadastrado";` — trata o caso (improvável, mas possível) de o amigo não ser mais encontrado.
> - `if (mensagem_erro == null) { ... } else informarErro (mensagem_erro);` — se nenhum erro ocorreu até aqui, os campos da tela são preenchidos com os dados do amigo encontrado; caso contrário, chama-se `informarErro`, exibindo a mensagem ao usuário (presume-se, por inferência, que esse método já existia no Tutorial 1, mostrando uma caixa de diálogo de erro).
> - `nomeTextField.setText(amigo.getNome());` — preenche o campo Nome com o valor vindo do objeto amigo.
> - `String apelido = amigo.getApelido(); if (apelido == null) apelido = "";` — trata o caso de apelido nulo (não informado), convertendo para string vazia antes de exibir (evitando mostrar a palavra "null" na tela).
> - `cidadeTextField.setText(amigo.getCidade()); emailTextField.setText(amigo.getEmail());` — preenchem os campos Cidade e Email diretamente, sem tratamento de nulo (porque são obrigatórios, conforme visto no método `obterAmigoInformado`).
> - `selecionarSexoRadioButton(amigo.getSexo().ordinal());` — **este é o ponto central novo desta seção**: em vez de escrever texto num campo, chama-se um método auxiliar (definido na seção seguinte) passando o **índice ordinal** do valor do enum `Sexo` armazenado no amigo. `ordinal()` é um método de qualquer enum em Java que devolve a posição (começando em 0) em que aquele valor foi declarado no `enum`.
> - `selecionarEstadoCivilRadioButton(amigo.getEstadoCivil().ordinal());` — mesma lógica, para o estado civil.
> - As últimas linhas repetem o padrão de tratamento de nulo para o whatsapp (campo opcional).

> **🔎 Esclarecimento:**
> Aqui aparece uma relação interessante e simétrica com a seção 2.2: lá, o `mnemonic` do RadioButton selecionado era usado como índice para obter o valor do enum (`Sexo.values()[mnemonic]`); aqui, o caminho é o inverso — parte-se do valor do enum já conhecido (`amigo.getSexo()`), obtém-se seu índice com `.ordinal()`, e usa-se esse índice para **selecionar** o RadioButton correspondente. Ou seja, `mnemonic` e `ordinal()` desempenham papéis equivalentes (ambos representam a posição do valor dentro do enum), só que em direções opostas do fluxo de dados (da tela para o objeto, e do objeto para a tela).

> **🎯 Em resumo:**
> Você deve entender o papel de `consultarAmigo`: buscar um amigo já cadastrado a partir da seleção do ComboBox e exibir seus dados na tela, incluindo a seleção do RadioButton correto via métodos auxiliares que recebem o `ordinal()` do valor do enum.

---

O método obterAmigoInformado utiliza métodos auxiliares, para selecionar o RadioButton correspondente ao elemento do enumerado associado aos atributos sexo e estado_civil. A implementação do método auxiliar selecionarEstadoCivilRadioButton é ilustrada a seguir.

```java
private void selecionarEstadoCivilRadioButton(int índice_estado_civil) {
    switch(índice_estado_civil) {
        case 0: solteiroRadioButton.setSelected(true);
                break;
        case 1: casadoRadioButton.setSelected(true);
                break;
        case 2: divorciadoRadioButton.setSelected(true);
                break;
        case 3: viúvoRadioButton.setSelected(true);
    }
}
```

A implementação do método auxiliar selecionarSexoRadioButton é equivalente.

> **Observação do Claude — explicação linha por linha:**
>
> - `private void selecionarEstadoCivilRadioButton(int índice_estado_civil) {` — método auxiliar privado que recebe um número inteiro (o `ordinal()` visto anteriormente) e não devolve nada (`void`); sua única função é marcar visualmente o RadioButton correto.
> - `switch(índice_estado_civil) { ... }` — estrutura de seleção múltipla que testa o valor do índice recebido e executa o bloco `case` correspondente.
> - `case 0: solteiroRadioButton.setSelected(true); break;` — se o índice for 0, marca (`setSelected(true)`) o RadioButton "solteiro" como selecionado. O `break` interrompe o `switch`, evitando que a execução "caia" para o próximo `case`.
> - Os casos 1, 2 e 3 seguem o mesmo padrão para "casado", "divorciado" e "viúvo", respectivamente.
> - Repare que o **último `case` (3, viúvo) não tem `break`**. Isso não causa erro porque, sendo o último caso do `switch`, não há um próximo bloco para o qual a execução possa "cair"; ainda assim, é considerada uma boa prática sempre incluir o `break` por clareza e para evitar bugs caso alguém adicione um novo `case` depois dele futuramente.

> **🔎 Esclarecimento:**
> Este método depende diretamente da ordem em que os valores foram declarados no `enum EstadoCivil` (que não é mostrado explicitamente neste trecho do tutorial, mas pode-se inferir, pela ordem dos `case`s e pelos rótulos dos RadioButtons na tela, que a declaração seria algo como `public enum EstadoCivil { solteiro, casado, divorciado, viúvo }`). Se a ordem de declaração do enum mudasse, este `switch` deixaria de corresponder corretamente aos RadioButtons, e seria necessário atualizá-lo also — essa é uma fragilidade implícita da técnica (não foi apontada explicitamente pelo professor, mas é importante ter em mente).

> **🎯 Em resumo:**
> Você deve saber implementar o "caminho inverso" da leitura de RadioButtons: dado um índice (obtido via `ordinal()`), usar um `switch` para marcar (`setSelected(true)`) o componente gráfico correspondente.

---

### 2.4 - Atualizando o método limparCampos

> O método limparCampos é atualizado para a remover as seleções dos valores dos enumerados associados aos atributos sexo e estado_civil.

```java
private void limparCampos(java.awt.event.ActionEvent evt) { 
    nomeTextField.setText("");
    apelidoTextField.setText("");
    cidadeTextField.setText("");
    emailTextField.setText("");
    sexoButtonGroup.clearSelection();
    estado_civilButtonGroup.clearSelection();
    whatsappTextField.setText("");
}
```

> **Observação do Claude — explicação linha por linha:**
>
> - As quatro primeiras linhas apagam o conteúdo de campos de texto simples, atribuindo string vazia (`""`) via `setText("")`.
> - `sexoButtonGroup.clearSelection();` e `estado_civilButtonGroup.clearSelection();` — são a novidade desta seção: o método `clearSelection()`, da classe `ButtonGroup`, desmarca **todos** os RadioButtons daquele grupo, deixando nenhum selecionado. Isso é necessário porque, diferentemente de um campo de texto (onde basta atribuir `""`), um `ButtonGroup` não tem um método "esvaziar texto" — é preciso usar seu método específico de limpeza de seleção.
> - `whatsappTextField.setText("");` — apaga o campo whatsapp, seguindo o mesmo padrão dos campos de texto.

> **🎯 Em resumo:**
> A lição desta seção é curta e direta: para limpar campos representados por `ButtonGroup`, usa-se `clearSelection()` em vez de `setText("")`.

---

## 3 — Atualizando a JanelaCadastroAtores

> A JanelaCadastroAtores será atualizada com grupo de botões de rádio para representar os atributos sexo e ganhador_oscar. A visualização da janela é mostrada a seguir.

**(Imagem no tutorial original: tela "Cadastrar Atores" mostrando os campos Nome, Ano de Nascimento, Sexo — com radio buttons "feminino"/"masculino" — e um CheckBox "Ganhador de Oscar" marcado, além dos botões Inserir, Consultar, Alterar, Remover, Limpar.)**

> A configuração do componente CheckBox é muito simples. São configuradas as seguintes propriedades específicas: (a) text: Ganhador de Oscar; e (b) horizontalTextPosition: LEFT. As alterações nos métodos também são muito simples.

> **🔎 Esclarecimento:**
> Repare que, apesar do título da seção mencionar "sexo e ganhador_oscar", o atributo **sexo** aqui usa exatamente a mesma técnica de RadioButton + ButtonGroup já explicada em detalhe na seção 2 (para a entidade Amigo) — o professor não repete essa explicação porque já foi coberta. A novidade real desta seção é o **CheckBox**, usado para o atributo booleano `ganhador_oscar`.

> **🔎 Esclarecimento:**
> A propriedade `horizontalTextPosition: LEFT` controla **onde o texto do rótulo aparece em relação à caixinha de marcação** do CheckBox. Com o valor `LEFT`, o texto "Ganhador de Oscar" é exibido à esquerda da caixinha (e a caixinha de marcação fica à direita do texto) — isso é visível na captura de tela do tutorial, onde o texto vem antes do quadradinho marcável. O padrão do componente, sem essa configuração, normalmente colocaria o texto à direita da caixinha.

No método obterAtorInformado as alterações são as seguintes:

```java
boolean ganhador_oscar = ganhador_oscarCheckBox.isSelected();
return new Ator (nome, ano_nascimento, sexo, ganhador_oscar);
```

No método consultarAtor utilizamos o comando:

```java
ganhador_oscarCheckBox.setSelected(ator.isGanhadorOscar());
```

No método limparCampos utilizamos o comando:

```java
ganhador_oscarCheckBox.setSelected(false);
```

> **Observação do Claude — explicação linha por linha:**
>
> - `boolean ganhador_oscar = ganhador_oscarCheckBox.isSelected();` — o método `isSelected()` de um CheckBox devolve diretamente um valor `boolean` (`true` se marcado, `false` se desmarcado). Note como isso é muito mais direto que a técnica usada para RadioButtons: não é preciso `ButtonGroup`, não é preciso `mnemonic`/`ordinal()`/`switch` — o CheckBox já representa nativamente um valor booleano, então basta ler seu estado de marcação.
> - `return new Ator (nome, ano_nascimento, sexo, ganhador_oscar);` — cria o objeto `Ator` com todos os atributos coletados, incluindo o booleano lido do CheckBox.
> - `ganhador_oscarCheckBox.setSelected(ator.isGanhadorOscar());` — no sentido inverso (ao consultar um ator já cadastrado), o método `setSelected(boolean)` marca ou desmarca o CheckBox de acordo com o valor booleano vindo do objeto `Ator` (presumindo, por inferência, que a classe `Ator` tem um método `isGanhadorOscar()` que devolve esse atributo).
> - `ganhador_oscarCheckBox.setSelected(false);` — para limpar o campo, basta desmarcar o CheckBox, atribuindo `false` diretamente — muito mais simples que o `clearSelection()` necessário para grupos de RadioButton.

> **💡 O que o professor está tentando ensinar:**
> Esta seção contrasta, por comparação implícita com a seção 2, a simplicidade do CheckBox em relação ao RadioButton: como o atributo booleano só tem dois estados (verdadeiro/falso), não é necessário nenhum mapeamento com índices de enum — o próprio componente já "fala a língua" do tipo `boolean` do Java, através dos métodos `isSelected()`/`setSelected(boolean)`.

> **🎯 Em resumo:**
> Você deve saber que, para atributos booleanos, o CheckBox dispensa toda a complexidade do `ButtonGroup`/`mnemonic`/`ordinal()`: basta usar `isSelected()` para ler o valor e `setSelected(boolean)` para escrever (seja para exibir um valor consultado, seja para limpar o campo com `false`).

---

## 4 — Relacionamento de Muitos para Muitos (n:n) entre Filme e Ator

> O relacionamento entre as entidades Filme e Ator é de muitos para muitos (n:n), dado que: (a) em um filme podem atuar vários atores; e (b) em um ator pode atuar em vários filmes. Em um banco de dados relacional, para representar um relacionamento n:n é necessário criar uma entidade adicional, para interligar Filme e Ator, que vamos denominar como Atuação. Observe como fica o script sql para criar um banco de dados no Tutorial 2, omitindo criação das tabelas Amigos e Atores, cujo script é o mesmo do Tutorial 1. Na tabela Atuações, cada atuação tem uma coluna associada a uma chave estrangeira (FOREIGN KEY) referenciando (REFERENCES) a chave de um filme e de um ator.

```sql
DROP TABLE IF EXISTS Atuações;
DROP TABLE IF EXISTS Amigos;
DROP TABLE IF EXISTS Atores;
DROP TABLE IF EXISTS Filmes;
 
CREATE TABLE Filmes (
    Sequencial INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Título VARCHAR(60) NOT NULL,
    Gênero INT NOT NULL,
    Ano INT NOT NULL);
 
CREATE TABLE Atuações (
    Sequencial INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    FilmeId INT NOT NULL,
    AtorId VARCHAR(50) NOT NULL,
    FOREIGN KEY (FilmeId) REFERENCES Filmes(Sequencial),
    FOREIGN KEY (AtorId) REFERENCES Atores(Nome));
```

> Neste caso, é necessário implementar o trio janela, controlador e entidade tanto para Filme, quanto para Atuação. Nas próximas subseções, são ilustradas a JanelaCadastroFilmes e a JanelaCadastroAtuações, com suas repectivas classes vinculadas.

> **Observação do Claude — explicação do script SQL:**
>
> - `DROP TABLE IF EXISTS ...` (as quatro linhas) — apagam as tabelas antigas, se existirem, antes de recriá-las do zero. A **ordem** dessas instruções é importante: `Atuações` é removida **antes** de `Filmes` e `Atores`, porque `Atuações` tem chaves estrangeiras que dependem dessas duas tabelas — não é possível remover uma tabela referenciada por outra (via FOREIGN KEY) enquanto a tabela que a referencia ainda existir. Remover primeiro quem depende, depois quem é dependido.
> - `CREATE TABLE Filmes (...)` — cria a tabela de filmes com quatro colunas: `Sequencial` (chave primária, gerada automaticamente por `AUTO_INCREMENT`), `Título` (texto obrigatório, até 60 caracteres), `Gênero` (um número inteiro, obrigatório — que na prática vai armazenar o índice/`ordinal()` do enum `Gênero`, análogo ao que já vimos para RadioButtons) e `Ano` (inteiro, obrigatório).
> - `CREATE TABLE Atuações (...)` — cria a tabela que representa a **entidade associativa** do relacionamento n:n. Ela tem: `Sequencial` (chave primária própria, também autoincrementada), `FilmeId` (chave estrangeira que referencia `Filmes.Sequencial`) e `AtorId` (chave estrangeira que referencia `Atores.Nome` — usando o nome como chave, e não um sequencial, o que é consistente com o fato de a entidade `Ator` provavelmente já usar o nome como chave primária desde o Tutorial 1).
> - `FOREIGN KEY (FilmeId) REFERENCES Filmes(Sequencial)` e `FOREIGN KEY (AtorId) REFERENCES Atores(Nome)` — são as restrições que garantem a integridade referencial: todo valor de `FilmeId` em `Atuações` precisa corresponder a um `Sequencial` existente em `Filmes`, e todo `AtorId` precisa corresponder a um `Nome` existente em `Atores`.

> **💡 O que o professor está tentando ensinar:**
> A ideia central desta seção é o motivo **estrutural** pelo qual um relacionamento n:n exige uma **tabela/entidade adicional** (aqui chamada `Atuação`): como um filme pode ter vários atores e um ator pode estar em vários filmes, seria impossível representar isso apenas com uma coluna de chave estrangeira dentro de `Filmes` ou dentro de `Atores` (uma única coluna só pode guardar uma referência por linha). A solução clássica de modelagem relacional é criar uma terceira tabela, com uma linha para cada **combinação** filme-ator, cada linha guardando duas chaves estrangeiras (uma para cada lado do relacionamento).

> **🔎 Esclarecimento:**
> O professor também antecipa que, na arquitetura do sistema (que provavelmente segue o padrão MVC ou similar, com "janela", "controlador" e "entidade" para cada conceito do domínio, como já estabelecido no Tutorial 1), será necessário criar esse **trio completo de classes também para a entidade Atuação** — ou seja, `Atuação` não é apenas uma tabela no banco, mas uma entidade de primeira classe no sistema, com sua própria janela de cadastro (`JanelaCadastroAtuações`) e seu próprio controlador.

> **🎯 Em resumo:**
> Você deve entender que relacionamentos n:n exigem, no modelo relacional, uma tabela associativa com duas chaves estrangeiras, e que essa tabela associativa deve ser tratada, na arquitetura do sistema, como uma entidade completa (com janela, controlador e classe de entidade próprios) — não apenas como um detalhe interno do banco de dados.

---

### 4.1 - Construindo a JanelaCadastroFilmes e suas classes vinculadas

> Os enumerados e atributos da entidade Filme são os seguintes:

```java
public enum Gênero { ação, aventura, comédia, drama, faroeste, ficção, guerra, infantil,
    musical, romance, suspense, terror }

private int sequencial;
private String título;
private Gênero gênero;
private int ano;
```

> Observe que não é definido nenhum dado como referência, dado que isto vai ocorrer somente na classe Atuação. Além do construtor com todos os atributos, é definido também um construtor para criar uma visão da classe Filme que será utilizado para povoar o ComboBox com os filmes cadastrados, que recebe como parâmetros a chave sequencial e o atributo título.
>
> A ilustração da JanelaCadastroFilmes é mostrada a seguir. O Button com o título Atuações, é utilizado para visualizar a JanelaCadastroAtuações e selecionar os atores que atuam em um dado filme. Para a seleção do gênero do filme, é utilizado um ComboBox, dado que a quantidade de elementos do enumerado Gênero, da classe Filme, não pode ser adequadamente representada por botões de radio.

**(Imagem no tutorial original: tela "Cadastrar Filmes" com campos Filmes Cadastrados (ComboBox), Identificador Sequencial (desabilitado), Título, Gênero (ComboBox), Ano, e uma lista "Atuações no Filme" mostrando os atores do filme, além dos botões Inserir, Consultar, Alterar, Remover, Limpar, Atuações.)**

> Nas próximas subseções serão comentados os tratadores de eventos cujos códigos requerem detalhes específicos de implementação. Os demais tratadores, alterarFilme e removerFilme, seguem o mesmo padrão das janelas comentadas anteriormente:
>
> Na configuração do ComboBox utilizado para selecionar os valores fixos do enumerado Gênero da classe Filme, na propriedade model é informado, o seguinte código:

```java
new DefaultComboBoxModel (Gênero.values())
```

> A função estática values retorna os valores do enumerado que são associados ao ComboBox.

> **Observação do Claude — explicação do enum e dos atributos:**
>
> - `public enum Gênero { ação, aventura, ... , terror }` — declara o tipo enumerado `Gênero` com doze valores possíveis, na ordem exata em que aparecem (isso importa, porque, como já vimos, a posição/`ordinal()` de cada valor é usada como índice em várias operações). Note que aqui o enum tem **12 elementos** — bem mais que o limite de "5" mencionado na Introdução como critério para usar RadioButton — por isso o tutorial reforça que o `Gênero` deve usar **ComboBox**, e não RadioButtons.
> - `private int sequencial;` — chave primária do filme (numérica, análoga ao `Sequencial` da tabela SQL).
> - `private String título;` — o título do filme.
> - `private Gênero gênero;` — o gênero do filme, do tipo enum recém-declarado.
> - `private int ano;` — o ano do filme.

> **🔎 Esclarecimento:**
> A frase "não é definido nenhum dado como referência" quer dizer que a classe `Filme` **não tem nenhum atributo do tipo `Ator` ou `Ator[]`** (não guarda diretamente uma lista de atores). Isso é proposital: como explicado na seção 4, num relacionamento n:n a referência entre as duas entidades fica isolada na entidade associativa (`Atuação`), e não em `Filme` nem em `Ator` diretamente. Essa é uma decisão de design consistente com o modelo relacional visto no script SQL.

> **🔎 Esclarecimento:**
> O "construtor para criar uma visão" é um segundo construtor da classe `Filme`, com menos parâmetros (apenas `sequencial` e `título`), usado quando não é necessário (ou não se tem ainda) o objeto `Filme` completo — por exemplo, para preencher rapidamente um ComboBox só com identificação e nome, sem carregar todos os outros atributos. Esse padrão de "visão" (uma versão resumida do objeto) já havia sido mencionado brevemente no código de `consultarAmigo` (seção 2.3), onde a variável se chamava `visão`.

> **Observação do Claude — explicação da linha de código do ComboBox:**
>
> `new DefaultComboBoxModel (Gênero.values())` — cria um novo "modelo de dados" para o ComboBox do gênero. `DefaultComboBoxModel` é uma classe do Swing que administra a lista de itens exibidos por um ComboBox. Ao passar `Gênero.values()` (o array com todos os valores do enum) como argumento do construtor, o ComboBox passa automaticamente a exibir, como opções, todos os doze gêneros declarados no enum — sem que seja necessário adicionar cada item manualmente um por um.

> **🎯 Em resumo:**
> Você deve entender que a entidade `Filme` não referencia diretamente `Ator` (a referência fica isolada na entidade `Atuação`), que existe um construtor "de visão" simplificado para popular ComboBoxes, e que o padrão para configurar um ComboBox com valores fixos de um enum é usar `new DefaultComboBoxModel(EnumX.values())` na propriedade `model` do componente.

---

> O sequencial, utilizado com chave de cada filme, é criado automaticamente pelo banco de dados. Desta forma, o TextField utilizado para mostrar o sequencial é desabilitado para preenchimento pelo usuário; e seu valor só será atualizado por programa, após a criação do filme no banco de dados. Para desabilitar o TextField para edição é necessário desabilitar a sua propridade: editable.
>
> Para informar os nomes dos principais atores que atuaram no filme é utilizado um componente List, que diferentemente do ComboBox mostra mais de um elemento ao mesmo tempo. Esse componente sempre é inserido em um componente container ScrollPane, para que caso o número de linhas ou colunas mostrado no List exceda o tamanho configurado, apareça respectivamente um scrollbar horizontal ou vertical, dependendo de qual dimensão foi excedida.
>
> A configuração do List é diferente da configuração do ComboBox. A sua propriedade visibleRowCount foi configurada com 4, indicando que o List mostra até 4 linhas. Caso ultrapasse, aparecerá o scroolbar vertical. A sua propridade selectionMode é configurada com SINGLE, indicando que somente uma linha do List será selecionada por vez. A sua propriedade model é configurada com o seguinte código:

```java
new DefaultListModel()
```

> Para o List, as visões são associadas diretamente no seu modelo e não no componente gráfico. Por esse motivo o construtor da JanelaCadastroFilmes armazena o modelo que será utilizado posteriormente.

```java
ControladorCadastroFilmes controlador;
Filme[] filmes_cadastrados;
DefaultListModel modelo_atuações_filme;

public JanelaCadastroFilmes(ControladorCadastroFilmes controlador) {
    this.controlador = controlador;
    filmes_cadastrados = Filme.getVisões();
    initComponents();
    modelo_atuações_filme = (DefaultListModel)atuações_filmeList.getModel();
    limparCampos(null);
}
```

> O método getVisões, da classe Filme, é semelhante aos das classes Amigo e Ator definidos anteriormente. Ao Button com texto Atuações, é vinculado o tratador de eventos cadastrarAtuações.

> **🔎 Esclarecimento:**
> O `sequencialTextField` fica **desabilitado para edição** (propriedade `editable = false`) porque seu valor não é escolhido pelo usuário — é o próprio banco de dados quem gera automaticamente o próximo número disponível, graças à cláusula `AUTO_INCREMENT` vista no `CREATE TABLE Filmes`. O campo continua sendo exibido (não é "invisível"), apenas não pode ser digitado — ele só é atualizado programaticamente, depois que o filme é efetivamente inserido no banco (isso será visto em detalhe na seção 4.1.1, no tratador `inserirFilme`).

> **🔎 Esclarecimento:**
> É importante diferenciar as propriedades do componente **List**:
> - `visibleRowCount = 4` — controla quantas linhas ficam visíveis **sem** precisar rolar a lista; se houver mais itens que isso, o `ScrollPane` que envolve o List mostra uma barra de rolagem vertical.
> - `selectionMode = SINGLE` — restringe a seleção a **apenas um item por vez** dentro da lista (existe também, no Swing, a possibilidade de seleção múltipla, que não é usada aqui).
> - `model = new DefaultListModel()` — assim como o ComboBox, o List também precisa de um "modelo de dados" que administra a coleção de itens exibidos. A diferença fundamental (explicada no próprio parágrafo do tutorial) é que, no List, esse modelo é criado **vazio** inicialmente — os itens são adicionados/removidos dinamicamente pelo próprio código (ex.: `modelo_atuações_filme.addElement(...)`), diferentemente do ComboBox de Gênero, cujo modelo já nasce preenchido com `Gênero.values()`.

> **Observação do Claude — explicação linha por linha do construtor:**
>
> - `ControladorCadastroFilmes controlador;` — atributo que guarda a referência ao controlador desta janela (para poder chamar métodos do controlador, como inserção/consulta no banco, a partir dos tratadores de eventos).
> - `Filme[] filmes_cadastrados;` — array que guarda as "visões" de todos os filmes já cadastrados (usado provavelmente para popular o ComboBox "Filmes Cadastrados").
> - `DefaultListModel modelo_atuações_filme;` — atributo que guardará uma referência direta ao modelo de dados do List de atuações, permitindo manipulá-lo (adicionar/remover elementos) a partir de qualquer método da classe, sem precisar buscar o modelo do componente gráfico repetidamente.
> - `public JanelaCadastroFilmes(ControladorCadastroFilmes controlador) {` — construtor da janela, que recebe o controlador que a está criando/chamando.
> - `this.controlador = controlador;` — guarda a referência recebida no atributo da classe.
> - `filmes_cadastrados = Filme.getVisões();` — busca (presumivelmente no banco de dados) todas as "visões" resumidas dos filmes já cadastrados, para popular o ComboBox de filmes cadastrados.
> - `initComponents();` — método gerado automaticamente pelo NetBeans que cria e configura todos os componentes visuais da janela (é chamado sempre em construtores de janelas Swing geradas pelo NetBeans Form Editor).
> - `modelo_atuações_filme = (DefaultListModel)atuações_filmeList.getModel();` — **este é o ponto central desta explicação**: depois que `initComponents()` já criou o componente `atuações_filmeList` (com seu modelo configurado como `new DefaultListModel()`, conforme visto acima), este código recupera esse modelo (usando `getModel()`, que devolve um tipo genérico `ListModel`, daí o `cast` para `DefaultListModel`) e o guarda no atributo `modelo_atuações_filme` da classe, para uso posterior em outros métodos (como `atualizarListaAtuaçõesFilme`, visto mais adiante).
> - `limparCampos(null);` — chama o método de limpar campos logo na criação da janela, deixando-a com os campos vazios/zerados por padrão (o `null` é passado como argumento porque o método espera um `ActionEvent`, mas, quando chamado diretamente pelo construtor — e não por um clique real de botão —, não existe um evento de verdade para passar).

> **💡 O que o professor está tentando ensinar:**
> A grande diferença conceitual explicada aqui é: enquanto o **ComboBox** de Gênero tem seu modelo criado **já preenchido** (com todos os valores fixos do enum, de uma vez), o **List** de Atuações tem seu modelo criado **vazio**, e cabe ao programador **preencher e esvaziar esse modelo manualmente**, ao longo da vida da janela, conforme os dados mudam (por exemplo, ao consultar um filme diferente). Por isso é necessário guardar uma referência a esse modelo (`modelo_atuações_filme`) como atributo da classe — ele será reutilizado repetidamente por vários métodos diferentes.

> **🎯 Em resumo:**
> Você deve entender a diferença entre um `ComboBox` com valores fixos (modelo preenchido de uma vez, na configuração) e um `List` (modelo vazio, alimentado dinamicamente pelo código, e cuja referência precisa ser guardada como atributo da classe para reuso).

---

#### 4.1.1 - O Tratamento de Eventos para Inserir um Filme

> A seguir, é ilustrada a implementação do tratador de eventos inserirFilme.

```java
private void inserirFilme(java.awt.event.ActionEvent evt) { 
    Filme filme = obtémFilmeInformado();
    String mensagem_erro = null;
    if (filme != null) mensagem_erro = controlador.inserirFilme(filme);
    else mensagem_erro = "Algum atributo do filme não foi informado";
    if (mensagem_erro == null) {
        int sequencial = Filme.últimoSequencial();
        filme.setSequencial(sequencial);
        Filme visão = filme.getVisão();
        filmes_cadastradosComboBox.addItem(visão);
        filmes_cadastradosComboBox.setSelectedItem(visão); 
        sequencialTextField.setText ("" + sequencial);
    } else informarErro (mensagem_erro);
} 
```

> Após a criação na base de dados, deve ser lido o último sequencial criado, para atualizar o atributo sequencial do objeto filme e mostrá-lo no sequencialTextField.

> **Observação do Claude — explicação linha por linha:**
>
> - `Filme filme = obtémFilmeInformado();` — chama o método (detalhado logo a seguir) que lê os dados digitados na tela e devolve um objeto `Filme`, ou `null` se algum dado obrigatório estiver faltando.
> - `if (filme != null) mensagem_erro = controlador.inserirFilme(filme); else mensagem_erro = "Algum atributo do filme não foi informado";` — se os dados foram lidos com sucesso, delega ao controlador a tarefa de efetivamente inserir o filme no banco de dados (o controlador devolve uma mensagem de erro, ou `null` se tudo correu bem); caso contrário, define diretamente a mensagem de erro apropriada.
> - `if (mensagem_erro == null) { ... } else informarErro (mensagem_erro);` — se não houve erro, executa o bloco de sucesso; caso contrário, exibe o erro ao usuário.
> - `int sequencial = Filme.últimoSequencial();` — **este é o ponto mais importante desta seção**: como o `Sequencial` do filme é gerado automaticamente pelo banco de dados (via `AUTO_INCREMENT`), o objeto `filme` criado em Java, antes da inserção, não sabe ainda qual será seu identificador definitivo. Por isso, logo após a inserção bem-sucedida, o código chama `Filme.últimoSequencial()` (detalhado a seguir) para descobrir qual foi o número gerado.
> - `filme.setSequencial(sequencial);` — atualiza o objeto `filme` (que estava em memória, com sequencial 0) com o valor real recém-descoberto.
> - `Filme visão = filme.getVisão();` — cria uma "visão" resumida (sequencial + título) do filme recém-cadastrado, para exibição no ComboBox.
> - `filmes_cadastradosComboBox.addItem(visão);` — adiciona essa nova visão como um item do ComboBox de filmes cadastrados (sem precisar recarregar a lista inteira do banco).
> - `filmes_cadastradosComboBox.setSelectedItem(visão);` — seleciona automaticamente, no ComboBox, o filme que acabou de ser inserido — uma conveniência de usabilidade, deixando a tela já "apontando" para o registro recém-criado.
> - `sequencialTextField.setText ("" + sequencial);` — atualiza visualmente o campo de sequencial (que, lembrando, está desabilitado para edição, mas pode ser atualizado por código) com o valor real gerado pelo banco. O truque `"" + sequencial` é uma forma comum em Java de **converter um número em texto** por concatenação implícita de String (equivalente a `String.valueOf(sequencial)` ou `Integer.toString(sequencial)`).

> **🎯 Em resumo:**
> Você deve entender o fluxo completo de inserção de um filme: ler os dados da tela → delegar a inserção ao controlador → se bem-sucedida, descobrir o sequencial gerado pelo banco → atualizar tanto o objeto em memória quanto os componentes visuais (ComboBox e campo de sequencial) para refletir esse novo dado.

---

> O método obterFilmeInformado, é implementado da seguinte forma:

```java
private Filme obtémFilmeInformado() {
    String sequencial_str = sequencialTextField.getText();
    int sequencial = 0;
    if (!sequencial_str.isEmpty()) sequencial = Integer.parseInt(sequencial_str);
    String título = títuloTextField.getText();
    if (título.isEmpty()) return null;
    Gênero gênero = null;
    if (gêneroComboBox.getSelectedItem() != null) 
        gênero = (Gênero)gêneroComboBox.getSelectedItem();
    else return null;
    String ano_str = anoFormattedTextField.getText();
    int ano = -1;
    if (!ano_str.isEmpty()) ano = Integer.parseInt(ano_str);
    else return null;
    return new Filme(sequencial, título, gênero, ano);
}
```

> O método obterFilmeInformado é utilizado pelos tratadores de evento: inserirFilme e alterarFilme. Antes de inserir um filme, o sequencial ainda não foi criado pelo banco. Neste caso, o filme é criado com o sequencial com valor 0, que na realidade não existe, porque os sequenciais são criados pelo banco de dados a partir do valor 1.
>
> Dado que o sequencialComboBox está desabilitado para preenchimento, para alterar um filme é necessário consultá-lo, e converter o string lido em um número inteiro, para criar o objeto filme utilizando com o sequencial obtido.
>
> Para ler o valor do gênero do filme, informado no gêneroComboBox, é necessário primeiro testar se algum valor foi selecionado no ComboBox.

> **Observação do Claude — explicação linha por linha:**
>
> - `String sequencial_str = sequencialTextField.getText(); int sequencial = 0; if (!sequencial_str.isEmpty()) sequencial = Integer.parseInt(sequencial_str);` — lê o texto do campo de sequencial; se estiver vazio (caso de um filme ainda não inserido), o sequencial permanece 0; se já houver um valor (caso de alteração de um filme já consultado), converte o texto para inteiro usando `Integer.parseInt`.
> - `String título = títuloTextField.getText(); if (título.isEmpty()) return null;` — título é obrigatório, segue o padrão já visto (retorna `null` se vazio).
> - `Gênero gênero = null; if (gêneroComboBox.getSelectedItem() != null) gênero = (Gênero)gêneroComboBox.getSelectedItem(); else return null;` — lê o item selecionado no ComboBox de gênero; como `getSelectedItem()` devolve um `Object` genérico, é feito um `cast` para `Gênero`. Se nada estiver selecionado (`null`), o método retorna `null` (gênero é obrigatório).
> - `String ano_str = anoFormattedTextField.getText(); int ano = -1; if (!ano_str.isEmpty()) ano = Integer.parseInt(ano_str); else return null;` — lê e converte o ano, que também é obrigatório (retorna `null` se vazio).
> - `return new Filme(sequencial, título, gênero, ano);` — cria e devolve o objeto `Filme` com todos os dados coletados.

> **🔎 Esclarecimento:**
> A frase do tutorial "o filme é criado com o sequencial com valor 0, que na realidade não existe" é importante: o valor `0` funciona como um **valor sentinela** (um valor especial que sinaliza "ainda não definido/ainda não existe no banco"), já que o `AUTO_INCREMENT` do MySQL gera sequenciais a partir de 1. Assim, o próprio valor do sequencial é usado, mais adiante, para diferenciar se um filme já foi persistido no banco (sequencial > 0) ou ainda não (sequencial = 0) — essa técnica é usada explicitamente no tratador `cadastrarAtuações`, visto na seção 4.1.4.

> **🔎 Esclarecimento:**
> O uso de `Integer.parseInt(sequencial_str)` sem tratamento de exceção (`try/catch`) é um ponto que pode gerar erro em tempo de execução (uma `NumberFormatException`) se o texto no campo não for um número válido. O tutorial não trata esse caso aqui, mas, como o campo de sequencial é desabilitado para edição manual (`editable = false`, visto na seção anterior), o texto ali sempre foi colocado programaticamente (sempre um número válido ou vazio), o que reduz bastante o risco prático desse tipo de erro neste caso específico.

> **🎯 Em resumo:**
> Você deve entender como ler, com tratamento de campo obrigatório, um valor selecionado de um `ComboBox` (usando `getSelectedItem()` com `cast`), e entender a convenção de usar sequencial = 0 como "ainda não inserido no banco".

---

> O método estático últimoSequencial, da classe Filme, é implementado levando em conta o fato que o maior sequencial utilizado na tabela Filmes é o último sequencial criado.

```java
public static int últimoSequencial () {
    String sql = "SELECT MAX(Sequencial) FROM Filmes";
    ResultSet lista_resultados = null;
    int sequencial = 0;
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        lista_resultados = comando.executeQuery(); 
        while (lista_resultados.next()) {
            sequencial = lista_resultados.getInt (1);
        }
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    return sequencial;
}
```

> **Observação do Claude — explicação linha por linha:**
>
> - `public static int últimoSequencial () {` — método **estático** (não precisa de um objeto `Filme` já existente para ser chamado; pode ser chamado como `Filme.últimoSequencial()`, como visto no tratador `inserirFilme`), que devolve um número inteiro.
> - `String sql = "SELECT MAX(Sequencial) FROM Filmes";` — monta a consulta SQL que busca o **maior** valor da coluna `Sequencial` em toda a tabela `Filmes`. Como o `AUTO_INCREMENT` sempre gera valores crescentes, o maior valor existente corresponde ao último gerado (ou seja, ao filme mais recentemente inserido).
> - `ResultSet lista_resultados = null; int sequencial = 0;` — declara variáveis auxiliares para armazenar o resultado da consulta e o valor final a ser devolvido (inicializado com 0 como valor padrão de segurança).
> - `try { ... } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}` — bloco de tratamento de exceções: operações de banco de dados podem lançar `SQLException` (por exemplo, se a conexão cair); caso isso aconteça, o erro é apenas impresso no console (`printStackTrace()`), sem interromper a aplicação de forma mais elaborada.
> - `PreparedStatement comando = BD.conexão.prepareStatement(sql);` — prepara o comando SQL usando a conexão global do banco (`BD.conexão`, presumivelmente um atributo estático de uma classe utilitária `BD`, responsável por gerenciar a conexão JDBC).
> - `lista_resultados = comando.executeQuery();` — executa a consulta e armazena o resultado (um `ResultSet`, que é como um "cursor" percorrendo as linhas retornadas).
> - `while (lista_resultados.next()) { sequencial = lista_resultados.getInt (1); }` — percorre as linhas do resultado (neste caso, a consulta `MAX()` sempre devolve exatamente uma linha) e lê o valor da primeira coluna (índice `1`, pois em JDBC a contagem de colunas começa em 1, não em 0) como um inteiro.
> - `lista_resultados.close(); comando.close();` — fecham os recursos abertos (`ResultSet` e `PreparedStatement`), uma boa prática para liberar memória/conexões do banco.
> - `return sequencial;` — devolve o valor encontrado (ou 0, se por algum motivo a consulta não retornou nenhuma linha ou ocorreu uma exceção).

> **🔎 Esclarecimento:**
> Vale observar (por inferência, já que o tutorial não comenta isso explicitamente) que essa abordagem de "buscar o `MAX(Sequencial)`" tem uma **limitação de concorrência**: se dois usuários inserissem filmes simultaneamente, ambos poderiam, teoricamente, ler o mesmo "último sequencial" antes que o outro terminasse sua operação, causando inconsistência. Em um sistema real com múltiplos usuários simultâneos, normalmente se usaria uma forma mais segura de recuperar o ID recém-gerado (como `getGeneratedKeys()` do JDBC, executado imediatamente após o `INSERT`). O tutorial não menciona essa questão, então isso é apenas uma observação de contexto, não uma correção do conteúdo original.

> **🎯 Em resumo:**
> Você deve entender como implementar uma consulta JDBC clássica (preparar comando → executar → percorrer resultado → fechar recursos) e a lógica de descobrir o "último sequencial gerado" através de `SELECT MAX(Sequencial)`.

---

> A implementação do método inserirFilme, da classe ControladorCadastroFilmes, é a seguinte:

```java
public String inserirFilme (Filme filme) {
    if (!Filme.existeFilmeMesmosAtributos (filme)) return Filme.inserirFilme (filme);
    else return "Já existe um filme com os mesmos atributos";
}
```

> Dado que o sequencial é criado automáticamente, se o usuário informasse os mesmos dados de um filme já criado, o filme seria criado novamente no banco com um outro sequencial. Para evitar que isso aconteça, o controlador verifica se já existe um filme com os mesmos atributos, obviamente desconsiderando o sequencial, que neste momento está assinalado provisoriamente como 0 no objeto filme recebido. A implementação do método estático existeFilmeMesmosAtributos, da classe Filme, é baseada na contagem do quantos sequenciais distintos foram criados para um filme com os mesmos atributos. Se for 0, é porque nenhum filme ainda foi criado com os mesmos atributos. Se for 1, é porque já existe um filme. Observe que nunca será superior a 1, porque o controlador não permite a criação de mais de um filme com os mesmos atributos.

```java
public static boolean existeFilmeMesmosAtributos (Filme filme) {
    String sql = "SELECT COUNT(Sequencial) FROM Filmes"
        + " WHERE Título = ? AND Gênero = ? AND Ano = ?";
    ResultSet lista_resultados = null;
    int n_filmes_mesmos_atributos = 0;
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setString(1, filme.getTítulo());
        comando.setInt(2, filme.getGênero().ordinal());
        comando.setInt(3, filme.getAno());
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) {
            n_filmes_mesmos_atributos = lista_resultados.getInt(1);
        }
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    if (n_filmes_mesmos_atributos > 0) return true;
    else return false;
}
```

> A implementação dos métodos estáticos buscarFilme e inserirFilme, da classe Filme, são equivalentes aos métodos definidos anteriormente.

> **Observação do Claude — explicação linha por linha:**
>
> - `public String inserirFilme (Filme filme) {` — método do **controlador** (nível intermediário entre a janela e a entidade); devolve uma `String` com a mensagem de erro, ou `null` se a inserção foi bem-sucedida (padrão já visto nos métodos das janelas).
> - `if (!Filme.existeFilmeMesmosAtributos (filme)) return Filme.inserirFilme (filme); else return "Já existe um filme com os mesmos atributos";` — antes de inserir, verifica se já existe um filme com os mesmos atributos (título, gênero e ano); se **não** existir, delega a inserção de fato à entidade `Filme` (retornando o que quer que esse método devolva — presumivelmente `null` em caso de sucesso); se **já existir**, devolve diretamente a mensagem de erro, sem tentar inserir.
> - `String sql = "SELECT COUNT(Sequencial) FROM Filmes" + " WHERE Título = ? AND Gênero = ? AND Ano = ?";` — monta uma consulta SQL que **conta** quantos registros existem em `Filmes` com o mesmo título, gênero e ano do filme recebido. Os `?` são **placeholders** (marcadores de posição) de um `PreparedStatement`, que serão preenchidos com valores concretos logo abaixo, evitando problemas de formatação/injeção de SQL.
> - `comando.setString(1, filme.getTítulo());` — associa o primeiro `?` ao título do filme (índice 1, começando em 1 em JDBC).
> - `comando.setInt(2, filme.getGênero().ordinal());` — associa o segundo `?` ao gênero, **convertido para seu índice ordinal** (mesma técnica já vista: o banco armazena o gênero como um número inteiro, correspondente à posição do valor no enum `Gênero`).
> - `comando.setInt(3, filme.getAno());` — associa o terceiro `?` ao ano do filme.
> - `lista_resultados = comando.executeQuery(); while (lista_resultados.next()) { n_filmes_mesmos_atributos = lista_resultados.getInt(1); }` — executa a consulta e lê o resultado da contagem (a consulta `COUNT()` sempre devolve exatamente uma linha, com um único número).
> - `if (n_filmes_mesmos_atributos > 0) return true; else return false;` — se a contagem for maior que zero, existe pelo menos um filme igual; caso contrário, não existe.

> **💡 O que o professor está tentando ensinar:**
> Esta seção ensina uma **regra de negócio de duplicidade**: como o sequencial é gerado automaticamente pelo banco (e portanto sempre único, mesmo para filmes "idênticos" em título/gênero/ano), é preciso uma verificação **explícita e adicional**, feita pelo controlador antes da inserção, para impedir que o mesmo filme seja cadastrado duas vezes por engano. Essa verificação é feita comparando os atributos "de negócio" (título, gênero, ano) — e não o sequencial, que nesse momento ainda não existe (está com valor 0 provisório, conforme visto na seção anterior).

> **🔎 Esclarecimento:**
> O comentário do professor "nunca será superior a 1" descreve uma **invariante** do sistema: como o próprio controlador impede a criação de filmes duplicados, é logicamente impossível, seguindo sempre esse fluxo de código, que existam dois ou mais filmes com os mesmos atributos ao mesmo tempo no banco — por isso a contagem, na prática, só pode valer 0 ou 1 (nunca mais que isso), *desde que* todas as inserções sempre passem por este controlador (se alguém inserisse diretamente no banco, sem passar pelo sistema, essa garantia deixaria de valer).

> **🎯 Em resumo:**
> Você deve entender o papel do controlador como camada que aplica regras de negócio (como evitar duplicidade) antes de delegar operações à entidade, e a técnica de comparar atributos de um enum usando seu `.ordinal()` dentro de consultas SQL parametrizadas.

---

#### 4.1.2 - O Tratamento de Eventos para Consultar um Filme

> A seguir, é ilustrada a implementação do tratador de eventos consultarFilme.

```java
private void consultarFilme(java.awt.event.ActionEvent evt) { 
    Filme visão = (Filme) filmes_cadastradosComboBox.getSelectedItem();
    Filme filme = null;
    String mensagem_erro = null;
    int sequencial = -1;
    if (visão != null) {
        sequencial = visão.getSequencial();
        filme = Filme.buscarFilme(sequencial);
        if (filme == null) mensagem_erro = "Filme não cadastrado";
    } else mensagem_erro = "Nenhum filme selecionado";
    if (mensagem_erro == null) {
        sequencialTextField.setText(sequencial + "");
        títuloTextField.setText (filme.getTítulo());
        gêneroComboBox.setSelectedItem(filme.getGênero());
        anoFormattedTextField.setText (filme.getAno() + "");
        atualizarListaAtuaçõesFilme(sequencial);
    } else informarErro (mensagem_erro);
}
```

> **Observação do Claude — explicação linha por linha:**
>
> - `Filme visão = (Filme) filmes_cadastradosComboBox.getSelectedItem();` — lê o filme selecionado no ComboBox (uma "visão" resumida).
> - `int sequencial = -1;` — inicializa o sequencial com `-1`, um valor que **não pode ocorrer naturalmente** (já que o banco gera sequenciais a partir de 1, e o valor "provisório" antes de inserir é 0) — funciona como outro valor sentinela, indicando "ainda não obtido".
> - `if (visão != null) { sequencial = visão.getSequencial(); filme = Filme.buscarFilme(sequencial); if (filme == null) mensagem_erro = "Filme não cadastrado"; } else mensagem_erro = "Nenhum filme selecionado";` — se havia seleção, obtém o sequencial da visão e busca o filme completo no banco a partir desse sequencial; se o filme não for encontrado (situação incomum, mas tratada por segurança), define erro; se não havia seleção nenhuma, define outro erro.
> - `sequencialTextField.setText(sequencial + "");` — exibe o sequencial na tela (convertendo para texto pela concatenação com string vazia, como já visto).
> - `títuloTextField.setText (filme.getTítulo());` — exibe o título.
> - `gêneroComboBox.setSelectedItem(filme.getGênero());` — **este é o ponto novo em relação ao RadioButton**: para selecionar o item correto em um ComboBox, basta chamar `setSelectedItem(...)` passando o **próprio valor do enum** (não seu índice/`ordinal()`), já que o modelo do ComboBox foi populado diretamente com os valores do enum (`Gênero.values()`) — o Swing localiza automaticamente o item correspondente na lista.
> - `anoFormattedTextField.setText (filme.getAno() + "");` — exibe o ano.
> - `atualizarListaAtuaçõesFilme(sequencial);` — chama o método (detalhado a seguir) que atualiza a lista de atores/atuações exibida no componente List, buscando no banco quais atores atuaram nesse filme específico.

> **🔎 Esclarecimento:**
> Vale notar a diferença entre como o **RadioButton** e o **ComboBox** são "reposicionados" ao consultar um valor já existente: para o RadioButton, era necessário um método auxiliar com `switch` que convertesse o `ordinal()` em uma chamada explícita a `setSelected(true)` do componente certo (seção 2.3); para o ComboBox, basta uma única chamada, `setSelectedItem(valorDoEnum)`, sem necessidade de índices nem de métodos auxiliares — o próprio ComboBox já sabe localizar o item correspondente dentro do seu modelo.

> **🎯 Em resumo:**
> Você deve saber que, para selecionar um item de ComboBox correspondente a um valor de enum já conhecido, usa-se `comboBox.setSelectedItem(valorEnum)` diretamente — mais simples que o padrão usado para RadioButtons.

---

> O método atualizarListaAtuaçõesFilme é implementado da seguinte forma:

```java
public void atualizarListaAtuaçõesFilme (int sequencial) {
    modelo_atuações_filme.clear();
    Atuação[] atuações_filme = Atuação.buscarAtuaçõesFilme(sequencial);
    for (Atuação atuação : atuações_filme) modelo_atuações_filme.addElement(atuação);
}
```

> O método buscarAtuaçõesFilme, da classe Atuação, é ilustrado a seguir:

```java
public static Atuação[] buscarAtuaçõesFilme (int sequencial_filme) {
    String sql = "SELECT Sequencial, AtorId FROM Atuações WHERE FilmeId = ?";
    ResultSet lista_resultados = null;
    ArrayList<Atuação> visões = new ArrayList();
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setInt(1, sequencial_filme);
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) {
            visões.add(new Atuação (lista_resultados.getInt("Sequencial"),
                Filme.buscarFilme(sequencial_filme).getVisão(),
                Ator.buscarAtor(lista_resultados.getString("AtorId")).getVisão()));
        }
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {exceção_sql.printStackTrace ();}
    return visões.toArray(new Atuação[visões.size()]);
}
```

> **Observação do Claude — explicação linha por linha (atualizarListaAtuaçõesFilme):**
>
> - `modelo_atuações_filme.clear();` — esvazia completamente o modelo do List antes de recarregá-lo, para não acumular itens de uma consulta anterior junto com os novos.
> - `Atuação[] atuações_filme = Atuação.buscarAtuaçõesFilme(sequencial);` — busca no banco, através da classe `Atuação`, todas as atuações associadas ao filme identificado por `sequencial`.
> - `for (Atuação atuação : atuações_filme) modelo_atuações_filme.addElement(atuação);` — percorre (usando um `for-each`, que itera sobre cada elemento do array) todas as atuações encontradas e as adiciona, uma a uma, ao modelo do List (`addElement` é o método de `DefaultListModel` usado para inserir um item).

> **Observação do Claude — explicação linha por linha (buscarAtuaçõesFilme):**
>
> - `String sql = "SELECT Sequencial, AtorId FROM Atuações WHERE FilmeId = ?";` — consulta que busca, na tabela `Atuações`, todas as linhas cujo `FilmeId` seja igual ao sequencial do filme informado, trazendo apenas as colunas `Sequencial` (da atuação) e `AtorId`.
> - `ArrayList<Atuação> visões = new ArrayList();` — cria uma lista dinâmica (que pode crescer conforme necessário, diferente de um array de tamanho fixo) para acumular os objetos `Atuação` encontrados.
> - `comando.setInt(1, sequencial_filme);` — associa o valor do parâmetro `sequencial_filme` ao primeiro `?` da consulta.
> - `while (lista_resultados.next()) { visões.add(new Atuação (...)); }` — para cada linha do resultado, cria um novo objeto `Atuação` e o adiciona à lista `visões`.
> - `new Atuação (lista_resultados.getInt("Sequencial"), Filme.buscarFilme(sequencial_filme).getVisão(), Ator.buscarAtor(lista_resultados.getString("AtorId")).getVisão())` — este construtor de `Atuação` recebe três argumentos: (1) o sequencial da própria atuação (lido da coluna `Sequencial`); (2) uma **visão** do filme (buscando o filme completo pelo sequencial recebido como parâmetro do método, e depois extraindo sua visão resumida via `.getVisão()`); (3) uma **visão** do ator (buscando o ator completo pelo `AtorId` lido da coluna, e extraindo sua visão via `.getVisão()`). Observe que, tanto para o filme quanto para o ator, primeiro se busca o objeto completo no banco, e só depois se extrai a "visão" resumida — isso é necessário porque a tabela `Atuações` só guarda as **chaves** (FilmeId, AtorId), não os dados completos de filme e ator.
> - `return visões.toArray(new Atuação[visões.size()]);` — converte a lista dinâmica `ArrayList<Atuação>` de volta para um array `Atuação[]` de tamanho fixo, que é o tipo de retorno declarado do método. `new Atuação[visões.size()]` cria um array vazio do tamanho certo para servir de "molde" ao método `toArray`.

> **🔎 Esclarecimento:**
> É importante notar que esta consulta faz, na prática, **duas buscas adicionais no banco de dados dentro do próprio laço `while`** (uma para `Filme.buscarFilme(...)` e outra para `Ator.buscarAtor(...)`, a cada iteração). Isso não é um erro, mas é uma característica de implementação relativamente ineficiente (do ponto de vista de performance, em uma tabela muito grande, geraria bastante tráfego repetido com o banco) — o tutorial não comenta essa questão, mas fica registrado como observação de contexto, já que é um padrão relevante para quem for estudar otimização de consultas no futuro.

> **🎯 Em resumo:**
> Você deve entender como uma entidade associativa (`Atuação`) é reconstruída a partir do banco: buscando as chaves estrangeiras na tabela intermediária e, a partir delas, recompondo as "visões" das entidades relacionadas (Filme e Ator), e como esses objetos alimentam o modelo do componente List na tela.

---

#### 4.1.3 - O Tratamento de Eventos para Limpar Campos

> A seguir, é ilustrada a implementação do tratador de eventos limparCampos.

```java
private void limparCampos(java.awt.event.ActionEvent evt) { 
    sequencialTextField.setText("");
    títuloTextField.setText ("");
    gêneroComboBox.setSelectedIndex(-1);
    anoFormattedTextField.setText ("");
    modelo_atuações_filme.clear();
}
```

> **Observação do Claude — explicação linha por linha:**
>
> - As linhas de `sequencialTextField`, `títuloTextField` e `anoFormattedTextField` seguem o padrão já conhecido de limpar campos de texto com `setText("")`.
> - `gêneroComboBox.setSelectedIndex(-1);` — este é o método correto para "desmarcar" um ComboBox, deixando-o sem nenhum item selecionado. O índice `-1` é um valor especial reconhecido pelo Swing como "nenhuma seleção" (diferente de `setSelectedIndex(0)`, que selecionaria o primeiro item da lista).
> - `modelo_atuações_filme.clear();` — esvazia a lista de atuações exibida, usando o mesmo método `clear()` já visto em `atualizarListaAtuaçõesFilme`.

> **🎯 Em resumo:**
> Você deve saber os três padrões de "limpeza" de componentes vistos até aqui: `setText("")` para campos de texto, `clearSelection()` para `ButtonGroup`, `setSelectedIndex(-1)` para `ComboBox`, e `clear()` para o modelo de um `List`.

---

#### 4.1.4 - O Tratamento de Eventos para Cadastrar Atuações

> A seguir, é ilustrada a implementação do tratador de eventos cadastrarAtuações.

```java
private void cadastrarAtuações(java.awt.event.ActionEvent evt) { 
    String sequencial_str = sequencialTextField.getText();
    int sequencial = 0;
    if (!sequencial_str.isEmpty()) sequencial = Integer.parseInt(sequencial_str);
    if (sequencial > 0) new ControladorCadastroAtuaçõesFilme(this, sequencial);
    else informarErro ("Nenhum filme selecionado");
}
```

> O construtor do ControladorCadastroAtuaçõesFilme é ilustrado na seção 4.2.

> **Observação do Claude — explicação linha por linha:**
>
> - `String sequencial_str = sequencialTextField.getText(); int sequencial = 0; if (!sequencial_str.isEmpty()) sequencial = Integer.parseInt(sequencial_str);` — lê o sequencial atualmente exibido na tela (padrão já conhecido).
> - `if (sequencial > 0) new ControladorCadastroAtuaçõesFilme(this, sequencial); else informarErro ("Nenhum filme selecionado");` — **aqui é usado exatamente o valor sentinela explicado na seção 4.1.1**: se o sequencial for maior que 0, significa que existe um filme já persistido no banco (com um sequencial de verdade, gerado pelo `AUTO_INCREMENT`) — nesse caso, cria-se um novo `ControladorCadastroAtuaçõesFilme`, passando a própria janela atual (`this`) e o sequencial do filme, o que provavelmente abrirá a `JanelaCadastroAtuaçõesFilme` (detalhada na seção 4.2). Se o sequencial for 0 (ou negativo), significa que **nenhum filme foi ainda inserido/consultado** (o campo está no seu valor padrão/vazio), e o usuário é avisado do erro, impedindo o cadastro de atuações "soltas", sem um filme associado.

> **💡 O que o professor está tentando ensinar:**
> Esta seção mostra, na prática, **por que** era importante a convenção do "sequencial = 0 significa ainda não inserido no banco" estabelecida na seção 4.1.1: aqui esse valor é usado como uma condição de validação de negócio — só é permitido cadastrar atuações (relacionar atores) para um filme que já exista de fato no banco de dados (ou seja, que já tenha um sequencial real, maior que zero).

> **🎯 Em resumo:**
> Você deve entender como o valor "sentinela" do sequencial (0 = não inserido, >0 = já inserido) é usado para validar se é seguro abrir a janela de cadastro de atuações para um determinado filme.

---

### 4.2 - Construindo a JanelaCadastroAtuações e suas classes vinculadas

> Os atributos da entidade Atuação são os seguintes:

```java
private int sequencial;
private Filme filme;
private Ator ator;
```

> Como informado anteriormente, na classe Filme não é definida nenhuma referência para a classe Ator. Em um relacionamento n:n, as referências ficam somente na nova classe criada para associar os objetos das entidades relacionadas; neste caso, a classe Atuação.
>
> Conforme ilustrado na seção 4.1.4, o tratador de eventos cadastrarAtuações da JanelaCadastroFilmes cria um objeto do ControladorCadastroAtuações.
>
> A implementação do construtor desse controlador é ilustrada a seguir.

```java
public ControladorCadastroAtuaçõesFilme(JanelaCadastroFilmes janela_cadastro_filmes,
    int sequencial_filme) 
{
    new JanelaCadastroAtuaçõesFilme(this, janela_cadastro_filmes, sequencial_filme)
        .setVisible(true);
}
```

> O construtor do controlador recebe como argumentos: (a) a referência ao objeto da JanelaCadastroFilmes que o executou; e (b) o sequencial que identifica o filme para o qual serão criadas as atuações.
>
> A ilustração da JanelaCadastroAtuações é mostrada a seguir.

**(Imagem no tutorial original: tela "Cadastro de Atuações" mostrando o rótulo "Filme : Titanic", um ComboBox "Atores Cadastrados", uma lista "Atuações no Filme" com atores já vinculados, e os botões Inserir e Remover.)**

> **Observação do Claude:**
> Nesta seção 4.2, o tutorial usa o nome `ControladorCadastroAtuaçõesFilme` (com "Filme" no final), enquanto na frase de introdução da própria seção 4.2 ("cria um objeto do ControladorCadastroAtuações") o nome aparece **sem** "Filme". Preservo ambas as grafias exatamente como aparecem no texto original — trata-se, ao que tudo indica, de uma pequena inconsistência de nomenclatura do professor entre um parágrafo e outro, mas o nome usado de forma consistente no código (`ControladorCadastroAtuaçõesFilme`) é o que deve ser considerado o nome real da classe.

> **Observação do Claude — explicação linha por linha do construtor do controlador:**
>
> - `public ControladorCadastroAtuaçõesFilme(JanelaCadastroFilmes janela_cadastro_filmes, int sequencial_filme) {` — o construtor do controlador recebe a referência à janela "mãe" (que abriu esta nova janela de atuações) e o sequencial do filme para o qual as atuações serão cadastradas.
> - `new JanelaCadastroAtuaçõesFilme(this, janela_cadastro_filmes, sequencial_filme).setVisible(true);` — cria uma nova instância da janela `JanelaCadastroAtuaçõesFilme`, passando: `this` (referência ao próprio controlador recém-criado, para que a janela possa chamar métodos dele depois), a janela mãe recebida, e o sequencial do filme. Em seguida, `.setVisible(true)` torna essa nova janela visível na tela — este é o padrão típico de abertura de uma janela secundária (um "popup" de cadastro) a partir de uma janela principal, encadeando a criação do objeto com a chamada para exibi-lo, tudo em uma única expressão.

> **💡 O que o professor está tentando ensinar:**
> Esta seção reforça, de forma concreta, o princípio já mencionado na seção 4.1: como a classe `Filme` não guarda referência a `Ator` (nem vice-versa), é a classe `Atuação` — com seus dois atributos `filme` e `ator` — quem materializa o relacionamento n:n entre as duas entidades. Também demonstra o padrão de arquitetura de "controlador que abre uma nova janela", recebendo dados de contexto (aqui, qual filme está sendo editado) da janela que o chamou.

> **🎯 Em resumo:**
> Você deve entender que a classe `Atuação` guarda diretamente os objetos `Filme` e `Ator` relacionados (e não apenas seus sequenciais/chaves), e como um controlador secundário é aberto a partir de um evento em outra janela, recebendo o contexto necessário (o filme selecionado) via parâmetros do construtor.

---

> Nas subseções, são descritos os tratadores de evento associados a essa janela. Os dados da janela, seu construtor e seus métodos auxiliares são mostrados a seguir.

```java
ControladorCadastroAtuaçõesFilme controlador;
JanelaCadastroFilmes janela_mãe;
int sequencial_filme;
Ator[] atores_cadastrados;
DefaultListModel modelo_lista_atuações;

public JanelaCadastroAtuaçõesFilme(ControladorCadastroAtuaçõesFilme controlador, 
    JanelaCadastroFilmes janela_mãe, int sequencial_filme) {
    this.controlador = controlador;
    this.janela_mãe = janela_mãe;
    this.sequencial_filme = sequencial_filme;
    atores_cadastrados = Ator.getVisões();
    initComponents();
    atualizarTítuloFilme();
    atualizarListaAtuaçõesFilme();
}

private void atualizarTítuloFilme () {
    Filme filme = Filme.buscarFilme(sequencial_filme);
    filmeLabel.setText("Filme : " + filme.getTítulo());
}

private void atualizarListaAtuaçõesFilme () {
    modelo_lista_atuações = (DefaultListModel)atuações_filmeList.getModel();
    Atuação[] atuações_filme = Atuação.buscarAtuaçõesFilme(sequencial_filme);
    for (Atuação atuação : atuações_filme) modelo_lista_atuações.addElement(atuação);
}
```

> O construtor da janela recebe como argumentos:
> - o objeto chamador do ControladorCadastroAtuações, para que possa chamá-lo nos tratadores de evento: inserirAtuação e removerAtuação;
> - objeto da JanelaCadastroFilmes, para que possa chamar seu método atualizarListaAtuaçõesFilme no tratador de evento: atualizarJanelaCadastroFilmes;
> - e o sequencial do filme para o qual serão cadastradas as atuações.
>
> O método auxiliar atualizarTítuloFilme atualiza o filmeLabel com o título do filme, para o qual serão cadastradas as atuações. O método auxiliar atualizarListaAtuaçõesFilme, atualiza o modelo do atuaçõesList com as atuações cadastradas previamente.

> **Observação do Claude — explicação linha por linha:**
>
> - Os cinco atributos declarados no topo (`controlador`, `janela_mãe`, `sequencial_filme`, `atores_cadastrados`, `modelo_lista_atuações`) guardam, respectivamente: a referência ao controlador (para acionar inserção/remoção), a referência à janela que abriu esta (para poder avisá-la de mudanças), o sequencial do filme em edição, a lista de todos os atores cadastrados (para popular o ComboBox), e a referência ao modelo do List de atuações (mesmo padrão já visto em `JanelaCadastroFilmes`).
> - No construtor, as três primeiras linhas apenas armazenam nos atributos os parâmetros recebidos.
> - `atores_cadastrados = Ator.getVisões();` — busca todas as visões dos atores cadastrados, para popular o ComboBox "Atores Cadastrados" da tela.
> - `initComponents();` — cria os componentes visuais (gerado pelo NetBeans).
> - `atualizarTítuloFilme();` — chama o método auxiliar que atualiza o rótulo mostrando o título do filme em edição (por exemplo, "Filme : Titanic", visível na captura de tela).
> - `atualizarListaAtuaçõesFilme();` — carrega a lista de atores que já atuam neste filme, exibindo-os no List.
> - No método `atualizarTítuloFilme`: `Filme filme = Filme.buscarFilme(sequencial_filme);` busca o filme completo pelo sequencial guardado; `filmeLabel.setText("Filme : " + filme.getTítulo());` monta o texto do rótulo concatenando o prefixo fixo "Filme : " com o título do filme encontrado.
> - No método `atualizarListaAtuaçõesFilme` (desta janela — note que é homônimo de um método com o mesmo nome, mas implementação ligeiramente diferente, existente em `JanelaCadastroFilmes`, visto na seção 4.1.2): `modelo_lista_atuações = (DefaultListModel)atuações_filmeList.getModel();` recupera o modelo do List (igual ao padrão já visto); `Atuação[] atuações_filme = Atuação.buscarAtuaçõesFilme(sequencial_filme);` busca todas as atuações do filme; o `for-each` adiciona cada uma ao modelo.

> **🔎 Esclarecimento:**
> Note que existem **dois métodos diferentes chamados `atualizarListaAtuaçõesFilme`**, um em `JanelaCadastroFilmes` (seção 4.1.2, que recebe um parâmetro `sequencial` e primeiro faz `.clear()` no modelo antes de recarregar) e outro nesta `JanelaCadastroAtuaçõesFilme` (sem parâmetros, usando o atributo `sequencial_filme` já guardado, e sem fazer `.clear()` — já que aqui é chamado apenas uma vez, na criação da janela, quando o modelo já está vazio por padrão). São métodos distintos, de classes distintas, que apenas compartilham o nome porque desempenham papéis semelhantes (atualizar a lista de atuações exibida) em cada contexto.

> **🎯 Em resumo:**
> Você deve entender a estrutura de dados que a `JanelaCadastroAtuaçõesFilme` guarda como contexto (controlador, janela mãe, sequencial do filme, atores cadastrados, modelo da lista) e como o construtor orquestra a inicialização: guardar parâmetros → buscar dados auxiliares → criar componentes → atualizar título → atualizar lista.

---

#### 4.2.1 - O Tratamento de Eventos para Inserir uma Atuação

> A implementação do tratador de eventos inserirAtuação é ilustrada a seguir.

```java
private void inserirAtuação(java.awt.event.ActionEvent evt) { 
    Ator visão_ator = (Ator) atores_cadastradosComboBox.getSelectedItem();
    String mensagem_erro = null;
    Atuação atuação = null;
    if (visão_ator != null) {
        Filme visão_filme = Filme.buscarFilme(sequencial_filme);
        atuação = new Atuação (0, visão_filme, visão_ator);
        mensagem_erro = controlador.inserirAtuação(atuação);
    } else mensagem_erro = "Nenhum ator foi selecionado";
    if (mensagem_erro == null) {
        int sequencial = Atuação.últimoSequencial();
        atuação.setSequencial(sequencial);
        modelo_lista_atuações.addElement(atuação);
    } else informarErro (mensagem_erro);
} 
```

> **Observação do Claude — explicação linha por linha:**
>
> - `Ator visão_ator = (Ator) atores_cadastradosComboBox.getSelectedItem();` — lê o ator selecionado no ComboBox.
> - `if (visão_ator != null) { ... } else mensagem_erro = "Nenhum ator foi selecionado";` — se houver um ator selecionado, prossegue com a criação da atuação; caso contrário, define erro.
> - `Filme visão_filme = Filme.buscarFilme(sequencial_filme);` — busca o filme completo (a partir do sequencial guardado como atributo da classe) para poder criar o objeto `Atuação` (que exige um objeto `Filme`, não apenas seu sequencial).
> - `atuação = new Atuação (0, visão_filme, visão_ator);` — cria uma nova `Atuação`, com sequencial provisório `0` (mesma convenção de "ainda não inserida no banco" já vista para `Filme`), associando o filme e o ator envolvidos.
> - `mensagem_erro = controlador.inserirAtuação(atuação);` — delega ao controlador a inserção efetiva no banco (o controlador devolve `null` em caso de sucesso, ou uma mensagem de erro).
> - `if (mensagem_erro == null) { ... } else informarErro (mensagem_erro);` — se não houve erro, atualiza a tela; caso contrário, exibe o erro.
> - `int sequencial = Atuação.últimoSequencial(); atuação.setSequencial(sequencial);` — mesma técnica já vista para `Filme`: descobre o sequencial gerado pelo banco e atualiza o objeto em memória.
> - `modelo_lista_atuações.addElement(atuação);` — adiciona a nova atuação diretamente ao modelo do List, atualizando a tela sem precisar refazer toda a consulta ao banco.

> **🎯 Em resumo:**
> Você deve reconhecer que este método segue exatamente o mesmo padrão do `inserirFilme` (seção 4.1.1): criar objeto provisório com sequencial 0 → delegar inserção ao controlador → se sucesso, descobrir o sequencial real gerado e atualizar tanto o objeto quanto a interface gráfica.

---

> A implementação do método inserirAtuação, do ControladorCadastroAtuações, é ilustrada a seguir.

```java
public String inserirAtuação (Atuação atuação) {
    boolean existe_atuação = Atuação.existeAtuação
        (atuação.getFilme().getSequencial(), atuação.getAtor().getNome());
    if (!existe_atuação) return Atuação.inserirAtuação (atuação);
    else return "Sequencial de atuação já cadastrado";
}
```

> A implementação do método existeAtuação, da classe Atuação, que recebe como argumentos o sequencial do filme e o nome do ator referenciados pela atuação, é ilustrado a seguir.

```java
public static boolean existeAtuação (int chave_filme, String chave_ator) {
    String sql = "SELECT Sequencial FROM Atuações WHERE FilmeId = ? AND AtorId = ?";
    ResultSet lista_resultados = null;
    boolean existe = false;
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setInt(1, chave_filme);
        comando.setString(2, chave_ator);
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) existe = true;
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) { exceção_sql.printStackTrace (); }
    return existe;
}
```

> A implementação dos métodos últimoSequencial e inserirAtuação, da classe Atuação, é equivalente aos métodos de mesmo nome definido na classe Filme.

> **Observação do Claude — explicação linha por linha:**
>
> - `boolean existe_atuação = Atuação.existeAtuação (atuação.getFilme().getSequencial(), atuação.getAtor().getNome());` — antes de inserir, verifica se já existe uma atuação com o mesmo filme e o mesmo ator, extraindo o sequencial do filme (`atuação.getFilme().getSequencial()`) e o nome do ator (`atuação.getAtor().getNome()`) diretamente dos objetos aninhados dentro da própria atuação recebida.
> - `if (!existe_atuação) return Atuação.inserirAtuação (atuação); else return "Sequencial de atuação já cadastrado";` — se não existir, delega a inserção; se já existir, devolve mensagem de erro (evitando que o mesmo ator seja associado duas vezes ao mesmo filme).
> - No método `existeAtuação`: a consulta `SELECT Sequencial FROM Atuações WHERE FilmeId = ? AND AtorId = ?` busca qualquer linha que combine o filme e o ator informados; `comando.setInt(1, chave_filme); comando.setString(2, chave_ator);` preenchem os parâmetros; `while (lista_resultados.next()) existe = true;` — este é um padrão sutil: como só interessa saber **se existe pelo menos uma linha** (não quantas, nem quais), o `while` simplesmente marca `existe = true` assim que encontra qualquer resultado (mesmo que houvesse mais de uma linha, o valor final seria sempre `true`).

> **🔎 Esclarecimento:**
> A mensagem de erro "Sequencial de atuação já cadastrado" é um pouco confusa/imprecisa em relação ao que o código realmente verifica: o código não está checando se um **sequencial de atuação** específico já existe, mas sim se **já existe uma combinação de filme + ator** cadastrada. Uma mensagem mais precisa seria algo como "Este ator já está cadastrado neste filme". Preservo a mensagem original do tutorial (é apenas o texto literal que aparece no código-fonte), mas deixo esta observação para evitar confusão ao estudar o trecho.

> **🎯 Em resumo:**
> Você deve entender como verificar a existência de uma combinação de duas chaves estrangeiras (filme + ator) usando uma consulta SQL com dois parâmetros, e reconhecer o padrão "usar um `while` só para setar uma flag booleana quando existe pelo menos um resultado".

---

#### 4.2.2 - O Tratamento de Eventos para Remover uma Atuação

> A implementação do tratador de eventos removerAtuação é ilustrada a seguir.

```java
private void removerAtuação(java.awt.event.ActionEvent evt) { 
    Atuação atuação = (Atuação) atuações_filmeList.getSelectedValue();
    String mensagem_erro = null;
    if (atuação != null) mensagem_erro = controlador.removerAtuação(atuação.getSequencial());
    else mensagem_erro = "Nenhum ator foi selecionado";
    if (mensagem_erro == null) modelo_lista_atuações.removeElement(atuação);
    else informarErro (mensagem_erro);
} 
```

> A implementação do método removerAtuação, do ControladorCadastroAtuações, é ilustrada a seguir.

```java
public String removerAtuação (int sequencial) {
    boolean existe_atuação = Atuação.existeAtuação (sequencial);
    if (existe_atuação) return Atuação.removerAtuação (sequencial);
    else return "Não existe atuação com este sequencial";
}
```

> **Observação do Claude — explicação linha por linha:**
>
> - `Atuação atuação = (Atuação) atuações_filmeList.getSelectedValue();` — **este é um ponto importante**: diferente de um ComboBox (que usa `getSelectedItem()`), o componente **List** usa o método `getSelectedValue()` para obter o item atualmente selecionado na lista (também retornando um `Object` genérico, daí o `cast` para `Atuação`).
> - `if (atuação != null) mensagem_erro = controlador.removerAtuação(atuação.getSequencial()); else mensagem_erro = "Nenhum ator foi selecionado";` — se havia uma atuação selecionada na lista, delega ao controlador a remoção (passando o sequencial da própria atuação, não do filme nem do ator); caso contrário, define erro. *(Observação de fidelidade: a mensagem diz "Nenhum ator foi selecionado", mas tecnicamente o que não foi selecionado é uma **atuação** na lista — preservo o texto literal do tutorial.)*
> - `if (mensagem_erro == null) modelo_lista_atuações.removeElement(atuação); else informarErro (mensagem_erro);` — se a remoção foi bem-sucedida, remove o item também da tela (do modelo do List, usando `removeElement`, o método correspondente/inverso de `addElement`); caso contrário, exibe o erro.
> - No método `removerAtuação` do controlador: `boolean existe_atuação = Atuação.existeAtuação (sequencial);` verifica se a atuação de fato existe no banco (usando uma **sobrecarga** — outra versão — do método `existeAtuação`, que recebe apenas o sequencial da atuação, detalhada a seguir); se existir, delega a remoção; caso contrário, devolve mensagem de erro.

> **🔎 Esclarecimento:**
> É importante notar que existem **duas versões (sobrecargas) do método `existeAtuação`** na classe `Atuação`: uma recebendo `(int chave_filme, String chave_ator)` (vista na seção 4.2.1, usada antes de **inserir**, para checar duplicidade de combinação filme+ator) e outra recebendo apenas `(int sequencial)` (vista aqui, usada antes de **remover**, para checar se aquela atuação específica ainda existe). Em Java, é possível ter dois métodos com o mesmo nome, desde que a lista de parâmetros seja diferente — isso se chama **sobrecarga de métodos** (*method overloading*).

> **🎯 Em resumo:**
> Você deve saber que Lists usam `getSelectedValue()` (não `getSelectedItem()`, que é específico de ComboBox) e reconhecer o conceito de sobrecarga de métodos aplicado aqui a duas variações do `existeAtuação`.

---

> A implementação do método existeAtuação, da classe Atuação, que recebe como argumento o sequencial da atuação, é ilustrado a seguir.

```java
public static boolean existeAtuação (int sequencial) {
    String sql = "SELECT COUNT(Sequencial) FROM Atuações WHERE Sequencial = ?";
    ResultSet lista_resultados = null;
    boolean existe = false;
    try {
        PreparedStatement comando = BD.conexão.prepareStatement(sql);
        comando.setInt(1, sequencial);
        lista_resultados = comando.executeQuery();
        while (lista_resultados.next()) existe = true;
        lista_resultados.close();
        comando.close();
    } catch (SQLException exceção_sql) {
        exceção_sql.printStackTrace ();
    }
    return existe;
}
```

> A implementação do método removerAtuação, da classe Atuação, é equivalente ao método de mesmo nome definido na classe Filme.

> **Observação do Claude:**
> Esta versão de `existeAtuação(int sequencial)` segue exatamente o mesmo padrão da versão de dois parâmetros vista na seção anterior, mudando apenas a consulta SQL (agora filtrando diretamente por `Sequencial`, a chave primária da própria tabela `Atuações`, em vez de pela combinação `FilmeId`+`AtorId`). Não repito a explicação linha por linha por ser idêntica em estrutura à já detalhada.

> **🔎 Esclarecimento:**
> O tutorial menciona que "a implementação do método removerAtuação, da classe Atuação, é equivalente ao método de mesmo nome definido na classe Filme" — porém, é importante notar que, **neste tutorial em específico, não foi mostrado explicitamente um método `removerFilme` da classe `Filme`** (apenas foi mencionado, na seção 4.1, que os tratadores `alterarFilme` e `removerFilme` "seguem o mesmo padrão das janelas comentadas anteriormente", sem exibir o código). Portanto, esta é uma referência a um método que, presumivelmente, seguiria o mesmo padrão de `removerAmigo`/`removerAtor` do Tutorial 1 (não incluído neste Tutorial 2), e não a um código explicitamente mostrado neste próprio documento. Deixo esta observação para evitar a impressão de que faltou algo na leitura do PDF.

---

#### 4.2.3 - O Tratamento de Eventos para Atualizar a JanelaCadastroFilmes

> Ao fechar a JanelaCadastroAtuações, as atuações cadastradas para um dado filme devem ser exibidas no atuaçõesList da classe JanelaCadastroFilmes. Para que isso ocorra, deve ser associado ao evento windowClosed, da JanelaCadastroAtuações, o tratador de eventos atualizarJanelaCadastroFilmes cuja implementação é ilustrada a seguir.

```java
private void atualizarJanelaCadastroFilmes(java.awt.event.WindowEvent evt) {
    janela_mãe.atualizarListaAtuaçõesFilme(sequencial_filme); } 
```

> **Observação do Claude — explicação linha por linha:**
>
> - `private void atualizarJanelaCadastroFilmes(java.awt.event.WindowEvent evt) {` — este método é um tratador de um tipo de evento diferente dos vistos até agora: em vez de `ActionEvent` (disparado por cliques em botões), este recebe um `WindowEvent` — um evento relacionado ao **ciclo de vida da própria janela** (abrir, fechar, minimizar, etc.). Especificamente, este é associado ao evento `windowClosed`, disparado quando a janela é efetivamente fechada.
> - `janela_mãe.atualizarListaAtuaçõesFilme(sequencial_filme);` — quando a janela de atuações é fechada, ela chama, na janela "mãe" que a abriu (`JanelaCadastroFilmes`, guardada no atributo `janela_mãe` desde o construtor), o método `atualizarListaAtuaçõesFilme(sequencial)` (aquele mesmo visto na seção 4.1.2), passando o sequencial do filme, para que a lista de atuações exibida na tela principal de filmes seja recarregada, refletindo qualquer inserção/remoção feita na janela secundária que acabou de ser fechada.

> **💡 O que o professor está tentando ensinar:**
> Esta seção resolve um problema de **sincronização entre duas janelas**: o usuário pode inserir ou remover atuações na janela secundária (`JanelaCadastroAtuaçõesFilme`), mas essas mudanças, por si só, não aparecem automaticamente na lista exibida na janela principal (`JanelaCadastroFilmes`), que só foi carregada uma vez, quando o filme foi consultado. A solução é usar o evento `windowClosed` como um "gatilho" — assim que o usuário fecha a janela secundária, a janela principal é instruída a recarregar sua própria lista, garantindo que os dados exibidos fiquem sempre atualizados.

> **🔎 Esclarecimento:**
> Isso também explica **por que** a janela secundária guarda uma referência à `janela_mãe` desde seu construtor (visto no início da seção 4.2): sem essa referência, seria impossível chamar `atualizarListaAtuaçõesFilme` na instância correta da janela principal, já que poderiam existir, teoricamente, múltiplas instâncias de `JanelaCadastroFilmes` abertas simultaneamente (embora isso não seja o caso típico neste sistema).

> **🎯 Em resumo:**
> Você deve entender o conceito de eventos de janela (`WindowEvent`, como `windowClosed`) como complemento aos eventos de ação (`ActionEvent`) já vistos, e a técnica de usar uma referência à "janela mãe" para sincronizar dados entre uma janela principal e uma janela secundária/filha, disparando a atualização no momento em que a janela filha é fechada.

---

## 5 - Ilustrando as diferenças em um Relacionamento de Um para Muitos (1:n)

> Existem situações, nas quais o relacionamento é de um para muitos (1:n) como, por exemplo, o relacionamento entre as entidades Montadora e Veículo: (a) uma montadora pode produzir vários veículos; e (b) um veículo é produzido por uma única montadora.
>
> No relacionamento n:n entre Filme e Ator, foi necessário criar a entidade Atuação para relacionar um filme com um ator, dado que na tabela Filmes um filme não pode referencias as chaves estrangeiras de vários atores, e vice-versa na tabela Atores com as chaves estrangeiras de vários filmes.
>
> No entanto, no relacionamento 1:n entre Montadora e Veículo, um veículo da tabela Veículos pode referenciar a chave estrangeira da montadora que o fabricou. Portanto, não é necessário criar uma entidade adicional para caracterizar um relacionamento 1:n. Essa simplificação tem as seguintes consequências:
> - na criação de um veículo, na JanelaCadastroVeículo, deve ser escolhida a montadora que o fabrica, utilizando o componente montadoraComboBox exibindo as visões das montadoras cadastradas;
> - obviamente, o cadastro da montadora, que fabrica o veículo, deve ter sido realizado previamente na JanelaCadastroMontadoras;
> - na consulta de uma montadora na JanelaCadastroMontadoras, o componente veículosList (encapsulado pelo componente veículosPanel) deve exibir as visões dos veículos que foram previamente associados à montadora consultada na JanelaCadastroVeículos;
> - obviamente, quando uma montadora é criada, na JanelaCadastroMontadoras, o componente veículosList estará vazio, só passando a exibir veículos após eles terem sido criados na JanelaCadastroVeículos associados a uma determinada montadora, a partir do método consultarMontadora, que por sua vez executa o método privado atualizarListaVeículosMontadora, ilustrado a seguir.

```java
public void atualizarListaVeículosMontadora (int sequencial_montadora) {
    modelo_veículos_montadora.clear();
    Veículo[] veículos_montadora = Veículo.buscarVeículosMontadora (int sequencial_montadora);
    for (Veículo visão_veículo : veículos_montadora) 
        modelo_veículos_montadora.addElement(visão_veículo);
}
```

> **💡 O que o professor está tentando ensinar:**
> Esta seção final faz um **contraste direto** com tudo o que foi visto na seção 4: ela mostra que nem todo relacionamento entre entidades precisa de uma tabela/entidade associativa. A regra de modelagem relacional é: relacionamentos **n:n** exigem uma tabela associativa (como `Atuação`), mas relacionamentos **1:n** podem ser representados com **uma única chave estrangeira** na tabela do lado "muitos" (aqui, `Veículos`), apontando para a tabela do lado "um" (aqui, `Montadoras`) — sem necessidade de nenhuma tabela extra.

> **Observação do Claude:**
> Chamo atenção para um provável **erro de digitação/sintaxe no código Java** desta seção: a linha
> ```java
> Veículo[] veículos_montadora = Veículo.buscarVeículosMontadora (int sequencial_montadora);
> ```
> não compilaria em Java como está escrita — dentro de uma **chamada** de método (e não em sua declaração), não se deve repetir o tipo do parâmetro (`int`); o correto seria apenas:
> ```java
> Veículo[] veículos_montadora = Veículo.buscarVeículosMontadora (sequencial_montadora);
> ```
> (Isso é coerente com a assinatura do método `buscarVeículosMontadora`, mostrada logo a seguir no tutorial, que já recebe `sequencial_montadora` como parâmetro do método externo `atualizarListaVeículosMontadora`.) Preservo o código exatamente como está no PDF original, apenas sinalizando aqui o que parece ser um erro de digitação do professor, sem corrigi-lo silenciosamente no texto original acima.

> **Observação do Claude — explicação linha por linha (ignorando o provável erro apontado acima):**
>
> - `modelo_veículos_montadora.clear();` — esvazia o modelo do List de veículos antes de recarregá-lo (mesmo padrão já visto em `atualizarListaAtuaçõesFilme`).
> - `Veículo[] veículos_montadora = Veículo.buscarVeículosMontadora (sequencial_montadora);` — busca, na classe `Veículo`, todos os veículos associados à montadora identificada pelo sequencial recebido.
> - `for (Veículo visão_veículo : veículos_montadora) modelo_veículos_montadora.addElement(visão_veículo);` — percorre os veículos encontrados e os adiciona ao modelo do List, exatamente no mesmo padrão já visto para atuações de filme.

> **🔎 Esclarecimento:**
> Repare a simetria estrutural entre este método e o `atualizarListaAtuaçõesFilme` da seção 4.1.2: ambos seguem o padrão "limpar modelo → buscar dados relacionados → percorrer e adicionar ao modelo". A diferença fundamental está **de onde vêm os dados**: em `atualizarListaAtuaçõesFilme`, os dados vêm de uma consulta à tabela associativa `Atuações` (relacionamento n:n); aqui, em `atualizarListaVeículosMontadora`, os dados vêm de uma consulta direta à tabela `Veículos`, filtrando por sua própria chave estrangeira `MontadoraId` (relacionamento 1:n) — sem precisar de nenhuma tabela intermediária.

> **🎯 Em resumo:**
> Você deve saber diferenciar, na prática de código, como consultar os "muitos" relacionados a um "um" em um relacionamento 1:n (uma simples consulta filtrando pela chave estrangeira na própria tabela do lado "muitos") versus como fazer o mesmo em um relacionamento n:n (que exige passar pela tabela associativa, como visto na seção 4).

---

> O script SQL de criacão das tabelas Montadoras e Veículos é ilustrado a seguir.

```sql
DROP TABLE IF EXISTS Veículos;
DROP TABLE IF EXISTS Montadoras;

CREATE TABLE Montadoras (
    Sequencial INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(60) NOT NULL,
    PaísOrigem VARCHAR(30) NOT NULL,
    SedeMaiorFábricaBrasil VARCHAR(30) NOT NULL);

CREATE TABLE Veículos (
    MarcaModelo VARCHAR(15) NOT NULL PRIMARY KEY,
    Alimentação INT,
    Potência INT,
    Importado BOOLEAN,
    MontadoraId INT NOT NULL,
    FOREIGN KEY (MontadoraId) REFERENCES Montadoras(Sequencial));
```

> **Observação do Claude — explicação do script SQL:**
>
> - `DROP TABLE IF EXISTS Veículos; DROP TABLE IF EXISTS Montadoras;` — novamente, a ordem importa: `Veículos` (que tem a chave estrangeira, "lado muitos") é removida antes de `Montadoras` (referenciada, "lado um"), pelo mesmo motivo já explicado na seção 4 (não se pode remover uma tabela referenciada por uma FOREIGN KEY enquanto a tabela que referencia ainda existir).
> - `CREATE TABLE Montadoras (...)` — cria a tabela com `Sequencial` (chave primária autoincrementada), `Nome`, `PaísOrigem` e `SedeMaiorFábricaBrasil` (todos obrigatórios).
> - `CREATE TABLE Veículos (...)` — cria a tabela com `MarcaModelo` como **chave primária** (aqui, diferente de `Filmes`/`Montadoras`, a chave primária não é um sequencial autoincrementado, mas sim o próprio texto da marca/modelo — uma escolha de modelagem que pressupõe que essa combinação já é naturalmente única); `Alimentação` (inteiro, opcional — sem `NOT NULL` — presumivelmente armazenando o `ordinal()` do enum `Alimentação` visto mais adiante); `Potência` (inteiro, opcional); `Importado` (booleano, opcional); e `MontadoraId` (inteiro, **obrigatório**, sendo a chave estrangeira que caracteriza o relacionamento 1:n, apontando para `Montadoras.Sequencial`).

> **🔎 Esclarecimento:**
> É essa **única coluna `MontadoraId`**, dentro da própria tabela `Veículos`, que torna desnecessária qualquer tabela associativa: cada veículo (linha) já carrega, dentro de si, a referência a exatamente uma montadora — o que é logicamente compatível com a regra de negócio "um veículo é fabricado por uma única montadora". Já o lado "muitos" (uma montadora ter vários veículos) é simplesmente uma consequência de que **várias linhas diferentes** da tabela `Veículos` podem ter o mesmo valor de `MontadoraId`.

> **🎯 Em resumo:**
> Você deve entender, olhando diretamente para o script SQL, como reconhecer visualmente um relacionamento 1:n (uma única coluna de chave estrangeira na tabela do lado "muitos") em contraste com um relacionamento n:n (uma tabela associativa separada com duas colunas de chave estrangeira, como visto na seção 4).

---

> A seguir são ilustrados na classe Veículo: (a) o método estático buscarVeículosMontadora; e (2) os atributos, os construtores e o método toString.

```java
class Veículo {
    public static Veículo[] buscarVeículosMontadora (int sequencial_montadora) {
        String sql = null;
        ResultSet lista_resultados = null;
        ArrayList<Veículo> visões = new ArrayList();
        sql = "SELECT MarcaModelo, Alimentação FROM Veículos WHERE MontadoraId = ?";
        try {
            PreparedStatement comando = BD.conexão.prepareStatement(sql);
            comando.setInt(1, sequencial_montadora);
            lista_resultados = comando.executeQuery();
            while (lista_resultados.next()) {
                visões.add(new Veículo (lista_resultados.getString("MarcaModelo"),
Alimentação.values()[lista_resultados.getInt("Alimentação")] ));
            }
            lista_resultados.close();
            comando.close();
        } catch (SQLException exceção_sql) { exceção_sql.printStackTrace (); }
        return visões.toArray(new Veículo[visões.size()]);
    }

    public enum Alimentação { flex, diesel, elétrico };
    private String marca_modelo;
    private Alimentação alimentação;
    private int potência;
    private boolean importado;

    public Veículo (marca_modelo, alimentação, potência, importado) {
        this.marca_modelo = marca_modelo;
        this.alimentação = alimentação;
        this.potência = potência;
        this.importado = importado;
    }

    public Veículo (marca_modelo, alimentação) {
        this.marca_modelo = marca_modelo;
        this.alimentação = alimentação;
    }

    public String toString() {
        return marca_modelo + " [" + alimentação + "]";
    }
}
```

> **Observação do Claude:**
> Este último bloco de código do tutorial contém **erros de sintaxe Java visíveis**, preservados aqui exatamente como aparecem no PDF original, sem correção silenciosa:
> 1. Nos dois construtores (`public Veículo (marca_modelo, alimentação, potência, importado)` e `public Veículo (marca_modelo, alimentação)`), os parâmetros estão **sem tipos declarados** — em Java, todo parâmetro de método precisa de um tipo antes do nome (ex.: `String marca_modelo`, `Alimentação alimentação`, `int potência`, `boolean importado`). Como está, o código não compilaria.
> 2. Há uma **chave sobrando** ao final da chamada `new Veículo (lista_resultados.getString("MarcaModelo"), Alimentação.values()[lista_resultados.getInt("Alimentação")] ));` — comparando com o padrão usado em `buscarAtuaçõesFilme` (seção 4.1.2), o parêntese de fechamento de `visões.add(...)` parece estar duplicado/mal balanceado neste trecho especificamente, embora, olhando com cuidado a contagem de parênteses, o código pode estar sintaticamente correto — deixo esta ressalva porque a formatação do PDF quebra a linha de forma que dificulta a conferência visual exata; recomendo, ao estudar/reproduzir este trecho, conferir cuidadosamente o balanceamento de parênteses no seu próprio editor de código.
> 3. O ponto e vírgula ao final de `public enum Alimentação { flex, diesel, elétrico };` é **desnecessário** (mas não é um erro — apenas uma instrução vazia extra, que o compilador Java aceita silenciosamente, embora não seja o estilo mais comum).
>
> Presumo (por inferência, já que o texto não afirma isso explicitamente) que essas inconsistências sejam decorrência de o professor ter simplificado/resumido o código deste último trecho — possivelmente o único deste tutorial que não foi copiado de um exemplo já testado e funcional, mas escrito de memória ou de forma ilustrativa/resumida, já que é o encerramento do documento.

> **Observação do Claude — explicação conceitual (assumindo a correção implícita dos tipos de parâmetro):**
>
> - `public static Veículo[] buscarVeículosMontadora (int sequencial_montadora) {` — método estático que busca todos os veículos de uma montadora específica.
> - `sql = "SELECT MarcaModelo, Alimentação FROM Veículos WHERE MontadoraId = ?";` — consulta filtrando pela chave estrangeira `MontadoraId`, trazendo apenas as colunas `MarcaModelo` e `Alimentação` (não todas as colunas da tabela — presumivelmente porque só esses dois dados são necessários para a "visão" resumida do veículo usada nesta lista).
> - `visões.add(new Veículo (lista_resultados.getString("MarcaModelo"), Alimentação.values()[lista_resultados.getInt("Alimentação")]));` — cria uma "visão" resumida do veículo, usando o construtor de dois parâmetros (marca/modelo + alimentação), convertendo o valor inteiro armazenado no banco de volta para o valor do enum `Alimentação` (mesma técnica de índice/`ordinal()` já vista repetidamente ao longo do tutorial, agora aplicada na direção "banco → objeto Java").
> - `public enum Alimentação { flex, diesel, elétrico };` — declara o enum com três valores possíveis para o tipo de alimentação do veículo (flex, diesel, elétrico), na ordem correspondente aos índices 0, 1 e 2, respectivamente.
> - Os quatro atributos privados (`marca_modelo`, `alimentação`, `potência`, `importado`) correspondem diretamente às colunas da tabela `Veículos` (exceto, claro, `MontadoraId`, que não é um atributo desta classe, mas seria representado de outra forma — presumivelmente através de uma referência ao objeto `Montadora`, embora isso não seja mostrado neste trecho final do tutorial).
> - Os **dois construtores sobrecarregados**: um completo (recebendo todos os quatro atributos, presumivelmente usado ao criar/alterar um veículo a partir dos dados completos da tela) e um resumido (recebendo só `marca_modelo` e `alimentação`, usado para criar a "visão" simplificada exibida na lista de veículos de uma montadora, análogo aos construtores "de visão" já vistos para `Filme` na seção 4.1).
> - `public String toString() { return marca_modelo + " [" + alimentação + "]"; }` — sobrescreve (`@Override`, embora a anotação não esteja explícita no código) o método `toString()` da classe `Object`, definindo como um objeto `Veículo` deve ser convertido para texto quando exibido — por exemplo, dentro de um componente List (que, ao exibir seus itens, chama automaticamente o `toString()` de cada objeto do modelo). O resultado seria algo como `"Gol [flex]"`.

> **💡 O que o professor está tentando ensinar:**
> Este último trecho do tutorial fecha o material demonstrando, na prática, como fica a classe do lado "muitos" (`Veículo`) de um relacionamento 1:n: com um método de busca filtrando pela chave estrangeira (`MontadoraId`), um enum próprio (`Alimentação`) usando a mesma técnica de índice já vista em todo o tutorial, e a reutilização do padrão de "construtor completo + construtor de visão" já estabelecido para `Filme`.

> **🎯 Em resumo:**
> Você deve concluir o estudo deste tutorial sabendo reconhecer e implementar tanto relacionamentos n:n (com entidade associativa, como `Atuação`) quanto relacionamentos 1:n (com uma única chave estrangeira, como `MontadoraId` em `Veículos`), além de dominar o uso apropriado de CheckBox (booleanos), RadioButton+ButtonGroup (enumerados pequenos) e ComboBox (enumerados maiores ou combinados com objetos de outras entidades) na construção de interfaces gráficas Java Swing.

---

## Considerações finais sobre este material de estudo

> **Observação do Claude:**
> Este documento reproduz **a totalidade do texto e do código** presentes no arquivo `LPII_-_Tutorial_2_-_Cadastro_com_Componentes_Adicionais.pdf` (16 páginas), na ordem original, sem omissões, resumos ou reescritas do conteúdo do professor. Todas as capturas de tela do tutorial foram indicadas entre parênteses, descrevendo seu conteúdo, já que não é possível reproduzir imagens dentro deste documento de texto — mas nenhuma informação textual das legendas ou do corpo do tutorial foi omitida.
>
> Ao longo do texto, três pontos foram sinalizados como possíveis inconsistências do material original (não corrigidos silenciosamente, apenas comentados): (1) uma possível troca de nome entre "obterAmigoInformado" e "consultarAmigo" no início da seção 2.3; (2) uma pequena variação de nomenclatura entre "ControladorCadastroAtuações" e "ControladorCadastroAtuaçõesFilme" na seção 4.2; e (3) erros de sintaxe Java (parâmetros sem tipo declarado) no bloco final de código da classe `Veículo`, na seção 5.
