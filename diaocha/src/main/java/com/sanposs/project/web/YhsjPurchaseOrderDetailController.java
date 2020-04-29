package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjPurchaseOrderDetail;
import com.sanposs.project.service.YhsjPurchaseOrderDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/05/09.
*/
@RestController
@RequestMapping("/yhsj/purchase/order/detail")
public class YhsjPurchaseOrderDetailController {
    @Resource
    private YhsjPurchaseOrderDetailService yhsjPurchaseOrderDetailService;

    @RequestMapping("/add")
    public Result add(YhsjPurchaseOrderDetail yhsjPurchaseOrderDetail) {
        yhsjPurchaseOrderDetailService.save(yhsjPurchaseOrderDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjPurchaseOrderDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjPurchaseOrderDetail yhsjPurchaseOrderDetail) {
        yhsjPurchaseOrderDetailService.update(yhsjPurchaseOrderDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjPurchaseOrderDetail yhsjPurchaseOrderDetail = yhsjPurchaseOrderDetailService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjPurchaseOrderDetail);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjPurchaseOrderDetail> list = yhsjPurchaseOrderDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
