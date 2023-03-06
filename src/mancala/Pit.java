package mancala;



import java.util.ArrayList;
import java.util.List;

public class Pit {
	private int marbleCount = 4;
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;
	
	public Pit(int marbleCount) {
		this.marbleCount = marbleCount;
		for (int i = 0; i < marbleCount; i++) {
			addMarble(new Marble());
		}
	}	
	
	public Pit() {
		this.marbleCount = 4;
		for (int i = 0; i < 4; i++) {
			addMarble(new Marble());
		}
	}
	
	// added set board side
	public void setBoardSide(int boardSide) {
		this.boardSide = boardSide;
	}
	
	public int getMarbleCount() {
		return marbleCount;
	}
	
	public int getSide() {
		return boardSide;
	}
	
	public List<Marble> getMarbleList() {
		return marblesContained;
	}
	
	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}
	
	public Marble removeMarble(Marble marble) {
		marblesContained.remove(marble);
		return marble;
	}
	
	
	
	public String toString() {
		return Integer.toString(getMarbleCount());
	}
	
	public Marble[] toArray() {
		Marble[] arr = new Marble[getMarbleCount()];
		for (int i = 0; i < getMarbleCount(); i++) {
			arr[i] = marblesContained.get(i);
		}
		return arr;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Testing Pit class....");
		Pit p = new Pit();
		p.setBoardSide(1);
		Marble m = new Marble();
		// test 1
		p.addMarble(m);
		if (p.getMarbleCount() == 1) {
			System.out.println("Marble Count true test passed...");
		} else {
			System.out.println("Marble Count true test failed...");
		}
		
		Pit l = new Pit();
		Marble n = new Marble();
		// test 2
		
		if (p.getSide() == 1) {
			System.out.println("Marble Count test passed...");
		} else {
			System.out.println("Marble Count test failed...");
		}
		

	}

	public void setMarbleCount(int i) {
		this.marbleCount = i;
	}


}

