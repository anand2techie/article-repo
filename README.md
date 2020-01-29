# Getting Started
Articles service for the news reader app, developed in Java 8 and latest version of Spring Boot (2.2.1.RELEASE) 

# Command to run the application
mvn spring-boot:run

# Project Design
* Adopted clean & test-driven design for developing REST APIs for CRUD operations on articles.
* Adhered to REST API design standards, ensuring right HTTP methods are used with proper response codes.
* Project structure is laid out with separation of concerns in the first place. Used Hyperlinks/HATEOAS for discoverability.
* Used H2 as the database; Spring data JPA as repository support
* Lombok is used for concise-code writing

# Highlights
* Handwritten request and response models for APIs (refer rest.api package)
* Dedicated controller for Exception handling (ErrorController)
* Reusable components (e.g. ArticleCreateAndUpdateEntityBase & ArticleEntityBase)
* Swagger Configuration (refer http://localhost:8080/swagger-ui.html)
* Added Postman collections (News Reader App's Articles API.postman_collection)
* Extensive test coverage
* Slf4j for logging
* Paging and Sorting support
