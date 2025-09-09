package ait.cohor63.shop.controller;

import ait.cohor63.shop.model.dto.ProductDTO;
import ait.cohor63.shop.model.entity.Role;
import ait.cohor63.shop.model.entity.User;
import ait.cohor63.shop.repository.ProductRepository;
import ait.cohor63.shop.repository.RoleRepository;
import ait.cohor63.shop.repository.UserRepository;
import ait.cohor63.shop.security.dto.LoginRequestDTO;
import ait.cohor63.shop.security.dto.TokenResponseDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerTest {

    // Cmd + Shift + U / Ctrl + Shift + U
    private static final String TEST_PRODUCT_TITLE = "Test Product";
    private static final int TEST_PRODUCT_PRICE = 777;

    private static final String TEST_ADMIN_NAME = "Test Admin";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_PASSWORD = "Test password";

    private static final String ROLE_ADMIN_TITLE = "ROLE_ADMIN";
    private static final String ROLE_USER_TITLE = "ROLE_USER";

    private final String URL_PREFIX = "http://localhost:";
    private final String AUTH_RESOURCE_NAME = "/api/auth";
    private final String PRODUCT_RESOURCE_NAME = "/api/products";
    private final String LOGIN_ENDPOINT = "/login";

    private final String BEARER_PREFIX = "Bearer ";
    private final String AUTH_HEADER_NAME = "Authorization";


    @LocalServerPort
    private int port;

    private TestRestTemplate template;

    // import org.springframework.http.HttpHeaders;
    private HttpHeaders headers;
    private ProductDTO testProduct;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    private String adminAccessToken;
    private String userAccessToken;

    private static Long testProductId;

    @BeforeEach
    public void setUp() {

        // Инициализируем TestRestTemplate
        template = new TestRestTemplate();

        // Инициализируем заголовки
        headers = new HttpHeaders();

        // создание тестового продукта
        testProduct = new ProductDTO();
        testProduct.setTitle(TEST_PRODUCT_TITLE);
        testProduct.setPrice(new BigDecimal(TEST_PRODUCT_PRICE));

        Role roleAdmin;
        Role roleUser = null;

        User admin = userRepository.findByUsername(TEST_ADMIN_NAME).orElse(null);
        User user = userRepository.findByUsername(TEST_USER_NAME).orElse(null);

        if (admin == null) {
            // Надо создать и сохранить в БД админа
            roleAdmin = roleRepository.findByTitle(ROLE_ADMIN_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role ADMIN not found in DB"));
            roleUser = roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role USER not found in DB"));

            admin = new User();
            admin.setUsername(TEST_ADMIN_NAME);
            admin.setPassword(encoder.encode(TEST_PASSWORD));
            admin.setRoles(Set.of(roleAdmin, roleUser));

            userRepository.save(admin);
        }

        if (user == null) {
            // сохранить и создать в БД user
            roleUser = (roleUser == null) ? roleRepository.findByTitle(ROLE_USER_TITLE)
                    .orElseThrow(() -> new RuntimeException("Role USER not found in DB")) : roleUser;

            user = new User();
            user.setUsername(TEST_USER_NAME);
            user.setPassword(encoder.encode(TEST_PASSWORD));
            user.setRoles(Set.of(roleUser));

            userRepository.save(user);
        }


        // POST -> http://localhost:port/api/auth/login + LoginRequestDTO

        // Создаем объекты LoginRequestDTO для админа и пользователя
        LoginRequestDTO loginAdminDto  = new LoginRequestDTO(TEST_ADMIN_NAME, TEST_PASSWORD);
        LoginRequestDTO loginUserDto = new LoginRequestDTO(TEST_USER_NAME, TEST_PASSWORD);

        String authUrl = URL_PREFIX + port + AUTH_RESOURCE_NAME + LOGIN_ENDPOINT;

        HttpEntity<LoginRequestDTO> request = new HttpEntity<>(loginAdminDto);

        ResponseEntity<TokenResponseDTO> response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );

        assertTrue(response.hasBody(), "Authorization (admin) response body is empty");

        TokenResponseDTO tokenResponse = response.getBody();
        adminAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();

        request = new HttpEntity<>(loginUserDto);

        response = template.exchange(
                authUrl,
                HttpMethod.POST,
                request,
                TokenResponseDTO.class
        );

        assertTrue(response.hasBody(), "Authorization (user) response body is empty");

        tokenResponse = response.getBody();
        userAccessToken = BEARER_PREFIX + tokenResponse.getAccessToken();
    }

    // тесты будут здесь

    @Test
    public void positiveGettingAllProductsWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        // Void -специальный класс пустышка

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<List<ProductDTO>> response = template.exchange(
                url,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<ProductDTO>>() {}
        );

        // Проверка статуса ответа
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status code");

        // Проверка наличия тела
        assertTrue(response.hasBody(), "Response doesn't have body");
    }

    @Test
    public void negativeSavingProductWithoutAuthorization() {

        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        HttpEntity<ProductDTO> request = new HttpEntity<>(testProduct);

        ResponseEntity<ProductDTO> response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDTO.class
        );

        // Проверка статуса: статус Forbidden 403
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode(), "Response has unexpected status code");

        // Проверка тела: отсутствие тела
        assertFalse(response.hasBody(), "Response has unexpected body");

    }

    @Test
    void negativeSavingProductWithUserTokenAuthorization() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;

        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        //Map<String, List<String>

        // Todo Homework
        /*
        Составить запрос (передать туда тестовый продукт и заголовки с авторизацией)
       Отправить запрос, получить ответ
       Проверка статуса ответа и тела
         */

    }

    @Order(10)
    @Test
    void positiveSavingProductWithAdminTokenAuthorization() {

        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME;
        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken));

        HttpEntity<ProductDTO> request = new HttpEntity<>(testProduct, headers);

        ResponseEntity<ProductDTO> response = template.exchange(
                url,
                HttpMethod.POST,
                request,
                ProductDTO.class
        );

        // Повторное сохранение продукта с таким же title невозможно
        // В последнем по order тесте я должен удалить продукт из БД
        // Мне нужно зафиксировать id сохраненного продукта (для удаления)

        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has unexpected status code");
        assertTrue(response.hasBody(), "Response body is empty");

        ProductDTO savedProduct = response.getBody();

        assertNotNull(savedProduct, "Response body is null");
        assertEquals(testProduct.getTitle(), savedProduct.getTitle(), "Saved product has unexpected title");

        // Сохраняем id продукта для использования
        testProductId = savedProduct.getId();

    }

    @Test
    @Order(20)
    void negativeGettingProductByIdWithoutAuthorization() {

        // GET http://localhost:port/api/products/7
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME + "/" + testProductId;

        //Todo HW
        /*
        Сформировать запрос без добавления авторизации
        Отправить запрос, получить результат
        // Проверить статус, проверить отсутствие тела
         */
    }

    @Test
    @Order(30)
    void negativeGettingProductByIdWithBasicAuthorization() {

        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME + "/" + testProductId;

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<ProductDTO> response = template
                // Добавляем базовую авторизацию в запрос
                .withBasicAuth(TEST_ADMIN_NAME, TEST_PASSWORD)
                .exchange(
                url,
                HttpMethod.GET,
                request,
                ProductDTO.class
                );

        // TODO HW
        // Проверить ответ (какой статус, какое тело)?
    }

    @Test
    @Order(40)
    void negativeGettingProductWithIncorrectTokenAuthorization() {

        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME + "/" + testProductId;

        headers.put(AUTH_HEADER_NAME, List.of(adminAccessToken + "a"));

        // TODO HW запрос, ответ, проверка статуса 403 и тела
    }

    @Test
    @Order(50)
    void positiveGettingProductByIdWithUserTokenAuthorization() {
        String url = URL_PREFIX + port + PRODUCT_RESOURCE_NAME + "/" + testProductId;

        headers.put(AUTH_HEADER_NAME, List.of(userAccessToken));
        
        // TODO HW запрос, ответ, статус, тело, наличие в теле объекта (не null)

        // В последнем тесте цепочки удаляем из БД сохраненный продукт

        productRepository.deleteById(testProductId);
    }





}