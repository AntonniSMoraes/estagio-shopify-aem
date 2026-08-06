# Projeto AEM

Este repositório contém a resolução dos **Desafios Relacionados ao AEM** do Programa de Estágio WebJump. O projeto utiliza como base o template de melhores práticas da Adobe para aplicações AEM.

## Conteúdo
1. Solução para o desafio 5.1 - Ambiente + primeiro deploy do WKND
2. Solução para o desafio 5.2 - Componente do zero: Cartão de Perfil
3. Solução para o desafio 6.1 - Editable Template + Políticas
4. Solução para o desafio 6.2 - Componente Full-Stack com Style System
5. Solução para o desafio 7.1 - Componente Equipe com Multifield, Delegação e Serviço OSGi
6. Solução para o desafio 7.2 - Ultimas do Magazine - query, exporter e endpoint
7. Solução para o desafio 8.1 - Modelando o Catálogo de Aventuras


## Objetivo do Desafio 5.1
O objetivo principal deste desafio é provar o domínio do ciclo de desenvolvimento no AEM: **editar → build → deploy → ver no Author**. 
Para isso, o ambiente de desenvolvimento local foi configurado do zero e o componente padrão `HelloWorld` foi modificado para exibir um novo campo personalizado.

### O que foi implementado:
1. **Configuração de Ambiente:** Instalação do JDK 21, Apache Maven 3.9.x e AEM SDK.
2. **Sling Model (`HelloWorldModel.java`):** Lógica Java alterada para receber a injeção de um novo campo customizado (Subtítulo) via `@ValueMapValue`.
3. **Dialog (`_cq_dialog/.content.xml`):** Inclusão de um novo campo de input de texto no painel de autoria do AEM.
4. **HTL (`helloworld.html`):** Marcação visual atualizada para exibir o valor dinâmico do novo campo utilizando a linguagem de template HTL (ex: `${model.subtitulo}`).

## Objetivo do Desafio 5.2
O objetivo principal deste desafio é construir um componente do zero **(Cartão de Perfil)**, sem herdar dos Core Components da Adobe, para consolidar o entendimento da arquitetura de componentes autônomos no AEM.
Para isso, toda a estrutura de pastas, dialog, lógica de back-end (Java) e renderização condicional no front-end (HTL) foram desenvolvidas manualmente.

### O que foi implementado:
1. **Nó do Componente (`.content.xml`):** Criação da raiz do componente no módulo `ui.apps`, definindo sua identidade e alocando-o no grupo "WKND - Custom".
2. **Sling Model (`PerfilModel.java`):** Criação da classe Java no módulo `core` para resgatar os valores preenchidos pelo autor (Nome, Cargo e Biografia) utilizando a anotação `@ValueMapValue`.
3. **Dialog (`_cq_dialog/.content.xml`):** Construção da interface de autoria com campos do tipo *textfield* e *textarea*.
4. **HTL (`perfil.html`):** Desenvolvimento da marcação visual puxando as variáveis do back-end. Foi aplicada a lógica condicional `data-sly-test="${model.cargo}"` para ocultar o elemento HTML do cargo caso o autor deixe o campo em branco.
5. **Estilização SCSS (`_perfil.scss`):** Aplicação de estilos seguindo a metodologia BEM (ex: `cmp-perfil`) e integração da compilação do arquivo diretamente no `main.scss` do projeto.

## Objetivo do Desafio 6.1
O objetivo principal deste desafio é estruturar um **Editable Template** do zero no AEM, definindo as regras de layout, travamentos de estrutura e garantindo o controle do autor por meio de **Políticas (Policies)** de componentes.

### O que foi implementado:
1. **Criação do Template Editável (`Landing WKND`):** Criação do modelo em `Tools > General > Templates > WKND Site` utilizando a página base.
2. **Definição da Estrutura (Structure):**
   - **Header e Footer:** Configuração de componentes *Experience Fragment* vinculados aos fragmentos do WKND e com os cadeados **fechados (travados)** para evitar alteração ou remoção por parte dos autores.
   - **Layout Container Central:** Configuração do container de conteúdo central com o cadeado **aberto (destravado)**, delimitando a área editável da página.
3. **Configuração da Política (Policy):** Restrição dos componentes permitidos (*Allowed Components*) no Layout Container central exclusivamente para os 5 Core Components exigidos: **Title**, **Text**, **Image**, **Teaser** e **Button**.
4. **Ajuste de Layout (Layout Mode):** Utilização do sistema de grid nativo no modo *Layout* para estruturar colunas e alinhamento do container central.
5. **Validação na Prática (Criação de Página):** Ativação do template (*Enable*) e publicação de uma página de teste via menu *Sites*, comprovando que apenas os 5 componentes permitidos pela política ficam acessíveis no painel de autoria.

## Objetivo do Desafio 6.2
O objetivo deste sprint foi construir um componente full-stack (Destaque / Callout), unindo todas as camadas de arquitetura do AEM: Node/Dialog, Camada de Negócio (Sling Model Java), Camada de Apresentação (HTL) e Estilização isolada via ClientLib com suporte nativo ao Style System do AEM.

### O que foi implementado:
1. **Estrutura e Dialog (`ui.apps`):** Criação do nó JCR do componente e de um dialog Touch UI contendo 4 campos configurados: *Título*, *Texto*, *Texto do Botão* e *URL do Botão* (utilizando um campo do tipo `pathbrowser`).
2. **Sling Model (`core`):** Implementação da classe Java `DestaqueModel.java` com injeção segura (`InjectionStrategy.OPTIONAL`). Foi implementada a regra de negócio `isMostrarBotao()`, garantindo que o botão só seja renderizado dinamicamente se o autor preencher uma URL válida.
3. **Client Library (`ui.apps`):** Criação de uma ClientLib dedicada (categoria `wknd.destaque`) com a propriedade de segurança `allowProxy="{Boolean}true"`. Os estilos base e as classes modificadoras do Style System (`.cmp-destaque--claro` e `.cmp-destaque--escuro`) foram estruturados no arquivo `style.css`.
4. **Sightly / HTL (`destaque.html`):** Marcação semântica com invocação da ClientLib customizada via `data-sly-call`. A renderização condicional do botão foi aplicada via `data-sly-test="${model.mostrarBotao}"`, respeitando rigorosamente a separação de responsabilidades (lógica no Java, apresentação no HTML).
5. **Integração com Style System:** Configuração de uma política (Policy) no template permitindo o componente Destaque e registrando os temas visuais "Claro" e "Escuro", permitindo ao autor alternar o visual dinamicamente na página através do ícone de pincel.

## Objetivo do Desafio 7.1
O objetivo deste desafio foi construir o componente Equipe WKND, integrando um Multifield de itens compostos no diálogo de autoria, delegação e limitação de exibição por Serviço OSGi com configuração em runtime, e estilização dedicada via ClientLib responsiva em formato de cards.

### O que foi implementado:
1. **Configuração e Serviço OSGi (`MostrarEquipeService`):**
   - Criação da interface de configuração `@ObjectClassDefinition` (`MostrarEquipeConfig.java`) contendo a propriedade global `maxMembros`.
   - Implementação do serviço OSGi (`MostrarEquipeServiceImpl.java`) anotado com `@Component` e `@Designate`, permitindo alterar a quantidade máxima de membros exibidos dinamicamente no Web Console (`/system/console/configMgr`) sem necessidade de redeploy da aplicação.
2. **Sling Model (`EquipeModel.java`):**
   - Mapeamento da coleção de membros cadastrados via multifield utilizando a anotação `@ChildResource`.
   - Injeção do serviço OSGi via `@OSGiService` para aplicar a regra de limitação (`limit()`) dos membros a serem entregues para a camada de apresentação.
   - Aplicação de tratamento gracioso contra valores nulos (`DefaultInjectionStrategy.OPTIONAL`) e regras de resiliência.
3. **Diálogo Touch UI (`_cq_dialog`):**
   - Construção do diálogo estruturado em `granite/ui/components/coral/foundation/form/multifield` para cadastro do *Título da Seção* e múltiplos membros contendo os campos: *Nome* (`textfield`), *Cargo* (`textfield`) e *Foto* (`pathfield`).
4. **HTL e ClientLib dedicada (`clientlib-equipe`):**
   - Renderização semântica em HTL (`equipe.html`) com iteração de lista via `data-sly-list` e carregamento otimizado de imagens.
   - Criação da ClientLib (`categories="[wknd.equipe]"`, `allowProxy="{Boolean}true"`) com estilização responsiva em CSS contendo layout em grid, animações em hover e fotos de perfil em avatares circulares.

## Objetivo do Desafio 7.2
O objetivo principal deste desafio foi construir um componente avançado que utiliza a API do QueryBuilder para buscar os artigos mais recentes do Magazine, além de expor esses dados via Sling Model Exporter (`.model.json`) e Sling Servlet por `resourceType` (`.ultimas.json`).

### O que foi implementado:
1. **Sling Model (`UltimasMagazineModel.java`):** Lógica Java responsável por executar a busca dinâmica via `QueryBuilder` filtrando por páginas em `/content/wknd/us/en/magazine` ordenadas por data de criação (`jcr:created desc`). O modelo foi configurado com `@Exporter(name = "jackson", extensions = "json")` e dupla adaptação (`SlingHttpServletRequest` e `Resource`).
2. **Item DTO (`ArtigoItem.java`):** Modelo utilitário responsável por encapsular os atributos individuais de cada artigo (título, caminho `.html` e imagem).
3. **Sling Servlet (`UltimasMagazineServlet.java`):** Servlet registrado por `resourceType` com seletor `ultimas` e extensão `json`, retornando a lista serializada dos artigos em formato JSON direto na requisição HTTP.
4. **Dialog Touch UI (`_cq_dialog/.content.xml`):** Diálogo de autoria configurado com um campo do tipo `numberfield` para permitir ao autor definir a quantidade $N$ de artigos a serem exibidos (valor padrão `4`).
5. **HTL (`ultimas-do-magazine.html`):** Marcação visual em grid responsiva construída com a linguagem de template HTL (`data-sly-list`) para iterar e exibir os artigos retornados pelo modelo.
6. **ClientLib & Estilização (`css/magazine.css`):** Criação da biblioteca de cliente (`wknd.site`) contendo os estilos em CSS para renderização dos cards com efeito hover.

#### Sling Servlet ou Sling Model Exporter?

Enquanto Servlet trabalha entregando requisições HTTP diretamente, ao criar endpoints, o Sling Model Exporter utiliza o mesmo model que alimenta o HTL, passando a responder como JSON. Assim sendo, usamos Servlet quando buscamos controle total sobre a requisição HTTP, ou quando queremos executar uma lógica customizada, e usamos Sling Model Exporter quando queremos apenas expor os dados do componente para serem consumidos por um front-end.

## Objetivo do Desafio 8.1
Criar uma arquitetura de conteúdo Headless no AEM utilizando Content Fragment Models (CFMs), cadastrar o conteúdo e expô-lo via API GraphQL utilizando consultas persistidas.

### O que foi implementado:
1. **Content Fragment Models (CFMs):**
   - Criação do modelo Instrutor contendo os campos: *Nome* (`Single line text`), *Bio* (`Multi line text` em Rich Text), *Especialidades* (`Enumeration` múltipla), *Foto* (`Content Reference`) e *Anos de experiência* (`Number` com validação de valor mínimo 0).
   - Criação do modelo Aventura contendo os campos: *Título*, *Descrição*, *Dificuldade* (`Enumeration`), *Preço* (`Number`), *Imagem* (`Content Reference`) e o campo fundamental de Instrutor (`Fragment Reference`), estabelecendo o relacionamento entre os dois modelos.
2. **Instâncias de Conteúdo (Content Fragments):**
   - Cadastro de 3 instâncias baseadas no modelo de Instrutores.
   - Cadastro de 4 instâncias baseadas no modelo de Aventuras, utilizando o campo de referência de fragmento para vincular cada aventura ao seu respectivo instrutor real no repositório.
3. **Configuração da API GraphQL:**
   - Habilitação da permissão de GraphQL Persistent Queries na pasta raiz do projeto através do *Configuration Browser*.
   - Criação de um Endpoint GraphQL dedicado e atrelado à configuração do projeto em `Tools > General > GraphQL`, permitindo a exposição segura dos modelos.
4. **Desenvolvimento e Persistência de Queries (GraphiQL):**
   - Escrita e validação de 3 consultas distintas na IDE embutida do AEM: 
     - **Query List:** Trazendo a lista de aventuras com os dados do instrutor aninhados via fragment spread (`... on InstrutorModel`).
     - **Query ByPath:** Busca isolada de um fragmento pelo seu caminho absoluto.
     - **Query com Filtro:** Busca utilizando argumentos dinâmicos (Variables) para filtrar aventuras pela dificuldade.
   - Persistência da *Query List* no repositório e validação do seu funcionamento através de uma chamada HTTP **GET** no navegador acessando a rota `/graphql/execute.json/...`.

### Decisões de Modelagem

**Por que utilizar Enumeração (Enumeration) para a Dificuldade e Especialidades?**

Foi utilizado o tipo Enumeração para garantir a padronização dos dados. Usar outros tipos para esses campos poderia causar problemas ao tentar criar filtros, no sentido de que o usuário pode acabar criando variações da mesma palavra (por exemplo, uma palavra começando com letra maiúscula e outra não, ou até mesmo sinônimos). Assim sendo, a escolha de enumeração restringe o usuário a opções travadas.

### Evidências de Funcionamento (Prints)

<img width="977" height="662" alt="componente-campo" src="https://github.com/user-attachments/assets/6aef3fd7-79ca-4e0e-9ce2-f6d05f67d874" />
<br>
<em>5.1 - Configuração do componente no modo Author demonstrando o novo campo adicionado.</em>

<br><br>

<img width="625" height="619" alt="componente-atualizada" src="https://github.com/user-attachments/assets/72476d05-5803-45b8-acb0-2fba79a7f989" />
<br>
<em>5.1 - Resultado final do componente renderizado na página de teste com o subtítulo.</em>

<br><br>

<img width="977" height="662" alt="componente-campo" src="https://github.com/user-attachments/assets/6aef3fd7-79ca-4e0e-9ce2-f6d05f67d874" />
<br>
<em>5.2 - Configuração do Cartão de Perfil no modo Author demonstrando os 3 campos criados.</em>

<br><br>

<img width="625" height="619" alt="componente-atualizada" src="https://github.com/user-attachments/assets/72476d05-5803-45b8-acb0-2fba79a7f989" />
<br>
<em>5.2 - Resultado final do Cartão de Perfil estilizado e renderizado na página.</em>
<br>
<br>

<br><br>

<img width="1907" height="801" alt="image" src="https://github.com/user-attachments/assets/0b2e954c-b924-44fa-b4a6-ba76ad03079d" />
<br>
<em>6.1 - Página criada com template WKND.</em>
<br>
<br>

<br><br>

<img width="1851" height="509" alt="Captura de tela 2026-07-24 103632" src="https://github.com/user-attachments/assets/2b8bdc03-70ae-45b6-ace4-d62ff146d6a8" />


<br>
<em>6.2 - Página com template WKND editado para receber os Destaques exibindo o tema claro.</em>
<br>
<br>

<br><br>

<img width="1859" height="228" alt="image" src="https://github.com/user-attachments/assets/02df7707-fa1f-4439-a875-267eed0f7acb" />

<br>
<em>6.2 - Página com template WKND editado para receber os Destaques exibindo o tema escuro.</em>
<br>
<br>

<br><br>

<img width="1854" height="845" alt="image" src="https://github.com/user-attachments/assets/ea5ebb37-bcd5-4765-a789-7ae0c65a1785" />

<br>
<em>7.1 - Página com componente Equipe WKND exibindo cards dentro do limite inicial (3 cards).</em>
<br>
<br>

<br><br>

<em>Evidências do exercício 7.2</em>

#### 1. Query Debugger
<img width="1737" height="726" alt="querybuilder" src="https://github.com/user-attachments/assets/ab40d943-bc79-4e9f-be56-4d41ddca6b76" />

#### 2. Configuração do Diálogo (Touch UI)
<img width="1885" height="965" alt="pg_magazine" src="https://github.com/user-attachments/assets/cc54f075-3dbb-4295-a2dd-480d6eb915dc" />


#### 3. Renderização Visual no Author (HTL + CSS)
<img width="1857" height="750" alt="pg_magazine_renderizado" src="https://github.com/user-attachments/assets/9c741b11-cbe5-4488-a911-29b448238171" />

#### 4. Resposta via Sling Model Exporter (.model.json)
<img width="1534" height="620" alt="sling_exporter" src="https://github.com/user-attachments/assets/5801985f-6eb9-4729-abc5-78391cb539f3" />

#### 5. Resposta via Sling Servlet (.ultimas.json)
<img width="1271" height="491" alt="sling_servlet" src="https://github.com/user-attachments/assets/da2a21aa-aee1-42e7-bfa3-1df5aec5c489" />

<br><br>

<em>Evidências do exercício 8.1</em>

**1. Query List (Com dados aninhados do Instrutor)**
<img width="1712" height="843" alt="Query List no GraphiQL" src="https://github.com/user-attachments/assets/8d466de9-e913-4b35-8483-e82a71ede30f" />


**2. Query By Path (Busca de aventura específica)**
<img width="1902" height="803" alt="QueryByPath" src="https://github.com/user-attachments/assets/acd08350-1cf9-4ccc-8484-cfbd6e81891e" />


**3. Query com Filtro Dinâmico (Variáveis)**
<img width="1610" height="820" alt="QueryComFiltro" src="https://github.com/user-attachments/assets/56947c21-9b45-4c5e-b688-68e3383610db" />


**4. Persisted Query (Resposta JSON via GET no navegador)**
<img width="795" height="677" alt="queryNoNavegador" src="https://github.com/user-attachments/assets/72de05bd-f42d-4256-b275-f681ffe3417f" />


## Pré-requisitos do Ambiente

Antes de configurar o AEM, certifique-se de que os seguintes requisitos estão instalados e configurados:

### Para Linux (Ubuntu)
1. **Java Development Kit (JDK 21):**
    ```bash
    sudo apt update
    sudo apt install openjdk-21-jdk
    sudo update-alternatives --config java
    export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
    ```

2. **Apache Maven (versão 3.9.x):**
   ```bash
    wget [https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz](https://dlcdn.apache.org/maven/maven-3/3.9.9/binaries/apache-maven-3.9.9-bin.tar.gz)
    sudo tar xf apache-maven-3.9.9-bin.tar.gz -C /opt
    export PATH=/opt/apache-maven-3.9.9/bin:$PATH
    ```

    ou

    ```bash
    sudo apt install maven
    ```

### Para Windows
1. **Java Development Kit (JDK 21):**
    - Baixe o instalador do OpenJDK 21 ou Oracle JDK 21 e execute-o.
    - Adicione a variável de ambiente JAVA_HOME apontando para a pasta de instalação (ex: C:\Program Files\Java\jdk-21).

2. **Apache Maven (versão 3.9.x):**
    - Baixe o arquivo .zip no site oficial do Maven (maven.apache.org).

    - Extraia na raiz do seu disco (ex: C:\apache-maven-3.9.9).
    
    - Adicione o caminho C:\apache-maven-3.9.9\bin à variável de ambiente Path do Windows.


## Download e Configuração do AEM Author (SDK)
1. Download do SDK

    - Acesse o portal da Adobe Software Distribution com o seu Adobe ID (sua organização deve estar provisionada para o AEM as a Cloud Service).
    
    - Localize e faça o download do zip do AEM SDK mais recente.
    
    - Descompacte o arquivo baixado. Você encontrará o arquivo Quickstart Jar (ex: aem-sdk-quickstart-xxxx.xxx.jar).

    - Crie uma pasta "author" e mova o .jar para ela com os segintes comandos:
    
    ### Linux (Ubuntu)
        
    ```bash
    mkdir -p /aem-sdk/author
    cp caminho\para\aem-sdk-quickstart-xxxx.xxx.jar /aem-sdk/author/aem-author-p4502.jar
    ```
    
    ### Windows
    ```bash
    mkdir -p C:\aem-sdk\author
    cp caminho\para\aem-sdk-quickstart-xxxx.xxx.jar C:\aem-sdk\author\aem-author-p4502.jar
    ```


2. Inicialização do AEM Author

    - Abra o terminal/prompt de comando na pasta author, e execute o comando
    ```bash
    java -jar aem-author-p4502.jar
    ```

O navegador abrirá automaticamente em localhost:4502 após alguns minutos. Login padrão: admin/admin (talvez seja necessário definir o login durante a primeira execução).

## Instalação e Execução (AEM-GUIDES-WKND)

Para testar ou instalar este projeto em uma instância local do AEM (Author rodando na porta `4502`), siga os passos abaixo:

### 1. Clonar o repositório
```bash
git clone [https://github.com/adobe/aem-guides-wknd.git](https://github.com/adobe/aem-guides-wknd.git)
cd aem-guides-wknd
```

### 2. Compilar e Realizar o Deploy (Build)

No diretório raiz do projeto clonado, execute o comando de deploy:

```bash
mvn clean install -PautoInstallSinglePackage
```

Este comando compilará os módulos Java (core), front-end (ui.frontend), pacotes de estrutura (ui.apps) e enviará o pacote consolidador (all) diretamente para a sua instância local na porta 4502.
