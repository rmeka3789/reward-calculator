//package com.retailer.calc;
//
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
//
//import com.retailer.calc.entity.Customer;
//import com.retailer.calc.entity.Transaction;
//import com.retailer.calc.repository.CustomerRepository;
//import com.retailer.calc.repository.TransactionRepository;
//import com.retailer.calc.service.RewardsService;
//import com.retailter.calc.RetailerCalculatorApplication;
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//@ContextConfiguration(classes = RetailerCalculatorApplication.class)
//@Sql(scripts = "/create-tables.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = "/create-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
//@DataJpaTest
//public class UserRepositoryH2Test {
//
//	@Autowired
//	private CustomerRepository custRepository;
//
//	@Autowired
//	private TransactionRepository txnRepository;
//
//	@Autowired
//	private RewardsService rewardsService;
//	
//	@Test
//	void calculateRewrdForCustomer01() {
//
//		Long custId = 1001L;
//		Customer customer = custRepository.findByCustomerId(custId);
//
//		assertNotNull(customer);
//
//		List<Transaction> txns = txnRepository.findAllByCustomerId(custId);
//		assertNotNull(txns);
//
//		rewardsService.getRewardsByCustomerId(custId);
//	}
//}
