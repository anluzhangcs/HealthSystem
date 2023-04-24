package org.graduate.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author: Zhanghao
 * @date: 2023/4/11-21:01
 * @Description 渲染返回给前端的信息
 */

public class WebRenderUtil {

    public static void render(HttpServletResponse response, ResponseResult result) throws IOException {

        response.setStatus(result.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        //获取字符输出流
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(result));
        writer.flush();
        writer.close();
    }

}
