# Hotela

Bem-vindo ao projeto do Sistema de Hotéis! Esta é uma API RESTful construída com as tecnologias mais modernas para gerenciar as operações de hotéis de forma eficiente.

## ✨ Funcionalidades

* **Gerenciamento de Hóspedes:** Cadastro, consulta e atualização de informações de hóspedes.
* **Controle de Reservas:** Criação, cancelamento e visualização de reservas.
* **Gestão de Quartos:** Verificação de disponibilidade e administração dos quartos.

---

## 🛠️ Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes tecnologias:

* **Backend:** [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot)
* **Comunicação:** API REST
* **Containerização:** [Docker](https://www.docker.com/)
* **Testes:** [JUnit 5](https://junit.org/junit5/) & [Mockito](https://site.mockito.org/)
* **Build Tool:** [Gradle](https://gradle.org/)

---

## 📋 Pré-requisitos

Antes de começar, certifique-se de que você tem as seguintes ferramentas instaladas em seu ambiente:

* [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
* [Docker](https://www.docker.com/products/docker-desktop/) e Docker Compose

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para ter a aplicação rodando em sua máquina.

### 1. Clone o Repositório

```bash
git clone https://github.com/luk3rr/HOTELA_BACK.git
cd HOTELA_BACK
```

### 2. Escolha como Executar

Você pode rodar o projeto de duas maneiras: utilizando Docker (recomendado para simplicidade) ou diretamente com o Gradle.

#### Opção A: Rodando com Docker (Recomendado) 🐳

Este é o método mais simples e não exige que você instale o Java ou o Gradle em sua máquina, apenas o Docker.

**Antes de iniciar o container, faça o build da aplicação:**

```bash
./gradlew build
```

**Inicie o container:**

```bash
docker-compose up -d
```

**Parar o container:**

```bash
docker-compose down
```

#### Opção B: Rodando com Gradle 🐘

Certifique-se de ter o JDK 21 configurado corretamente em seu sistema, além do banco de dados PostgreSQL instalado e em execução.

**Execute o projeto:**

No Linux ou macOS:

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

A aplicação estará disponível em [http://localhost:8080](http://localhost:8080).

---

### 🔧 Rodando aplicação Spring

Para rodar a aplicação localmente:

1. Configure o banco de dados em `src/main/resources/application.properties`.
2. Execute:

    No Linux:
    ```bash
    ./gradlew bootRun
    ```
---

## 🧪 Executando os Testes

Para garantir a qualidade e o bom funcionamento do código, execute os testes unitários com o seguinte comando:

No Linux ou macOS:

```bash
./gradlew test
```

No Windows:

```bash
gradlew.bat test
```

---

## 💅 Padrões e Qualidade de Código

Para manter o código limpo e padronizado, utilizamos o plugin Spotless.

**Verificar formatação:**

Antes de enviar suas alterações (commit/push), verifique se o código está de acordo com os padrões.

```bash
./gradlew spotlessCheck
```

**Aplicar formatação:**

Se a verificação falhar, aplique a formatação correta automaticamente com o comando:

```bash
./gradlew spotlessApply
```

---

## 📝 Documentação da API

A documentação completa de todos os endpoints está disponível via Swagger. Com a aplicação em execução, acesse o seguinte link no seu navegador:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Lá você poderá visualizar e interagir com todos os recursos da API em tempo real.