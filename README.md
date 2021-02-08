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

[Free Spire.PDF for Java] has limitation to 10 pages:

## Open Api design

[Apicurio] is used for API designing and is located in

    src/main/resources/swagger/api.yml

## Docker

To start the application from docker images on port 8080 run in project root:

    docker-compose up

Or on port 1234

    PORT=1234 docker-compose up

## Notes

Temporary link available 5min after generation.  
Maximum PDF processing time is 5min.  
File size limited to 30MB.

## Versions

Node v12.16.1  
NPM 6.14.2  
JHipster 6.10.5  
  
[JHipster]: https://www.jhipster.tech
[Free Spire.PDF for Java]: https://www.e-iceblue.com/Introduce/free-pdf-for-java.html#.YCAJ6ei2lnI
[Apicurio]: https://www.apicur.io/
