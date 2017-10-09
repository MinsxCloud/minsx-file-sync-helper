# FileSyncHelper
FileSyncHelper (咪斯文件同步助手) 是一款小巧的基于Java的文件同步工具

### 软件说明
- 软件名称：咪斯文件同步助手
- 版本号：1.0.0
- 开发者：www.minsx.com
- 语言：Java
- 功能：支持本地局域网的文件同步，如果您需要同步外网文件
          需要架设FTP等服务，映射成本地盘后进行相同操作即可
- 优点：跨平台，目前支持：windows，linux，mac 系统。
		  同步机制采用对比机制，即每次同步会全目录对比，
		  不受之前同步或来源/目标文件不同或缺失等情况的
		  影响，容错性较高。
- 缺点：某些情况下会占用教多的IO资源，比如当某一根目录名
		  发生改变时，将会重新拷贝该目录下所有文件。
- 版权申明：Apache License Version 2.0 
				http://www.apache.org/licenses/
        
### 安装部署
- 关于二次开发的说明：该软件开发平台为win10/Eclipse4.7.0
					config目录下的一些配置文件路径在Eclipse下能较容易读取，如果您是IDEA开发
					请验证您的user.dir是否对应到相应目录。一般情况下IDEA如果是Project引入则能正常
					读取，如果是以子模块引入，则需要手动指定Constant.java中对应路径，以确保能正常读取到
					config文件夹所在位置。
					
- 软件打包说明：默认使用Eclipse导出可运行JAR包后即可运行。目前仅发布windows平台可运行程序，不需要
					用户安装JAVA环境即可运行。软件包请致GITHUB项目主页获取。其他平台目前仅支持JAR命令运行。
					
### 软件截图

![0](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/0.png "0")
![1](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/1.png "")
![2](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/2.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/3.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/4.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/5.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/6.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/7.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/8.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/9.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/10.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/11.png "")
![](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/12.png "")
![13](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/13.png "13")
![14](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/14.png "14")
![15](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/15.png "15")
![16](https://raw.githubusercontent.com/goodsave/FileSyncHelper/master/screenshot/16.png "16")
