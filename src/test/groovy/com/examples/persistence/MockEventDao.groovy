package com.examples.persistence;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional

import com.examples.persistence.mock.MockCycle;
import com.examples.persistence.mock.MockDao;
import com.examples.persistence.mock.MockEvent;
import com.examples.test.configuration.TestConfig


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
@Transactional("txManager")
class MockEventDaoTest {

	
		@Autowired
		private ApplicationContext applicationContext;
	
		@Resource
		MockDao mockDao;
		
	
		public MockCycle testCycle = null;
		public MockEvent testEvent = null;
		public Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
	
	
		@Before
		public void setup() throws ParseException{
			testCycle = new MockCycle();
			testEvent = new MockEvent();
			testCycle.setCycleGid(999999l);
			testCycle.setBeginDate(new Date());
			testCycle.setEndDate(new Date());
			
			testEvent.setEventGid(124l);
			testEvent.setName("Test Event");
			testEvent.setCycle(testCycle);
			
		}
	
	
	
	@Test
	public void testOneToOne()
	{
		assert (null)
	}

}