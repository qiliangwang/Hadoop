570 Managers with at Least 5 Direct Reports

```sql
DROP TABLE IF EXISTS `Employee`;

CREATE TABLE `Employee` (
  `Id` int(11) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Department` varchar(255) DEFAULT NULL,
  `ManagerId` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `Employee` VALUES (101,'John','A',NULL),(102,'Dan','A',101),(103,'James','A',101),(104,'Amy','A',101),(105,'Anne','A',101),(106,'Ron','B',101);
```

```sql
SELECT
    Name
FROM
    Employee AS t1 JOIN
    (SELECT
        ManagerId
    FROM
        Employee
    GROUP BY ManagerId
    HAVING COUNT(ManagerId) >= 5) AS t2
    ON t1.Id = t2.ManagerId
;
```

```sql
SELECT 
	Name
FROM Employee where Id in
	(select
     	ManagerId
	from 
     	Employee 
    group by 
     	ManagerId 
    having 
     count(ManagerId) >= 5)Winning Candidate
```

574 Winning Candidate

```

```

user agent parser

```bash
git clone git@github.com:LeeKemp/UserAgentParser.git
```

```bash
cd UserAgentParser/
```

```bash
mvn clean package -DskipTests
```

安装到本地maven仓库

```bash
mvn clean install -DskipTests
```

```xml
<dependency>
    <groupId>com.kumkee</groupId>
    <artifactId>UserAgentParser</artifactId>
    <version>0.0.1</version>
</dependency>
```

简单测试

先提取日志文件的前100条

```bash
head -n 100 access.20161111.log > 100_access.log
```

```$xslt
localresult

Blackberry 32
Unknown 5860409
MSIE 98679
Apache HTTP Client 114
Safari 116196
Chrome 2468930
IEMobile 29
Opera 57
Firefox 364935

mapreduce result
 
Apache HTTP Client	114
Blackberry	32
Chrome	2468930
Firefox	364935
IEMobile	29
MSIE	98679
Opera	57
Safari	116196
Unknown	5860409
```
