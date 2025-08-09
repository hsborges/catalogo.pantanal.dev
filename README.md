# Cinema (Catálogo) <pantanal.dev>

![CI](https://github.com/hsborges/catalogo.pantanal.dev/actions/workflows/ci.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-green)
![Maven](https://img.shields.io/badge/Maven-3.6+-blue)

Cinema é uma aplicação com fins didáticos para ensino de criação de web services em Java usando o _framework_ Spring. Esse serviço foca na criação de um microsserviço para gestão do catálogo de filmes.

## 📋 Pré-requisitos

- **Java 17** ou superior
- **Maven 3.6+** (ou use o wrapper incluído)
- **Git** para controle de versão

## 🚀 Como executar

### Usando Maven Wrapper (recomendado)

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd catalogo.pantanal.dev

# Execute a aplicação
./mvnw spring-boot:run
```


## 📋 Comandos úteis

```bash
# Limpar e compilar
./mvnw clean compile

# Gerar JAR
./mvnw package

# Executar com perfil específico
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

./mvnw test -Dtest=NomeDoTeste

# Executar todos os testes
./mvnw test

# Executar testes com relatório de cobertura
./mvnw test jacoco:report
```

## ⚙️ Configuration

### Configuração de ambiente

As configurações são centralizadas em `application.properties`. Para diferentes ambientes, use perfis do Spring:

- `application-dev.properties` - Desenvolvimento
- `application-prod.properties` - Produção
- `application-test.properties` - Testes

Ao atualizar variáveis de ambiente, atualize em todos os arquivos:

1. Preferências em `src/main/resources/application.properties`
2. Documentação em README.md

Todas as configurações devem possuir nomes consistentes e documentadas.

### Principais configurações

| Propriedade | Descrição | Valor padrão |
|-------------|-----------|--------------|
| `spring.application.name` | Nome da aplicação | `catalogo-cinema` |
| `server.port` | Porta do servidor | `8080` |

## 📄 Licença

Este projeto está sob a licença [MIT](LICENSE).
