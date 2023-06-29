package org.selenium.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.base.BasePage;

public class StorePage extends BasePage {

    private By searchFld = By.cssSelector("#woocommerce-product-search-field-0");
    private By searchBtn = By.xpath("//button[@value='Search']");
    private By text = By.xpath("//h1[@class='woocommerce-products-header__title page-title']");
    private By viewCartLink = By.xpath("//a[@title='View cart']");

    public StorePage(WebDriver driver) {
        super(driver);
    }

    public StorePage enterTextInSearchFld(String txt){
        driver.findElement(searchFld).sendKeys(txt);
        return this;
    }

    public StorePage clickSearchBtn(){
        driver.findElement(searchBtn).click();
        return this;
    }

    public String getText(){
        return driver.findElement(text).getText();
    }

    private By getAddToCartBtnElement(String productName){
        return By.cssSelector("a[aria-label='Add “"+ productName +"” to your cart']");
    }

    public StorePage clickAddToCartBtn(String productName){
        By addToCartBtn = getAddToCartBtnElement(productName);
        driver.findElement(addToCartBtn).click();
        return this;
    }

    public CartPage clickViewCart(){
        driver.findElement(viewCartLink).click();
        return new CartPage(driver);
    }
}
