# airport_indoormap
<img src="https://www.navvis.com/hubfs/NavVis_November2018/Images/maps_icon_2.png">
对项目室内交互式引导APP 做概要设计。主要解决了实现该系统需求的程序模块设计问题。包括如何把该系统
划分成若干个模块、决定各个模块之间的接口、模块之间传递的信息，以及数据
结构、模块结构的设计等。以便于评审专家能够更好，更快地对项目有一个整体、
全面的逻辑构架。对项目的功能点、创新点、实现算法、结构模块等有一个清晰
准确的认识。

1.实时定位：通过无线WiFi 对用户进行实时定位，然后进行后续导航和线
路规划服务。

2.最优线路规划：通过设置起始点、中转点，自动规划出最优导航路径。

3.线路纠偏：在规划路径当中实现实时纠偏功能，当用户偏离规划路线时，
系统自动实时规划出合适的路线保证最优化。

4.紧急求救：通过紧急求救功能可以向自己的好友发送消息和自己的定位消
息，可以起到安全保护作用和处置各种突发情况。

5.语音识别：通过语音识别，作为输入信息进行各种查询。

6.二维码扫描：通过对航班、商店、厕所灯建筑的二维码进行扫描，从而达
到输入信息的作用，进而进行信息查询。

7.机场地图路径设计：JOSM。

8.社交娱乐：可以对使用同一软件的人通过添加好友，进行聊天、位置分享、
紧急求救等。

9.搜索查询：可以进行楼层、地点名、类别名等进行快速、准确的查询并显
示在机场地图上。

## 预览
功能1
功能2
功能2

## 运行环境

### hardware
1.本软件运行对服务器、客户端的硬件要求：
    服务器：一台完备的计算机系统
    客户端：两台Android4.3 及以上版本的智能手机、手机可用内存不小于10M
2.本软件运行所使用的外围设备：
    无线WiFi 网络支持

### software 
本软件运行所使用的计算机软件及版本：
1) 操作系统：Win7 及以上
2) 数据库系统：MYSQL
3) 开发平台及工具：装载有完备SDK 的Eclipse 开发环境
4) 通信协议：HTTP 协议


## Architecture结构
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/architicture.png">

构件列表

|序号|构建分类|依赖关系|复用情况|
|:---:|:---:|:--:|:--:|
|1|功能构件|机场服务|无|无|
|2|功能构件|航班信息|无|无|
|3|功能构件|室内导航|无|无|

## feature

### airport service

该构件的核心功能在于“好友&&求救”模块，实现了该软件的社交功能，可以为使用该软件的用户提供一个社交网络平台，可以相互加好友、实时聊天等，最重要的是能够向好友紧急求救，通过发送自己的位置信息来应对某些突发情况以求能够快速解救。

模块结构
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport3.png">

与本模块相关的表

|名称|中文注释|作用|
|:--:|:--:|:--:|
|chatEntity|聊天实体表|用户数据表|
|LocationRequestEntity|位置请求|提供用户的位置请求数据表|
|LocationResponseEntity|位置请求的响应|对位置请求所响应的数据表|
|UserInfo|用户信息|用户基本信息|

与本模块相关的功能

|名称|作用|
|:--:|:--:|
|ConnectedApp|连接系统服务器，调用数据库|
|RegisterActivity|记录实体信息|
|ChatMsgReceiver|聊天记录接收|
|ChatServiceData|聊天数据调用和存储|
|FriendListInfo|好友列表信息|
|LocationRequestReceiver|位置信息请求和响应|
|MyLocation|个人位置信息定位|
|ChatEmoticonUtil|在线好友匹配查找|
|ReadyMadeDialog|讯息接收状态|
|LocationAcceptActivity|位置请求接收状态|
|SearchFriendByNameActivity|通过姓名查找好友|
|SearchFriendByOnlineActivity|通过在线状态查找好友|
|FriendSearchResultActivity|好友查找结果反馈|
|FriendListGroupItem|好友管理|
|DbSaveOldMsg|信息记录状态|

preview

|机场服务模块主界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport8.png">|好友登录界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport7.png">|用户注册界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport6.png">|
|:--:|:--:|:--:|:--:|:--:|:--:|
|好友聊天及位置请求|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport5.png">|好友列表界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport4.png">|好友查找界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport2.png">|
|个人信息设置界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport1.png">|

### flight info

用户到达机场之后，可以通过输入航班次号对自己乘坐的航班进行相关查询，如登机口、值机信息、起飞时间、到达时间等，达到人性化需求，使乘客更能从此款软件中体验方便、快捷的使用体验。

模块结构
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info3.png">

与本模块相关的表

|名称|中文注释|作用|
|:--:|:--:|:--:|
|FightInfo|航班信息表|航班信息表|

与本模块相关的功能

|名称|作用|
|:--:|:--:|
|FightDetailActivity|记录并存储航班详细信息|
|SearchFightResultActivity|航班信息匹配查询并返回结果|
|CarInfo|机场停车信息查询与更新|
|SearchresultActivity|航班信息查询并规划实时线路室内导航|

preview

|航班搜索主界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info1.png">|航班详情主界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info2.png">|
|:--:|:--:|
|||

### indoor navigation
该构件是项目的最核心构件，也集中了大部分需求，该构件主要实现
的核心功能如下：
1.实时定位：通过无线WiFi 对用户进行实时定位，然后进行后续导航和线
路规划服务。
2.最优线路规划：通过设置起始点、终点和中转点等，自动规划出最优导航
路径。
3.线路纠偏：在规划路径当中实现实时纠偏功能，当用户偏离规划路线时，
系统自动实时规划出合适的路线保证最优化。
4.语音识别：在机场设施搜索的时候，通过语音识别，作为输入信息进行各
种查询。
5.二维码扫描：通过对航班、商店、厕所灯建筑的唯一二维码进行扫描，从
而达到输入信息的作用，进而进行信息查询。
6.搜索查询：可以进行楼层、地点名、类别名等进行快速、准确的查询并显示在机场地图上。

模块结构
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi1.png">
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi2.png">

与本模块相关的表

|名称|中文注释|作用|
|:--:|:--:|:--:|
|SearchEntity|搜索实体表|实体数据库表|
|ShopInfo|商店信息|商店信息表|
|TabMsgItemEntity|导航路径数据实 体|导航路径数据实体|
|LocationRequestEntity|位置请求数据|位置请求数据库|
|LocationResponseEntity|位置请求相应数据库|位置请求相应数据库|

与本模块相关的功能

|名称|作用|
|:--:|:--:|
|通过调用SAILS_SDK_V1.73_Publisher.jar|实现该定位功能|
|MapActivity|地图显示|
|ShopDetailActivity|商店等详细信息查询|
|SearchresultActivity|输入信息的匹配查询功能|
|List<PathRoutingManager.SwitchFloorInfo> routeinfolist|路径导航|
|lockcenter|中心定位|
|distanceView|距离计算|

preview

|实时定位界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi3.png">|语音输入界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi4.png">|二维码扫描输入界面|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi5.png">|
|:--:|:--:|:--:|:--:|:--:|:--:|
|搜索结果列表|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi6.png">|搜索结果列表2|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi7.png">|搜索结果地图显示|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi8.png">|
|设置起点、终点、中转点|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi9.png">|自动规划路线导航|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi10.png">|设置中转|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi11.png">|
|查看路线|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi12.png">|

## Roadmap

- [x] **1.4.2015** 
    - 概要设计基本框架
- [x] **10.4.2015** 
    - 概要设计补充完成
- [x] **22.4.2015** 
    - 概要设计修改版本
- [x] **9.5.2015** 
    - 概要设计修订版本
- [x] **20.5.2015** 
    - 概要设计最终版本



## Discussion
- [submit issue](https://github.com/teddy-ssy/Airport_indoormap/issues/new)

