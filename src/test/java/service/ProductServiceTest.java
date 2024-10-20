//package service;
//import static org.mockito.Mockito.*;
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.oms.dao.Interface.ProductDAO;
//import com.oms.model.Product;
//import com.oms.service.ProductService;
//
//import java.util.Arrays;
//
//public class ProductServiceTest {
//
//    @Mock
//    private ProductDAO productRepository;
//
//    @InjectMocks
//    private ProductService productService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);  // Initialise les mocks
//    }
//
//    @Test
//    public void testCreateProduct() {
//        Product product = new Product(1, "Laptop", "Good Quality" ,1200.00, 10);
//        when(productRepository.saveProduct(product)).thenReturn(product);
//        System.out.println(product);
//        Product createdProduct = productService.saveProduct(product);
//        
//        assertNotNull(createdProduct);
//        assertEquals("Laptop", createdProduct.getName());
//        assertEquals(1200.00, createdProduct.getPrice(), 0);
//        verify(productRepository, times(1)).saveProduct(product);
//    }
//
//
////    @Test
////    public void testGetAllProducts() {
////        Product product1 = new Product(1, "Laptop", "Mac",1200.00, 10);
////        Product product2 = new Product(2, "Phone", "Iphone",800.00, 15);
////
////        when(productRepository.listProducts(1,2)).thenReturn(Arrays.asList(product1, product2));
////
////        assertEquals(2, productService.listProducts(0,0).size());
////        verify(productRepository, times(1)).listProducts(0, 0);
////    }
//
////    @Test
////    public void testDeleteProduct() {
////        productService.deleteProduct(1);
////        verify(productRepository, times(1)).deleteProduct(1);
////    }
//}
