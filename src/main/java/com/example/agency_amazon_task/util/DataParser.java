package com.example.agency_amazon_task.util;

import com.example.agency_amazon_task.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_task.model.SalesAndTrafficByDate;
import com.example.agency_amazon_task.repository.StatisticsByAsinRepository;
import com.example.agency_amazon_task.repository.StatisticsByDateRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DataParser {
    private static final String JSON_FILE = "src/main/resources/test_report.json";
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private StatisticsByDateRepository statisticsByDateRepository;

    @Autowired
    private StatisticsByAsinRepository statisticsByAsinRepository;

    public List<SalesAndTrafficByDate> parseDateStats() {
     List<SalesAndTrafficByDate> dateStatsList = new ArrayList<>();
     try{
         JsonNode root = mapper.readTree(new File(JSON_FILE));
         JsonNode dateStats = root.path("salesAndTrafficByDate");
         for (JsonNode node : dateStats){
             SalesAndTrafficByDate dateStat = new SalesAndTrafficByDate();
             dateStat.setDate(mapper.convertValue(node.get("date"), Date.class));
             dateStat.setOrderProductSales(node.get("salesByDate").get("orderedProductSales").get("amount").asDouble());
             dateStat.setUnitsOrdered(node.get("salesByDate").get("unitsOrdered").asInt());
             dateStat.setPageViews(node.get("trafficByDate").get("pageViews").asInt());
             dateStat.setId(String.valueOf(dateStat.hashCode()));

             if (!statisticsByDateRepository.existsById(dateStat.getId())) {
                 dateStatsList.add(dateStat);
             }
         }
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
     return dateStatsList;
    }

    public List<SalesAndTrafficByAsin> parseAsinStats(){
        List<SalesAndTrafficByAsin> asinStatsList = new ArrayList<>();
        try{
            JsonNode root = mapper.readTree(new File(JSON_FILE));
            JsonNode asinStats = root.path("salesAndTrafficByAsin");
            for (JsonNode node : asinStats){
                SalesAndTrafficByAsin asinStat = new SalesAndTrafficByAsin();
                asinStat.setParentAsin(node.get("parentAsin").asText());
                asinStat.setOrderedProductSales(node.get("salesByAsin").get("orderedProductSales").get("amount").asDouble());
                asinStat.setUnitsOrdered(node.get("salesByAsin").get("unitsOrdered").asInt());
                asinStat.setPageViews(node.get("trafficByAsin").get("pageViews").asInt());
                asinStat.setId(String.valueOf(asinStat.hashCode()));

                if (!statisticsByAsinRepository.existsById(asinStat.getId())) {
                    asinStatsList.add(asinStat);
                }
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return asinStatsList;
    }

    @Scheduled(fixedRate = 20000)
    public void checkAndUpdateStats() {
        List<SalesAndTrafficByDate> dateStatsList = parseDateStats();
        List<SalesAndTrafficByAsin> asinStatsList = parseAsinStats();

        for (SalesAndTrafficByDate dateStat : dateStatsList) {
            SalesAndTrafficByDate existingStat = statisticsByDateRepository.findByDate(dateStat.getDate());
            if (existingStat!= null && !existingStat.getId().equals(dateStat.getId())) {
                statisticsByDateRepository.deleteById(existingStat.getId());
                statisticsByDateRepository.save(dateStat);
            }

        }

        for (SalesAndTrafficByAsin asinStat : asinStatsList) {
            SalesAndTrafficByAsin existingStat = statisticsByAsinRepository.findByParentAsin(asinStat.getParentAsin());
            if (existingStat != null && !existingStat.getId().equals(asinStat.getId())) {
                statisticsByAsinRepository.deleteById(existingStat.getId());
                statisticsByAsinRepository.save(asinStat);
            }
        }

        if (!dateStatsList.isEmpty() ) {
            evictDateCache();
        }

        if (!asinStatsList.isEmpty()){
            evictAsinCache();
        }
    }

    @CacheEvict(cacheNames = {"allStaticByDates", "StaticByDates"})
    public void evictDateCache() {
    }

    @CacheEvict(cacheNames = {"allStaticByAsins", "staticByAsins"})
    public void evictAsinCache() {
    }
}
