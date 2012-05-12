package com.examples.persistence.jpa.util

import java.lang.reflect.Constructor
import java.util.List;
import javax.persistence.EntityManager
import javax.persistence.Query

import org.springframework.transaction.annotation.Transactional;

import com.examples.entity.GenericEntityInterface
import com.examples.persistence.exception.JpaPersistenceException

class JpaUtil<T extends GenericEntityInterface>{
	Class<T> entityClass;
	
	JpaUtil(Class<T> classToUse)
	{
		entityClass = classToUse;
	}
	
	
	public void save(EntityManager em, T entity) throws JpaPersistenceException {	
		em.persist(entity);
	}
	
	
	/**
	 * Update or Save the entity in the database.  If the entity exists perform a merge, if it doesn't
	 * persist it as a new entry
	 * @param entity
	 * @throws JpaPersistenceException if any error occurs in storage
	 * @return the updated entity
	 */
	public  T saveOrUpdate(EntityManager em, T entity) throws JpaPersistenceException {
		T entityToReturn = null;
		T attachedEntity = em.find(this.getEntityClass(),entity.getPrimaryKey());
		if (attachedEntity == null) {
			em.persist(entity);
			entityToReturn = entity;
		} else {
			entityToReturn =  em.merge(entity);
		}		
		return entityToReturn;	
	}
	
	
	
	public void remove(EntityManager em, T entity) throws JpaPersistenceException {	
		T attachedEntity = em.find(this.getEntityClass(),entity.getPrimaryKey());
		if (attachedEntity == null) {
			return;
		}
		em.remove(attachedEntity);		
	}
		
	
	public T findByPrimaryKey(EntityManager em, Object primaryKey) throws JpaPersistenceException 
	{	
		T result = null;		
		result = em.find(this.getEntityClass(), primaryKey);	
		return result;	
	}

	
	
	public void removeByPrimaryKey(EntityManager em, Object primaryKey) throws JpaPersistenceException 
	{	
		T result = null;	
		result = em.find(this.getEntityClass(), primaryKey);
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
	@Transactional
	public boolean executeNativeUpdate(EntityManager em, String sql, Object... params) throws JpaPersistenceException
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
	

	/**
	 * Takes a class and attempts to use that class to populate a list of the query results
	 * @param classToUse The class that you want data casted to
	 * @param sql The sql statement you want to execute.  It has to be a select statement.
	 * @param params The parameters used for the ? for your sql statement
	 * @return A list of results cast to the ClassToUse
	 * @throws JpaPersistenceException
	 */
	public <E>List<E> executeNativeQuery(EntityManager em, Class<E> classToUse, String sql, Object... params) throws JpaPersistenceException
	{
		String errorCode = "An error occurred during execution of a native query.";	
		try {
			Query query = null;
		
			if (GenericEntityInterface.class.isAssignableFrom(classToUse))
			{
				query = em.createNativeQuery(sql, classToUse);
			} else {
				query = em.createNativeQuery(sql);
			}
			int position = 1;
			for (Object parameter : params)
			{
				query.setParameter(position, parameter);
				position++;
			}
			List<E> results = query.getResultList();
			if (results.size() > 0)
			{
				if (Number.class.isAssignableFrom(classToUse))
				{
					if (!(results.get(0) instanceof BigDecimal)) {
						throw new JpaPersistenceException("Type mismatch attempting to cast a "+results.get(0).getClass().getName()+" to "+classToUse.getName());
					} else if (classToUse.getName().equals(BigDecimal.class.getName())) {
						return results;
					}
					List<E> formattedList = new ArrayList<E>();
					for (E result : results) {						
						Constructor<E> ct = classToUse.getConstructor(String.class);
						E test = ct.newInstance(result.toString());
						formattedList.add(test);
					}
					return formattedList;
				}
			}
			return query.getResultList();				
		} catch (ClassCastException classCastExc) {			
			throw new JpaPersistenceException(errorCode,classCastExc);
		} catch (InstantiationException e) {			
			throw new JpaPersistenceException(errorCode,e);
		} catch (IllegalAccessException e) {
			throw new JpaPersistenceException(errorCode,e);
		} catch (SecurityException e) {
			throw new JpaPersistenceException(errorCode,e);
		} catch (NoSuchMethodException e) {
			throw new JpaPersistenceException(errorCode,e);
		} catch (IllegalArgumentException e) {
			throw new JpaPersistenceException(errorCode,e);
		} 
	}	

}
