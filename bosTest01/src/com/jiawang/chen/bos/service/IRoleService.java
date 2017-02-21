/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.Role;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IRoleService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午11:35:31
 *@版本 
 */
public interface IRoleService {

	public void add(Role model,String ids);

	/**
	 * 
	 *@时间 2017年2月20日 下午11:47:32
	 */
	public List<Role> findAll();

	/**
	 * 
	 *@时间 2017年2月20日 下午11:48:09
	 */
	public void pageQuery(PageBean pageBean);
}
