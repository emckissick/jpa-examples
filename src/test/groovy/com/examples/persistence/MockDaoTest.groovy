package com.examples.persistence;

import static org.junit.Assert.*

import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat

import javax.annotation.Resource

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.transaction.annotation.Transactional

import com.examples.entity.MockCycle
import com.examples.persistence.mock.MockDao
import com.examples.test.configuration.TestConfig



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
@Transactional("txManager")
class MockDaoTest {

	
		@Autowired
		private ApplicationContext applicationContext;
	
		@Resource
		MockDao mockDao;
		
	
		public MockCycle testEntity = null;
		public Format formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
	
	
		@Before
		public void setup() throws ParseException{
			
			//Entity used to generate tables for test
			MockCycle seedEntity = new MockCycle();
			seedEntity.cycleId = -1234L;
			seedEntity.beginDate = new Date();
			seedEntity.endDate = new Date();
			mockDao.save(seedEntity);
			mockDao.em.flush();
			
			testEntity = new MockCycle();
			testEntity.cycleId = 999999l;
			testEntity.beginDate = new Date();
			testEntity.endDate = new Date();
			
		}
	
	
	
		@Test
		public void testAbstractGenericDao() {
			Class testClass = mockDao.entityClass;
			assertEquals(testClass,MockCycle.class);
		}
	
		@Test
		public void testSaveAndFind() {	
			mockDao.save(testEntity);
			MockCycle storedEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert storedEntity.cycleId == testEntity.cycleId;
			assert formatter.format(storedEntity.beginDate) == formatter.format(testEntity.beginDate);
			assert formatter.format(storedEntity.endDate) == formatter.format(testEntity.endDate);
		}
	
		@Test
		public void testSaveOrUpdate() {			
			MockCycle emptyEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert (!emptyEntity);
			//Test save portion of the method
			MockCycle savedEntity = mockDao.saveOrUpdate(testEntity);
			MockCycle originalEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert savedEntity.cycleId == originalEntity.cycleId;
			assert formatter.format(savedEntity.beginDate) == formatter.format(originalEntity.beginDate);
			assert formatter.format(savedEntity.endDate) == formatter.format(originalEntity.endDate);
	
			//Test update portion of the method
			MockCycle updatedEntity = mockDao.saveOrUpdate(testEntity);
			MockCycle storedEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert storedEntity.cycleId == updatedEntity.cycleId;
			assert testEntity.cycleId == updatedEntity.cycleId;
			assert formatter.format(storedEntity.beginDate) == formatter.format(updatedEntity.beginDate);
			assert formatter.format(storedEntity.endDate) == formatter.format(updatedEntity.endDate);
	
		}
	
		@Test
		public void testRemove() {
			mockDao.remove(testEntity);
			assert (!mockDao.findByPrimaryKey(testEntity.getPrimaryKey()));
		}
	
	
	
		@Test
		public void testRemoveByPrimaryKey() {
			Long id = testEntity.cycleId;
			mockDao.save(testEntity);
			mockDao.removeByPrimaryKey(id);
			assert (!mockDao.findByPrimaryKey(id));
		}
	
	}

