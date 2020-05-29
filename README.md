# School Notifier

Curso: Análise e Desenvolvimento de Sistemas [ADS]

##### Integrantes:
 * 002201801390 - Eduwardo Keizo Horibe Junior

##### Estrutura das pastas:
<pre>
/                       -> Raiz do projeto
  /database             -> Arquivos para criar e popular o banco de dados containerizado
  /school_notifier_app  -> Projeto mobile utilizando o FLutter
    /lib/...
    /test/...
  /rest_api             -> Código fonte da API REST
    /src
      /main/...
      /test/...
  /setup.sh             -> Script de configuração inicial
  /deploy.sh            -> Script para fazer o build e o deploy da aplicação REST
</pre>

### Instalação

##### Pré-requisitos:
 * Banco de dados `Postgres` instalado, rodando com a porta `5432` exposta, senha `pass` para o usuário `postgres`
   e com uma nova base de dados com o nome `school_notifier`, executando `CREATE DATABASE school_notifier`
   (PS. para criar o banco de dados conteinerizado com as especificações citadas, execute o script `database/docker_postgres.sh`);
 * Servidor de aplicação `Tomcat`;

##### Unix:
 * Rode o arquivo `setup.sh` passando o caminho do arquivo `catalina.sh` e da pasta `webapps` nessa sequência e seja feliz ;)

##### Windows:
 * Rode as os comandos do arquivo `database/configure_database.sql` no Postgres
 * Faça o build do projeto, gerando o arquivo `rest_api/target/SchoolNotifier-0.0.1-SNAPSHOT.war`
 * Extraia o arquivo `.war` do projeto `rest_api` para a pasta `webapps` do Tomcat com o nome `school_notifier`,
   ficando algo similar a `$WEBAPPS/school_notifier/...`
 * Reinicie o Tomcat
