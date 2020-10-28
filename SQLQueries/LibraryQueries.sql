/*These queries now deal with a library system. */

--Find how many copies of Dracula that are available

--count the number of draculas and subtract the number of draculas that are checked out
SELECT (SELECT COUNT(Books.BookID) FROM Books WHERE Title = "Dracula")	- (SELECT Count(Books.BookID) FROM Loans JOIN Books ON Loans.BookID = Books.BookID WHERE Loans.ReturnedDate IS NULL AND Books.Title = "Dracula")
AS draculaInStock;


--add new books to the books database
INSERT INTO Books(Title, Author, Published, Barcode)
VALUES ("Dracula", "Bram Stoker", 1897, 4819277482),
("Gulliver's Travels", "Jonathan Swift", 1729, 4899254401);


--check out picture of dorian gray and great expectations to jack vaan

INSERT INTO Loans(PatronID, BookID, LoanDate, DueDate)
VALUES ((SELECT PatronID FROM Patrons WHERE FirstName = "Jack" AND LastName = "Vaan"), 
(SELECT BookID FROM BOOKS WHERE Barcode = 2855934983),
 "2020-08-25", "2020-09-08"),

 ((SELECT PatronID FROM Patrons WHERE FirstName = "Jack" AND LastName = "Vaan"), 
(SELECT BookID FROM BOOKS WHERE Barcode = 4043822646),
 "2020-08-25", "2020-09-08");
 
 
 --check for books due back 2020-07-13
SELECT Loans.DueDate, Books.Title, Patrons.FirstName, Patrons.LastName, Patrons.Email FROM LOANS 
LEFT JOIN Patrons ON Loans.PatronID = Patrons.PatronID
INNER JOIN Books ON Loans.BookID = Books.BookID
WHERE Loans.DueDate = "2020-07-13" AND ReturnedDate IS NULL;


--return books with barcodes 6435968624, 5677520613, 8730298424 to the library
UPDATE Loans SET ReturnedDate = "2020-07-05"
WHERE (Loans.BookID = (SELECT BookID FROM Books WHERE Barcode = 6435968624) 
OR Loans.BookID = (SELECT BookID FROM Books WHERE Barcode = 5677520613) 
OR Loans.BookID = (SELECT BookID FROM Books WHERE Barcode = 8730298424))
AND ReturnedDate IS NULL;


--find the 10 patrons who have checked out the least amount of Books
SELECT COUNT(LoanID) AS LoanCount, Patrons.FirstName, Patrons.LastName, Patrons.Email FROM Loans 
LEFT JOIN Patrons ON Loans.PatronID = Patrons.PatronID
GROUP BY Patrons.PatronID
ORDER BY LoanCount 
LIMIT 10;


--SELECT books for an exhibit about the 1890's
SELECT * FROM Books 
JOIN Loans ON Books.BookID = Loans.BookID
WHERE Books.Published >= 1890 AND Books.Published < 1900 AND ReturnedDate IS NOT NULL
GROUP BY Books.BookID;


--create reports - one showing how many books were published each year and  one of the five most popular books to check out

--for every year, how many were published in that year
SELECT Published, COUNT(DISTINCT(TITLE)) AS yearCount FROM Books
GROUP BY Published
ORDER BY yearCount DESC;


--find which books have been loaned out the most
SELECT Books.Title, COUNT(Loans.BookID) AS CheckedOut FROM Loans
LEFT JOIN Books ON Loans.BookID = Books.BookID
GROUP BY Books.Title
ORDER BY CheckedOut DESC
LIMIT 5;