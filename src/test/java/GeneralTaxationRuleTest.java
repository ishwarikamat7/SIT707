import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertTrue;

public class GeneralTaxationRuleTest {

    @BeforeAll
    public static void setup() {
        System.out.println("Before in GeneraTaxationRule");
    }

    public static Object[][] getIncomeDetails() {
        List<Integer> incomes = new ArrayList<>();
        incomes.add(15000);
        incomes.add(35000);
        incomes.add(90000);
        incomes.add(140000);
        incomes.add(250000);
        List<Integer> ages = new ArrayList<>();
        ages.add(17);
        ages.add(30);
        List<Boolean> isResident = new ArrayList<>();
        isResident.add(true);
        isResident.add(false);
        List<Boolean> TBRLevy = new ArrayList<>();
        TBRLevy.add(true);
        TBRLevy.add(false);
        List<Boolean> LILevy = new ArrayList<>();
        LILevy.add(true);
        LILevy.add(false);
        List<Double> expectedTax = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 3528.0, 3528.0, 3528.0, 3528.0, 3192.0, 3192.0, 3192.0, 3192.0, 3035.97, 3528.0, 3035.97,
                3528.0, 2746.83, 3192.0, 2746.83, 3192.0, 20617.0, 20617.0, 20617.0, 20617.0, 19717.0, 19717.0, 19717.0,
                19717.0, 20617.0, 20617.0, 20617.0, 20617.0, 19717.0, 19717.0, 19717.0, 19717.0, 37267.0, 37267.0, 37267.0,
                37267.0, 36867.0, 36867.0, 36867.0, 36867.0, 37267.0, 37267.0, 37267.0, 37267.0, 36867.0, 36867.0, 36867.0,
                36867.0, 85967.0, 85967.0, 84567.0, 84567.0, 84567.0, 84567.0, 83167.0, 83167.0, 85967.0, 85967.0, 84567.0,
                84567.0, 84567.0, 84567.0, 83167.0, 83167.0);
        int counter = 0;
        Object[][] details = new Object[80][6];
        for (Integer income : incomes) {
            for (Integer age : ages) {
                for (Boolean isRes : isResident) {
                    for (Boolean tbrLevy : TBRLevy) {
                        for (int i = 0; i < LILevy.size(); i++, counter++) {
                            Object[] detail = new Object[]{
                                    income,
                                    age,
                                    isRes,
                                    tbrLevy,
                                    LILevy.get(i),
                                    expectedTax.get(counter)
                            };
                            details[counter] = detail;
                        }
                    }
                }
            }
        }

        return details;
    }

    @ParameterizedTest
    @MethodSource("getIncomeDetails")
    @DisplayName("Test for Tax with all combination of parameters")
    public void getIncomeTaxTest(int income, int age, boolean isResident, boolean TBRLevy, boolean LILevy, double expectedTax) {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(income, age, isResident, TBRLevy, LILevy);
        assertTrue(taxationRule.getTax() == expectedTax);
    }

    @Test
    @DisplayName("Testing Actual income with specific parameters")
    public void getActualIncomeTaxTest() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(30000, 45, true, true, true);
        assertTrue(taxationRule.getActualIncome() == 27603.17);
    }

    @Test
    @DisplayName("Testing Tax with specific parameters")
    public void getTaxTest() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(30000, 45, true, true, true);
        assertTrue(taxationRule.getTax() == 2396.83);
    }

    @Test
    @DisplayName("Test for annual income is less than lowThreshold")
    public void incomeWithLowerThreshold() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(17000, 20, true, true, true);
        assertTrue(taxationRule.getTax() == 0);
    }

    @Test
    @DisplayName("Test for annual income is in between lowThreshold and first tier")
    public void incomeWithFirstTier() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(21000, 20, false, true, true);
        assertTrue(taxationRule.getTax() == 86.83);
    }

    @Test
    @DisplayName("Test for annual income is in between lowThreshold and first tier for Resident")
    public void incomeWithFirstTierForResident() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(21000, 20, true, true, true);
        assertTrue(taxationRule.getTax() == 506.83);
    }

    @Test
    @DisplayName("Test for annual income is in between first tier and second tier")
    public void incomeWithSecondTier() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(47000, 20, false, true, true);
        assertTrue(taxationRule.getTax() == 5742);
    }

    @Test
    @DisplayName("Test for annual income is in between first tier and second tier for Resident")
    public void incomeWithSecondTierForResident() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(47000, 20, true, true, true);
        assertTrue(taxationRule.getTax() == 6682);
    }

    @Test
    @DisplayName("Test for annual income is in between second tier and third tier")
    public void incomeWithThirdTier() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(130000, 20, false, true, true);
        assertTrue(taxationRule.getTax() == 33167);
    }

    @Test
    @DisplayName("Test for annual income is in between second tier and third tier for Resident")
    public void incomeWithThirdTierForResident() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(130000, 20, true, true, true);
        assertTrue(taxationRule.getTax() == 35767);
    }

    @Test
    @DisplayName("Test for annual income is in between third tier and fourth tier without TBR Levy")
    public void incomeWithFourthTierWithoutTBR() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(190000, 20, false, false, true);
        assertTrue(taxationRule.getTax() == 56167);
    }

    @Test
    @DisplayName("Test for annual income is in between third tier and fourth tier for Resident without TBR Levy")
    public void incomeWithFourthTierWithoutTBRForResident() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(190000, 20, true, false, true);
        assertTrue(taxationRule.getTax() == 59967);
    }

    @Test
    @DisplayName("Test for annual income is in between third tier and fourth tier with TBR Levy")
    public void incomeWithFourthTierWithTBR() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(190000, 20, false, true, true);
        assertTrue(taxationRule.getTax() == 58933.66);
    }

    @Test
    @DisplayName("Test for annual income is in between third tier and fourth tier for Resident with TBR Levy")
    public void incomeWithFourthTierWithTBRForResident() {
        GeneralTaxationRule taxationRule = new GeneralTaxationRule(190000, 20, true, true, true);
        assertTrue(taxationRule.getTax() == 62733.66);
    }

    @AfterAll
    public static void shutDown() {
        System.out.println("After in GeneraTaxationRule");
    }
}
