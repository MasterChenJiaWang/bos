/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import org.w3c.dom.ls.LSInput;

import com.jiawang.chen.bos.entity.Function;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IFunctionService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午10:47:10
 *@版本 
 */
public interface IFunctionService {

	public void pageQuery(PageBean pageBean);
	
	public List<Function> findAll();
	
	public  void add(Function model);

	/**
	 * 
	 *@时间 2017年2月21日 上午9:22:44
	 */
	public List<Function> findMenu();
}
