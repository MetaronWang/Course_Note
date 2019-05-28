![](../_v_images/The_Logo_Of_Sustc.png)

---

<center><font face="Source Code Variable" size="6">Lab 10</font></center>
<center>姓名：汪至圆 &nbsp; 学号:11610634</center>

---

# Experiments

## 1.Fundamental:

### a. According to the unit data read mode, I/O devices can be classified as

1.  Byte
2.  Block
3.

### b. I/O control methods can be classified as

1. Programmed IO
2. Direct memory access

### c. Each physical record on the disk has a unique address that consists of three parts:

1. Head
2. Track
3. Sector

### d. Data READ/WRITE time

1. Seek time
2. Rotational latency
3. Transfer time

### e. The metric for measuring I/O performance are

1. Response Time
2. Throughput

### f. What are the work steps of the DMA controller? Please answer it and briefly describe the process of each step.

- Device driver is told to transfer disk data to buffer at address X
- Device driver tells disk controller to transfer C bytes from disk to buffer at address X
- Disk controller initiates DMA transfer
- Disk controller send each byte to DMA controller
- DMA controller transfers bytes to buffer X, incresasing memory address and decreasing C until C=0;
- When C=0, DMA interrupts CPU to signal transfer completion.

## 2.Application

Let a single-sided disk rotation speed be 12000r/min, each track has 100 sectors, 200 tracks in total, and the average movement time between adjacent tracks is 1 ms.
If at some point, the head is located at track 100 and moves in the direction in which the track number increases, the track number request queue is 70, 90, 30, 120, 20, 60. For each track in the request queue, a randomly distributed sector is read.

### a. If the C-SCAN algorithm is used to read the six sectors,

#### 1) Write the track access sequence

100->120->199->0->20->30->60->70->90

#### 2) How much time is required in total? The calculation process is required.

$Time_{sector} = \frac{50\times1r}{100\times12000r/min}=\frac{50}{1200000}min=2.5ms$  
$Time_total=99+90+2.5\times6=204ms$

### b. If using SSD, which scheduling algorithm do you think should be used, and explain why?

FCFS algorithm.  
Reason: The SSD don't have seek time and rotational latency, so it needn't to set the access order.

## 3. Programming:

| Algorithms/Tests | 1.in |   2.in   |   3.in    |
| :--------------: | :--: | :------: | :-------: |
|       FCFS       | 676  | 22173758 | 215124803 |
|       SSTF       | 554  |  102429  |   95951   |
|       SCAN       | 850  |  93760   |   95987   |
|      C-SCAN      | 542  |  65445   |   65529   |
|       LOOK       | 508  |  93744   |   95951   |
|      C-LOOK      | 367  |  65301   |   65505   |
