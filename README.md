# AluMind
// descrição
  ### Ferramentas
  - Java (v9.^)
  - Maven (v3.^)
  - Docker
  ### Dependências e Bibliotecas
  -  Spring Boot (v3.3.5)
  -  Spring Data JPA (v3.3.5)
  -  Spring AI(v1.0.0-M3)
  - OpenAI integration
  - Swagger                                  
  ### Compilação e execução
  ```bash
  $ cd 
  ```
No Arquivo application.properties dentro do diretório resources, adicione as informações para conexão com seu banco de dados.
- Exemplo:
- - spring.datasource.url=jdbc:postgresql://localhost:5432/alumind_db
- - spring.datasource.username=postgres
- - spring.datasource.password=postgres

## Banco de Dados :bank:
### Ferramenta
- PostgresSQL (v19.1)
- Flyway Core
### Migrações
Após criar seu banco de dados localmente com as informações de usuário e senha execute o projeto. O arquivo de migração será executado automaticamente para criar a tabela "feedback" do banco, além de inserir dados fictícios de feedback. Caso deseje, você pode remover essa funcionalidade no arquivo application.properties atribuindo false na seguinte configuração:
- spring.flyway.enabled

  



