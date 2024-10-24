# Railway Ticket Management System
Railway ticket management system back-end. This architecture can be used in Bangladesh railway to maintain railway schedule and sell the Railway tickets.

## Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Setup](#project-setup)
- [Workflow](#workflow)
- [Future Improvement](#future-improvement)

## Features

- An `Admin User` will perform all the necessary setup. Based on the setup at a specific time of the day, the scheduler
  will run and schedule the train of every route. `User` can search for train-journey for a specific date. Based on the
  ticket availability, `User` can book a Ticket.
- When user confirms the ticket an email with the Ticket is sent to the User's email.

## Technology Stack

This repository is built upon following technologies:

* Java 21
* Spring Boot 3.3.x
* Postgresql
* Kafka

## Project Setup

To build and run the project, follow these steps:

### Repository Cloning

Clone the repository: `git clone https://github.com/mdgiasuddin/railway-ticket-management.git`.

### Setup Database

* Set up <b>Postgresql</b> database for this either by installing Postgresql database manually or by <b>Docker</b>.
  Change the `application.properties` file accordingly.
* Run the project.

### Setup Kafka

* Run the command `docker-compose up -d` or install Kafka manually. For manual installation change the Kafka properties
  based on the installation.

The application will be available at `http://localhost:{{port}}/swagger-ui/index.html`. <br>
Swagger Credential => `(username: 'swagger', password: 'swagger')`

### Run Railway Ticket Notification System

* To run Ticket Notification System clone the notification system by running the command:
  `git clone https://github.com/mdgiasuddin/railway-ticket-notification.git` & follow its instructions to run the
  project.

## Workflow
* Create an `Admin User` at the start of the project by running insert query.

##### With Admin User Access-token

* Create Railway Stations => `POST('/api/stations')`
* Create a Route using 2 Stations of the existing stations => `POST('/api/routes')`
* Create a Train => `POST('/api/trains')`
* Map Train with a Route (UP and DOWN) => `POST('/api/train-routes')`
* Map all the Stations with the Train-Route from which the Train will take passengers =>
  `POST('/api/train-routes/stations')`
* Create Coaches of the Train => `POST('/api/coaches')`
* Create Seats of the Coach => `POST('/api/seats')` <br>
  `Note: Set the upStationMapping & downStationMapping from the station List of Train-Route-Station.`
* Create Fare for all the pairs of Stations for all the available Ticket-class to sell the ticket =>
  `POST('/api/fares')`
* Run scheduler on a suitable time for creating Train-Journey.

<br>

* Register a new User => `POST('/api/auth/register')`

##### With User Access-token

* Search for Ticket => `GET('/api/bookings/search-ticket')`
* Book Ticket => `POST('/api/bookings/book-ticket')`
* Confirm Ticket => `POST('/api/bookings/confirm-ticket')`


## Future Improvement

* Integrate payment-gateway so that user can make payment to purchase the Ticket. Integrate accounting module so the
  company authority can see the report of income, expense and profit.