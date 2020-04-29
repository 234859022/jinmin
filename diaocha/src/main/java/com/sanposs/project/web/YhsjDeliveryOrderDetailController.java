package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjDeliveryOrderDetail;
import com.sanposs.project.service.YhsjDeliveryOrderDetailService;
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
@RequestMapping("/yhsj/delivery/order/detail")
public class YhsjDeliveryOrderDetailController {
    @Resource
    private YhsjDeliveryOrderDetailService yhsjDeliveryOrderDetailService;

    @RequestMapping("/add")
    public Result add(YhsjDeliveryOrderDetail yhsjDeliveryOrderDetail) {
        yhsjDeliveryOrderDetailService.save(yhsjDeliveryOrderDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjDeliveryOrderDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjDeliveryOrderDetail yhsjDeliveryOrderDetail) {
        yhsjDeliveryOrderDetailService.update(yhsjDeliveryOrderDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjDeliveryOrderDetail yhsjDeliveryOrderDetail = yhsjDeliveryOrderDetailService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjDeliveryOrderDetail);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjDeliveryOrderDetail> list = yhsjDeliveryOrderDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
