## Tecnologias Utilizadas

- Java
- Spring Framework
- Docker

<p align="center">
  <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" width="80" height="80"/>
  <img src="https://www.vectorlogo.zone/logos/docker/docker-icon.svg" alt="Docker" width="80" height="80"/>
</p>

# Certificado API

Esta aplicação é uma API para emissão de certificados usando Java e Spring, e é executada em um contêiner Docker.

## Funcionalidades

A API fornece as seguintes funcionalidades:
- Ranking dos 10 melhores estudantes.
- Submissão de respostas de certificação.
- Listagem de questões por tecnologia.
- Verificação de certificações dos estudantes.

## Endpoints

### Ranking dos 10 melhores estudantes

`GET /ranking/top10`

Este endpoint retorna o ranking dos 10 melhores estudantes.

### Submissão de Respostas de Certificação

`POST /students/certification/answer`

Este endpoint permite que os estudantes submetam suas respostas para certificação.

### Listagem de Questões por Tecnologia

`GET /questions/technology/{tech}`

Este endpoint retorna uma lista de questões filtradas por uma tecnologia específica. O parâmetro `{tech}` deve ser substituído pela tecnologia desejada (por exemplo, `java`, `python`).

### Verificação de Certificações dos Estudantes

`GET /students/verifyIfHasCertification`

Este endpoint verifica se um estudante possui certificação.

## Configuração e Execução

### Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

- [Java 11+](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
