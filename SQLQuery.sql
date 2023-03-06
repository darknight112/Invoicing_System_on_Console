CREATE TABLE Customers (
    id INT NOT NULL PRIMARY KEY identity,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone INT NOT NULL,
);

CREATE TABLE Products (
    id INT NOT NULL PRIMARY KEY identity,
    name VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
);

CREATE TABLE Invoice (
    invoice_no INT NOT NULL PRIMARY KEY identity,
    invoice_date DATE NOT NULL,
    cID INT NOT NULL,	
    num_items INT NOT NULL,
    total_paid DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL
    FOREIGN KEY (cID) REFERENCES Customers (id)

);


CREATE TABLE Invoice_Details (
	id INT NOT NULL PRIMARY KEY identity,
    invoice_no INT NOT NULL ,
    product_id INT NOT NULL ,
    quantity INT NOT NULL,
    quantity_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (invoice_no) REFERENCES Invoice(invoice_no),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);
