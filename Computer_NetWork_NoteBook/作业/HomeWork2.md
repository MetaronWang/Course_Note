![](../../_v_images/The_Logo_Of_Sustc.png)

****

<center><font face="Arial" size="6">HomeWork2</font></center>
<center><p>姓名：汪至圆 &nbsp; 学号11610634</p></center>

#### 1.  
For this problem, the cache server will query in the origin server to check if the resource in the cache server is same as which in the origin server.
If there is no change in the resource after the cache server obtained the cached copy, it will return the cache directly.

****
#### 2. 
$RTT_0 + \sum_{i=0}^{n}RTT_i = 2 \times RTT_0 + \sum_{i=1}^{n}RTT_i$

****
#### 3. 
a.  
*   $\beta = 15$ 
*   $2+\frac{\frac{960000}{16\times1000\times1000}}{1-15\times\frac{960000}{16\times1000\times1000}}=2.6s$

b.  
*   $\beta_{miss} = 15 \times 0.4 = 6$
*   $(2+\frac{\frac{960000}{16\times1000 \times1000}}{1-6\times\frac{960000}{16\times1000\times1000}})\times0.4+0\times0.6 = 2.09475\times0.4=0.8375$

****
#### 4.  
$D_{cs} = max(\frac{NF}{u_s},\frac{F}{d_i})$  
$ D_{p2p}\geq max (\frac{F}{u_s}, \frac{F}{d_{min}}, \frac{NF}{u_s+\sum^N_{i=1}u_i} ) $
<table>
<tr>
<th>N </th>
<th>u /Mbps</th>
<th>D<sub>cs</sub>/s</th>
<th>D<sub>p2p</sub>/s</th>
</tr>
<tr>
<td>10</td>
<td>0.3</td>
<td>7500</td>
<td>7500</td>
</tr>
<tr>
<td>10</td>
<td>0.7</td>
<td>7500</td>
<td>7500</td>
</tr>
<tr>
<td>10</td>
<td>2</td>
<td>7500</td>
<td>7500</td>
</tr>
<tr>
<td>100</td>
<td>0.3</td>
<td>50000</td>
<td>25000</td>
</tr>
<tr>
<td>100</td>
<td>0.7</td>
<td>50000</td>
<td>15000</td>
</tr>
<tr>
<td>100</td>
<td>2</td>
<td>50000</td>
<td>6522</td>
</tr>
<tr>
<td>1000</td>
<td>0.3</td>
<td>500000</td>
<td>4545</td>
</tr>
<tr>
<td>1000</td>
<td>0.7</td>
<td>500000</td>
<td>2054</td>
</tr>
<tr>
<td>1000</td>
<td>2</td>
<td>500000</td>
<td>739</td>
</tr>

</table>

****
#### 5.   
*   0xc0a8+0x0268+0x7447+0x4cfe+0x6d38+0x6d2e+0x000c+0x756c+0x6c20 = 0x00034503
*   0x0003+0x4503 = 0xbaf9
*   The result is **0xbaf9**