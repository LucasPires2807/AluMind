# AluMind
Este projeto proporciona uma API para avaliação de feedbacks com base nos sentimentos transmitidos no conteúdo das mensagens enviadas pelos usuários. O principal objetivo consiste em fazer uso de LLMs (Large Language Model), para classificar os feedbacks recebidos como positivos ou negativos, e a partir disso avaliar a mensagem e gerar resultados.
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
## Banco de Dados (Ferramentas) :bank:
- PostgresSQL (v19.1)
- Flyway Core

No Arquivo application.properties dentro do diretório resources, adicione as informações para conexão com seu banco de dados. Lembre-se de criar um banco localmente usando as mesmas configurações de acesso determinadas no arquivo application.properties.
- Exemplo:
    - spring.datasource.url=jdbc:postgresql://localhost:5432/alumind_db
    - spring.datasource.username=postgres
    - spring.datasource.password=postgres
      
  ### Instruções OpenAI
  A aplicação realiza uma integração com as ferramentas disponibilizadas pela API do OpenAI Chat, sendo assim caso ainda não possua uma conta no serviço da OpenAI, será necessário criá-la. [crie sua conta](https://auth.openai.com/authorize?audience=https%3A%2F%2Fapi.openai.com%2Fv1&auth0Client=eyJuYW1lIjoiYXV0aDAtc3BhLWpzIiwidmVyc2lvbiI6IjEuMjEuMCJ9&client_id=DRivsnm2Mu42T3KOpqdtwB3NYviHYzwD&device_id=9076a0de-c5c3-43cb-9b24-c91f82566242&issuer=https%3A%2F%2Fauth.openai.com&max_age=0&nonce=dWwwOXlHaHpXaXA0TjYwdERvY25icWVJMzd2ZVhFVU9TZTg3RjBzSTJkZw%3D%3D&redirect_uri=https%3A%2F%2Fplatform.openai.com%2Fauth%2Fcallback&response_mode=query&response_type=code&scope=openid+profile+email+offline_access&screen_hint=signup&state=Q0VON21QRjZWakdhUEJMRUJPVkJ2ZHI0fnFUSEc5STh2N1BiNHJZbUtnVQ%3D%3D&flow=treatment), também será necessário criar uma chave de API, acesse esse link [API key](https://platform.openai.com/api-keys) para mais informações sobre como criar sua chave de API. Por fim você deve anexar a sua chave no arquivo application.properties, nas configurações:
    - spring.ai.openai.api-key=API_KEY
    -  openai.api.key=API_KEY;
  ### Compilação e execução
  No diretório principal do projeto inicie a aplicação executando os seguintes comandos (lembre-se de verificar se sua versão do maven e java estão corretos): 
  ```bash
  $  mvn clean install
  $  mvn spring-boot:run
  ```
### Usando um container
Caso não queira criar um banco localmente, ou se desejar você pode executar a aplicação usando um container docker. Para isso vá até o diretório principal do projeto e execute 
o comando para iniciar o container (certifique-se de ter o docker instalado e configurado em sua maquina):
 ```bash
  $  docker-compose up
  ```
### Migrações
Após criar seu banco de dados localmente com as informações de usuário e senha execute o projeto. O arquivo de migração será executado automaticamente para criar a tabela "feedback" do banco, além de inserir dados fictícios de feedback. Caso deseje, você pode remover essa funcionalidade no arquivo application.properties atribuindo false na seguinte configuração:
  - spring.flyway.enabled


  



