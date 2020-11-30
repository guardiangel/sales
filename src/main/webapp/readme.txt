一、项目介绍：
    进销存管理系统，从最代码网站http://www.zuidaima.com/下载的源码，
    部分经过调整（有的模块无法正常运行），原作者代码需要maven plugins spring-boot:run方式运行，
    经过调整，可以在Intellij里面调试运行，后续功能界面一一修正，感谢原作者的付出。
    2020-11-25 8:27:51

二、涉及的技术
    Spring boot + spring data jpa + jquery + mysql+shiro(权限管理)
    + maven + bootstrap + highcharts4

    bootstrap + highcharts4实际未使用

三、运行环境
    JDK8+Intellij+Maven 3.3.9
四、数据库脚本
        参见jxc-SQL.sql，数据源参见application.yml，根据各自需要进行配置。

五、本项目的git地址为：https://github.com/guardiangel/sales.git

六、项目构建方式：
    1.	从github拉取项目 本例新建目录G:\salesfromgithub\sales，在doc下面执行
        git clone https://github.com/guardiangel/sales.git
    2.	使用idea新建项目File-New-Porject from Existing Sources
    3.因为是Maven项目，则导入idea后， 在项目上右键点击，Add As Maven Project…



1.数据源、应用端口
    application.yml
2.ServletInitializer类的作用参见类的相关注释。
3.启动配置如过滤器等，参见ShiroConfig
4.某些注释的用法，如@JsonIgnore @JsonSerialize等
5.main.html 中 $("#tree").tree('expandAll')的用法
6.如前端通过JS输入到后台的日期少八个小时，则可以在配置文件中，使用驱动时设置serverTimezone=GMT%2B8
7.如前端日期格式为yyyy-MM-dd 后端为yyyy-MM-dd hh:mm:ss 则在Controller类里面使用定义如下:
    @InitBinder
        public void initBinder(WebDataBinder binder) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);//不强制校验
            binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        }
8.如数据库中的数据日期格式为yyyy-MM-dd hh:mm:ss,前端少了8个小时，则在实体类中的日期的get方法上，使用注解如下：
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    其中CustomDateTimeSerializer为自定义的日期类

9.jpa要求，’没有事务支持，不能执行更新和删除操作
    如果在Repository里面的方法中使用了@Modifying，则必须在对应的service层添加@Transitional
    本例参考PurchaseListGoodsRepository的deleteByPurchaseListId，对应的service为PurchaseListServiceImpl的delete方法
    否则会报
    javax.persistence.TransactionRequiredException: Executing an update/delete query
10.purchaseSearch.html中，dg标签中的th,换成td，结果不显示，需要后续研究2020-11-25 7:55:30
    标签属性问题

11.return.html不执行$(document).ready();，浏览器F12，查看console。原因为th标签中使用了formatter属性，
    其中对应的formatPrice等方法需要提前在js里面定义完整。

12.对于jquery中datagrid用法，需要熟悉。

13.对于Controller中某个方法存在多个修改操作，代码需要迁移到service层，添加事务处理。

14.按日统计和按月统计两个页面，代码未调整2020-11-30 11:15:7