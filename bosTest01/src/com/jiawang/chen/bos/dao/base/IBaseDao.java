/**
 * 
 */
package com.jiawang.chen.bos.dao.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.catalina.User;
import org.hibernate.criterion.DetachedCriteria;

import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: IBaseDao </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午1:47:36
 *@版本 
 */
public interface IBaseDao<T> {

	public void regist(T entity);
	public Serializable save(T entity);
	
	public void batchSave(List<T> entitys);
	public void delete(T entity);
	public void delete(Serializable id);
	public void update(T entity);
	public void executeUpdate(String queryName, Object... objects);
	public void saveUpdate(T entity);
	
	public T get(Class<T> c,Serializable id);
	
	public T get(Serializable id);
	
	public T get(String hql);
	
	public T get(String hql,Object...params);
	
	public List<T> find();
	
	
	public List<T> find(String hql, Object...params);
	
	@SuppressWarnings("rawtypes")
	public List findBySql(Class transFormClass, String sql, Object...params);
	
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);
	//分页
	public List<T> find(String hql, int page, int rows);
	
	public List<T> find(String hql,  int page, int rows,Object...params);
	
	public void  pageQuery(PageBean pageBean);
	
	public long count(String hql);
	
	public int getcount(String hql);
	
	public long count(String hql, Object...params);
	
	
	public int executeHql(String hql);//执行一条HQL语句
	public int executeHql(String hql, Object...params);
	
	//获得结果集
	public List<Object[]> findBySql(String sql);
	public List<Object[]> findBySql(String sql, int page, int rows);
	public List<Object[]> findBySql(String sql, Object...params);
	public List<Object[]> findBySql(String sql,int page, int rows,Object...params);
	public List<T> sqlFind(String hql, int page, int rows, Object...params);
	
	
	//统计
	public long countBySql(String sql);
	public long countBySql(String sql, Object...params);
	
}
