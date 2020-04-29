package com.sanposs.project.service;
import java.util.List;

import com.sanposs.project.model.YhsjDeliveryOrder;
import com.sanposs.project.model.YhsjDeliveryOrderDetail;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.common.PlatformException;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.Service;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
public interface YhsjDeliveryOrderDetailService extends Service<YhsjDeliveryOrderDetail> {

	Result addDeliveryOrder(YhsjDeliveryOrder deliveryOrder,List<YhsjDeliveryOrderDetail> deliveryOrderDetails,String purchaseId) throws PlatformException;

}
