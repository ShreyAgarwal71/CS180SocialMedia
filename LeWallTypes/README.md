# Overview

This private dependency is shared between the LeWall Server and App project for the common DTOs (Data Transfer Objects). These DTOs contain the body's structure (data), which is being transferred between the client and server and vice versa. 

Additionally, there are other helper classes such as `Request`, `Response`, and various custom-defined errors that can be thrown by the server. These errors include `InternalServerError` when an unexplained error occurs and `BadRequest` when the user sends a request with invalid data. 

There is also a special `Validation` class that contains `static` methods for verifying the format of user data such as `emails` and `passwords`.

After Phase 2, we decided to move the `Database Models` into this shared library so that both the app and server have access to the `User`, `Post`, and `Comment` models.

There is no further functionality aside from shared classes, this project can not be ran standalone.

# Compile

> Note: Must have JDK 23 installed and Maven (just like the rest of the project)
- `mvn clean compile`
- `mvn clean install`
