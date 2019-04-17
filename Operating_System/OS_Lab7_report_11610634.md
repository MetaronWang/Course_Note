![](../_v_images/The_Logo_Of_Sustc.png)

---

<center><font face="Source Code Variable" size="6">Lab 7</font></center>
<center>姓名：汪至圆 &nbsp; 学号:11610634</center>

---

# Fundamental

## What is deadlock?

- Deadlock is a state in which each member of a group is waiting for another member, including itself, to take action, such as sending a message or more commonly releasing a lock.

## What are the requirements of deadlock?

- Mutual exclusion
  - Only one thread at a time can use a resource.
- Hold and wait
  - Thread holding at least one resource is waiting to acquire additional resources held by other threads
- No preemption
  - Resources are released only voluntarily by the thread holding the resource, after thread is finished with it

* Circular wait
  - There exists a set $\{ T_1, …, T_n \}$ of waiting threads
    - T1 is waiting for a resource that is held by T2
    - T2 is waiting for a resource that is held by T3
    - …
    - Tn is waiting for a resource that is held by T1

## What’s different between deadlock prevention and deadlock avoidance?

- Deadlock prevent:
  - Destory the requirements of deadlock
- Deadlock avoidance:
  - If a process will cause deadlock, won't new it.
  - If a process requst to much resource and maybe cause deadlock, won't allocate resource to it.

## How to prevent deadlock? Give at least two examples.

- prevent the deadlock cause by No preemption
  - If a process who hold some resource is refused, it will release all the resource it hold.
- prevent the deadlcok cause by the circular wait
  - Define the linear order for the type of the resource.

## Which way does recent UNIX OS choose to deal with deadlock problem, why?

- Ostrich strategy, it will ignore deadlock
- Because the probability of a deadlock occurring is very low, and the dealing with deadlocks is costly

# Banker’s algorithm

## What data structures you use in your implementation? Where and why you use them? Are they optimal for your purpose?

- Map
  - I use it to store the information of the process.
  - I create a map as a global variable, which key is a int and the value is int array.
  - The key is the pid
  - The value will store the max number of the resources and the number of used resource.
- Optimal:
  - I can get the detail of one process qucikly by using map.
  - Merge the max number and used number of process in one array can just use one map and save time.
