package com.example.agency_amazon_task.repository;

import com.example.agency_amazon_task.model.SalesAndTrafficByAsin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticsByAsinRepository extends MongoRepository<SalesAndTrafficByAsin, String> {
    List<SalesAndTrafficByAsin> findByParentAsinIn(List<String> asinList);
    SalesAndTrafficByAsin findByParentAsin(String parentAsin);
}
