


CREATE TABLE productlist (
  `productId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `productPrice` int NOT NULL,
  `productInventryCount` int NOT NULL,
  PRIMARY KEY (`productId`)
);

CREATE TABLE purchasehistory_table (
  `id` INT NOT NULL,
  `productId` INT NOT NULL,
  `product` VARCHAR(45) NOT NULL,
  `productPrice` INT NOT NULL,
  `customerInputAmount` INT NOT NULL,
  `vendingMachineBalance` INT NOT NULL,
  `initialBalance` INT NOT NULL,
  PRIMARY KEY (`id`));

