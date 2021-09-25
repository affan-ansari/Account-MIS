import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	private Admin admin;
	
	@Before
	public void setup() {
		admin = new Admin();
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@SuppressWarnings("static-access")
	@After
	public void tearDown() {
		admin.customers.clear();
		admin.accounts.clear();
	}


}
