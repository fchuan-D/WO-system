package com.spcloud.app.user.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wosys.base.utils.Base64Utils;
import com.wosys.base.utils.JwtTokenUtils;
import com.wosys.base.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码控制器
 */
@RestController
@RequestMapping("/user")
public class CaptchaController {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private DefaultKaptcha kaptcha;

    @GetMapping("/captch")
    public R createCaptch() {
        String code = createRandomString(4);
        BufferedImage image = kaptcha.createImage(code);
        String base64 = createBase64Png(image);

        Map result = new HashMap();
        result.put("token", jwtTokenUtils.generateToken(code, new HashMap()));
        result.put("url", base64);

        return R.success(result);
    }

    /**
     * 生成验证码字符串
     *
     * @param len
     * @return
     */
    private String createRandomString(int len) {
        // 生成随机数类
        Random random = new Random();
        String result = "";
        for (int i = 0; i < len; i++) {
            String rand = String.valueOf(random.nextInt(10));
            result += rand;
        }
        return result;
    }

    private String createBase64Png(BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(); //io流
            ImageIO.write(bufferedImage, "png", baos); //写入流中
            byte[] bytes = baos.toByteArray(); //转换成字节
            //BASE64Encoder encoder = new BASE64Encoder();
            String png_base64 = Base64Utils.encode(bytes); //encoder.encodeBuffer(bytes).trim(); //转换成base64串
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", ""); //删除 \r\n

            //        ImageIO.write(bufferedImage, "png", new File("D:/qrcode1.png"));
            //System.out.println("值为："+"data:image/jpg;base64,"+png_base64);
            return "data:image/png;base64," + png_base64;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
