package mancala;

import java.util.ArrayList;
import java.util.List;

public class Pit {
	private int marbleCount;
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;
	
	public Pit(int marbleCount, int boardSide) {
		this.boardSide = boardSide;
		for (int i = 0; i < marbleCount; i++) {
			addMarble(new Marble());
		}
	}
	
	public Pit() {
		for (int i = 0; i < 4; i++) {
			addMarble(new Marble());
		}
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
	
	public void setMarbleCount(int newMarbleCount) {
		marbleCount = newMarbleCount;
	}
	
	public void addMarble(Marble marble) {
		marblesContained.add(marble);
		marbleCount++;
	}
	
	public void removeMarble(Marble marble) {
		marblesContained.remove(marble);
		marbleCount--;
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

}
