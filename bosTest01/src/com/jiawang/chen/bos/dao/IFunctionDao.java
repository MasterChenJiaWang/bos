/**
 * 
 */
package com.jiawang.chen.bos.dao;

import java.util.List;

import com.jiawang.chen.bos.dao.base.IBaseDao;
import com.jiawang.chen.bos.entity.Function;

/**
 *<p>����: IFunctionDao </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��20�� ����10:48:38
 *@�汾 
 */
public interface IFunctionDao extends IBaseDao<Function> {

	/**
	 * 
	 *@ʱ�� 2017��2��21�� ����8:35:06
	 */
	public List<Function> findListByUserid(String userid);

	/**
	 * 
	 *@ʱ�� 2017��2��21�� ����9:26:59
	 */
	public List<Function> findAllMenu();

	/**
	 * 
	 *@ʱ�� 2017��2��21�� ����9:27:11
	 */
	public List<Function> findMenuByUserid(String id);

}
