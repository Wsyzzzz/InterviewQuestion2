public class PriceComparisonTest extends TestBase {

    public static void main(String[] args) {
        PriceComparison priceComparison = new PriceComparison();
        try {
            setUp();
            priceComparison.firstURLExecution();
            priceComparison.secondURLExecution();
            priceComparison.afterMethod();

        }catch (Exception e){
            e.printStackTrace();
            priceComparison.afterMethod();
        }
    }

}
