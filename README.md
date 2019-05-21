# airport_indoormap
<img src="https://www.navvis.com/hubfs/NavVis_November2018/Images/maps_icon_2.png">

An outline design of the project indoor interactive guide APP. It mainly solves the problem of designing program modules to realize the requirements of the system. It includes how to divide the system into several modules, determine the interface between each module, the information transmitted between the modules, and the design of the data structure and module structure. So that the review experts can have a holistic and comprehensive logical structure of the project better and faster. Have a clear and accurate understanding of the project's function points, innovation points, implementation algorithms, and structural modules.

1. Real-time positioning: Real-time positioning of users through wireless WiFi, followed by subsequent navigation and route planning services.

2. Optimal route planning: automatically set the optimal navigation path by setting the starting point and the transit point.

3. Line rectification: Real-time rectification function is realized in the planning path. When the user deviates from the planned route, the system automatically plans the appropriate route to ensure optimization in real time.

4. Emergency help: Through the emergency help function, you can send messages and your own positioning messages to your friends, which can play a security role and deal with various emergencies.

5. Speech recognition: Through speech recognition, various inquiries are made as input information.

6. Two-dimensional code scanning: By scanning the two-dimensional code of the flight, store, and toilet light building, the function of inputting information is realized, and then information inquiry is performed.

7. Airport map path design: JOSM.

8. Social entertainment: You can add chats, share, emergency, etc. to people who use the same software.

9. Search query: You can quickly and accurately query the floor, location name, category name, etc. and display it on the airport map.

## preview
功能1
功能2
功能2

## developing environment

### hardware
1. The software runs on the hardware requirements of the server and client:
     Server: A complete computer system
     Client: Two Android 4.3 and above smartphones and mobile phones have no less than 10M of available memory.
2. Peripherals used by the software to run:
     Wireless WiFi network support

### software 
The computer software and version used to run the software:
1) Operating system: Win7 and above
2) Database system: MYSQL
3) Development platform and tools: Eclipse development environment loaded with a complete SDK
4) Communication protocol: HTTP protocol


## Architecture
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/architicture.png">

model list

|No|type|dependence|reuse|
|:---:|:---:|:--:|:--:|
|1|function|indoor navigation|N|N|
|2|function|airport service|N|N|
|3|function|fight info|N|N|

## feature

### indoor navigation

This component is the core component of the project, and it also concentrates most of the requirements of the game. The core functions of the component are as follows:

1. Real-time positioning: Real-time positioning of users through wireless WiFi, followed by subsequent navigation and route planning services.

2. Optimal route planning: automatically set the optimal navigation path by setting the starting point, the ending point and the transit point.

3. Line rectification: Real-time rectification function is realized in the planning path. When the user deviates from the planned route, the system automatically plans the appropriate route to ensure optimization in real time.

4. Speech recognition: At the time of airport facility search, various queries are made as input information through voice recognition.

5. Two-dimensional code scanning: By scanning the unique two-dimensional code of the flight, store, and toilet light building, the input information is achieved, and then the information is inquired.

6. Search query: You can quickly and accurately query the floor, location name, category name, etc. and display it on the airport map.

#### structure

<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi1.png">
<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi2.png">

#### table

|name|meaning|feature|
|:--:|:--:|:--:|
|SearchEntity|searching entity|entity database|
|ShopInfo|shop information|shop fatabase|
|TabMsgItemEntity|navigation infoarmtion|path entity database|
|LocationRequestEntity|loaction require data|location require data|
|LocationResponseEntity|oaction respone data|location respone data|

#### function

|name|feature|
|:--:|:--:|
|import SAILS_SDK_V1.73_Publisher.jar|wifi locating |
|MapActivity|map display|
|ShopDetailActivity|store and shop detial|
|SearchresultActivity|matching function|
|List<PathRoutingManager.SwitchFloorInfo> routeinfolist|navigation|
|lockcenter|locating to the central point|
|distanceView|disance computing|

#### preview

|real time locating|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi3.png">|voice input|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi4.png">|QR scaning|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi5.png">|
|:--:|:--:|:--:|:--:|:--:|:--:|
|searching result |<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi6.png">|searching result 2|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi7.png">|indoor map display|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi8.png">|
|setting start point transffer point and end point|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi9.png">|auto-navigation|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi10.png">|setting transffer point|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi11.png">|
|detial of the path|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/navi12.png">|

### airport service

The core function of this component lies in the "Friends & Savage" module, which realizes the social function of the software. It can provide a social network platform for users who use the software, add friends, live chat, etc., and most importantly, can be friends. Emergency help, by sending your own location information to deal with certain unexpected situations in order to be able to quickly rescue.

#### structure

<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport3.png">

#### table

|name|meaning|feature|
|:--:|:--:|:--:|
|chatEntity|chat entity|user information|
|LocationRequestEntity|locate require|location inforamtion provide to the user|
|LocationResponseEntity|locate respone|location inforamtion provide to the user|
|UserInfo|user inforamtion|basic user information|

#### function

|name|feature|
|:--:|:--:|
|ConnectedApp|connect to the main body and network，connect to the database|
|RegisterActivity|record entity|
|ChatMsgReceiver|caht log receiver|
|ChatServiceData|chat log restore and store|
|FriendListInfo|friend list resotre|
|LocationRequestReceiver|location require and respone|
|MyLocation|locating|
|ChatEmoticonUtil|on-line matching|
|ReadyMadeDialog|message receiver state|
|LocationAcceptActivity|loaction require state|
|SearchFriendByNameActivity|searching friend by name|
|SearchFriendByOnlineActivity|searching friend by state|
|FriendSearchResultActivity|result return|
|FriendListGroupItem|friend managment|
|DbSaveOldMsg|information record state|

#### preview

|main view|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport8.png">|logging |<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport7.png">|register|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport6.png">|
|:--:|:--:|:--:|:--:|:--:|:--:|
|chating and locating|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport5.png">|friend list|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport4.png">|friend locating|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport2.png">|
|personal information setting|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/airport1.png">|

### flight info

After the user arrives at the airport, he can enter the flight number to make relevant inquiries about his flight, such as boarding gate, check-in information, departure time, arrival time, etc., to meet the humanized needs, so that passengers can more easily experience from this software. Fast user experience.

#### structure

<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info3.png">

#### table

|name|meaning|feature|
|:--:|:--:|:--:|
|FightInfo|fight infotamtion|fight infotamtion|

#### function

|name|feature|
|:--:|:--:|
|FightDetailActivity|record and store the fight information|
|SearchFightResultActivity|matching the fight feature and return the result|
|CarInfo|fight parking inforamtion|
|SearchresultActivity|fight searching and generate suitable path for user|

#### preview

|fight searching|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info1.png">|fight detial|<img src="https://github.com/teddy-ssy/Airport_indoormap/blob/master/readme/info2.png">|
|:--:|:--:|
| | |

## Roadmap

- [x] **1.4.2015** 
    - finish the main part
- [x] **10.4.2015** 
    - improve the main body
- [x] **22.4.2015** 
    - modify the main part
- [x] **9.5.2015** 
    - modify the main part
- [x] **20.5.2015** 
    - modify the final part

## Discussion
- [submit issue](https://github.com/teddy-ssy/Airport_indoormap/issues/new)

