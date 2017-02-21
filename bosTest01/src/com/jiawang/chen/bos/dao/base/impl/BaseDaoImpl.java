/**
 * 
 */
package com.jiawang.chen.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.jiawang.chen.bos.dao.base.IBaseDao;
import com.jiawang.chen.bos.web.utils.PageBean;

/**
 *<p>标题: BaseDaoImpl </p>
 *<p>描述： </p>
 *<p>company:</p>
 * @作者  陈加望
 * @时间  2017年2月15日 下午2:00:13
 *@版本 
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	
	//不能用注解
//	@Resource
//	private SessionFactory sessionFactory;
	private Class<T> entityClass;
	
	@Resource
	public void getSession(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * 反射获得类型
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType  genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass=(Class<T>)actualTypeArguments[0];
	}
	/* 
	 *注册用户
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void regist(T entity) {

		this.getHibernateTemplate().save(entity);
	}


	/* 
	 *保存一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public Serializable save(T entity) {
		if(entity!=null){
			Serializable result = this.getHibernateTemplate().save(entity);
			this.getHibernateTemplate().flush();
			return result;
		}
		return null;
	}

	/* 
	 *批量保存对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void batchSave(List<T> entitys) {

		for(int i=0;i<entitys.size();i++){
			this.getHibernateTemplate().save(entitys.get(i));
			if(i%20==0){
				//20个对象后才清理缓存，写入数据库
				this.getHibernateTemplate().flush();
				this.getHibernateTemplate().clear();
			}
		}
	}

	/* 
	 *删除一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void delete(T entity) {

		this.getHibernateTemplate().delete(entity);
	}

	/* 
	 * 删除一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void delete(Serializable id) {

		this.getHibernateTemplate().delete(this.get(id));
		this.getHibernateTemplate().flush();
	}

	/* 
	 *更新一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void update(T entity) {

		if(entity!=null){
			this.getHibernateTemplate().update(entity);
			this.getHibernateTemplate().flush();
		}
	
	}

	/* 
	 *保存或更新一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public void saveUpdate(T entity) {
		if(entity!=null){
			this.getHibernateTemplate().saveOrUpdate(entity);
			this.getHibernateTemplate().flush();
		}
	}

	/**
	 * 通用更新方法
	 */
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSession();// 从本地线程中获得session对象
		
		// 使用命名查询语句获得一个查询对象
		Query query = session.getNamedQuery(queryName);
		// 为HQL语句中的？赋值
		int i = 0;
		for (Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();// 执行更新
	}
	
	/* 
	 *通过主键获得对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public T get(Class<T> c, Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/* 
	 *通过主键获得对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public T get(Serializable id) {
		
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/* 
	 *通过HQL语句获取一个对象
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql) {
		List<T> list = this.getHibernateTemplate().find(hql);
		return list.get(0);
	}

	/* 
	 *通过HQL语句获取一个对象
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public T get(String hql, Object...params) {
		Query query = this.getSession().createQuery(hql);
		if(params!=null && !params.equals("")){
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params);
			}
		}
		List<T> list = query.list();
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		
		return null;
	}

	/* 
	 *获得对象列表
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find( ) {
		String hql="from  "+entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	/* 
	 *获得对象列表
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Object... params) {
	
		 return this.getHibernateTemplate().find(hql, params);
	}

	/* 
	 *获得分页后的对象列表
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public List findBySql(Class transFormClass, String sql, Object... params) {
		return null;
	}

	/* 
	 *获得分页后的对象列表
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, int page, int rows) {
		Query query = this.getSession().createQuery(hql);
		return query.setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

	/* 
	 *统计数目
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public List<T> find(String hql,  int page, int rows,Object... params) {
		
		return null;
	}

	/* 
	 *统计数目
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public long count(String hql) {

		return (Long)this.getSession().createQuery(hql).uniqueResult();
	}

	/* 
	 *统计数目
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public int getcount(String hql) {
		
		return (Integer)this.getSession().createQuery(hql).uniqueResult();
	}

	/* 
	 *统计数目
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public long count(String hql, Object... params) {

		Query query = this.getSession().createQuery(hql);
		if(params!=null && ! params.equals("")){
			int i=0;
			for(Object arg:params){
				query.setParameter(i++, arg);
			}
			
		}
		return (Long)query.uniqueResult();
	}

	/* 
	 *执行一条HQL语句
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public int executeHql(String hql) {

		Query query = this.getSession().createQuery(hql);
		return query.executeUpdate();
	}

	/* 
	 *执行一条HQL语句
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public int executeHql(String hql, Object... params) {

		Query query = this.getSession().createQuery(hql);
		if(params!=null && ! params.equals("")){
			int i=0;
			for(Object arg:params){
				query.setParameter(i++, arg);
			}
		}
		return query.executeUpdate();
	}

	/* 
	 *获得结果集
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public List<Object[]> findBySql(String sql) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		return query.list();
	}

	/* 
	 *获得结果集
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBySql(String sql, int page, int rows) {
		Query query = this.getSession().createQuery(sql);
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/* 
	 *获得结果集
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBySql(String sql, Object... params) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if(params!=null &&! params.equals("")){
			int i=0;
			for(Object arg:params){
				query.setParameter(i++, arg);
			}
		}
		return query.list();
	}

	/* 
	 *获得结果集
	 *2017年2月15日下午2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBySql(String sql,int page, int rows,Object... params) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		if(params!=null && !params.equals("")){
			int i=0;
			for(Object arg:params){
				query.setParameter(i++, arg);
			}
		}
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/* 
	 *
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public List<T> sqlFind(String hql, int page, int rows, Object... params) {

		return null;
	}

	/* 
	 *执行SQL语句
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public long countBySql(String sql) {
		// TODO 自动生成的方法存根
		return 0;
	}

	/* 
	 *
	 *2017年2月15日下午2:00:38
	 */
	@Override
	public long countBySql(String sql, Object... params) {
		// TODO 自动生成的方法存根
		return 0;
	}

	/* 
	 *
	 *2017年2月15日下午7:11:46
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();//获取当前页
		int pageSize = pageBean.getPageSize();//获取每页显示的数量
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//总数据量----select count(*) from bc_staff
		//改变Hibernate框架发出的sql形式
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = list.get(0);
		pageBean.setTotal(total.intValue());//设置总数量
		detachedCriteria.setProjection(null);//修改sql的形式为select * from ....
		
		//重置表和类的映射关系
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		//当前页展示的数据集合
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
		
	}
	
	/* 
	 *通用  有条件的查询
	 *2017年1月10日下午1:28:41
	 */
	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {

		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
