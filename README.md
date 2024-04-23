### 2023-11-30 ver 1.5.0
* feature：增加了小程序对接
* feature：基础包增加方法，需要覆盖

### 2023-11-23 ver 1.4.0
* 增加了很多字段，因此需要做数据补丁
* df_wms_product_delivery_order.proxy = false/true
* df_wms_product_delivery_order.type = OO/OM/MM


### 2023-11-11 ver 1.3.3
* feature：废弃与快递商直接对接方式，修改为轻流对接
* 其它一些业务逻辑调整

### 2023-10-31 ver 1.3.1
* feature：增加了BeeThere快递接口
  * 新增了配置df.feature.c3x.*
* feature：增加了电商商品入库、出库功能
* bugfix：框架代码bug处理，需要重新安装框架jar
* bugfix: 因为区分了管理员与用户，导致前期留下了的口子需要封住，重新处理了权限
  * 创建一个客户角色，创建一个打印员角色，给这些角色重新分配权限
  * 管理员角色增加权限
  * 因为修正了权限，所有用户需要重新登录

### 2023-10-31 ver 1.3.0
* feature：增加了出库订单的导入功能，区分管理员与用户

### 2023-10-31 ver 1.2.0
* feature：字段名变更(TEST\PRO环境)
* feature：打印条码样式变更
* feature：print调用飞鹅需要调整间隔时间
* feature：在“Undo”列表页，加“Print”按钮，并且在相关表上增加print字段用以标记
* bugfix：框架代码bug处理，需要重新安装框架jar

### 2023-10-21 ver 1.1.1
* feature：增加飞书互动，修改了配置文件
  * 新增了配置df.feature.lark.*
  * 飞书需要在机器人里面配置相关信息
* feature：增加轻流订单交互，增加了表，修改了配置文件
  * 新增了表，详见602_db_dicfree_wms_patch02.sql
  * 配置key修改df.feature.feie.userKey -> df.feature.feie.user-key
* feature：增加了用户多地登录控制，修改了配置文件
  * 新增了配置yqfw.multiple-login
  * 新增了配置yqfw.multiple-login-reserved
* bugfix：飞鹅国际版与中国版差异处理，修改了配置文件
  * 删除了配置df.feature.feie.debug
  * 增加了配置df.feature.feie.host，可以配置国内或者国外的飞鹅环境
* bugfix：框架代码bug处理，需要重新安装框架jar

### 2023-09-02 ver 1.0.1（1.1.0）
* feature：增加轻流互动
  * 新增了基础数据，详见602_db_dicfree_wms_patch01.sql

### 2023-08-19 ver 1.0.0
* 新系统上线
* feature：飞鹅打印机互动

60200  客户端等粗颗粒权限
60201  /ads/boxSku/**
60202  /ads/boxArrivalOrder/** ？？？
60203  N.A 菜单出库                 
60204  /ads/boxArrivalOrder/**
60205  /ads/collectionTask/**   60205008/60205009 /ads/boxDeliveryOrder/**错了
60206  N.A 菜单入库
60207  /ads/productDeliveryOrder/**
60208  N.A 菜单出库
60209  /ads/productArrivalOrder/**
60210  N.A 菜单出库校验
60211  N.A 菜单打印
60212  N.A 菜单锁定
60213  /ads/productWaveTask/**
按照这个做法，对于菜单，应该直接分配到1000 0000以前