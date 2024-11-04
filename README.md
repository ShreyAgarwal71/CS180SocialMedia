# CS180SocialMedia

CS180SocialMedia is a Java-based backend simulation for a simple social media platform. The platform is a digital wall-writing app, essentially users will be able to join classes they are currently enrolled in and view posts that previous students have made. Plase 1 contains for basic user management, posting, and commenting, with data persistence through the use of a file storing system. This project is built with Maven and includes Java classes for users, posts, comments, and images.

**Class**: CS 180  
**Group Names**:

- Shrey Agarwal
- Christian Slade
- Zayan Niaz
- Ates Isfendiyaroglu
- Mahit Mehta

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Usage](#usage)
- [Classes] (#classes)
- [Data Storage](#data-storage)
- [Testing](#testing)

---

## Features

- **User Management**: Create, update, retrieve, and delete user profiles.
- **Posts and Comments**: Users can create posts and add comments to interact with one another.
- **Image Collection**: Placeholder for image management, where users or posts may have associated images.
- **File-based Storage**: Data is stored in text files (`users.txt`, `posts.txt`, and `comments.txt`) for persistence.

---

## Project Structure

The main components of the project include:

- **App.java**: The main entry point of the application, initializing and running the backend simulation.
- **Database.java**: Manages reading from and writing to files, acting as a simple file-based database.
- **User, Post, and Comment Classes**: Define the entities in the system, with `UserCollection`, `PostCollection`, and `CommentCollection` managing collections of these entities.
- **ImageCollection.java**: Handles image data, if implemented for users or posts.

---

### Directory Layout

```plaintext
CS180SocialMedia/
├── README.md
├── .gitignore
├── eclipse-formatter.xml
├── SocialMediaServer/
│   ├── pom.xml                 # Maven configuration file
│   └── src/
│       ├── main/
│       │   └── java/com/cs180/
│       │       ├── App.java    # Main application entry point
│       │       ├── db/         # Database and collection classes
│       │           ├── User.java
│       │           ├── UserCollection.java
│       │           ├── Post.java
│       │           ├── PostCollection.java
│       │           ├── Comment.java
│       │           ├── CommentCollection.java
│       │           ├── ImageCollection.java
│       │           └── Database.java
│       └── test/
│           └── java/com/cs180/
│               └── AppTest.java  # Unit tests for core functionality
└── Data Files
    ├── users.txt               # Stores user data
    ├── posts.txt               # Stores post data
    └── comments.txt            # Stores comment data
```

---

## Setup

### Prerequisites

- **Java**: JDK 8 or higher
- **Maven**: For dependency management and building

---

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ShreyAgarwal71/CS180SocialMedia.git
   cd CS180SocialMedia/SocialMediaServer
   ```
2. Build the project with Maven:
   ```bash
   mvn clean install
   ```

---

## Classes

- **User**: This class represents a user object that can be added to a user collection. A user has a username, password, display name, and email. The class defines all respective getter and setter methods, equals methods and a toString.
- **Post**: This class represents a post object that can be added to a feed. A post has a message, a user, a date, a postID, and votes. The class defines all respective getter and setter methods, equals methods and a toString.
- **Comment**: This class represents a comment object that can be added to a post or another comment. A comment contains a message, a user, a date, a commentID, votes, and comments. The class defines all respective getter and setter methods, equals methods and a toString.

---

## Usage

1. Run the application:
   ```bash
   mvn exec:java -Dexec.mainClass="com.cs180.App"
   ```
2. Interact with the program to create, view, and manage users, posts, and comments. The application saves data to text files for persistence.

---

## Data Storage

The program stores data in three text files:

- **users.txt**: Holds user data (e.g., usernames, passwords, and profiles).
- **posts.txt**: Contains post details, including content, author, and timestamps.
- **comments.txt**: Holds comment data associated with specific posts.

These files are read and updated through the `Database.java` class, ensuring data is saved across application sessions.

---

## Testing

To run unit tests for this project:

```bash
mvn test
```

Tests are located in the `AppTest.java` file within the `test` directory, this covers the main functionalities of user and post management.

---
