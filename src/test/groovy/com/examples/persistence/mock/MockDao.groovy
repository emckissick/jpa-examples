package com.examples.persistence.mock

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.examples.persistence.AbstractJpaGenericDao






public class MockDao extends AbstractJpaGenericDao<MockCycle>  {

	public MockDao() {
		super(MockCycle.class);
	}
	
	public MockDao(EntityManager em) {
		super(em);
	}
	
	@PersistenceContext(unitName = "applicationPU")
	public void setEntityManager(EntityManager em)
	{           
	 this.em = em;
	}






}
