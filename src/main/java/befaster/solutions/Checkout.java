package befaster.solutions;

import java.util.HashMap;
import java.util.Map;
import static befaster.solutions.SKU.A;

public class Checkout {
	
	public static Integer checkout(String skus)
	{
		Map<String, Integer> skuCounts = new HashMap<>();
		if(skus.equals(""))
			return 0;
		if(skus.equals("-"))
			return -1;
		
		String[] splitSkus = skus.split("");
		
		for(String thisSku : splitSkus)
		{	
			int count = 0;
			
			if(skuCounts.containsKey(thisSku))
			{
				count = skuCounts.get(thisSku);
			}
			skuCounts.put(thisSku, ++count);
		}
		

		int totalForAllSkus = 0;
		int totalForThisSku;
		for(String skuItem : skuCounts.keySet())
		{
			Integer numberOfSkus = skuCounts.get(skuItem);
			
			try {
				SKU sku = SKU.valueOf(skuItem);
				
				switch(sku)
				{
					case A:
						{
							int numberOfBatchesOf3TimesA = (int) Math.ceil(numberOfSkus / 3);
							int remainder = numberOfSkus % 3;
							totalForThisSku = (remainder * sku.price()) + numberOfBatchesOf3TimesA * 130;
							break;
						}
					
					case B:
						{
							int numberOfBatchesOf2TimesB = (int) Math.ceil(numberOfSkus / 2);
							int remainder = numberOfSkus % 2;
							totalForThisSku = (remainder * sku.price()) + numberOfBatchesOf2TimesB * 45;
							break;
						}
					case C:
						
					case D:
						totalForThisSku = (numberOfSkus * sku.price());
						break; 
						
					default:
						totalForThisSku = (numberOfSkus * sku.price());
			
				}
			}
			catch(IllegalArgumentException iae)
			{
				return -1;
			}
			
			totalForAllSkus = totalForAllSkus + totalForThisSku;
		
		}
		
		return totalForAllSkus;
	}
}


enum SKU 
{
	A("A", 50),
	B("B", 30),
	C("C", 20),
	D("D", 15)
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