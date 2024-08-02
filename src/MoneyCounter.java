import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;


public class MoneyCounter {
	public static String CSVRootFolder = "F:\\data";
	public static List<String> currencyList = new ArrayList<String>();
	public static List<Integer> valuesList = new ArrayList<Integer>();
	
	public static void main(String[] args){
		count();
		printTotalCount();
	}
	
	private static void count(){
		System.out.println("Searching for money files in \"F:\\data\"");
		
		ArrayList<String> CSVFiles = returnCSVFileList(CSVRootFolder);
		for(int i = 0; i < CSVFiles.size(); i++) {
			System.out.println("\n\"" + CSVFiles.get(i) + "\"" + " found");
			printCounts(CSVFiles.get(i));
		}
	}
	
	private static ArrayList<String> returnCSVFileList(String rootFolder) {
		
		ArrayList<String> CSVFiles = new ArrayList<String>();
		File folder = new File(rootFolder);
		File[] listOfFiles = folder.listFiles();
		
		if(listOfFiles != null) {
			for(int i = 0; i < listOfFiles.length; i++) {
				if(listOfFiles[i].getName().contains(".csv")) {
					CSVFiles.add(listOfFiles[i].getName());
				}
			}
		}
		
		return CSVFiles;
	}
	
	private static void printCounts(String fileName){
		List<List<String>> CSVItems = new ArrayList<>();
		
		try(Scanner scan = new Scanner(new File(CSVRootFolder + "\\" + fileName))){
			while (scan.hasNextLine()) {
				CSVItems.add(getRowItem(scan.nextLine()));
			}
		}
		catch(Exception e)
		{
			System.out.println("File not found!");
		}
		
		System.out.println("  Totals by currencies:");
		
		printValues(CSVItems);
	}
	
	private static List<String> getRowItem(String line) {
		List<String> items = new ArrayList<String>();
		try (Scanner rowScan = new Scanner(line)){
			rowScan.useDelimiter(",");
			while(rowScan.hasNext()) {
				items.add(rowScan.next());
			}
		}
			
		return items;
	}
	
	private static void printValues(List<List<String>> items) {
		List<String> currencies = new ArrayList<String>();
		List<Integer> values = new ArrayList<Integer>();
		int sum = 0;
		
		for(int i = 0; i < items.size(); i++) {
			if(currencies.contains(items.get(i).get(1))) {
				sum = values.get(currencies.indexOf(items.get(i).get(1)));
				sum += Integer.valueOf(items.get(i).get(2).trim());
				values.set(currencies.indexOf(items.get(i).get(1)), sum);
			}
			else {
				currencies.add(items.get(i).get(1));
				values.add(Integer.valueOf(items.get(i).get(2).trim()));
			}
		}
		
		for(int i = 0; i < currencies.size(); i++) {
			System.out.printf("   %s: %d\n", currencies.get(i), values.get(i));
			if(currencyList.contains(currencies.get(i))) {
				int index = currencyList.indexOf(currencies.get(i));
				sum = valuesList.get(index);
				sum += values.get(i);
				valuesList.set(index, sum);
			}
			else {
				currencyList.add(currencies.get(i));
				valuesList.add(values.get(i));
			}
		}
	}
	
	private static void printTotalCount() {
		System.out.println("\nMoney in all countries:");
		for(int i = 0; i < currencyList.size(); i++) {
			System.out.printf("  %s: %d\n", currencyList.get(i), valuesList.get(i));
		}
	}
	
}
