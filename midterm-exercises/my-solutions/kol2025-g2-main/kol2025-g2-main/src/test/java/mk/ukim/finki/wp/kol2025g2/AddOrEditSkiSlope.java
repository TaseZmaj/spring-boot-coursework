package mk.ukim.finki.wp.kol2025g2;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditSkiSlope extends AbstractPage {
    private WebElement name;
    private WebElement length;
    private WebElement difficulty;
    private WebElement skiResort;
    private WebElement submit;

    public AddOrEditSkiSlope(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String length, String difficulty, String skiResort) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditSkiSlope addOrEditSkiSlope = PageFactory.initElements(driver, AddOrEditSkiSlope.class);
        addOrEditSkiSlope.assertNoError();
        addOrEditSkiSlope.name.sendKeys(name);
        addOrEditSkiSlope.length.sendKeys(length);

        Select selectSkiSlopeDifficulty = new Select(addOrEditSkiSlope.difficulty);
        selectSkiSlopeDifficulty.selectByValue(difficulty);

        Select selectSkiResort = new Select(addOrEditSkiSlope.skiResort);
        selectSkiResort.selectByValue(skiResort);

        addOrEditSkiSlope.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static AddOrEditSkiSlope getEditPage(WebDriver driver, WebElement editButton) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        return PageFactory.initElements(driver, AddOrEditSkiSlope.class);
    }

    public static ItemsPage update(WebDriver driver, AddOrEditSkiSlope addOrEditSkiSlope, String name, String length, String difficulty, String skiResort) {
        addOrEditSkiSlope.name.clear();
        addOrEditSkiSlope.name.sendKeys(name);
        addOrEditSkiSlope.length.clear();
        addOrEditSkiSlope.length.sendKeys(length);

        Select selectSkiSlopeDifficulty = new Select(addOrEditSkiSlope.difficulty);
        selectSkiSlopeDifficulty.selectByValue(difficulty);

        Select selectSkiResort = new Select(addOrEditSkiSlope.skiResort);
        selectSkiResort.selectByValue(skiResort);

        addOrEditSkiSlope.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertEditFormIsPrefilled(String name, String length, String difficulty, String skiResort) {
        ExamAssert.assertEquals("Name is not prefilled", name, this.name.getAttribute("value"));
        ExamAssert.assertEquals("Length created is not prefilled", length, this.length.getAttribute("value"));
        boolean checkExpenseCategory = ExamAssert.assertNotEquals("Ski slope difficulty is not preselected", 0, new Select(this.difficulty).getAllSelectedOptions().size());
        if (checkExpenseCategory) {
            ExamAssert.assertEquals("Ski slope difficulty is not preselected", difficulty, new Select(this.difficulty).getFirstSelectedOption().getAttribute("value"));
        }

        boolean checkVendor = ExamAssert.assertNotEquals("Ski resort is not preselected", 0, new Select(this.skiResort).getAllSelectedOptions().size());
        if (checkVendor) {
            ExamAssert.assertEquals("Ski resort is not preselected", skiResort, new Select(this.skiResort).getFirstSelectedOption().getAttribute("value"));
        }
    }
}