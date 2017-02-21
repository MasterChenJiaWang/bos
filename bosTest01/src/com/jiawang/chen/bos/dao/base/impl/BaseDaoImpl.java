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
 *<p>����: BaseDaoImpl </p>
 *<p>������ </p>
 *<p>company:</p>
 * @����  �¼���
 * @ʱ��  2017��2��15�� ����2:00:13
 *@�汾 
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);
	
	//������ע��
//	@Resource
//	private SessionFactory sessionFactory;
	private Class<T> entityClass;
	
	@Resource
	public void getSession(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	/**
	 * ����������
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType  genericSuperclass = (ParameterizedType)this.getClass().getGenericSuperclass();
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		entityClass=(Class<T>)actualTypeArguments[0];
	}
	/* 
	 *ע���û�
	 *2017��2��15������2:00:38
	 */
	@Override
	public void regist(T entity) {

		this.getHibernateTemplate().save(entity);
	}


	/* 
	 *����һ������
	 *2017��2��15������2:00:38
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
	 *�����������
	 *2017��2��15������2:00:38
	 */
	@Override
	public void batchSave(List<T> entitys) {

		for(int i=0;i<entitys.size();i++){
			this.getHibernateTemplate().save(entitys.get(i));
			if(i%20==0){
				//20�������������棬д�����ݿ�
				this.getHibernateTemplate().flush();
				this.getHibernateTemplate().clear();
			}
		}
	}

	/* 
	 *ɾ��һ������
	 *2017��2��15������2:00:38
	 */
	@Override
	public void delete(T entity) {

		this.getHibernateTemplate().delete(entity);
	}

	/* 
	 * ɾ��һ������
	 *2017��2��15������2:00:38
	 */
	@Override
	public void delete(Serializable id) {

		this.getHibernateTemplate().delete(this.get(id));
		this.getHibernateTemplate().flush();
	}

	/* 
	 *����һ������
	 *2017��2��15������2:00:38
	 */
	@Override
	public void update(T entity) {

		if(entity!=null){
			this.getHibernateTemplate().update(entity);
			this.getHibernateTemplate().flush();
		}
	
	}

	/* 
	 *��������һ������
	 *2017��2��15������2:00:38
	 */
	@Override
	public void saveUpdate(T entity) {
		if(entity!=null){
			this.getHibernateTemplate().saveOrUpdate(entity);
			this.getHibernateTemplate().flush();
		}
	}

	/**
	 * ͨ�ø��·���
	 */
	public void executeUpdate(String queryName, Object... objects) {
		Session session = this.getSession();// �ӱ����߳��л��session����
		
		// ʹ��������ѯ�����һ����ѯ����
		Query query = session.getNamedQuery(queryName);
		// ΪHQL����еģ���ֵ
		int i = 0;
		for (Object arg : objects) {
			query.setParameter(i++, arg);
		}
		query.executeUpdate();// ִ�и���
	}
	
	/* 
	 *ͨ��������ö���
	 *2017��2��15������2:00:38
	 */
	@Override
	public T get(Class<T> c, Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/* 
	 *ͨ��������ö���
	 *2017��2��15������2:00:38
	 */
	@Override
	public T get(Serializable id) {
		
		return this.getHibernateTemplate().get(entityClass, id);
	}

	/* 
	 *ͨ��HQL����ȡһ������
	 *2017��2��15������2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String hql) {
		List<T> list = this.getHibernateTemplate().find(hql);
		return list.get(0);
	}

	/* 
	 *ͨ��HQL����ȡһ������
	 *2017��2��15������2:00:38
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
	 *��ö����б�
	 *2017��2��15������2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find( ) {
		String hql="from  "+entityClass.getSimpleName();
		return this.getHibernateTemplate().find(hql);
	}

	/* 
	 *��ö����б�
	 *2017��2��15������2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, Object... params) {
	
		 return this.getHibernateTemplate().find(hql, params);
	}

	/* 
	 *��÷�ҳ��Ķ����б�
	 *2017��2��15������2:00:38
	 */
	@Override
	public List findBySql(Class transFormClass, String sql, Object... params) {
		return null;
	}

	/* 
	 *��÷�ҳ��Ķ����б�
	 *2017��2��15������2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String hql, int page, int rows) {
		Query query = this.getSession().createQuery(hql);
		return query.setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}

	/* 
	 *ͳ����Ŀ
	 *2017��2��15������2:00:38
	 */
	@Override
	public List<T> find(String hql,  int page, int rows,Object... params) {
		
		return null;
	}

	/* 
	 *ͳ����Ŀ
	 *2017��2��15������2:00:38
	 */
	@Override
	public long count(String hql) {

		return (Long)this.getSession().createQuery(hql).uniqueResult();
	}

	/* 
	 *ͳ����Ŀ
	 *2017��2��15������2:00:38
	 */
	@Override
	public int getcount(String hql) {
		
		return (Integer)this.getSession().createQuery(hql).uniqueResult();
	}

	/* 
	 *ͳ����Ŀ
	 *2017��2��15������2:00:38
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
	 *ִ��һ��HQL���
	 *2017��2��15������2:00:38
	 */
	@Override
	public int executeHql(String hql) {

		Query query = this.getSession().createQuery(hql);
		return query.executeUpdate();
	}

	/* 
	 *ִ��һ��HQL���
	 *2017��2��15������2:00:38
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
	 *��ý����
	 *2017��2��15������2:00:38
	 */
	@Override
	public List<Object[]> findBySql(String sql) {
		SQLQuery query = this.getSession().createSQLQuery(sql);
		return query.list();
	}

	/* 
	 *��ý����
	 *2017��2��15������2:00:38
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBySql(String sql, int page, int rows) {
		Query query = this.getSession().createQuery(sql);
		return query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	/* 
	 *��ý����
	 *2017��2��15������2:00:38
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
	 *��ý����
	 *2017��2��15������2:00:38
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
	 *2017��2��15������2:00:38
	 */
	@Override
	public List<T> sqlFind(String hql, int page, int rows, Object... params) {

		return null;
	}

	/* 
	 *ִ��SQL���
	 *2017��2��15������2:00:38
	 */
	@Override
	public long countBySql(String sql) {
		// TODO �Զ����ɵķ������
		return 0;
	}

	/* 
	 *
	 *2017��2��15������2:00:38
	 */
	@Override
	public long countBySql(String sql, Object... params) {
		// TODO �Զ����ɵķ������
		return 0;
	}

	/* 
	 *
	 *2017��2��15������7:11:46
	 */
	@Override
	public void pageQuery(PageBean pageBean) {
		int currentPage = pageBean.getCurrentPage();//��ȡ��ǰҳ
		int pageSize = pageBean.getPageSize();//��ȡÿҳ��ʾ������
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		
		//��������----select count(*) from bc_staff
		//�ı�Hibernate��ܷ�����sql��ʽ
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> list = this.getHibernateTemplate().findByCriteria(detachedCriteria);
		Long total = list.get(0);
		pageBean.setTotal(total.intValue());//����������
		detachedCriteria.setProjection(null);//�޸�sql����ʽΪselect * from ....
		
		//���ñ�����ӳ���ϵ
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		//��ǰҳչʾ�����ݼ���
		int firstResult = (currentPage - 1) * pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
		
	}
	
	/* 
	 *ͨ��  �������Ĳ�ѯ
	 *2017��1��10������1:28:41
	 */
	@Override
	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {

		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
	}
}
