CREATE TABLE Customers (
    id INT NOT NULL PRIMARY KEY ,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE Products (
    id INT NOT NULL PRIMARY KEY ,
    name VARCHAR(255) NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    qty_amount DECIMAL(10,2) NOT NULL
);

CREATE TABLE Invoice (
    invoice_no INT NOT NULL PRIMARY KEY ,
    invoice_date DATE NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    num_items INT NOT NULL,
    total_paid DECIMAL(10,2) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    balance DECIMAL(10,2) NOT NULL
);


CREATE TABLE Invoice_Items (
    id INT NOT NULL PRIMARY KEY ,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_no),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);
