package iob.service.dao;

import java.util.List;

import org.springframework.data.geo.Distance;

import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;


import iob.data.InstanceEntity;

@Repository
public interface InstanceDao extends MongoRepository<InstanceEntity, String> {
	List<InstanceEntity> findByLocationNear(Point location, Distance distance);
}