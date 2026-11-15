# Carteira Digital (Wallet API) - Testes Automatizados I

API REST de Carteira Digital desenvolvida em **Java 21** e **Spring Boot**, com foco integral na aplicação de **TDD (Test-Driven Development)** e uma suíte completa de testes automatizados (Unitários, Integração e Interface).

---

## Tecnologias e Ferramentas

- **Java 21** & **Spring Boot 4**
- **Spring Data JPA** & Banco em Memória **H2**
- **JUnit 5** & **Mockito** (Testes Unitários da camada de serviço)
- **Spring MockMvc** (Testes de Integração da camada Controller)
- **Selenium WebDriver** (Testes E2E de Interface)
- **JaCoCo** (Métricas de Cobertura de Código com meta > 80%)

---

## Arquitetura da Aplicação

A aplicação segue a divisão clássica em 3 camadas:
1. **Controller (`ContaController`)**: Endpoints REST para consulta de saldo, depósito e transferência.
2. **Service (`ContaService`)**: Regras de negócio e validações:
   - Consulta de saldo por ID.
   - Depósito com bloqueio de valores $\le 0$ (`IllegalArgumentException`).
   - Transferência com validação de saldo de origem (`SaldoInsuficienteException`).
3. **Repository (`ContaRepository`)**: Persistência de dados usando Spring Data JPA.

---

## Suíte de Testes Automatizados

### 1. Testes Unitários (`ContaServiceTest`)
- Testa as regras de negócio isoladas com `@Mock` e `@InjectMocks`.
- Valida cenários de sucesso e lançamentos de exceção para valores inválidos e saldo insuficiente.

### 2. Testes de Integração (`ContaControllerTest`)
- Valida os endpoints HTTP com `@WebMvcTest` e `MockMvc`.
- Testa retornos `200 OK` para operações bem-sucedidas e `400 Bad Request` para falhas/validações.

### 3. Testes de Interface / E2E (`ContaInterfaceSeleniumTest`)
- Simula o fluxo completo de um usuário interagindo com a página HTML (`deposito.html`).
- Localização de elementos via `By.id` e sincronização inteligente via `WebDriverWait` (espera explícita).

---

## Relatório de Cobertura (JaCoCo)

O projeto possui cobertura acima de **80%** nas camadas de Service e Controller.

Para gerar e visualizar o relatório:
```bash
./mvnw clean test
```
O relatório HTML estará disponível em:
`target/site/jacoco/index.html`

---

## Histórico TDD no Git

O repositório foi construído seguindo estritamente a disciplina do **TDD (Test-Driven Development)**:
- `test: RED` $\to$ Adição da asserção ou validação que falha.
- `feat: GREEN` $\to$ Implementação do código mínimo necessário para o teste passar.
- `refactor: REFACTOR` $\to$ Limpeza e melhoria estrutural do código preservando os testes verdes.