package Invoicing;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;

public class Invoice {
	private int id;
	private int date;
	private float totalPaid;
	private float totalAmount;
	private float totalBalance;
	Scanner sr = new Scanner(System.in);
	Customer c = new Customer();
	ArrayList<Item> purchase = new ArrayList<Item>();

	String url = "jdbc:sqlserver://localhost:1433;" +
            "databaseName=Invoicing_System;" +
            "encrypt=true;" +
            "trustServerCertificate=true";
    String user = "sa";
    String pass = "root";

	 Connection con = null;

	public void total() {
		
		 try {
				Driver driver = (Driver) Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
				DriverManager.registerDriver(driver);
				          con = DriverManager.getConnection(url, user, pass);
				Statement st = con.createStatement();
		
		for (Item element : purchase) {
			
			float total = element.getQuantity()*element.getPrice();
			this.totalAmount=this.totalAmount+total;
			
			System.out.println("The unit price of "+ element.getName()  + " " +element.getPrice());
			System.out.println("The total price of " + element.getName()  + " " +total);
			


		      
		String sql1 = "INSERT INTO [dbo].[Invoice_Items]\r\n"
				+ "           ([invoice_id]\r\n"
				+ "           ,[product_id]\r\n"
				+ "           ,[quantity]\r\n"
				+ "           ,[price])\r\n"
				+ "     VALUES\r\n("
				+ "           (<invoice_id, int,>\r\n"
				+ "           ,<product_id, int,>\r\n"
				+ "           ,<quantity, int,>\r\n"
				+ "           ,<price, decimal(10,2),>)";
				
		Integer m = st.executeUpdate(sql1); //sql execution
		          if (m >= 1) {
		System.out.println("inserted successfully : " + sql1);
		} else {
		System.out.println("insertion failed");
		}
		ResultSet resultSet = st.executeQuery(sql1);

		          con.close();
		} 
		}
		 
		 catch (Exception ex) {
				System.err.println(ex);
				}
		System.out.println("The total amount is "+ this.totalAmount);
		System.out.println("Enter the the payment ");
		float pay = sr.nextFloat();
		this.totalPaid=pay;
		this.totalBalance = this.totalAmount - this.totalPaid;
		System.out.println("The total paid is "+ this.totalPaid);
		System.out.println("The total balance is "+ this.totalBalance);


	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public float getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(float totalPaid) {
		this.totalPaid = totalPaid;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}

	public void setDate(LocalDate now) {
		// TODO Auto-generated method stub
		
	}
}
