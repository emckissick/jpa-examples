package com.examples.persistence;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.examples.entity.GenericEntityInterface
import com.examples.persistence.exception.JpaPersistenceException



public abstract class AbstractJpaGenericDao<T extends GenericEntityInterface>{
	protected Class<T> entityClass;
	
	protected EntityManager em;
	private static Logger logger = Logger.getLogger(AbstractJpaGenericDao.class);

	public AbstractJpaGenericDao(Class<T> classToUse)
	{
		entityClass = classToUse;
	}
	
	
	public abstract void setEntityManager(EntityManager em);
	
	
	public void save(T entity) throws JpaPersistenceException {	
		em.persist(entity);					
	}
	
	
	/**
	 * Update or Save the entity in the database.  If the entity exists perform a merge, if it doesn't
	 * persist it as a new entry
	 * @param entity
	 * @throws JpaPersistenceException if any error occurs in storage
	 * @return the updated entity
	 */
	public T saveOrUpdate(T entity) throws JpaPersistenceException {
		T entityToReturn = null;
		T attachedEntity = null;
		if (entity.getPrimaryKey() != null ) {
			attachedEntity = em.find(entityClass,entity.getPrimaryKey());
		}
		if (attachedEntity == null) {
			em.persist(entity);
			entityToReturn = entity;
		} else {
			entityToReturn =  em.merge(entity);
		}			
		return entityToReturn;
		
	}
	
	
	
	public void remove(T entity) throws JpaPersistenceException {
		
		T attachedEntity = em.find(entityClass,entity.getPrimaryKey());
		if (attachedEntity == null) {
			return;
		}
		em.remove(attachedEntity);
			
	}
		
	
	public T findByPrimaryKey(Object primaryKey)  
	{		
		T result = null;		
		result = em.find(entityClass, primaryKey);		
		return result;		
	}

	
	
	public void removeByPrimaryKey(Object primaryKey) 
	{		
		T result = null;		
		result = em.find(entityClass, primaryKey);
		if (result != null)
		{
			this.remove(result);
		}				
	}
	
	
	
	/**
	 * Allow the Persistence layer to perform a native update using the a sql statement and a list of parameters
	 * @param sql - Native sql update query
	 * @param params
	 * @return true if the update completes without error
	 * @throws JpaPersistenceException
	 */
	public boolean executeNativeUpdate(String sql, Object... params) throws JpaPersistenceException
	{	
			Query query = em.createNativeQuery(sql);
			int position = 1;
			for (Object parameter : params)
			{
				query.setParameter(position, parameter);
				position++;
			}
			query.executeUpdate();	
		return true;
	}
}
