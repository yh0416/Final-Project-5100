/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  kiara
 * Created: Nov 27, 2024
 */

CREATE TABLE users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullName VARCHAR(200) NOT NULL,
    emailAddress VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    userRole VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products(
    id INT AUTO_INCREMENT PRIMARY KEY,
    productName VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    price DOUBLE,
    stock INT DEFAULT 0,
    image_url VARCHAR(255),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customerName VARCHAR(100) NOT NULL,
    customerPhone VARCHAR(20) NOT NULL,
    customerAddress VARCHAR(255) NOT NULL,
    deliveryFee DOUBLE NOT NULL,
    deliveryStatus VARCHAR(50),
    deliveryResponse VARCHAR(255), 
    deliveryNotes VARCHAR(255),
    expectedDeliveryTime INT,
    storeAddress VARCHAR(255),
    assignedTo VARCHAR(100), 
    createdBy VARCHAR(100),
    createdAt DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items(
    id INT AUTO_INCREMENT PRIMARY KEY,
    orderId INT,
    productName VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (orderId) REFERENCES orders(id)
);


CREATE TABLE delivery_timeline (
    id INT AUTO_INCREMENT PRIMARY KEY,
    deliveryId INT NOT NULL,
    status VARCHAR(50) NOT NULL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    notes VARCHAR(255) DEFAULT NULL,
    FOREIGN KEY (deliveryId) REFERENCES orders(id)
);