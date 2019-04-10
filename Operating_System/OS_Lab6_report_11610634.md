![](../_v_images/The_Logo_Of_Sustc.png)

****

<center><font face="Source Code Variable" size="6">Lab 6</font></center>
<center>姓名：汪至圆 &nbsp; 学号:11610634</center>

****

#   Fundamental:
##  Function of APIs:
*The data come form man*
*   pthread_create:
    *   Starts a new thread in the calling process.  The new thread starts execution by invoking start_routine(); arg is passed as the sole argument of start_routine().
*   pthread_join:
    *   Waits for the  thread  specified  by  thread  to  terminate. If  that thread has already terminated, then pthread_join() returns immediately. The thread specified by thread must be joinable.
*   pthread_mutex_lock:
    *   The mutex object referenced by mutex shall be locked by a call to pthread_mutex_lock() that returns zero or [EOWNERDEAD].  If the  mutex  is already  locked  by  another  thread, the calling thread shall block until the mutex becomes available.
*   pthread_cond_wait:
    *   Block on a condition variable
*   pthread_cond_signal：
    *   Unblock at least one of the threads that are blocked on the specified condition variable cond (if any threads are blocked on cond).
*   pthread_mutex_unlock:
    *   Release the mutex object referenced by mutex.

#   Producer-Consumer Problem
*   Are the data that consumers read from the buffer are produced by the same producer?
    *   For the code given, there is only one producer, so the data that consumers read from buffer are produced by the same producer.
*   What is the order of the consumer's read operations and the producer's write operations, and their relationship
    *   A product must be wrote before read. When buffer is empty, the reader will wait.
*   Briefly describe the result of the program
    *   The program simulate the procedure the producer-consumer problem with one producer, one consumer and a buffer of size 10.
*    What queue is used in this program, and its characteristics?
     *    Ring queue, it use head and tail to flag the location where to push and pop. It can push and pop in a size limited buffer efficiently
*   Briefly describe the mutual exclusion mechanism of this program:
    *   It use a mutex lock to keep there just a producer or consumer can write or read the queue. When a producer or consumer want to read or write, it will acquire a lock firstly, and release it after finish the operation.

#   Readers-Writers Problem
*   What interfaces of semaphore are used in this program?
    *   variable:
        *   rc
        *   db
    *   Function
        *   sem_init()
        *   sem_wait()
        *   sem_post()
*   What are these interfaces used for?
    *   variable:
        *   rc: Semaphore use for the readcount
        *   db: Semaphore use for operation of read and write
    *   Function
        *   sem_init(): Initializes the unnamed semaphore at the address pointed to by sem.
        *   sem_wait(): Decrements (locks) the semaphore pointed to by sem.
        *   sem_post(): Increments  (unlocks)  the  semaphore  pointed to by sem.
*   Can readers read at the same time? Why?
    *   Yes, the reader won't change the data in the buffer. It just need to use lock when add the readcount.
*   Can writers write at the same time? Why?
    *   No, the writer will change the data in the buffer, if mre than 1 writer do write operation at one time, it will cause some problem.
*   What is the performance of the reader's synchronous reading?
    *   When read and add the readcount, it need gain a lock and this will reduce the performance. 
*   After one writer writes, can the next writer write before one reader read? Why?
    *   Yes. Which operator will be chosen depending on who get the db semaphore firstly.