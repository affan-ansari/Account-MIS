import static org.junit.Assert.*;
import org.junit.*;

public class AdminTest {
	
	@Test
	public void register_customer() {
		Customer expected_customer = new Customer("Affan Arif", "I-10/1", "03039554355");
		Customer actual_customer = Admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Assert.assertEquals(expected_customer, actual_customer);
	}
	
	@Test
	public void test() {
		Assert.assertEquals(1, Admin.customers.size());
	}

}
