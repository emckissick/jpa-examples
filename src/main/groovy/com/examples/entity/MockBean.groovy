package com.examples.entity;

import groovy.transform.Canonical

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table
@Canonical
@Entity
@Table(name="MOCK_BEAN")
class MockBean{

	public enum Color {
		RED, YELLOW, GREEN, MUDDY;
	}
	
	@Id
	@GeneratedValue
	@Column(name="BEAN_ID")
	Long beanId;	
	@Column(name="COLOR")
	@Enumerated(EnumType.STRING)
	String color;
	@ManyToOne
	@Column(name="BAG_ID")
	Long bagId;
}
