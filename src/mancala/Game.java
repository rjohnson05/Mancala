package mancala;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	private List<Pit> storeList = new ArrayList<>();
	private int currentPlayer;
	private boolean playerGetsAnotherMove;
	private boolean capturedMarbles;

	public Game() {
		currentPlayer = 0;
		resetBoard();
	}

	/**
	 * Returns a boolean of whether or not the player gets another turn. This method
	 * uses the number of marbles in the pit, and the distance from the store to see
	 * if they get another turn. If the player selects a pit that, when moved, will
	 * end in the store, then they get another turn.
	 * 
	 * @param selectedPitIndex, player input of the pit the player wants to move
	 * @return boolean whether or not the player gets another move
	 */
	public boolean getsAnotherMove(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();

		if (selectedPit.getMarbleList().size() == 0) {
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
		}
		return false;
	}

	/**
	 * This method moves the marbles throughout the board. It takes the amount of
	 * marbles in the selected pit, and distributes them to the sequential pits.
	 * 
	 * @param selectedPitIndex, player input of the pit the player wants to move
	 * @return a boolean on whether or not the player chose to move marbles from a
	 *         valid pit
	 */
	public boolean move(int selectedPitIndex) {
		if (storeList.get(selectedPitIndex).getSide() != currentPlayer) {
			return false;
		}

		if (storeList.get(selectedPitIndex).getMarbleList().size() == 0) {
			return false;
		}

		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();
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
					// After reaching the end of the list of pits, the marble is placed in the first
					// pit,
					// creating a wrap-around effect
					nextPitIndex = 0;
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				}
				currentPitIndex = nextPitIndex;
			}
			// Clears all marbles from the selected pit
			selectedPit.getMarbleList().clear();
		}

		return true;
	}

	/**
	 * The method checks each side of the board to see if the game is over. The game
	 * is over when there are no marbles left on one or the other sides of the board
	 * 
	 * @return boolean on whether or not the game has a winner (is the game over)
	 */
	public boolean hasWinner() {
		// check the first side of the board for a winner
		boolean side1Empty = true;
		for (int i = 0; i < 6; i++) {
			if (storeList.get(i).getMarbleList().size() != 0) {
				side1Empty = false;
			}
		}
		// check the second side of the board for a winner
		boolean side2Empty = true;
		for (int i = 7; i < 13; i++) {
			if (storeList.get(i).getMarbleList().size() != 0) {
				side2Empty = false;
			}
		}

		return (side1Empty || side2Empty);
	}

	/**
	 * This is a void method that switches the player.
	 * 
	 */
	public void switchPlayer() {
		// if the current player is 0, switch it
		if (currentPlayer == 0) {
			currentPlayer = 1;
		}
		// if the current player is 1, switch it
		else if (currentPlayer == 1) {
			currentPlayer = 0;
		}
	}

	/**
	 * This is a void method prints when the game is done. It prints a sentence with
	 * the winner and the score of the game.
	 * 
	 */
	public void endGame() {
		System.out.println("player " + getWinner() + " won the game! The score was, Player 0: "
				+ storeList.get(6).getMarbleList().size() + " to Player 2:  "
				+ storeList.get(13).getMarbleList().size());
		resetBoard();
	}

	/**
	 * This is a void method that resets the board.
	 * 
	 */
	public void resetBoard() {
		// reset the first 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit(4, 0);
			storeList.add(pit);
		}
		// reset the first store at index 6
		storeList.add(new Pit(0, -1));
		// reset the second 6 pits
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit(4, 1);
			storeList.add(pit);
		}
		// reset the second store at index 13
		storeList.add(new Pit(0, -1));
	}

	/**
	 * 
	 * @return the player that won as represented by an int
	 */
	public int getWinner() {
		// compare the stores of each player
		if (hasWinner()) {
			// if player 0 has a greater amount in their store, return 0
			if (storeList.get(6).getMarbleList().size() > storeList.get(13).getMarbleList().size()) {
				return 0;
			}
			// otherwise, return player 1
		}
		return 1;

	}

	/**
	 * 
	 * 
	 * @param selectedPitIndex
	 * @return a boolean on whether or not the player made a capture move
	 */
	public boolean checkCapture(int selectedPitIndex) {
		System.out.println("Entered capture check");
		// get the selected pit
		Pit selectedPit = storeList.get(selectedPitIndex);
		// get the marble count of the selected pit
		int marbleCount = selectedPit.getMarbleList().size();

		if (selectedPit.getMarbleList().size() == 0) {
			capturedMarbles = false;
			return false;
		}

		// check if the end pit needs to loop around the board
		int endPitIndex = selectedPitIndex + marbleCount;
		if (endPitIndex > 13) {
			endPitIndex -= 13;
		}
		// if the end pit is in either of the stores, it can not be captured
		if (endPitIndex == 13 || endPitIndex == 6) {
			capturedMarbles = false;
			return false;
		}

		// check if the end pit is on your side
		Pit endPit = storeList.get(endPitIndex);

		if (endPit.getSide() != currentPlayer) {
			capturedMarbles = false;
			return false;
		}
		// if the end pit marble count is 0 then we have a capture
		if (endPit.getMarbleList().size() == 0) {
			System.out.println("Capture made");
			int capturePitIndex = 12 - endPitIndex;
			Pit capturePit = storeList.get(capturePitIndex);

			if (capturePit.getMarbleList().size() == 0) {
				return false;
			}

			// formula to check the capture 2, 3, 7, 0, 11, 4, 7, 3

			// set both of the pits to a marble count of 0
			System.out.println("You captured their marbles!");
			capturedMarbles = true;
			return true;

		}
		capturedMarbles = false;
		return false;
	}

	public void moveCapturedMarbles(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();
		Pit endPit = storeList.get(selectedPitIndex + marbleCount);
		if (endPit.getMarbleList().size() == 0) {
			System.out.println("Capture made");
			int capturePitIndex = 12 - (selectedPitIndex + marbleCount);
			Pit capturePit = storeList.get(capturePitIndex);

			// Determining which store gets the captured marbles
			Pit currentStore = storeList.get(6);
			if ((currentPlayer == 1)) {
				currentStore = storeList.get(13);
			}

			// Moving all the marbles from the captured pit to the capturing player's store
			for (Marble marble : capturePit.getMarbleList()) {
				currentStore.getMarbleList().add(marble);
			}
			capturePit.getMarbleList().clear();

			// Moving the marble from the capturing pit to the capturing player's store
			currentStore.getMarbleList().add(selectedPit.getMarbleList().get(selectedPit.getMarbleList().size() - 1));
			selectedPit.getMarbleList().remove(selectedPit.getMarbleList().size() - 1);

			// formula to check the capture 2, 3, 7, 0, 11, 4, 7, 3

			// set both of the pits to a marble count of 0
			System.out.println("You captured their marbles!");
		}
	}

	/**
	 * 
	 * @return a list of pits that the board is made of
	 */
	public List<Pit> getStoreList() {
		return storeList;
	}

	/**
	 * 
	 * @return the current player
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void playGame() {

		int getSelectedIndex = -1;
		Scanner s = new Scanner(System.in);
		while (!hasWinner()) {
			System.out.println("Player " + currentPlayer + " is up.");
			System.out.println((storeList.toString()));
			System.out.println("Enter selected pit you want to move");
			getSelectedIndex = s.nextInt();
			playerGetsAnotherMove = getsAnotherMove(getSelectedIndex);
			checkCapture(getSelectedIndex);

			while (!move(getSelectedIndex)) {
				if (capturedMarbles) {
					moveCapturedMarbles(getSelectedIndex);
					break;
				}
				System.out.println((storeList.toString()));
				System.out.println("Select a valid pit!");
				getSelectedIndex = s.nextInt();
				playerGetsAnotherMove = getsAnotherMove(getSelectedIndex);

			}

			while (playerGetsAnotherMove) {

				System.out.println("Nice! Player " + currentPlayer + " gets another turn.");
				System.out.println((storeList.toString()));
				System.out.println("Enter selected pit you want to move");
				getSelectedIndex = s.nextInt();
				playerGetsAnotherMove = getsAnotherMove(getSelectedIndex);
				checkCapture(getSelectedIndex);

				while (!move(getSelectedIndex)) {
					moveCapturedMarbles(getSelectedIndex);
					if (capturedMarbles) {
						break;
					}
					System.out.println("Select a valid pit!");
					System.out.println((storeList.toString()));

					getSelectedIndex = s.nextInt();
					playerGetsAnotherMove = getsAnotherMove(getSelectedIndex);

				}
			}
			switchPlayer();
			System.out.println("______________________");
		}
		endGame();
		s.close();

	}

	public static void main(String[] args) {
		Game g = new Game();
		g.playGame();

	}

}