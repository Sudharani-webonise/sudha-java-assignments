package com.tddTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

import com.tdd.RemoveConEle;



public class RemoveConEleTest {

	/**
	 * @throws java.lang.Exception
	 */
	RemoveConEle remEle;

	@Before
	public void init() {
		System.out.println("Before");
		remEle = new RemoveConEle();
	}

	@After
	public void stop(){
		System.out.println("After");
	}
	
	
	
	@Test
	public void testToRemoveElem() {
		int[] arr = create_arr(1, 2, 1, 1, 3, 3, 1, 1, 1, 5);
		int[] resultantArray = remEle.removeDuplicate(arr);
		assertArrayEquals(new int[] { 1, 2, 1, 3, 1, 5, 0, 0, 0, 0 }, resultantArray);

	}

	private int[] create_arr(int... array) {
		return array;
	}

	@Test
	public void testToRemoveSingleElem() {
		int[] arr = create_arr(1, 1, 1, 1, 1, 1 );
		int[] resultantArray = remEle.removeDuplicate(arr);
		assertArrayEquals(new int[] { 1, 0, 0, 0, 0, 0 }, resultantArray);

	}

	@Test
	public void testToRemoveNothing() {
		int[] arr = create_arr();
		int[] resultantArray = remEle.removeDuplicate(arr);
		assertArrayEquals(new int[] {}, resultantArray);

	}

	@Test
	public void testToRemoveNoElem() {
		int[] arr = create_arr(1, 0, 5, 2, 13, 4 );
		int[] resultantArray = remEle.removeDuplicate(arr);
		assertArrayEquals(new int[] { 1, 0, 5, 2, 13, 4 }, resultantArray);

	}
}
