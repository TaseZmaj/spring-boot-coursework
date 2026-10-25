package mk.ukim.finki.wp.auditoryexercise4and5project.web.controller;

import mk.ukim.finki.wp.auditoryexercise4and5project.model.Product;
import mk.ukim.finki.wp.auditoryexercise4and5project.model.enums.ProductLevel;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.CategoryService;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ManufacturerService;
import mk.ukim.finki.wp.auditoryexercise4and5project.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final ManufacturerService manufacturerService;

    public ProductController(ProductService productService, CategoryService categoryService, ManufacturerService manufacturerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }

    //CRUD - READ -> display "products page"
    @GetMapping()
    public String getProductPage(@RequestParam(required = false) String error, @RequestParam(required = false) String search, Model model){
        if(error != null) {
            model.addAttribute("error", error);
        }

        //Ako ima searchQuery - filter products, ako ne -> pokazi gi site
        List<Product> products;
        if(search != null && !search.isEmpty()){
            products = productService.search(search);
        }else{
            products = productService.findAll();
        }
        model.addAttribute("products", products);
        return "products";
    }

    //CRUD - DELETE product
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return "redirect:/products";
    }

    //CRUD - READ -> display "edit a single product" page
    @GetMapping("/edit-form/{id}")
    public String editProductPage(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("levels", ProductLevel.values());
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "product-form";
    }

    //CRUD - READ -> display "add new product" page
    @GetMapping("/add-form")
    public String addProductPage(Model model){
        model.addAttribute("levels", ProductLevel.values());
        model.addAttribute("categories", categoryService.listCategories());
        model.addAttribute("manufacturers", manufacturerService.findAll());
        return "product-form.html";
    }

    //CRUD - CREATE product
    @PostMapping
    public String saveProduct(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam ProductLevel level,
            @RequestParam Long category,
            @RequestParam Long manufacturer
    ){
        productService.create(name,price,quantity,level,category,manufacturer);
        return "redirect:/products";
    }

    //CRUD - UPDATE product
    @PostMapping("/{id}")
    public String updateProduct(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam Integer quantity,
            @RequestParam ProductLevel level,
            @RequestParam Long category,
            @RequestParam Long manufacturer
    ){
        productService.update(id, name,price,quantity,level,category,manufacturer);
        return "redirect:/products";
    }
}
