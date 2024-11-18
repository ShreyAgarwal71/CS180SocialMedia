## Features

- **Register**: Create an account.
- **Login**: Login to an existing account.
- **Local Storage**: Caching previous requests, storing data like Auth tokens locally.

---

### Prerequisites

- **Java**: JDK 23
- **Maven**: For dependency management and building
- Run `mvn clean install` before in the root directory before attempting to run the client application

---

## Usage

> Note: Must have JDK 23 installed.
> cd into LeWallApp/ before running this command

- mvn clean compile javafx:run

---

### Directory Layout

```plaintext
LeWall/
├── README.md
├── pom.xml                     # Maven configuration file
└── src/main/
    ├── java/com/lewall/
    │   ├── api/ 
    │   │   ├── Connection.java
    │   │   └── LocalStorage.java
    │   ├── components/
    │   │   ├── Footer.java
    │   │   └── PasswordField.java
    │   ├── pages/
    │   │   ├── Home.java
    │   │   ├── Login.java
    │   │   └── Register.java
    │   ├── App.java
    │   └── Navigator.java
    └── resources/
        ├── css/ 
        │   └── global.css
        ├── imgs/ 
        │   ├── eye-hide.png
        │   ├── eye-reveal.png
        │   ├── google-icon.png
        │   └── loading-gear.java
        └── log4j2.xml
```

---

## Classes

- **App.java**: The main entry point of the application, initializing and running the frontend simulation.
- **Navigator.java**: Manages the navigation in the application.
- **Connection.java**: Manages the client-server connection of the application.
- **LocalStorage.java**: Manages local storage of the application.
- **Footer.java**: Defines the footer in the UI.
- **PasswordField.java**: Defines the password field in the UI.
- **Home.java**: Defines the home page of the application.
- **Login.java**: Defines the login page of the application.
- **Register.java**: Defines the register page of the application.

---

## Data Storage

The program stores data in three text files:

- **users.txt**: Holds user data (e.g., usernames, passwords, and profiles).
- **posts.txt**: Contains post details, including content, author, and timestamps.
- **comments.txt**: Holds comment data associated with specific posts.

These files are read and updated through the `Database.java` class, ensuring data is saved across application sessions.

---
