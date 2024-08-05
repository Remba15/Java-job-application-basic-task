package basic_assignment;

public class Main {
	public static void main(String[] args){
		MoneyCounter moneyCounter = new MoneyCounter("F:\\data");
		moneyCounter.count();
		moneyCounter.printTotalCount();
	}
}
