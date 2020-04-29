package com.sanposs.project.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Table;

@Table(name = "yhsj_purchase_order")
public class YhsjPurchaseOrderExtend extends YhsjPurchaseOrder implements Serializable {
	List detailList;

	public List getDetailList() {
		return detailList;
	}

	public void setDetailList(List detailList) {
		this.detailList = detailList;
	}

}