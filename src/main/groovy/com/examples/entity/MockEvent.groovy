package com.examples.entity;

import groovy.transform.Canonical

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne
import javax.persistence.Table
@Canonical
@Entity
@Table(name="MOCK_EVENT")
class MockEvent implements GenericEntityInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7649228617339065020L;
	@Id
	@Column(name="EVENT_ID")
	Long eventId;	
	@Column(name="EVENT_NAME")
	String name;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CYCLE_ID")
	MockCycle cycle;
		

	public Long getPrimaryKey() {
		return this.eventId;
	}
}
