package mancala;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	private List<Pit> storeList = new ArrayList<>();
	private int currentPlayer;
	private boolean playerGetsAnotherMove;

	
	public Game() {
		currentPlayer = 0;
		resetBoard();
	}
	
	public boolean getsAnotherMove(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleCount();

		
		if (selectedPit.getMarbleCount() == 0) {
			return false;
		}

		// checking if the marbles get to the end of the list and will loop around
		int endPitIndex = selectedPitIndex + marbleCount;
		if (endPitIndex > 13) {
			endPitIndex -= 13;
		}
		
		// checking which store to check
		if (currentPlayer == 0) {
			if (endPitIndex == 6) {
				return true;
			}
		} else {
			if (endPitIndex == 13) {
				return true;
			}
		} return false;
	}
	
	
	
	public boolean move(int selectedPitIndex) {
		if (storeList.get(selectedPitIndex).getSide() != currentPlayer) {
			return false;
		}
		

		if (storeList.get(selectedPitIndex).getMarbleCount() == 0) {
			return false;
		}
		

		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleCount();
		// Marbles can only be moved if there is at least 1 marble in the selected pit
		if (marbleCount > 0) {
			int currentPitIndex = selectedPitIndex;
			// Move each marble in the selected pit to another pit
			for (Marble marble : selectedPit.getMarbleList()) {

				// skip the other players store
				if (currentPlayer == 0) {
					if (currentPitIndex == 12) {
						currentPitIndex++;
					}
				}
				// skip the other players store
				if (currentPlayer == 1) {
					if (currentPitIndex == 5) {
						currentPitIndex++;
					}
				}

				int nextPitIndex = currentPitIndex + 1;
				// Makes sure the pit index loops around if at the end of the list
				if (nextPitIndex < storeList.size()) {
					// Moves the marble to the next pit on the board
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				} else {
					// After reaching the end of the list of pits, the marble is placed in the first pit,
					// creating a wrap-around effect
					nextPitIndex = 0;
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				}
				currentPitIndex = nextPitIndex;
			}
			// Clears all marbles from the selected pit
			selectedPit.getMarbleList().clear();
			selectedPit.setMarbleCount(0);
		}
		return true;
	}
		
	
	public boolean hasWinner() {
		// check the first side of the board for a winner
		boolean side1Empty = true;
		for (int i = 0; i < 6; i++) {
			if (storeList.get(i).getMarbleCount() != 0) {
				side1Empty = false;
			}
		}
		// check the second side of the board for a winner
		boolean side2Empty = true;
		for (int i = 7; i < 13; i++) {
			if (storeList.get(i).getMarbleCount() != 0) {
				side2Empty = false;
			}
		}
		
		return (side1Empty || side2Empty);
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
		System.out.println("player " + getWinner() + " won the game! The score was, Player 0: " + storeList.get(6).getMarbleCount() + " to Player 2:  " + storeList.get(13).getMarbleCount());
		resetBoard();
	}
	
	public void resetBoard() {
		// reset the first 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit(4,0);
			storeList.add(pit);
		}
		// reset the first store at index 6
		storeList.add(new Pit(0, -1));
		// reset the second 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit(4,1);
			storeList.add(pit);
		}
		// reset the second store at index 13
		storeList.add(new Pit(0, -1));
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
	
	public boolean checkCapture(int selectedPitIndex) {
		// get the selected pit
		Pit selectedPit = storeList.get(selectedPitIndex);
		//get the marble count of the selected pit
		int marbleCount = selectedPit.getMarbleCount();
		
		// check if the end pit needs to loop around the board
		int endPitIndex = selectedPitIndex + marbleCount;
		if (endPitIndex > 13) {
			endPitIndex -= 13;
		}
		// if the end pit is in either of the stores, it can not be captured
		if (endPitIndex == 13 || endPitIndex == 6) {
			return false;
		}
		// check if the end pit is on your side
		Pit endPit = storeList.get(endPitIndex);
		if (endPit.getSide() != currentPlayer) {
			return false;
		}
		// if the end pit marble count is 0 then we have a capture
		if (endPit.getMarbleCount() == 0) {
			int capturePitIndex = 12 - endPitIndex;
			Pit capturePit = storeList.get(capturePitIndex);
			if (currentPlayer == 0) {
				// then move the marbles to the correct store
				int currentStore = storeList.get(6).getMarbleCount();
				storeList.get(6).setMarbleCount(currentStore + 1 + capturePit.getMarbleCount());

			} else {
				int currentStore = storeList.get(6).getMarbleCount();
				storeList.get(13).setMarbleCount(currentStore + 1 + capturePit.getMarbleCount());
				// 2, 3, 7, 0, 11, 4, 7, 3
				
			}
			
			// set both of the pits to a marble count of 0
			capturePit.setMarbleCount(0);
			storeList.get(endPitIndex - 1).setMarbleCount(0);

			return true;
			
		} return false;
	}
	
	public List<Pit> getStoreList() {
		return storeList;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	
	public static void main(String[] args) {
		Game g = new Game();
		int getSelectedIndex = -1;
		Scanner s = new Scanner(System.in);
		while (!g.hasWinner()) {
			System.out.println("Player " + g.currentPlayer + " is up.");
			System.out.println((g.storeList.toString()));
		    System.out.println("Enter selected pit you want to move");
			getSelectedIndex = s.nextInt();
			g.playerGetsAnotherMove = g.getsAnotherMove(getSelectedIndex);
//			g.checkCapture(getSelectedIndex);
			while(!g.move(getSelectedIndex)) {
			    System.out.println("Select a valid pit!");
				getSelectedIndex = s.nextInt();
				g.playerGetsAnotherMove = g.getsAnotherMove(getSelectedIndex);
//				g.checkCapture(getSelectedIndex);
			}
			while(g.playerGetsAnotherMove) {
				System.out.println("Nice! Player " + g.currentPlayer + " gets another turn.");
				System.out.println((g.storeList.toString()));
			    System.out.println("Enter selected pit you want to move");
				getSelectedIndex = s.nextInt();
				g.playerGetsAnotherMove = g.getsAnotherMove(getSelectedIndex);
//				g.checkCapture(getSelectedIndex);
				while(!g.move(getSelectedIndex)) {
				    System.out.println("Select a valid pit!");
					getSelectedIndex = s.nextInt();
					g.playerGetsAnotherMove = g.getsAnotherMove(getSelectedIndex);
//					g.checkCapture(getSelectedIndex);
				}
			}
			g.switchPlayer();
			System.out.println("______________________");
		}
		g.endGame();
		s.close();

	}

	
}