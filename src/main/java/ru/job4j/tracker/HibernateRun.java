package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HibernateRun {
	public static void main(String[] args) {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			Item item1 = create(new Item("First", "Desc 1"), sf);
			Item item2 = create(new Item("Second",  "Desc 2 "), sf);
			Item item3 = create(new Item("Third", "Description"), sf);
			update(item1, sf);
			update(item2, sf);
			update(item3, sf);
			Item rsl1 = findById(item1.getId(), sf);
			Item rsl2 = findById(item2.getId(), sf);
			Item rsl3 = findById(item3.getId(), sf);
			System.out.println(rsl1);
			System.out.println(rsl2);
			System.out.println(rsl3);
			delete(rsl2.getId(), sf);
			List<Item> list = findAll(sf);
			for (Item it : list) {
				System.out.println(it);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
	
	public static Item create(Item item, SessionFactory sf) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(item);
		session.getTransaction().commit();
		session.close();
		return item;
	}
	
	public static void update(Item item, SessionFactory sf) {
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(item);
		session.getTransaction().commit();
		session.close();
	}
	
	public static void delete(Integer id, SessionFactory sf) {
		Session session = sf.openSession();
		session.beginTransaction();
		Item item = new Item(null);
		item.setId(id);
		session.delete(item);
		session.getTransaction().commit();
		session.close();
	}
	
	public static List<Item> findAll(SessionFactory sf) {
		Session session = sf.openSession();
		session.beginTransaction();
		List result = session.createQuery("from ru.job4j.tracker.Item").list();
		session.getTransaction().commit();
		session.close();
		return result;
	}
	
	public static Item findById(Integer id, SessionFactory sf) {
		Session session = sf.openSession();
		session.beginTransaction();
		Item result = session.get(Item.class, id);
		session.getTransaction().commit();
		session.close();
		return result;
	}
}
