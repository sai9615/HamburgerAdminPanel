## **RESTful HAMBURGER ADMIN PANEL**

* Before you run the app make sure you have mongoDB installed on your local system and MongoDB Compass to view the data in DB.
* Can view all the endpoints by simply running the app and searching for the URL mentioned below.
* **URL:** http://localhost:8080/swagger-ui.html

###App Details
* **Entity (package com.example.HamburgerAdminPanel.Entity):** contains location, menu and reservation entities, used to define how every collection(data) is structured. 
* **Repository (package com.example.HamburgerAdminPanel.Repository):** contains location, menu and reservation repositories used to encapsulate the mechanism of storing, retrieving and searching collection of objects from a database(mongoDB).
* **Service Layer (package com.example.HamburgerAdminPanel.Service):** contains logic for searching, storing, deleting and updating data in different collections(menu, location, reservation).
* **Controller Layer (package com.example.HamburgerAdminPanel.Controller):** Application has location, menu, reservation RESTful web controller which maps the different HTTP requests to service methods.

###Api End Points
**LOCATION**

 * **GET:** Http://localhost:8080/api/location/{ID} : find location by id.
 * **GET:** Http://localhost:8080/api/location : retrieve all locations from Location collection in DB.
 * **POST:** Http://localhost:8080/api/location : store a list of new locations to DB.
 * **PUT:** Http://localhost:8080/api/location/{ID} : update a location of the given ID.
 * **DELETE:** Http://localhost:8080/api/location/{ID} : delete a location of the given ID.
 * **DELETE:** Http://localhost:8080/api/location : delete all locations.

**MENU**

* **GET:** Http://localhost:8080/api/menu/{menuId} : find menu by id.
* **GET:** Http://localhost:8080/api/menu : retrieve all menu items from Menu collection in DB.
* **GET:** Http://localhost:8080/api/menu/menuType : retrieve all menu items of a given menu Type.
* **GET:** Http://localhost:8080/api/menu/menuItem : retrieve a menu item based on item name.  
* **POST:** Http://localhost:8080/api/menu : store a list of new menu items to DB.
* **PUT:** Http://localhost:8080/api/menu/menuItem/{ID} : update a menu item of the given ID.
* **DELETE:** Http://localhost:8080/api/menu/{ID} : delete a menu object of the given ID.
* **DELETE:** Http://localhost:8080/api/menu : delete all menu items.

**RESERVATIONS**

* **GET:** Http://localhost:8080/api/reservations/{ID} : find reservation by id.
* **GET:** Http://localhost:8080/api/reservations : retrieve all reservations from reservation collection in DB.
* **GET:** Http://localhost:8080/api/reservations/getByName : find reservation by first or last name of person.
* **POST:** Http://localhost:8080/api/reservations : store a list of new reservations to DB.
* **PUT:** Http://localhost:8080/api/reservations/{ID} : update a reservation of the given ID.
* **DELETE:** Http://localhost:8080/api/reservations/{ID} : delete a reservation of the given ID.
* **DELETE:** Http://localhost:8080/api/reservations : delete all reservations.