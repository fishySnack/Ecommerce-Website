package com.simpleWebApp.ecomProj.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.simpleWebApp.ecomProj.model.Product;
import com.simpleWebApp.ecomProj.repo.ProductRepo;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;

    public List<Product> getAllProducts(){
        return repo.findAll();
    }

    public Product getProductById(int id) {
        //can be null so you need to handle it
        //in this case we use orElse
        return repo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        //have to convert the image into byte with other image data
        //make sure to not have an image that's too big
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        return repo.save(product);
    }

    //this is not running properly *fix need*
    public Product updateProduct(int productId, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());

        //this would return only the product without the imagefiles so that's why you need to add the codes above
        return repo.save(product);
    }
    
    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword) {
        return repo.searchProduct(keyword);
    }
}
