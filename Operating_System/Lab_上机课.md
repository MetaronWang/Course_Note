Four Fundamental OS Concepts
*   Thread
    *   执行的最小单元
*   Address Space
    *   进程执行必须要有地址空间以及线程
*   Process
    *   五个状态: new(fork()的时候, 属于new的状态, 在call fork()到ready之前的状态, 全部属于new), ready(所有的资源都已经到位, 在等待调用), running, waiting, terminated
    *   zombie状态
*   Dual mode operation
*   Heavyweight process: 一个进程中只有一个线程
    *   不会乱序执行
    *   protection
    *   Passive part
*   Context Switch

### Thread:
*   A sequential execution stream within process(Some times called a **"Lightweight process"**)
    *   Process still contains a single Address Space
    *   No protection between threads Heap
*   Multithreading: 一个程序是由多个不同的concurrent活动组成的
    *   Sometimes called multitasking as in Ada
*   为何要有thread: 线程切换开销小(多个线程可以共享一个内存地址空间)
    *   同时, 线程依旧是能concurrent 以及并行化

### 线程的状态
*   线程share memory, IO state
*   线程独有的:
    *   TCB(Thread control block)
    *   CPU registers(including program counter)(例如word的例子)
    *   Execution stack(What is this?)(例如word的例子)
        *   执行栈
        *   Parameters, temporary variables
        *   Return PCs are kept while called procedures are execution

*   Multithreaded Process:
    *   PCBs points to multiple TCBs.
    *   Switching threads within a block is a simple thread switch
    *   Switch thread s across blocks requires changes ...


*   并行:
    *   同一时间所有的task都在执行
*   并发:
    *   在一个时间段当中, 所有的task进度都能向前移动
*   并行是并发的一个子集