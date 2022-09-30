//a simulate tax calculator, does not fully reflect Australian tax rules
public class GeneralTaxationRule {
	private int lowThreshold = 18200;
	private int firstTier = 45000;
	private int firstTierBase = 5092;
	private int secondTier = 120000;
	private int secondTierBase = 29467;
	private int thirdTier = 180000;
	private int thirdTierBase = 51667;
	private double actualIncome;
	private double tax;
	
	public GeneralTaxationRule(int income, int age, boolean isResident, boolean TBRLevy, boolean LILevy){
		actualIncome = income - ReturnTax(income, age, isResident, TBRLevy, LILevy);		
	}
	private double ReturnTax(int income, int age, boolean isResident, boolean TBRLevy, boolean LILevy){		
		double stage1 = 0.19;
		double stage2 = 0.325;
		double stage3 = 0.37;
		double stage4 = 0.45;
		double medicareLevyCharge = 0.02;
		double TBRLevyCharge = 0.02;
		if(income <= lowThreshold){
			return tax = 0.0;
		}else if(income <= firstTier){
			if(LILevy){			
				if(income > 20543){
					return tax = medicareTax(isResident, income, 20543, stage1, medicareLevyCharge);
				}else{
					return tax = 0;
					}
				}
			else{
				return tax = medicareTax(isResident, income, lowThreshold, stage1, medicareLevyCharge);
			   }
		}else if(income <= secondTier){
			return tax = firstTierBase + medicareTax(isResident, income, firstTier, stage2, medicareLevyCharge);	
		}else if(income <= thirdTier){
			return tax = secondTierBase + medicareTax(isResident, income, secondTier, stage3, medicareLevyCharge);
		}else{
			if (TBRLevy){
				return tax = thirdTierBase + medicareTax(isResident, income, thirdTier, stage4, medicareLevyCharge) + 
				             (income - thirdTierBase) * TBRLevyCharge;
			}
			return tax = thirdTierBase + medicareTax(isResident, income, thirdTier, stage4, medicareLevyCharge);			
		}
	}

	public double medicareTax(boolean isResident, int income, int threshold, double taxRate, double additionalTax){
		if(isResident){
			return (income - threshold)*taxRate + income * additionalTax;
		}else{
			return (income - threshold)*taxRate;
		}
	}
	public double getActualIncome(){
		return actualIncome;
	}
	public double getTax(){
		return tax;
	}
}
