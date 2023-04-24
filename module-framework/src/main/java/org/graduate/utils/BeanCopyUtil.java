package org.graduate.utils;

import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-21:50
 * @Description
 */

public class BeanCopyUtil {

    public static <T> T copyBean(Object source, Class<T> clazz) {
        T result = null;
        try {
            result = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(source, result);
        return result;
    }

    public static <O, T> Collection<T> copyBeanList(Collection<O> source, Class<T> clazz) throws Exception {
        return source.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
