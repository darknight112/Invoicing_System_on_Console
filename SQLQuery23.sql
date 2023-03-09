USE [Invoicing_System]
GO

INSERT INTO [dbo].[Invoice]
           ([invoice_date]
           ,[cID]
           ,[num_items]
           ,[total_paid]
           ,[total_amount]
           ,[balance])
     VALUES
           (<invoice_date, date,>
           ,<cID, int,>
           ,<num_items, int,>
           ,<total_paid, decimal(10,2),>
           ,<total_amount, decimal(10,2),>
           ,<balance, decimal(10,2),>)
GO


ALTER TABLE dbo.Invoice ADD [total_amount] decimal(10,2);
GO

ALTER TABLE dbo.Invoice_Details DROP COLUMN quantity_price;

ALTER TABLE dbo.Invoice_Details ADD quantity_price decimal(10,2);
