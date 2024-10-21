# BTrip - Packaged Travel Arrangements Platform

BTrip stands for Best Trip or Belgium Trip. Here you can book hotels and flights and rent cars.
Our target customers are young people who want to travel to europian countries and price sensitive.
The flights provider is Ryanair, the hotels provider is Booking.com, and the car rental provider is Europcar.

![BTrip Logo](./frontend/public/assets/logos/logo512.png)

## Project Overview

BTrip is a web application developed for the Distributed Systems (B-KUL-T4YDS2) 2023-2024 course. The platform serves as a broker for packaged travel arrangements, including hotel bookings, car rentals, and flight reservations. This service-oriented application is hosted on a cloud platform and integrates various remote services.

### Key Features

- **Packaged Bookings**: Users can book travel packages that include hotels, flights, and rental cars.
- **Service Integration**: Connects with multiple external supplier services via RESTful or SOAP APIs.
- **Cloud Deployment**: Utilizes Google App Engine to ensure scalability and high availability.
- **Fault Tolerance**: Designed to handle failures in external service interactions robustly.
- **Data Persistence**: Uses Cloud Firestore to manage booking data consistently and reliably.
- **User Authentication**: Secure user management using Firebase Authentication.

## System Architecture

This multi-tiered system includes:

- **Frontend**: A React-based Single Page Application (SPA) providing a user-friendly interface.
- **Backend**: A Java/Spring Boot application that processes API requests and handles business logic.
- **External Supplier Services**: Separate services for hotels, flights, and car rentals hosted on independent VMs.

## Installation and Setup

### Prerequisites

- Java SE Development Kit (JDK) 17
- Maven
- Google Cloud SDK
- Firebase CLI
- Node.js

## Development Log

### Frontend

reference: [Connections](https://www.connections.be/en/tours/Iceland/magical-iceland)
content: Wentai Ye will provide the Itinerary and Photos according to his personal experience.

- 24/05/2024: Choose React as the frontend framework and created a new project using `create-react-app`.
- 25/05/2024: Implemented the basic layout of the application with dummy data. Including the home page, footer, header, and navigation bar.
- 26/05/2024: Add a login page and destination pages.
- 27/05/2024: Add a travelling details page and a booking page.
- 28/05/2024: FIX:The layout of the product page needs to be improved, and the content is blocked by the header.
- 30/05/2024: Improve the layout of the product page and add more details.
- 31/05/2024: Add Map component to the product page.
- 11/06/2024: Update the login page and product page. Fix images cannot be loaded correctly except for the home page.
- 12/06/2024: Update the pricing section of the product page.
- 13/06/2024: Refactor the code for easier maintenance.

TODO:

- FIX: After clcking the marker on the map, the map should not refresh.
- ADD: Add a administator page to manage the orders.

### Backend

- 27/05/2024: Create Hotel, Flight, and CarRental classes and controllers.
- 28/05/2024: Test the external supplier services.

TODO:

- ADD: travel agency order management
- ADD: use ryanair-py 3.0.0 to get flight information
- ADD: calculate the total price of the order with the price of the hotel, flight, car rental, tax, and service fee

