package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static befaster.solutions.SKU.A;
import static befaster.solutions.SKU.B;

public class Checkout {
	
	public static Integer checkout(String basket)
	{
		if(basket.equals(""))
			return 0;
		if(basket.equals("-"))
			return -1;
		
		Map<SKU, Integer> skuBasketValue = new TreeMap<>();
		Map<SKU, Integer> skuNumberOfItemsInBasket;
		
		try {
			skuNumberOfItemsInBasket = parseSKUItemsInBasket(basket);
		}
		catch(IllegalArgumentException iae)
		{
			return -1;
		}
		

		for(SKU sku : skuNumberOfItemsInBasket.keySet())
		{
			Integer numberOfSKUsInTheBasket = skuNumberOfItemsInBasket.get(sku);
			 		
			switch(sku)
			{
				case A: {
					computeForSKUItemA(skuBasketValue, skuNumberOfItemsInBasket, sku);
					break;
				}
				
				case B:{
					computeForSKUItemB(skuBasketValue, numberOfSKUsInTheBasket, sku);
					break;
				}
				
				case C:
				case D:
				case G:
				case I:
				case J:
				case L:
				case M:
				case O:
				case S: {
					skuBasketValue.put(sku, numberOfSKUsInTheBasket * sku.price());
					break; 
				}
					
				case E: {
					computeForSKUItemE(sku, skuBasketValue, skuNumberOfItemsInBasket, numberOfSKUsInTheBasket);
					break; 
				}		
				
				case F: {
					computeForSKUItemF(skuBasketValue, sku, numberOfSKUsInTheBasket);
					break;
				}
				
				case H: {
					int numberOfBatchesOf10 = (int) Math.ceil(numberOfSKUsInTheBasket / 10);
					int remainder = numberOfSKUsInTheBasket % 10;
					int totalForThisSKU = numberOfBatchesOf10 * 80;
					
					if(remainder >= 5)
					{
						int numberOfBatchesOf5 = (int) Math.ceil(numberOfSKUsInTheBasket / 5);
						totalForThisSKU = totalForThisSKU + (numberOfBatchesOf5 * 45);
						remainder = remainder % 5;
					}
					
					totalForThisSKU = totalForThisSKU + (remainder * sku.price());
					skuBasketValue.put(sku, totalForThisSKU);
					break; 
				}
			}
			
				
		}
		
		return getTotalOfAllSKUInTheBasket(skuBasketValue);
	}

	/*private static int computeTotalForMultipleItems(SKU sku, Integer numberOfSKUsInTheBasket, int numberOfItemsInABatch, int offerPriceForBatch) {
		int numberOfBatches = (int) Math.ceil(numberOfSKUsInTheBasket / numberOfItemsInABatch);
		return (remainder * sku.price()) + numberOfBatches * offerPriceForBatch;
	}*/

	private static void computeForSKUItemF(Map<SKU, Integer> skuBasketValue, SKU sku, Integer numberOfSKUsInTheBasket) {
		int totalForThisSKU = 0;
		int numberOfPairsOfItemF = 0;
		while(numberOfSKUsInTheBasket >= 3)
		{
			numberOfSKUsInTheBasket = numberOfSKUsInTheBasket - 2;
			numberOfPairsOfItemF++;
			numberOfSKUsInTheBasket--;
		}
		totalForThisSKU = (numberOfSKUsInTheBasket * sku.price());
		totalForThisSKU = totalForThisSKU + (numberOfPairsOfItemF * 2 * sku.price());
		
		skuBasketValue.put(sku, totalForThisSKU);
	}

	private static Map<SKU, Integer> parseSKUItemsInBasket(String basket) {
		Map<SKU, Integer> result = new TreeMap<>();
		String[] basketSplit = basket.split("");
		
		for(String eachBasketItem : basketSplit)
		{	
			SKU sku = SKU.valueOf(eachBasketItem);
			int count = 0;
			
			if(result.containsKey(sku))
			{
				count = result.get(sku);
			}
			result.put(sku, ++count);

		}
		
		return result;
	}


	private static void computeForSKUItemA(Map<SKU, Integer> skuBasketValue, Map<SKU, Integer> skuNumberOfItemsInTheBasket, SKU sku) 
	{
		int numberOfSKUsInTheBasket = skuNumberOfItemsInTheBasket.get(sku);
		int numberOfBatchesOf5TimesA = (int) Math.ceil(numberOfSKUsInTheBasket / 5);
		int remainder = numberOfSKUsInTheBasket % 5; 
		int totalForThisSKU	 = numberOfBatchesOf5TimesA * 200;
		
		int numberOfBatchesOf3TimesA = (int) Math.ceil(remainder / 3);
		remainder = remainder % 3;
		totalForThisSKU = totalForThisSKU + (remainder * sku.price()) + numberOfBatchesOf3TimesA * 130;
		
		skuBasketValue.put(sku, totalForThisSKU);
	}
	
	private static void computeForSKUItemB(Map<SKU, Integer> skuBasketValue, Integer numberOfSkus, SKU sku) {
		int numberOfBatchesOf2TimesB = (int) Math.ceil(numberOfSkus / 2);
		int remainder = numberOfSkus % 2; 
		int totalForThisSKU = (remainder * sku.price()) + numberOfBatchesOf2TimesB * 45;
		skuBasketValue.put(sku, totalForThisSKU);
	}
	
	private static void computeForSKUItemE(SKU sku, Map<SKU, Integer> skuBasketValue, Map<SKU, Integer> skuNumberOfItemsInTheBasket, Integer numberOfSKUsInTheBasket) {
		skuBasketValue.put(sku, numberOfSKUsInTheBasket * sku.price());
		
		int numberOfBatchesOf2TimesE = (int) Math.ceil(numberOfSKUsInTheBasket / 2);
		if(numberOfBatchesOf2TimesE > 0 && skuBasketValue.containsKey(B))
		{
			int numberOfBItemsInTheBasket = skuNumberOfItemsInTheBasket.get(B);
			int remainingBItems = numberOfBItemsInTheBasket - numberOfBatchesOf2TimesE; 
			skuBasketValue.put(B, remainingBItems);
			computeForSKUItemB(skuBasketValue, remainingBItems, B);
		}
	}


	private static Integer getTotalOfAllSKUInTheBasket(Map<SKU, Integer> skuBasketValue) {
		int totalForAllSkus = 0;
		for(SKU sku : skuBasketValue.keySet())
		{
			totalForAllSkus = totalForAllSkus + skuBasketValue.get(sku);
		}
		return totalForAllSkus;
	}
}


enum SKU 
{
	A("A", 50),
	B("B", 30),
	C("C", 20),
	D("D", 15),
	E("E", 40),
	F("F", 10),
	G("G", 20),
	H("H", 10),
	I("I", 35),
	J("J", 60),
	L("L", 90),
	M("M", 15),
	O("O", 10),
	S("S", 30),
	;
	
	private final String name;
	private int price;
	
	SKU(String name, int price)
	{
		this.name = name;
		this.price = price;
	}
	
	int price()
	{
		return price;
	}
}
