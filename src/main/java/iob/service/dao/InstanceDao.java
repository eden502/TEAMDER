package iob.service.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import iob.data.InstanceEntity;
import iob.data.UserEntity;
@Repository
public interface InstanceDao extends MongoRepository<InstanceEntity, String> {
	
}