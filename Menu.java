package Invoicing;

import java.io.BufferedReader;
import java.sql.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Menu {

	transient Scanner sr = new Scanner(System.in);
	Shop shop = new Shop();
	float totalSales = 0;

	public static void main(String[] args) throws Exception {
		System.out.println("==== System Main Menu ====");

		Menu menu = new Menu();
		menu.showMenu();

	}

	public void showMenu() {
		boolean condition = true;

		while (condition) {
			System.out.println("1- Shop Settings"); // finish
			System.out.println("2- Manage Shop Items"); // finish
			System.out.println("3- Create New Invoice"); // finish
			System.out.println("4- Report: Statistics (No Of Items, No of Invoices, Total Sales)"); // finish
			System.out.println("5- Print All Invoices"); // finish
			System.out.println("6- Search Invoice (Search by Invoice No and Report All Invoice details with items)"); // finish
			System.out.println("7- Program Statistics (Print each Main Menu Item with how many number selected)");
			System.out.println("8- Exit");
			int input = sr.nextInt();
			switch (input) {
			case 1:
				boolean select1 = true;
				while (select1) {
					System.out.println("1. Load Data (Items and invoices)"); // finish
					System.out.println("2. Set Shop Name"); // finish
					System.out.println("3. Set Invoice Header (Tel / Fax / Email / Website)"); // finish
					System.out.println("4. Go Back"); // finish

					switch (sr.nextInt()) {
					case 1:
						System.out.println("=== Items List ===");
						try {
							BufferedReader read = new BufferedReader(new FileReader("item.txt")); // Creation of

							// object
							Scanner scan = new Scanner(read);
							String scaner;
							ArrayList<String> arr = new ArrayList<String>();

							int count = 0;
							while ((scaner = read.readLine()) != null) // Reading Content from the file and insert it to
																		// the array
							{
								arr.add(scaner);
								count++;
								System.out.println(scaner);
							}
							for (int i = 0; i < arr.size(); i++) {
								Item t = new Item();
								Integer tempId = Integer.parseInt(arr.get(i));
								t.setId(tempId);
								Float price = Float.parseFloat(arr.get(i + 1));
								t.setPrice(price);
								t.setName(arr.get(i + 2));
								shop.item.add(t);
								System.out.println(i);

							}

							scan.close();

						} catch (IOException except) {
							except.printStackTrace();
						}

						// printInvoice();

						break;
					case 2:
						System.out.println("Enter the new name of the shop ");
						shop.setName(sr.next());
						System.out.println("The shop name changed to " + shop.getName());

						break;
					case 3:
						System.out.println("Enter the phone of the shop ");
						shop.setTel(sr.nextInt());
						System.out.println("Enter the fax of the shop ");
						shop.setFax(sr.nextInt());
						System.out.println("Enter the Email of the shop ");
						shop.setEmail(sr.next());
						System.out.println("Enter the website of the shop ");
						shop.setWebsite(sr.next());
						header();
						System.out.println("the changes has been saved  ");

						break;
					case 4:
						select1 = false;
						break;
					}

				}
				break;

			case 2:
				boolean select = true;

				System.out.println(" 1 create new item");
				System.out.println(" 2 change the price of item");
				System.out.println(" 3 remove item");
				System.out.println(" 4 Go back");
				while (select) {
					input = sr.nextInt();
					switch (input) {
					case 1:
						shop.addItem();

						break;
					case 2:
						shop.changePrice();

						break;
					case 3:
						shop.deleteItem();

						break;
					case 4:
						select = false;
						break;
					}
				}
				break;

			case 3: // 3- Create New Invoice
				createInvoice();
				break;
			case 4: // 4- Report: Statistics (No Of Items, No of Invoices, Total Sales)
				System.out.println(" No Of Items " + shop.item.size());
				System.out.println(" No of Invoices " + shop.invoiceList.size());
				System.out.println(" Total Sales " + shop.getTotalSales());

				break;

			case 5: // 5- Print All Invoices
				printInvoice();

				break;

			case 6: // 6- Search Invoice (Search by Invoice No and Report All Invoice details with
					// items)
				System.out.println("Enter the invoice ID ");
				Invoice temp = new Invoice();
				temp = shop.invoiceList.get(sr.nextInt());
				System.out.println("Invoice ID " + temp.getId() + " Invoice Date " + temp.getDate());
				System.out.println("Shop ID " + shop.getId() + " Shop Name " + shop.getName());
				System.out.println(" Customer Name " + temp.c.getName() + " Customer Phone " + temp.c.getPhone());
				for (Item item : temp.purchase) {
					System.out.println(" Item Name " + item.getName() + " Item Unit Price " + item.getPrice()
							+ " Item Quantity " + item.getQuantity() + " Item total price "
							+ (item.getPrice() * item.getQuantity()));
				}
				System.out.println("The total amount is " + temp.getTotalAmount());
				System.out.println("The total paid is " + temp.getTotalPaid());
				System.out.println("The total balance is " + temp.getTotalBalance());

				break;

			case 7: // 7- Program Statistics (Print each Main Menu Item with how many number
					// selected)

				break;

			case 8: // exit
				condition = false;
				System.out.println("exit");
				break;
			}
		}

	}

	public void printInvoice() {
		System.out.println("=== Inoivce List ===");
		for (Invoice element : shop.invoiceList) {
			System.out.println("Invoice ID " + element.getId() + " Invoice Date " + element.getDate());
			System.out.println("Shop ID " + shop.getId() + " Shop Name " + shop.getName());
			System.out.println(" Customer Name " + element.c.getName() + " Customer Phone " + element.c.getPhone());
			element.total();

		}
		
		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing_System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = "sa";
		String pass = "root";

		Connection con = null;

		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();
			String sql1 =" select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.name,Products.unit_price, Invoice_Details.quantity,\r\n"
					+ "  (select Products.unit_price*Invoice_Details.quantity   from Invoice_Details join Products on Invoice_Details.product_id=Products.id   )\r\n"
					+ "  ,Invoice.total_amount,Invoice.total_paid,Invoice.balance\r\n"
					+ "   from Invoice , Invoice_Details , Products where Invoice.invoice_no=Invoice_Details.invoice_no and Invoice_Details.product_id=Products.id;" ;

			ResultSet resultSet = st.executeQuery(sql1);
			while (resultSet.next()) {
				System.out.println("invoice number = " + resultSet.getString("invoice_no"));
				System.out.println("Date = " + resultSet.getString("invoice_date"));
				System.out.println("Customer ID = " + resultSet.getString("cID"));
				System.out.println("Product name = " + resultSet.getString("name"));
				System.out.println("unit price = " + resultSet.getString("unit_price"));
				System.out.println("quantity = " + resultSet.getString("quantity"));
				System.out.println("Price = " + resultSet.getString(""));
				System.out.println("Total Amount = " + resultSet.getString("total_amount"));
				System.out.println("Total Paid = " + resultSet.getString("total_paid"));
				System.out.println("Total Balance = " + resultSet.getString("balance"));

			}
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	public void header() {
		try {
			// Initializing BufferedWriter
			BufferedWriter itemWriter = new BufferedWriter(new FileWriter("header.txt"));
			itemWriter.write(shop.getName() + "\n");
			itemWriter.write(shop.getTel() + "\n");
			itemWriter.write(shop.getFax() + "\n");
			itemWriter.write(shop.getEmail() + "\n");
			itemWriter.write(shop.getWebsite() + "\n");
			itemWriter.close();
		} catch (IOException except) {
			except.printStackTrace();
		}
	}

	public void saveInvoice() {
		try {
			// Initializing BufferedWriter
			BufferedWriter itemWriter = new BufferedWriter(new FileWriter("invoice.txt"));

			for (Invoice x : shop.invoiceList) {
				itemWriter.write(x.getId() + "\n");
				itemWriter.write(x.getDate() + "\n");
				header();
				itemWriter.write(x.c.getName() + "\n");
				itemWriter.write(x.c.getPhone() + "\n");
				for (Item item : x.purchase) {
					itemWriter.write(item.getName() + "\n");
					itemWriter.write(item.getPrice() + "\n");
					itemWriter.write(item.getQuantity() + "\n");
					itemWriter.write((item.getQuantity() * item.getPrice() + "\n"));

				}
				itemWriter.write(x.getTotalAmount() + "\n");
				itemWriter.write(x.getTotalPaid() + "\n");
				itemWriter.write(x.getTotalBalance() + "\n");

			}

			itemWriter.close();
			System.out.println("The invoice now saved ");
		} catch (IOException except) {
			except.printStackTrace();
		}

	}

	public void createInvoice() {
		Invoice invoice = new Invoice();
		invoice.setId(shop.invoiceList.size());
		invoice.setDate(java.time.LocalDate.now());
		
		boolean condition = true;
		while (condition) {
			System.out.println("1 Enter the ID of customer who purchase");
			System.out.println("2 to show the customer");
			System.out.println("3 add new customer");
			System.out.println("4 exit ");
			int input = sr.nextInt();
			switch (input) {
			case 1:
				System.out.println("Enter the ID of customer who purchase ");
				invoice.c = shop.customer.get(sr.nextInt());

				break;
			case 2:
				shop.printCustomers();
				break;
			case 3:
				shop.addCustomer();
				invoice.c = shop.customer.get(shop.customer.size());
				break;
			case 4:
				condition = false;
				System.out.println("exit");
				break;
			}
			boolean condition2 = true;
			while (condition2) {
				System.out.println("1 Enter the ID of item you want to purchase ");
				System.out.println("press 2 to show the items ");
				System.out.println("press 3 to exit ");
				input = sr.nextInt();
				switch (input) {
				case 1:
					boolean newItem = true;
					while (newItem) {
						System.out.println("Enter the ID of item you want to purchase / 99 to exit ");
						input = sr.nextInt();
						if (input == 99) {
							condition = false;
							System.out.println("exit");
						}
						
						String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing_System;"
								+ "encrypt=true;" + "trustServerCertificate=true";
						String user = "sa";
						String pass = "root";

						Connection con = null;

						try {
							Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
									.newInstance();
							DriverManager.registerDriver(driver);
							con = DriverManager.getConnection(url, user, pass);
							Statement st = con.createStatement();
						
						
						
						String sql1 ="SELECT [id]\r\n"
								+ "      ,[name]\r\n"
								+ "      ,[unit_price]\r\n"
								+ "      ,[quantity]\r\n"
								+ "  FROM [dbo].[Products] where id="+ input +";";
						ResultSet resultSet = st.executeQuery(sql1);
						while (resultSet.next()) {
							System.out.println("Enter the quantity of " + resultSet.getString("name") + " you want to purchase");
							System.out.println("Name = " + resultSet.getString("name"));
							System.out.println("Unit Price = " + resultSet.getString("unit_price"));
							System.out.println("Quantity = " + resultSet.getString("quantity"));
						}
						
						
						i = shop.item.get(sr.nextInt());
						System.out.println("Enter the quantity of " + i.getName() + " you want to purchase");
						i.setQuantity(sr.nextInt());
						invoice.purchase.add(i);
						invoice.total();
						totalSales = totalSales + invoice.getTotalAmount();
						shop.setTotalSales(totalSales);
						shop.invoiceList.add(invoice);
						
							String sql2 = "INSERT INTO [dbo].[Invoice]\r\n"
									+ "           ([invoice_date]\r\n"
									+ "           ,[cID]\r\n"
									+ "           ,[num_items]\r\n"
									+ "           ,[total_paid]\r\n"
									+ "           ,[balance],[total_amount])\r\n"
									+ "     VALUES('" + java.time.LocalDate.now()
									+ "' , " + invoice.c.getId() + " ," + invoice.purchase.size() + ","
									+ invoice.getTotalPaid() + "," + invoice.getTotalBalance() + ","
									+ invoice.getTotalAmount() + ");"
											+ "INSERT INTO [dbo].[Invoice_Details]\r\n"
											+ "           ([invoice_no]\r\n"
											+ "           ,[product_id]\r\n"
											+ "           ,[quantity]\r\n"
											+ "           ,[quantity_price])\r\n"
											+ "     VALUES("+invoice.c.getId()+","+i.getId()+","+i.getQuantity()+","+i.getPrice()*i.getQuantity()+");";

							Integer m = st.executeUpdate(sql1); // sql execution
							if (m >= 1) {
								System.out.println("inserted successfully : " + sql1);
							} else {
								System.out.println("insertion failed");
							}
							

							con.close();
						} catch (Exception ex) {
							System.err.println(ex);
						}
						saveInvoice();
					}
					break;

				case 2:
					shop.printItems();
					break;

				case 3:
					condition2 = false;
					System.out.println("exit");
					break;
				}

			}
		}
	}
}
