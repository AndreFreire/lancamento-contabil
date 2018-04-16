# Projeto de estudo Spring Boot 2+

## Pré requisitos:

* Maven 3+
* Java 8+

## Compilação, testes e empacotamento:

`mvn clean verify package`

Será gerado um arquivo `jar` na pasta `target`

## Execução:

`java -jar target/lancamento-contabil-0.0.1-SNAPSHOT.jar`

** A aplicação utiliza um banco de dados `mongodb` em memória

## Utilidades:

Coleção do postman com os requests do projeto: [postman](lancamentos_contabeis.postman_collection.json)

## Arquitetura utilizada:

[Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)