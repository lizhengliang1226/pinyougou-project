

# 品优购项目
系统架构图
![image-20210523233017836](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210523233017836.png)


### SOA开发模式

就是把服务层抽离出来形成一个模块

把controller层抽离出来形成一个模块

把pojo和dao，抽离出来，形成单独的模块

当需要调用的时候，去远程调用

### Solr

配置域：

中文分析器

![image-20210428001154477](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210428001154477.png)

![image-20210428001630379](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210428001630379.png)

![image-20210501012236394](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210501012236394.png)



cas的配置文件需要修改

cas/WEB-INF/deployConfigContext.xml修改：



![image-20210509140429946](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509140429946.png)

在末尾增加三个bean，完成cas的认证

![image-20210509150822717](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509150822717.png)

此时需要修改64行的引用为当前的bean

![image-20210509144558577](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509144558577.png)

cas/WEN-INF/spring-configuration/ticketGrantingTicketCookieGenerator.xml需要修改：

![](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509140905895.png)

cas/WEN-INF/spring-configuration/warnCookieGenerator.xml也要执行上面一样的操作

![image-20210509141039086](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509141039086.png)

cas退出之后需要重定向，修改配置文件cas-servlet.xml

![image-20210509143222947](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509143222947.png)

完成I18N国际化，显示登录的错误信息为中文

复制原来登录文件的错误信息到新文件，修改样式

![image-20210509160537307](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509160537307.png)

粘贴WEB-INF/classes/的message.properties的下面两句，然后修改message_zh_CN.properties文件，添加这两句，然后再这两句的等号后面的信息改为想要的中文提示信息的unicode编码

![image-20210509155624359](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509155624359.png)

修改WEB-INF/cas-servlet.xml文件

![image-20210509155956356](C:\Users\17314\AppData\Roaming\Typora\typora-user-images\image-20210509155956356.png)

#### 服务

1. pinyougou-cart-service 购物车服务 9006

2. pinyougou-content-service 广告服务 9002

3. pinyougou-order-service 订单服务 9007

4. pinyougou-page-service 静态页面服务 9003

5. pinyougou-pay-service 支付服务 9008

6. pinyougou-search-service 搜索服务 9004

7. pinyougou-seckill-service 秒杀服务 9009

8. pinyougou-sellergoods-service 运营商后台服务 9001

9. pinyougou-sms-service 短信服务 9201

10. pinyougou-user-service 用户服务 9005

    

#### web层

1. pinyougou-cart-web 购物车系统 9106
2. pinyougou-manager-web 运营商后台系统 9101
3. pinyougou-portal-web 网站前台系统 9103
4. pinyougou-search-web 搜索系统 9104
5. pinyougou-seckill-web 秒杀系统 9109
6. pinyougou-shop-web 商家后台管理系统 9102
7. pinyougou-user-web 用户系统 9105

### 安装jar包

mvn install:install-file -Dfile=.\wxpay-sdk-0.0.3.jar -DgroupId=com.github.wxpay -DartifactId=wxpay-sdk -Dversion=0.0.3 -Dpackaging=jar -DgeneratePom=true