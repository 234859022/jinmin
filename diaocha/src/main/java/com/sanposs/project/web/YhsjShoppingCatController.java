package com.sanposs.project.web;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultCode;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.model.YhsjShoppingCat;
import com.sanposs.project.pojo.BaseDto;
import com.sanposs.project.pojo.LoginUser;
import com.sanposs.project.pojo.QueryPage;
import com.sanposs.project.service.YhsjShoppingCatService;
import com.sanposs.project.utils.ApiSessinoManager;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

/**
* Created by CodeGenerator on 2019/05/11.
*/
@RestController
@RequestMapping("/yhsj/shopping/cat")
public class YhsjShoppingCatController {
	private static final Logger logger = LoggerFactory.getLogger(YhsjShoppingCatController.class);
    @Resource
    private YhsjShoppingCatService yhsjShoppingCatService;

    @RequestMapping("/add")
    public Result add(YhsjShoppingCat yhsjShoppingCat) {
        yhsjShoppingCatService.save(yhsjShoppingCat);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        yhsjShoppingCatService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(YhsjShoppingCat yhsjShoppingCat) {
        yhsjShoppingCatService.update(yhsjShoppingCat);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        YhsjShoppingCat yhsjShoppingCat = yhsjShoppingCatService.findById(id);
        return ResultGenerator.genSuccessResult(yhsjShoppingCat);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<YhsjShoppingCat> list = yhsjShoppingCatService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
