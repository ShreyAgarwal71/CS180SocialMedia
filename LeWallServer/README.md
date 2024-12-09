# LeWall Server

## Overview

The LeWallServer handles the business logic, user authentication, and all database interactions for the application. The server receives requests from the client-side and processes them accordingly, ensuring data is retrieved, updated, or deleted as needed. It is built with Java and Maven as the dependency management tool.

---

### Prerequisites

- **Java**: JDK 23
- **Maven**: For dependency management and building
- Run `mvn clean install` before in the root directory before attempting to run the server application

---

## To Run

> Note: cd into LeWallServer/ before running this command

```bash
mvn clean compile exec:java
```

---

## Features

- **User Management**: Create, update, retrieve, and delete user profiles.
- **Posts and Comments**: Users can create posts and add comments to interact with one another.
- **File-based Storage**: Data is stored in text files (`users.txt`, `posts.txt`, and `comments.txt`) for persistence.
- **Services and Resolvers**: Users can be created and deleted. They can also follow, unfollow, block, unblock, change profile name, create 2 feeds, one for posts from people the user follows and the other feed is all the post in the given class. The user can also create, delete, like, unlike, hide and make private posts. The user can also add, delete, like and unlike comments.

---

## Project Structure

The main components of the project include:

- **AppServer.java**: The main entry point of the application, is initializing and running the backend simulation.
- **Worker.java:** Instance of a server worker that can handle multiple clients with the usage of `java.nio` channels
- **Database.java**: Manages reading from and writing to files, acting as a simple file-based database.
- **ResolverTools.java**: Maps requests from the socket output to the functions in the resolvers and defines crucial annotations such as `Endpoint` and `Resolver`.

---

## Classes

### Resolvers:

The backend uses six main resolver classes, each responsible for handling specific types of data. These resolvers act as interfaces between the client and the business logic:

AuthResolver: Manages requests related to user authentication and authorization (e.g., logging in, registering).

UserResolver: Handles user-specific data like profile management, following/unfollowing, and searching users.

PostResolver: Manages posts—creating, deleting, liking, disliking, hiding, and fetching posts.
CommentResolver: Handles operations on comments, including creation, deletion, liking, and fetching comments related to posts.

Also manages interactions such as liking/unliking posts or comments, following/unfollowing users, and blocking/unblocking other users.

### Services:

Each resolver works in conjunction with services that contain the core logic of the application. These services implement the business logic for managing users, posts, comments, and interactions.

The UserService, PostService, and CommentService handle operations like creating, deleting, and updating posts and comments, as well as implementing actions such as following, unfollowing, and blocking users.

These services interact with the models (objects like User, Post, Comment, etc.) to manipulate and persist data. For example, the PostService will create a new post object, persist it, and return a response to the client side.

### DTOs (Data Transfer Objects):

DTOs are used to send and receive data between the client and server. These objects contain only the necessary data required for a specific operation and prevent unnecessary data transfer, optimizing network performance.

Examples of DTOs include AuthTokenDTO, UserDTO, PostDTO, and CommentDTO. These objects ensure that the client receives only the required fields, such as post content or user details, and not the entire user object, which may contain sensitive information like passwords.

### API Endpoints:

The server exposes a set of RESTful API endpoints for communication with the client-side application. These endpoints handle CRUD operations for posts and comments, as well as user management (e.g., logging in, registering, and fetching user data).

### Example endpoints might include:

    POST /login for authentication.
    GET /posts/{userId} for fetching posts from a specific user.
    POST /posts for creating new posts.
    DELETE /posts/{postId} for deleting a post.

The server uses HTTP status codes to indicate the success or failure of requests, such as 200 OK, 404 Not Found, or 500 Internal Server Error.

### Exception Handling:

The server also includes custom exceptions to handle errors, ensuring that users receive appropriate error messages when something goes wrong (e.g., when a post is not found or when a user attempts an unauthorized action).

Custom exception handling allows the server to respond with helpful messages, reducing ambiguity and improving the user experience.

---

## Class Testing

- **CommentTest**:
- **PostTest**:
- **UserTest**:

- **DatabaseTest**:

- **AuthResolverTest**:

- **AuthServiceTest**:
- **UserServiceTest**:

- **Multithread Testing**

---

## Testing

To run unit tests for this project:

```bash
mvn test
```

Tests are located in the `test` directory, this covers the main functionalities of user and post management.
