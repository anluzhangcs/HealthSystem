package org.graduate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.graduate.domain.entity.Demand;
import org.graduate.mapper.DemandMapper;
import org.graduate.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Zhanghao
 * @date: 2023/4/8-22:43
 * @Description
 */

@SpringBootTest
public class ApplicationTest {


    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private DemandMapper demandMapper;


    @Test
    public void testDemanDelete() {
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Demand::getId, "1651092298956288001");
        demandMapper.delete(wrapper);
    }

    @Test
    public void testUUID() {
        Demand demand = new Demand();
        demandMapper.insert(demand);
    }

    @Test
    public void testPasswordEncoder() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = "{bcrypt}$2a$10$6S2.zkjO8Oqa9UmV8tQjs.vzCmxiAnnIff6sQKwVrFBRtUS0yLOhi";
        System.out.println(encode.indexOf("}"));
        boolean matches = passwordEncoder.matches("123456", encode.substring(encode.indexOf("}") + 1));
        System.out.println(matches);
    }

    /**
     *
     */
    @Test
    public void testREsult() {
        System.out.println();
    }

    @Test
    public void testFastJson() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "success");
        result.put("data", 1);
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testDb() {
    }

}
