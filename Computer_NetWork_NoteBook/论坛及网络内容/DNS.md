# DNS
<div>

<p><br></p>
<p><strong><span style="font-family:'黑体';">一、概述</span></strong></p>
<p><strong>1.1 DNS</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>识别主机有两种方式：主机名、IP地址。前者便于记忆(如<a href="http://www.yahoo.com/" rel="nofollow" target="_blank">www.yahoo.com</a>)，但路由器很难处理(主机名长度不定)；后者定长、有层次结构，便于路由器处理，但难以记忆。折中的办法就是建立IP地址与主机名间的映射，这就是域名系统DNS做的工作。DNS通常由其他应用层协议使用(如HTTP、SMTP、FTP)，将主机名解析为IP地址，其运行在UDP之上，使用53号端口。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong><span style="color:#f00000;">注：DNS除了提供主机名到IP地址转换外，还提供如下服务：主机别名、邮件服务器别名、负载分配。</span></p>
<p><strong>1.2 HTTP使用DNS情形</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>考虑这样的操作，在浏览器输入<a href="http://www.baidu.com/index.html" rel="nofollow" target="_blank">http://www.baidu.com/index.html</a>并回车，首先需要将URL(<strong>存放对象的服务器主机名和对象的路径名)</strong>解析成IP地址，具体步骤为：</p>
<blockquote>
<p>(1)同一台用户主机上运行着DNS应用的客户机端(如浏览器)</p>
<p>(2)从上述URL抽取主机名<a href="http://www.baidu.com/" rel="nofollow" target="_blank">www.baidu.com</a>，传给DNS应用的客户机端(浏览器)</p>
<p>(3)该DNS客户机向DNS服务器发送一个包含主机名的请求(<strong>DNS查询报文</strong>)</p>
<p>(4)该DNS客户机收到一份回答报文(即<strong>DNS回答报文</strong>)，该报文包含该主机名对应的IP地址119.75.218.70</p>
</blockquote>
<blockquote>
<p>(5)浏览器由该IP地址定位的HTTP服务器发送一个TCP链接</p>
</blockquote>
<p>用Wireshark捕获的DNS报文如下图，显然第一行是DNS查询报文，第二行是DNS回答报文。</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243124P6pi.png" alt=""></p>
<p>图1 Wireshark捕获的DNS报文</p>
<p><strong><span style="font-family:'黑体';">二、DNS报文</span></strong></p>
<p><strong>2.1 DNS报文格式</strong></p>
<p>DNS只有两种报文：查询报文、回答报文，两者有着相同格式，如下：</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243125YfCr.png" alt=""></p>
<p>图2 DNS报文格式</p>
<p><strong>2.1.1 首部区域</strong></p>
<p><strong>标识数</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>对该查询进行标识，该标识会被复制到对应的回答报文中，客户机用它来匹配发送的请求与接收到的回答。</p>
<p><strong>标志[1]</strong></p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243125kk5k.png" alt=""></p>
<p>图3 DNS报文首部区域的标志</p>
<blockquote>
<p><strong>QR(1比特)：</strong>查询/响应的标志位，1为响应，0为查询。</p>
<p><strong>opcode(4比特)：</strong>定义查询或响应的类型(若为0则表示是标准的，若为1则是反向的，若为2则是服务器状态请求)。</p>
<p><strong>AA(1比特)：</strong>授权回答的标志位。该位在响应报文中有效，1表示名字服务器是权限服务器(关于权限服务器以后再讨论)</p>
<p><strong>TC(1比特)：</strong>截断标志位。1表示响应已超过512字节并已被截断(依稀好像记得哪里提过这个截断和UDP有关，先记着)</p>
<p><strong>RD(1比特)：</strong>该位为1表示客户端希望得到递归回答(递归以后再讨论)</p>
<p><strong>RA(1比特)：</strong>只能在响应报文中置为1，表示可以得到递归响应。</p>
<p><strong>zero(3比特)：</strong>不说也知道都是0了，保留字段。</p>
<p><strong>rcode(4比特)：</strong>返回码，表示响应的差错状态，通常为0和3，各取值含义如下：</p>
</blockquote>
<blockquote dir="ltr">
<blockquote>
<p>0 无差错&nbsp;</p>
<p>1 格式差错&nbsp;</p>
<p>2 问题在域名服务器上&nbsp;</p>
<p>3 域参照问题&nbsp;</p>
<p>4 查询类型不支持&nbsp;</p>
<p>5 在管理上被禁止&nbsp;</p>
<p>6 -- 15 保留 </p>
</blockquote>
</blockquote>
<p dir="ltr"><strong>问题数、回答RR数、权威RR数、附加RR数</strong></p>
<p dir="ltr"><strong>&nbsp; &nbsp;&nbsp;</strong>这四个字段都是两字节，分别对应下面的查询问题、回答、授权和附加信息部分的<strong>数量</strong>。一般问题数都为1，DNS查询报文中，资源记录数、授权资源记录数和附加资源记录数都为0[1]。
</p>
<p dir="ltr"><strong>2.1.2 区域</strong></p>
<p dir="ltr"><strong>(1)问题区域</strong></p>
<p dir="ltr"><strong>&nbsp; &nbsp;&nbsp;</strong>包含正在进行的查询信息。包含查询名(被查询主机名字的名字字段)、查询类型、查询类。</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243126ZnLZ.png" alt=""></p>
<p>图4 DNS报文的问题区域</p>
<p><strong>查询名</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>查询名部分长度不定，一般为要查询的域名(也会有IP的时候，即反向查询)。此部分<strong>由一个或者多个标示符序列组成</strong>，每个标示符以首字节数的计数值来说明该标示符长度，每个名字以0结束。计数字节数必须是0~63之间。该字段无需填充字节。还是借个例子来说明更直观些，查询名为gemini.tuc.noao.edu的话，查询名字段如下[1]：</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243127s5l2.png" alt=""></p>
<p>图5 DNS报文问题区别的查询名</p>
<p><strong>查询类型</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>通常查询类型为A(由名字获得IP地址)或者PTR(获得IP地址对应的域名)，类型列表如下：</p>
<p align="center"><span style="font-family:'宋体';font-size:14px;"><a name="OLE_LINK2" target="_blank"></a><a name="OLE_LINK1" target="_blank">表1 DNS报文查询类型</a></span></p>
<span style="font-family:'宋体';font-size:14px;"></span>
<div align="center">
<table border="1" cellspacing="0" cellpadding="0"><tbody><tr><td valign="top">
<p align="center"><strong><span style="font-family:'宋体';font-size:12pt;">类型</span></strong></p>
</td>
<td valign="top">
<p align="center"><strong><span style="font-family:'宋体';font-size:12pt;">助记符</span></strong></p>
</td>
<td valign="top">
<p align="center"><strong><span style="font-family:'宋体';font-size:12pt;">说明</span></strong></p>
</td>
</tr><tr><td valign="top">
<p align="left"><span style="color:#0000FF;">1</span></p>
</td>
<td valign="top">
<p align="left"><span style="color:#0000FF;">A</span></p>
</td>
<td valign="top">
<p align="left"><span style="color:#0000FF;">IPv4</span><span style="color:#0000FF;">地址</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">2</p>
</td>
<td valign="top">
<p align="left">NS</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">名字服务器</span></p>
</td>
</tr><tr><td valign="top">
<p align="left"><span style="color:#0000FF;">5</span></p>
</td>
<td valign="top">
<p align="left"><span style="color:#0000FF;">CNAME</span></p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">规范名称定义主机的正式名字的别名</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">6</p>
</td>
<td valign="top">
<p align="left">SOA</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">开始授权标记一个区的开始</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">11</p>
</td>
<td valign="top">
<p align="left">WKS</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">熟知服务定义主机提供的网络服务</span></p>
</td>
</tr><tr><td valign="top">
<p align="left"><span style="color:#0000FF;">12</span></p>
</td>
<td valign="top">
<p align="left"><span style="color:#0000FF;">PTR</span></p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">指针把</span><span style="color:#0000FF;">IP</span><span style="font-family:'宋体';font-size:12pt;">地址转化为域名</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">13</p>
</td>
<td valign="top">
<p align="left">HINFO</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">主机信息给出主机使用的硬件和操作系统的表述</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">15</p>
</td>
<td valign="top">
<p align="left">MX</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">邮件交换把邮件改变路由送到邮件服务器</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">28</p>
</td>
<td valign="top">
<p align="left">AAAA</p>
</td>
<td valign="top">
<p align="left">IPv6<span style="font-family:'宋体';font-size:12pt;">地址</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">252</p>
</td>
<td valign="top">
<p align="left">AXFR</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">传送整个区的请求</span></p>
</td>
</tr><tr><td valign="top">
<p align="left">255</p>
</td>
<td valign="top">
<p align="left">ANY</p>
</td>
<td valign="top">
<p align="left"><span style="font-family:'宋体';font-size:12pt;">对所有记录的请求</span></p>
</td>
</tr></tbody></table></div>
<p>&nbsp;<strong>&nbsp; &nbsp;&nbsp;</strong>NS记录指定了名字服务器。一般情况，每个DNS数据库中，针对每个顶级域都会有一条NS记录，这样一来，电子邮件就可以被发送到域名树中远处的部分。</p>
<p><strong>查询类</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>通常为1，指Internet数据。</p>
<p><strong>(2)回答、权威、附加区域</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>回答区域包含了最初请求名字的资源记录，一个回答报文的回答区域可以包含多条资料记录RR(<strong>因为一个主机名可以对应多个IP地址，冗余Web服务器</strong>)。权威区域包含了其他权威DNS服务器的记录。附加区域包含其他一些"有帮助"的记录，例如，对于一个MX(邮件交换)请求的回答报文中，回答区域包含一条资料记录(该记录提供邮件服务器的规范主机名)，附加区域可以包含一条类型A记录(该记录提供了该邮件服务器的规范主机名的IP地址)。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>每条资料记录是一个五元组，如下：</p>
<p><strong>(域名，生存期，类别，类型，值)</strong></p>
<p>直接表示如下[1]：</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243128BnDB.png" alt=""></p>
<p>图6 DNS报文的资源记录</p>
<p><strong>域名(2字节或不定长)</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>记录中资源数据对应的名字，它的格式和查询名字段格式相同。当报文中域名重复出现时，就需要使用2字节的偏移指针来替换。例如，在资源记录中，域名通常是查询问题部分的域名的重复，就需要用指针指向查询问题部分的域名。关于指针怎么用，TCP/IP详解里面有，即2字节的指针，最前面的两个高位是11，用于识别指针。其他14位从报文开始处计数(从0开始)，指出该报文中的相应字节数。注意，DNS报文的第一个字节是字节0，第二个报文是字节1。一般响应报文中，资源部分的域名都是指针C00C(11<strong>00000000001100，12正好是首部区域的长度</strong>)，刚好指向请求部分的域名[1]。</p>
<p><strong>类型(记录的类型，见表1)</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>A记录，Name是主机名，Value是该主机名的IP地址，因此，一条类型为A的资源记录提供了标准的主机名到IP地址的映射。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>NS记录，Name是域(如foo.com)，Value是知道如何获得该域中主机IP地址的权威DNS服务器的主机名(如dns.foo.com)，这个记录常用于沿着查询链进一步路由DNS查询。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>CNAME记录，Name是主机别名，Value是主机别名对应的<strong>规范主机名</strong>，该记录能够向请求主机提供一个主机名对应的规范主机名。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>MX记录，Name是邮件服务器别名，Value是邮件服务器别名的规范主机名。通过MX记录，一个公司的邮件服务器和其他服务器可以使用相同的别名。</p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong><span style="color:#f00000;">注：有着复杂主机名的主机能拥有多个别名，前者称为规范主机名，后者称为主机别名(便于记忆)。</span></p>
<p><strong>类</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>对于Internet信息，它总是IN。</p>
<p><strong>生存时间</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>用于指示该记录的稳定程度，极为稳定的信息会被分配一个很大的值(如86400，一天的秒数)。该字段表示资源记录的生命周期(以秒为单位)，一般用于当地址解析程序取出资源记录后决定保存及使用缓存数据的时间[1]。</p>
<p><strong>资源数据长度(2字节)</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>表示资源数据的长度(以字节为单位，如果资源数据为IP则为0004)。&nbsp;</p>
<p><strong>资源数据</strong></p>
<p><strong>&nbsp; &nbsp;&nbsp;</strong>该字段是可变长字段，表示按查询段要求返回的相关资源记录的数据。</p>
<p><strong>2.2 DNS查询报文实例</strong></p>
<p>以<a href="http://www.baidu.com/" rel="nofollow" target="_blank">www.baidu.com</a>为例，用Wireshark俘获分组，结合2.1的理论内容，很容易看明白的，DNS请求报文如下：</p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_1337243128sbS6.png" alt=""></p>
<p>图7 DNS请求报文示例</p>
<p><strong>2.3 DNS回答报文实例</strong></p>
<p><img src="http://blog.chinaunix.net/attachment/201205/17/9112803_13372431293B5P.png" alt=""></p>
<p>图8 DNS回答报文示例</p>
</div>