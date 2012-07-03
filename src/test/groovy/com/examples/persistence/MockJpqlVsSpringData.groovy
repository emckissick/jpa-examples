package com.examples.persistence;

import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

import com.examples.entity.MockCycle
import com.examples.entity.MockEvent
import com.examples.test.configuration.TestConfig


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
@Transactional("txManager")
class MockJpqlVsSpringData {

	
		@Autowired
		private ApplicationContext applicationContext;
	
		@PersistenceContext(unitName = "applicationPU")
		EntityManager em;
		
	
		public MockCycle testCycle, secondCycle = null;
		public MockEvent testEvent, backupEvent = null;
		public Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
		private final Long eventId = 124L;
		private final Long backupId = 125L;
		private final String initialComment = "First Cycle Plan";
		private final String backupComment = "Rain site - Lets goto the backup location";
	
	
		@Before
		public void setup() throws ParseException{
			//Java like
			testCycle = new MockCycle();
			testEvent = new MockEvent();
			testCycle.setCycleId(999999L);
			testCycle.setBeginDate(new Date());
			testCycle.setEndDate(new Date());
			testCycle.setComments(initialComment)
			
			testEvent.setEventId(124l);
			testEvent.setName("Test Event");
			testEvent.setCycle(testCycle);
			
			//Let's be Groovy
			secondCycle = new MockCycle(cycleId: 999990L,
												  beginDate: new Date(),
												  endDate: new Date(),
												  comments: backupComment);
			backupEvent = new MockEvent(eventId: 125L, 
												 name: "Backup Event",
												 cycle: secondCycle);	
			
		}
	
	@Test
	public void testJpql()
	{
		em.persist(testEvent);
		em.persist(backupEvent);
		em.flush();
		String jpqlSecondCycle = "from MockEvent where cycle.cycleId = :someId";
		Query myQuery = em.createQuery(jpqlSecondCycle);
		myQuery.setParameter("someId", 999990L);
		def result = myQuery.getSingleResult();		
		println result;
		assert result.cycle.comments == backupComment;		
	}
	
	@Test
	public void testSpringBatch() 
	{
		//Finish this
	}
}