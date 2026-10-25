package mk.ukim.finki.wp.jan2025g2;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.wp.exam.util.CodeExtractor;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import mk.ukim.finki.wp.exam.util.SubmissionHelper;
import mk.ukim.finki.wp.jan2025g2.model.NationalPark;
import mk.ukim.finki.wp.jan2025g2.model.ParkLocation;
import mk.ukim.finki.wp.jan2025g2.model.ParkType;
import mk.ukim.finki.wp.jan2025g2.service.NationalParkService;
import mk.ukim.finki.wp.jan2025g2.service.ParkLocationService;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SeleniumScenarioTest {

    
    static {
        SubmissionHelper.exam = "2025-jan-g2";
        //TODO: CHANGE THE VALUE OF THE SubmissionHelper.index WITH YOUR INDEX NUMBER!!!
        SubmissionHelper.index = "233105";
    }

    @Autowired
    NationalParkService nationalParkService;

    @Autowired
    ParkLocationService locationService;

    @Order(1)
    @Test
    public void test_list_10pt() {
        SubmissionHelper.startTest("test-list-10", 10);
        List<NationalPark> items = this.nationalParkService.listAll();
        int itemNum = items.size();

        ExamAssert.assertNotEquals("Empty db", 0, itemNum);

        ItemsPage listPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);
        listPage.assertItems(itemNum);

        SubmissionHelper.endTest();
    }

    @Order(2)
    @Test
    public void test_pagination_10pt() {
        SubmissionHelper.startTest("test-pagination-10", 10);
        int pageSize = 5;
        int pageNum = 1;

        ExamAssert.assertNotEquals("Empty db", 0, pageSize);

        ItemsPage listPage = ItemsPage.to(this.driver, pageSize, pageNum);
        AbstractPage.assertRelativeUrl(this.driver, "/?pageSize=" + pageSize + "&pageNum=" + pageNum);
        listPage.assertItems(pageSize);

        SubmissionHelper.endTest();
    }

    @Order(3)
    @Test
    public void test_filter_5pt() {
        SubmissionHelper.startTest("test-filter-5", 5);
        ItemsPage listPage = ItemsPage.to(this.driver);

        listPage.filter("", "", "", "", "");
        listPage.assertItems(10);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("National Park 2", "", "", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "1200", "", "", "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "5.0", "", "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "NATIONAL_RESERVE", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "", "1");
        listPage.assertItems(4);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("1", "1000", "", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("1", "", "5.0", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("National Park 1", "", "", "WILDLIFE_SANCTUARY", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "1000", "5.0", "", "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "1000", "", "MARINE_PROTECTED_AREA", "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "4.5", "", "2");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("", "", "", "WILDLIFE_SANCTUARY", "2");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("National Park 1", "500", "", "WILDLIFE_SANCTUARY", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("National Park 2", "", "3.9", "", "2");
        listPage.assertItems(0);

        listPage = ItemsPage.to(this.driver);
        listPage.filter("National Park 1", "600", "3.7", "WILDLIFE_SANCTUARY", "");
        listPage.assertItems(0);

        SubmissionHelper.endTest();
    }


    @Order(4)
    @Test
    public void test_filter_service_5pt() {
        SubmissionHelper.startTest("test-filter-service-5", 5);

        ExamAssert.assertEquals("without filter", 10, this.nationalParkService.findPage(null, null, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name only", 1, this.nationalParkService.findPage("National Park 2", null, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by area size only", 3, this.nationalParkService.findPage(null, 1200.0, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by rating only", 3, this.nationalParkService.findPage(null, null, 5.0, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by  parkType only", 2, this.nationalParkService.findPage(null, null, null, ParkType.NATIONAL_RESERVE, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by location only", 4, this.nationalParkService.findPage(null, null, null, null, 1L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name and area size", 1, this.nationalParkService.findPage("1", 1000.0, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name and rating", 1, this.nationalParkService.findPage("1", null, 5.0, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name and  parkType", 1, this.nationalParkService.findPage("National Park 1", null, null, ParkType.WILDLIFE_SANCTUARY, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by area size and rating", 3, this.nationalParkService.findPage(null, 1000.0, 5.0, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by area size and  parkType", 2, this.nationalParkService.findPage(null, 1000.0, null, ParkType.MARINE_PROTECTED_AREA, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by rating and location", 1, this.nationalParkService.findPage(null, null, 4.5, null, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by location and parkType", 1, this.nationalParkService.findPage(null, null, null, ParkType.WILDLIFE_SANCTUARY, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name, area size, and  parkType", 1, this.nationalParkService.findPage("National Park 1", 500.0, null, ParkType.WILDLIFE_SANCTUARY, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name, rating, and location", 0, this.nationalParkService.findPage("National Park 2", null, 3.9, null, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by park name, area size, rating, and location", 0, this.nationalParkService.findPage("National Park 1", 600.0, 3.7, ParkType.WILDLIFE_SANCTUARY, null, 0, 20).getNumberOfElements());

        SubmissionHelper.endTest();
    }

    @Order(5)
    @Test
    public void test_create_10pt() {
        SubmissionHelper.startTest("test-create-10", 10);
        List<ParkLocation> locations = this.locationService.listAll();
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();
        ItemsPage listPage = null;

        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
        }


        listPage = AddOrEditNationalPark.add(this.driver, ADD_URL, "testName", "250", "5", ParkType.WILDLIFE_SANCTUARY.name(), locations.get(0).getId().toString());
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, LIST_URL);
        listPage.assertNoError();
        listPage.assertItems(itemNum + 1);

        SubmissionHelper.endTest();
    }

    @Order(6)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_create_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-create-mvc-10", 10);
        List<ParkLocation> locations = this.locationService.listAll();
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();

        MockHttpServletRequestBuilder addRequest = MockMvcRequestBuilders
                .post("/national-parks")
                .param("name", "testName")
                .param("areaSize", "200")
                .param("rating", "3.3")
                .param("parkType", ParkType.WILDLIFE_SANCTUARY.name())
                .param("locationId", locations.get(0).getId().toString());

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        parks = this.nationalParkService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum + 1, parks.size());

        addRequest = MockMvcRequestBuilders
                .get("/national-parks/add");

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(7)
    @Test
    public void test_edit_10pt() {
        SubmissionHelper.startTest("test-edit-10", 10);
        List<ParkLocation> locations = this.locationService.listAll();
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        AddOrEditNationalPark editPage = AddOrEditNationalPark.getEditPage(this.driver, listPage.getEditButtons().get(itemNum - 1));
        NationalPark park = parks.get(itemNum - 1);
        editPage.assertEditFormIsPrefilled(park.getName(), park.getAreaSize().toString(), park.getRating().toString(), park.getParkType().name(), park.getLocation().getId().toString());

        listPage = AddOrEditNationalPark.update(this.driver, editPage, "testName", "25", "5", ParkType.WILDLIFE_SANCTUARY.name(), locations.get(0).getId().toString());
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, LIST_URL);
        if (listPage.assertItems(itemNum)) {
            ExamAssert.assertEquals("The updated park title is not as expected.", "testName", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(0).getText().trim());
        }

        SubmissionHelper.endTest();
    }

    @Order(8)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_edit_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-edit-mvc-10", 10);
        List<ParkLocation> locations = this.locationService.listAll();
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/national-parks/" + parks.get(itemNum - 1).getId())
                .param("name", "testName")
                .param("areaSize", "200.0")
                .param("rating", "3.3")
                .param("parkType", ParkType.WILDLIFE_SANCTUARY.name())
                .param("locationId", locations.getFirst().getId().toString());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        parks = this.nationalParkService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum, parks.size());
        ExamAssert.assertEquals("The updated park name is not as expected.", "testName", parks.get(itemNum - 1).getName());
        ExamAssert.assertEquals("The updated size is not as expected.", 200.0, parks.get(itemNum - 1).getAreaSize());
        ExamAssert.assertEquals("The updated rating is not as expected.", 3.3, parks.get(itemNum - 1).getRating());
        ExamAssert.assertEquals("The updated park type is not as expected.", ParkType.WILDLIFE_SANCTUARY.name(), parks.get(itemNum - 1).getParkType().name());
        ExamAssert.assertEquals("The updated park location is not as expected.", locations.getFirst().getId().toString(), parks.get(itemNum - 1).getLocation().getId().toString());

        request = MockMvcRequestBuilders
                .get("/national-parks/edit/" + parks.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(new ViewMatcher("form")));

        SubmissionHelper.endTest();
    }

    @Order(9)
    @Test
    public void test_delete_3pt() throws Exception {
        SubmissionHelper.startTest("test-delete-3", 3);
        List<NationalPark> items = this.nationalParkService.listAll();
        int itemNum = items.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getDeleteButtons().get(itemNum - 1).click();
        listPage.assertNoError();


        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        listPage = ItemsPage.to(this.driver);
        listPage.assertItems(itemNum - 1);

        SubmissionHelper.endTest();
    }

    @Order(10)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_delete_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-delete-mvc-2", 2);
        List<NationalPark> items = this.nationalParkService.listAll();
        int itemNum = items.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/national-parks/delete/" + items.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        items = this.nationalParkService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum - 1, items.size());

        SubmissionHelper.endTest();
    }

    @Order(11)
    @Test
    public void test_security_urls_10pt() {
        SubmissionHelper.startTest("test-security-urls-10", 10);
        List<NationalPark> parks = this.nationalParkService.listAll();
        String editUrl = "/national-parks/edit/" + parks.get(0).getId();

        ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);

        AbstractPage.get(this.driver, DEFAULT_URL);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);
        AbstractPage.get(this.driver, "/random");
        AbstractPage.assertRelativeUrl(this.driver, LOGIN_URL);

        LoginPage loginPage = LoginPage.openLogin(this.driver);
        LoginPage.doLogin(this.driver, loginPage, admin, admin);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);

        AbstractPage.get(this.driver, DEFAULT_URL);
        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);

        AbstractPage.get(this.driver, ADD_URL);
        AbstractPage.assertRelativeUrl(this.driver, ADD_URL);

        AbstractPage.get(this.driver, editUrl);
        AbstractPage.assertRelativeUrl(this.driver, editUrl);

        LoginPage.logout(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, "/");

        SubmissionHelper.endTest();
    }

    @Order(12)
    @Test
    public void test_security_buttons_10pt() {
        SubmissionHelper.startTest("test-security-buttons-10", 10);
        List<NationalPark> parks = this.nationalParkService.listAll();
        int itemNum = parks.size();

        ItemsPage NationalParksPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);
        NationalParksPage.assertButtons(0, 0, 0, 0);

        LoginPage loginPage1 = LoginPage.openLogin(this.driver);
        NationalParksPage = LoginPage.doLogin(this.driver, loginPage1, admin, admin);
        NationalParksPage.assertButtons(itemNum, itemNum, 1, 10);
        LoginPage.logout(this.driver);

        LoginPage loginPage2 = LoginPage.openLogin(this.driver);
        NationalParksPage = LoginPage.doLogin(this.driver, loginPage2, user, user);
        NationalParksPage.assertButtons(0, 0, 0, 0);
        LoginPage.logout(this.driver);
        SubmissionHelper.endTest();
    }

    @Order(13)
    @Test
    public void test_additional_functionality_3pt() throws Exception {
        SubmissionHelper.startTest("test-additional-functionality-3", 3);
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();

        ItemsPage listPage = null;
        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!DEFAULT_URL.equals(this.driver.getCurrentUrl())) {
            System.err.println("Reloading items page");
            listPage = ItemsPage.to(this.driver);
        }

        listPage.getCloseButtons().get(itemNum - 1).click();
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        listPage = ItemsPage.to(this.driver);
        ExamAssert.assertEquals("The close park text is not as expected.", "CLOSED", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(5).getText().trim());
        ExamAssert.assertEquals("The close park buttons are not as expected.", false, listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(6).getText().contains("Close"));

        SubmissionHelper.endTest();
    }

    @Order(14)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_additional_functionality_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-additional-functionality-mvc-2", 2);
        List<NationalPark> parks = this.nationalParkService.listAll();

        int itemNum = parks.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/national-parks/close/" + parks.get(0).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        parks = this.nationalParkService.listAll();
        ExamAssert.assertEquals("First park is now closed", true, parks.get(0).isClosed());

        SubmissionHelper.endTest();
    }

    private HtmlUnitDriver driver;
    private MockMvc mockMvc;

    private static String admin = "admin";
    private static String user = "user";

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.driver = new HtmlUnitDriver(true);
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }

    @AfterAll
    public static void finalizeAndSubmit() throws JsonProcessingException {
        CodeExtractor.submitSourcesAndLogs();
    }

    public static final String LIST_URL = "/national-parks?pageSize=20&pageNum=1";
    public static final String DEFAULT_URL = "/national-parks";
    public static final String BASE_DEFAULT_URL = "/?pageSize=20&pageNum=1";
    public static final String ADD_URL = "/national-parks/add";
    public static final String LOGIN_URL = "/login";

    static class ViewMatcher implements Matcher<String> {

        final String baseName;

        ViewMatcher(String baseName) {
            this.baseName = baseName;
        }

        @Override
        public boolean matches(Object o) {
            if (o instanceof String) {
                String s = (String) o;
                return s.startsWith(baseName);
            }
            return false;
        }

        @Override
        public void describeMismatch(Object o, Description description) {
        }

        @Override
        public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {
        }

        @Override
        public void describeTo(Description description) {
        }
    }
}
