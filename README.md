# ⚔️ Strategic Matchup Finder

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue.svg)
![Vanilla JS](https://img.shields.io/badge/JavaScript-Vanilla-yellow.svg)

*Read this in other language: [🇧🇷 Português](README.pt-br.md)*

A Full-Stack application designed to analyze and display strategic matchups for League of Legends. The platform allows players to check win rates against specific opponents based on real, dynamic data, crossing these statistics with their own *Champion Pool*.

## 🎯 The Purpose: From Console App to Full-Stack
This project marks my official technical transition. Before starting it, my experience was strictly limited to logic running entirely in console applications. 

The **Strategic Matchup Finder** was born with the strict purpose of challenging me to step out of my comfort zone. Over a period of approximately 4 months, I built this entire ecosystem from scratch, learning and applying hands-on for the first time:
* How to design a **Layered Architecture** (Controller, Service, Repository).
* How **Spring Boot** manages dependency injection and exposes HTTP routes.
* How to model and integrate a relational database (**PostgreSQL**) using an ORM (Hibernate).
* How to build and manipulate the DOM in the **Frontend** (HTML/CSS/JS) and make it consume a REST API asynchronously.

---

## 🛠️ Tech Stack & Tools

### Backend (Core)
* **Java 21:** Utilizing modern features like `Records` to create immutable DTOs, ensuring clean and secure data transfer between layers.
* **Spring Boot (Web, Data JPA):** Building a robust RESTful API.
* **PostgreSQL & Hibernate:** Relational database modeling and data persistence.
* **JSoup (Web Scraping):** Implementing an automation pipeline for real data extraction.
* **Lombok:** Reducing boilerplate code for entities and dependency injection.

### Frontend
* **HTML5, CSS3 & Vanilla JavaScript:** Building a responsive and dynamic interface without the use of heavy frameworks, demonstrating absolute domain over DOM events and asynchronous requests (`Fetch API`, `Promises`).

---

## 🚀 Technical Highlights

This project was built focusing on best practices, performance, and data resilience:

### 1. Automation and Real Data Extraction (Web Scraping)
The system does not rely on manually inserted data or static APIs. The `ScrapperService` acts as a bot that dynamically scrapes the *u.gg* platform.
* **Conscious Rate Limiting:** Implemented request throttling (`Thread.sleep`) to respect the target server and avoid IP blocks.
* **Data Sanitization:** Complex HTML parsing, string anomaly handling, and type safety enforcement before database persistence.

### 2. Database & Query Optimization (Avoiding the N+1 Problem)
To ensure high performance when fetching the matchup list, database communication was optimized using **Custom JPQL Queries**.
* The use of `JOIN FETCH` in the repository ensures that the Champion, the Opponent, and the Matchup are brought into memory in a `Single Query`, completely avoiding the notorious N+1 Problem in Hibernate.
* Used `@UniqueConstraint` in the database modeling to guarantee referential integrity at the DBMS level (binding Hero + Enemy + Lane), preventing duplicate data during concurrent executions of the scraper.

### 3. Transaction Management (ACID)
Mass insertion routines (such as updating hundreds of simultaneous matchups) are wrapped by the `@Transactional` annotation. This guarantees the operation's atomicity: either the database updates all data successfully, or it triggers a complete *Rollback* in case of network failure or exceptions, protecting the system against corrupted data.

### 4. Decoupled Architecture and Immutability
* **Domain Isolation:** Database entities (`@Entity`) are never exposed to the Frontend. All traffic occurs strictly via immutable `DTOs`.
* **CORS Configuration:** The backend is natively configured to handle cross-origin requests, allowing scalability for deployment across separate domains.

---

## ⚙️ How to Run Locally

### Prerequisites
* Java 21+
* Maven
* PostgreSQL running locally (or via Docker)

### Database Configuration
The project already includes a `docker-compose.yml` to easily spin up the database.
```bash
# Start the database via Docker
docker-compose up -d
```

_If running Postgres locally without Docker, make sure to create a database named `lolproject` and update the `application.properties` with your credentials._

### Running the Backend

Bash

```
cd backend
./mvnw spring-boot:run
```

_Note: When running the application for the first time, the `DatabaseSeeder` will automatically invoke the Scraper to populate the main champion tables._

### Running the Frontend

Simply open the `/frontend/index.html` file in any modern browser, or use an extension like VS Code's _Live Server_.


**_To run the final compiled version without downloading the source code, head over to the Releases tab and follow the instructions provided there._**

---

## 📈 Roadmap & Next Steps

- [ ] **Unit and Integration Tests:** Implement backend test coverage using classic ecosystem tools (such as **JUnit** and **Mockito**) to guarantee the stability of business rules and prevent regressions.
    
- [ ] **Task Scheduling (Cron Jobs):** Implement Spring's `@Scheduled` annotation so the `ScrapperService` runs automatically during the night, keeping the database updated without manual intervention.
    
- [ ] **Frontend Algorithm Optimization:** Refactor search logic (`find/filter`) using HashMaps (JS Objects/Maps) to reduce time complexity from $O(n)$ to $O(1)$.
    
- [ ] **Full Dockerization:** Create Docker images for both the Backend and Frontend, unifying the infrastructure.
    
- [ ] **Cloud Deployment (CI/CD):** Deploy the application to cloud services (like AWS, Render, or Railway) using the images generated in the dockerization step.
    

---

_Developed with curiosity, logic, and a massive thirst to be a champion._ 🏆