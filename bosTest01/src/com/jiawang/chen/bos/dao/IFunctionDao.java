/**
 * 
 */
package com.jiawang.chen.bos.dao;

import java.util.List;

import com.jiawang.chen.bos.dao.base.IBaseDao;
import com.jiawang.chen.bos.entity.Function;

/**
 *<p>标题: IFunctionDao </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月20日 下午10:48:38
 *@版本 
 */
public interface IFunctionDao extends IBaseDao<Function> {

	/**
	 * 
	 *@时间 2017年2月21日 上午8:35:06
	 */
	public List<Function> findListByUserid(String userid);

	/**
	 * 
	 *@时间 2017年2月21日 上午9:26:59
	 */
	public List<Function> findAllMenu();

	/**
	 * 
	 *@时间 2017年2月21日 上午9:27:11
	 */
	public List<Function> findMenuByUserid(String id);

}
