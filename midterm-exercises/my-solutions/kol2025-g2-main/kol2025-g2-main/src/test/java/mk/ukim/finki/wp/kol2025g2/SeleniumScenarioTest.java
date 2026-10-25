package mk.ukim.finki.wp.kol2025g2;

import com.fasterxml.jackson.core.JsonProcessingException;
import mk.ukim.finki.wp.exam.util.CodeExtractor;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import mk.ukim.finki.wp.exam.util.SubmissionHelper;
import mk.ukim.finki.wp.kol2025g2.model.SkiResort;
import mk.ukim.finki.wp.kol2025g2.model.SkiSlope;
import mk.ukim.finki.wp.kol2025g2.model.SlopeDifficulty;
import mk.ukim.finki.wp.kol2025g2.service.SkiResortService;
import mk.ukim.finki.wp.kol2025g2.service.SkiSlopeService;
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

    //TODO: CHANGE THE VALUE OF THE SubmissionHelper.index WITH YOUR INDEX NUMBER!!!
    static {
        SubmissionHelper.exam = "2025-k2-g2";
        SubmissionHelper.index = "233105";
    }

    @Autowired
    SkiResortService skiResortService;

    @Autowired
    SkiSlopeService slopeService;

    @Order(1)
    @Test
    public void test_list_10pt() {
        SubmissionHelper.startTest("test-list-10", 10);
        List<SkiSlope> items = this.slopeService.listAll();
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

        listPage.filter("", "", "", "");
        listPage.assertItems(10);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("9", "", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "70", "", "");
        listPage.assertItems(5);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "", SlopeDifficulty.RED.name(), "");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "", "", "2");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("Slope 10", "20", "", "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("1", "", SlopeDifficulty.BLUE.name(), "");
        listPage.assertItems(0);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("Slope 6", "", "", "3");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "30", SlopeDifficulty.RED.name(), "");
        listPage.assertItems(2);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "30", "", "3");
        listPage.assertItems(3);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "", SlopeDifficulty.RED.name(), "2");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("1", "20", SlopeDifficulty.BLACK.name(), "");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("6", "40", "", "3");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("1", "", SlopeDifficulty.RED.name(), "2");
        listPage.assertItems(0);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("", "20", SlopeDifficulty.RED.name(), "2");
        listPage.assertItems(1);

        listPage = ItemsPage.to(this.driver);

        listPage.filter("Slope 2", "30", SlopeDifficulty.RED.name(), "2");
        listPage.assertItems(0);

        SubmissionHelper.endTest();
    }

    @Order(4)
    @Test
    public void test_filter_service_5pt() {
        SubmissionHelper.startTest("test-filter-service-5", 5);

        ExamAssert.assertEquals("without filter", 10, this.slopeService.findPage(null, null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name only", 1, this.slopeService.findPage("9", null, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by length only", 5, this.slopeService.findPage(null, 70, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by difficulty only", 3, this.slopeService.findPage(null, null, SlopeDifficulty.RED, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by ski resort only", 3, this.slopeService.findPage(null, null, null, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and length", 1, this.slopeService.findPage("Slope 10", 20, null, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and difficulty", 0, this.slopeService.findPage("1", null, SlopeDifficulty.BLUE, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name and ski resort", 1, this.slopeService.findPage("Slope 6", null, null, 3L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by length and difficulty", 2, this.slopeService.findPage(null, 30, SlopeDifficulty.RED, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by length and ski resort", 3, this.slopeService.findPage(null, 30, null, 3L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by difficulty and ski resort", 1, this.slopeService.findPage(null, null, SlopeDifficulty.RED, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, length, and difficulty", 1, this.slopeService.findPage("1", 20, SlopeDifficulty.BLACK, null, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, length, and ski resort", 1, this.slopeService.findPage("6", 40, null, 3L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, difficulty, and ski resort", 0, this.slopeService.findPage("1", null, SlopeDifficulty.RED, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by length, difficulty, and ski resort", 1, this.slopeService.findPage(null, 20, SlopeDifficulty.RED, 2L, 0, 20).getNumberOfElements());
        ExamAssert.assertEquals("filter by name, length, difficulty, and ski resort", 0, this.slopeService.findPage("Slope 2", 30, SlopeDifficulty.RED, 2L, 0, 20).getNumberOfElements());

        SubmissionHelper.endTest();
    }

    @Order(5)
    @Test
    public void test_create_10pt() {
        SubmissionHelper.startTest("test-create-10", 10);
        List<SkiResort> resorts = this.skiResortService.listAll();
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();
        ItemsPage listPage = null;

        try {
            LoginPage loginPage = LoginPage.openLogin(this.driver);
            listPage = LoginPage.doLogin(this.driver, loginPage, admin, admin);
        } catch (Exception e) {
        }


        listPage = AddOrEditSkiSlope.add(this.driver, ADD_URL, "testName", "25", SlopeDifficulty.RED.name(), resorts.get(0).getId().toString());
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
        List<SkiResort> resorts = this.skiResortService.listAll();
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();

        MockHttpServletRequestBuilder addRequest = MockMvcRequestBuilders
                .post("/ski-slopes")
                .param("name", "testName")
                .param("length", "20")
                .param("difficulty", SlopeDifficulty.RED.name())
                .param("skiResort", resorts.get(0).getId().toString());

        this.mockMvc.perform(addRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        slopes = this.slopeService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum + 1, slopes.size());

        addRequest = MockMvcRequestBuilders
                .get("/ski-slopes/add");

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
        List<SkiResort> resorts = this.skiResortService.listAll();
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();

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

        AddOrEditSkiSlope editPage = AddOrEditSkiSlope.getEditPage(this.driver, listPage.getEditButtons().get(itemNum - 1));
        SkiSlope slope = slopes.get(itemNum - 1);
        editPage.assertEditFormIsPrefilled(slope.getName(), slope.getLength().toString(), slope.getDifficulty().name(), slope.getSkiResort().getId().toString());

        listPage = AddOrEditSkiSlope.update(this.driver, editPage, "testName", "25", SlopeDifficulty.RED.name(), resorts.get(0).getId().toString());
        listPage.assertNoError();

        AbstractPage.assertRelativeUrl(this.driver, DEFAULT_URL);
        AbstractPage.get(this.driver, LIST_URL);
        if (listPage.assertItems(itemNum)) {
            ExamAssert.assertEquals("The updated slope title is not as expected.", "testName", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(0).getText().trim());
        }

        SubmissionHelper.endTest();
    }

    @Order(8)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_edit_mvc_10pt() throws Exception {
        SubmissionHelper.startTest("test-edit-mvc-10", 10);
        List<SkiResort> resorts = this.skiResortService.listAll();
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/ski-slopes/" + slopes.get(itemNum - 1).getId())
                .param("name", "testName")
                .param("length", "25")
                .param("difficulty", SlopeDifficulty.RED.name())
                .param("skiResort", resorts.get(0).getId().toString());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        slopes = this.slopeService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum, slopes.size());
        ExamAssert.assertEquals("The updated slope name is not as expected.", "testName", slopes.get(itemNum - 1).getName());
        ExamAssert.assertEquals("The updated length is not as expected.", Integer.valueOf(25), slopes.get(itemNum - 1).getLength());
        ExamAssert.assertEquals("The updated slope difficulty is not as expected.", SlopeDifficulty.RED.name(), slopes.get(itemNum - 1).getDifficulty().name());
        ExamAssert.assertEquals("The updated slope ski center is not as expected.", resorts.get(0).getId().toString(), slopes.get(itemNum - 1).getSkiResort().getId().toString());

        request = MockMvcRequestBuilders
                .get("/ski-slopes/edit/" + slopes.get(itemNum - 1).getId());

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
        List<SkiSlope> items = this.slopeService.listAll();
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
        List<SkiSlope> items = this.slopeService.listAll();
        int itemNum = items.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/ski-slopes/delete/" + items.get(itemNum - 1).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        items = this.slopeService.listAll();
        ExamAssert.assertEquals("Number of items", itemNum - 1, items.size());

        SubmissionHelper.endTest();
    }

    @Order(11)
    @Test
    public void test_security_urls_10pt() {
        SubmissionHelper.startTest("test-security-urls-10", 10);
        List<SkiSlope> slopes = this.slopeService.listAll();
        String editUrl = "/ski-slopes/edit/" + slopes.get(0).getId();

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
        List<SkiSlope> slopes = this.slopeService.listAll();
        int itemNum = slopes.size();

        ItemsPage skiSlopesPage = ItemsPage.to(this.driver);
        AbstractPage.assertRelativeUrl(this.driver, BASE_DEFAULT_URL);
        skiSlopesPage.assertButtons(0, 0, 0, 0);

        LoginPage loginPage1 = LoginPage.openLogin(this.driver);
        skiSlopesPage = LoginPage.doLogin(this.driver, loginPage1, admin, admin);
        skiSlopesPage.assertButtons(itemNum, itemNum, 1, 10);
        LoginPage.logout(this.driver);

        LoginPage loginPage2 = LoginPage.openLogin(this.driver);
        skiSlopesPage = LoginPage.doLogin(this.driver, loginPage2, user, user);
        skiSlopesPage.assertButtons(0, 0, 0, 0);
        LoginPage.logout(this.driver);
        SubmissionHelper.endTest();
    }

    @Order(13)
    @Test
    public void test_close_slope_3pt() throws Exception {
        SubmissionHelper.startTest("test-close-slope-3", 3);
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();

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
        ExamAssert.assertEquals("The close slope buttons are not as expected.", "CLOSED", listPage.getRows().get(itemNum - 1).findElements(By.tagName("td")).get(4).getText().trim());

        SubmissionHelper.endTest();
    }

    @Order(14)
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void test_close_mvc_2pt() throws Exception {
        SubmissionHelper.startTest("test-close-mvc-2", 2);
        List<SkiSlope> slopes = this.slopeService.listAll();

        int itemNum = slopes.size();

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/ski-slopes/close/" + slopes.get(0).getId());

        this.mockMvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(DEFAULT_URL));

        slopes = this.slopeService.listAll();
        ExamAssert.assertEquals("First slope is now closed", true, slopes.get(0).isClosed());

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

    public static final String LIST_URL = "/ski-slopes?pageSize=20&pageNum=1";
    public static final String DEFAULT_URL = "/ski-slopes";
    public static final String BASE_DEFAULT_URL = "/?pageSize=20&pageNum=1";
    public static final String ADD_URL = "/ski-slopes/add";
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
