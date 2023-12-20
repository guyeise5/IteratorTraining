package testing;

public class TestingDataProvider {
    @org.testng.annotations.DataProvider(name = "tf")
    public static Object[][] tf() {
        return new Object[][]{{true},{false}};
    }
}
