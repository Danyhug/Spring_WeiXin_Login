# 基于SpringBoot3的微信公众号登录实现

## 准备工作
- 安装环境
- 官方文档：https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html
- 内网穿透 `推荐ngrok`

## 步骤
1. 登录公众号
2. 公众号后台 - 开发者工具 - 公众平台测试信息
3. 将appid和secret复制到 `application.yml`中
4. 点击网页账号 - 修改 - 改为内网穿透的地址`写域名，不要加http` https://mp.weixin.qq.com/debug/cgi-bin/sandboxinfo?action=showinfo&t=sandbox/index
5. 更改 `application.yml`中的`callback`为内网穿透的地址
6. 运行项目
7. 获取到数据
```json
{
  "openid": "**********",
  "nickname": "蒲公英",
  "sex": 0,
  "language": "",
  "city": "",
  "province": "",
  "country": "",
  "headimgurl": "https://thirdwx.qlogo.cn/mmopk/132",
  "privilege": []
}

```
8. 此时openid已经获取到了，可以进行业务处理了

## 备注
- 获取到openid之后，可以进行业务处理了，比如将openid保存到数据库中，或者将openid返回给前端，让前端进行业务处理
- 不知道为什么获取不到城市，访问的接口应该是正确的，可能是微信更改了API权限，但是影响不大
