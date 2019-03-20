```sql
Select bucket ,min(surname),max(surname)
form(Select surname, 
       ntile(255) over (order by surname) bucket
    from people order by surname)
group by bucket
order by bucket
```

### More on performance
##### Plan Change
"Attach" execution plan to query  
- Estimate quert Cost  
- Execute Query  
- Compare to the  

Adaprative Cursor Sharing  
bind Sensitiveness  
Bind awareness


### Write or ReWrite a Correctly Query
#### Pahse1
Removw what doesn't shape ResultSet.尽可能精简返回的值

Foreign Key or Not Foreign Key 不必要的外键校验同样会损失性能




