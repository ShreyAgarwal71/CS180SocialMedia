# LeWall Project

## Overview

LeWall is a modern, Java-based, social media application. The platform is a digital wall-writing app, essentially users will be able to join classes they are currently enrolled in and view posts that previous students have made. Plase 1 contains for basic user management, posting, and commenting, with data persistence through the use of a file storing system. This project is built with Maven and includes Java classes for users, posts and comments.

## Table of Contents

- [Instructions to Compile and Run](#Instructions-to-Compile-and-Run)
- [Contributors](#Contributors)
- [Project Structure](#Project-Structure)
- [Setup](#setup)

## Instructions to Compile and Run

1. **Ensure prerequisites are installed**:
   - Java JDK 23 or higher.
   - Apache Maven.
2. **Compile the entire project**:
   - Navigate to the root directory and run `mvn clean install`.
3. **Run the application**:
   - Start the server:
     - Navigate to the `LeWallServer` README.md
   - Start the app:
     - Navigate to the `LeWallApp` README.md

## Contributors

- **Shrey Agarwal**: Submitted report on Brightspace
- **Mahit Mehta**
- **Ates Isfendiyaroglu**
- **Christian Slade**
- **Zayan Niaz**

## Project Structure

### [LeWallTypes](./LeWallTypes/README.md)

- Shared data types and utilities used across the app and server.
- Core functionality includes:
  - Type definitions for ensuring data consistency.
  - Utility functions for data manipulation and validation.

### [LeWallApp](./LeWallApp/README.md)

- Frontend user interface for interacting with the application.
- Key features include:
  - User-friendly interface for creating and managing data.
  - Visual representation of server responses and data.

### [LeWallServer](./LeWallServer/README.md)

- Backend server that processes data and handles requests.
- Main tasks include:
  - Handling API endpoints.
  - Interfacing with the database and business logic.

--

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

### Docker 

Run the LeWall Server in a docker container
- `docker compose up --build -d`

Run the LeWall Server in a docker container for traefik
- `docker compose -f "docker-compose-traefik.yml" up`

View server logs
- `docker logs -f lewall_server`