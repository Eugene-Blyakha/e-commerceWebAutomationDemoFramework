package org.selenium.pom.tests;


import org.selenium.pom.base.BaseTest;
import org.selenium.pom.objects.BillingAddress;
import org.selenium.pom.objects.Product;
import org.selenium.pom.objects.User;
import org.selenium.pom.pages.CartPage;
import org.selenium.pom.pages.CheckoutPage;
import org.selenium.pom.pages.HomePage;
import org.selenium.pom.pages.StorePage;
import org.selenium.pom.utils.ConfigLoader;
import org.selenium.pom.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class MyFirstTest extends BaseTest {

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {

        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/Drivers/chromedriver");

        //filling in the Billing Address form by using getters and setters method (POJO class)
        /*BillingAddress billingAddress = new BillingAddress().
                setFirstName("demo").
                setLastName("user").
                setAddressLineOne("San Francisco").
                setCity("San Francisco").
                setPostalCode("94188").
                setEmail("JohnGreen@gmail.com");*/

        //filling in the billing address using parameterized constructor
        /*BillingAddress billingAddress = new BillingAddress("demo", "user", "San Francisco",
                "San Francisco", "94188", "JohnGreen@gmail.com");*/
        //this statement will read values in Json file and set them to instance variables values in BillingAddress class
        //in other words we are Parsing Json object and converting it to Java object and use it in test case

        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        HomePage homePage = new HomePage(driver).load();
        StorePage storePage = homePage.clickStoreMenuLink();
        Thread.sleep(2000);
        storePage.
                enterTextInSearchFld(searchFor).
                clickSearchBtn();
        Thread.sleep(2000);
        Assert.assertEquals(storePage.getText(), "Search results: “Blue”");
        storePage.clickAddToCartBtn(product.getName());
        Thread.sleep(2000);
        CartPage cartPage = storePage.clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkoutPage = cartPage.checkout().
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Thread.sleep(2000);
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

        driver.quit();

    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws InterruptedException, IOException {
        String searchFor = "Blue";
        BillingAddress billingAddress = JacksonUtils.deserializeJson("myBillingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
//      User user = new User("demouser2", "demopwd");
        User user = new User(ConfigLoader.getInstance().getUsername(),
                ConfigLoader.getInstance().getPassword());

        HomePage homePage = new HomePage(driver).load();
        StorePage storePage = homePage.clickStoreMenuLink();
        Thread.sleep(2000);
        storePage.
                enterTextInSearchFld(searchFor).
                clickSearchBtn();
        Thread.sleep(2000);
        Assert.assertEquals(storePage.getText(), "Search results: “Blue”");
        storePage.clickAddToCartBtn(product.getName());
        Thread.sleep(2000);
        CartPage cartPage = storePage.clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());
        CheckoutPage checkoutPage = cartPage.checkout();
        checkoutPage.clickHereToLoginLink();
        Thread.sleep(2000);

        checkoutPage.login(user).
                setBillingAddress(billingAddress).
                selectDirectBankTransfer().
                placeOrder();
        Thread.sleep(2000);
        Assert.assertEquals(checkoutPage.getNotice(), "Thank you. Your order has been received.");

        driver.quit();

    }

}
