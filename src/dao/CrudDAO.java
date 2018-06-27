package dao;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

public class CrudDAO<ID extends Serializable, EntityClass> {

	private Class<EntityClass> entityClass;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public CrudDAO() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = null;

		while (parameterizedType == null) {
			if (type instanceof ParameterizedType) {
				parameterizedType = (ParameterizedType) type;
			} else
				type = ((Class<?>) type).getGenericSuperclass();
		}

		entityClass = (Class<EntityClass>) parameterizedType.getActualTypeArguments()[1];
	}

	public EntityClass findOne(ID id) {
		return entityManager.find(entityClass, id);
	}

	public List<EntityClass> findByQuery(EntityClass query) {
		return entityManager
				.createQuery("SELECT tmp from " + entityClass.getName() + " tmp" + perpareQuery(query), entityClass)
				.getResultList();
	}

	private String perpareQuery(EntityClass query) {
		StringBuffer queryBuffer = new StringBuffer();
		try {
			queryBuffer.append(" WHERE 1=1");

			for (PropertyDescriptor pdec : Introspector.getBeanInfo(query.getClass()).getPropertyDescriptors()) {
				if (pdec.getReadMethod() != null) {
					Object value = pdec.getReadMethod().invoke(query);
					if(value instanceof Class<?>)
						continue;
					
					if (value == null)
						continue;
					if (value instanceof Collection)
						continue;
					if (value instanceof Domain)
						value = ((Domain) value).getId();

					boolean isString = value instanceof String;
					if(isString && value.equals(""))
						continue;

					queryBuffer.append(" AND ");
					queryBuffer.append("tmp.");
					queryBuffer.append(pdec.getName());
					queryBuffer.append("=");
					if (isString)
						queryBuffer.append("'");
					queryBuffer.append(value.toString());
					if (isString)
						queryBuffer.append("'");
				
				}
			}
	
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println("LOG INFO: " + queryBuffer.toString());
		return queryBuffer.toString();
	}

	public EntityClass save(EntityClass entity) {
		return entityManager.merge(entity);
	}

	public void remove(ID id) {
		EntityClass entity = findOne(id);
		entityManager.remove(entity);
	}
	
	public void remove(EntityClass entity) {
		entityManager.remove(entity);
	}

	private TypedQuery<EntityClass> prepareQueryForAll() {
		return entityManager.createQuery("SELECT tmp from " + entityClass.getName() + " tmp", entityClass);
	}

	public List<EntityClass> findAll() {
		return prepareQueryForAll().getResultList();
	}

	/*public List<EntityClass> findAll(Page page) {
		return prepareQueryForAll().setFirstResult(page.getFirst()).setMaxResults(page.getMax()).getResultList();
	}*/
	
	public Class<EntityClass> getEntityClass(){
		return entityClass;
	}

}