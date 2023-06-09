package org.graduate;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.graduate.domain.entity.Demand;
import org.graduate.domain.entity.Doctor;
import org.graduate.mail.MailService;
import org.graduate.mapper.DemandMapper;
import org.graduate.mapper.DoctorMapper;
import org.graduate.mapper.ElderMapper;
import org.graduate.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.time.LocalDate;
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

    @Autowired
    private MailService mailService;

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Test
    public void testInsertMp() {
        Doctor doctor = new Doctor();
        doctor.setAge(56);
        doctor.setEnterDate(LocalDate.now());
        doctor.setGender("男");
        doctor.setName("ad");
        doctor.setPhoneNumber("12345");
        doctorMapper.insert(doctor);
        System.out.println(doctor.getId());
        
    }

    @Test
    public void testMail() throws MessagingException {
        mailService.sendHtmlMail("anlu.zhangcs@outlook.com", "欢迎来到 幸福敬老院", "218126");
    }

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
