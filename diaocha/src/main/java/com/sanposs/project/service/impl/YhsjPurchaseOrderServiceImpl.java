package com.sanposs.project.service.impl;

import com.sanposs.project.dao.YhsjPurchaseOrderMapper;
import com.sanposs.project.model.YhsjPurchaseOrder;
import com.sanposs.project.service.YhsjPurchaseOrderService;
import com.sanposs.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/05/09.
 */
@Service
@Transactional
public class YhsjPurchaseOrderServiceImpl extends AbstractService<YhsjPurchaseOrder> implements YhsjPurchaseOrderService {
    @Resource
    private YhsjPurchaseOrderMapper yhsjPurchaseOrderMapper;

}
