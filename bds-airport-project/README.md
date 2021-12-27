# Maven basic commands

## Basic info
Application to course BPC-BDS. Third project.

## Utilities
Firstly you need to log in with an employee account.

Authentization is made by BCrypt.

Application to create, edit, show info and detailed info about customers in database of Airport.

Application allows you to try SQL injection attacks on dummy table.

## Maven requirements
Download the Maven .zip from [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi). Extract the .zip and configure the path to the Maven bin folder in the system environment variables. Beforehand, download and configure the `JAVA_HOME` environment variable and `Java` in your `system environment variables` also. If something is not working, use Google, there are a plethora of tutorials on how to do it on all the OS platforms.

## Project build
To build the project you need to `cd` into `the cloned repository`:
```shell
$ mvn clean install
```
If you would like to just build the project enter `mvn clean package`

## Run the project
To run the built project (assume that the built .jar is in the target folder):
```shell
$ java -jar target/bds-airport-project-1.0.0.jar
```

## Bonus

