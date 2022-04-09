package iob.service.dao;

import org.springframework.data.repository.CrudRepository;

import iob.data.InstanceEntity;

public interface InstanceDao extends CrudRepository<InstanceEntity, String> {
	
}