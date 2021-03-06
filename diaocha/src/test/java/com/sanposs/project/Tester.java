package com.sanposs.project;


import com.alibaba.druid.support.json.JSONUtils;
import com.sanposs.project.Application;
import com.sanposs.project.utils.HttpTookit;
import com.sanposs.project.utils.HttpUtils;
import com.sanposs.project.utils.UUIDGenerator;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sanposs.project.utils.HttpTookit;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public abstract class Tester {



}



