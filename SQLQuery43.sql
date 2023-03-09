/****** Script for SelectTopNRows command from SSMS  ******/
select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.id,Products.name,Products.unit_price, Invoice_Details.quantity,
									 (select    Products.unit_price*Invoice_Details.quantity    from Invoice_Details join Products   on Invoice_Details.product_id=Products.id   ) as total_quantity
									  ,Invoice.total_amount,Invoice.total_paid,Invoice.balance
									from Invoice , Invoice_Details , Products where Invoice.invoice_no=Invoice_Details.invoice_no and Invoice_Details.product_id=Products.id;




									select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.id,Products.name,Products.unit_price, Invoice_Details.quantity,
									Invoice_Details.quantity_price
								,Invoice.total_amount,Invoice.total_paid,Invoice.balance
								from Invoice inner join Invoice_Details on Invoice.invoice_no=Invoice_Details.invoice_no
								inner join Products on  Invoice_Details.product_id=Products.id;


								SELECT TOP (1) invoice_no from [dbo].[Invoice] order by invoice_no desc;