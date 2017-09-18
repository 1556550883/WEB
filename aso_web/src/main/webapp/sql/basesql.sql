

DROP TABLE IF EXISTS `t_authority`;

CREATE TABLE `t_authority` (
  `auth_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `auth_code` varchar(100) DEFAULT NULL COMMENT '权限CODE业务主键',
  `parent_auth_code` varchar(100) DEFAULT NULL COMMENT '父权限',
  `auth_name` varchar(100) DEFAULT NULL COMMENT '权限名称',
  `auth_url` varchar(100) DEFAULT NULL COMMENT '访问路径',
  `auth_type` varchar(100) DEFAULT NULL COMMENT '类型',
  `auth_orderby` int(11) DEFAULT '100' COMMENT '访问顺序',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `request_type` varchar(100) DEFAULT NULL COMMENT '请求类型（1--pc 2--手机  -- 所有）',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8 COMMENT='权限表';

insert  into `t_authority`(`auth_id`,`auth_code`,`parent_auth_code`,`auth_name`,`auth_url`,`auth_type`,`auth_orderby`,`createdate`,`request_type`) values (14,'A_1000','A_199','主菜单','213','1',1,NULL,'1'),(15,'A_1015','A_1000','权限管理','#','1',1,NULL,'1'),(16,'A_1016','A_1015','权限菜单管理','auth/auths','1',1,NULL,'1'),(18,'A_1018','A_1000','角色管理','#','1',2,NULL,'1'),(19,'A_1019','A_1018','角色管理','role/roles','1',1,NULL,'1'),(20,'A_1020','A_1000','用户管理','#','1',4,NULL,'1'),(21,'A_1021','A_1020','用户管理','user/users','1',1,NULL,'1'),(40,'A_1040','A_1020','增加高级用户','','2',100,NULL,'1'),(55,'A_1055','A_1000','系统管理','#','1',3,NULL,'1'),(56,'A_1056','A_1055','登录日志','loginlog/getlist','1',1,NULL,'1'),(62,'A_1062','A_1055','操作日志','operationLog/getlist','1',2,NULL,'1'),(76,'A_1076','A_1055','导出权限','','2',0,NULL,'1'),(85,'A_199','A_0000','金赚','#','1',100,NULL,'1'),(108,'A_1108','A_1055','组织管理','org/orgList','1',4,NULL,'3'),(129,'A_1129','A_1055','更新缓存','cacheOperate/toUpdate','1',5,'2014-06-10 11:20:25','3');

DROP TABLE IF EXISTS `t_dictionary`;

CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_code` varchar(20) DEFAULT NULL COMMENT '父类型编码',
  `parent_name` varchar(200) DEFAULT NULL COMMENT '父类型名称',
  `item_code` varchar(50) DEFAULT NULL COMMENT '编码',
  `item_name` varchar(200) DEFAULT NULL COMMENT '名称',
  `orderby` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '状态',
  `flag1` varchar(100) DEFAULT NULL COMMENT 'flag1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8 COMMENT='字典表';

insert  into `t_dictionary`(`id`,`parent_code`,`parent_name`,`item_code`,`item_name`,`orderby`,`status`,`flag1`) values (1,'USERSEX','性别','1','男',1,1,NULL),(2,'USERSEX','性别','2','女',2,1,NULL),(3,'USERSTATUS','用户状态','1','正常',1,1,NULL),(4,'USERSTATUS','用户状态','0','禁用',2,1,NULL),(5,'USERSTATUS','用户状态','0','删除',3,1,NULL),(6,'AUTH_TYPE','权限类型','1','菜单',1,1,NULL),(7,'AUTH_TYPE','权限类型','2','权限',2,1,NULL),(8,'AUTH_REQUEST_TYPE','权限访问类型','1','电脑权限',2,1,NULL),(9,'AUTH_REQUEST_TYPE','权限访问类型','2','手机权限',3,1,NULL),(10,'AUTH_REQUEST_TYPE','权限访问类型','3','全部',1,1,NULL),(11,'TASK_STATUS','任务状态','1','启用',2,1,NULL),(12,'TASK_STATUS','任务状态','0','冻结',1,1,NULL);

DROP TABLE IF EXISTS `t_login_log`;

CREATE TABLE `t_login_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录日志表主键',
  `log_ip` varchar(100) DEFAULT NULL COMMENT '登录ip',
  `log_user_id` int(11) DEFAULT NULL COMMENT '登陆人id',
  `login_name` varchar(500) DEFAULT NULL COMMENT '登陆名称',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=277 DEFAULT CHARSET=utf8 COMMENT='登录日志表';


insert  into `t_login_log`(`log_id`,`log_ip`,`log_user_id`,`login_name`,`login_time`) values (272,'127.0.0.1',1,'admin','2014-06-16 18:02:33'),(273,'127.0.0.1',1,'admin','2014-06-16 18:03:47'),(274,'127.0.0.1',1,'admin','2014-06-16 18:06:08'),(275,'127.0.0.1',1,'admin','2014-06-16 18:12:58'),(276,'127.0.0.1',1,'admin','2014-06-16 18:18:58');



DROP TABLE IF EXISTS `t_operation_log`;

CREATE TABLE `t_operation_log` (
  `operation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `operation_name` varchar(100) DEFAULT NULL COMMENT '操作 名称',
  `operation_content` varchar(2000) DEFAULT NULL COMMENT '操作内容',
  `createdate` datetime DEFAULT NULL COMMENT '操作日期',
  `operation_user` int(11) DEFAULT NULL COMMENT '操作人',
  `operation_user_name` varchar(100) DEFAULT NULL COMMENT '操作人名称',
  `operation_type` varchar(100) DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`operation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';



DROP TABLE IF EXISTS `t_org`;

CREATE TABLE `t_org` (
  `org_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_code` varchar(100) DEFAULT NULL COMMENT '组织code',
  `parent_org_code` varchar(100) DEFAULT NULL COMMENT '组织父类code',
  `org_name` varchar(300) DEFAULT NULL COMMENT '组织名称',
  `create_code` int(11) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `org_order` int(11) DEFAULT NULL COMMENT '排序字段',
  `status` int(11) DEFAULT NULL COMMENT '1--正常  0--删除',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='所属机构表';

/*Data for the table `t_org` */

insert  into `t_org`(`org_id`,`org_code`,`parent_org_code`,`org_name`,`create_code`,`create_date`,`org_order`,`status`) values (1,'R_1000','R_199','安徽软云',1,'2013-12-20 13:41:19',1,1);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(500) DEFAULT NULL COMMENT '角色名称',
  `orderby` int(11) DEFAULT '100',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `t_role` */

insert  into `t_role`(`role_id`,`role_name`,`orderby`) values (1,'系统管理员',1);

/*Table structure for table `t_role_authority` */

DROP TABLE IF EXISTS `t_role_authority`;

CREATE TABLE `t_role_authority` (
  `role_authority_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `auth_code` varchar(100) DEFAULT NULL COMMENT '权限code',
  `role_id` int(11) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`role_authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2313 DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

/*Data for the table `t_role_authority` */

insert  into `t_role_authority`(`role_authority_id`,`auth_code`,`role_id`) values (2287,'A_1021',1),(2291,'A_1055',1),(2293,'A_1040',1),(2294,'A_1015',1),(2295,'A_1000',1),(2296,'A_1019',1),(2297,'A_1020',1),(2301,'A_1056',1),(2304,'A_199',1),(2305,'A_1129',1),(2310,'A_1076',1),(2311,'A_1016',1),(2312,'A_1018',1);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id--主键',
  `login_name` varchar(100) DEFAULT NULL COMMENT '用户登录名称',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户真实姓名',
  `login_pass` varchar(100) DEFAULT NULL COMMENT '登录密码',
  `unit_name` varchar(200) DEFAULT NULL COMMENT '单位名称',
  `org_id` int(11) DEFAULT NULL COMMENT '所属机构的id',
  `user_sex` int(11) DEFAULT NULL COMMENT '用户性别 1/2 男/女',
  `user_phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `address` varchar(2000) DEFAULT NULL COMMENT '地址',
  `user_email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `create_code` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `user_status` int(11) DEFAULT NULL COMMENT '用户状态 1/0 正常/禁用',
  `access_token` varchar(200) DEFAULT NULL COMMENT '验证密码',
  `user_photo` varchar(200) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`user_id`,`login_name`,`user_name`,`login_pass`,`unit_name`,`org_id`,`user_sex`,`user_phone`,`address`,`user_email`,`create_code`,`create_date`,`user_status`,`access_token`,`user_photo`) values (1,'admin','系统管理员','21232f297a57a5a743894a0e4a801fc3','安徽省委省委讲师团',1,1,'15556468221','安徽合肥','www.ahsw@ahsw.com',1,'2013-12-18 00:00:00',1,'06fd107ad92846ece31845c04998e53f','20140325103224917.png');

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关联表主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`user_role_id`),
  KEY `FK_PK_ROLEUSER_USER` (`user_id`),
  KEY `FK_T_USERROLE_ROLE` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

insert  into `t_user_role`(`user_role_id`,`user_id`,`role_id`) values (20,1,1);
