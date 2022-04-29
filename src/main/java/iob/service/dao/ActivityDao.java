package iob.service.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import iob.data.ActivityEntity;
@Repository
public interface ActivityDao extends MongoRepository<ActivityEntity, String> {

}
