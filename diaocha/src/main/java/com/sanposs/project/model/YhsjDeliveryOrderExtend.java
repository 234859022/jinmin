package com.sanposs.project.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "yhsj_delivery_order")
public class YhsjDeliveryOrderExtend extends YhsjDeliveryOrder implements Serializable{
	List detailList;

	public List getDetailList() {
		return detailList;
	}

	public void setDetailList(List detailList) {
		this.detailList = detailList;
	}
}