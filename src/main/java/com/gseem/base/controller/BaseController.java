package com.gseem.base.controller;

import com.gseem.base.utils.StateParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import java.util.UUID;

/**
 * Base控制器
 * @author feiyue
 * @date 2019/11/9
 */
public abstract class BaseController{

	protected final String success = StateParameter.SUCCESS;

	protected final String  fail = StateParameter.FAULT;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public ModelMap getModelMap(String status,Object data,String msg){
		ModelMap modelMap=new ModelMap();
		modelMap.put("status", status);
		modelMap.put("data", data);
		modelMap.put("msg", msg);
		return modelMap;
		
	}

	/**
	 * 获取UUID并转化为String对象
	 * @return
	 */
	public String getUuid(){
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.replace("-", "");
		return uuid;
	}

}
