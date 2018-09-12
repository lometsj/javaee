# javaee课程设计的log

## 9.7
第一次记录，在此之前只是看了一堆javaee的介绍和技术选型之类的网页，尝试把2008 r2 sql server连上去，下sql server的jdbc jar包之类的东西，比较杂，随便看了看。
今天把整个框架和环境都想了想明白，（bootstrap引用的是在线包），基本上架构是spring boot + jpa（hibernate）+ thymeleaf +  +bootstrap 这么一套，这是我看了一堆java那些鬼玩意儿想出来的最简便的环境。首先spring boot简化了spring繁杂的配置，非常方便。因为数据库是老师给定的，使用idea的hibernate下persistence窗口的工具可以直接由表生成实体类。thymeleaf作为动态模板，支持html格式，相对于jsp更快，更利于前后端分离。前端采用ajax对后端传输数据，也是前后端分离的东西。bootstrap不多说。

今日坑点：
- jpa在连接sql server默认会读取sys.sequence表，然而sequence功能是sql server 2012才开始引入的，所以会报列表名 "sys.sequence"无效。这里需要在配置文件中注明`spring.jpa.database-platform=org.hibernate.dialect.SQLServer2008Dialect
`这样标注了使用的版本，
- 因为我用的hibernate版本较高，默认的命名方式会添加下划线，全部改小写，并使实体类的注解无效。在使用已有数据库时需要加上`spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
`来启用以前的命名方式。!()[https://www.jianshu.com/p/fc2c79814956]这里讲的比较细。

## 9.8
上午想把老师给的项目跑起来，挣扎了一下放弃了，里面配置了一个我不知道怎么搞的数据库。
关于图书封面图片，是直接用`[isbn].jpg`保存，就按照这个方案吧。


## 9.9
看了下ajax怎么用，基本清楚jquery下的ajax调用和spring boot 如何接收的问题。
弄清楚了激活码功能的过程，模态窗口等。
下午4点才起床。。凄凉
拿了isc2018的包，把伞寄了。

## 9.10-9.11
睡得比较久，两天并作一天用。
主要是完成了用户的注册并发送验证码两个工作。
从老师给的数据库可以发现密码是明文保存的，就现在来说，比较好的方式是md5+salt，不过课设就不用管那么多了。
session 保存登录状态。
今天基本上把所有要用到的技术细节搞明白了，之后功能开发可以很快，本来也就是一些增删改查。

坑点：
- 在hibernate save数据的时候报错，大概是说主键自增不能自行设定值，而我明明就没有设置主键的值。这里hibernate会对主键Id在没有设置的情况下给一个默认的0，导致报错。需要在实体类中，Id注解下添加
```
@GeneratedValue(generator = "paymentableGenerator")
@GenericGenerator(name = "paymentableGenerator", strategy = "native")
```
来设置hibernate调用数据库自身的逻辑来设置Id。

- 上个厕所回来发现sql server连不上了，重启虚拟机没用，mac重启就好了，这尼玛。

