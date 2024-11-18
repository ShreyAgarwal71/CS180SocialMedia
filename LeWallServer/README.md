# LeWall Server

## Features

- **User Management**: Create, update, retrieve, and delete user profiles.
- **Posts and Comments**: Users can create posts and add comments to interact with one another.
- **File-based Storage**: Data is stored in text files (`users.txt`, `posts.txt`, and `comments.txt`) for persistence.
- **Services and Resolvers**: Users can be created and deleted. They can also follow, unfollow, block, unblock, change profile name, create 2 feeds, one for posts from people the user follows and the other feed is all the post in the given class. The user can also create, delete, like, unlike, hide and make private posts. The user can also add, delete, like and unlike comments.

---

## Project Structure

The main components of the project include:

- **App.java**: The main entry point of the application, initializing and running the backend simulation.
- **Database.java**: Manages reading from and writing to files, acting as a simple file-based database.

---

### Directory Layout

```plaintext
├── LeWallServer/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/com/lewall/
│       │       ├── db/
│       │             ├── collections
│       │                 ├── CommentCollection.java
│       │                 ├── PostCollection.java
│       │                 └── UserCollection.java
│       │             └──helpers
│       │                 ├── BaseCollection.java
│       │                 ├── Collection.java
│       │                 └── RwLockArrayList.java
│       │         ├── helpers
│       │           ├── PostSort.java
│       │         ├── interfaces
│       │             ├── IAuthResolver.java
│       │             ├── ICommentResolver.java
│       │             ├── IPostResolver.java
│       │           ├── IRootResolver.java
│       │           └── IUserResolver.java
│       │         ├── resolvers
│       │             ├── AuthResolver.java
│       │             ├── CommentResolver.java
│       │             ├── PostResolver.java
│       │           ├── RootResolver.java
│       │           └── UserResolver.java
│       │         ├── services
│       │             ├── AuthService.java
│       │             ├── CommentService.java
│       │             ├── IService.java
│       │           ├── PostService.java
│       │           └── UserService.java
│       │         ├── AppServer.java
│       │         └──Worker.java
│       │     ├── resources/
│       │         └──log4j2.xml
│       │     ├── test/java/com/lewall/
│       │         ├── db/
│       │             ├── models/
│       │                ├──CommentTest.java
│       │                ├──PostTest.java
│       │                ├──UserTest.java
│       │          ├── models/
│       │              ├──CommentTest.java
│       │              ├──PostTest.java
│       │              ├──UserTest.java
│       │

```

---

## Classes

- **CommentCollection**: A Collection class to manage comments in the database. This class is responsible for reading and writing comment data to and from the disk. It also provides methods to find comments by their postID.
- **PostCollection**: A Collection class to manage posts in the database. This class is responsible for reading and writing post data to and from the disk. It also provides methods to find posts by their postID.
- **UserCollection**: A Collection class to manage users in the database of our program. This class is responsible for reading and writing user data to and from the disk. It also provides methods to find users by their username.

- **BaseCollection**: This class is the base class for all the collections in the database. It provides the basic functionality for adding, updating, removing, and finding elements in the collection.
- **Collection**: A Collection interface to help manage our program's database collections. This interface provides methods to read and write data to and from the disk, add, update, and remove elements from the collection, and find elements in the collection that match a given predicate.
- **RwLockArrayList**: A thread-safe ArrayList that uses a ReadWriteLock to manage access to the list. This class is used to store data in the database. It provides methods to add, remove, and get elements from the list. It also provides methods to lock and unlock the read and write locks. This class is used by the database to store user, post, and comment data. This class is a generic class. The type of the elements in the list is specified by the type parameter T

- **Database**: A Database class to manage the Collection singletons in the database. This class is responsible for reading and writing user, post, and comment data to and from the disk. It also provides methods to get the UserCollection, the PostCollection, and the CommentCollection. The Database class is a singleton class.

- **PostSort**: Quicksort algorith to sort a List of posts by date from newest to oldest

- **IAuthResolver.java**: Interface for AuthResolver
- **ICommentResolver.java**: Interface for CommentResolver
- **IPostResolver.java**: Interface for PostResolver
- **IRootResolver.java**: Interface for RootResolver
- **IUserResolver.java**: Interface for User Resolver

- **AuthResolver.java**: Define the endpoints of the business logic for user authentication
- **CommentResolver.java**: Define the endpoints of the business logic for comments, including add, delete, like and unlike
- **PostResolver.java**: Define the endpoints of the business logic for posts, including create, delete, like, unlike, hide and make private posts
- **RootResolver.java**: Health check resolver
- **UserResolver.java**: Define the endpoints of the business logic for users, including follow, unfollow, block, unblock, change profile name, create 2 feeds, one for posts from people the user follows and the other feed is all the post in the given class

- **AuthService.java**: Defines method to authenticate user
- **CommentService.java**: Defines method to to add, delete, like and unlike
- **IService.java**: Interface for services
- **PostService.java**: Defines method to create, delete, like, unlike, hide and make private posts
- **UserService.java**: Defines method to follow, unfollow, block, unblock, change profile name, create 2 feeds, one for posts from people the user follows and the other feed is all the post in the given class

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

---

## To Run

```bash
mvn exec:java -Dexec.mainClass="com.lewall.AppServer";     
```

---
