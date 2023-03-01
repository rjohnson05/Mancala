package mancala;

import java.util.ArrayList;
import java.util.List;

public class Pit {
	private int marbleCount = 4;
	private List<Marble> marblesContained = new ArrayList<>();
	private int boardSide;
	
	public Pit() {

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
	
	public void addMarble(Marble marble) {
		marblesContained.add(marble);
	}
	
	public void removeMarble(Marble marble) {
		marblesContained.remove(marble);
	}
}