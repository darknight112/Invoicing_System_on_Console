package Invoicing;

import java.util.*;
import java.io.*;

public class Shop {
	private int id;
	private String name;
	private int tel;
	private int fax;
	private String email;
	private String website;
	private float totalSales;
	ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();
	ArrayList<Item> item = new ArrayList<Item>();
	ArrayList<Customer> customer = new ArrayList<Customer>();
	transient Scanner sr = new Scanner(System.in);

	
	
	public void saveItem() {
		try {

			// Initializing BufferedWriter
			BufferedWriter itemWriter = new BufferedWriter(new FileWriter("item.txt"));

			System.out.println("Buffered Writer start writing ");

			for (Item x : item) {
				itemWriter.write(x.getId()+"\n" );
				itemWriter.write(x.getPrice()+ "\n");
				itemWriter.write(x.getName()+"\n");

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
			System.out.println("enter item price ");
			temItem.setPrice(sr.nextInt());
			System.out.println("enter item name ");
			String inputS = sr.next();
			temItem.setName(inputS);
			item.add(temItem);
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
		item.get(input);
		item.remove(input);
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
		saveItem();
	}

	public void printItems() {
		System.out.println("items list ");
		for (Item element : item) {
			System.out.println("Item ID " + element.getId() + " Item name " + element.getName() + " Item Price "
					+ element.getPrice());

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
			System.out.println("Customer added ... ");
			System.out.println("enter 1 to continue/ 2 to exit ... ");
			int input = sr.nextInt();
			if (input == 2) {
				condition = false;
				System.out.println("exit ... ");

			}

		}
	}

	public void printCustomers() {
		System.out.println("List of Customers ");
		for (Customer element : customer) {
			System.out.println("Customer ID " + element.getId() + " Customer name " + element.getName()
					+ " Customer Phone " + element.getPhone());

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
