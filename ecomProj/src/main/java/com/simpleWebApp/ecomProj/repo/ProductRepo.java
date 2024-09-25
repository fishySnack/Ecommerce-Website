package com.simpleWebApp.ecomProj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simpleWebApp.ecomProj.model.Product;

import java.util.List;
@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>{
    
    //searching for product
    @Query("SELECT p from Product p WHERE "+ 
    "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " + 
    "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
    "LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProduct(String keyword);
    
}
