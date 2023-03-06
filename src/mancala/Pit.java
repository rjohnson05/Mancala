package mancala;

import java.util.ArrayList;
import java.util.List;

public class Pit {
	private int marbleCount;
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;
	
	public Pit(int marbleCount) {
		this.marbleCount = marbleCount;
	}
	
	public Pit() {
		this.marbleCount = 4;
	}
	
	public int getMarbleCount() {
		return marbleCount;
	}
	
	public int getSide() {
		return boardSide;
	}
	
	public List<Marble> getMarbles() {
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
}