# Adaptive Replacement Cache (ARC)
Implementation of Adaptive Replacement Cache Algorithm using Java and MySQL

**Requirements:**

Java, MySQL
<br><br>

**Instructions for database in MySQL**

:point_right: create database arcache;  // use to create database

:point_right: use arcache;              // to use the database  

:point_right: CREATE TABLE `cache` (`pageId` int(11) DEFAULT NULL,`pageValue` int(11) DEFAULT NULL); // To create table in the database

or

**Import the database scheme for Pages [arcache.sql](arcache.sql)**
<br><br>

**Instructions to Run the Project**
-  Run [Main.java](Main.java) file

<br><br>
**Theory**
- ARC maintains two LRU pages lists: L1(T1 + B1) and L2(T2 + B2)
- L1 captures recency, while L2 captures frequency
- Total number of pages = 2*c, c is cache size
- ARC caches a variable number of most recent pages from both L1 and L2 such that the total number of cached pages is c.
- The pages in T1 and T2 reside in the cache directory and in the cache, but the history pages in B1 and B2 reside only in the cache directory, not in the cache.
