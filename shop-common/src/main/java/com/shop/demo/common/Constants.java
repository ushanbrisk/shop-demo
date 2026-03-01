package com.shop.demo.common;

/**
 * 常用常量类
 */
public class Constants {

    /**
     * JWT密钥
     */
    public static final String JWT_SECRET = "shop-demo-secret-key-2024";

    /**
     * JWT过期时间（毫秒）- 7天
     */
    public static final long JWT_EXPIRATION = 7 * 24 * 60 * 60 * 1000L;

    /**
     * Token请求头
     */
    public static final String TOKEN_HEADER = "Authorization";

    /**
     * Token前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    private Constants() {
    }
}
