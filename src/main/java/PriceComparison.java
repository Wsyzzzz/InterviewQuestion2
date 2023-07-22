import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;


public class PriceComparison extends TestBase {
    String searchedProduct1Name = null;
    String searchedProduct1URL = null;
    String firstUrlPrice = null;
    List<itemDetails> itemDetailsItems = new ArrayList<>();
    String secondUrlPrice = null;
    String searchedProductName = null;
    String searchedProduct2URL = null;
    double firstUrlPriceConverted;
    double secondUrlPriceConverted;
    public void firstURLExecution() {

        //URL 1 Visit
        driver.get(prop.getProperty("URL1"));

        //Search for keyword
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label=\"Search for anything\"]")));
        driver.findElement(By.xpath("//input[@aria-label=\"Search for anything\"]")).sendKeys(prop.getProperty("searchKeyword"));
        Select drpCategory = new Select(driver.findElement(By.name("_sacat")));
        drpCategory.selectByVisibleText("Cell Phones & Accessories");
        driver.findElement(By.id("gh-btn")).click();

        //List Devices and getting details of product matches with Searched Keyword
        List<WebElement> allDevices = driver.findElements(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span[1]"));
        for(int i=3;i<=allDevices.size();i++){
            WebElement productNameElement = driver.findElement(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]"));
            searchedProduct1Name = productNameElement.getText();
            String subTitle = driver.findElement(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/ancestor::div/div[@class=\"s-item__subtitle\"]/span")).getText();
            if(!subTitle.equalsIgnoreCase("Parts Only")){
                if(driver.findElements(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span")).size() == 2){
                    firstUrlPrice=driver.findElement(By.xpath("((//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span)[2]")).getText();
                }else if(driver.findElements(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span")).size() == 3) {
                    firstUrlPrice=driver.findElement(By.xpath("((//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span)[3]")).getText();
                }else {
                    firstUrlPrice=driver.findElement(By.xpath("(//span[contains(text(),'" + prop.getProperty("searchKeyword") + "')])[" + i + "]/ancestor::div/div[contains(@class,\"s-item__details\")]/div/span[@class=\"s-item__price\"]/span")).getText();
                }
                assert firstUrlPrice != null;
                firstUrlPriceConverted = Double.parseDouble(firstUrlPrice.replace("RM","").replace(",","").trim());
                searchedProduct1URL = driver.findElement(By.xpath("(//span[contains(text(),'"+prop.getProperty("searchKeyword")+"')])["+i+"]/parent::div/parent::a")).getAttribute("href");
                itemDetailsItems.add(new itemDetails(prop.getProperty("URL1"),searchedProduct1Name,firstUrlPriceConverted,searchedProduct1URL));
            }
        }
        for (itemDetails item : itemDetailsItems) {
            System.out.println("Website URL: "+item.getWebsiteURL());
            System.out.println("Product Name: "+item.getProdName());
            System.out.println("Product Price: "+item.getProdPrice());
            System.out.println("Product Link: "+item.getProdLink());
            System.out.println("=============================================================================");
        }
    }



    public void secondURLExecution() throws InterruptedException {

        //URL 2 Visit
        driver.get(prop.getProperty("URL2"));
        Thread.sleep(2000);

        //English - Language Selection
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"English\"]")));
        driver.findElement(By.xpath("//button[text()=\"English\"]")).click();
        Thread.sleep(2000);

        //Shadow Root Switch to close Offer Popup
        WebElement shadowElement = (WebElement) ((JavascriptExecutor) driver)
                .executeScript("return document.querySelector('#main > div > div:nth-child(4) > div > div > shopee-banner-popup-stateful').shadowRoot.querySelector('.home-popup__close-button')");
        shadowElement.click();

        //Login to the account
        driver.findElement(By.linkText("Login")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("loginKey")));
        driver.findElement(By.name("loginKey")).sendKeys(prop.getProperty("email"));
        driver.findElement(By.name("password")).sendKeys(prop.getProperty("password"));
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[text()=\"Log In\"]")).click();
        Thread.sleep(2000);

        //Finding another offer popup after login
        try {
            if (driver.findElements(By.cssSelector("#main > div > div:nth-child(4) > div > div > shopee-banner-popup-stateful")).size() > 0) {
                WebElement shadowElement1 = (WebElement) ((JavascriptExecutor) driver)
                        .executeScript("return document.querySelector('#main > div > div:nth-child(4) > div > div > shopee-banner-popup-stateful').shadowRoot.querySelector('.home-popup__close-button')");
                shadowElement1.click();
            }
        } catch (Exception ignored) {
        }

        //Search Input
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class=\"shopee-searchbar-input__input\"]")));
        driver.findElement(By.xpath("//input[@class=\"shopee-searchbar-input__input\"]")).sendKeys(prop.getProperty("searchKeyword"));
        driver.findElement(By.xpath("//button[contains(@class,\"shopee-searchbar__search-button\")]")).click();

        //Get All Product Results for Searched Keyword
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(text(),\""+prop.getProperty("searchKeyword")+"\")])[1]")));
        List<WebElement> allDevicesAnother = driver.findElements(By.xpath("(//div[contains(text(),\""+prop.getProperty("searchKeyword")+"\")])"));
        for(int i=1;i<=allDevicesAnother.size();i++) {
            WebElement prodNameElement =  driver.findElement(By.xpath("(//div[contains(text(),\""+prop.getProperty("searchKeyword")+"\")])["+i+"]"));
            searchedProductName = prodNameElement.getText();
            searchedProduct2URL = driver.findElement(By.xpath("(//a[@data-sqe=\"link\"])["+i+"]")).getAttribute("href");
            for (int j = i; j <= allDevicesAnother.size(); j++) {
                if (j % 2 != 0) {
                    secondUrlPrice = driver.findElement(By.xpath("(//span[@aria-label=\"current price\"]/following-sibling::span[2])["+j+"]")).getText();
                    assert secondUrlPrice != null;
                    secondUrlPriceConverted = Double.parseDouble(secondUrlPrice.replace(",","").trim());
                    itemDetailsItems.add(new itemDetails(prop.getProperty("URL2"),searchedProductName,secondUrlPriceConverted,searchedProduct2URL));
                }
            }
        }
        for (itemDetails item : itemDetailsItems) {
            System.out.println("Website URL: "+item.getWebsiteURL());
            System.out.println("Product Name: "+item.getProdName());
            System.out.println("Product Price: "+item.getProdPrice());
            System.out.println("Product Link: "+item.getProdLink());
            System.out.println("=============================================================================");
        }
    }
        public void afterMethod(){
        driver.quit();
        }


}

