# AluMind
Este projeto proporciona uma api para avaliação de feedbacks com base nos sentimentos transmitidos no escopo das mensagens recebidas. O principal objetivo consiste em fazer uso de LLM'S para classificar os feedbacks recebidos como positivos ou negativos e a partir disso avaliar a mensagem e gerar resultados.
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
  ### Instruções
  A aplicação realiza uma integração com as ferramentas disponibilizadas pela API do OpenAI Chat, sendo assim caso ainda não possua uma conta no serviço da OpenAI, será necessário criá-la 
  https://auth.openai.com/authorize?issuer=auth0.openai.com&client_id=DRivsnm2Mu42T3KOpqdtwB3NYviHYzwD&audience=https%3A%2F%2Fapi.openai.com%2Fv1&redirect_uri=https%3A%2F%2Fplatform.openai.com%2Fauth%2Fcallback&device_id=87361688-5e45-467b-a934-ef8d01e44665&screen_hint=signup&max_age=0&scope=openid%20profile%20email%20offline_access&response_type=code&response_mode=query&state=NkZtZzc0RmlWenpOZWZsMHQySmNWSVlBbkFvMDBVUHhZajJxbVZTUnVjUA%3D%3D&nonce=eGtEWDQxc3JPeXF2enB%2BLmhhUlFRcXhHblkyUHZiaGsucjk3VGdWc2NGLg%3D%3D&code_challenge=KdRY2ZYj0Lq-swlw9str66f0wftddl8IGUusNf2bArE&code_challenge_method=S256&auth0Client=eyJuYW1lIjoiYXV0aDAtc3BhLWpzIiwidmVyc2lvbiI6IjEuMjEuMCJ9, também será necessário criar uma chave de API, acesse esse link https://platform.openai.com/api-keys para mais informações sobre como criar sua chave de API. Por fim você deve anexar a sua chave no arquivo application.properties nas configurações spring.ai.openai.api-key=API_KEY e openai.api.key=API_KEY;
  ### Compilação e execução
  ```bash
  $ cd 
  ```
### Usando um container
Caso não queira criar um banco localmente, ou se desejar você pode executar a aplicação usando um container docker. Para isso vá até o diretório principal do projeto e execute 
o comando para iniciar o container docker-compose up.
No Arquivo application.properties dentro do diretório resources, adicione as informações para conexão com seu banco de dados.
- Exemplo:
    - spring.datasource.url=jdbc:postgresql://localhost:5432/alumind_db
    - spring.datasource.username=postgres
    - spring.datasource.password=postgres

## Banco de Dados :bank:
### Ferramentas
- PostgresSQL (v19.1)
- Flyway Core
### Migrações
Após criar seu banco de dados localmente com as informações de usuário e senha execute o projeto. O arquivo de migração será executado automaticamente para criar a tabela "feedback" do banco, além de inserir dados fictícios de feedback. Caso deseje, você pode remover essa funcionalidade no arquivo application.properties atribuindo false na seguinte configuração:
- - spring.flyway.enabled

  



