package com.examples.persistence;

import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examples.persistence.mock.MockCycle
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
			testEntity = new MockCycle();
			testEntity.setCycleGid(999999l);
			testEntity.setBeginDate(new Date());
			testEntity.setEndDate(new Date());
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
			assert storedEntity.getCycleGid() == testEntity.getCycleGid();
			assert formatter.format(storedEntity.getBeginDate()) == formatter.format(testEntity.getBeginDate());
			assert formatter.format(storedEntity.getEndDate()) == formatter.format(testEntity.getEndDate());
		}
	
		@Test
		public void testSaveOrUpdate() {
	
			MockCycle emptyEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert (!emptyEntity);
			//Test save portion of the method
			MockCycle savedEntity = mockDao.saveOrUpdate(testEntity);
			MockCycle originalEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert savedEntity.getCycleGid() == originalEntity.getCycleGid();
			assert formatter.format(savedEntity.getBeginDate()) == formatter.format(originalEntity.getBeginDate());
			assert formatter.format(savedEntity.getEndDate()) == formatter.format(originalEntity.getEndDate());
	
			//Test update portion of the method
			MockCycle updatedEntity = mockDao.saveOrUpdate(testEntity);
			MockCycle storedEntity = mockDao.findByPrimaryKey(testEntity.getPrimaryKey());
			assert storedEntity.getCycleGid() == updatedEntity.getCycleGid();
			assert testEntity.getCycleGid() == updatedEntity.getCycleGid();
			assert formatter.format(storedEntity.getBeginDate()) == formatter.format(updatedEntity.getBeginDate());
			assert formatter.format(storedEntity.getEndDate()) == formatter.format(updatedEntity.getEndDate());
	
		}
	
		@Test
		public void testRemove() {
			mockDao.remove(testEntity);
			assert (!mockDao.findByPrimaryKey(testEntity.getPrimaryKey()));
		}
	
	
	
		@Test
		public void testRemoveByPrimaryKey() {
			Long id = testEntity.getCycleGid();
			mockDao.save(testEntity);
			mockDao.removeByPrimaryKey(id);
			assert (!mockDao.findByPrimaryKey(id));
		}
	
	}

