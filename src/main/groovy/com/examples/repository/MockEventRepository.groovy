package com.examples.repository

import org.springframework.data.repository.RepositoryDefinition

import com.examples.entity.MockEvent

@RepositoryDefinition(domainClass = MockEvent, idClass=Long)
interface MockEventRepository  {

	MockEvent findByCycleCycleId(Long cycleId)

}
