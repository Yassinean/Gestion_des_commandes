//package service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.oms.dao.Interface.UserDAO;
//import com.oms.model.Admin;
//import com.oms.model.AdminType;
//import com.oms.service.UserService;
//
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.Map;
//import java.util.HashMap;
//
//class AdminTest {
//    
//    private static final Logger logger = LoggerFactory.getLogger(AdminTest.class);
//    
//    @Mock
//    private UserDAO userDAO;
//    
//    private UserService userService;
//    private List<Admin> mockAdmins;
//    
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        userService = new UserService();
//        try {
//            java.lang.reflect.Field daoField = UserService.class.getDeclaredField("userDAO");
//            daoField.setAccessible(true);
//            daoField.set(userService, userDAO);
//        } catch (Exception e) {
//            logger.error("Failed to inject UserDAO into UserService", e);
//        }
//        
//        mockAdmins = Arrays.asList(
//            new Admin("Alami", "Karim", "karim@test.com", "password123", AdminType.SUPER_ADMIN),
//            new Admin("Bennani", "Sara", "sara@test.com", "password456", AdminType.ADMIN),
//            new Admin("Tazi", "Omar", "omar@test.com", "password789", AdminType.ADMIN)
//        );
//    }
//    
//    @Test
//    void createAdmin() {
//        Admin admin = mockAdmins.get(0);
//        when(userDAO.createAdmin(admin)).thenReturn(true);
//        
//        boolean result = userService.createAdmin(admin);
//        
//        assertTrue(result);
//        logger.info("Admin created: {}", admin.getNom());
//        verify(userDAO).createAdmin(admin);
//    }
//    
//    @Test
//    void getAllAdmins() {
//        when(userDAO.getAllAdmins()).thenReturn(mockAdmins);
//        
//        List<Admin> result = userService.getAllAdmins();
//        
//        List<Admin> superAdmins = result.stream()
//            .filter(admin -> admin.getAdminType() == AdminType.SUPER_ADMIN)
//            .collect(Collectors.toList());
//            
//        assertEquals(1, superAdmins.size());
//        logger.info("Number of Super Admins: {}", superAdmins.size());
//        assertEquals("Alami", superAdmins.get(0).getNom());
//    }
//    
//    @Test
//    void searchAdmin() {
//        String searchTerm = "Alami";
//        List<Admin> filteredAdmins = mockAdmins.stream()
//            .filter(admin -> admin.getNom().contains(searchTerm))
//            .collect(Collectors.toList());
//            
//        when(userDAO.searchAdmin(searchTerm)).thenReturn(filteredAdmins);
//        
//        List<Admin> result = userService.searchAdmin(searchTerm);
//        assertEquals(1, result.size());
//        logger.info("Admin found: {}", result.get(0).getNom());
//        assertEquals("Alami", result.get(0).getNom());
//    }
//    
//    @Test
//    void authenticateAdmin() {
//        Map<String, String> testCredentials = new HashMap<>();
//        testCredentials.put("karim@test.com", "password123");
//        
//        Admin admin = mockAdmins.get(0);
//        when(userDAO.authenticateAdmin(
//            testCredentials.get("karim@test.com"),
//            "password123"
//        )).thenReturn(Optional.of(admin));
//        
//        Optional<Admin> result = userService.authenticateAdmin(
//            testCredentials.get("karim@test.com"),
//            "password123"
//        );
//        
//        assertTrue(result.isPresent());
//        logger.info("Admin authenticated: {}", result.get().getNom());
//        assertEquals("Alami", result.get().getNom());
//    }
//    
//    @Test
//    void updateAdmin() {
//        Admin admin = mockAdmins.get(0);
//        admin.setNom("Alami Updated");
//        
//        when(userDAO.updateAdmin(admin)).thenReturn(true);
//        
//        boolean result = userService.updateAdmin(admin);
//        
//        assertTrue(result);
//        logger.info("Admin updated to: {}", admin.getNom());
//        verify(userDAO).updateAdmin(admin);
//    }
//    
//    @Test
//    void deleteAdmin() {
//        int adminId = 1;
//        when(userDAO.deleteAdmin(adminId)).thenReturn(true);
//        
//        boolean result = userService.deleteAdmin(adminId);
//        
//        assertTrue(result);
//        logger.info("Admin deleted with ID: {}", adminId);
//        verify(userDAO).deleteAdmin(adminId);
//    }
//    
//    @Test
//    void getAdminById() {
//        int adminId = 1;
//        Admin admin = mockAdmins.get(0);
//        when(userDAO.getAdminById(adminId)).thenReturn(Optional.of(admin));
//        
//        Optional<Admin> result = userService.getAdminById(adminId);
//        
//        assertTrue(result.isPresent());
//        logger.info("Admin retrieved: {}", result.get().getNom());
//        result.ifPresent(a -> {
//            assertEquals("Alami", a.getNom());
//            assertEquals(AdminType.SUPER_ADMIN, a.getAdminType());
//        });
//    }
//}
