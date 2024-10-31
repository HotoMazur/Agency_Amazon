package com.example.agency_amazon_task.controller;

import com.example.agency_amazon_task.dto.DateFilter;
import com.example.agency_amazon_task.model.SalesAndTrafficByAsin;
import com.example.agency_amazon_task.model.SalesAndTrafficByDate;
import com.example.agency_amazon_task.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/statistic")
@RestController
public class StatisticController {
  @Autowired private StatisticService statisticService;

  @GetMapping("/statisticByDates")
  @Cacheable(value = "allStaticByDates")
  public List<SalesAndTrafficByDate> allStatisticByDates() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return statisticService.allSalesAndTrafficByDates();
    } else {
      return null;
    }
  }

  @GetMapping("/statisticByAsins")
  @Cacheable(value = "allStaticByAsins")
  public List<SalesAndTrafficByAsin> allStatisticByAsins() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return statisticService.allSalesAndTrafficByAsins();
    } else {
      return null;
    }
  }

  @GetMapping("/statisticBetweenDates")
  @Cacheable(value = "StaticByDates", key = "#dateFilter.startDate + ' : ' + #dateFilter.endDate")
  public List<SalesAndTrafficByDate> statisticBetweenDates(@RequestBody DateFilter dateFilter) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return statisticService.SalesAndTrafficBetweenDates(dateFilter.getStartDate(), dateFilter.getEndDate());
    } else {
      return null;
    }
  }

  @GetMapping("/statisticByAsinsIn")
  @Cacheable(value = "staticByAsins", key = "#asins")
  public List<SalesAndTrafficByAsin> statisticByAsins(@RequestBody List<String> asins) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication.isAuthenticated()) {
      return statisticService.SalesAndTrafficByAsins(asins);
    } else {
      return null;
    }
  }
}
