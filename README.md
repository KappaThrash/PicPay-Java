# picpay
Repo original do desafio: https://github.com/PicPay/picpay-desafio-backend<div align="center"> ![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff)](#) 	[![Postgres](https://img.shields.io/badge/Postgres-%23316192.svg?logo=postgresql&logoColor=white)](#) [![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff)](#) [![AWS](https://custom-icon-badges.demolab.com/badge/AWS-%23FF9900.svg?logo=aws&logoColor=white)](#)
[![Terraform](https://img.shields.io/badge/Terraform-844FBA?logo=terraform&logoColor=fff)](#) [![Bash](https://img.shields.io/badge/Bash-4EAA25?logo=gnubash&logoColor=fff)](#)
![RabbitMQ](https://img.shields.io/badge/Rabbitmq-FF6600?logo=rabbitmq&logoColor=white) [![JUnit5](https://img.shields.io/badge/JUnit5-C21325?logo=junit5&logoColor=fff)](#) 	[![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2088FF?logo=github-actions&logoColor=white)](#)
</div>


## DOCUMENTAÇÃO COMPLETA

A documentação detalhada do projeto está disponível na pasta [`docs/`](docs/):

* [ Arquitetura do Sistema](docs/architecture.md): Diagrama Mermaid, fluxo de transação e descrição de camadas.
* [ Referência da API REST](docs/api-reference.md): Endpoints, payloads JSON, contratos de validação e tratamento de erros.
* [ Modelo de Banco de Dados](docs/database-schema.md): Diagrama ERD, tabelas (`usuarios`, `carteira`, `transacoes`) e migrações.
* [ Infraestrutura e Deployment](docs/infrastructure-and-deployment.md): Configuração Docker Compose e automação de deploy na AWS EC2 via Terraform.
* [ Guia de Testes](docs/testing-guide.md): Estrutura de testes unitários e de integração com JUnit 5 e Mockito.

---

## DOCKER

Subir o containers:
```bash
#PostgreSQL e a Network
docker compose up -d

#Spring (Imagem no docker hub)
docker run -d --name picpay-app --network picpay-network --env-file .env -p 80:8080 danielkappa/picpay
```

## ENDPOINTS

## Usuario

### `POST /usuario` - Criar conta Usuario

### Validação
- CPF ou CNPJ devem ser validos, serão recusados caso não sejam
- Email formatado certamente
- CPF/CNPJ e Email são unicos no sistema

#### Exemplo:
```json
{
  "nome": "João Lima",
  "tipo": "USUARIO",
  "cpf": "546.471.429-49",
  "email": "teste@exemplo.com",
  "senha": "senha123"
}
```

### `POST /lojista` - Criar conta Lojista

#### Exemplo:
```json
{
  "nome": "João Lima",
  "tipo": "LOJISTA",
  "cpnpj": "66.838.061/0001-30",
  "email": "teste@exemplo.com",
  "senha": "senha123"
}
```

## Carteira

### `POST /carteira` - Criar Carteira apartir de um Usuario existente

#### Exemplo:
```json
{
  "user_id": "550e8400-e29b-41d4-a716-446655440000"
}
```
Retorno:
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "balance": "100"
}
```

### `GET /carteira/{UUID}` - Retorna Carteira existente

#### Exemplo:
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": "550e8400-e29b-41d4-a716-446655440000",
  "balance": "100"
}
```

## Transação

### `POST /transfer` - Efetuar transação apartir de duas carteiras existentes (Rollback em caso de falhas)

### Regras de negocio:
- Carteira payer deve pertencer a Usuario do tipo USUARIO, LOJISTAS não podem fazer transferencias, apenas receber
- Autorizador externo (Mock) tem que autorizar a transferencia
- Saldo deve ser suficiente

#### Exemplo:
```json
{
  "amount": "150",
  "payer": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "payee": "7d0c10e7-972a-4774-8b51-7a0335de610a"
}
```
Retorno:
```json
{
  "id": "e02e3bf3-f748-434b-a72c-86034e7b8b7a",
  "amount": "200",
  "payer": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "payee": "7d0c10e7-972a-4774-8b51-7a0335de610a"
  "created_at": "2026-04-27T23:09:38.894601324Z"
}
