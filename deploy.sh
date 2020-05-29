#!/bin/bash

mvn -f ./rest_api clean install
mvn -f ./rest_api -DskipTests=true package

# Extract
~/Apache/Tomcat/apache-tomcat-9.0.33/bin/catalina-school_notifier.sh stop && \
cd ~/git/school_notifier/rest_api/ && \
/usr/lib/jvm/java-11/bin/java -Dmaven.multiModuleProjectDirectory=/home/eduwardo/git/school_notifier/rest_api -Dmaven.home=/home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/plugins/maven/lib/maven3 -Dclassworlds.conf=/home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/plugins/maven/lib/maven3/bin/m2.conf -Dmaven.ext.class.path=/home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/plugins/maven/lib/maven-event-listener.jar -javaagent:/home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/lib/idea_rt.jar=33553:/home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/bin -Dfile.encoding=UTF-8 -classpath /home/eduwardo/.local/share/JetBrains/Toolbox/apps/IDEA-U/ch-0/201.6668.121/plugins/maven/lib/maven3/boot/plexus-classworlds-2.6.0.jar org.codehaus.classworlds.Launcher -Didea.version2020.1 -DskipTests=true clean package && \
cp ~/git/school_notifier/rest_api/target/SchoolNotifier-0.0.1-SNAPSHOT.war /home/eduwardo/Apache/Tomcat/apache-tomcat-9.0.33/webapps && \
rm -rf ~/Apache/Tomcat/apache-tomcat-9.0.33/webapps/school_notifier && \
unzip -d ~/Apache/Tomcat/apache-tomcat-9.0.33/webapps/school_notifier ~/Apache/Tomcat/apache-tomcat-9.0.33/webapps/SchoolNotifier-0.0.1-SNAPSHOT.war && \
rm -rf ~/Apache/Tomcat/apache-tomcat-9.0.33/webapps/SchoolNotifier-0.0.1-SNAPSHOT.war
~/Apache/Tomcat/apache-tomcat-9.0.33/bin/catalina-school_notifier.sh run

