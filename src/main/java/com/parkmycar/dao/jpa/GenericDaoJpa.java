package com.parkmycar.dao.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

public class GenericDaoJpa<T, PK extends Serializable> implements
		com.parkmycar.dao.GenericDao<T, PK> {

	protected final Logger log = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "entityManagerFactory")
	protected EntityManager entityManager;
        
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public GenericDaoJpa() {

		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public GenericDaoJpa(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public List<T> getAll() {

		CriteriaBuilder queryBuilder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> qdef = queryBuilder.createQuery(this.persistentClass);
		Root<T> from = qdef.from(persistentClass);
		qdef.orderBy(queryBuilder.desc(from.get("id")));
		return this.entityManager.createQuery(qdef)
				.setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();

	}

	public T get(PK id) {
		T entity = this.entityManager.find(this.persistentClass, id);		
		return entity;
	}

	public boolean exists(PK id) {
		T entity = this.entityManager.find(this.persistentClass, id);

		if (entity == null) {
			return false;
		} else {
			return true;
		}
	}

	public T save(T object) {
		this.entityManager.persist(object);
		return object;
	}

	public T update(T object) {
		T newObj = this.entityManager.merge(object);
		return newObj;
	}

	public void remove(PK id) {
		this.entityManager.remove(this.get(id));
	}

	public void flush() {
		this.entityManager.flush();
	}

	public <X, Z> Join<X, Z> doJoin(Root<X> from, String relationName) {
		Join<X, Z> retJoin = null;
		if (null != relationName) {
			String[] relationNames = relationName.split(":");
			boolean firstFlag = true;
			for (String s : relationNames) {
				if (firstFlag) {
					retJoin = from.join(s);
					firstFlag = false;
				} else {
					retJoin = retJoin.join(s);
				}
			}
		}
		return retJoin;
	}

}
