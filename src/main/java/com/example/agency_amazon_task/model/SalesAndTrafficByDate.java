package com.example.agency_amazon_task.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "sales_add_traffic_by_date")
@Getter
@Setter
public class SalesAndTrafficByDate {
    @Id
    private String id;
    private Date date;
    private double orderProductSales;
    private int unitsOrdered;
    private int pageViews;

    @Override
    public int hashCode() {
        return Objects.hash(date, orderProductSales, unitsOrdered, pageViews);
    }
}
