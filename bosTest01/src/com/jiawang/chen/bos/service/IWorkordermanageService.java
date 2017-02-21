/**
 * 
 */
package com.jiawang.chen.bos.service;

import java.util.List;

import com.jiawang.chen.bos.entity.Workordermanage;

/**
 *<p>标题: IWorkordermanageService </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月21日 下午12:06:52
 *@版本 
 */
public interface IWorkordermanageService {

	/**
	 * 
	 *@时间 2017年2月21日 下午12:08:07
	 */
	public List<Workordermanage> findListNotStart();

	/**
	 * 
	 *@时间 2017年2月21日 下午1:46:40
	 */
	public void start(String id);

	/**
	 * 
	 *@时间 2017年2月21日 下午1:56:12
	 */
	public void save(Workordermanage model);

	/**
	 * 
	 *@时间 2017年2月21日 下午2:21:35
	 */
	public Workordermanage findById(String workordermanageId);

	/**
	 * 
	 *@时间 2017年2月21日 下午2:23:52
	 */
	public void checkWorkordermanage(String taskId, Integer check, String workordermanageId);

}
