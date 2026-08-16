The Railway Reservation System based on Java and it designed to manage railway related information like trains, stations, users, bookings and seat availability.
Our project demonstrates the practical use of data structures and algorithms in real-world railway reservation system. We used a few different data structures to help store and sort the information effectively.
The whole system was built by using Java and it follows an object oriented approach. The primary model classes inside the project are Train, Booking, Station, User and Ticket

FEATURES
--------
* Admin Operations: Manage trains, stations, and view all bookings.
* User Operations: Search for trains, book tickets, view booking history, and manage personal accounts.
* Core Application: The entry point that orchestrates user and admin flows.


DATA STRUCTURES USED


* Hash Table (UserHashTable): Efficient storage and retrieval of user accounts.
* Graph (StationRouteGraph): Re
presents the railway network, stations, and connections.
* Binary Search Tree (TrainBST): Organizes train data for fast searching and retrieval.
* AVL Tree (BookingAVLTree): Keeps bookings sorted and balanced for optimal access time.
* Linked List (TrainLinkedList): Manages dynamic lists of trains.
* Stack (BookingStack): Useful for tracking recent booking operations or history.
* Queue (BookingWaitingListQueue): Handles waitlisted bookings fairly (First-In, First-Out).
* Array/Set (StationArray, BookedSeatSet): Fixed-size collections for stations and seat tracking.
* Sorting Algorithms (TrainSorter): Organizes trains based on various criteria (e.g., departure time, name).


PROJECT STRUCTURE
* src/app/       : Contains user interaction, user menu and admin menu
* src/model/     : Data models (User, Train, Station, Booking, Ticket).
* src/service/   : Business logic and system state integration (RailwayReservationSystem).
* src/structure/ : Custom implementations of fundamental data structures.


GETTING STARTED

Prerequisites:
- Java Development Kit (JDK) 8 or higher.
- IntelliJ IDEA (recommended) or any standard Java IDE.

Running the Application:
1. Open the project folder in your Java IDE.
2. Ensure the 'src' folder is marked as the Sources Root.
3. Locate 'src/app/Main.java'.
4. Run the 'Main' class to start the interactive console application.
