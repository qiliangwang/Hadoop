## Hadoop

web客户端

http://localhost:50070/explorer.html#/

### HDFS  shell基本操作

1、 上传文件到hdfs中

```bash
hadoop fs -put /本地文件  /aaa

hadoop fs -put ./hadoop-2.7.3.tar.gz /
```

将本地的hadoop-2.7.3.tar.gz存放到hdfs的/目录下



2、  下载文件到客户端本地磁盘

```bash
hadoop fs -get /hdfs中的路径   /本地磁盘目录
```



3、  在hdfs中创建文件夹

```bash
hadoop fs -mkdir  -p /aaa/xxx
```



 4、 移动hdfs中的文件（更名）

```bash
hadoop fs -mv /hdfs的路径1  /hdfs的另一个路径2
hadoop fs -mv /hadoop-2.7.3.tar.gz /software/rename.gz
```

​	复制hdfs中的文件到hdfs的另一个目录

```bash
hadoop fs -cp /hdfs路径_1  /hdfs路径_2
hadoop fs -mv /hadoop-2.7.3.tar.gz /software/
```



  5、 删除hdfs中的文件或文件夹

```bash
hadoop fs -rm -r /aaa
```



 6、 查看hdfs中的文本文件内容

```
hadoop fs -cat /demo.txt
hadoop fs -tail -f /demo.txt
```

### 使用Java api操作HDFS

