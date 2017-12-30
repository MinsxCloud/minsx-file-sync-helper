# FileSyncHelper
FileSyncHelper (咪斯文件同步助手) 是一款小巧的跨平台较稳定的轻量级文件同步工具

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
- 开源协议：Apache License Version 2.0 
				http://www.apache.org/licenses/
        
### 安装部署
- 关于二次开发的说明：该软件开发平台为win10/Eclipse4.7.0
					config目录下的一些配置文件路径在Eclipse下能较容易读取，如果您是IDEA开发
					请验证您的user.dir是否对应到相应目录。一般情况下IDEA如果是Project引入则能正常
					读取，如果是以子模块引入，则需要手动指定Constant.java中对应路径，以确保能正常读取到
					config文件夹所在位置。
					
- 软件打包说明：默认使用Eclipse导出可运行JAR包后即可运行。目前仅发布windows平台可运行程序，不需要
					用户安装JAVA环境即可运行。软件包请致GITHUB项目主页获取。其他平台目前仅支持JAR命令运行。
					
- MAC打包运行请注意启动命令中应该加入以下参数:-XstartOnFirstThread
					
- 您可以[点击这里](https://github.com/MinsxCloud/minsx-file-sync-helper/raw/master/runable-software/%E5%92%AA%E6%96%AF%E6%96%87%E4%BB%B6%E5%90%8C%E6%AD%A5%E5%8A%A9%E6%89%8B-win32-64.zip)立即下载win/32bit可运行版本进行体验	


					
					
### 软件截图

![0](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/0.png "0")
![1](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/1.png "1")
![2](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/2.png "2")
![3](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/3.png "3")
![4](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/4.png "4")
![5](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/5.png "5")
![6](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/6.png "6")
![7](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/7.png "7")
![8](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/8.png "8")
![9](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/9.png "9")
![10](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/10.png "10")
![11](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/11.png "11")
![12](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/12.png "12")
![13](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/13.png "13")
![14](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/14.png "14")
![15](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/15.png "15")
![16](https://raw.githubusercontent.com/MinsxCloud/minsx-file-sync-helper/master/screenshot/16.png "16")
