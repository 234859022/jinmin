package com.nanwang.project.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 创建uuid
     * @return
     */
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static void main(String[] args) {
        String uuid = createUUID();
        System.out.println(uuid);
    }
}
