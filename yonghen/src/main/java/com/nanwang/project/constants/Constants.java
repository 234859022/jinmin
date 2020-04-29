package com.nanwang.project.constants;

/**
 * 常量定义
 * @author aaron
 *
 */
public class Constants {

	/**
	 * 默认的编码
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 默认的日期时间格式
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	/**
	 * 默认的分隔符
	 */
	public static final String STRINGSPLIT = ",";

	public static final String ExistPhone = "存在相同手机号码的用户!";
	public static final String VerifyError = "验证码错误!";
	public static final String RegistSuccess = "注册成功!";
	public static final String RegistFail = "注册失败!";

	public static final String LoginFailErr = "用户名或者密码不能为空!";
	public static final String LoginFailPhoneErr = "登录失败，用户名（手机号码）或密码错误!";
	public static final String GetLoginUserErr = "获取用户信息错误!";
	public static final String LoginFailVerify = "没有查询后台数据权限，请联系管理员!";
	public static final String LoginFailVerify2 = "车场不存在或者车场未通过审核!";
	
	
	public static final String LoginFailPasswordErr = "登录失败，密码错误!";
	public static final String ChangeFailPasswordErr = "失败，密码错误!";
	public static final String ChangeFailUserErr = "失败，用户错误!";
	public static final String ChangeFailUserErr2 = "失败，用户已删除!";
	
	
	//*************************ParkCar*************************
	public static final String PlateNumRepeatErr = "该车牌号的车辆已经存在!";
	//*************************ParkCar*************************
	
	//*************************ParkPlace*************************
	public static final String LockErr = "设备ID不能为空!";
	public static final String LockExitErr = "设备编号不存在!";
	public static final String LockUseErr = "该设备已经被使用!";
	public static final String PlaceShareErr = "共享订单尚未结束，无法切换状态!";
	public static final String PlaceHaveErr = "当前还有车辆在使用该车位，无法切换状态!";
	public static final String PlaceIsUseErr = "该车位已经被使用!";
	public static final String PlaceIsOverMaxErr = "您已抢占太多车位,该车位无法抢占使用!";
	public static final String PlaceIsNoOverSpanErr = "您已频繁抢占车位,该车位无法抢占使用!请等待";
	//*************************ParkPlace*************************

	public static final String Success = "成功!";
	public static final String Fail = "失败!";
	public static final String UserBinded = "openid已经绑定";
	public static final String lockcodeBinded = "设备编号已经存在";

	public static final String shareInvite = "已经邀请过该用户，请换其他手机号";
	public static final String CouponSystem = "系统!";

	public static final String CouponIsOVerDay = "已过期!";
	public static final String CouponNoOVerDay= "未过期";
	public static final String CoponCannotUse = "不满足优惠券使用条件，金额不到抵用限制费用!";

	public static int cardpass = 0;//不使用没有效
	public static int chargeTypeTimes = 2;//按次计费的类型
	public static int active = 1;//生效
	public static int noactive = 2;//失效
	
	public static int noCompany = 1;//非商户
	public static int company = 2;//商户
	public static int companying = 3;//商户审核中
	
	public static int nodelete = 1;//未删除
	public static int isdelete = 2;//删除
	
	public static int parking = 1;//停车记录生效
	public static int noparking = 2;//停车记录失效

	public static int flowNoEffect = 1;//未确认
	public static int flowEffect = 2;//已确认
	
	public static final String LockCodeExitErro = "lockCode已经存在!";
	public static int lockState = 1;//正常
	public static int lockNoState = 2;//故障
	public static int lockDelState = 3;//删除
	
	public static int switchOpen = 1;//正常
	public static int switchClose = 2;//故障
	
	public static int parkNoShareState = 1;//正常
	public static int parkShareingState = 2;//故障

	public static int ruleTypeA = 1;//:1 按照时间段
	public static int ruleTypeB = 2;// 2:按次数收费
	public static int ruleTypeC = 3;// 3:按每小时收费


	public static int ticketTypeA = 1;//:1 代金券，抵用券
	public static int ticketTypeB = 2;// 2:月卡
	public static int ticketTypeC = 3;// 3:系统优惠券

	public static int ticketNotUsed = 1; //未使用
	public static int ticketUsed = 2;//已使用

	public static int ticketPass = 1;
	public static int ticketFromTick= 1;//来源park_ticket表
	public static int ticketFromCoupon= 2;//来源park_coupon表

	public static final String InviteTitle = "添加共享人";

	public static String errorMobileEmpty = "手机号码为空";//
	public static String errorTokenEmpty = "token为空";//
	public static String chargeFlow = "充值记录流水";//充值记录
	public static String consumeFlow = "消费记录";//充值记录

	public static String chargeSuc = "充值成功";//充值成功
	public static String chargeFail = "充值失败";//充值失败

	public static String parkConsume = "停车记录";//充值失败
	public static String defaultPwd = "123456";//用户pwd
	public static String verifyCodeExpire = "验证码过期";//

	public static String verifyMobileEmpty = "手机号为空";//
	public static String verifyMobileExist = "手机号已经存在";//
	public static String verifyMobileError = "验证码错误";//
	public static String verifyMobileOk = "验证成功";//

	public static String existParklog = "车位已经被抢";//
	public static String leaveParklog = "车辆已经离开车位";//

	public static String SystemMsgBody = "系统消息";
	public static String lockMsgBody = "地锁消息";
	public static String ruleMsgBody = "规则审核消息";
	public static String reportMsgBody = "设备故障消息";
	public static String companyMsgBody = "商户审核消息";
	public static final String UserNofindByMobile = "不存在绑定该手机号用户";
	public static final String UserIsSelf = "不能邀请该手机号用户";
	public static final String UserIsShared = "不能邀请该手机号用户";
	public static final String Accept = "同意";

	public static String parkNoShare = "车位已经下线";//
	public static String parkCarNo = "未知车牌";//
	public static String normalTimeCode = "normal";//
	public static String nightTimeCode = "night";//

	public static String nightTimeSpan = "nighttimespan";//
	public static String nightTimeMoney = "nighttimemoney";//

	public static String parkPunishMoneyLine = "punishmoneyline";//违约离开金额
	public static String parkMoneyLine = "moneyline";//钱包最少保留余额
	public static String nightTime = "nighttime";//夜晚时间
	public static String nighttimemoney = "nighttimemoney";//夜晚收费
	public static String parkMoneyLineValue = "50";//钱包最少保留余额
	public static String maxparkuse = "maxparkuse";//最多可同时抢占多少
	public static String parkspantime = "parkspantime";//抢占间隔
	public static String cancelpark = "cancelpark";//允许取消停车
	public static String cancelparktime = "cancelparktime";//允许取消停车的时长
	public static String nightTimeBegin = "22:00:00";//
	public static String nightTimeEnd = "06:00:00";//
	public static String paySwitch = "payswitch";//连接设备可支付
	public static int existParklogCode = 1;//车位已经被抢";
	public static int noexistParklogCode = 0;//车位未被抢";
	public static int offlineCode = 2;//车位已经下线";

	public static int ParkPlaceNoCar = 1;//车位没车";
	public static int ParkPlaceHaveCar = 2;//车位有车";
	public static int successStatus = 200;//成功

	public static int MsgTypeInvite = 100;//添加共享人消息类型
	public static int RentMsgType = 1;//租用、还车位消息
	public static int SystemMsgType = 0;//系统消息

	public static int UnReadStatus = 1;//未读
	public static int ReadStatus = 2;//已读

	public static long expireVerifySeconds = 360;// 360秒
	public static int expireSeconds = 1800;// 1800秒

	public static int walletDefault = 1;//  普通钱包

	public static double freezeMoneyDefault = 0;//  冻结金额
	public static double initMoneyDefault = 0;//  初始金额

	public static double payLevelTwo = 2;// 收费阶梯 第二级 倍数

	public static int flowTypeCharge = 1;//  充值流水

	public static int flowTypeConsume = 2;//  xiaofei 流水
	public static int deleteStatus = 1;//  默认删除状态,1 未删除 2 已删除
	
	public static int IsSystem = 1;
	public static int IsUser = 2;
	
	public static int AuditStatusIng = 1;//审核中
	public static int IsAuditStatus = 2;//审核成功
	public static int NoAuditStatus = 3;//审核失败
	
	public static int companyParkNum = 4;//商户最低车位要求
	
	public static String SanPossOrderUrl = "https://tm.banxie.net/carportApi/notifyCarport.shtml";
	public static String SanPossBalanceUrl = "https://tm.banxie.net/carportApi/findAccountByCarportNo.shtml";
	public static String SanPossFlowingWaterUrl = "https://tm.banxie.net/carportApi/findAccountDetailByCarportNo.shtml";
	public static String SanPossKey = "3b4038dbce18457a94ad9299ff3195d4";
	public static String SanPossSecret = "E6A01889A740E276993B5A60EC4DBF95";
	
	/**
	 * RSA公钥
	 */
	public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQKTbS5mdxWMElERuJQ6pWlT6L\n" +
			"R4cK9eu+dZnTeEmU5tsob2CIM79ycUNIaiN7XBZQGh3Xocogfvjk3Z9E6Li8Fxx4\n" +
			"hHuphf0tRyvYIr4K2ROHRbfwu6phm/mZjo4ykdLDGsZEWGpMFNMnF1tzQqxEvIfE\n" +
			"U6akhCT77WGZW9oYMQIDAQAB";

	/**
	 * RSA私钥
	 */
	public static final String RSA_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANApNtLmZ3FYwSUR\n" +
			"G4lDqlaVPotHhwr16751mdN4SZTm2yhvYIgzv3JxQ0hqI3tcFlAaHdehyiB++OTd\n" +
			"n0TouLwXHHiEe6mF/S1HK9givgrZE4dFt/C7qmGb+ZmOjjKR0sMaxkRYakwU0ycX\n" +
			"W3NCrES8h8RTpqSEJPvtYZlb2hgxAgMBAAECgYB0oaMGJ/Jtn70Yd61mC3O1b4nL\n" +
			"oBgaHBAqdtnpkswnkfHZhg4gQl5SUQPR3yZiSAZ4eymdWBlYe8wY79P9WCeouLo4\n" +
			"GOFyZ/Ddw4dvPqJDWjbPrRmv9fZojO0XgQVCH1jlMqZ7F2SYS0oMVe8jdQ1GT41u\n" +
			"XDbFL200A9+/59i+wQJBAO4yBZE8023EC0N+zG8Nn5apLXiKgNgYblyb52q8Svrv\n" +
			"p2h+5y4ZnsJyn5hqJpT55QXf6LONO19KxO9cU4v1XNsCQQDfuHhk9Onib04Lcq/3\n" +
			"1XBNuOwfqCi5ePlb/oP5Ai65Y80yz5QPqFrjtM43ePyPBCuIHVTPP3A9YEUmtX2I\n" +
			"VObjAkEAjKQsdV0c2arvm6K6wregiyzJFGtzOCT0xOmo8nTLMO9gQWrsxWOLQfS8\n" +
			"FxpWPZBvkdBrbdjuPY3ASjSRgAoYEQJBAM3Fh38cQW9S/w7Kiw3CFIx4VT581hBa\n" +
			"M8konUxQdJdyM0/T25RXPloIeFBPiMUmib+lctc4u0zImnITJoZZyQ8CQQCkBV3Q\n" +
			"5UkXCMM908/gSpDN5pjrdCqg0J2dUoK4Bv2QchdvhfC9TbGG2V8GwNZcGQ9tMiVM\n" +
			"1UzDEopjbHj2UuMk";

	/**
	 * 默认编码
	 */
	public static final String DEFAULT_CHARTSET = "UTF-8";

	/**
	 * 获取Session的缓存的key
	 * @param key 存储的KEY
	 * @return    实际的KEY
	 */
	public static String getSessionCacheKey(String key){
		return "SESSIONITEM_" + key;
	}

	/**
	 * 获取Session的缓存的key
	 * @param key 存储的KEY
	 * @return    实际的KEY
	 */
	public static String getLockCacheKey(String key){
		return "LOCK_" + key;
	}

	/**
	 * 获取邀请内容
	 */
	public static String getInviteContent(String userFrom,String userTo){
		return "你好，" + userTo + ",手机号为" + userFrom + "的用户邀请你成为共享人!";
	}

	/**
	 * 获取分享的key
	 * @param key 存储的KEY
	 * @return    实际的KEY
	 */
	public static String getShareCacheKey(String key){
		return "SHARE_" + key;
	}

	/**
	 * 获取校验码的缓存的key
	 * @param key 存储的KEY
	 * @return    实际的KEY
	 */
	public static String getVerifyCacheKey(String key){
		return "VERIFY_" + key;
	}

	/**
	 * 获取校验码的缓存的key 保存发送验证码时间
	 * @param key 存储的KEY
	 * @return    实际的KEY
	 */
	public static String getVerifyTimeCacheKey(String key){
		return "VERIFY_T" + key;
	}

	/**
	 * 获取校验码
	 */
	public static String getVerifyTemplate(String code) {
		return "【尚宝科技】您的注册验证码为：" + code + "。5分钟内有效，请不要泄露。";
	}
	/**
	 *  收费规则审核通过消息
	 */
	public static String getChargeMessage(String key,boolean passFlag){
		return "规则（" + key +") 已经审核,状态" + (passFlag == false?"不通过":"通过");
	}

	/**
	 *  故障上报消息
	 */
	public static String getReportMessage(String key,String reportUser){
		return "车位（" + key +") 有故障产生,用户(" + reportUser +")上报";
	}

	/**
	 *  租用消息
	 */
	public static String getRentMessage(String key){
		return "车位（" + key +") 租用成功";
	}

	/**
	 *  钱包消息
	 */
	public static String getWalletMessage(String key){
		return "系统授予（" + key +"元) 金额";
	}

	/**
	 *  租用消息，结束
	 */
	public static String gettakeMessage(String key){
		return "车位（" + key +") 租用结束";
	}
	/**
	 *  故障消息
	 */
	public static String errMessage(String key){
		String msg = "";
		if(!key.equals("1")) {
			msg = "无法上锁(升起)";
		}else {
			msg = "无法降锁(解锁)";

		}
		return msg;
	}
	/**
	 *  故障消息
	 */
	public static String couponCode(String key){
		return  "COUPON-XX" +key;
	}

	/**
	 *  消息XXX的所有停车位
	 */
	public static String getticketMessage(String key){
		return "车位来源用户（" + key +")";
	}
	
	public static String getcompanyMessage(int state){
		if(state ==2){
			return "尊敬的用户,您本次的商户审核已经通过";
		}
			return "对不起,您本次的商户审核不通过";
	}
}
