/****** Script for SelectTopNRows command from SSMS  ******/
SELECT TOP (1000) [invoice_no]
      ,[invoice_date]
      ,[cID]
      ,[num_items]
      ,[total_paid]
      ,[balance]
      ,[total_amount]
  FROM [Invoicing_System].[dbo].[Invoice]

  select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.name,Products.unit_price, Invoice_Details.quantity,
  (select Products.unit_price*Invoice_Details.quantity   from Invoice_Details join Products on Invoice_Details.product_id=Products.id   )
  ,Invoice.total_amount,Invoice.total_paid,Invoice.balance
   from Invoice , Invoice_Details , Products where Invoice.invoice_no=Invoice_Details.invoice_no and Invoice_Details.product_id=Products.id;