package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
	private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
	private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
	private Session session = sf.openSession();
	
	@Override
	public Item add(Item item) {
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
		return item;
	}
	
	@Override
	public boolean replace(int id, Item item) {
		try {
			session.beginTransaction();
			session.createQuery("update Item s set s.name = :newName, s.description = :newDesc where s.id = :fId").setParameter("newName", item.getName()).setParameter("newDesc", item.getDescription()).setParameter("fId", id).executeUpdate();
			session.getTransaction().commit();
			session.close();
			session = sf.openSession();
			return true;
		} catch (Exception r) {
			return false;
		}
	}
	
	@Override
	public boolean delete(int id) {
		try {
			session.beginTransaction();
			session.createQuery("delete from Item where id = :fId").setParameter("fId", id).executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<Item> findAll() {
		session.beginTransaction();
		Query<Item> query = session.createQuery("from Item ");
		session.getTransaction().commit();
		return query.list();
	}
	
	@Override
	public List<Item> findByName(String key) {
		session.beginTransaction();
		Query<Item> queryId = session.createQuery("from Item s where s.name = :key ").setParameter("key", key);
		session.getTransaction().commit();
		return queryId.list();
	}
	
	@Override
	public Item findById(int id) {
		session.beginTransaction();
		Query<Item> queryId = session.createQuery("from Item s where s.id = :findId ").setParameter("findId", id);
		session.getTransaction().commit();
		return queryId.uniqueResult();
	}
	
	@Override
	public void close() throws Exception {
		StandardServiceRegistryBuilder.destroy(registry);
	}
}
