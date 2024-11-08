package com.example.hahaha.controllers;


import com.example.hahaha.models.Product;
import com.example.hahaha.models.createProduct;
import com.example.hahaha.services.ProductsRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/products";
    }
}
