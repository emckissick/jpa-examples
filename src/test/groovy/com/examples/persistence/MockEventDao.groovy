package com.examples.persistence;

import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

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
class MockEventDaoTest {

	
		@Autowired
		private ApplicationContext applicationContext;
	
		@PersistenceContext(unitName = "applicationPU")
		EntityManager em;
		
	
		public MockCycle testCycle, secondCycle = null;
		public MockEvent testEvent, backupEvent = null;
		public Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
		private final Long eventId = 124L;
	
	
		@Before
		public void setup() throws ParseException{
			testCycle = new MockCycle();
			testEvent = new MockEvent();
			testCycle.cycleId = 999999L;
			testCycle.beginDate = new Date();
			testCycle.endDate = new Date();
			
			testEvent.eventId = 124l;
			testEvent.name = "Test Event";
			testEvent.cycle =testCycle;
			
			//Let's be Groovy
			secondCycle = new MockCycle(cycleId: 999990L,
												  beginDate: new Date(),
												  endDate: new Date());
			backupEvent = new MockEvent(eventId: 125L, 
												 name: "Backup Event",
												 cycle: secondCycle);	
			
		}
	
	
	
	@Test
	public void testOneToOneWithCascadingSave()
	{
		em.persist(testEvent);
		MockEvent result = em.find(MockEvent, eventId);
		println result;
		assert result == testEvent;
		assert result.cycle == testCycle;
	}
	
	@Test
	public void testOneToOneWithCascadingUpdate()
	{
		em.persist(testEvent);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(testEvent.cycle.endDate);
		cal.add(Calendar.DATE, 25);
		Date bumpedDate = cal.getTime();
		testEvent.cycle.endDate = bumpedDate
		
		MockEvent mergedEvent = em.merge(testEvent);
		MockEvent result = em.find(MockEvent, eventId);
		println result;
		assert mergedEvent == result;
		assert mergedEvent == testEvent;
		
		MockCycle updatedCycle = em.find(MockCycle, testCycle.cycleId);
		assert updatedCycle.endDate == bumpedDate;
	}
	
	@Test
	public void testOneToOneRemoveCycleIdFromEvent()
	{
		em.persist(testEvent);
		
		testEvent.cycle = null;
		
		MockEvent mergedEvent = em.merge(testEvent);
		MockEvent result = em.find(MockEvent, eventId);
		println result;
		assert mergedEvent == result;
		assert mergedEvent == testEvent;
		
		MockCycle updatedCycle = em.find(MockCycle, testCycle.cycleId);
		assert updatedCycle
	}
	
	@Test
	public void testOneToOneScorchEarth()
	{
		em.persist(testEvent);
		
		MockEvent result = em.find(MockEvent, eventId);
		println result;
		assert testEvent == result;
		MockCycle updatedCycle = em.find(MockCycle, testCycle.cycleId);
		assert updatedCycle
		
		em.remove(testEvent);
		result = em.find(MockEvent, eventId);		
		assert !result;
		
		updatedCycle = em.find(MockCycle, testCycle.cycleId);
		assert !updatedCycle
	}
}