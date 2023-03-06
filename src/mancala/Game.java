package mancala;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Pit> storeList = new ArrayList<>();
	private int currentPlayer;

	
	public Game() {
		currentPlayer = 0;
		resetBoard();
	}
	
	public boolean getsAnotherMove(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		// checking if the marbles get to the end of the list and will loop around
		int endPit = selectedPit.getMarbleCount() + selectedPitIndex;
		if (endPit > 13) {
			endPit -= 13;
		}
		
		// checking which store to check
		if (currentPlayer == 0) {
			if (endPit == 6) {
				return true;
			}
		} else {
			if (endPit == 13) {
				return true;
			}
		} return false;
	}
	
	public void move(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleCount();
		// Marbles can only be moved if there is at least 1 marble in the selected pit
		if (marbleCount > 0) {
			int currentPitIndex = selectedPitIndex;
			// Move each marble in the selected pit to another pit
			for (Marble marble : selectedPit.getMarbles()) {
				selectedPit.removeMarble(marble);
				int nextPitIndex = currentPitIndex + 1;
				if (nextPitIndex < storeList.size()) {
					// Moves the marble to the next pit on the board
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				} else {
					// After reaching the end of the list of pits, the marble is placed in the first pit,
					// creating a wrap-around effect
					Pit nextPit = storeList.get(0);
					nextPit.addMarble(marble);
				}
			}
		}
	}
		
	
	public boolean hasWinner() {
		// check the first side of the board for a winner
		for (int i = 0; i < 6; i++) {
			if (storeList.get(i).getMarbleCount() != 0) {
				return false;
			}
		}
		// check the second side of the board for a winner
		for (int i = 7; i < 13; i++) {
			if (storeList.get(i).getMarbleCount() != 0) {
				return false;
			}
		} return true;
	}
	

	
	public void switchPlayer() {
		// if the current player is 0, switch it
		if (currentPlayer == 0) {
			currentPlayer = 1;
		} 
		// if the current player is 1, switch it
		else if (currentPlayer == 1) {
			currentPlayer = 0;
		}
		// add an error exception here
		
	}
	
	public void endGame() {
		System.out.println("player" + getWinner() + "won the game!");
		resetBoard();
	}
	
	public void resetBoard() {
		// reset the first 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();
			storeList.add(pit);
		}
		// reset the first store at index 6
		storeList.add(new Pit());
		// reset the second 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();
			storeList.add(pit);
		}
		// reset the second store at index 13
		storeList.add(new Pit());
	}
	
	public int getWinner() {
		// compare the stores of each player
		if (hasWinner()) {
			// if player 0 has a greater amount in their store, return 0
			if (storeList.get(6).getMarbleCount() > storeList.get(13).getMarbleCount()) {
				return 0;
			}
			// otherwise, return player 1
		} return 1;
		
	}
	
	public List<Pit> getStoreList() {
		return storeList;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	
	public static void main(String[] args) {
		
	}
	
}

