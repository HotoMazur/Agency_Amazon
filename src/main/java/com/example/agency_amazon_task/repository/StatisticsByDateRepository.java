package com.example.agency_amazon_task.repository;

import com.example.agency_amazon_task.model.SalesAndTrafficByDate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StatisticsByDateRepository extends MongoRepository<SalesAndTrafficByDate, String> {
    List<SalesAndTrafficByDate> findByDateBetween(Date startDate, Date endDate);

    SalesAndTrafficByDate findByDate(Date date);
}
