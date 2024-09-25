package com.simpleWebApp.ecomProj.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.simpleWebApp.ecomProj.model.Product;
import com.simpleWebApp.ecomProj.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
    
    @Autowired
    private ProductService service;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct(){
        //this is nice to look at
        //when doing this don't forget to send the httpstatus
        //responseEntity makes it so that the 'user' can add data
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProuct(@PathVariable int id){
        //checking for null before returning
        Product product = service.getProductById(id);
        if(product == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //extra mapping done after the first initial mapping
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = service.addProduct(product, imageFile); 
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch(Exception e){
            //e.getMessage get the error that e provide
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int productId){
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();

        return ResponseEntity.ok()
        .contentType(MediaType.valueOf(product.getImageType()))
        .body(imageFile);
    }
    
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile){        
        try {
            service.updateProduct(id,product,imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    } 
    
    @DeleteMapping("product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product == null){
            return new ResponseEntity<>("Failed to delete", HttpStatus.BAD_REQUEST);
        }
        service.deleteProduct(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products,HttpStatus.OK);
    }
}
