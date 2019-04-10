![](The_Logo_Of_Sustc.png)

<center><font face="Source Code Variable" size="6">OS Project1 Design Document</font></center>
<p><center>姓名：汪至圆 &nbsp; 学号11610634</center></p>

****

#   I. Task1 Efficient Alarm Clock
##  Data Structure and Function
*   Data Structure:
    *   LinkedList: I will use a List to build a list called sleep_list to storage the threads which is blocked.
        ```C
            static struct list sleep_list;
        ```
    *   thread, I need to change the structure $thread$, add a member variable $sleep\_ticks$ to storage how many times this thread need to sleep at the current tick(in src/thread/thread.h).
        ```C
            struct thread{
                \*The original member variable*\
                int64_t sleep_ticks;
            }
        ```

*   Function:
    *   $timer\_sleep()$: This is a function which exist in the src/device/timer.c, this function is used to sleep the thread by use function $thread\_yield()$, but it's not a good way to sleep the function in this way and the reason I will mention in the Rationale part. So we need to modify this function to fit my algorithm
    *   $timer\_block$: This is a function which exist in the src/device/timer.c, this function is used to block a thread, it will choice the current thread and change the $thread\rightarrow status$ to THREAD_BLOCKED
    *   $timer\_block$: This is a function which exist in the src/device/timer.c, this function is used to block a thread, it will choice the thread incoming and change the $thread\rightarrow status$ to THREAD_READY, then push this $thread\rightarrow elem$ to the $ready\_list$.
    *   $thread\_foreach$: This is a function which exist in the src/thread/thread.c, this function will be transferred in the function $timer\_interrupt()$, and it will execute a function which is incoming into itself.
    *   $thread\_check$: This function will be created by myself, it will be transferred into the thread\_foreach and will be executed in each timer interrupt. This will check if the thread need be unblocked or not.
    *   $thread\_create$: This is a function which exist in the src/thread/thread.c, this function is used to created a new thread, and for that we have a new member variable $sleep\_ticks$ in the structure $thread$, so we need add a statement in this function.
        ```C
            t->sleep_ticks=0;
        ```

##  Algorithm:
In this part, I have two idea to implement it, but I haven't try it so I don't know which can be better.
*   The first is to create a list $block\_list$ to storage the threads which are sleeping.
    *   Execute $timer\_sleep()$
        *   Set the current status be Interrupted Disabled, and storage the old status into the $old\_level$
        *   Change the $sleep\_ticks$ of the current thread
        *   block the current thread.
        *   push the current thread into the $block\_list$
        *   set the status be the $old\_level$
    *   When the timer interrupt:
        *   If the $block\_list$ isn't empty, then traverse all the list and check the $sleep\_ticks$.
            *   If equal to 0, then unblock that thread
            *   Else $sleep\_ticks$ will minus one.
        *   Else won't do anything.
*   The second way is that won't create list to storage the threads that are blocked, I distinguish the thread by the status, and in the timer interrupt, I will check all the threads.
    *   Execute $timer\_sleep()$
        *   Set the current status be Interrupted Disabled, and storage the old status into the $old\_level$
        *   Change the $sleep\_ticks$ of the current thread
        *   block the current thread.
        *   set the status be the $old\_level$
    *   When the timer interrupt:
        *   Execute the function $thread\_foreach$
            *   If the status is blocked
                *   If equal to 0, then unblock that thread
                *   Else $sleep\_ticks$ will minus one.

##  Synchronization:
*   Simultaneously Calling: When execute the $timer\_sleep$, execute $intr\_disable()$ immediately.
*   Timer Interrupt: The operation of the change the sleep_ticks and unblock the threads are all in the timer interrupt of the system.

##  Rationale
For the original alarm clock, the thread will switch between running and in the ready list, this will take so much resource of the CPU, it's so inefficient

But for my design, the thread will be block when sleep, it just take some operation when the timer interrupt to check if the thread need be block.

#   II. Task2: Priority Scheduler
##  Data Structure and Function:
*   Data Structure:
    *   $thread$, need add some member variables into this structure(in src/thread/thread.h):
        ```C
        struct thread{
            \*The original member variable*\
            int base_priority;                 
            struct list locks;                  
            struct lock *lock_waiting;
        }
        ```
        *   $base\_priority$ is the original priority
        *   $locks$ is the list of the locks which are holded by the thread
        *   $lock_wait$ is the locks that this thread is waiting.
    *   $lock$, need add some member variables into this structure(in src/thread/synch.h):
        ```C
        struct lock{
            \*The original member variable*\
            struct list_elem elem;
            int max_priority;
        }
        ```
        *   $elem$ is to store the donate
        *   $max\_priority$ is the max priority of the threads which have acquired this lock.
*   Function:
    *   $lock_acquire()$: This is a function which exist in the src/thread/synch.c, it was used to do the P operation of the semaphore and get the lock. But we need to do the priority donate so we need to add some extra statement to implement this demand.
    *   $thread\_hold\_lock()$: This  is the function to  let the thread hold a lock
    *   $thread\_priority\_donate()$: This is the function to do the operations about the priority donate.
    *   $list\_insert\_ordered()$: This is a function which exist in the src/lib/kernel/list.c, it will insert the element into the list by the return value of a function we give. We can use this function to maintain a priority queue.
        *   Will use in the:
            *   $thread\_unblock()$
            *   $init\_thread()$
            *   $thread\_yield()$
            *   $thread\_hold\_lock()$
            *   $thread\_update\_priority()$
    *   $priority\_compare()$: This function is used to define the rule that the $list\_insert\_ordered()$ used for the ready_list. 
    *   $lock\_release()$: This is a function which exist in the src/thread/synch.c, it was used to release the lock of the current thread. In the original function, it just let the $lock\rightarrow holder$ be NULL, but we have add a member variable $locks$ into the structure $thread$, so we need add some statements into this function or create a extra function to delete the lock from the $current\_thread\rightarrow locks$.
    *   $update\_thread\_priority()$: This function is used to update the priority of the thread.
    *   $set\_thread\_priority()$: This is a function which exist in the src/thread/thread.c, it is used to set the priority of the current thread. For the modify in the structure $thread$, so we also change something in this function.
    *   $cond\_signal()$: This is a function exist in the src/thread/synch.C, it was used to send a signal to a thread which is waiting the lock and wake up it. For that we need priority scheduler, so we also modify this function let threads line up by the priority.
    *   $sema\_down$, $sema\_up$: They are the "P", "V" options of the semaphore, they also need to modify to operate by the priority.

##  Algorithm:
*   Choosing the next thread to run:
    *   Pop the first element of the $ready\_list$ is Ok, for that when we push the thread into the $ready\_list$, we use $list\_insert\_ordered()$ and $priority\_compare()$ to make sure the threads in the $ready\_list$ are line up by the priority.
*   Acquiring a Lock:
    *   When a thread want to acquiring a lock, it will call the $lock\_acquire()$:
        *   If the lock has holder, then apply for the priority donate:
            *   Let $current\_thread\rightarrow lock\_wait$ be the lock it want to acquire.
            *   new a new $lock$ variable $l$ which is equal to the lock that the current acquire.
            *   Then execute the $thread\_priority\_donate$ recursively to update the threads' priority until the $l$ is NULL or the $l\rightarrow max\_priority$ is not less the priority current thread. .
                *   Get the holder of the $l$.
                *   Update the $l\rightarrow max\_priority$.
                *   Update the priority of $l\rightarrow holder$ with $l\rightarrow max\_priority$
                *   Update the $ready\_list$
                *   let $l$ be $l\rightarrow holder\rightarrow lock_wait$
        *   Do the "P" operation of the semaphore for the lock's semaphore
        *   Disabled the interrupt.
        *   Update the current thread
        *   Update the $lock\rightarrow max\_priority$ with the priority of the thread
        *   Update $current\_thread\rightarrow locks$ and $lock\rightarrow holder$.
        *   Enable the interrupt.
*   Releasing a Lock:
    *   When it want to release a lock, it will call $lock\_release()$:
        *   Disabled the interrupt.
        *   Let $lock\rightarrow holder$ be NULL
        *   Remove the lock from the $lock\rightarrow holder\rightarrow locks$.
        *   Update the $lock\rightarrow holder\rightarrow priority$
            *   Equal to the max of the $lock\rightarrow max\_priority$ which in the $thread\rightarrow locks$ and the $thread\rightarrow base\_priority$.
        *   Enable the interrupt.
*   Computing the effective priority:
    *   Like the computing procedure in the Acquiring a lock:
        *   Let $current\_thread\rightarrow lock\_wait$ be the lock it want to acquire.
        *   new a new $lock$ variable $l$ which is equal to the lock that the current acquire.
        *   Then execute the $thread\_priority\_donate$ recursively to update the threads' priority until the $l$ is NULL or the $l->max\_priority$ is not less the priority current thread. .
            *   Get the holder of the $l$.
            *   Update the $l\rightarrow max\_priority$.
            *   Update the priority of $l\rightarrow holder$ with $l\rightarrow max\_priority$
            *   Update the $ready\_list$
            *   let $l$ be $l\rightarrow holder\rightarrow lock_wait$
*   Priority scheduling for semaphores and locks:
    *   $sema\_up()$:
        *   Disabled the interrupt.
        *   if $sema\rightarrow waiters$ is not empty
            *   use $list\_sort()$ to sort the $sema\rightarrow waiters$ by their priorities.
            *   use $thread\_unblock()$ to unblock the front of the $sema\rightarrow waiters$.
        *   Enable the interrupt.
    *   $sema\_down()$:
        *   Disabled the interrupt.
        *   if $sema\rightarrow waiters$ is not empty
            *   use$list\_insert\_ordered()$ to ordered insert the current thread into the $sema\rightarrow waiters$ by the priority.
        *   Enable the interrupt.
*   Priority scheduling for condition variables:
    *   $cond\_signal()$:
        *   Disabled the interrupt.
        *   If $cond\rightarrow waiters$ is not empty
            *   use$list\_insert\_ordered()$ to ordered insert the current thread into the $cond\rightarrow waiters$ by the priority.
        *   Enable the interrupt.
*   Changing thread’s priority:
    *   Call $thread\_set\_priority()$:
        *   Disable the interrupt
        *   Change the $thread\rightarrow base\_priority$ to the priority value incoming
        *   If $thread\rightarrow locks$ is empty or $thread\rightarrow priority$ less than the priority value incoming
            *    Change the $thread\rightarrow priority$ to the priority value incoming
            *    Call the $thread\_yield()$
        *   Enable the interrupt

##  Synchronization:
For all the options that need to change the recent_cpu and priority, I will disable the interrupt and recover it after the operation end. There are no race condition.
*   For all the options that need to change the priority, I will disable the interrupt and recover it after the operation end. 
*   For the priority queue: When I insert the thread into the priority, I will use $list\_insert\_ordered()$, so after the insert, the list must be ordered, and before the operation of the insert, I will disable the interrupt and recover it after the operation is finished. 

##  Rationale:
The reason I choose this schema is that this schema have simple logic to understand, and it use the original code of the Pintos as much as possible for me. And it have detailed implement claim so that it was convenient to write the code. Also, I didn’t consider any other designs that seemed feasible and effective like this design. 

#   III. Task3: Multi-level Feedback Queue Scheduler
##  Data Structure and functions:
*   Data Structure:
    *   $thread$: We need add two member variables into the structure $thread$:
        ```C
            struct thread{
                \*The original member variable*\
                int nice;
                fixed recent_cpu;
            }
        ```   
        *   $nice$ is the niceness
        *   $recent\_cpu$ is the value of recent cpu.
    *   $load\_avg$: We need add a global variable $load\_avg$
*   Function:
    *   $init\_thread()$ This is a function which exist in the src/thread/thread.c, for that we add two new member variable into the struct $thread$, so we need to initialize them when initialize a new thread variable:
        ```C
        t->nice = 0;
        t->recent_cpu = FP_CONST (0);
        ```   
    *   $timer\_interrupt()$: This is a function which exist in the src/device/timer.c, it was use to handle the timer interrupt. In this task, for that we need add some part to update the $recent\_cpu$, $load\_avg$ and the $priority$
    *   $thread\_set\_nice()$: This is a function which exist in the src/thread/thread.c, we need to implement to set niceness, we will set the niceness for the current thread, and then update the $priority$, call the $thread\_yield()$ to switch the thread
    *   $thread\_get\_nice()$: This is a function which exist in the src/thread/thread.c, we need to implement to get niceness, we will get the niceness from the current thread by $thread\rightarrow nice$
    *   $thread\_get\_load\_avg()$: This is a function which exist in the src/thread/thread.c, we need to implement to get load_avg, we will set the load_avg by call $FP\_ROUND(FP\_MULT\_MIX(load\_avg, 100))$
    *   $thread\_get\_recent\_cpu()$: This is a function which exist in the src/thread/thread.c, we need to implement to get recent_cpu, we will get the recent_cpu by $FP\_ROUND(FP\_MULT\_MIX(thread\_current()\rightarrow recent\_cpu,100))$.
    *   $mlfqs_update\_priority()$: This function is used to update the $thread\rightarrow priority$ in the timer interrupt.
    *   $mlfqs_update\_load_avg()$: This function is used to update the $load\_avg$ in the timer interrupt.
    *   $mlfqs_update\_recent\_cpu()$: This function is used to update the $thread\rightarrow recent\_cpu$ in the timer interrupt.
    *   $thread\_start()$: Add a statement to initialize the $load\_avg$
        ```C
        load_avg = FP_CONST (0);
        ```
    *   **To be compatible with the task2**, some changes in the task2 to the original functions belong to the Pintos will add a if statement by using the global boolean variable $mlfqs$
##  Algorithm:
*   Calculating Priority:
    $priority=PRI\_MAX-(recent\_cpu/4)-(nice\times 2)$
*   Calculating Recent CPU:
    $x(0)=f(0)$
    $x(t)=a\times x(t-1)+f(t)$
    $a=k/(k+1)$
    $recent\_cpu=(2\times load\_avg)/(2\times load\_avg+1)\times recent\_cpu+nice$
*   Calculating Load Average:
    $load\_avg=(59/60)\times load\_avg+(1/60)\times ready\_threads$
*   Update:
    *   I will update the $recent\_cpu$ and $load\_avg$ per $TIMER\_FREQ$ ticks, that $TIMER\_FREQ$ is define in the src/device/timer.h, which means number of timer interrupts per second. 
    *   I will update the $priority$ per 4 ticks.


## Synchronization:
For all the options that need to change the recent_cpu and priority are in the timer interrupt.

##  Rationale:
The key of this method is the algorithm to get the value of $priority$, $recent\_cpu$ $load\_avg$. We can schedule threads reasonably by the $recent\_cpu$. When $recent\_cpu$ is large, means that cpu is busy and the  $priority$ of threads should decrease. Some time later, the $recent\_cpu$ become smaller and it will adjust the $priority$ of threads as feedback. And this algorithm just occupies a little memory.

#   IV. Answer questions about pintos:
##  1.  The MLFQS problem in released project 1 document
*   `Suppose threads A, B, and C have nice values 0, 1, and 2. Each has a recent_cpu value of 0. Fill in the table below showing the scheduling decision and the recent_cpu and priority values for each thread after each given number of timer ticks. We can useR(A)andP(A)to denote the recent_cpu and priority values of thread A, for brevity`
  
|timer ticks|R(A)|R(B)|R(C)|P(A)|P(B)|P(C)|thread to run|
|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|:--:|
|0|0|0|0|63|61|59|A|
|4|4|0|0|62|61|59|A|
|8|8|0|0|61|61|59|B|
|12|8|4|0|61|60|59|A|
|16|12|4|0|60|60|59|B|
|20|12|8|0|60|59|59|A|
|24|16|8|0|59|59|59|C|
|28|16|12|0|59|58|59|C|
|32|16|12|4|59|58|58|A|
|36|20|12|4|58|58|58|C|


*   `Did any ambiguities in the scheduler specification make values in the table (in theprevious question) uncertain? If so, what rule did you use to resolve them?`
    *   Yes, like the 8th, 16th, 24th, 28th, 36th ticks.
    *   I apply the rule to random run it.

##  2.  Answer questions about pintos source code
*   `Tell us about how pintos start the first thread in its thread system (only consider the thread part)`
    *   Call the $thread\_init()$
    *   The pintos initialize the threading system by transforming the code that's currently running into a thread
*   `Consider priority scheduling, how does pintos keep running a ready thread with highest priority after its time tick reaching **TIME_SLICE**?`
    *   $TIME\_SLICE$ was define in the src/threads/thread.c:54
    *   $timer\_interrupt()$(src/devices/timer.c:171) will be executed each timer interrupt.
    *   $timer\_interrupt()$ will call the $thread\_tick()$(src/threads/thread.c:123)
    *   $thread\_tick()$ will call $intr\_yield\_on\_return()$(src/threads/interrupt.c:222), it will let $yield\_on\_return$ be true. 
    *   And then we can find $intr\_handler$(src/threads/interrupt.c:345), which to handler for all interrupts, faults, and exceptions. It will call the $thread\_yield()$(src/threads/thread.c:302)
    *   $thread\_yield()$ will switch the current thread and call $schedule()$ to schedule the thread with the highest priority in the $ready\_list$
*   `What will pintos do when switching from one thread to the other? By calling what functions and doing what?`
    *   $thread\_yield()$ was called
    *   If the current thread isn't free, push it into the $ready\_liat$ and change it's status to THREAD_READY
    *   $schedule()$(src/threads/thread.c:553) will be called:
        *    Get the current thread $cur$
        *    Get the next thread to run $next$ by call $next\_thread\_to\_run$(src/threads/thread.c:491)
             *    If $ready\_list$ is empty it will return a empty pointer, else it will return the first thread in the $ready\_list$.
        *    Make true current status is disable interrupt, $cur\rightarrow status$ is THREAD_RUNNING and the $next$ is a thread
        *    If $cur$ is different from $next$, call $switch\_threads()$(implement by Assembly language and in src/threads/switch.S:17) and the return value will be given to $prev$
        *    call $thread\_schedule\_tail()$
             *    Get the current thread $cur$(Has been switch )
                  *    The thread will change is the $ready\_list$ is not empty.
             *    Change it status to THREAD_RUNNING
             *    set $thread\_tickd$ to 0
             *    Call $process\_activate()$ to activate the new space.

##  3.  How does pintos implement fixed point number operation
*   Fixed-point number representation is a real type with a fixed number of digits after radix point. And there are 16bits after radix point in our project.
*   $FP\_SHIFT\_AMOUNT$ 16
*   $FP\_CONST(A)$:(fixed_t)A << $FP\_SHIFT\_AMOUNT$
    *   Left shifting A 16 bits
*   $FP\_ADD(A,B)$: A + B
    *   Just add A and B is OK
*   $FP\_ADD\_MIX(A,B)$: A + (B << $FP\_SHIFT\_AMOUNT$)
    *   When add fix point and integer , left shifting fixed point 16bit and add them
*   $FP\_SUB(A,B)$: (A - B)
    *   Just substract directly
*   $FP\_SUB\_MIX(A,B)$: (A - (B << $FP\_SHIFT\_AMOUNT$))
    *   When add fix point and integer , left shifting fixed point 16bit and substract
*   $FP\_MULT\_MIX(A,B)$: (A * B)
    *   Multiply directly.
*   $FP\_DIV\_MIX(A,B)$ (A / B)
    *   Divide directly
*   $FP\_MULT(A,B)$ ((fixed_t)(((int64_t) A) * B >> $FP\_SHIFT\_AMOUNT$))
    *   When two multiply fixed-point value, They should have 32bits after the radix point, but the fixed point have 16bits after radix point, so they need right shifting 16bits
*   $FP\_DIV(A,B)$ ((fixed_t)((((int64_t) A) << $FP\_SHIFT\_AMOUNT$) / B))
    *   When divide two fixed-point value, translate A to int and do $FP\_DIV\_MIX$
*   $FP\_INT\_PART(A)$ (A >> $FP\_SHIFT\_AMOUNT$)
    *   Get integer integer of a fixed-point value, so right shifting 16bits
*   $FP\_ROUND(A)$ (A >= 0 ? ((A + (1 << ($FP\_SHIFT\_AMOUNT$ - 1))) >> $FP\_SHIFT\_AMOUNT$):((A - (1 << ($FP\_SHIFT\_AMOUNT$ - 1))) >> $FP\_SHIFT\_AMOUNT$))
    *   Get rounded integer of a fixed-point value. So it should think the case that negative number

##  4.  What do priority-donation test cases(priority-donate-chainand priority-donate-nest)do and illustrate the running process:
(*Sorry teacher, the time is not enough, so I don't use latex format to write the function and variable*)
*   What it do?
    *   original_thread has priority PRI_DEFAULT
    *   Create a lock, and original_thread get it.
    *   Create a thread acquire1 with priority PRI_DEFAULT+1 and transfer a function with the lock as argument to it, means when acquire1 execute will call it.
    *   Then acquire1 will execute immediately and get the lock
    *   call sema_down(), The "P" operation in the semaphore.
    *   create a thread acquire2 with priority PRI_DEFAULT+2, and for the priority schedule, acquire2 will execute first, then acquire1, then original_thread
*   This test want original_thread will be changed to PRI_DEFAULT+1, then change to PRI_DEFAULT+2.
*   The lock of the original called by the acquire1, acquire1 will be blocked, then acquire need original_thread to execute to release the lock, then original_thread will be donated to PRI_DEFAULT+1
*   acquire2 is the same as above.

