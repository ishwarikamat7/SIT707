public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int income = 30000;
		int age = 45;
		boolean isResident = true;
		boolean TBRLevy = true; 
		boolean LILevy = true;
		GeneralTaxationRule generalTaxationRule = new GeneralTaxationRule(income, age, isResident, TBRLevy, LILevy);
		System.out.println(generalTaxationRule.getTax());
		System.out.println(generalTaxationRule.getActualIncome());
	}

}
