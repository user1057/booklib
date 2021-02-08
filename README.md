# Booklib - Assignment 63

Develop an app for uploading books in PDF format and viewing their pages.

## Description

[JHipster] is used to generate monolithic project with security(JWT), database(Postgres), messaging(Kafka), docker and test files.

Subsequent generation of entity domain models is done with:

    jhipster import-jdl BookEntity.jdl

For Book data and metadata is used Postgres.

Asynchronus PDF conversion is done in Kafka consumer on "BOOK_TOPIC"(by default with only 1 partition and 1 replication broker)

For scaling, Kafka consumer should be put in microservice and scaled up, with topic containing several partitons for parallelism.

Mapstruct is used for decoupling and mapping from OpenApi to domain(MDL sufix files).

Hibernate is doing ORM and domain objects are updated after generation(with import-jdl) to prevent EAGER loading(ex. when fetching Book entity not to load PDF data).

## PDF conversion

[Free Spire.PDF for Java] is used for conversion of pages to images and has a limitation of 10 pages.

## Open Api design

[Apicurio] is used for API designing and is located in

    src/main/resources/swagger/api.yml

## Docker

Image is prebuilt and located at:

    docker.io/bsdockerzg/booklib

Building and pushing is done using:

    ./mvnw -Pprod compile -Djib.to.image=docker.io/bsdockerzg/booklib:latest -Djib.to.auth.username=bsdockerzg -Djib.to.auth.password=XXX jib:build

To start application using docker:

    export PORT=8080
    docker-compose up

    OR

    PORT=1234 docker-compose up

## Postman requests

There is a collection of requests available for import in Postman. The import file is located in project root file `Booklib.postman_collection.json`. Enviroment variables `protocol`, `host_`, `username`, `password` and global variables `token` are used.

Collection has a authentication script so every call automatically prefetches Bearer token.

    const getTaxAccessToken={
      url: pm.environment.get("protocol")+'://'+pm.environment.get("host_")+'/api/authenticate',
      method: "post",
      body: {
         mode: 'raw',
         raw: JSON.stringify({'password': pm.environment.get("password"), 'rememberme': true, 'username':pm.environment.get("username")})
      },
      header: {
          'Content-Type': 'application/json'
      }
    };
    pm.sendRequest(getTaxAccessToken, function (err, response) {
      pm.globals.set("token", response.json()['id_token']);
    });

Single authentication token fetch request is also included.

Authenticate:

    curl --location --request POST 'http://localhost:1234/api/authenticate' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "password": "admin",
            "rememberme": true,
            "username": "admin"
        }'

Upload book:

    curl --location --request POST 'http://localhost:1234/book' \
        --header 'Accept-Encoding: gzip, deflate' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxMjgzMjY4MX0.51P3rCpHg4Y46g4t6yZYG2qpU7moohv-ijxgHfEK3WHTxd90V5Rc24RkpqIHk1ghay54neJsFliQ-cAwe6dabw' \
        --form 'file=@"/home/bobo/Downloads/sample.pdf"' \
        --form 'isbn="978-1-4949-3461-3"'

Get all books:

    curl --location --request GET 'http://localhost:1234/book' \
        --header 'Accept-Encoding: gzip, deflate' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxMjgzOTYwNn0.dpf3Y8LWyyqzHKNJCkHOXQgoQ5d-mkGhmTwoKsZLprPyn6ii6z9ATadlohyMy9VV2jufzUaw11OgotTH3NUU9w'

Get page url:

    curl --location --request GET 'http://localhost:1234/page' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxMjgzOTc3Mn0.f8omegjk5TYsf0kpdNzFfwCThMDqI92woRHXASgnM987qEUtU10PKtAIPa8teOTLSrH9tpaYAt4QQbPx-ab35A' \
        --form 'isbn="978-1-4949-3461-3"' \
        --form 'page="0"'

Get page image:

    curl --location --request GET 'http://localhost:1234/url/113ff1e6-cc5c-497d-bcfb-5cdf2a9788a0' \
        --header 'Accept-Encoding: gzip, deflate' \
        --header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLFJPTEVfVVNFUiIsImV4cCI6MTYxMjgzOTc4Nn0.yK2Bln-waeP4sN9jBh30zUcZXNfsyfVndpIwzg6DGzZk2GEK3xalHPMFhx0G3oNa-GgV2wSs8C2ZsYMiiX8CvQ'

## Notes

Temporary link available 5min after generation.  
Maximum PDF processing time is 5min.  
File size limited to 30MB.

## Versions

Node v12.16.1  
NPM 6.14.2  
JHipster 6.10.5  
Docker 1.25

[jhipster]: https://www.jhipster.tech
[free spire.pdf for java]: https://www.e-iceblue.com/Introduce/free-pdf-for-java.html#.YCAJ6ei2lnI
[apicurio]: https://www.apicur.io/
