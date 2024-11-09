package com.example.hahaha.controllers;


import com.example.hahaha.models.Product;
import com.example.hahaha.models.createProduct;
import com.example.hahaha.services.ProductsRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;

import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsControllers {
//    hiện thị sản phẩm
    @Autowired
    private ProductsRepository repo;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Product> sanpham= repo.findAll();
        model.addAttribute("products",sanpham);
        return "products/index";
    }

//    thêm sản phẩm
    @GetMapping("/create")
    public String showCreateProducts(Model model){
       createProduct addsanpham = new createProduct();
        model.addAttribute("addsanpham",addsanpham);
        return "products/create";
    }
    @PostMapping("/create")
    public String themsp(@Valid @ModelAttribute createProduct Createproduct, BindingResult result)
    {
        if(Createproduct.getImageFile().isEmpty()){
            result.addError(new FieldError("Createproduct","imageFile","ảnh không tồn tại"));
        }
        if(result.hasErrors()){
            return "produects/create";

        }
//        lưu ảnh
        MultipartFile image = Createproduct.getImageFile();
        LocalDateTime createdAt = LocalDateTime.now() ;
        String storageFileName =image.getOriginalFilename();
        try {
            String uploadDir = "public/images";
            Path uploadPath = Paths.get(uploadDir);

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Sử dụng uploadPath.resolve(storageFileName) để nối đường dẫn đúng cách
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = uploadPath.resolve(storageFileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }


        Product sanpham=new Product();
        sanpham.setName(Createproduct.getName());
        sanpham.setBrand(Createproduct.getBrand());
        sanpham.setCategory(Createproduct.getCategory());
        sanpham.setPrice(Createproduct.getPrice());
        sanpham.setDescription(Createproduct.getDescription());
        sanpham.setCreateAt(createdAt);
        sanpham.setImageFileName(storageFileName);

        repo.save(sanpham);

        return "redirect:/products";
    }


//    xoá sản phẩm
    @GetMapping("/edit")
    public String editsanpham(Model model,@RequestParam int id){

        try {
            Product sanpham=repo.findById(id).get();
            model.addAttribute("sanpham",sanpham);

            createProduct themsp = new createProduct();

            themsp.setName(sanpham.getName());
            themsp.setBrand(sanpham.getBrand());
            themsp.setCategory(sanpham.getCategory());
            themsp.setPrice(sanpham.getPrice());
            themsp.setDescription(sanpham.getDescription());

            model.addAttribute("editSp",themsp);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }

        return "products/edit";
    }

    @PostMapping("/edit")
    public String editProducts(Model model,
                               @RequestParam int id,
                               @Valid @ModelAttribute createProduct editsp,
                               BindingResult result){

        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product",product);
            if(result.hasErrors()){
                return "products/edit";
            }

            if(!editsp.getImageFile().isEmpty()){
                // xoá ảnh cũ
                String uploadDir="public/images/";
                Path oldImage=Paths.get(uploadDir+product.getImageFileName());

                try{
                    Files.delete(oldImage);

                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }

                // lưu ảnh mới

                MultipartFile image = editsp.getImageFile();
                LocalDateTime createdAt = LocalDateTime.now() ;
                String storageFileName =image.getOriginalFilename();

                try {

                    try (InputStream inputStream = image.getInputStream()) {
                        Files.copy(inputStream,Paths.get(uploadDir+storageFileName),
                         StandardCopyOption.REPLACE_EXISTING);
                    }
                    product.setImageFileName(storageFileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                product.setName(editsp.getName());
                product.setBrand(editsp.getBrand());
                product.setCategory(editsp.getCategory());
                product.setPrice(editsp.getPrice());
                product.setDescription(editsp.getDescription());
                repo.save(product);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/products";
    }
}
