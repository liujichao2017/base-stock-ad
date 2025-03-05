package com.ruoyi.system.googleauth;

import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.ICredentialRepository;

import java.util.List;

public class GoogleAuthManager {
    private static final GoogleAuthenticator GOOGLE_AUTH = new GoogleAuthenticator();
    private static final String KEY_FORMAT = "otpauth://totp/%s?secret=%s";

    static {
        init();
    }

    public static void init() {
        GOOGLE_AUTH.setCredentialRepository(new ICredentialRepository() {
            @Override
            public String getSecretKey(String userName) {
                SysUserMapper sysUserMapper = SpringUtils.getBean(SysUserMapper.class);
                return sysUserMapper.selectGoogleAuthSecretKey(userName);
            }

            @Override
            public void saveUserCredentials(String userName, String secretKey, int validationCode,
                    List<Integer> scratchCodes) {
                SysUserMapper sysUserMapper = SpringUtils.getBean(SysUserMapper.class);
                sysUserMapper.saveGoogleAuthSecretKey(userName, secretKey);
            }
        });
    }

    public static boolean check(String userName, String code) {
        return  GOOGLE_AUTH.authorizeUser(userName,Integer.parseInt(code));
    }

    public static String qrCodeUrl(String userName, String secretKey) {
        return String.format(KEY_FORMAT, userName, secretKey);
    }

    public static String create(String userName) {
        GoogleAuthenticatorKey key = GOOGLE_AUTH.createCredentials(userName);
        return key.getKey();
    }
}
