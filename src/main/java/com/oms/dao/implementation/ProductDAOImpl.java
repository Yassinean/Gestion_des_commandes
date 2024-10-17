package com.oms.dao.implementation;

import com.oms.dao.Interface.ProductDAO;
import com.oms.model.Product;
import com.oms.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.List;

public class ProductDAOImpl implements ProductDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ProductDAOImpl.class.getName());
	
	@Override
	public Product saveProduct(Product product) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(product);
			et.commit();
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			  LOGGER.log(Level.SEVERE, "Error saving product", e);
			return null;
		}finally {
			em.close();
		}
		
		return product;
	}

	@Override
	public void updateProduct(int id, Product product) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Product existProduct = em.find(Product.class, product.getId());
			if(existProduct != null) {
				existProduct.setName(product.getName());
				existProduct.setDescription(product.getDescription());
				existProduct.setPrice(product.getPrice());
				existProduct.setStock(product.getStock());
				em.merge(existProduct);
			}
			et.commit();
			
		}catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			 LOGGER.log(Level.SEVERE, "Error updating product", e);
		}finally {
			em.close();
		}
		
	}

	@Override
	public void deleteProduct(int id) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			Product product = em.find(Product.class,id);
			if (product != null ) {
				em.remove(product);				
			}
			et.commit();
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			 LOGGER.log(Level.SEVERE, "Error deleting product", e);
		}finally {
			em.close();
		}
		
	}

	@Override
	public Product findProduct(int id) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Product product = null;
		try {
			product = em.find(Product.class, id);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error finding product", e);
		}
		finally {
			em.close();
		}
		return product;
	}

	@Override
	public List<Product> listProducts(int page, int size) {
	    EntityManager em = PersistenceUtil.getEntityManager();
	    List<Product> products = null;
	    try {
	        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
	        query.setFirstResult((page - 1) * size);  // Offset calculation
	        query.setMaxResults(size);  // Limit the result set size
	        products = query.getResultList();
	    } catch (Exception e) {
	        LOGGER.log(Level.SEVERE, "Error listing products", e);
	    } finally {
	        em.close();
	    }
	    return products;
	}

	@Override
	public List<Product> searchProducts(String name) {
	    EntityManager em = PersistenceUtil.getEntityManager();
	    List<Product> products = null;
	    try {
	        TypedQuery<Product> query = em.createQuery(
	            "SELECT p FROM Product p WHERE p.name LIKE :name", Product.class);
	        query.setParameter("name", "%" + name + "%");
	        products = query.getResultList();
	    } catch (Exception e) {
	        LOGGER.log(Level.SEVERE, "Error searching products", e);
	    } finally {
	        em.close();
	    }
	    return products;
	}
	
	@Override
	public int getTotalProductCount() {
	    EntityManager em = PersistenceUtil.getEntityManager();
	    int count = 0;
	    try {
	        TypedQuery<Long> query = em.createQuery("SELECT COUNT(p) FROM Product p", Long.class);
	        count = query.getSingleResult().intValue();
	    } catch (Exception e) {
	    	  LOGGER.log(Level.SEVERE, "Error getting total product count", e);
	           return 0;
	    } finally {
	        em.close();
	    }
	    return count;
	}
	
}