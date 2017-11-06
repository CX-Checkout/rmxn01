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
					computeForSKUItemA(skuBasketValue, numberOfSKUsInTheBasket, sku);
					break;
				}
				
				case B:{
					computeForSKUItemB(skuBasketValue, numberOfSKUsInTheBasket, sku);
					break;
				}
				
				case C:
				case D : {
					skuBasketValue.put(sku, numberOfSKUsInTheBasket * sku.price());
					break; 
				}
					
				case E: {
					computeForSKUItemE(skuBasketValue, skuNumberOfItemsInBasket, numberOfSKUsInTheBasket, sku);
					break; 
				}	
			}
			
				
		}
		
		return getTotalOfAllSKUInTheBasket(skuBasketValue);
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


	private static void computeForSKUItemA(Map<SKU, Integer> skuBasketValue, Integer numberOfSKUs, SKU sku) 
	{
		int numberOfBatchesOf5TimesA = (int) Math.ceil(numberOfSKUs / 5);
		int remainder = numberOfSKUs % 5; 
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
	
	private static void computeForSKUItemE(Map<SKU, Integer> skuBasketValue, Map<SKU, Integer> skuNumberOfItemsInTheBasket, Integer numberOfSKUsInTheBasket, SKU sku) {
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
	E("E", 40)
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
