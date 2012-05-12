package com.examples.persistence.mock;

import groovy.transform.Canonical;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne
import javax.persistence.Table;

import com.examples.entity.GenericEntityInterface


@Canonical
@Entity
@Table(name="EVENT")
public class MockEvent implements GenericEntityInterface {

	@Id
	@Column(name="EVENT_GID")
	Long eventGid;
	@Column(name="EVENT_NAME")
	String name
	
	@OneToOne
	MockCycle cycle;
	
	public Object getPrimaryKey() {
		return eventGid;
	}
	
}
