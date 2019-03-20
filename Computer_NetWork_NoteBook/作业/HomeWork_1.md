# HomeWork_1
Name:汪至圆   SID:11610634
## 1.
### a.
For the propagation delay:
*   $ d_{prop} =  \frac{distance}{speed} = \frac{m}{s}$
*   The propagation is **$\frac{m}{s}$**
  
### b.
The transmission time of the packet is:
*   $d_{trans} = \frac{size}{rate} = \frac{L}{R}$
*   The transmission time is **$\frac{L}{R}$**

### c.
For the end-to-end delay:
*   We ignore the processing and queuing delay, so the end-to-end delay is :
    *   $d_{end-to-end} = d_{prop} + d_{trans} = \frac{L}{R} + \frac{m}{s}$
* The end-to-end delay is   **$ \frac{L}{R} + \frac{m}{s}$**

### d.
When the time=$d_{trans}$, we can know that at this time the last bit of the packet is the **L***th* bit.

### e.
* For the m let $ d_{prop} = d_{trans}$  
    *   $\because s= 2.5 * 10^8, L=120bits, R = 56bps$
    *   $\therefore d_{prop} = d_{trans} = \frac{L}{R} = \frac{120}{56} = \frac{15}{7}$
    *   $\therefore m = s * d_prop = 2.5*10^8 * 15 / 7 = 5.36 * 10^8 $
*   The distance is **$m = 5.35 * 10^8 $** meters.

## 2.
### a.
*   When circuit switching is used, the number of users that link can support is :
    *   $num = 3Mbps  \%  150kbps = 3 * 1024 \% 150 = 20$
*   **20** users can be supported.

### b.
*   For that each usee transmits only 10percent of time:
*   we can know that  the probability that a given isers is transmitting is **10%**

### c.
*  For 120 users, if there are **n** users are  transmitting simulaneously:
    *  $P_n= C_{120}^n (p^n) * ((1-p)^{120-n})=C_{120}^n0.1^n*0.9^{120-n }$
*  The probbility is **$C_{120}^n0.1^n*0.9^{120-n}$**

### d.
*   For the probability that 21 or more than 21 users transmitting simultaneously is :
    *   $P_{n \geqslant 21} = 1- P_{n < 21}$
    *   $P_{n<21}= \sum_{i=0}^{20} P_i =  \sum_{i=0}^{20}C_{120}^i 0.1^i*0.9^{120-i} = 0.99205$
    *   $P_{n\geqslant21} = 1 - P_{n<21} = 1 -0.99205 = 0.00795$
*   The probability is **0.00795**.

## 3.
### a.
*   For that there are there links 500kbps, 2Mbps, 1Mbps from A and B:
    *   $\because$ The throughput depend on the minnum value of theses three links.
    *   $\therefore R_{throughput} = \min(R1,R2,R3) = 500kbps $
*   The througput of file transfer is **500kbps**
  
### b.
*   For a 4 million bytes file
    *   $Size = 4MB = 4096KB = 8*4096Kb$
    *   $T = \frac{8*4096}{500} = 65.536 s $
*   So it will take **65.536** seconds.

### c.
*   a.
    *   $R_{throughput} = \min(R1,R2,R3) = 100kbps$
    *   The througput of file transfer is **100kbps**
*   b.
    *   $T = \frac{8*4096}{100} =327.68 s $
    *   So it will take **327.68** seconds.

## 4.
*   Home access:
    *   Digital Subscriber Line(DSL)
    *   Cable NetWork
    *   Wireless access
*   Enterprise access:
    *   Ethernet
*   Wide-area wireless access:
    *   3G
    *   4G

## 5.
### a.
*    For the average queuing delay:
     *    $\because$ 
            *    The first one won't be in queue.
            *    The second one will  delay L/R in queue
            *    The third one will delay 2L/R in queue
            *    ......
            *    The N*th* one will delay (N-1)L/R in queue
      *    $\therefore$  The total delay is $\frac{N(N-1)L}{2R}$ in queue
      *    $\therefore$ The average delay is $\frac{(N-1)}{2R}$
    *    The average delay of these N packets is $d_{avg}=\frac{(N-1)}{2R}s$

### b.
*   When N packets was arrive each LN/R seconds.
    *   $\because$Transfor N packet need LN/R seconds
    *   $\therefore$In the first LN/R seconds, the $d_{avg1} = \frac{(N-1)}{2R}$,
        In the second LN/R seconds, the  $d_{avg2} = \frac{(N-1)}{2R}$,
        ......
        In all the LN/R secons, the $d_{avg} = \frac{(N-1)}{2R}$
    *   $\therefore d_{avg} = \frac{(N-1)}{2R}$

## 6.
### a.
*   email: IMAP, STMP POP
*   WebPage: HTTP, HTTPs
*   Java use database: JDBC
*   Download server: FTP
*  Resolve domain name: DNS

### b.
Find another host by its IP address, and find the process by the port.