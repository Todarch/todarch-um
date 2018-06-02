# User Management Service

It is user management service for Todarch application.

## Maven

```shell
mvn clean package

mvn clean package -DskipTests=true
```

## Docker

- Build your own image

```shell
docker image build -t todarch/um:latest -f dockerfiles/Dockerfile .
```

- Or use [the image](https://hub.docker.com/r/todarch/um/) from public repository (when master branch is updated, it is automatically rebuilt on Docker Cloud)

```shell
docker container run -it --rm --name todarch-um todarch:um -p 8081:8080 todarch/um
```

- reach [health check endpoint](http://localhost:8081/non-secured/up) at local

## Heroku

- master branch is automatically deployed [as Heroku app](https://todarch-um.herokuapp.com/non-secured/up) after each successful Jenkins build.

## Swagger Api Documentation

- [on Heroku deploy](https://todarch-um.herokuapp.com/swagger-ui.html)

**Note:** Currently, the application uses H2 in-memory database, so after each restart/redeployed database will be wiped up. Later, after some maturity, an external database is going to be used.





