# gseem-distributed-lock
分布式锁实现

1. redis注解方式实现分布式锁
2. redis实现限流，自定义key和ip限流

#启动访问测试  

key-String存储测试:   
http://localhost:8080/user/testGetSet

key-对象存储测试:   
http://localhost:8080/user/setUser

分布式锁测试
http://localhost:8080/cacheLock/test

自定义key限流
http://localhost:8080/cacheLimit/testCustom

IP限流
http://localhost:8080/cacheLimit/testIp
