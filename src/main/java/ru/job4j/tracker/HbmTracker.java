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
	
	@Override
	public Item add(Item item) {
		try (Session session = sf.openSession()) {
			session.save(item);
			return item;
		}
	}
	
	@Override
	public boolean replace(int id, Item item) {
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			int count = session.createQuery("update Item s set s.name = :newName, "
					+ "s.description = :newDesc where s.id = :fId")
					.setParameter("newName", item.getName())
					.setParameter("newDesc", item.getDescription())
					.setParameter("fId", id)
					.executeUpdate();
			session.getTransaction().commit();
			return count >= 1;
		}
	}
	
	@Override
	public boolean delete(int id) {
		try (Session session = sf.openSession()) {
			session.beginTransaction();
			int count = session
					.createQuery("delete from Item where id = :fId")
					.setParameter("fId", id)
					.executeUpdate();
			session.getTransaction().commit();
			return count >= 1;
		}
	}
	
	@Override
	public List<Item> findAll() {
		try (Session session = sf.openSession()) {
			Query<Item> query = session
					.createQuery("from Item ");
			return query.list();
		}
	}
	
	@Override
	public List<Item> findByName(String key) {
		try (Session session = sf.openSession()) {
			Query<Item> queryId = session
					.createQuery("from Item s where s.name = :key ")
					.setParameter("key", key);
			return queryId.list();
		}
	}
	
	@Override
	public Item findById(int id) {
		try (Session session = sf.openSession()) {
			Query<Item> queryId = session
					.createQuery("from Item s where s.id = :findId ")
					.setParameter("findId", id);
			return queryId.uniqueResult();
		}
	}
	
	@Override
	public void close() throws Exception {
		StandardServiceRegistryBuilder.destroy(registry);
	}
}
