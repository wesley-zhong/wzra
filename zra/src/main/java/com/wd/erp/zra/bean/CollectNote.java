
package com.wd.erp.zra.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* Auto-generated: 2016-09-10 15:28:18
*
* @author aTool.org (i@aTool.org)
* @website http://www.atool.org/json2javabean.php
*/
public class CollectNote {

   @JsonProperty("collect_number")
   private String collectNumber;
   @JsonProperty("order_number")
   private String orderNumber;
   @JsonProperty("collect_date")
   private String collectDate;
   @JsonProperty("collect_amount")
   private int collectAmount;
   @JsonProperty("type_flag")
   private int typeFlag;
   private String remark;
   public void setCollectNumber(String collectNumber) {
        this.collectNumber = collectNumber;
    }
    public String getCollectNumber() {
        return collectNumber;
    }

   public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public String getOrderNumber() {
        return orderNumber;
    }

   public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }
    public String getCollectDate() {
        return collectDate;
    }

   public void setCollectAmount(int collectAmount) {
        this.collectAmount = collectAmount;
    }
    public int getCollectAmount() {
        return collectAmount;
    }

   public void setTypeFlag(int typeFlag) {
        this.typeFlag = typeFlag;
    }
    public int getTypeFlag() {
        return typeFlag;
    }

   public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }

}