package dcatano.micrometer_demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @GetMapping("/products")
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("Television"));
        products.add(new Product("Book"));
        return products;
    }
}
