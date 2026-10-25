package mk.ukim.finki.wp.jan2025g2;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditNationalPark extends AbstractPage {
    private WebElement name;
    private WebElement areaSize;
    private WebElement rating;
    private WebElement parkType;
    private WebElement location;
    private WebElement submit;

    public AddOrEditNationalPark(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String name, String areaSize, String rating, String parkType, String location) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditNationalPark addOrEditNationalPark = PageFactory.initElements(driver, AddOrEditNationalPark.class);
        addOrEditNationalPark.assertNoError();
        addOrEditNationalPark.name.sendKeys(name);
        addOrEditNationalPark.areaSize.sendKeys(areaSize);
        addOrEditNationalPark.rating.sendKeys(rating);

        Select selectParkType = new Select(addOrEditNationalPark.parkType);
        selectParkType.selectByValue(parkType);

        Select selectLocation = new Select(addOrEditNationalPark.location);
        selectLocation.selectByValue(location);

        addOrEditNationalPark.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static AddOrEditNationalPark getEditPage(WebDriver driver, WebElement editButton) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        return PageFactory.initElements(driver, AddOrEditNationalPark.class);
    }

    public static ItemsPage update(WebDriver driver, AddOrEditNationalPark addOrEditNationalPark, String name, String areaSize, String rating, String parkType, String location) {
        addOrEditNationalPark.name.clear();
        addOrEditNationalPark.name.sendKeys(name);
        addOrEditNationalPark.areaSize.clear();
        addOrEditNationalPark.areaSize.sendKeys(areaSize);
        addOrEditNationalPark.rating.clear();
        addOrEditNationalPark.rating.sendKeys(rating);

        Select selectParkType = new Select(addOrEditNationalPark.parkType);
        selectParkType.selectByValue(parkType);

        Select selectLocation = new Select(addOrEditNationalPark.location);
        selectLocation.selectByValue(location);

        addOrEditNationalPark.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertEditFormIsPrefilled(String name, String areaSize, String rating, String parkType, String location) {
        ExamAssert.assertEquals("Name is not prefilled", name, this.name.getAttribute("value"));
        ExamAssert.assertEquals("Area size is not prefilled", areaSize, this.areaSize.getAttribute("value"));
        ExamAssert.assertEquals("Rating is not prefilled", rating, this.rating.getAttribute("value"));

        boolean checkParkType = ExamAssert.assertNotEquals("Park type is not preselected", 0, new Select(this.parkType).getAllSelectedOptions().size());
        if (checkParkType) {
            ExamAssert.assertEquals("Park type is not preselected", parkType, new Select(this.parkType).getFirstSelectedOption().getAttribute("value"));
        }

        boolean checkLocation = ExamAssert.assertNotEquals("Location is not preselected", 0, new Select(this.location).getAllSelectedOptions().size());
        if (checkLocation) {
            ExamAssert.assertEquals("Location is not preselected", location, new Select(this.location).getFirstSelectedOption().getAttribute("value"));
        }
    }
}
