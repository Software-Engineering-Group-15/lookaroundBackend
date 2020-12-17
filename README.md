# lookaroundBackend
back-end program of Lookaround Application , based on Java and Spring boot.

## 基于JWT的登录和验证
---
### 登录接口：Post /login
需要传入一个JSON文件，目前只能用以下设置登录
```JSON
{
    "username":"ADMIN",
    "password":"password"
}
```
后端传回的response.header中"Authorization"项包含一个JWT字符串

----
### 测试用验证接口GET /hello
RESTful接口，返回"hello，world"字符串
受保护，访问需要身份验证

验证方法：在Request.header中加入JWT
```JSON
{
    ...
    "Authorization": "Bearer JWT" 
    ...
}
```
