import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class PriceComparison extends TestBase {
    public void firstURLExecution() {

        //URL 1 Visit
        driver.get(prop.getProperty("URL1"));

        //Search for keyword
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label=\"Search for anything\"]")));
        driver.findElement(By.xpath("//input[@aria-label=\"Search for anything\"]")).sendKeys(prop.getProperty("searchKeyword"));
        Select drpCategory = new Select(driver.findElement(By.name("_sacat")));
        drpCategory.selectByVisibleText("Cell Phones & Accessories");
        driver.findElement(By.id("gh-btn")).click();
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
    }
        public void afterMethod(){
        driver.quit();
        }


}

