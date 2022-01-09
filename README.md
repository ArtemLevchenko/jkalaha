#What is this?
Kalaha/Mancala board game in Spring Boot for Tech Assignment

https://en.wikipedia.org/wiki/Kalah

# Tech Stack
* Java 17
* Spring Boot/MVC/Security
* Lombok
* JUnit + Spring Boot Test
* Thymeleaf
* Native JS + jQuery 3.2.1
* MySQL 8.0.27

# How to run

Application properties:

    spring.datasource.url=jdbc:mysql://localhost:3306/jkalaha
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.username=root
    spring.datasource.password=root

Setup db:

    $ mysql
    CREATE SCHEMA jkalaha collate utf8mb4_0900_ai_ci;

    CREATE TABLE 'halloffame` (
    `id` bigint NOT NULL AUTO_INCREMENT, 
    `winner_name` varchar(250) NOT NULL, 
    `date` date NOT NULL, 
    PRIMARY KEY (`id`));

Run with maven:

    mvn test
    mvn spring-boot:run

# Users
There are 2 users, who are the players:
* marcoS/marcoS
* brandoN/brandoN

#Game Rules
A) Basic Rules:
* Play always moves around the board in a counter-clockwise circle (to the right)
* The store on your right belongs to you. That is where you keep the seeds you win.
* The six pits near you are your pits.
* Only use one hand to pick up and put down seeds.
* Once you touch the seeds in a pit, you must move those seeds.
* Only put seeds in your own store, not your opponent’s store.

B) Special Rules:
  * When the last seed in your hand lands in your store, take another turn.
  * When the last seed in your hand lands in one of your own pits, if that pit had been empty you
  get to keep all of the seeds in your opponents pit on the opposite side. Put those captured seeds,
  as well as the last seed that you just played on your side, into the store. 


# UML Overall
Flow for the user (sequence diagram): 

![Sequence](https://i.ibb.co/1sx4HGK/Sequence.png)

Use cases:

![Use Cases](https://i.ibb.co/FJ7cdWm/usecases.png)

# Game
### Gameplay:
For the test you can use apache-tomcat-9.0.56. Deploy your changes to apache-tomcat-9.0.56\webapps.
Then start your server:

![Start Tomcat Server](https://i.ibb.co/PWRQnVk/1.png)

### Location

    localhost:8080

#### Default Login Form in the first browser. brandoN (North) Player:
![Default Login Form Brandon](https://i.ibb.co/fSVvz9g/brando-NLog.png)
#### Default Login Form in the second browser. marcoS (South) Player:
![Default Login Form South](https://i.ibb.co/DMDFSsw/marco-SLog.png)
#### Start page before click [Start Game]:
![Start Game](https://i.ibb.co/H2ZfCBX/Game-Start.png)
#### Start Game is here:
![Gameplay1](https://i.ibb.co/1QY0Lty/Click-To-Start.png)
#### marcoS turn:
![MarcoS Turn](https://i.ibb.co/svKzNrF/marco-STurn.png)
#### brandoN turn:
![BrandoN Turn](https://i.ibb.co/LdXzH49/Brando-NTurn.png)
#### Get All Results from Database:
![BrandoN Turn](https://i.ibb.co/7C42bLY/Show-Result.png)

# Potential improvements

* Improve error handling
* Frontend design (bootstrap?)
* Add multiple board layouts
* CRUD operations for the players/users
* Frontend Angular/React implementation
* Security improvements

