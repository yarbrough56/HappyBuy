package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Product;

@Repository 
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	public void addProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			//database operation all through transaction
			session.beginTransaction();
			session.save(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			// if any exception appeared, rollback to the state beofore the opeation
			session.getTransaction().rollback(); 
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	
	public void deleteProduct(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			// 强制类型转换  --> get only return a generic type 
			Product product = (Product) session.get(Product.class, productId);  
			session.delete(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}

	
	public void updateProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			 // saveOrUpdate
			session.saveOrUpdate(product); 
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

	
	public Product getProductById(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Product product = (Product) session.get(Product.class, productId);
			session.getTransaction().commit();
			return product;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return null;
	}

	
	public List<Product> getAllProducts() {
		Session session = null;
		List<Product> products = new ArrayList<Product>();
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			// should try creatQuery maybe
			//(Prodcut.class)--> persistentClass means all the stuff data are stored in database not jvm
			products = session.createCriteria(Product.class).list(); 
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return products;
	}

}
