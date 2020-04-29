package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjProductTp;
import com.sanposs.project.service.YhsjProductTpService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/05/08.
*/
@RestController
@RequestMapping("/yhsj/product/tp")
public class YhsjProductTpController {
    @Resource
    private YhsjProductTpService yhsjProductTpService;

    @RequestMapping("/add")
    public Result add(YhsjProductTp yhsjProductTp) {
        yhsjProductTpService.save(yhsjProductTp);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjProductTpService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjProductTp yhsjProductTp) {
        yhsjProductTpService.update(yhsjProductTp);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjProductTp yhsjProductTp = yhsjProductTpService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjProductTp);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjProductTp> list = yhsjProductTpService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
