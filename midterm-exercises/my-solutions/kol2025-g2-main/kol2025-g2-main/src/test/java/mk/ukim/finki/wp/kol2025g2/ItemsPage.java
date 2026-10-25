package mk.ukim.finki.wp.kol2025g2;

import lombok.Getter;
import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

@Getter
public class ItemsPage extends AbstractPage {

    private WebElement name;
    private WebElement length;

    private WebElement difficulty;

    private WebElement skiResort;

    private WebElement filter;

    @FindBy(css = "tr[class=item]")
    private List<WebElement> rows;

    @FindBy(css = ".delete-item")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-item")
    private List<WebElement> editButtons;

    @FindBy(css = ".close-item")
    private List<WebElement> closeButtons;

    @FindBy(css = ".add-item")
    private List<WebElement> addButton;

    public ItemsPage(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage to(WebDriver driver) {
        get(driver, "/?pageSize=20&pageNum=1");
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static ItemsPage to(WebDriver driver, int pageSize, int pageNum) {
        get(driver, "/?pageSize=" + pageSize + "&pageNum=" + pageNum);
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public ItemsPage filter(String name, String length, String difficulty, String skiResort) {
        System.out.println(driver.getCurrentUrl());
        this.name.sendKeys(name);
        this.length.sendKeys(length);
        Select selectSkiSlopeDifficulty = new Select(this.difficulty);
        selectSkiSlopeDifficulty.selectByValue(difficulty);
        Select selectSkiResort = new Select(this.skiResort);
        selectSkiResort.selectByValue(skiResort);
        this.filter.click();
        String url = "?name=" + name + "&length=" + length + "&difficulty=" + difficulty + "&skiResort=" + skiResort;
        if (driver.getCurrentUrl().contains("/ski-slopes")) {
            url = "/ski-slopes" + url;
        } else {
            url = "/" + url;
        }
        assertRelativeUrl(this.driver, url.replaceAll(" ", "+"));
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertButtons(int deleteButtonsCount, int editButtonsCount, int addButtonsCount, int closeButtonsCount) {
        ExamAssert.assertEquals("delete btn count", deleteButtonsCount, this.getDeleteButtons().size());
        ExamAssert.assertEquals("edit btn count", editButtonsCount, this.getEditButtons().size());
        ExamAssert.assertEquals("add btn count", addButtonsCount, this.getAddButton().size());
        ExamAssert.assertEquals("close btn count", closeButtonsCount, this.getCloseButtons().size());
    }

    public boolean assertItems(int expectedItemsNumber) {
        return ExamAssert.assertEquals("Number of items", expectedItemsNumber, this.getRows().size());
    }
}
