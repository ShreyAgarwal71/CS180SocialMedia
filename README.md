# LeWall

LeWall is a modern, Java-based, social media application. The platform is a digital wall-writing app, essentially users will be able to join classes they are currently enrolled in and view posts that previous students have made. Plase 1 contains for basic user management, posting, and commenting, with data persistence through the use of a file storing system. This project is built with Maven and includes Java classes for users, posts and comments.

**Class**: CS 180
**Group Names**:

- Submitted by Shrey Agarwal
- Christian Slade
- Zayan Niaz
- Ates Isfendiyaroglu
- Mahit Mehta

## Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Usage](#usage)
- [Classes](#classes)
- [ClassTesting](#class-testing)
- [Data Storage](#data-storage)
- [Testing](#testing)

---

## Features

- **User Management**: Create, update, retrieve, and delete user profiles.
- **Posts and Comments**: Users can create posts and add comments to interact with one another.
- **File-based Storage**: Data is stored in text files (`users.txt`, `posts.txt`, and `comments.txt`) for persistence.

---

## Project Structure

The main components of the project include:

- **App.java**: The main entry point of the application, initializing and running the backend simulation.
- **Database.java**: Manages reading from and writing to files, acting as a simple file-based database.
- **User, Post, and Comment Classes**: Define the entities in the system, with `UserCollection`, `PostCollection`, and `CommentCollection` managing collections of these entities.

---

### Directory Layout

```plaintext
LeWall/
├── README.md
├── .gitignore
├── eclipse-formatter.xml
├── LeWallServer/
│   ├── pom.xml                 # Maven configuration file
│   └── src/
│       ├── main/
│       │   └── java/com/lewall/
│       │       ├── App.java    # Main application entry point
│       │       ├── db/         # Database and collection classes
│       │           ├── User.java
│       │           ├── BaseCollection.java
│       │           ├── UserCollection.java
│       │           ├── Post.java
│       │           ├── PostCollection.java
│       │           ├── Comment.java
│       │           ├── RwLockArrayList.java
│       │           ├── CommentCollection.java
│       │           └── Database.java
│       └── test/
│           └── java/com/lewall/db
│               ├── CommentTest.java
│               ├── DataBaseTest.java
│               ├── PostTest.java
│               └── UserTest.java
│
└── Data Files
    ├── users.txt               # Stores user data
    ├── posts.txt               # Stores post data
    └── comments.txt            # Stores comment data
```

---

## Setup

### Prerequisites

- **Java**: JDK 23
- **Maven**: For dependency management and building

---

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ShreyAgarwal71/LeWall.git
   cd LeWall
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
- **BaseCollection**: This class is the base class for all the collections in the database. It provides the basic functionality for adding, updating, removing, and finding elements in the collection.
- **Collection**: A Collection interface to help manage our program's database collections. This interface provides methods to read and write data to and from the disk, add, update, and remove elements from the collection, and find elements in the collection that match a given predicate.
- **UserCollection**: A Collection class to manage users in the database of our program. This class is responsible for reading and writing user data to and from the disk. It also provides methods to find users by their username.
- **PostCollection**: A Collection class to manage posts in the database. This class is responsible for reading and writing post data to and from the disk. It also provides methods to find posts by their postID.
- **CommentCollection**: A Collection class to manage comments in the database. This class is responsible for reading and writing comment data to and from the disk. It also provides methods to find comments by their postID.
- **Database**: A Database class to manage the Collection singletons in the database. This class is responsible for reading and writing user, post, and comment data to and from the disk. It also provides methods to get the UserCollection, the PostCollection, and the CommentCollection. The Database class is a singleton class.
- **RwLockArrayList**: A thread-safe ArrayList that uses a ReadWriteLock to manage access to the list. This class is used to store data in the database. It provides methods to add, remove, and get elements from the list. It also provides methods to lock and unlock the read and write locks. This class is used by the database to store user, post, and comment data. This class is a generic class. The type of the elements in the list is specified by the type parameter T.

---

## Class Testing

- **User**:
  - Tests constructor, and all methods located in the User class.
  - Test 1: Tests the user constructor
  - Test 2: Test getUsername
  - Test 3: Test getPassword
  - Test 4: Test getDisplayName
  - Test 5: Test getEmail
  - Test 6: Test setUsername
  - Test 7: Test getPassword
  - Test 8: Test setDisplayName
  - Test 9: Test setEmail
  - Test 10: Test the equals method
    -Test 11: Test the To String
- **Post**:
  - Tests constructor, and all methods located in the Post class
  - Test 1: Tests the post constructor
  - Test 2: Test getMessagePost
  - Test 3: Test getPostUsername
  - Test 4: Test getPostDate
  - Test 5: test getPostID
  - Test 6: Test getPostVotes
  - Test 7: Test getPostImageURL
  - Test 8: Test setPostUsername
  - Test 9: Test setPostUsername
  - Test 10: Test setPostDate
  - Test 11: Test setpostID
  - Test 12: Test setPostIDTODO
  - Test 13: Test setPostVotes
  - Test 14: Test setPostImageURL
  - Test 14 Test testPostEquals
- **Database**:
  - Test 1: Verifies that all posts for a given user are returned
  - Test 2: Verifies that a duplicate post is not added to the collection
  - Test 3: Verifies that a post is removed from the collection
  - Test 4: Verifies that a post is updated in the collection
  - Test 5: Verifies that duplicate comments are not added to the collection
  - Test 6: Verifies that a comment is removed from the collection
  - Test 7: Verifies that a comment is updated in the collection
  - Test 8: Verifies that a user can be found by username
  - Test 9: Verifies that a user is removed from the collection
  - Test 10: Verifies that a user is updated in the collection
- **Comment**:
  - Tests constructor, and all methods located in the Comment class
  - Test 1: Tests the comment construtor
  - Test 2: Test getMessageComment
  - Test 3: Test getCommentUser
  - Test 4: test getCommentDate
  - Test 5: test getCommentID
  - Test 6: test getCommentVotes
  - Test 7: Test getCommentComments
  - Test 8: Test setMessageComment
  - Test 9: Test setCommentUser
  - Test 10 Test setCommentDate
  - Test 11: Test setCommentID
  - Test 12: Test setCommentVotes
  - Test 13: Test commentEquals
- **Multithread Testing**
  - Verifies that multimple threads can write users to the database
  - Verifies that multiple threads can delete users from the database

---

## Usage

1. View application specific `README.md`
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

Tests are located in the `test` directory, this covers the main functionalities of user and post management.

---
