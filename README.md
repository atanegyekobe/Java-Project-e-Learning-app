
# 📘 E-Learning Platform

An **E-Learning platform** built with **Java** and **Spring Boot**, connected to a **MySQL database** via **MySQL Connector/J**.  
This project provides a foundation for managing online learning content, users, and workflows.

---

## 🚀 Features
- Java-based backend using **Spring Boot**
- Database integration with **MySQL**
- Modular architecture for scalability
- Ready-to-use SQL schema (`incometrac.sql`)

---

## 🛠️ Tech Stack
| Component        | Technology |
|------------------|------------|
| Language         | Java       |
| Framework        | Spring Boot |
| Database         | MySQL      |
| Connector        | MySQL Connector/J |

---

## ⚙️ Setup Instructions

Follow these steps to run the project locally:

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/e-learning.git
   cd e-learning
   ```

2. **Open in your IDE**  
   Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse, or Apache Netbeans).
   Recommended ApacheNetbeans

4. **Import required libraries**  
   Ensure that **Spring Boot** and **MySQL Connector/J** dependencies are included in your project.  
   If using Maven, check that your `pom.xml` contains:
   ```xml
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

5. **Create the database**  
   In MySQL, create a new database:
   ```sql
   CREATE DATABASE elearning;
   Password: Hellohi.rappers@1992
   ```

6. **Import the schema**  
   Load the provided `incometrac.sql` file into your database:
   ```bash
   mysql -u your_username -p elearning < incometrac.sql
   ```

7. **Configure application properties**  
   Update `src/main/resources/application.properties` with your database credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/elearning
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

8. **Run the application**  
   Start the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
   or run directly from your IDE.

---

## 📂 Project Structure
```
e-learning/
 ├── src/
 │   ├── main/
 │   │   ├── java/        # Java source code
 │   │   └── resources/   # Configuration files
 │   └── test/            # Unit tests
 ├── incometrac.sql       # Database schema
 ├── pom.xml              # Maven dependencies
 └── README.md            # Project documentation

- Added role-based authentication (students, instructors, admins)
