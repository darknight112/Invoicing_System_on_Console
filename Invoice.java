package Invoicing;

import java.util.*;

public class Invoice {
	private int id;
	private int date;
	private float totalPaid;
	private float totalAmount;
	private float totalBalance;
	Scanner sr = new Scanner(System.in);
	Customer c = new Customer();
	ArrayList<Item> purchase = new ArrayList<Item>();


	public void total() {
		for (Item element : purchase) {
			
			float total = element.getQuantity()*element.getPrice();
			this.totalAmount=this.totalAmount+total;
			
			System.out.println("The unit price of "+ element.getName()  + " " +element.getPrice());
			System.out.println("The total price of " + element.getName()  + " " +total);
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
}
