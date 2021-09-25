import static org.junit.Assert.*;
import org.junit.*;

public class AccountTest {
	public Admin admin;
	public Account account1;
	public Account account2;
	public Customer customer1;
	
	@SuppressWarnings("static-access")
	@Before
	public void setup() {
		admin = new Admin();
		customer1 = admin.register_customer("03039554355", "Affan Arif", "I-10/1");
		admin.register_account(customer1, "saving");
		admin.register_account(customer1, "checking");
		account1 = admin.accounts.get(0);
		account2 = admin.accounts.get(1);
	}
	
	
	@Test
	public void make_deposit() {
		account1.make_deposit(5000);
		Assert.assertEquals(5000, account1.balance, 0.0);
	}
	
	@Test
	public void make_withdrawal_saving() {
		account1.make_deposit(5000);
		Boolean success = account1.make_withdrawal(3000);
		assertTrue(success);
		assertEquals(2000, account1.balance, 0.0);
		
		success = account1.make_withdrawal(2500);
		assertFalse(success);
	}
	
	@Test
	public void make_withdrawal_checking() {
		account2.make_deposit(5000);
		Boolean success = account2.make_withdrawal(3000);
		assertTrue(success);
		assertEquals(2000, account2.balance, 0.0);
		
		success = account2.make_withdrawal(7000);
		assertTrue(success);
		assertEquals(-5000, account2.balance, 0.0);
		
		success = account2.make_withdrawal(100);
		assertFalse(success);
	}
	
	@Test
	public void transfer_amount() {
		// FROM SAVING TO CHECKING
		account1.make_deposit(5000);
		Boolean success = account1.transfer_amount(5000, account2);
		assertTrue(success);
		assertEquals(0, account1.balance, 0.0);
		assertEquals(5000, account2.balance, 0.0);
		
		// FROM CHECKING TO SAVING  
		success = account2.transfer_amount(3000, account1);
		assertTrue(success);
		assertEquals(3000, account1.balance, 0.0);
		assertEquals(2000, account2.balance, 0.0);
	}
	
	@SuppressWarnings("static-access")
	@After
	public void tearDown() {
		admin.customers.clear();
		admin.accounts.clear();
	}

}