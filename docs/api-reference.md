# Referência Completa da API REST - PicPay

## Visão Geral

A API expõe endpoints para gerenciamento de contas de Usuários e Lojistas, criação e consulta de Carteiras Digitais, e transferência de valores entre contas.

---

## Sumário de Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/usuario` | Cadastrar novo usuário do tipo `USUARIO` (Pessoa Física) |
| `POST` | `/lojista` | Cadastrar novo usuário do tipo `LOJISTA` (Pessoa Jurídica) |
| `POST` | `/carteira` | Criar carteira digital para um usuário/lojista existente |
| `GET` | `/carteira/{id}` | Consultar dados e saldo de uma carteira específica |
| `POST` | `/transfer` | Realizar transferência de valores entre duas carteiras |

---

## Detalhamento dos Endpoints

### 1. Criar Usuário (Pessoa Física)

* **Método**: `POST`
* **URL**: `/usuario`
* **Headers**: `Content-Type: application/json`

#### Corpo da Requisição (Request Body)
```json
{
  "nome": "João Lima",
  "cpf": "546.471.429-49",
  "email": "joao.lima@exemplo.com",
  "senha": "senhaSegura123"
}
```

#### Regras de Validação
* `nome`: Não pode ser vazio. Max 100 caracteres.
* `cpf`: Deve ser um CPF válido (formato com pontuação/máscara).
* `email`: Formato de e-mail válido e único no banco.
* `senha`: Obrigatório (máximo 140 caracteres).

#### Resposta de Sucesso (`201 Created`)
```json
{
  "usuario_id": "550e8400-e29b-41d4-a716-446655440000",
  "nome": "João Lima",
  "tipo": "USUARIO",
  "documento": "546.471.429-49",
  "email": "joao.lima@exemplo.com"
}
```

---

### 2. Criar Lojista (Pessoa Jurídica)

* **Método**: `POST`
* **URL**: `/lojista`
* **Headers**: `Content-Type: application/json`

#### Corpo da Requisição (Request Body)
```json
{
  "nome": "Mercado Central LTDA",
  "cnpj": "66.838.061/0001-30",
  "email": "contato@mercadocentral.com",
  "senha": "senhaSegura123"
}
```

#### Regras de Validação
* `cnpj`: Deve ser um CNPJ válido com máscara.
* `documento` e `email`: Devem ser únicos no sistema.

#### Resposta de Sucesso (`201 Created`)
```json
{
  "usuario_id": "7d0c10e7-972a-4774-8b51-7a0335de610a",
  "nome": "Mercado Central LTDA",
  "tipo": "LOJISTA",
  "documento": "66.838.061/0001-30",
  "email": "contato@mercadocentral.com"
}
```

---

### 3. Criar Carteira Digital

* **Método**: `POST`
* **URL**: `/carteira`
* **Headers**: `Content-Type: application/json`

#### Corpo da Requisição (Request Body)
```json
{
  "user_id": "550e8400-e29b-41d4-a716-446655440000"
}
```

#### Resposta de Sucesso (`201 Created`)
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": {
    "usuario_id": "550e8400-e29b-41d4-a716-446655440000",
    "nome": "João Lima",
    "tipo": "USUARIO",
    "documento": "546.471.429-49",
    "email": "joao.lima@exemplo.com",
    "senha": "senhaSegura123"
  },
  "balance": 0.00
}
```

---

### 4. Consultar Carteira por ID

* **Método**: `GET`
* **URL**: `/carteira/{id}`

#### Parâmetros de Path
* `id` (UUID): ID da carteira a ser pesquisada.

#### Resposta de Sucesso (`201 Created`)
```json
{
  "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "user_id": {
    "usuario_id": "550e8400-e29b-41d4-a716-446655440000",
    "nome": "João Lima",
    "tipo": "USUARIO",
    "documento": "546.471.429-49",
    "email": "joao.lima@exemplo.com"
  },
  "balance": 150.00
}
```

---

### 5. Efetuar Transferência

* **Método**: `POST`
* **URL**: `/transfer`
* **Headers**: `Content-Type: application/json`

#### Corpo da Requisição (Request Body)
```json
{
  "amount": 150.00,
  "payer": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
  "payee": "7d0c10e7-972a-4774-8b51-7a0335de610a"
}
```

#### Regras de Negócio
1. O pagador (`payer`) deve pertencer a uma conta do tipo `USUARIO`. Lojistas não podem efetuar transferências, apenas receber.
2. O saldo disponível na carteira do `payer` deve ser maior ou igual ao `amount`.
3. O serviço autorizador externo (`https://util.devi.tools/api/v2/authorize`) deve aprovar a transação.
4. A transação ocorre dentro de uma transação `@Transactional`. Qualquer falha desfaz todas as alterações (rollback).

#### Resposta de Sucesso (`201 Created`)
```json
{
  "id": "e02e3bf3-f748-434b-a72c-86034e7b8b7a",
  "amount": 150.00,
  "payer": {
    "id": "f81d4fae-7dec-11d0-a765-00a0c91e6bf6",
    "balance": 0.00
  },
  "payee": {
    "id": "7d0c10e7-972a-4774-8b51-7a0335de610a",
    "balance": 150.00
  },
  "created_at": "2026-09-13T16:20:00.123456Z"
}
```

---

## Tratamento Global de Erros

A API padronizou as respostas de erro através do `GlobalExceptionHandler`.

### Formato de Erro Padrão (`ErrorResponse`)
```json
{
  "timestamp": "2026-09-13T16:20:00.123-03:00",
  "status": 400,
  "error": "BusinessException",
  "message": "Saldo insuficiente",
  "path": "/transfer"
}
```

### Principais Códigos de Status HTTP Retornados

| Código | Nome | Motivo |
|---|---|---|
| `400` | Bad Request | Regra de negócio violada (`BusinessException`), saldo insuficiente ou recusa do autorizador |
| `404` | Not Found | Usuário (`UserNotFoundException`) ou Carteira (`CarteiraNotFoundException`) não encontrados |
| `409` | Conflict | Documento (CPF/CNPJ) ou E-mail já cadastrados no banco de dados (`DataIntegrityViolationException`) |
| `422` | Unprocessable Entity | Erro de validação de argumentos nos DTOs (`MethodArgumentNotValidException` / `IllegalArgumentException`) |
