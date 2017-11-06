package befaster.solutions;


import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CheckoutTest {
	
	@Test
	public void return_zero_for_empty_input()
	{
		assertThat("empty input", Checkout.checkout(""), equalTo(0));
	}
	
	@Test
	public void return_minus_one_for_illegal_input()
	{
		assertThat("-", Checkout.checkout("-"), equalTo(-1));
	}
	
	
	@Test
	public void return_50_for_one_item_of_A()
	{
		assertThat(Checkout.checkout("A"), equalTo(50));
	}
	
	@Test
	public void return_100_for_two_items_of_A()
	{
		assertThat(Checkout.checkout("AA"), equalTo(100));
	}
	
	@Test
	public void return_130_for_three_items_of_A()
	{
		assertThat(Checkout.checkout("AAA"), equalTo(130));
	}
	
	@Test
	public void return_30_for_one_item_of_B()
	{
		assertThat(Checkout.checkout("B"), equalTo(30));
	}
	
	@Test
	public void return_45_for_two_items_of_B()
	{
		assertThat(Checkout.checkout("BB"), equalTo(45));
	}
	
	@Test
	public void return_80_for_four_items_of_C()
	{
		assertThat(Checkout.checkout("CCCC"), equalTo(80));
	}
	
	@Test
	public void return_15_for_one_item_of_D()
	{
		assertThat(Checkout.checkout("D"), equalTo(15));
	}
	
	@Test
	public void return_150_for_a_basket_with_ABCa()
	{
		assertThat(Checkout.checkout("ABCa"), equalTo(-1));
	}
	
	@Test
	public void return_100_for_a_basket_with_AxA()
	{
		assertThat(Checkout.checkout("AxA"), equalTo(-1));
	}
}