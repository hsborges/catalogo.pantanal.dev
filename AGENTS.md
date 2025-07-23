# Cinema (Catalogo) <pantanal.dev>

Cinema é uma aplicação com fins didáticos para ensino de criação de web services em Java usando o _framework_ Spring. Esse serviço foca na criação de um microsserviço para gestão do catálogo de filmes.


## Build & Commands

- Inicia servidor: `./mvnw spring-boot:run`

### Development Environment

- Server: http://localhost:8080

## Code Style

- Use 4 espaços para indentação (sem tabulações)
- Limite as linhas a 100–120 caracteres
- Coloque chaves de abertura em novas linhas
- Evite imports com curingas; use imports explícitos
- Agrupe os imports e separe com linhas em branco
- Adicione espaço após if, for, while, etc.
- Não use espaço entre o nome do método e os parênteses
- Use camelCase para variáveis e métodos
- Use PascalCase para nomes de classes
- Use UPPER_CASE para constantes
- Mantenha uma instrução por linha
- Use uma linha em branco entre os métodos
- Mantenha anotações na mesma linha quando forem curtas
- Marque variáveis e parâmetros como final se não forem reatribuídos
- Mantenha os métodos do controller curtos e legíveis
- Use nomes descritivos para métodos e variáveis
- Evite comentários desnecessários; prefira código autoexplicativo

## Testing

// TODO

## Architecture

- Estruture os pacotes por funcionalidades
- Para cada funcionalidade, crie controllers, services, repositories, dtos e entity
- Mantenha as dependências internas encapsuladas dentro da feature, evitando acoplamento cruzado
- Use interfaces públicas para comunicação entre features (ex: portas da arquitetura hexagonal)
- Crie um pacote common ou shared para utilitários, exceções, mapeadores e objetos compartilhados
- Cada feature deve ser responsável por expor sua própria API REST (próprios controllers)
- Aplique @Transactional nos serviços onde necessário, nunca nos controllers
- Use ResponseEntity<T> nos controllers para padronizar retornos e status HTTP
- Nomeie os endpoints com base no contexto da funcionalidade, usando kebab-case:
Ex: /api/atores/{id}
- Separe as configurações em application.yml com perfis (@Profile) para ambientes distintos
- Use construtores para injeção de dependência, facilitando a testabilidade
- Adote mapeadores internos (ex: MapStruct ou classes *Mapper) para converter entre entidades e DTOs
- Use contratos de interface entre camadas para manter baixo acoplamento
- Use lombook para gerar construtores e getters/setters padrões

## Security

// TODO

## Git Workflow

- Use o padrão GitHub Flow com branches curtas a partir da main
- Nomeie branches com prefixo semântico, ex: feat/, fix/, chore/
- Escreva commits (semânticos) no formato: <tipo>(escopo): descrição
- Realize merge via squash para manter histórico limpo
- Proíba commits diretos na main com regras de proteção
- Execute testes e validações em cada PR via GitHub Actions
- Rebase a branch com main antes de finalizar o PR

## Configuration

Ao atualizar variáveis de ambiente, atualize em todos os arquivos:

1. Preferencias em `src/main/resources/application.properties`
2. Documentação em README.md

Todas as configurações devem possuir nomes consistentes e documentadas.
