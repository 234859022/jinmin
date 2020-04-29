package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjUserAddress;
import com.sanposs.project.service.YhsjUserAddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/05/13.
*/
@RestController
@RequestMapping("/yhsj/user/address")
public class YhsjUserAddressController {
    @Resource
    private YhsjUserAddressService yhsjUserAddressService;

    @RequestMapping("/add")
    public Result add(YhsjUserAddress yhsjUserAddress) {
        yhsjUserAddressService.save(yhsjUserAddress);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjUserAddressService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjUserAddress yhsjUserAddress) {
        yhsjUserAddressService.update(yhsjUserAddress);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjUserAddress yhsjUserAddress = yhsjUserAddressService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjUserAddress);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjUserAddress> list = yhsjUserAddressService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
