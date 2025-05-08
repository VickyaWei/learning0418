package com.learning.connector.repository;

import com.learning.connector.model.CustomerProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepository extends MongoRepository<CustomerProfile, String> {

}