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
		Scanner s = new Scanner(System.in);
		System.out.println("==== Invoice System Login ====");
		System.out.println("Enter user name");
		String user1 = s.next();
		System.out.println("Enter password");
		String pass1 = s.next();

		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing_System;" + "encrypt=true;"
				+ "trustServerCertificate=true";
		String user = user1; // sa
		String pass = pass1; // root

		Connection con = null;

		try {
			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			DriverManager.registerDriver(driver);
			con = DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

		System.out.println("==== System Main Menu ====");

		Menu menu = new Menu();
		menu.showMenu();

	}

	public void loadCustomers() {
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

			String sql1 = " SELECT *" + "  FROM [dbo].[Customers]";

			Customer c = new Customer();

			ResultSet resultSet1 = st.executeQuery(sql1);
			while (resultSet1.next()) {
				c.setId(resultSet1.getInt("id")-1);
				c.setName(resultSet1.getString("name"));
				c.setPhone(resultSet1.getInt("phone"));
				shop.customer.add(c);

			}

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}
	}

	void loadItems() {
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
			Item item = new Item();
			String sql2 = "SELECT *" + "  FROM [dbo].[Products]";
			System.out.println("=== Product List ===");
			ResultSet result = st.executeQuery(sql2);
			while (result.next()) {
				int id = result.getInt("id")-1;
				String name = result.getString("name");
				Float unit_price = result.getFloat("unit_price");
				int quantity = result.getInt("quantity");
				System.out.println("Product ID = " + id);
				item.setId(id);
				System.out.println("Product Name = " + name);
				item.setName(name);
				System.out.println("Product Price = " + unit_price);
				item.setPrice(unit_price);
				System.out.println("Product Quantity = " + quantity);
				item.setQuantity(quantity);

				shop.item.add(item);
			}

			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

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
					int switch1=sr.nextInt();
					switch (switch1) {
					case 1:
						System.out.println("=== Items List ===");
						loadItems();
						loadCustomers();

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
							// System.out.println("Please Enter the Invoice ID:");
							// int invoiceID = sr.nextInt();
						
							System.out.println("=== Invoice List ===");

							String sql3 = "select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.id,Products.name,Products.unit_price, Invoice_Details.quantity,\r\n"
									+ "									Invoice_Details.quantity_price\r\n"
									+ "								,Invoice.total_amount,Invoice.total_paid,Invoice.balance\r\n"
									+ "								from Invoice inner join Invoice_Details on Invoice.invoice_no=Invoice_Details.invoice_no\r\n"
									+ "								inner join Products on  Invoice_Details.product_id=Products.id;";
							Statement st = con.createStatement();

							ResultSet resultSet3 = st.executeQuery(sql3);
							while (resultSet3.next()) {
								Invoice invoice = new Invoice();

									Item item1 = new Item();
									Customer customer = new Customer();
									int no = resultSet3.getInt("invoice_no")-1;
									String date = resultSet3.getString("invoice_date");
									invoice.setId(no);
									invoice.setDate(date);
									int cID = resultSet3.getInt("cID")-1;
									
									customer=shop.customer.get(cID);
									
									invoice.c.setId(customer.getId());
									invoice.c.setName(customer.getName());
									invoice.c.setPhone(customer.getPhone());

									int itemID = resultSet3.getInt("id")-1;
									String pName = resultSet3.getString("name");
									int quantity = resultSet3.getInt("quantity");
									float uPrice = resultSet3.getFloat("unit_price");
									float p = resultSet3.getFloat("quantity_price");

									for(Item item : shop.item) {
										
										if(itemID==item.getId()) {
											item1=item;
											item1.setQuantity(quantity);
										}
									}


									
										invoice.purchase.add(item1);
										

									
									

									float total_amount = resultSet3.getFloat("total_amount");
									float total_paid = resultSet3.getFloat("total_paid");
									float balance = resultSet3.getFloat("balance");

									invoice.setTotalAmount(total_amount);
									invoice.setTotalPaid(total_paid);
									invoice.setTotalPaid(total_paid);
									invoice.setTotalBalance(balance);
									shop.invoiceList.add(invoice);
									shop.setTotalSales(shop.getTotalSales()+total_amount);
									
									printInvoice();

//									System.out.println(no + "\t" + date + "\t" + cID + "\t" + itemID + "\t\t"
//											+ pName + "\t\t" + uPrice + "\t\t" + quantity + "\t\t" + p + "\t\t"
//											+ total_amount + "\t\t" + total_paid + "\t\t" + balance);
//									System.out.println(
//											"--------------------------------------------------------------------------------------------------------------------------------");

								}

							con.close();
						} catch (Exception ex) {
							System.err.println(ex);
						}
						// try {
//							BufferedReader read = new BufferedReader(new FileReader("item.txt")); // Creation of
//
//							// object
//							Scanner scan = new Scanner(read);
//							String scaner;
//							ArrayList<String> arr = new ArrayList<String>();
//
//							int count = 0;
//							while ((scaner = read.readLine()) != null) // Reading Content from the file and insert it to
//																		// the array
//							{
//								arr.add(scaner);
//								count++;
//								System.out.println(scaner);
//							}
//							for (int i = 0; i < arr.size(); i++) {
//								Item t = new Item();
//								Integer tempId = Integer.parseInt(arr.get(i));
//								t.setId(tempId);
//								Float price = Float.parseFloat(arr.get(i + 1));
//								t.setPrice(price);
//								t.setName(arr.get(i + 2));
//								shop.item.add(t);
//								System.out.println(i);
//
//							}
//
//							scan.close();
//
//						} catch (IOException except) {
//							except.printStackTrace();
//						}

						// printInvoice();

						break;
					case 2:
						System.out.println("Enter the new name of the shop ");
						String sName=sr.next();
						shop.setName(sName);
						System.out.println("The shop name changed to " + sName);

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
				int id=sr.nextInt();
				int count=0;
				for(Invoice invoice : shop.invoiceList) {
					
					if(invoice.getId()==id) {
						temp = shop.invoiceList.get(count);
					}
					count++;
				}
				
				
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
			System.out.println(" Customer Name: " + element.c.getName() + " Customer Phone: " + element.c.getPhone());
			for(Item item : element.purchase) {
				System.out.println(" Item Name: " + item.getName() + " Item unit price: " + item.getPrice());
				System.out.println(" Quantity: " + item.getQuantity() + " Item price: " + (item.getPrice()*item.getQuantity()));

			}
			System.out.println(" total amount : " + element.getTotalAmount());
			System.out.println(" total paid : " + element.getTotalPaid());
			System.out.println(" total balance : " + element.getTotalBalance());

			

		}

//		String url = "jdbc:sqlserver://localhost:1433;" + "databaseName=Invoicing_System;" + "encrypt=true;"
//				+ "trustServerCertificate=true";
//		String user = "sa";
//		String pass = "root";
//
//		Connection con = null;
//
//		try {
//			Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
//			DriverManager.registerDriver(driver);
//			con = DriverManager.getConnection(url, user, pass);
//			Statement st = con.createStatement();
//			String sql1 = " select Invoice.invoice_no,Invoice.invoice_date,Invoice.cID,Products.name,Products.unit_price, Invoice_Details.quantity,\r\n"
//					+ "  (select Products.unit_price*Invoice_Details.quantity   from Invoice_Details join Products on Invoice_Details.product_id=Products.id   )\r\n"
//					+ "  ,Invoice.total_amount,Invoice.total_paid,Invoice.balance\r\n"
//					+ "   from Invoice , Invoice_Details , Products where Invoice.invoice_no=Invoice_Details.invoice_no and Invoice_Details.product_id=Products.id;";
//			Invoice invoice = new Invoice();
//			invoice.setTotalBalance(totalSales);
//			ResultSet resultSet = st.executeQuery(sql1);
//			while (resultSet.next()) {
//				System.out.println("invoice number = " + resultSet.getString("invoice_no"));
//				System.out.println("Date = " + resultSet.getString("invoice_date"));
//				System.out.println("Customer ID = " + resultSet.getString("cID"));
//				System.out.println("Product name = " + resultSet.getString("name"));
//				System.out.println("unit price = " + resultSet.getString("unit_price"));
//				System.out.println("quantity = " + resultSet.getString("quantity"));
//				System.out.println("Price = " + resultSet.getString(""));
//				System.out.println("Total Amount = " + resultSet.getString("total_amount"));
//				System.out.println("Total Paid = " + resultSet.getString("total_paid"));
//				System.out.println("Total Balance = " + resultSet.getString("balance"));
//
//			}
//			con.close();
//		} catch (Exception ex) {
//			System.err.println(ex);
//		}
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
				invoice.c = shop.customer.get(input);

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
				System.out.println("Enter the ID of customer ");
				input = sr.nextInt();

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

							String sql1 = "SELECT [name]\r\n" + "      ,[unit_price]\r\n"
									+ "      ,[quantity]\r\n" + "  FROM [dbo].[Products] where id=" + input + ";";
							ResultSet resultSet = st.executeQuery(sql1);
							String name = null;
							int id = input;
							float price = 0;
							int stockQ;
							while (resultSet.next()) {
								name = resultSet.getString("name");
								price = resultSet.getFloat("unit_price");
								stockQ = resultSet.getInt("quantity");
							}
							System.out.println("Enter the quantity of " + name + " you want to purchase");
							int quant = (sr.nextInt());
							Item i = new Item();
							i.setId(id);
							i.setName(name);
							i.setPrice(price);
							i.setQuantity(quant);
							invoice.purchase.add(i);
							
							invoice.total();
							totalSales = totalSales + invoice.getTotalAmount();
							shop.setTotalSales(totalSales);
							shop.invoiceList.add(invoice);

							String sql2 = "INSERT INTO [dbo].[Invoice]\r\n" + "           ([invoice_date]\r\n"
									+ "           ,[cID]\r\n" + "           ,[num_items]\r\n"
									+ "           ,[total_paid]\r\n" + "           ,[balance],[total_amount])\r\n"
									+ "     VALUES('" + java.time.LocalDate.now() + "' , " + invoice.c.getId() + " ,"
									+ invoice.purchase.size() + "," + invoice.getTotalPaid() + ","
									+ invoice.getTotalBalance() + "," + invoice.getTotalAmount() + ");";
							
							String getID="SELECT TOP (1) invoice_no from [dbo].[Invoice] order by invoice_no desc;";
							ResultSet result = st.executeQuery(getID);
							int invoice_No=0;
							while (result.next()) {
								invoice_No= result.getInt("invoice_no");
							}
							 

							String sql = "INSERT INTO [dbo].[Invoice_Details]\r\n" + "           ([invoice_no]\r\n"
									+ "           ,[product_id]\r\n" + "           ,[quantity]\r\n"
									+ "           ,[quantity_price])\r\n" + "     VALUES(" + invoice_No + ","
									+ i.getId() + "," + i.getQuantity() + "," + i.getPrice() * i.getQuantity() + ");";
							st.executeUpdate(sql2);
							st.executeUpdate(sql);
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
