1.数据源、应用端口
    application.yml
2.ServletInitializer类的参见作用类的相关注释。
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

