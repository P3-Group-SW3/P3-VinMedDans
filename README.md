# Project Name

[[License](https://github.com/P3-Group-SW3/P3-VinMedDans/blob/main/LICENSE)]


## DISCLAIMER READ ME FIRST
- All pushes to the production(Stable) branch should be STABLE versions of the software. It must pass all tests before a pull request for it to be made. 
- The pre-production branch is allowed to have failing unit tests, although they should be solved within a relatively short time frame or generally avoided
- Three reviews are required for something to be pushed to main branch

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
- [Setup](#Setup)
- [Backend Setup](#backend-setup)
- [Frontend Setup](#Frontend-setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project is developed as an 3. semester project at Aalborg University. The project is developed in collaboration with the company "Vin Med Dans" and is a web shop. The main goal of the project is to develop a larger (then previous semesters) program in a structured way using OOAD.

## Features

- Buy/Sell products
- Manage Stock
- Educate yourself about fruit wine

## Technologies

### Backend:
- **Java** 
- **Spring Boot** 
  - Spring Web
  - Spring Security
  - Spring Data JPA
- **Database**: [MariaDB]
- **Build Tool**: [Gradle]

### Frontend:
- **React.js**
  - React Router
- **JavaScript (ES6+)**
- **CSS** Bootstrap

## Getting Started

### Prerequisites
  - Java 23
  - Node.js 16.x
  - npm 7.x
  - MySQL 8.x

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/P3-Group-SW3/P3-VinMedDans/
    ```
2. Navigate to the project directory:
    ```bash
    cd <Repo Directory>
    ```


## Setup

  ### Frontend Setup
  1. Go to the project root directory:
      ```bash
      cd your-repo-dir
      ```
  2. Install dependencies:
      ```bash
      ./gradlew clean build
      ```
  3. Configure the database:
     - Update the `application.properties` file with your database credentials.
  4. Run the Spring Boot application:
      ```bash
      ./gradlew bootRun
      ```

  ### Frontend Setup
  1. Navigate to the React project directory:
      ```bash
      cd path/to/repo/frontend
      ```
  2. Build the React application:
      ```bash
      npm run build
      ```
  3. Copy the build output to the Spring Boot `static` directory:
      ```bash
      cp -r build/* path/to/repo/src/main/resources/static/
      ```

## API Endpoints


## Contributing

If you'd like to contribute, please fork the repository and use a feature branch. Pull requests are warmly welcome.

1. Fork the repository
2. Create a new branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request


## License

This project is licensed under the "GNU General Public License v3.0" License - see the [LICENSE](LICENSE) file for details.

