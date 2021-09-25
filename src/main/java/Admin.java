import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Vector;

class Admin {
	static Vector<Customer> customers = new Vector<Customer>(1);
	static Vector<Account> accounts = new Vector<Account>(1);
	static String[] account_types = {"saving", "checking"};
	
	static Customer register_customer(String phone_no, String name, String address)
	{
		Customer customer = new Customer();
		customer.phone_no = phone_no;
		customer.name = name;
		customer.address = address;
		customers.add(customer);
		return customer;
	}
	
	static Boolean register_account(Customer customer, String account_type)
	{
		if(customer.can_open_acc == false)
			return false;
		
		if(account_type == "saving")
		{
			if(customer.has_saving_acc)
				return false;
			customer.has_saving_acc = true;
		}
		else if(account_type == "checking")
		{
			if(customer.has_checking_acc)
				return false;
			customer.has_checking_acc = true;
		}
		if(customer.has_checking_acc == true && customer.has_saving_acc == true)
		{
			customer.can_open_acc = false;
		}

		Account account = new Account();
		account.account_no = createID();
		account.customer = customer;
		account.date_created = LocalDate.now();
		account.type = account_type;
		accounts.add(account);
		return true;
	}
	
	static Boolean close_account(String account_no)
	{
		int account_idx = get_account_idx(account_no);
		if(account_idx != -1)
		{
			Account current_account = accounts.get(account_idx);
			if(current_account.type == "saving")
				current_account.customer.has_saving_acc = false;
			else
				current_account.customer.has_checking_acc = false;
			current_account.customer.can_open_acc = true;
			accounts.remove(account_idx);
			return true;
		}
		return false;
	}
	
	static int get_customer_idx(String phone_no)
	{
		for (int i = 0; i < customers.size(); i++)
		{
			if(customers.get(i).phone_no.equals(phone_no))
			{
				return i;
			}
		}
		return -1;
	}
	
	static int get_account_idx(String account_no)
	{
		for(int idx = 0; idx < accounts.size(); idx++)
		{
			if(accounts.get(idx).account_no.equals(account_no))
			{
				return idx;
			}
		}
		return -1;
	}
	public static void main(String[] args) throws IOException {
//		Vector<Customer> customers = read_customers();
		Scanner int_scanner = new Scanner(System.in);
		Scanner str_scanner = new Scanner(System.in);
		
		while(true)
		{
			print_menu();
			int option = int_scanner.nextInt();
			if (option == 0)
			{
				break;
			}
			
			switch(option)
			{
			case 1:{
				System.out.println("Enter Phone Number");
				String phone_no = str_scanner.nextLine();
				Customer customer = null;
				int customer_idx = get_customer_idx(phone_no);
				if(customer_idx != -1)
					customer = customers.get(customer_idx);
				
				if(customer == null)
				{
					System.out.println("Enter Name");
					String name = str_scanner.nextLine();
					System.out.println("Enter Address");
					String address = str_scanner.nextLine();
					customer = register_customer(phone_no, name, address);
				}

				System.out.println("Welcome " + customer.name);
				int acc_option = -1;
				while(true)
				{
					System.out.println("\nPlease select an option");
					System.out.println("1: Saving Account");
					System.out.println("2: Checking Account");
					System.out.println("0: Exit");
					acc_option = int_scanner.nextInt();
					if(acc_option == 1 || acc_option == 2 || acc_option == 0)
						break;
					else
						System.out.println("Invalid option");
				}
				if(acc_option == 0)
					break;
				Boolean account_created = register_account(customer, account_types[acc_option - 1]);
				if(account_created == true)
					System.out.println("Account created successfully!");
				else
					System.out.println("Cannot open more accounts!");			
				break;
			}
			case 2:{
				System.out.println("Enter Account number to close");
				String account_no = str_scanner.nextLine();
				Boolean account_removed = close_account(account_no);
				if(account_removed)
					System.out.print("Account: " + account_no + " successfully removed");
				else
					System.out.print("Account: " + account_no + " does not exist!");
				break;
			}
			case 3:{
				System.out.print("Please enter Account number to Log In:\t");
				String login_acc_no= str_scanner.nextLine();
				int account_idx = get_account_idx(login_acc_no);
				
				if(account_idx != -1)
				{
					Account curr_account = accounts.get(account_idx);
					int operation_option = -1;
					while(operation_option != 0)
					{
						print_operations_menu();
						operation_option = int_scanner.nextInt();
						//  Check Balance
						if(operation_option == 1)
						{
							System.out.println("Balance : " + String.valueOf(curr_account.check_balance()));
						}
						//	Print statement	
						else if(operation_option == 2)
						{
							Customer owner = curr_account.customer;
							System.out.println("Name:\t" + owner.name);
							System.out.println("Phone No: \t" + owner.phone_no);
							System.out.println("Address:\t" + owner.address);
							
							System.out.println("Account No:\t" + curr_account.account_no);
							System.out.println("Type:\t" + curr_account.type);
							System.out.println("Created on: \t" + curr_account.date_created);
							System.out.println("Balance :\t" + curr_account.balance);
							
						}
						//	Make Deposit	
						else if(operation_option == 3)
						{
							System.out.print("Enter Amount to deposit:\t");
							double amount = int_scanner.nextDouble();
							curr_account.make_deposit(amount);
							System.out.print("Amount deposited successfully!");
						}
						//	Make Withdrawal
						else if(operation_option == 4)
						{
							System.out.print("Enter Amount to withdraw:\t");
							double amount = int_scanner.nextDouble();
							Boolean success = curr_account.make_withdrawal(amount);
							if(success)
								System.out.print("Amount withdrawn successfully!");
							else
								System.out.print("Insufficient Balance!");
						}
						//	Transfer Amount
						else if(operation_option == 5)
						{
							Account reciever_acc = null;
							System.out.print("Please enter Account number to transfer amount:\t");
							String reciever_acc_no= str_scanner.nextLine();
							
							int reciever_account_idx = get_account_idx(reciever_acc_no);	
							if(reciever_account_idx != -1)
							{
								reciever_acc = accounts.get(reciever_account_idx);
								System.out.print("Enter Amount to transfer:\t");
								double amount = int_scanner.nextDouble();
								Boolean success = curr_account.transfer_amount(amount, reciever_acc);
								if(success)
									System.out.print("Amount transferred successfully!");
								else
									System.out.print("Insufficient Balance!");
							}
							else
								System.out.print("Account does not exist!");
						}
						//	Calculate Zakat
						else if(operation_option == 6)
						{
							double zakat = (curr_account.balance * 2.5) / 100.0;
							System.out.println("Payable Zakat: " + String.valueOf(zakat));
						}
					}
				}
				else
					System.out.println("Account does not exist!");
				break;
			}
			case 4:{
				if(accounts.size() > 0)
				{
					for(int i = 0; i < accounts.size(); i++)
					{
						Account account = accounts.get(i);
						System.out.println(String.valueOf(i) + " : Account");
						System.out.println("Account No:\t" + account.account_no);
						System.out.println("Type:\t" + account.type);
						System.out.println("Created on: \t" + account.date_created);
						System.out.println("Balance :\t" + account.balance);
						
						Customer owner = account.customer;
						System.out.println(String.valueOf(i) + " : Account Owner");
						System.out.println("Name:\t" + owner.name);
						System.out.println("Phone No: \t" + owner.phone_no);
						System.out.println("Address:\t" + owner.address);
					}
				}
				else
					System.out.print("No accounts created!");
				break;
			}
			default:
				System.out.print("Invalid Option\n");
			}
		}
	}
	
	static void print_menu()
	{
		System.out.print("\n\t Please choose an option\n");
		System.out.print("1:\t Open New Account\n");
		System.out.print("2:\t Close Account\n");
		System.out.print("3:\t Log In to Account\n");
		System.out.print("4:\t Display All Accounts\n");
		System.out.print("0:\t Exit\n");
	}
	
	static void print_operations_menu()
	{
		System.out.print("\n\t Please choose an option\n");
		System.out.print("1:\t Check Balance\n");
		System.out.print("2:\t Print Statement\n");
		System.out.print("3:\t Make Deposit\n");
		System.out.print("4:\t Make Withdrawal\n");
		System.out.print("5:\t Transfer Amount\n");
		System.out.print("6:\t Calculate Zakat\n");
		System.out.print("0:\t Exit\n");
	}
	
	private static long idCounter = 1000;

	public static synchronized String createID()
	{
	    return "IBN" + String.valueOf(idCounter++);
	}   
	
//	static Vector<Customer> read_customers() throws IOException
//	{
//		Vector<Customer> customers = new Vector<Customer>(1);
//		
//		BufferedReader reader;
//		try {
//			reader = new BufferedReader(new FileReader("customers.csv"));
//			String line = null;
//			while ((line = reader.readLine()) != null) 
//			{
//				Customer customer = new Customer();
//				String[] tokens = line.split(",");
//				customer.name = tokens[0];
//				customer.phone_no = tokens[1];
//				customer.address = tokens[2];
//				customers.add(customer);
//				System.out.println(tokens[1]);
//			}
//			
//			return customers;	
//		}
//		catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return customers;
//	}

}
