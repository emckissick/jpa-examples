package com.examples.entity;

import groovy.transform.Canonical

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType
@Canonical
@Entity
@Table(name="MOCK_CYCLE")
class MockCycle implements GenericEntityInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649228617339065020L;
	@Id
	@Column(name="CYCLE_ID")
	Long cycleId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CYCLE_BEGIN_DATE")
	Date beginDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CYCLE_END_DATE")
	Date endDate;	
	@Column(name="DATE_CREATED")
	Date dateCreated;
		

	

	public Long getPrimaryKey() {
		return this.cycleId;
	}
}
