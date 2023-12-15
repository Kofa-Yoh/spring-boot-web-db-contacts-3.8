# Task 3.8 Contact List
The project presents simple web app made with Java and Spring Boot.
On the main page `localhost:8081` you can see full contact list and create some other contacts. Table with contacts contains links for its edition and deletion.

Html pages are filled with Thymeleaf.
Database object manipulating is made through JdbcTemplate and JOOQ (configured via docker-compose file).

## Stack
* Spring Boot
* Spring MVC
* Spring Data JDBC
* JOOQ
* Docker
* PostgresSQL
* Lombok

## Quick Start
1. Clone this repository
2. Create bootJar with gradle
3. Change properties:
   * APP_INIT_CONTACTS - to add some contacts when application started (true) or not (false)
   * APP_USE_JOOQ - to use JOOQ (true), to use JdbcTemplate (false)

in `application.yml`
```
app:
    init:
        contacts: ${APP_INIT_CONTACTS:true}
    jooq: ${APP_USE_JOOQ:false}
```

or environments in `docker-compose.yml`
```
- APP_INIT_CONTACTS=true
- APP_USE_JOOQ=false
```
4. Run application
```
# via docker compose

cd docker
docker compose build
docker compose up
```
5. After finish of work stop docker containers
```
Ctrl + C
docker compose down
```