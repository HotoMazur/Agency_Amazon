package com.example.agency_amazon_task.service;

import com.example.agency_amazon_task.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_task.model.SalesAndTrafficByDate;
import com.example.agency_amazon_task.repository.StatisticsByAsinRepository;
import com.example.agency_amazon_task.repository.StatisticsByDateRepository;
import com.example.agency_amazon_task.util.DataParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataImportService {
    @Autowired
    private StatisticsByDateRepository dateRepository;
    @Autowired
    private StatisticsByAsinRepository asinRepository;
    @Autowired
    private DataParser dataParser;

    public void importData(){
        List<SalesAndTrafficByDate> dateStats = dataParser.parseDateStats();
        List<SalesAndTrafficByAsin> asinStats = dataParser.parseAsinStats();

        dateRepository.saveAll(dateStats);
        asinRepository.saveAll(asinStats);
    }


}
