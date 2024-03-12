FROM tomcat:8.0.51-jre8-alpine
MAINTAINER satyam@gmail.com
# copy war file on to container
COPY ./target/mmt-project*.jar  /usr/local/tomcat/webapps
EXPOSE  8080
USER mmt-project
WORKDIR /usr/local/tomcat/webapps
CMD ["catalina.sh","run"]