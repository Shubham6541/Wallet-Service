# Exercise-04

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
    - Spring JPA, ORM    
- Second Story Get on wallet
    - Swagger Documentation
        `https://editor.swagger.io/`
        `http://localhost:8080/v2/api-docs`
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
    
    

#CD/CI

##Heroku

- https://devcenter.heroku.com/articles/deploying-spring-boot-apps-to-heroku


## GCP

```
Create gcp account
create project
Create docker registry account for gitlab
brew cask install google-cloud-sdk
gcloud init
gcloud projects create ep3-wallet-service
kubectl expose deployment wallet-service --type=LoadBalancer --port 80 --target-port 8082
```    

  
  
