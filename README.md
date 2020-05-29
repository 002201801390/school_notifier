# School Notifier

Curso: Análise e Desenvolvimento de Sistemas [ADS]

##### Integrantes:
 * 002201801390 - Eduwardo Keizo Horibe Junior

##### Estrutura das pastas:
<pre>
/                            -> Raiz do projeto
  /database                  -> Arquivos relacionados a manipulação do bando de dados
    /docker_postgres.sh      -> Script para criar o banco de dados containerizado
    /configure_database.sql  -> Script SQL para popular o banco de dados
    /reset_database.sql      -> Script SQL para limpar o banco de dados
  /school_notifier_app       -> Projeto mobile utilizando o Flutter
    /lib/...
    /test/...
  /rest_api                  -> Código fonte da API REST
    /src
      /main/...
      /test/...
  /setup.sh                  -> Script de configuração inicial
  /deploy.sh                 -> Script para fazer o build e o deploy da aplicação REST
</pre>

### Instalação

##### Pré-requisitos:
 * JDK 11 instalado
 * Servidor de aplicação `Tomcat 9`
 * Banco de dados `Postgres` instalado, rodando com a porta `5432` exposta, senha `pass` para o usuário `postgres`
   e com uma nova base de dados com o nome `school_notifier`, executando `CREATE DATABASE school_notifier`
   * Caso o seu sistema seja Unix, para criar o banco de dados conteinerizado com as especificações citadas,
     execute o script `database/docker_postgres.sh`
 * Para sistemas Unix:
    * Maven instalado, e configurado para utilizar a JDK 11. A saída para o comando `mvn --version` deve indicar algo como
      `Java version: 11.X.X`. Caso o maven esteja utilizando uma versão mais antiga da JDK, adicione `JAVA_HOME` indicando
      o diretório da JDK 11 e `JAVACMD` indicando `$JAVA_HOME/bin/java` como variáveis de ambiente no seu sistema.

##### Unix:
 * Dentro do diretório do projeto, rode o arquivo `setup.sh` passando o caminho do arquivo `catalina.sh` e da pasta `webapps` nessa sequência e seja feliz ;)
 <pre>$ ./setup.sh $CATALINA_PATH $WEBAPPS_PATH</pre>

##### Windows:
 * Rode os comandos do arquivo `database/configure_database.sql` no Postgres
 * Faça o build do projeto, gerando o arquivo `rest_api/target/SchoolNotifier-0.0.1-SNAPSHOT.war`
 * Extraia o arquivo `.war` do projeto `rest_api` para a pasta `webapps` do Tomcat com o nome `school_notifier`,
   ficando algo similar a `$WEBAPPS_PATH/school_notifier/...`
 * Inicie o Tomcat


###### TLTR(Too Lazy To Read) - UNIX
###### Assuming:
 * JDK 11 installed
 * Maven installed and using JDK 11
 * Tomcat 9 installed
 * Docker installed and running

<pre>
$ cd $PROJECT_DIRECTORY
$ ./database/docker_postgres.sh
$ ./setup.sh $CATALINA_PATH $WEBAPPS_PATH
</pre>
