package com.sanposs.project.web;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjEnclosure;
import com.sanposs.project.service.YhsjEnclosureService;
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
@RequestMapping("/yhsj/enclosure")
public class YhsjEnclosureController {
    @Resource
    private YhsjEnclosureService yhsjEnclosureService;

    @RequestMapping("/add")
    public Result add(YhsjEnclosure yhsjEnclosure) {
        yhsjEnclosureService.save(yhsjEnclosure);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjEnclosureService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjEnclosure yhsjEnclosure) {
        yhsjEnclosureService.update(yhsjEnclosure);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjEnclosure yhsjEnclosure = yhsjEnclosureService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjEnclosure);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjEnclosure> list = yhsjEnclosureService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
