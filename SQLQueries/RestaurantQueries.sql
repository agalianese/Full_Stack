--These code challenges have been taken from LinkedIn Learnings SQL Code Challenges. More can be found at https://www.linkedin.com/learning/sql-code-challenges/sql-code-challenges?u=26113546
 

 /*These code challenges are restaurant themed. For the restaurant's five year anniversary, they are throwing an anniversary party. These SQL queries will reflect
 that. */
 
--Create invitations for a party by generating a list of customer info - FirstName, LastName, Email, and sorted alphabetically by last name
	
SELECT FirstName, LastName, Email FROM Customers
ORDER BY LastName;

--Create a table to store customer information of customerid and party size

CREATE TABLE AnniversaryAttendees(
	CustomerID INT,
	PartySize INT
);

--Create three menus - one sorted lowest to highest, then one with just appetizers and beverages by type, finally a menu of all items except beverages by type

SELECT * FROM Dishes
ORDER BY PRICE;

SELECT * FROM Dishes 
WHERE Type IS "Beverage" OR Type IS "Appetizer"
ORDER BY Type;

SELECT * FROM Dishes 
WHERE Type IS NOT "Beverage" 
ORDER BY Type;

--Add a customer's information into the customer DATABASE
INSERT INTO Customers(FirstName, LastName, Email, Address, City, State, Phone, Birthday)
VALUES("Anna", "Smith", "asmith@kinetecoinc.con", "479 Lapis Dr.", "Memphis", "TN", "(555) 555-1212", "1973-07-21");


--Update a customer's information who has moved 
UPDATE Customers 
SET Address = "74 Pine St.", City = "New York", State = "NY"
WHERE FirstName="Taylor" AND LastName="Jenkins" AND State="DC";


--Remove a customer from the DATABASE
DELETE FROM Customers WHERE Email = "tjenkins@rouxacademy.org";

--Using the customer's email address, find their id and enter their party size into the AnniversaryAttendees table
SELECT CustomerID, FirstName FROM Customers WHERE Email = "atapley2j@kinetecoinc.com";
INSERT INTO AnniversaryAttendees(CustomerID, PartySize)
VALUES (92, 4);


--look up a customer's reservation whose last name is something similar to stevenson, but we're not sure of exact spelling
SELECT Customers.FirstName, Customers.LastName, Reservations.Date, Reservations.PartySize FROM Customers
LEFT JOIN Reservations ON Customers.CustomerID = Reservations.CustomerID
WHERE Customers.LastName LIKE "Ste%";



--Create a Reservation

--check if that customer is already in the database
SELECT * FROM Customers WHERE Email IS "smac@rouxacademy.com"; 

--he's not in the database so he must be added
INSERT INTO Customers(FirstName, LastName, Email, Phone)
VALUES ("Sam", "McAdams", "smac@rouxacademy.com", "(555) 555-1212");

--now create a reservation for july 14th 2020 at 6PM for 5 people
INSERT INTO Reservations(CustomerID, Date, PartySize)
VALUES ((SELECT CustomerID FROM Customers WHERE Email IS "smac@rouxacademy.com"), "2020-07-14 18:00:00", 5);


--Process a delivery order for Loretta Hundey at 6939 Elka Place for a house salad, mini cheeseburgers, and a tropical blue smoothie
--first find loretta's CustomerID
SELECT CustomerID FROM Customers WHERE Address = "6939 Elka Place";

--we get a customer id of 70

--now lets the dish ids of the foods she's ordering
SELECT * FROM Dishes WHERE name = "House Salad" OR name = "Mini Cheeseburgers" OR name = "Tropical Blue Smoothie";

--create an order and get that OrderID
INSERT INTO Orders(CustomerID, OrderDate)
VALUES (70, "2020-03-20 14:00:00");
SELECT OrderID FROM Orders WHERE CustomerID = 70 AND OrderDate = "2020-03-20 14:00:00";

--insert into OrderDishes the dishes for that ORDER
INSERT INTO OrdersDishes(OrderID, DishID)
VALUES (1001, 4),
		(1001, 7),
		(1001, 20);

--check that the orders joined correctly
SELECT OrdersDishes.OrderID, Dishes.DishID, Dishes.Name, Dishes.Price FROM Dishes
LEFT JOIN OrdersDishes ON Dishes.DishID = OrdersDishes.DishID
WHERE OrderID = 1001;

--calculate price
SELECT SUM(Price) FROM Dishes
LEFT JOIN OrdersDishes ON Dishes.DishID = OrdersDishes.DishID
WHERE OrderID = 1001;


--Set cleo goldwater's FavoriteDish to the quinoa salmon salad
--get the quinoa salmon salad's id
SELECT DishID FROM Dishes WHERE name = "Quinoa Salmon Salad";

--we get a dishId of 9, so we will now find cleo's customer profile
SELECT * FROM Customers WHERE FirstName = "Cleo" AND LastName = "Goldwater";

--her CustomerID is 42 so we will now update her profile
UPDATE Customers 
SET FavoriteDish = 9
WHERE FirstName="Cleo" AND LastName="Goldwater";

--find the top 5 customers with the most to go Orders

SELECT COUNT(Customers.CustomerID), Customers.FirstName, Customers.LastName, Customers.Email FROM Customers
LEFT JOIN Orders ON Customers.CustomerID = Orders.CustomerID
GROUP BY Customers.CustomerID
ORDER BY COUNT(Customers.CustomerID) DESC
LIMIT 5;