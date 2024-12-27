# Sistema de Consulta

## Descrição
O Sistema de Consulta é um projeto web desenvolvido para facilitar a consulta de informações em um banco de dados. Ele oferece uma interface amigável para que os usuários possam realizar buscas e visualizar resultados de maneira eficiente.

## Contexto
Este projeto é um trabalho avaliativo da disciplina Laboratório Web realizado na Universidade Estadual da Bahia.

## Estrutura do Projeto
O projeto utiliza a arquitetura MVC (Model-View-Controller) para organizar o código de forma modular e facilitar a manutenção. A estrutura do projeto é dividida em Frontend e Backend:

### Frontend
O frontend do projeto "angular-marcacao-consulta" é desenvolvido utilizando Angular. A versão do Angular utilizada é a 18.2. A estrutura do frontend é a seguinte:

- **Componentes**: Contém os componentes da interface do usuário.
- **Serviços**: Contém os serviços que fazem a comunicação com o backend.
- **Módulos**: Contém os módulos que organizam os componentes e serviços.

#### Instalação
Para instalar e executar o projeto localmente, siga os passos abaixo:

1. Clone o repositório:
    ```bash
    git clone https://github.com/EduardoIkeda/SistemaConsulta.git
    ```
2. Navegue até o diretório do projeto:
    ```bash
    cd SistemaConsulta/angular-marcacao-consulta
    ```
3. Instale as dependências:
    ```bash
    npm install
    ```
4. Inicie o servidor:
    ```bash
    npm start
    ```

### Backend
O backend do projeto é desenvolvido utilizando Spring Boot. A estrutura do backend é a seguinte:

- **Model**: Contém as classes que representam os dados e a lógica de negócios.
- **Controller**: Contém as classes que controlam o fluxo de dados entre o frontend e o backend.
- **Service**: Contém as classes que implementam a lógica de negócios.
- **Repository**: Contém as interfaces que fazem a comunicação com o banco de dados.
- **DTO (Data Transfer Object)**: Contém as classes que são utilizadas para transferir dados entre o frontend e o backend de forma eficiente e segura, sendo separado entre DTOs de response e request.

#### Instalação
Para instalar e executar o backend localmente, siga os passos abaixo:

1. Clone o repositório:
    ```bash
    git clone https://github.com/EduardoIkeda/SistemaConsulta.git
    ```
2. Navegue até o diretório do backend:
    ```bash
    cd SistemaConsulta/spring-marcacao-consulta
    ```
3. Instale as dependências do Maven:
    ```bash
    mvn install
    ```
4. Inicie o servidor Spring Boot:
    ```bash
    mvn spring-boot:run
    ```

### Banco de Dados
O projeto utiliza o banco de dados H2 para armazenamento de dados durante o desenvolvimento e testes. O H2 é um banco de dados em memória que facilita a configuração e execução do projeto sem a necessidade de instalar um banco de dados externo.

#### Configuração do Banco de Dados
O banco de dados H2 é configurado automaticamente pelo Spring Boot. As configurações padrão estão localizadas no arquivo `application.properties` do backend:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

#### Acessando o Console do H2
Para acessar o console do H2 e visualizar os dados armazenados, siga os passos abaixo:

1. Inicie o servidor Spring Boot conforme descrito na seção de instalação do backend.
2. Abra o navegador e acesse `http://localhost:8080/h2-console`.
3. Utilize as seguintes credenciais para login:
    - **JDBC URL**: `jdbc:h2:mem:testdb`
    - **User Name**: `sa`
    - **Password**: `password`

## Uso
Após iniciar o servidor do angular e do spring, abra o navegador e acesse `http://localhost:4200` para utilizar o Sistema de Consulta.
