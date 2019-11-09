package com.gseem.controller;

import com.alibaba.fastjson.JSON;
import com.gseem.base.cache.annotation.CacheLock;
import com.gseem.base.cache.annotation.CacheParam;
import com.gseem.base.controller.BaseController;
import com.gseem.base.utils.RedisConstants;
import com.gseem.base.utils.RedisUtil;
import com.gseem.base.utils.StateParameter;
import com.gseem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author feiyue
 * @date 2019/11/9
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private RedisUtil redisUtil;

   /**
    * redis存储、读取测试
    * @author feiyue
    * @date 2019/11/9 20:18
    * @param
    * @return
    **/
    @RequestMapping(value="/testGetSet")
    @ResponseBody
    public ModelMap testGetSet(){
        try {
            String key = "feiyue";
            redisUtil.set(key,"飞月", RedisConstants.datebase1);
            String value = redisUtil.get(key,RedisConstants.datebase1).toString();
            logger.info("读取redis成功,value="+value);
            return getModelMap(StateParameter.SUCCESS, value, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

    /**
     * 对象set测试
     * @author feiyue
     * @date 2019/11/9 20:21
     * @param
     * @return
     **/
    @RequestMapping(value="/setUser")
    @ResponseBody
    public ModelMap setUser(){
        try {
            User user = new User();
            user.setName("feiyue");
            user.setAge(20);
            user.setId(getUuid());
            redisUtil.set("user",user, RedisConstants.datebase1);
            User res = (User)redisUtil.get("user",RedisConstants.datebase1);
            logger.info("读取redis成功，user="+ JSON.toJSONString(res));
            return getModelMap(StateParameter.SUCCESS, res, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return getModelMap(StateParameter.FAULT, null, "操作失败");
        }
    }

}
