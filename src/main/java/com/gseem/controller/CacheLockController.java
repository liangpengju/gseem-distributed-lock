package com.gseem.controller;

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
 * 分布式锁
 * @author feiyue
 * @date 2019/11/9
 */
@Controller
@RequestMapping("/cacheLock")
public class CacheLockController extends BaseController{


    /**
     * 分布式锁
     * @param token
     * @return
     */
    @CacheLock(prefix = "cacheTest",expire = 10)
    @GetMapping("/test")
    public  @ResponseBody String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }


}
