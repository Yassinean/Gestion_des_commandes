package com.oms.dao.implementation;

import com.oms.dao.Interface.ProductDAO;
import com.oms.model.Product;
import com.oms.util.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

	EntityManager em = PersistenceUtil.getEntityManager();
	EntityTransaction et = em.getTransaction();
	
	@Override
	public Product saveProduct(Product product) {
		try {
			et.begin();
			em.persist(product);
			et.commit();
		} catch (Exception e) {
			if(et.isActive()) {
				et.rollback();
			}
			e.printStackTrace();
			return null;
		}finally {
			em.close();
		}
		
		return product;
	}

	@Override
	public void updateProduct(int id, Product product) {
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
			e.printStackTrace();
		}finally {
			em.close();
		}
		
	}

	@Override
	public void deleteProduct(int id) {
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
			e.printStackTrace();
		}finally {
			em.close();
		}
		
	}

	@Override
	public Product findProduct(int id) {
		Product product = null;
		try {
			product = em.find(Product.class, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			em.close();
		}
		return product;
	}

	@Override
	public List<Product> listProducts() {
		List<Product> products = null;
		try {
			TypedQuery<Product> jpql = em.createQuery("SELECT p FROM Product p",Product.class);
			products = jpql.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			em.close();
		}
		return products;
	}
	
	
}