
public class Customer {
	String name = null;
	String address = null;
	String phone_no = null;
	Boolean has_checking_acc = false;
	Boolean has_saving_acc = false;
	Boolean can_open_acc = true;
	
	public Customer(String name, String address, String phone_no)
	{
		this.name = name;
		this.address = address;
		this.phone_no = phone_no;
	}
	public Customer()
	{
		// empty constructor
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Customer))
            return false;
		
		Customer c = (Customer) obj;
		
		Boolean eq = false;
		
		if(
				c.name == name &&
				c.address == address &&
				c.phone_no == phone_no&&
				c.has_checking_acc == has_checking_acc &&
				c.has_saving_acc == has_saving_acc &&
				c.can_open_acc== can_open_acc
		)
			eq = true;
		
		return eq;
	}
}
