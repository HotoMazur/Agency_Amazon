package com.example.agency_amazon_task.service;

import com.example.agency_amazon_task.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_task.model.SalesAndTrafficByDate;
import com.example.agency_amazon_task.repository.StatisticsByAsinRepository;
import com.example.agency_amazon_task.repository.StatisticsByDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class StatisticService {
    @Autowired
    private StatisticsByDateRepository statisticsByDateRepository;
    @Autowired
    private StatisticsByAsinRepository statisticsByAsinRepository;

    public List<SalesAndTrafficByDate> allSalesAndTrafficByDates() {
        return new ArrayList<>(statisticsByDateRepository.findAll());
    }

    public List<SalesAndTrafficByAsin> allSalesAndTrafficByAsins() {
        return new ArrayList<>(statisticsByAsinRepository.findAll());
    }

    public List<SalesAndTrafficByDate> SalesAndTrafficBetweenDates(Date startDate, Date endDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date adjustedStartDate = calendar.getTime();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date adjustedEndDate = calendar.getTime();

        return statisticsByDateRepository.findByDateBetween(adjustedStartDate, adjustedEndDate);
    }

    public List<SalesAndTrafficByAsin> SalesAndTrafficByAsins(List<String> asins) {
        return statisticsByAsinRepository.findByParentAsinIn(asins);
    }
}
