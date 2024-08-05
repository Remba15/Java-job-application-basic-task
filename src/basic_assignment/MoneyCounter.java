package basic_assignment;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;


public class MoneyCounter {
	private static List<String> currencyList = new ArrayList<String>();
	private static List<Integer> valuesList = new ArrayList<Integer>();
	
	private String csvRootFolder;
	
	public MoneyCounter(String rootFolder) {
		this.csvRootFolder = rootFolder;
	}
	
	public String getRootFolder() {
		return this.csvRootFolder;
	}
	
	public void setRootFolder(String rootFolder) {
		this.csvRootFolder = rootFolder;
	}
	
	public void count(){
		System.out.println("Searching for money files in \"F:\\data\"");
		
		List<String> csvFiles = returnCSVFileList(this.csvRootFolder);
		for(int i = 0; i < csvFiles.size(); i++) {
			System.out.println("\n\"" + csvFiles.get(i) + "\"" + " found");
			printCounts(csvFiles.get(i));
		}
	}
	
	public void printTotalCount() {
		System.out.println("\nMoney in all countries:");
		for(int i = 0; i < currencyList.size(); i++) {
			System.out.printf("  %s: %d\n", currencyList.get(i), valuesList.get(i));
		}
	}
	
	private List<String> returnCSVFileList(String rootFolder) {
		
		List<String> csvFiles = new ArrayList<String>();
		File folder = new File(rootFolder);
		File[] listOfFiles = folder.listFiles();
		
		if(listOfFiles != null) {
			for(int i = 0; i < listOfFiles.length; i++) {
				if(listOfFiles[i].getName().contains(".csv")) {
					csvFiles.add(listOfFiles[i].getName());
				}
			}
		}
		
		return csvFiles;
	}
	
	private void printCounts(String fileName){
		List<List<String>> csvItems = new ArrayList<>();
		
		try(Scanner scan = new Scanner(new File(this.csvRootFolder + "\\" + fileName))){
			while (scan.hasNextLine()) {
				csvItems.add(getRowItem(scan.nextLine()));
			}
		}
		catch(Exception e)
		{
			System.out.println("File not found!");
		}
		
		System.out.println("  Totals by currencies:");
		
		printValues(csvItems);
	}
	
	private List<String> getRowItem(String line) {
		List<String> items = new ArrayList<String>();
		try (Scanner rowScan = new Scanner(line)){
			rowScan.useDelimiter(",");
			while(rowScan.hasNext()) {
				items.add(rowScan.next());
			}
		}
			
		return items;
	}
	
	private void printValues(List<List<String>> items) {
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
	
}
