package mancala;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
import java.util.Iterator;


public class Game {
	private List<Pit> storeList = new ArrayList<>();
	private int currentPlayer;

	
	public Game() {
		currentPlayer = 0;
		resetBoard();
	}
	
	private boolean getsAnotherMove(int selectedPitIndex) {
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
	
	private void move(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleCount();
		// Marbles can only be moved if there is at least 1 marble in the selected pit
		if (marbleCount > 0) {
			int currentPitIndex = selectedPitIndex;
			// Move each marble in the selected pit to another pit
			for (int i = 0; i < selectedPit.getMarbleCount(); i++) {
				int nextPitIndex = currentPitIndex + 1;
				// Makes sure the pit index loops around if at the end of the list
				if (nextPitIndex < storeList.size()) {
					// Moves the marble to the next pit on the board
					Marble removedMarble = selectedPit.removeMarble(storeList.get(selectedPitIndex).getMarbleList().get(selectedPitIndex+i));
					Pit nextPit = storeList.get(nextPitIndex);
					System.out.println("Next pit = " + nextPit.getMarbleCount());
					nextPit.addMarble(removedMarble);
					System.out.println("After add = " + nextPit.getMarbleCount());
				} else {
					// After reaching the end of the list of pits, the marble is placed in the first pit,
					// creating a wrap-around effect
					Marble removedMarble = selectedPit.removeMarble(storeList.get(selectedPitIndex).getMarbleList().get(selectedPitIndex+i));
					Pit nextPit = storeList.get(0);
					nextPit.addMarble(removedMarble);
				}
				currentPitIndex = nextPitIndex;
			}
			// Clears all marbles from the selected pit
			selectedPit.getMarbleList().clear();
			selectedPit.setMarbleCount(0);
		}
	}
	


		
	
	private boolean hasWinner() {
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
	

	
	private void switchPlayer() {
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
	
	private void endGame() {
		System.out.println("player" + getWinner() + "won the game!");
		resetBoard();
	}
	
	private void resetBoard() {
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
	
	private int getWinner() {
		// compare the stores of each player
		if (hasWinner()) {
			// if player 0 has a greater amount in their store, return 0
			if (storeList.get(6).getMarbleCount() > storeList.get(13).getMarbleCount()) {
				return 0;
			}
			// otherwise, return player 1
		} return 1;
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		Game g = new Game();
		int getSelectedIndex;
		Scanner s = new Scanner(System.in);
		while (!g.hasWinner()) {
		    System.out.println("Enter selected pit you want to move");
			getSelectedIndex = s.nextInt();
			g.move(getSelectedIndex);
			System.out.println("Moved");
			System.out.println((g.storeList.toString()));
			while(g.getsAnotherMove(getSelectedIndex)) {
			    System.out.println("Enter selected pit you want to move");
				getSelectedIndex = s.nextInt();
				g.move(getSelectedIndex);
			}
			g.switchPlayer();
		}
		g.endGame();

	}
	
}

