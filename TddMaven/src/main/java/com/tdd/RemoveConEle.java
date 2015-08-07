package com.tdd;

public class RemoveConEle {

	public static void main(String[] args) {
		int[] sample = new int[] { 1, 1, 2, 2, 3, 3, 3 ,3, 3, 1, 1, 3, 3, 3,1 };
		//int[] resultIndex=removeDuplicate(sample);
	}

	public  int[] removeDuplicate(final int[] sample) {
		int curIndex = 0;
		int resultIndex = curIndex;
		int remove;
		int len=sample.length;
		while (curIndex < len) {
			remove = sample[curIndex];
			if (sample[curIndex] == remove) {
				sample[resultIndex++] = sample[curIndex++];
				curIndex = returnCurrentId(sample, curIndex, remove, len);
			} 
		}
		assignRemainingZero(sample, resultIndex, len);
		
		
		for(int i=0;i<len;i++){
			System.out.print(sample[i]);
		}
		System.out.println();
		return sample;
	}

	private static void assignRemainingZero(final int[] sample, final int resultIndex, final int len) {
		for (int i = resultIndex; i < len; i++) {
			sample[i]=0;
		}
	}

	private static int returnCurrentId(final int[] sample, int curIndex, final int remove, final int len) {
		for (;curIndex < len && sample[curIndex] == remove;++curIndex) ;
		return curIndex;
	}
	
	

}
