package mk.ukim.finki.wp.kol2025g3;

import mk.ukim.finki.wp.exam.util.ExamAssert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AddOrEditExpense extends AbstractPage {
    private WebElement title;
    private WebElement dateCreated;
    private WebElement daysToExpire;
    private WebElement expenseCategory;
    private WebElement amount;
    private WebElement vendor;
    private WebElement submit;

    public AddOrEditExpense(WebDriver driver) {
        super(driver);
    }

    public static ItemsPage add(WebDriver driver, String addPath, String title, String dateCreated, String amount, String daysToExpire, String expenseCategory, String vendor) {
        get(driver, addPath);
        assertRelativeUrl(driver, addPath);

        AddOrEditExpense addOrEditExpense = PageFactory.initElements(driver, AddOrEditExpense.class);
        addOrEditExpense.assertNoError();
        addOrEditExpense.title.sendKeys(title);
        addOrEditExpense.dateCreated.sendKeys(dateCreated);
        addOrEditExpense.daysToExpire.sendKeys(daysToExpire);
        addOrEditExpense.amount.sendKeys(amount);

        Select selectExpenseCategory = new Select(addOrEditExpense.expenseCategory);
        selectExpenseCategory.selectByValue(expenseCategory);

        Select selectVendor = new Select(addOrEditExpense.vendor);
        selectVendor.selectByValue(vendor);

        addOrEditExpense.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public static AddOrEditExpense getEditPage(WebDriver driver, WebElement editButton) {
        String href = editButton.getAttribute("href");
        System.out.println(href);
        editButton.click();
        assertAbsoluteUrl(driver, href);

        return PageFactory.initElements(driver, AddOrEditExpense.class);
    }

    public static ItemsPage update(WebDriver driver, AddOrEditExpense addOrEditExpense, String title, String dateCreated, String amount, String daysToExpire, String expenseCategory, String vendor) {
        addOrEditExpense.title.clear();
        addOrEditExpense.title.sendKeys(title);
        addOrEditExpense.dateCreated.clear();
        addOrEditExpense.dateCreated.sendKeys(dateCreated);
        addOrEditExpense.daysToExpire.clear();
        addOrEditExpense.daysToExpire.sendKeys(daysToExpire);
        addOrEditExpense.amount.clear();
        addOrEditExpense.amount.sendKeys(amount);

        Select selectExpenseCategory = new Select(addOrEditExpense.expenseCategory);
        selectExpenseCategory.selectByValue(expenseCategory);

        Select selectVendor = new Select(addOrEditExpense.vendor);
        selectVendor.selectByValue(vendor);

        addOrEditExpense.submit.click();
        return PageFactory.initElements(driver, ItemsPage.class);
    }

    public void assertEditFormIsPrefilled(String title, String dateCreated, String amount, String daysToExpire, String expenseCategory, String vendor) {
        ExamAssert.assertEquals("Title is not prefilled", title, this.title.getAttribute("value"));
        ExamAssert.assertEquals("Date created is not prefilled", dateCreated, this.dateCreated.getAttribute("value"));
        ExamAssert.assertEquals("Amount is not prefilled", amount, this.amount.getAttribute("value"));
        ExamAssert.assertEquals("Days to expire is not prefilled", daysToExpire, this.daysToExpire.getAttribute("value"));
        boolean checkExpenseCategory = ExamAssert.assertNotEquals("Expense category is not preselected", 0, new Select(this.expenseCategory).getAllSelectedOptions().size());
        if (checkExpenseCategory) {
            ExamAssert.assertEquals("Expense category is not preselected", expenseCategory, new Select(this.expenseCategory).getFirstSelectedOption().getAttribute("value"));
        }

        boolean checkVendor = ExamAssert.assertNotEquals("Vendor is not preselected", 0, new Select(this.vendor).getAllSelectedOptions().size());
        if (checkVendor) {
            ExamAssert.assertEquals("Vendor is not preselected", vendor, new Select(this.vendor).getFirstSelectedOption().getAttribute("value"));
        }
    }
}