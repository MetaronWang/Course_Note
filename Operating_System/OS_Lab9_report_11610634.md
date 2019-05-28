![](../_v_images/The_Logo_Of_Sustc.png)

---

<center><font face="Source Code Variable" size="6">Lab 8</font></center>
<center>姓名：汪至圆 &nbsp; 学号:11610634</center>

---

## 1. Algorithms：

- Briefly describe the FIFO page-replacement algorithm and analyze its algorithm complexity
  - The simplest page-replacement algorithm is a FIFO algorithm. The first-in, first-out (FIFO) page replacement algorithm is a low-overhead algorithm that requires little bookkeeping on the part of the operating system. The idea is obvious from the name – the operating system keeps track of all the pages in memory in a queue, with the most recent arrival at the back, and the oldest arrival in front. When a page needs to be replaced, the page at the front of the queue (the oldest page) is selected. While FIFO is cheap and intuitive, it performs poorly in practical application.
  - Find: O(n), Add: O(1), Replacement: O(1)
- Briefly describe the MIN page-replacement algorithm and analyze its algorithm complexity
  - Replace the page which won't be used in the longest time.
  - Init:O(m) Find: O(n), Add： O(1), Replacement: O(n)
- Briefly describe the LRU page-replacement algorithm and analyze its algorithm complexity
  - The least recently used (LRU) page replacement algorithm, though similar in name to NRU, differs in the fact that LRU keeps track of page usage over a short period of time, while NRU just looks at the usage in the last clock interval. LRU works on the idea that pages that have been most heavily used in the past few instructions are most likely to be used heavily in the next few instructions too. While LRU can provide near-optimal performance in theory (almost as good as adaptive replacement cache), it is rather expensive to implement in practice.
  - Find: O(n), Add: O(1), Replacement: O(log n)
- Briefly describe the clock algorithm and analyze its algorithm complexity
  - Clock is a more efficient version of FIFO than Second-chance because pages don't have to be constantly pushed to the back of the list, but it performs the same general function as Second-Chance. The clock algorithm keeps a circular list of pages in memory, with the "hand" (iterator) pointing to the last examined page frame in the list. When a page fault occurs and no empty frames exist, then the R (referenced) bit is inspected at the hand's location. If R is 0, the new page is put in place of the page the "hand" points to, and the hand is advanced one position. Otherwise, the R bit is cleared, then the clock hand is incremented and the process is repeated until a page is replaced.[
  - Find: O(n), Add: O(1), Replacement: O(n)
- Briefly describe the second-chance algorithm and analyze its algorithm complexity
  - A modified form of the FIFO page replacement algorithm, known as the Second-chance page replacement algorithm, fares relatively better than FIFO at little cost for the improvement. It works by looking at the front of the queue as FIFO does, but instead of immediately paging out that page, it checks to see if its referenced bit is set. If it is not set, the page is swapped out. Otherwise, the referenced bit is cleared, the page is inserted at the back of the queue (as if it were a new page) and this process is repeated. This can also be thought of as a circular queue. If all the pages have their referenced bit set, on the second encounter of the first page in the list, that page will be swapped out, as it now has its referenced bit cleared. If all the pages have their reference bit cleared, then second chance algorithm degenerates into pure FIFO.
  - Find: O(n), Find: O(1), Replacement:O(1)

## 2.Fundamental:

- In theory, the optimal page-replacement algorithm is **MIN**, and prove it:

  - When we use the MIN algorithm, we will replace the page which won't be used in the longest time.
  - When one miss happened, let the page which need be repleaced by the MIN algorithm will be used after _n_ cycles.
  - Suppose we replace the other pages. Then the page we choice will be used after _m_ cycles and m<n;
    - Then we can know the miss cause by the replacement will happen earlier
    - And the pages between _m_ and _n_ may happen other miss.
  - So, the MIN is the optimal algorithm.

- Can the FIFO page-replacement algorithm be improved? If yes, please provide a plan; If no, please give your proof.
  - Yes, we can improve it to O(1) complexity. When add and replacement, it's complexity is O(1), and I add a HashMap or a HashSet to storage the information of the cache, then the complexity of find can also reduce to O(1).
- Can the LRU page-replacement algorithm be improved? If yes, please provide a plan; If no, please give your proof.
  - Yes. We use a HashMap:
    - It's value is index of the pages in the cache
    - The value is the address of the pages in the list.
    - When hit, remove the pages hited to the head of the list.
    - When miss and full, pop the tail of the list.
  - Then the complexity can be O(1).

## 4.Program running result：

### Hit percent:

| Algorithm/tests |  1.in  |  2.in  |  3.in  |
| :-------------: | :----: | :----: | :----: |
|      FIFO       | 11.98% | 11.85% | 82.36% |
|       MIN       | 42.40% | 43.27% | 88.58% |
|       LRU       | 11.76% | 11.85% | 82.39% |
|      Clock      | 11.93% | 11.83% | 82.38% |
|  Second-Chance  | 11.85% | 11.85% | 82.39% |

### Time:(Optimized by O3 when comparing)

| Algorithm/tests |  1.in  |  2.in  |  3.in  |
| :-------------: | :----: | :----: | :----: |
|      FIFO       | 0.0039 | 0.0323 | 0.0130 |
|       MIN       | 0.0246 | 0.2846 | 0.0683 |
|       LRU       | 0.0025 | 0.0282 | 0.0227 |
|      Clock      | 0.0019 | 0.0241 | 0.0178 |
|  Second-Chance  | 0.0038 | 0.0434 | 0.0144 |
