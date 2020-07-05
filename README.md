# School Notifier

Curso: Análise e Desenvolvimento de Sistemas [ADS]

##### Integrantes:
 * 002201801390 - Eduwardo Keizo Horibe Junior

##### Estrutura das pastas:
```
/                            -> Raiz do projeto
  /database                  -> Arquivos relacionados a manipulação do bando de dados
    /docker_postgres.sh      -> Script para criar o banco de dados containerizado
    
    /configure_database.sql  -> Script SQL para configurar o banco de dados
    /fill_database.sql       -> Script SQL para popular o banco de dados
    /clear_database.sql      -> Script SQL para limpar o banco de dados

  /school_notifier_app       -> Projeto mobile utilizando o Flutter
    /lib/...
  /school_notifier_web       -> Projeto desktop utilizando o Flutter
    /lib/...
  /rest_api                  -> Código fonte da API REST
    /src
      /main/...
      /test/...
  /setup.sh                  -> Script de configuração inicial
  /deploy.sh                 -> Script para fazer o build e o deploy da aplicação REST
```

### Instalação
Observação: No decorrer deste arquivo utilizarei termos como `$PROJECT_DIRECTORY`, `$CATALINA_PATH` e `$WEBAPPS_PATH` para descrever a localização
dos arquivos deste projeto, do arquivo `catalina.[sh|bat]` e a pasta de aplicações web do tomcat respectivamente.

#### Pré-requisitos:
 * JDK 11 instalado
 * Servidor de aplicação `Tomcat 9` - [Link para download](https://tomcat.apache.org/download-90.cgi)
 * Banco de dados `PostgreSQL` instalado, rodando na porta `5432`, senha `pass` para o usuário `postgres`
   e com uma nova base de dados com o nome `school_notifier`, executando `CREATE DATABASE school_notifier`
   * Caso o seu sistema seja Unix, para criar o banco de dados conteinerizado com as especificações citadas,
     execute o script `database/docker_postgres.sh`
 * Maven instalado, e configurado para utilizar a JDK 11. A saída para o comando `mvn --version` deve indicar algo como
   `Java version: 11.X.X`. Caso o maven esteja utilizando uma versão mais antiga da JDK, adicione `JAVA_HOME` indicando
   o diretório da JDK 11 e `JAVACMD` indicando `$JAVA_HOME/bin/java` como variáveis de ambiente no seu sistema.
 * Flutter SDK instalado e configurado - [Link para download](https://flutter.dev/docs/get-started/install)


#### Configuração inicial do banco de dados:
##### Unix:
 * Dentro do diretório do projeto, rode o arquivo `setup.sh`
 ```
 $ ./setup.sh
 ```

##### Windows:
 * Rode os comandos do arquivo `database/configure_database.sql` e `database/fill_database.sql` respectivamente no banco de dados PostgreSQL


#### Deploy da API Rest:
##### Unix - Automatizado:
 * Dentro do diretório do projeto, rode o arquivo `deploy.sh` passando o caminho do arquivo `catalina.sh` e da pasta `webapps` nessa sequência
 ```
 $ ./deploy.sh $CATALINA_PATH $WEBAPPS_PATH
 ```

##### Unix/Windows - Manual:
 * Realizar o build do projeto dentro de `/rest_api` com os comandos `mvn install -DskipTests=true` para baixar as dependencias e `mvn package -DskipTests=true` para compilar o projeto
 * Extrair o arquivo `/rest_api/target/SchoolNotifier-0.0.1-SNAPSHOT.war` para dentro da pasta `$WEBAPPS_PATH/school_notifier`
 * Inicie o Tomcat


#### Iniciando o Aplicativo Mobile:
 * Instale o arquivo `/compiled_apps/app/app-release.apk` no dispositivo

 ou

 * Certifique-se de possuir um dispositivo android conectado em modo de depuração USB ou um emulador já iniciado
 * Entre na pasta do aplicativo em `/school_notifier_app`
 * Execute o comando `flutter run`


#### Iniciando o Aplicativo Desktop:
**ATENÇÃO**: O Flutter para desktop ainda está em fase de desenvolvimento, podendo ocorrer alguns bugs inesperados.
A versão desktop foi desenvolvida e testada na distribuição Linux Fedora 31 e 32, com a interface desktop GNOME e com
o servidor gráfico Xorg.
Para verificar qual servidor gráfico seu sistema está utilizando execute `echo $XDG_SESSION_TYPE` no terminal. 
Caso a saída seja `x11` o sistema já está utilizando o Xorg, caso a saída seja `wayland` (que está sendo adotado nas distros linux mais novas)
será necessário mudar para o Xorg.
Para mudar o servidor pela insterface do sistema siga [este guia](https://www.maketecheasier.com/switch-xorg-wayland-ubuntu1710/)
e para alterar pelo teminal siga as instruções abaixo:

OBS: Realizado na distribuição Linux Fedora 31 e 32
```
$ sudo [vim|nano|gedit|etc...] /var/lib/AccountsService/users/$USER
```
Altere a propriedade `XSession=` para `XSession=gnome-xorg` e realize o logout e login novamente


 * Execute o arquivo `compiled_apps/web/school_notifier_web` - *Linux apenas*
 
 ou
 
 * Habilite a feature de aplicações desktop executando `flutter config --enable-[linux|macos|windows]-desktop`
 * Entre na pasta do aplicativo em `/school_notifier_web`
 * Execute o comando `flutter run`


#### TLTR(Too Lazy To Read) - UNIX
##### Assuming:
 * JDK 11 installed
 * Maven installed and using JDK 11
 * Tomcat 9 installed
 * Docker installed and running
 * Flutter SDK installed
 * Xorg as graphical server

##### Deploy:
```
$ cd $PROJECT_DIRECTORY
$ ./database/docker_postgres.sh
$ ./setup.sh
$ ./deploy.sh $CATALINA_PATH $WEBAPPS_PATH
```

##### Running Mobile App:
```
$ cd $PROJECT_DIRECTORY/school_notifier_app
$ flutter run
```

##### Running Desktop App:
```
$ flutter config --enable-[linux|macos|windows]-desktop
$ cd $PROJECT_DIRECTORY/school_notifier_web
$ flutter run
```
