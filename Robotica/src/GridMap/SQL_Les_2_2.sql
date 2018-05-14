SELECT        Sales.SalesOrderDetail.SalesOrderID, Sales.SalesOrderDetail.SalesOrderDetailID, Sales.SalesOrderHeader.SalesOrderID AS Expr1
FROM            Sales.SalesOrderDetail INNER JOIN
                         Sales.SalesOrderHeader ON Sales.SalesOrderDetail.SalesOrderID = Sales.SalesOrderHeader.SalesOrderID