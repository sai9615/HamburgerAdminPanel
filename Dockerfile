FROM openjdk:15
ADD target/HamburgerAdminPanel-0.0.1-SNAPSHOT.jar HamburgerAdminPanel-0.0.1-SNAPSHOT.jar
EXPOSE 8080
EXPOSE 3306
ENTRYPOINT ["java", "-jar", "HamburgerAdminPanel-0.0.1-SNAPSHOT.jar"]


#My Docker commands
#docker build -t springapp .
#Connect to db using: docker run --name mydb -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=test -d mysql:5.6
#run: docker run -p 8080:8080 --name springapp --link mydb:mysql -d springapp
