/**
 * 
 */
package com.jiawang.chen.bos.service;

import com.jiawang.chen.bos.entity.Decidedzone;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IDecidedzoneService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月17日 下午4:32:15
 *@版本 
 */
public interface IDecidedzoneService {

	public void add(Decidedzone decidedzone,String[] subareaid);
	public void delete(Decidedzone decidedzone);
	public void edit(Decidedzone decidedzone);
	
	public void pageQuery(PageBean pageBean);
	
}
