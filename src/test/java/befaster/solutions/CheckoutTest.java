package befaster.solutions;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static java.util.Arrays.asList;

import java.util.Collection;

@RunWith(Parameterized.class)
public class CheckoutTest {
	
	private final String basket;
	private final int totalValue;
	
	@Parameters(name = "basket with {0} is of total value {1}")
	public static Collection<Object[]> data()
	{
		return asList(
			new Object[][]
			{
					// illegal cases
					{"", 0},
					{"-", -1},
					
					// happy path
					{"A", 50},
					{"AA", 100},
					
					{"AAA", 130}, //special offer
					{"AAAAA", 200}, //special offer
					
					{"B", 30},
					{"BB", 45},
					{"CCCC", 80},
					{"D", 15},
					{"E", 40},
					{"EE", 80},
					
					// special offer
					{"EEB", 80},
					{"EEEB", 120},
					{"EEBB", 110},
					{"EEEBB", 150},
					{"EEEEBB", 160},
					{"EEBBB", 80+0+45},
					{"EB", 70},
					{"EBB", 85},
					
					// edge cases
					{"ABCa", -1},
					{"AxA", -1}
			}
		);
	}
	
	public CheckoutTest(String basket, int totalValue)
	{
		this.basket = basket;
		this.totalValue = totalValue;
	}
	
	@Test
	public void return_value_of_items_in_the_basket()
	{
		assertThat(Checkout.checkout(basket), equalTo(totalValue));
	}	
}
