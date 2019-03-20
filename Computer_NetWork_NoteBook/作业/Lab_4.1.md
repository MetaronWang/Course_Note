
![](../../_v_images/The_Logo_Of_Sustc.png)

****

<center><font face="微软雅黑" size="6">Lab_4.1</font></center>
<center><p>姓名：汪至圆 &nbsp; 学号11610634</p></center>

# 实验内容
*    Try to capture session cookie of pms.sustc.edu.cn (yours or your classmates’)
*    Manipulate your cookie of pms.sustc.edu.cn to hijack this session. (Chrome Extension:edit this cookie)
*    Or you can replay the HTTP request using curl. (https://github.com/jullrich/pcap2curl)

# 实验过程:

## Write the Shell Script to change the Wirless Adapter to Monitor mode:
``` sh
sudo iw phy phy0 interface add mon0 type monitor
sudo iw dev wlp30s0 del
sudo ip link set mon0 up
sudo iw dev mon0 set freq 2437
sudo ifconfig mon0 promisc
sudo ifconfig mon0 down
sudo ifconfig mon0 up
sudo ifconfig mon0 up
```
## Change its permission and run it.
**After thr Option above We can get this:**
![](_v_images/_1538500274_172944243.png)

## Open WireShark Set tcp port http and then open the mon0
![](_v_images/_1538502885_365135886.png)
## Find the poocket we need and choice it
**Find the cookie and copy it as text**  
**Paste to the Vscode**
![](_v_images/_1538501138_443106615.png)

## Open the Chrome
Connect to the pms.sustc.edu.cn
![](_v_images/_1538501713_2119824742.png)

*    Delete all the Cookie
![](_v_images/_1538501763_540195816.png)
*    Login my self account
![](_v_images/_1538501827_1402622083.png)

*    Output the cookies as json and exit my account
![](_v_images/_1538501873_657791967.png)

*    Change the value by the pocket we get.
![](_v_images/_1538501924_402661341.png)


*    Input the cookies by json
![](_v_images/_1538502228_1557033001.png)

*    refresh The page
![](_v_images/_1538502587_1566143248.png)
*We can find that we can upload the Document*


## Request  By the curl:
*    Copy the cookie as String and use curl -b "cookies" pms.sustc.edu.cn to get
![](_v_images/_1538548845_1288593106.png)


#    结果分析
##    How did you capture the cookie? What’sthecontentofthecookie?

*    Get the packet by the wireshark and filter them buy " http.host == "pms.sustc.edu.cn" || http.host == "172.18.1.141"
*    Then we can find all the packet that comunicate with pms.sustc.edu.cn
*    Find a POST request and get its infomation
*    Its cookies is our goal.
        ![](_v_images/_1538502885_365135886.png)
*    By this Cookies, we can know:
        *    The Student of him
        *    The SeesionID
        *    The login status

## How did you set your cookie into target values?Show the edit page
*    Using editthiscookie, a extention of **Chrome**.
*    First get the cookies of this page by editthiscookie on json
*    Find the value we need to set, like sessionid logininfo and so on.
*    Import the cookies on json by editthiscookie
![](_v_images/_1538501713_2119824742.png)

*    Delete all the Cookie
![](_v_images/_1538501763_540195816.png)
*    Login my self account
![](_v_images/_1538501827_1402622083.png)

*    Output the cookies as json and exit my account
![](_v_images/_1538501873_657791967.png)

*    Change the value by the pocket we get.
![](_v_images/_1538501924_402661341.png)


*    Input the cookies by json
![](_v_images/_1538502228_1557033001.png)

*    refresh The page
![](_v_images/_1538502587_1566143248.png)

## Did you success hijack the session? Describe how did you do it
**Yes**

I get my roommate's cookies by the wireless Adapter which is on Monitor mode and copy it to my editor.(With the consent of my roommate)
And then open  the pms pages and replace the cookies and refresh the page.
 Now, I'm login as my roommate's Identify.

#    小结与感悟
在非加密的网络传输环境下, cookies在带来便捷的同时, 也会带来十分大的安全隐患.  
为了数据和个人信息的安全, 应当尽量避免使用非加密的公共网络, 同时及时清除cookies信息



