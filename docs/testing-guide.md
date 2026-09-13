# Guia de Testes - PicPay

## Visão Geral

O projeto PicPay possui uma suíte de testes unitários e de integração utilizando **JUnit 5**, **Mockito** e banco de dados em memória **H2** para testes de persistência.

---

## Estrutura da Suíte de Testes (`src/test/java/bank/picpay`)

```text
src/test/java/bank/picpay/
├── PicpayApplicationTests.java        # Teste de carregamento do contexto do Spring Boot
├── auth/
│   └── AuthorizeApiTest.java          # Testes unitários do cliente de autorização externa
├── controller/
│   ├── CarteiraControllerTest.java    # Testes dos endpoints /carteira (WebMvcTest/MockMvc)
│   ├── TransacaoControllerTest.java   # Testes dos endpoints /transfer (WebMvcTest/MockMvc)
│   └── UsuarioControllerTest.java     # Testes dos endpoints /usuario e /lojista
├── models/carteira/
│   └── CarteiraEntityTest.java        # Testes de unidade dos métodos debit() e credit()
└── service/
    └── TransacaoServiceTest.java      # Testes de regras de negócio de transferências
```

---

## Execução dos Testes

### Executar todos os testes via Maven Wrapper
```bash
./mvnw test
```

### Executar uma classe de teste específica
```bash
./mvnw test -Dtest=TransacaoServiceTest
```

### Executar relatório de cobertura (se configurado)
```bash
./mvnw verify
```

---

## Principais Cenários Testados

### 1. Testes de Regra de Negócio (`TransacaoServiceTest`)
* Transferência bem-sucedida entre dois usuários do tipo `USUARIO`.
* Lançamento de `BusinessException` se o `payer` for do tipo `LOJISTA`.
* Lançamento de `BusinessException` se o saldo do `payer` for inferior ao valor solicitado.
* Lançamento de `BusinessException` se a API autorizadora externa recusar a transação.
* Disparo de evento de notificação e e-mail no RabbitMQ via `NotificationProducer`.

### 2. Testes de Entidade (`CarteiraEntityTest`)
* Subtração correta de saldo ao debitar.
* Adição de saldo ao creditar.
* Exceção ao tentar debitar valor maior que o saldo.
* Exceção ao tentar creditar valor negativo.

### 3. Testes dos Controladores (`*ControllerTest`)
* Validação de payload DTO com anotações `@Valid` (`CPFDto`, `CNPJDto`, `TransacaoDTO`).
* Retorno de status `201 Created` e cabeçalhos HTTP corretos.
* Resposta de erro estruturada com `GlobalExceptionHandler` em casos de dados inválidos ou não encontrados.
