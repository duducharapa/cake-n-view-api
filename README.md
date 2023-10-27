# Cake 'n View API

O projeto Cake n' View é uma aplicação destinada a **avaliar alguns tipos de bolos** e ajudar pessoas a **descobrirem novas variedades**.

Este repositório possui o código relacionado à API. Caso deseje conhecer mais sobre a interface que a consome, você pode conferir [nesse repositório](https://github.com/duducharapa/cake-n-view-web).

- [Tecnologias utilizadas](#tecnologias-utilizadas)
- [Instalação](#instalação)
- [Considerações](#considerações)

<br>

## Tecnologias utilizadas
A linguagem utilizada é **Java versão 17** juntamente com o framework **Spring Boot na versão 3**.

Além disso, algumas outras bibliotecas comuns no ecossistema Spring foram utilizadas, sendo algumas:
- [Spring Security](https://spring.io/projects/spring-security) para autenticação e autorização
- [H2 database](https://h2database.com/html/main.html) para persistência de dados
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) para facilitar a comunicação com banco de dados e modelagem de entidades

<br>

## Instalação
O projeto possui integração com o **Docker Compose**. Para executar o ambiente, basta executar esse comando na raiz do diretório.

> docker-compose up -d

E assim, após a aplicação inicializar, a aplicação poderá ser acessada pelo link: http://localhost:8080

### Sem Docker Compose?
Caso não possui o Docker Compose instalado, você poderá também executar a aplicação utilizando o seguinte comando na raiz do diretório.

> ./mvnw spring-boot:run

<br>

## Considerações
- Enquanto não está devidamente documentado o conteúdo sobre cada rota, o arquivo ```requests.http``` possui alguns exemplos de requisições utilizadas na aplicação.
- Logo mais será adicionado um banco de dados mais convencional no lugar do H2.
