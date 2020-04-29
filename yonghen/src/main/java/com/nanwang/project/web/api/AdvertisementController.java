package com.nanwang.project.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nanwang.project.core.Result;
import com.nanwang.project.core.ResultGenerator;
import com.nanwang.project.model.YhsjAdvertisement;
import com.nanwang.project.service.YhsjAdvertisementService;

import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("advertisementApi")
public class AdvertisementController {
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
    @Resource
    private YhsjAdvertisementService advertisementService;
    
    /**
     * 方法描述:查询广告轮播图
      */
    @RequestMapping("/getAdvertisement")
    public Result getAdvertisement(HttpServletRequest request,HttpServletResponse response,YhsjAdvertisement advertisement) {
    	logger.info("开始查询广告轮播图");
		
        Condition condition = new Condition(YhsjAdvertisement.class);
		Example.Criteria criteria = condition.createCriteria();

		if (advertisement.getAdvertisementChannel() != null) {
			criteria.andCondition(" advertisement_channel = " +advertisement.getAdvertisementChannel());
		}
		criteria.andCondition(" advertisement_status = 1" );
		condition.setOrderByClause("advertisement_sort asc");
		int totalCount = advertisementService.countByCondition(condition);
		List<YhsjAdvertisement> returnlist = new ArrayList<YhsjAdvertisement>();
		if(totalCount>0){
			returnlist = advertisementService.findByCondition(condition);
		}
        Result result = ResultGenerator.genSuccessResult();
        result.setTotalCount(totalCount);
        result.setItems(returnlist);
        logger.info("结束查询广告轮播图");
        return result;
    }
}
