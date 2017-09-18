package com.ruanyun.web.util;

public class Constants {
	
	public static String FILE_SEPARATOR=System.getProperty("file.separator");

    /**
     * url 地址配置
     */
    public static  String URL_PATH = "/url.properties";
    

    /**
     * email 地址配置
     */
    public static  String EMAILCONFIG_PATH = "/emailconfig.properties";

    /**
     * 页面消息配置 路径
     */
    public static  String PAGECONFIG_PATH = "/pagemessage.properties";

    /**
     * 短信通知配置文件
     */
    public static final String SMS_PATH = "/sms.properties";
    
    /**
     * 用户的session key值
     */
    public static  String SESSION_KEY_USERINFO = "systemUser";
    
    /**
     * 用户的session key值
     */
    public static  String SESSION_KEY_USERAPP = "user";

    /**
     * 微信登录session key值
     */
    public static final String SESSION_KEY_WEIXIN_USERINFO = "systemWeixinUser";
    
    /**
     * 微信用户的session key值
     */
    public static final String SESSION_KEY_WEIXIN_OPEN_ID = "systemWeixinOpenId";
    public static final String SESSION_KEY_WEIXIN_ACCESS_TOKEN = "systemWeixinAccessToken";
    
    /**
     * 微信用户的session key值
     */
    public static final String SESSION_KEY_WEIXIN_USER = "systemWeixinUser";


   /**
    *商品图片 
    */
	public static final String FILE_IMG = "file"+FILE_SEPARATOR+"img"+FILE_SEPARATOR;//商品图片
	/**
	 *手机用户图像 
	 */
	public static final String FILE_HEAD_IMG = "file"+FILE_SEPARATOR+"userapp"+FILE_SEPARATOR;//商品图片
    
    /**
     * 左边菜单url sessionKEY
     */
    public static  String SEESION_KEY_LEFTURLS = "leftUrls";

    /**
     * 机构状态
     */
    public static  int ORG_STATUS = 1;

    /**
     * 默认角色
     */
    public static  int ROLE_DEFAULT = 1;

 

    /**
     * 权限类型URL
     */
    public static  String AUTHORITY_TYPE_URL = "1";

    /**
     * 权限类型 权限
     */
    public static  String AUTHORITY_TYPE_AUTH = "2";


    /**
     * 新加用户默认密码
     */
    public static  String USER_DEFULT_PASSWORD = "q";

    /**
     * 上传文件最大50
     */
    public static  int FILE_MAX_SIZE_FILE = 1000 * 1000 * 1000;
    /**
     * 上传文件名长度，最大30
     */
    public static  int FILE_NAME_MAX_SIZE=30;
    /**
	 * 没有选择文件
	 */
	public static  int FILE_ERROR_NOFILE=-5;   
	/**
	 * 文件名太长
	 */
	public static  int FILE_ERROR_MOREFILENAME=-2;   
                       
	/**
	 * 文件格式不正确
	 */
	public static  int FILE_ERROR_NOTYPE=-3;  
	
	/**
	 * 文件太大
	 */
	public static  int FILE_ERROR_MOREFILESIZE=-4; 
	
	/**
	 * 文件上传证成功
	 */
	public static  int FILE_SUCCESS=1;  
	
	/**
	 * 文件上传失败
	 */
	public static  int FILE_FAIL=-1;		
	

    /**
     * 全局通用正常状态
     */
    public static  int GLOBAL_STATUS = 1;
    /**
     * 全局通用删除状态
     */
    public static  int GLOBAL_DEL = 0;
    /**
     * 手机端返回access_token 判断账户是否异常
     */
    public static  String  APP_ACCESS_TOKEN = "access_token";
    /**
     * 手机端返回成功状态
     */
    public static  int APP_SUCCESS = 1;
    /**
     * 手机端返回失败状态
     */
    public static  int APP_ERROR = -1;

    /**
     * 需要的点号
     */
    public static  String FILE_BIT = ".";

    /**
     * 需要的逗号
     */
    public static  String FILE_COMMA = ",";
    
    /**
     * 需要的负号
     */
    public static  String SUBTRACT = "-";
    
    /**
     * 兑换类型图片路劲
     */

    public static  String FILE_GOODS_TYPE = "file"+FILE_SEPARATOR+"goodstype"+FILE_SEPARATOR;
    /**
     * 广告图片
     */

    public static  String FILE_ADVER_IMG = "file"+FILE_SEPARATOR+"adver"+FILE_SEPARATOR+"img"+FILE_SEPARATOR;
    
    /**
     * 广告图片
     */

    public static  String FILE_ADVER_APK = "file"+FILE_SEPARATOR+"adver"+FILE_SEPARATOR+"apk"+FILE_SEPARATOR;
    
    /**
     * 活动图片
     */

    public static  String FILE_ACTIVITY = "file"+FILE_SEPARATOR+"activity"+FILE_SEPARATOR;
    
    /**
     * 图片---用户头像路劲
     */
    
    public static  String FILE_USER_PHOTO = "file"+FILE_SEPARATOR+"userphoto"+FILE_SEPARATOR;

   
	 /**
	 * xhEditor编辑器文件上传路径
	 */
	public static  String XHEDITOR_UPLOAD_PATH = "file"+FILE_SEPARATOR+"xheditor"+FILE_SEPARATOR;
	
	/**
	 * 图片压缩比例的宽度compression ratio
	 */
	public static  int PIC_COMPRESS_RATIO=350;
  
	
	 //==============================JQYERY UI数据操作返回值=================================================================

    /**
     * JQYERY UI 表单提交 返回值--正常状态 statusCode
     */
    public static  String STATUS_SUCCESS_CODE = "200";
    /**
     * JQYERY UI 表单提交 返回值--失败状态 statusCode
     */
    public static  String STATUS_FAILD_CODE = "300";
    /**
     * JQYERY UI 表单提交 返回值--会话失效状态 statusCode
     */
    public static  String STATUS_FAILSESSION_CODE = "301";
    /**
     * JQYERY UI 提示信息-- 返回 成功提示信息 message
     */
    public static  String MESSAGE_SUCCESS = "数据操作成功";
    /**
     * JQYERY UI 提示信息-- 返回 失败提示信息 message
     */
    public static  String MESSAGE_FAILED = "数据操作失败";
   
    //=========================================================================================================
   
    
    /**
     * 积分兑换审核状态--审核失败
     */
    public static  int SCORE_FAIL_AUDIT_STATUS=-1;
    
    /**
     * 积分兑换审核状态--未审核
     */
    public static  int SCORE_NOT_AUDIT_STATUS=0;
    
    /**
     * 积分兑换审核状态--审核通过
     */
    public static  int SCORE_PASS_AUDIT_STATUS=1;
    
    /**
     * 积分兑换审核状态--已提现
     */
    public static  int SCORE_SUCCESS_AUDIT_STATUS=2;
    
    /**
     * t_cash表提现类型的ParentCode
     */
    public static  String CASH_TYPE_PARENTCODE ="CASH_TYPE";
    
    
    /**
     * 任务所属类型----联盟接口类型
     */
    public static  int TASK_TYPE=1;
    
  //============================================一下数据在每次启动时通过数据库重新赋值==============================================================
    /**
     * 有米积分墙接口1
     */
    public static  String YOUMI_PORT = "c4ca4238a0b923820dcc509a6f75849b";

    /**
     *力美积分墙接口2
     */
    public static  String LIMEI_PORT = "c81e728d9d4c2f636f067f89cc14862c";

    /**
     * 多盟积分墙接口3
     */
    public static  String DOMO_PORT = "eccbc87e4b5ce2fe28308fd9f2a7baf3";

    /**
     * 米迪积分墙接口4
     */
    public static  String MIDI_PORT= "a87ff679a2f3e71d9181a67b7542122c";

    /**
     * 艾德思奇积分墙接口5
     */
    public static  String ADSQ_PORT = "e4da3b7fbbce2345d7772b0674a318d5";

    /**
     * 易积分 积分墙接口6
     */
    public static  String YIJIFEN_PORT = "1679091c5a880faf6fb5e6087eb1b2dc";

    /**
     * 安沃积分墙接口7
     */
    public static  String ADWO_PORT = "8f14e45fceea167a5a36dedd4bea2543";

    /**
     * 点入积分墙接口8
     */
    public static  String DIANRU_PORT = "c9f0f895fb98ab9159f51fd0297e236d";

    /**
     * 酷果积分墙接口9
     */
    public static  String KUGUO_PORT = "45c48cce2e2d7fbdea1afc51c7c6ad26";
    
    /**
     * adView积分墙接口10
     */
    public static  String ADVIEW_PORT = "d3d9446802a44259755d38e6d163e820";
    
    /**
     * 磨盘 积分墙接口11
     */
    public static  String MOPAN_PORT = "6512bd43d9caa6e02c990b0a82652dca";
    
    /**
     * 触控广告积分墙接口10
     */
    public static  String CHUKONG_PORT = "c20ad4d76fe97759aa27a0c99bff6710";
    
    /**
     * 用户兑换总额人工审核限制
     */
    public static  int exchangeMoney=50;
    
    /**
     * 同一用户每天限制兑换金额的次数
     */
    public static  int EXCHANGE_COUNT =3;
    
    
    /**
     * 用户积分上线
     */
    public static  int MAX_SCORE=5000;
    
    /**
     * 每个渠道使用次数
     */
    public static  int MAX_PORT_COUNT=5;
    
    /**
     * 百度APIKEY
     */
    public static   String APIKEY="lbRrSxqGAYwk6hWt3vEdxZG7";
    /**
     * 百度SECRETKEY
     */
    public static   String SECRETKEY="p2fV1eTe1BgBwGo7bjSjcVUQWuqohEfs";
    
    /**
     * 邀请人的得分率
     */
    public static float PARENT_SCORE=0.0f;
    
   
    
    /**
     * 微信url 地址配置
     */
    public static  String WEIXIN_URL_PATH = "http://www.hefeiruanyun.com/syrmall";
    
    /**
     * 微信服务号AppId
     */
    public static String appid = "wx25cd4afae90315ee";

    /**
     * 微信服务号AppSecret
     */
    public static String appsecret = "4945fcdc76657ace4a17ec4c8e4f3c5d";

    /**
     * 通过微信绑定时,用来存放短信验证码和用户的手机号
     */
    public static String validateContent = "validateContent";
    
    /**
     * 广告得分
     */
    public static Integer USER_SCORE_INFO_TYPE_1=1;
    
    /**
     * 签到得分
     */
    public static Integer USER_SCORE_INFO_TYPE_2=2;
    
    /**
     * 兑换类型分数
     */
    public static Integer USER_SCORE_INFO_TYPE_3=3;
    
    /**
     * 注册奖励得分
     */
    public static Integer USER_SCORE_INFO_TYPE_4=4;
    
    /**
     * 徒弟奖励得分
     */
    public static Integer USER_SCORE_INFO_TYPE_5=5;
    /**
     * 商品兑换失败退还分
     */
    public static Integer USER_SCORE_INFO_TYPE_6=6;
    
    /**
     * 商品兑换成功 店铺加分
     */
    public static Integer USER_SCORE_INFO_TYPE_7=7;
    
    /**
     * 抽奖得分
     */
    public static Integer USER_SCORE_INFO_TYPE_8=8;
    
    
    /**
     * 用户文章任务得分
     */
    public static Integer USER_SCORE_INFO_TYPE_9=9;
    
    /**
     * 新手任务扥分
     */
    public static Integer USER_SCORE_INFO_TYPE_10=10;
    
    /**
     * 等级默认比例
     */
    public static int LEVEL_DEFAULT_VALUE=100;
    /**
     * 用户类型  1 后台用户类型
     */
    public static int USER_TYPE_PC=1;
    /**
     * 用户类型  2 app用户类型
     */
    public static int USER_TYPE_APP=2;
    // 徒弟下级
    public static int APPRENTICE_LEVEL=2;
    /**
     * 填写邀请人奖励
     */
    public static Float USER_SCORE_INFO_TYPE_INVITED_YES=100f;
    /**
     * 不填写邀请人奖励
     */
    public static Float USER_SCORE_INFO_TYPE_INVITED_NO=50f;
    
    /**
     * 新手任务奖励
     */
    public static Float TASK_NEW_NO=200f;
    
    
    /**
     * 未审核通过
     */
    public static int ADVER_USER_STEP_STATUS_NO=-1;
    /**
     * 审核OK
     */
    public static int ADVER_USER_STEP_STATUS_YES=1;
    
    public static String CHANNEL_STEP_NUM_INSTALL="CASU_INSTALL_0";
    
    /**
     * 用户广告完成状态  未完成
     */
    public static int ADVER_USER_STATUS_NO=-1;
    /**
     * 用户广告完成状态  已完成
     */
    public static int ADVER_USER_STATUS_YES=1;
    
    /**
     * 广告主通知
     */
    public static String EFFECTIVE_SOURCE_1="1";
    /**
     * 平台通知
     */
    public static String EFFECTIVE_SOURCE_2="2";
    
    /**
     * 签到获得的积分
     */
    public static Float USER_SIGN_SCORCE=10.0f;
    
    /**
     * 参数名称对应
     */
    public static String PARAMETER_TYPE="PARAMETER_TYPE";
    /**
     * 服务器方回调
     */
    public static Integer PARAMETER_INFERFACE_REQUEST_TYPE_SERVER=1;
    /**
     * 客户端回调我们
     */
    public static Integer PARAMETER_INFERFACE_REQUEST_TYPE_CLIENT=2;
    
    /**
     * 广告接口
     */
    public static String PARAMETER_INFERFACE_TYPE_ADVER="1";
    /**
     * 渠道接口
     */
    public static String PARAMETER_INFERFACE_TYPE_CHANNEL="2";
    
    /**
     * 积分比例
     */
    public static Integer SCORE_PROPORTION=100;
    /**
     * 对吧的appkey
     */
    public static String DUIBA_APP_KEY="29V7dzFbWkTN1WEvFLpwdw9bUF8v";
    /**
     * 对吧的APP_SECRET
     */
    public static String DUIBA_APP_SECRET="3n12tGu5WMUemrGh1yfLTHbCg7Kc";
    
    /*************************请求参数开始****************************************/
    
    /**
     * 用户编号
     */
    public static String PARAMETER_USERNUM="userNum"; 
    /**
     * 手机序列号激活唯一码
     */
    public static String PARAMETER_PHONESERIALNUMBER="phoneSerialNumber";
    /**
     * 手机串号
     */
    public static String PARAMETER_IMEI="imei";
    /**
     * 广告标识符
     */
    public static String PARAMETER_IDFA="idfa";
    public static String PARAMETER_IP="ip";
    public static String PARAMETER_APPSTORENAME="appStoreName";
    /**
     * 系统版本号
     */
    public static String PARAMETER_SYSTEMVERSION="systemVersion";
    /**
     * 终端唯一标识符
     */
    public static String PARAMETER_OPENUDID="openudid";
    /**
     * mac地址
     */
    public static String PARAMETER_MAC="mac";
    /**
     * 手机型号
     */
    public static String PARAMETER_PHONEMODEL="phoneModel";
    /**
     * 错误回调返回的值
     */
    public static String PARAMETER_ERROR="ERROR";
    /**
     * 回调成功返回的值
     */
    public static String PARAMETER_SUCCESS="SUCCESS";
    
    /**
     * 回调 广告名称
     */
    public static String PARAMETER_ADVER_NAME="ADVER_NAME";
    
    /**
     * 回调 记录分数
     */
    public static String PARAMETER_ADVER_SCORE="score";
    
    /**
     * 回调返回的唯一值
     */
    public static String PARAMETER_UNIQUEPRIMARYKEY="uniquePrimaryKey";
    /*************************请求参数结束****************************************/
    
    /**用户类型**/
    public static int USER_APPP_TYPE_1=1;//普通用户
    public static int USER_APPP_TYPE_2=2;//学生
    
    /**学生信息审核状态**/
    public static int CART_STATUS_1=1;//审核中
    public static int CART_STATUS_2=2;//审核通过
    public static int CART_STATUS_1_=3;//审核失败
    

}
