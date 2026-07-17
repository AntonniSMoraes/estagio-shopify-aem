# Projeto AEM

Este repositório contém a resolução dos **Desafios Relacionados ao AEM** do Programa de Estágio WebJump. O projeto utiliza como base o template de melhores práticas da Adobe para aplicações AEM.

## Conteúdo
1. Solução para o desafio 5.1 - Ambiente + primeiro deploy do WKND
2. Solução para o desafio 5.2 - Componente do zero: Cartão de Perfil

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
