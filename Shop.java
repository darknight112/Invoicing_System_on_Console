package Invoicing;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;

public class Shop {
	private int id;
	private String name;
	private int tel;
	private int fax;
	private String email;
	private String website;
	private float totalSales;
	ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
	ArrayList<Item> item =  new ArrayList<Item>();
	ArrayList<Customer> customer = new ArrayList<Customer>();
	transient Scanner sr = new Scanner(System.in);

	public void saveItem() {
		try {

			// Initializing BufferedWriter
			BufferedWriter itemWriter = new BufferedWriter(new FileWriter("item.txt"));

			System.out.println("Buffered Writer start writing ");

			for (Item x : item) {
				itemWriter.write(x.getId() + "\n");
				itemWriter.write(x.getPrice() + "\n");
				itemWriter.write(x.getName() + "\n");

			}

			itemWriter.close();
			System.out.println("The new items are saved ");
		} catch (IOException except) {
			except.printStackTrace();
		}

	}

	public void addItem() {
		boolean condition = true;
		while (condition) {
			Item temItem = new Item();

			temItem.setId(item.size());
			
			System.out.println("enter item name ");
			String inputS = sr.next();
			temItem.setName(inputS);
			System.out.println("enter item price ");
			temItem.setPrice(sr.nextInt());
			System.out.println("enter item quantity ");
			temItem.setQuantity(sr.nextInt());
			item.add(temItem);

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
				String sql1 = "INSERT INTO [dbo].[Products]\r\n"
						+ "           ([name]\r\n"
						+ "           ,[unit_price]\r\n"
						+ "           ,[quantity])\r\n"
						+ "     VALUES( '" + temItem.getName() + "' ,"
						+ temItem.getPrice() + "," + temItem.getQuantity() + ");";

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

			System.out.println("Item created ... ");
			System.out.println("enter 1 to continue/ 2 to exit ... ");
			int input = sr.nextInt();
			saveItem();
			if (input == 2) {
				condition = false;
				System.out.println("exit ... ");

			}

		}
	}

	public void deleteItem() {
		System.out.println("enter item ID ");
		int input = sr.nextInt();

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
			String sql1 = "DELETE FROM [Products] WHERE id=" + input + ";";

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

		System.out.println("item deleted ");
		saveItem();

	}

	public void changePrice() {
		System.out.println("enter item ID ");
		int input = sr.nextInt();
		Item tem = new Item();
		tem = item.get(input);
		System.out.println("enter item " + tem.getName() + " new price ");
		input = sr.nextInt();
		tem.setPrice(input);
		System.out.println("item price changed ");
		item.set(input, tem);

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
			String sql1 = "UPDATE [Products]\r\n" + "   SET [unit_price] =" + tem.getPrice() + " WHERE id= "
					+ tem.getId();

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

		saveItem();
	}

	public void printItems() {
		System.out.println("items list ");
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
			String sql1 = "SELECT * FROM [Products]";

			ResultSet resultSet = st.executeQuery(sql1);
			while (resultSet.next()) {
				System.out.println("ID = " + resultSet.getString("id"));
				System.out.println("Name = " + resultSet.getString("name"));
				System.out.println("Unit Price = " + resultSet.getString("unit_price"));
				System.out.println("Quantity = " + resultSet.getString("quantity"));

			}
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

	public void addCustomer() {
		boolean condition = true;
		while (condition) {
			Customer temCustomer = new Customer();

			temCustomer.setId(item.size());
			System.out.println("enter customer phone number ");
			temCustomer.setPhone(sr.nextInt());
			System.out.println("enter customer name ");
			String inputS = sr.next();
			temCustomer.setName(inputS);
			customer.add(temCustomer);

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
				String sql1 = "INSERT INTO Customers\r\n" + "           ([name]\r\n" + "           ,[phone])\r\n"
						+ "VALUES ( '" + temCustomer.getName() + "' ," + temCustomer.getPhone() + ")";

				Integer m = st.executeUpdate(sql1); // sql execution
				if (m >= 1) {
					System.out.println("inserted successfully : " + sql1);
				} else {
					System.out.println("insertion failed");
				}

				System.out.println("Customer added ... ");
				System.out.println("enter 1 to continue/ 2 to exit ... ");
				int input = sr.nextInt();
				if (input == 2) {
					condition = false;
					System.out.println("exit ... ");

				}
				con.close();
			} catch (Exception ex) {
				System.err.println(ex);
			}
		}

	}

	public void printCustomers() {
		System.out.println("List of Customers ");

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
			String sql1 = "SELECT * FROM [Customers]";

			ResultSet resultSet = st.executeQuery(sql1);
			while (resultSet.next()) {
				System.out.println("ID = " + resultSet.getString("id"));
				System.out.println("Name = " + resultSet.getString("name"));

				System.out.println("Phone = " + resultSet.getString("phone"));
			}
			con.close();
		} catch (Exception ex) {
			System.err.println(ex);
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTel() {
		return tel;
	}

	public void setTel(int tel) {
		this.tel = tel;
	}

	public int getFax() {
		return fax;
	}

	public void setFax(int fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public ArrayList<Customer> getCustomer() {
		return customer;
	}

	public float getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(float totalSales) {
		this.totalSales = totalSales;
	}

}
