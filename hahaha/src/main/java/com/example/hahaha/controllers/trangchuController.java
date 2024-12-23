package com.example.hahaha.controllers;

import com.example.hahaha.models.Product;
import com.example.hahaha.services.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/static")
public class trangchuController {
    @Autowired
    private ProductsRepository repo;

    @GetMapping("/index")
    public String Trangchu(Model model)
    {
        List<Product> sanpham=repo.findAll(Sort.by(Sort.Direction.DESC,"id"));
        model.addAttribute("products",sanpham);
        return "static/index";

    }
}
