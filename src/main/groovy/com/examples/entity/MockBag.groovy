package com.examples.entity;

import groovy.transform.Canonical

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
@Canonical
@Entity
@Table(name="MOCK_BAG")
class MockBag {
	@Id
	@GeneratedValue
	@Column(name="BAG_ID")
	Long bagId;	
	@Column(name="BRAND_NAME")
	String name;
	@OneToMany(mappedBy="bag", fetch=FetchType.EAGER)	
	List<MockBean> beans;	
}
