import static org.junit.Assert.*;
import org.junit.*;

public class AdminTest {
	private Admin admin;
	
	@Before
	public void setup() {
		admin = new Admin();
	}
	
	
	@SuppressWarnings("static-access")
	@Test
	public void register_customer() {
		Customer expected_customer = new Customer("Affan Arif", "I-10/1", "03039554355");
		Customer actual_customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Assert.assertEquals(expected_customer, actual_customer);
		actual_customer = admin.customers.get(0);
		Assert.assertEquals(actual_customer, expected_customer);
	}
	@SuppressWarnings("static-access")
	@Test
	public void register_saving_account() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "saving");
		Assert.assertTrue(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void register_checking_account() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "checking");
		Assert.assertTrue(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void register_two_saving_account() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "saving");
		created = admin.register_account(customer, "saving");
		Assert.assertFalse(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void register_two_checking_account() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "checking");
		created = admin.register_account(customer, "checking");
		Assert.assertFalse(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void register_two_different_accounts() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "checking");
		created = admin.register_account(customer, "saving");
		Assert.assertTrue(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void register_three_accounts() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		Boolean created = admin.register_account(customer, "checking");
		Assert.assertTrue(created);
		created = admin.register_account(customer, "saving");
		Assert.assertTrue(created);
		created = admin.register_account(customer, "checking");
		Assert.assertFalse(created);
		created = admin.register_account(customer, "saving");
		Assert.assertFalse(created);
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void close_account() {
		Customer customer = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		admin.register_account(customer, "checking");
		Boolean closed = admin.close_account("IBN1000");
		Assert.assertTrue(closed);
		
		// CLOSE NON-EXISTENT ACCOUNT
		closed = admin.close_account("IBN1001");
		Assert.assertFalse(closed);
	}
	
	@SuppressWarnings("static-access")
	@After
	public void tearDown() {
		admin.customers.clear();
		admin.accounts.clear();
	}

}
