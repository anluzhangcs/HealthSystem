package org.graduate.mail.verify;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author: Zhanghao
 * @date: 2023/5/2-20:59
 * @Description
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    /**
     * 生产的验证码位数
     */
    private final static int LENGTH = 4;

    private final String[] metaCode = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成验证码
     *
     * @return
     */
    @Override
    public String generateVerificationCode() {
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();
        while (verificationCode.length() < LENGTH) {
            int i = random.nextInt(metaCode.length);
            verificationCode.append(metaCode[i]);
        }
        return verificationCode.toString();
    }
}
