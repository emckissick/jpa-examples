package com.examples.persistence;

import java.text.ParseException

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

import com.examples.entity.MockBag
import com.examples.entity.MockBean
import com.examples.entity.MockBean.Color
import com.examples.test.configuration.TestConfig

/**
 * A Bi-Directional Test with development Landmines
 * @author eugenemckissick
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestConfig.class)
@Transactional("txManager")
class MockBagTest {

	
		@Autowired
		private ApplicationContext applicationContext;
	
		@PersistenceContext(unitName = "applicationPU")
		EntityManager em;
		
	
		public MockBag jellyBellyBag = null;
		public List<MockBean> beans = new ArrayList<MockBean>();
		public MockBean redBean, greenBean, popCornBean;
			
		@Before
		public void setup() throws ParseException{
			jellyBellyBag = new MockBag(name: "Jelly Belly");
			em.persist(jellyBellyBag);
			em.flush();
			redBean = new MockBean(color: Color.RED, bag:jellyBellyBag );
			
			
			greenBean = new MockBean(color: Color.GREEN, bag:jellyBellyBag );
			popCornBean = new MockBean(color: Color.MUDDY, bag: jellyBellyBag );
		
			em.persist(redBean);			
			em.persist(greenBean);
			em.persist(popCornBean);				
		}
	
	
	
	@Test
	public void testRetrieveBagWithBeans()
	{	
		em.refresh(jellyBellyBag);
		MockBag result = em.find(MockBag, jellyBellyBag.bagId);	
		println result;	
		result.beans.each { it -> println it.color };					
	}
	
}