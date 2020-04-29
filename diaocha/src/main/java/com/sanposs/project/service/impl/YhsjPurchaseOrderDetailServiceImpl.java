package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjPurchaseOrderDetailMapper;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
@Service
@Transactional
public class YhsjPurchaseOrderDetailServiceImpl extends AbstractService<YhsjPurchaseOrderDetail> implements YhsjPurchaseOrderDetailService {
    @Resource
    private YhsjPurchaseOrderDetailMapper yhsjPurchaseOrderDetailMapper;

}
