package cn.danyhug.spring_weixin_login.controller;

import cn.danyhug.spring_weixin_login.utils.HttpClientUtils;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class IndexController {

    @Value("${oauth.wx.appid}")
    private String appid;

    @Value("${oauth.wx.appsecret}")
    private String secret;

    @Value("${oauth.wx.callback.http}")
    private String redirectUri;

    @GetMapping("/")
    public String index() {
        return "Hello, Wechat Login Demo";
    }

    @GetMapping("/wxLogin")
    public void wxLogin(HttpServletResponse httpServletResponse) throws IOException {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + this.appid +
                "&redirect_uri=" + this.redirectUri +
                "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

        // 重定向
        System.out.println(url);
        httpServletResponse.sendRedirect(url);
    }

    @GetMapping("/wxCallBack")
    public String wxCallBack(String code) throws IOException, InterruptedException {
        // 携带code获取网页授权
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + this.appid +
                "&secret=" + this.secret +
                "&code=" + code +
                "&grant_type=authorization_code";
        JSONObject jsonObject = HttpClientUtils.doGet(url);
        System.out.println(jsonObject);

        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");

        // 获取用户信息
        url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken +
                "&openid=" + openid +
                "&lang=zh_CN";
        JSONObject userInfo = HttpClientUtils.doGet(url);
        System.out.println(userInfo);
        /**
         * {
         *   "openid": "**********",
         *   "nickname": "蒲公英",
         *   "sex": 0,
         *   "language": "",
         *   "city": "",
         *   "province": "",
         *   "country": "",
         *   "headimgurl": "https://thirdwx.ql
         *   "privilege": []
         * }
         */
        return "wxCallBack";
    }
}
