# HOS Service

A file system develop with hbase and spring boot.

```sql
CREATE DATABASE IF NOT EXISTS HOS_SERVICE DEFAULT CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;

USE HOS_SERVICE;

CREATE TABLE USER_INFO(
	USER_ID VARCHAR(32) NOT NULL,
    USER_NAME VARCHAR(32) NOT NULL,
    PASSWORD VARCHAR(64) NOT NULL COMMENT 'BY MD5',
    DETAIL VARCHAR(256),
    CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(USER_ID),
    UNIQUE KEY AK_UQ_USER_NAME(USER_NAME)
)
	ENGINE=InnoDB
	DEFAULT CHARSET = utf8
	COMMENT = '用户信息表';


CREATE TABLE TOKEN_INFO(
	TOKEN VARCHAR(32) NOT NULL,
    EXPIRE_TIME VARCHAR(32) NOT NULL,
    CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    REFRESH_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ACTIVE TINYINT NOT NULL,
    CREATOR VARCHAR(32) NOT NULL,
    PRIMARY KEY(TOKEN)
)
	ENGINE=InnoDB
	DEFAULT CHARSET = utf8
	COMMENT = 'TOKEN信息表';


CREATE TABLE SERVICE_AUTH(
	BUCKET_NAME VARCHAR(32) NOT NULL,
    TARGET_TOKEN VARCHAR(32) NOT NULL,
    AUTH_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(BUCKET_NAME, TARGET_TOKEN)
)
	ENGINE=InnoDB
	DEFAULT CHARSET = utf8
	COMMENT = '权限信息表';


CREATE TABLE HOS_BUCKET(
	BUCKET_ID VARCHAR(32) NOT NULL,
    BUCKET_NAME VARCHAR(32) NOT NULL,
    CREATOR VARCHAR(32) NOT NULL,
    DETAIL VARCHAR(256),
    CREATE_TIME TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (BUCKET_ID),
    UNIQUE KEY AK_UQ_USER_NAME(BUCKET_NAME)
)
	ENGINE=InnoDB
	DEFAULT CHARSET = utf8
	COMMENT = 'BUCKET信息表';
```

接下来是HBase部分的设计



## Redis

```shell
127.0.0.1:6379> subscribe myTopic

127.0.0.1:6379> subscribe myTopic


127.0.0.1:6379> subscribe anotherTopic

127.0.0.1:6379> publish myTopic "Hello"
(integer) 2
127.0.0.1:6379> publish myTopic "world"
(integer) 2
127.0.0.1:6379> publish anotherTopic "For U"
(integer) 1
127.0.0.1:6379> 
```

```shell
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "myTopic"
3) (integer) 1
1) "message"
2) "myTopic"
3) "Hello"
1) "message"
2) "myTopic"
3) "world"


127.0.0.1:6379> subscribe myTopic
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "myTopic"
3) (integer) 1
1) "message"
2) "myTopic"
3) "Hello"
1) "message"
2) "myTopic"
3) "world"


127.0.0.1:6379> subscribe anotherTopic
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "anotherTopic"
3) (integer) 1
1) "message"
2) "anotherTopic"
3) "For U"
```

Redis持久化

```shell
save 
lastsave
bgsave
```

bgsave原理









