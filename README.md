# Exercise-04

## Topics covered in classroom

- Http,Rest intro
    - Model apis for simple application (facebeook post) 
- Greeting controller
    - Change the controller to read name from query parameters
    - WebMvc test
- Intro to Spring Boot Annotations
- First story (create wallet)
    - Serialization
    - Organizing project (Controller,Service,Repository)
    - Unit testing using SpringBoot test 
    - In Memory database (h2) and Console
        `http://localhost:8080/h2-console`
    - Spring JPA, ORM    
- Second Story Get on wallet
    - Swagger Documentation
        `https://editor.swagger.io/`
        `http://localhost:8080/v2/api-docs`
        `http://localhost:8080/swagger-ui.html`
    - Using Postgres instead of in memory DB
        `docker run --name postgresdb -e POSTGRES_PASSWORD=psql123 -p 5432:5432 -d postgres`
        `brew cask install pgadmin4`   
        `brew install psqlodbc`   
    - db migration using flyway
        - clusters 
        - db specific tuning and data types
        - column renaming , adding default values
- Transactions on wallet
    - migration script for new table
    - spring profiles
    - db relationships (one to many) , lazy loading
    - storing enumerations
    
## Topics covered in simulation    

- Validations
    - Using annotation for model validation
    - Controller Advice for generic validation handler
- CD/CI
    - Separate yml files for different environment
    - Code coverage using jacoco
    - Deployment using Heroku
        - https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku 
        - add postgres addon ->  `heroku addons:create heroku-postgresql`
        - prod url -> `https://pathashala-wallet-service.herokuapp.com/wallets/1`   
- Docker
    - custom docker file for application (devops/Dockefile) 
- Logging
    - Using logback for logging
    - logging configuration using yml and xml files
    - logstash friendly logging for feeding data into elk  
    - moving logs to elk using logstash (devops/logstash)  
- Cross Origins
    - Allowing Cross origin requests        
    

  
  
