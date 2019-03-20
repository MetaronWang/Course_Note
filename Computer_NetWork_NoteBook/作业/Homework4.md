![](../../_v_images/The_Logo_Of_Sustc.png)

****

<center><font face="Arial" size="6">HomeWork4</font></center>
<center><p>姓名：汪至圆 &nbsp; 学号11610634</p></center>

##  1. 
1.  interface0: 
    1.  Range:  0000 0000 - 0011 1111
    2.  Number: $2^6 = 64$
2.  interface1:
    1.  Range:  0100 0000 - 1001 1111
    2.  Number: $2^7+2^5-1-2^6+1 = 2^6+2^5=96$
3.  interface2:
    1.  Range:  1010 0000 - 1011 1111
    2.  Number: $2^5 = 32$
4.  interface3:
    1.  Range:  1100 0000 - 1111 1111
    2.  Number: $2^6 = 64$

##  2.
For that both subnets need the prefix 222.1.26/24
And subnet1 need 90 interface, subnet2 need 60 interfaces, subnet3 need 12 interface.
Firstly, The number of IP address that have prefix 222.1.26./24 is 256<90+60+12=162>, so the number is enough.  
Secondly
1.  For the subnet1, it need 90 interfaces
    $2^6<90<2^7, 32-7=25$
    so the network address should be 222.1.26.128/25
2.  For the subnet2, it need 60 interfaces
    $2^5<60<2^6, 32-6=26$
    so the network address should be 222.1.26.64/26
3.  For the subnet3, it need 12 interfaces
    $2^3<12<2^4, 32-4 = 28$
    so the network address should be 222.1.26.0/28

##  3.
*   a.  All the interface in the home network is from 192.168.1.0 to 192.168.1.127
*   b.  
    |WAN|LAN|
    |:--:|:--:|
    |24.34.112.231, 5001|192.168.1.0, 3345|
    |24.34.112.231, 5002|192.168.1.0, 2333|
    |24.34.112.231, 5003|192.168.1.1, 3345|
    |24.34.112.231, 5004|192.168.1.1, 2333|
    |24.34.112.231, 5005|192.168.1.2, 3345|
    |24.34.112.231, 5006|192.168.1.2, 2333|

##  4.  
*   For the forwarding table, the destination-based forwarding just do it base on the IP sddress. And it just show which packet in the input should be moved to which output.
*   For the flow table, the Openflow build it based on any set of header field values, it define many action include drop, forward, modify.

##  5.
*   |N'|D(t)|D(u)|D(v)|D(w)|D(y)|D(z)|
    |--|--|--|--|--|--|--|
    |x|$\infty$|$\infty$|2,x|7,x|6,x|8,x|
    |xv|6,v|5,v||6,v|6,x|8,x|
    |xvu|6,v|||6,v|6,x|8,x|
    |xvut||||6,v|6,x|8,x|
    |xvutw|||||6,x|8,x|
    |wvutwy||||||8,x|

*   **Forwording Table**
    |Destination|Next Hop|
    |:--:|:--:|
    |t|v|
    |u|v|
    |w|v|
    |y|y|
    |z|z|

##  6.
*   a.  
    *   $D_x(w) = 2$
    *   $D_x(y) = 4$
    *   $D_x(u) = 7$
*   b.  
    *   
