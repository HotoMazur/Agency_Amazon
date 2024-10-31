package com.example.agency_amazon_task.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "sales_and_traffic_by_asin")
@Getter
@Setter
public class SalesAndTrafficByAsin {
  @Id private String id;
  private String parentAsin;
  private double orderedProductSales;
  private int unitsOrdered;
  private int pageViews;

  @Override
  public int hashCode() {
    return Objects.hash(parentAsin, orderedProductSales, unitsOrdered,  pageViews);
  }
}
