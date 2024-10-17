# Railway Ticket Management System
Railway ticket management system back-end. This architecture can be used in Bangladesh railway to maintain railway schedule and sell the Railway tickets.

## Table of Contents
- [Technology Stack](#technology-stack)
- [Project Setup](#project-setup)
- [Features](#features)
- [Workflow](#workflow)
- [Future Improvement](#future-improvement)

## Technology Stack

This repository is built upon following technologies:

* Spring Boot 3.3.x
* Postgresql
* JDK 21

## Project Setup

To build and run the project, follow these steps:

### Repository Cloning

Clone the repository: `git clone https://github.com/mdgiasuddin/railway-ticket-management.git`.

### Set up Database

* Set up <b>Postgresql</b> database for this either by installing Postgresql database manually or by <b>Docker</b>.
  Change the `application.properties` file accordingly.
* Run the project.

The application will be available at `http://localhost:{{port}}/swagger-ui/index.html`. <br>
Swagger Credential => `(username: 'swagger', password: 'swagger')`

## Features
- Needs to update

## Workflow

* Create an `Admin User` at the start of the project by running insert query.

### With Admin User Credential

* Create Railway Stations => `POST('/api/stations')`
* Create a Route using 2 Stations of the existing stations => `POST('/api/routes')`
* Create a Train => `POST('/api/trains')`
* Map Train with a Route (UP and DOWN) => `POST('/api/train-routes')`
* Map all the Stations with the Train-Route on which the Train will take passengers =>
  `POST('/api/train-routes/stations')`
* Create Coaches of the Train => `POST('/api/coaches')`
* Create Seats of the Coach => `POST('/api/seats')` <br>
  `Note: Set the upStationMapping & downStationMapping from the station List of Train-Route-Station.`
* Create Fare for all the pairs of Stations for all the available Ticket-class to sell the ticket =>
  `POST('/api/fares')`
* Run scheduler on a suitable time for creating Train-Journey.

* Register a new User => `POST('/api/auth/register')`

### With User Credential

* Search for Ticket => `GET('/api/bookings/search-ticket')`
* Book Ticket => `POST('/api/bookings/book-ticket')`
* Confirm Ticket => `POST('/api/bookings/confirm-ticket')`


## Future Improvement
* Integrate accounting module so the company authority can see the report of income, expense and profit.