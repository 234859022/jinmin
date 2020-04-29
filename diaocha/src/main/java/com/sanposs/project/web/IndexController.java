package com.sanposs.project.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanposs.project.core.Result;
import com.sanposs.project.core.ResultGenerator;
import com.sanposs.project.utils.DateUtils;

/**
 * Created by Administrator on 2019/1/12/012.
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("index")
    public String index(Model model,HttpServletRequest reqPageParameter) {
        model.addAttribute("now", DateUtils.getTimeCurMillis());
        String url = reqPageParameter.getParameter("url");
        String urlName = reqPageParameter.getParameter("urlName");
        logger.info("--------index/index(" + url + ")--------");

        if(null!= url && url.equals("parklog_order")){
            String strPage = reqPageParameter.getParameter("page");
            String strSize = reqPageParameter.getParameter("size");
            int page = Integer.parseInt(strPage==null?"0":strPage);
            int size = Integer.parseInt(strSize==null?"10":strSize);
            String listData = "";

            model.addAttribute("listdata", listData);
        }

        model.addAttribute("urlName", urlName);
        model.addAttribute("redict", url);
        return "index";
    }



    @RequestMapping("geturl")
    public String geturl(Model model,HttpServletRequest reqPageParameter, HttpServletResponse response) {
        model.addAttribute("now", DateUtils.getTimeCurMillis());
        String url = reqPageParameter.getParameter("url");

        logger.info("--------index/geturl(" + url + ")--------");
        Cookie[] cookies = reqPageParameter.getCookies();
        for(int i = 0; cookies != null && i < cookies.length; i++){
            Cookie c = cookies[i];
            if("username".equals(c.getName())){
                response.addCookie(c);
            }
            if("rember".equals(c.getName())){
                response.addCookie(c);
            }
        }

        model.addAttribute("redict", url);
        return url;
    }

    private String setRedict(String parm){
        return  "";
    }


    @RequestMapping("text")
    public String test(Model model,HttpServletRequest reqPageParameter) {
        return "text";
    }
    
    @RequestMapping("/update")
    public Result update(String id) {
        return ResultGenerator.genSuccessResult();
    }
    
    @RequestMapping("breakPage")
    public String breakPage(Model model,String pageName,String param_str, HttpServletResponse response) {
    	if(param_str!=null&&param_str.compareTo("")>0){
    		String[] params=param_str.split("&");
    		if(params.length>0){
    			for(String par:params){
    				String[] pars=par.split("=");
    				if(pars.length>0){
    					model.addAttribute(pars[0], pars[1]);
    				}
    			}
    		}
    	}
        return pageName;
    }
}
