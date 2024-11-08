package com.example.hahaha.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class createProduct {
    @NotEmpty(message = "không được để trống tên sản phẩm")
    private String name;
    @NotEmpty(message = "không được để trông tên hãng ")
    private String brand;
    @NotEmpty(message = "không được để trống chi tiết sản phầm")
    private String category;
    @Min(0)
    private Float price;
    @Size(min=10,message = "ngăn nhất là 10 ký tự")
    @Size(max = 2000,message = "dài nhất là 2000 ký tự")
    private String description;
    private MultipartFile imageFile;

    public @NotEmpty(message = "không được để trống tên sản phẩm") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "không được để trống tên sản phẩm") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "không được để trông tên hãng ") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "không được để trông tên hãng ") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "không được để trống chi tiết sản phầm") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "không được để trống chi tiết sản phầm") String category) {
        this.category = category;
    }

    public @Min(0) Float getPrice() {
        return price;
    }

    public void setPrice(@Min(0) Float price) {
        this.price = price;
    }

    public @Size(min = 10, message = "ngăn nhất là 10 ký tự") @Size(max = 2000, message = "dài nhất là 2000 ký tự") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "ngăn nhất là 10 ký tự") @Size(max = 2000, message = "dài nhất là 2000 ký tự") String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
