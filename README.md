# Product-ms

Leia a versão em inglês [aqui](doc/README_EN.md).

🎯 **Descrição**:  
O **Product-ms** é um microserviço para gerenciamento de produtos. Ele permite criar, pesquisar, inativar e atualizar produtos. O foco principal deste projeto é o aprendizado de técnicas de testes unitários com JUnit5, validação de campos em DTOs e tratamento de erros de validação.

🔧 **Funcionalidades**:
- CRUD completo para produtos.
- Produtos são logicamente excluídos: um campo `available` é usado para definir se um produto está ativo (`true`) ou inativo (`false`).

## Tecnologias Usadas

- **Java** 21
- **Spring Boot** 3.3.2
- **Maven** 3.9.7

## Dependências

- **SpringDoc Swagger OpenAPI** 2.6.0: Para documentação da API
- **H2Database**: Para ambiente de teste
- **PostgreSQL**: Para ambiente em nuvem no Railway
- **Lombok**: Para eliminação de código boilerplate
- **ModelMapper** 3.2.1: Para facilitar a transferência de dados entre a DTO e a Entidade
- **Fixture-factory** 3.1.0: Para criação de fixtures ou templates para dados fictícios e facilitar a criação dos testes
- **Spring Validation**: Para validação dos campos da DTO
- **Spring Test**: Para testes unitários e de integração

## Pré-requisitos

Para rodar o **Product-ms**, você precisa de:
- Java 17+
- Spring Boot 3.x.x
- IDE (como IntelliJ, Eclipse, etc.)
- Gerenciador de dependências Maven
- Cliente HTTP (como HTTPie, Insomnia, etc.) ou navegador para testar os endpoints (a API está documentada com Swagger)

## Instalação e Configuração

1. Clone o repositório:
    ```bash
    git clone https://github.com/seu-usuario/product-ms.git
    ```

2. Abra o projeto em uma IDE (como IntelliJ ou Eclipse) e faça o reload do Maven para baixar as dependências (geralmente disponível através de um botão "Play" na interface da IDE).

3. Alternativamente, abra o terminal, navegue até a pasta do projeto clonado e execute:
    ```bash
    mvn spring-boot:run
    ```

   Isso irá baixar as dependências e iniciar o servidor Tomcat.

## Uso

Com o servidor em execução, abra o navegador e acesse:
[http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

Na interface do Swagger, você encontrará todos os endpoints listados com os detalhes das requisições e respostas.

## Endpoints

- **POST** `/products`  
  Cria um novo produto.  
  **Resposta**: 201 Created

- **GET** `/products`  
  Recupera todos os produtos.  
  **Resposta**: 200 OK

- **GET** `/products/{id}`  
  Recupera um produto pelo seu ID.  
  **Resposta**: 200 OK

- **POST** `/products/{id}`  
  Atualiza um produto especificando o ID.  
  **Resposta**: 200 OK

- **DELETE** `/products/{id}`  
  Inativa um produto (em vez de excluir fisicamente).  
  **Resposta**: 200 OK

## Testes

Para rodar os testes, certifique-se de que o ambiente está instalado e configurado com o projeto. Navegue até o caminho `src/test/java/com/github/alex3g/product_ms` e execute a classe `ProductMsApplicationTests`.

## Contribuição

Este projeto é apenas para fins de aprendizado. No entanto, se você deseja contribuir ou fazer melhorias, sinta-se à vontade para entrar em contato.

## Contato

🔗 [LinkedIn](https://www.linkedin.com/in/alex--gaspar/)
