package com.example.hahaha.controllers;


import com.example.hahaha.models.Product;
import com.example.hahaha.services.ProductsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsControllers {
    @Autowired
    private ProductsRepository repo;

    @GetMapping({"","/"})
    public String showProductList(Model model){
        List<Product> sanpham= repo.findAll();
        model.addAttribute("products",sanpham);
        return "products/index";
    }
}
