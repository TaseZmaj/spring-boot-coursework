package mk.ukim.finki.wp.jan2025g1;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditArchaeologicalSite extends AbstractPage {
    private WebElement name;
    private WebElement areaSize;
    private WebElement rating;
    private WebElement period;
    private WebElement location;
    private WebElement submit;

    public AddOrEditArchaeologicalSite(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String areaSize, String rating, String period, String location) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditArchaeologicalSite addOrEditArchaeologicalSite = PageFactory.initElements(driver, AddOrEditArchaeologicalSite.class);
        addOrEditArchaeologicalSite.assertNoError();
        addOrEditArchaeologicalSite.name.sendKeys(name);
        addOrEditArchaeologicalSite.areaSize.sendKeys(areaSize);
        addOrEditArchaeologicalSite.rating.sendKeys(rating);

        Select selectPeriod = new Select(addOrEditArchaeologicalSite.period);
        selectPeriod.selectByValue(period);

        Select selectLocation = new Select(addOrEditArchaeologicalSite.location);
        selectLocation.selectByValue(location);

        addOrEditArchaeologicalSite.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static AddOrEditArchaeologicalSite getEditPage(WebDriver driver, WebElement editButton) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        return PageFactory.initElements(driver, AddOrEditArchaeologicalSite.class);
    }

    public static ItemsPage update(WebDriver driver, AddOrEditArchaeologicalSite addOrEditArchaeologicalSite, String name, String areaSize, String rating, String period, String location) {
        addOrEditArchaeologicalSite.name.clear();
        addOrEditArchaeologicalSite.name.sendKeys(name);
        addOrEditArchaeologicalSite.areaSize.clear();
        addOrEditArchaeologicalSite.areaSize.sendKeys(areaSize);
        addOrEditArchaeologicalSite.rating.clear();
        addOrEditArchaeologicalSite.rating.sendKeys(rating);

        Select selectPeriod = new Select(addOrEditArchaeologicalSite.period);
        selectPeriod.selectByValue(period);

        Select selectLocation = new Select(addOrEditArchaeologicalSite.location);
        selectLocation.selectByValue(location);

        addOrEditArchaeologicalSite.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertEditFormIsPrefilled(String name, String areaSize, String rating, String period, String location) {
        ExamAssert.assertEquals("Name is not prefilled", name, this.name.getAttribute("value"));
        ExamAssert.assertEquals("Area size is not prefilled", areaSize, this.areaSize.getAttribute("value"));
        ExamAssert.assertEquals("Rating is not prefilled", rating, this.rating.getAttribute("value"));

        boolean checkPeriod = ExamAssert.assertNotEquals("Period is not preselected", 0, new Select(this.period).getAllSelectedOptions().size());
        if (checkPeriod) {
            ExamAssert.assertEquals("Period is not preselected", period, new Select(this.period).getFirstSelectedOption().getAttribute("value"));
        }

        boolean checkLocation = ExamAssert.assertNotEquals("Location is not preselected", 0, new Select(this.location).getAllSelectedOptions().size());
        if (checkLocation) {
            ExamAssert.assertEquals("Location is not preselected", location, new Select(this.location).getFirstSelectedOption().getAttribute("value"));
        }
    }
}
