USE [Invoicing_System]
GO

INSERT INTO [dbo].[Invoice]
           ([invoice_date]
           ,[cID]
           ,[num_items]
           ,[total_paid]
           ,[balance]
           ,[total_amount])
     VALUES
           ('11/02/2022',1,5,1,0,1);
GO


