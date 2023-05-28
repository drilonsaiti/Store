package com.example.store.controllers;




import com.example.store.model.Products;
import com.example.store.model.Sales;
import com.example.store.model.dto.ProductQuantityDto;
import com.example.store.model.dto.TopProductQuantityAndProfitDto;
import com.example.store.repository.SalesRepository;
import com.example.store.service.ProductsService;
import com.example.store.service.SalesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SalesController {

    private final SalesService salesService;
    private final ProductsService prodcutsService;

    private final SalesRepository salesRepository;

    public SalesController(SalesService salesService, ProductsService prodcutsService, SalesRepository salesRepository) {
        this.salesService = salesService;
        this.prodcutsService = prodcutsService;
        this.salesRepository = salesRepository;
    }

    @GetMapping("sales")
    public String getSales(Model model){
        Sales sales = this.salesService.findByDay(LocalDateTime.now());
        if (sales != null){
        LocalDateTime time = sales.getOpen();
        String data = sales.getOpenDayString();
        String openTime = sales.getTimeOpenString();
        List<String> productList = new ArrayList<>();
        for (Products p : this.prodcutsService.getAll()){
            productList.add(p.getName());
        }
        model.addAttribute("productList",productList);
        model.addAttribute("data",data);
        model.addAttribute("koha",openTime);
        model.addAttribute("products", this.salesService.fromMapToList(sales));
        }
        model.addAttribute("sales",sales);
        return "sales";
    }

     @PostMapping("sales")
    public String addSales(@RequestParam String name,@RequestParam int quantity, Model model){
        Long id = this.prodcutsService.SearchProductsByName(name).get(0).getId();
        Sales sales = this.salesService.addProductToSalesDay(id, quantity);
         model.addAttribute("sales",sales);
         return "redirect:/sales";
     }

    @PostMapping("open-store")
    public String openStore(Model model){
        this.salesService.openDay();
        return "redirect:/sales";
    }
    @PostMapping("close-store")
    public String closeStore(Model model){
        this.salesService.closeDay();
        return "redirect:/sales";
    }

    @GetMapping("all-sales")
    public String getAllSales(Model model){
        List<Sales> sales = this.salesService.findAll();
        model.addAttribute("sales",sales);

        return "all-sales";
    }

    @GetMapping("all-sales-data/{day}")
    public String getAllSalesPerDay(@PathVariable String day, Model model){
        LocalDateTime time = LocalDateTime.parse(day);
        Sales sales = this.salesService.findByDay(time);
        List<ProductQuantityDto> list = this.salesService.fromMapToList(sales);

        LocalDateTime open = sales.getOpen();
        String data = sales.getOpenDayString();
        String koha = sales.getTimeOpenString();
        String closed = sales.getClosedDayString();

        model.addAttribute("data",data);
        model.addAttribute("koha",koha);
        model.addAttribute("closed",closed);

        model.addAttribute("sales",list);
        model.addAttribute("totalProfit",list.stream().mapToInt(ProductQuantityDto::getProfit).sum());
        return "all-sales-data";
    }
    @GetMapping("top-products")
    public String getTopProductsByProfit(Model model){
        List<TopProductQuantityAndProfitDto> list = this.salesService.topProducts("","").stream().sorted(Comparator.comparing(TopProductQuantityAndProfitDto::getProfit).reversed()).collect(Collectors.toList());
        model.addAttribute("products",list);


        return "top-products";
    }

    @PostMapping("top-products-date")
    public String getTopProductsByProfit(@RequestParam String startDate, @RequestParam String endDate,Model model){
        System.out.println(startDate);
        List<TopProductQuantityAndProfitDto> list = this.salesService.topProducts(startDate,endDate).stream().sorted(Comparator.comparing(TopProductQuantityAndProfitDto::getProfit).reversed()).collect(Collectors.toList());
        model.addAttribute("products",list);


        return "top-products";
    }

    @GetMapping("test")
    public void test(){
        List<Object[]> queryResult = this.salesRepository.list(1L);
        List<ProductQuantityDto> productQuantityDtos = new ArrayList<>();

        for (Object[] row : queryResult) {
            String name = (String) row[0];
            Integer quantity = (Integer) row[1];
            Integer totalPrice = (Integer) row[2];
            productQuantityDtos.add(new ProductQuantityDto(name, quantity, totalPrice));
        }

        productQuantityDtos.forEach(System.out::println);
    }
}
