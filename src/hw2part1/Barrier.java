package hw2part1;

public class Barrier {
	int value;
	int initialValue;
	
	public Barrier(int initialValue) {
		this.value=initialValue;
	}
	
	public synchronized void await() {
		value--;
		if(value > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(value == 0){
			value = initialValue;
			notifyAll();
		}
	}
}
