# LeWallApp

## Overview

The LeWallApp is a JavaFX-based frontend that provides the user interface for students to interact with the server. It enables users to log in, create posts, view profiles, and interact with other users on the platform. The app communicates with the server through RESTful API calls to access and manage data.

## Features

Built using JavaFX, the frontend features multiple pages that allow users to interact with the app. Key pages include: - **Login Page**: Allows users to authenticate and access the app. - **Home Page**: Displays posts from users the logged-in student follows. - **Profile Page**: Displays users profile and allows the user to follow, unfollow, block and unblock other users - **New Post Page**: Allows users to create and submit new posts related to classes. - **Register Page**: Allows users to create new accounts for the app. - **Search Page**: Allows users to search for users and classes in the app.

---

### Prerequisites

- **Java**: JDK 23
- **Maven**: For dependency management and building
- Run `mvn clean install` before in the root directory before attempting to run the client application

---

## Usage

> Note: Must have JDK 23 installed.

> cd into LeWallApp/ before running this command

### Local Server
- `mvn clean compile javafx:run`

### Remote Server (Linux/MacOS)
- `REMOTE=1 mvn clean compile javafx:run`
### Remote Server (Windows)
- `set REMOTE=1`
- `mvn clean compile javafx:run`

---

## Classes

### Components:

The pages are built using components such as NavBar, ClassCard, UserCard, PostItem and footers. For example the search page finds results and displays them all as either ClassCard components or PostCard components. Every page has the NavBar and uses it to navigate between pages. The Footer is consistent on all the pages.

### App:

Sets up the main method

### Navigation:

Navigator.java manages navigation between pages within the application. It provides a stack-based navigation model, enabling users to go back and forth between pages as they interact with the app. After logging in, users are directed to the home feed, where they can access other pages like their profile, class feeds, or create new posts.

### Local Storage:

Class to manage client-side storage in the application. This class is used to store data locally on the user's machine. The data is stored in a HashMap which is then serialized and saved to disk. The data can be accessed and modified using the set, get, and remove methods. The data is saved to disk when the application is closed and is loaded when the application is opened. The data is saved to disk every 5 seconds in case the application crashes. The data is saved in a file called state.ser in the user's home directory.

### Connection:

Static class to manage client-server communication in the application. This class is used to send requests to the server and receive responses. The requests are sent using a non-blocking socket channel. The responses are received using a selector. The responses are parsed using the Gson library. The responses are then returned to the caller as a CompletableFuture. The connection is established when the application is opened and is closed when the application is closed. The connection is re-established if it is lost.

### CSS and Styling:

global.css is used to style the application’s UI components. JavaFX uses CSS to control the layout and appearance of the elements, including fonts, colors, margins, padding, and transitions. Interactive elements like buttons have hover and click effects to improve the user experience, making it intuitive and visually appealing. The app has set fonts, images, and a theme

### HTTP Requests:

The app sends HTTP requests to the backend using RESTful API calls. For example:
POST /login for logging in.
GET /posts for retrieving posts to display on the home feed.
POST /posts for creating new posts.
The app handles the responses from the server and updates the UI accordingly, whether it's displaying user data or showing a list of posts.

### Error Handling:

Errors encountered during API calls are handled gracefully. The app displays appropriate error messages or prompts to the user, ensuring they understand what went wrong (e.g., "Invalid login credentials" or "Failed to load posts").

## Testing
