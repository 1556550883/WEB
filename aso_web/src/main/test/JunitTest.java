import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml"})
//,		"classpath:spring-servlet.xml"
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback =false)
public class JunitTest {

	
	@Test
	public void testCreate() {
		
		
	}
}