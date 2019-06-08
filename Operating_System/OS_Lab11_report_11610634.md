![](../_v_images/The_Logo_Of_Sustc.png)

---

<center><font face="Source Code Variable" size="6">Lab 11</font></center>
<center>姓名：汪至圆 &nbsp; 学号:11610634</center>

# Question:

### A. Consider a really simple filesystem, cs302fs, where each inode only has 10 direct pointers, each of which can point to a single file block. Direct pointers are 32 bits in size (4 bytes). What is the maximum file size for cs302fs?

$$
\begin{aligned}
    &\text{For this file system it only have 10 direct pointer and on indirect pointer.}\\
    &\text{We assume the block size is 4KBytes}\\
    &\therefore \text{The maximum file size for cs302fs is :}\\
    &\quad10\times4Kbytes = 40KBytes
\end{aligned}
$$

### B. Consider a filesystem, called extcs302fs, with a construct called an extent. Extents have a pointer (base address) and a length (in blocks). Assume the length field is 8 bits (1 byte). Assuming that an inode has exactly one extent. What is the maximum file size for extcs302fs?

$$
\begin{aligned}
    &\text{For the extent in the extcs302fs, the length is 8 bits}\\
    &\text{We assume the block size is 4KBytes}\\
    &\therefore \text{The maximum file size for extcs302fs is:}\\
    &\quad (2^8-1)\times 4KBytes = 1020KBytes
\end{aligned}
$$

### C. Consider a filesystem that uses direct pointers, but also adds indirect pointers and double-indirect pointers. We call this filesystem, indcs302fs. Specifically, an inode within indcs302fs has 1 direct pointer, 1 indirect pointer, and 1 doubly-indirect pointer field. Pointers, as before, are 4 bytes (32 bits) in size. What is the maximum file size for indcs302fs?

$$
\begin{aligned}
    &\text{For the indcs302fs, the size of the blocks is:}\\
    &\quad 1 + 1\times\frac{4Kbytes}{4Bytes}+1\times\frac{4Kbytes}{4Bytes}\times\frac{4Kbytes}{4Bytes} = 1+2^{10}+2^{20}\\
    &\text{Assume the block size is 4KBytes}\\
    &\therefore\text{The maximum file size of incs302fs is: }\\
    &\quad (1+2^{10}+2^{20})\times 4KBytes = 4GB+4MB+4KB
\end{aligned}
$$

### D. Consider a compact file system, called compactfs, tries to save as much space as possible within the inode. Thus, to point to files, it stores only a single 32-bit pointer to the first block of the file. However, blocks within compactfs store 4,092 bytes of user data and a 32-bit next field (much like a linked list), and thus can point to a subsequent block (or to NULL, indicating there is no more data). First, draw a picture of an inode and a file that is 10 KBytes in size. Second, what is the maximum file size for compactfs (assuming no other restrictions on file sizes)?]
*   Picture of an inode and a file that is 10 KBytes in size  
    ![](_v_images/20190528_220307.jpg)

*   The maximum file size for compactfs
    $$
    \begin{aligned}
        &\text{For compactfs, a file can linked by the end of block for as many as possible times}\\
        &\therefore Max\quad Size: 2^{32}\times4KBytes = 2^{44}Bytes = 16TBytes
    \end{aligned}
    $$

### E. The Linux journaling file system writes the content of all modified disk blocks to the log. Your friend Bob considers such logging to be wasteful since copying the content of modified disk blocks to the log doubles the amount of disk writes for each logged file system operation. Bob decides to implement a more efficient journaling file system. In particular, he decides to only record an operation’s name and parameter in the log file instead of recording the content of all modified blocks. For example, for an operation that creates file “/d/f”, the file system would append the transaction record of the form [create “/d/f”] to the log. Bob’s file system ensures that the corresponding transaction record is written to the log before the modified disk blocks are flushed to disk. Upon crash and recovery, Bob’s file system re-executes the logged file system operations and truncates the log. Bob’s new logging mechanism is certainly more efficient since each transaction record is much smaller than that with Linux’s logging. Is his design also correct? Specifically, can it recover a file system correctly from crashes? Explain your reasoning and give concrete examples.
*   Not correct.
*   A action in filesystem like create or delete file need many steps if and crash happened when the action haven't finished, and the Bob's logging mechanism will be invalid. 

### F. Your friend suggests doubling the Pintos block size from 512 bytes to 1,024 bytes, since that means you will be able to reach twice as much data from the direct pointers (as a result, medium sized files could fit entirely within the direct region). Why might it be a bad idea to increase the block size?

$$\text{Will increase the internal fragmentation.}$$

### G. The NFS authors had a goal of transparency. They wanted applications to be unable to distinguish whether a file system was (a) a remote file system served from an NFS server; or (b) a typical, local Unix file system. They did not succeed (in fact, their goal was impossible). Briefly state one way in which application code can experience different behavior when interacting with a remote NFS file system versus a local Unix file system. Your answer should be in terms of what application code sees, rather than in terms of what a global observer sees.
*   The read and write operations have higher latency.
*   The read and write operation will failed when they are being done. (Cause by the network)