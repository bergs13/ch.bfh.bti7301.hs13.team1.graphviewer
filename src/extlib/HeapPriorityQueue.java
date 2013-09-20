/**
 * 
 */
package extlib;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Random;


/**
 * @author ps
 *
 */
public class HeapPriorityQueue<K extends Comparable<? super K>, E> implements
		PriorityQueue<K, E> {
	
	private class PQLocator<K extends Comparable<? super K>, E> 
			implements Locator<K,E>{

		private K key;
		private E elem;
		private Object thisPQueue = HeapPriorityQueue.this;
		private int pos;
		
		/* (non-Javadoc)
		 * @see examples.Position#element()
		 */
		@Override
		public E element() {
			return elem;
		}

		/* (non-Javadoc)
		 * @see examples.Locator#key()
		 */
		@Override
		public K key() {
			return key;
		}
		
	}
	private int nextPos = 1; // we begin with number 1 (not 0!)
	PQLocator<K,E> [] locs;

	public HeapPriorityQueue(){
		locs = (PQLocator<K,E>[]) new PQLocator[256]; 
	}

	public HeapPriorityQueue(int initialSize){
		locs = (PQLocator<K,E>[]) new PQLocator[initialSize]; 
	}
	
	private void swap(int pos1, int pos2){
		PQLocator<K,E> tmp = locs[pos1];
		locs[pos1]= locs[pos2];
		locs[pos2]=tmp;
		// locators updaten:
		locs[pos1].pos=pos1;  
		locs[pos2].pos=pos2;
	}


	private void expand(){
		locs = Arrays.copyOf(locs,locs.length*2);
	}


	private void upHeap(int pos) {
		// key [pos] ist das neue Element
		while (pos >1  && locs[pos/2].key.compareTo(locs[pos].key)>0){
			// Vertauschen mit parent
			swap(pos,pos/2);
			pos=pos/2;
		}
		return;
	}

	private void downHeap(int pos){
		int left = pos*2;
		int right = left+1;
		while (left<nextPos){
			// search the bigger of left and right 
			int min = left;
			if (right < nextPos && locs[right].key.compareTo(locs[left].key) < 0) min = right;
			// now max is the index of the bigger child
			// do we have to swap??
			// if not we break
			if (locs[pos].key.compareTo(locs[min].key)<=0) break; // heap ok
			else swap(pos,min);
			pos=min;
			left=pos*2;
			right=left+1;
		}
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#showMin()
	 */
	@Override
	public Locator<K,E> showMin() {
		return locs[1];
	}

	
	/* (non-Javadoc)
	 * @see examples.PriorityQueue#removeMin()
	 */
	@Override
	public Locator<K,E> removeMin() {
		Locator<K,E> ret = locs[1];
		remove(locs[1]);
		return ret;
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#insert(java.lang.Comparable, java.lang.Object)
	 */
	@Override
	public Locator<K,E> insert(K key, E o) {
		if (nextPos >= locs.length) expand();
		PQLocator<K,E> pl = new PQLocator<K,E>();
		pl.elem = o;
		pl.key = key;
		pl.pos = nextPos;
		locs[nextPos]=pl;
		upHeap(nextPos);
		nextPos++;
		return pl;
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#remove(examples.Locator)
	 */
	@Override
	public void remove(Locator<K,E> loc) {
		PQLocator<K,E> n = (PQLocator<K,E>)loc;
		if (n.thisPQueue != this) throw new RuntimeException("Invalid locator!");
		int pos = n.pos;
		// put the last locator on position pos and repair the heap
		swap(pos,--nextPos);
		upHeap(pos);
		downHeap(pos);
		n.thisPQueue=null;
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#replaceKey(examples.Locator, java.lang.Comparable)
	 */
	@Override
	public void replaceKey(Locator<K,E> loc, K newKey) {
		PQLocator<K,E> n = (PQLocator<K,E>)loc;
		if (n.thisPQueue != this) throw new RuntimeException("Invalid locator!");
		int pos = n.pos;
		n.key = newKey;
		upHeap(pos);
		downHeap(pos);		
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return nextPos < 2;
	}

	/* (non-Javadoc)
	 * @see examples.PriorityQueue#size()
	 */
	@Override
	public int size() {
		return nextPos-1;
	}

	protected boolean test(){
		// test for heap condition and correct locator position
		for (int i=2;i<nextPos;i++){
			if (locs[i/2].key.compareTo(locs[i].key)>0) return false;
			if (locs[i].pos != i) return false;
		}
		return locs[1].pos == 1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
		final int N = 1000000;
		Locator<Integer,Object> [] locs =new Locator[N];
		// java.util.PriorityQueue<Integer> jq = new java.util.PriorityQueue<Integer>();
		HeapPriorityQueue<Integer,Object> pq = 
				new HeapPriorityQueue<Integer,Object>(N+1);;
		Random rand = new Random();
		long te1 = System.currentTimeMillis();
		long t1 = threadBean.getCurrentThreadCpuTime();
		for (int i=0;i<N;i++){
			locs[i] = pq.insert(rand.nextInt(N),null);
			// jq.add(rand.nextInt(N));
			// jq.remove();
		}
		// System.out.println(pq.test());
//		for (int i=0;i<N/2;i++){
//			pq.replaceKey(locs[i], rand.nextInt(N));
//		}
		// System.out.println(pq.test());

		for (int i=0;i<N;i++){
			// pq.removeMin();
			//jq.remove();
			pq.remove(locs[i]);
		}
		// System.out.println(pq.test());
		
//		for (int i=0;i<N/2;i++){
//			pq.replaceKey(locs[i],3);
//			// pq.removeMin();
//		}	
		long te2 = System.currentTimeMillis();
		long t2 = threadBean.getCurrentThreadCpuTime();		
		long time=t2-t1;
		long eTime=te2-te1;
		System.out.println("CPU-Time usage: "+time/1000000.0+" ms");
		System.out.println("elapsed time: "+eTime+" ms");
		// System.out.println("Heap  is correct: "+ pq.test());
	}
}
