# Business Rule Engine

- This API manages the Business Rules for a Order Handing System. Rules are defined based on the product type.
- This API provides the business rules related to a product type.
- Further this needs to be extended to perform the actions based on that rules.

# Installation
This service requires Docker and docker-compose to be installed on your system. 

To build the project
```
mvn clean package
```

To start the database locally use :

```
docker-compose up
```

To run the springboot application use:

```
mvn spring-boot:run
```

# Swagger-UI
Url to open swagger-ui : http://localhost:8080/swagger-ui.html

# Test Data 
Available products type:

```
physical_product
book
activate_membership
upgrade_membership
video
learning_to_ski_video
```