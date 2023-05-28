package com.example.store.controllers;


import com.example.store.model.Products;
import com.example.store.model.dto.ProductsByDateDto;
import com.example.store.service.ProductsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService prodcutsService;

    public ProductsController(ProductsService prodcutsService) {
        this.prodcutsService = prodcutsService;
    }

    @GetMapping
    public String getProducts(Model model){
        List<Products> productsList = this.prodcutsService.getAll();
        model.addAttribute("products",productsList);

        return "products";
    }
    @GetMapping("add")
    public String addAgencies(Model model) {


        return "add-product";
    }

    @PostMapping
    public String addProducts(@RequestParam  String name,@RequestParam int price,@RequestParam int quantity){
        this.prodcutsService.createProduct(name, price, quantity);
        return "redirect:/products";

    }


    @GetMapping("/{id}/edit")
    public String showEdit(@PathVariable Long id,Model model) {
        Products product = this.prodcutsService.getById(id).orElseThrow();

        model.addAttribute("product",product);
        return "add-product";

    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,@RequestParam  String name,@RequestParam int price,@RequestParam int quantity){
        this.prodcutsService.updateProduct(id,name, price, quantity);
        return "redirect:/products";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        this.prodcutsService.deleteProduct(id);
        return "redirect:/products";
    }

    @GetMapping("/details/{id}/{name}")
    public String getDetails(@PathVariable Long id, @PathVariable String name , Model model){
        List<ProductsByDateDto> list = this.prodcutsService.productsByDate(id,name);
        model.addAttribute("products",list);
        model.addAttribute("name",name);
        model.addAttribute("totalQuantity",list.stream().mapToInt(ProductsByDateDto::getQuantity).sum());
        model.addAttribute("totalProfit",list.stream().mapToInt(ProductsByDateDto::getProfit).sum());
        return "details";
    }
}
