package org.graduate.utils;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

/**
 * @author: Zhanghao
 * @date: 2023/4/26-9:43
 * @Description 自定义ID生成器
 */

public class CustomIdGenerator implements IdentifierGenerator {


    @Override
    public Number nextId(Object entity) {
        return null;
    }

    /**
     * 默认使用雪花算法+nextUUID
     *
     * @param entity
     * @return
     */
    @Override
    public String nextUUID(Object entity) {
        return IdentifierGenerator.super.nextUUID(entity);
    }
}
