SELECT        Sales.SalesOrderHeader.SalesOrderID, Sales.SalesOrderDetail.SalesOrderID AS Expr1, Sales.SalesOrderDetail.SalesOrderDetailID, Sales.SalesOrderDetail.ProductID, Sales.SalesOrderDetail.SpecialOfferID, 
                         Sales.SalesOrderHeader.CustomerID, Sales.SalesOrderHeader.SalesPersonID, Sales.SalesOrderHeader.TerritoryID, Sales.SalesOrderHeader.BillToAddressID, Sales.SalesOrderHeader.ShipToAddressID, 
                         Sales.SalesOrderHeader.ShipMethodID, Sales.SalesOrderHeader.CreditCardID
FROM            Sales.SalesOrderDetail INNER JOIN
                         Sales.SalesOrderHeader ON Sales.SalesOrderDetail.SalesOrderID = Sales.SalesOrderHeader.SalesOrderID