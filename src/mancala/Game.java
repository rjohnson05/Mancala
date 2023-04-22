package mancala;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The main class for the game Mancala. This contains the business logic for the
 * game and additionally allows you to play the text based version.
 * 
 * @author Ryan Johnson, Hank Rugg
 */
public class Game {

	public Random rand = new Random();
	public boolean isOver;
	private List<Pit> storeList = new ArrayList<>();
	private int currentPlayer;
	private boolean playerGetsAnotherMove;
	private boolean capturedMarbles;
	private int winner;

	/**
	 * The constructor for the game. It creates the board and sets the current
	 * player to player 0 (the first player)
	 */
	public Game() {
		currentPlayer = 0;
		winner = -1;
		resetBoard();
	}

	/**
	 * Sets the playerGetsAnotherMove class variable to either true if the player
	 * gets another move or false if the player' turn is over
	 * 
	 * @param selectedPitIndex index of the pit the player wants to move
	 * @return boolean true if the player gets another move, false otherwise
	 */
	public boolean setsAnotherMove(int selectedPitIndex) {
		Pit selectedPit = storeList.get(selectedPitIndex);
		int marbleCount = selectedPit.getMarbleList().size();

		if (selectedPit.getMarbleList().size() == 0) {
			playerGetsAnotherMove = false;
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
				playerGetsAnotherMove = true;
				return true;
			}
		} else {
			if (endPitIndex == 13) {
				playerGetsAnotherMove = true;
				return true;
			}
		}
		playerGetsAnotherMove = false;
		return false;
	}

	/**
	 * Returns whether or not the player gets another turn. The player gets another
	 * turn if the last marble that they move ends in their store.
	 * 
	 * @return boolean true if the player gets another move, false otherwise
	 */
	public boolean getsAnotherMove() {
		return playerGetsAnotherMove;
	}

	/**
	 * Moves the marbles throughout the board. It takes the amount of
	 * marbles in the selected pit and distributes them to the sequential pits. A
	 * valid pit is one that is on that player's side of the board and has at least
	 * one marble in it.
	 * 
	 * @param selectedPitIndex index of the pit the player wants to move
	 * @return boolean on whether or not the player selected a valid pit. True for
	 *         valid, false otherwise
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
		Marble homePitMarble = null;
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
				if (nextPitIndex == selectedPitIndex) {
					// skip the current pit until the end if there are enough marbles to loop all
					// the way around the board
					homePitMarble = marble;
				} else if (nextPitIndex < storeList.size()) {
					// Makes sure the pit index loops around if at the end of the list
					// Moves the marble to the next pit on the board
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				} else {
					// After reaching the end of the list of pits, the marble is placed in the first
					// pit, creating a wrap-around effect
					nextPitIndex = 0;
					Pit nextPit = storeList.get(nextPitIndex);
					nextPit.addMarble(marble);
				}
				currentPitIndex = nextPitIndex;
			}
		}
		// Clears all marbles from the selected pit
		selectedPit.getMarbleList().clear();
		if (homePitMarble != null) {
			selectedPit.addMarble(homePitMarble);
		}

		return true;
	}

	/**
	 * Checks each side of the board to see if the game is over. The game is over
	 * when there are no marbles left on either player one's side, or player two's
	 * side.
	 * 
	 * @return boolean on whether or not the game has a winner. True if there is a
	 *         winner, false otherwise.
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
	 * Changes the turn to the opposite player.
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
	 * Called when the game is done. It outputs text containing the winner
	 * and the score of the game.
	 * 
	 */
	public void endGame() {
		System.out.println("player " + getWinner() + " won the game! The score was, Player 0: "
				+ storeList.get(6).getMarbleList().size() + " to Player 2:  "
				+ storeList.get(13).getMarbleList().size());
		resetBoard();
	}

	/**
	 * Resets the game board. It adds 6 pits, each with 4 marbles in each pit for
	 * player 0's side of the board. It then creates the store for player 0. Next,
	 * it creates 6 additional pits, each with 4 marbles in each pit for player 1's
	 * side of the board. Finally, it creates the store for player 1.
	 * 
	 */
	public void resetBoard() {
		storeList.clear();
		currentPlayer = 0;
		winner = -1;
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
	 * Sets the winner of the game.
	 */
	public void setWinner() {
		// compare the stores of each player
		if (hasWinner()) {
			// Determine which side of the board is empty
			int emptySide = 0;
			for (int i = 0; i < 6; i++) {
				if (storeList.get(i).getMarbleList().size() != 0) {
					emptySide = 1;
				}
			}

			// Move all marbles from the non-empty side to the corresponding player's store
			if (emptySide == 0) {
				for (int i = 7; i < 13; i++) {
					for (Marble marble : storeList.get(i).getMarbleList()) {
						storeList.get(13).getMarbleList().add(marble);
					}
					storeList.get(i).getMarbleList().clear();
				}
			} else {
				for (int i = 0; i < 6; i++) {
					for (Marble marble : storeList.get(i).getMarbleList()) {
						storeList.get(6).getMarbleList().add(marble);
					}
					storeList.get(i).getMarbleList().clear();
				}
			}

			// if player 0 has a greater amount in their store, return 0
			if (storeList.get(6).getMarbleList().size() > storeList.get(13).getMarbleList().size()) {
				winner = 0;
			} else {
				// otherwise, return player 1
				winner = 1;
			}
		}
	}

	/**
	 * Returns the identifier of the player that wins the game.
	 * 
	 * @return the player that won as represented by an int
	 */
	public int getWinner() {
		return winner;

	}

	/**
	 * Checks to see if a capture play was made. A capture happens when a player
	 * lands on an empty pit with their final marble, and the pit across from the
	 * board has one or more marbles in it. If this scenario happens, the player
	 * that moved the marbles captures the one marble they moved and all the marbles
	 * in the opposite pit. These marbles are then moved into the store of the
	 * player that moved. This method is paired with the
	 * {@link moveCapturedMarbled()} method.
	 * 
	 * @param endPitIndex Index of the pit the player's move will finish on
	 * @return boolean on whether or not the player made a capture move. True if the 
	 * player made a capture, false otherwise
	 * @see #moveCapturedMarbles(int)
	 */
	public boolean checkCapture(int endPitIndex) {
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
		Pit capturedPit = storeList.get(12 - endPitIndex);
		if (endPit.getMarbleList().size() == 1 && capturedPit.getMarbleList().size() != 0) {
			capturedMarbles = true;
			return true;
		}

		capturedMarbles = false;
		return false;
	}

	/**
	 * Carries out the action of moving the captured marbles. Gets the selected pit
	 * from the selected pit index and moves the marbles to the correct pits.
	 * Paired with the checkCapture() method.
	 * 
	 * @param endPitIndex Index of the pit the player's move will finish on
	 * @see #checkCapture(int)
	 */
	public void moveCapturedMarbles(int endPitIndex) {
		Pit endPit = storeList.get(endPitIndex);
		Pit capturedPit = storeList.get(12 - endPitIndex);

		if (capturedPit.getMarbleList().size() != 0) {
			// Determining which store gets the captured marbles
			Pit currentStore = storeList.get(6);
			if ((currentPlayer == 1)) {
				currentStore = storeList.get(13);
			}

			// Moving all the marbles from the captured pit to the capturing player's store
			for (Marble marble : capturedPit.getMarbleList()) {
				currentStore.getMarbleList().add(marble);
			}
			capturedPit.getMarbleList().clear();

			// Moving the marble from the capturing pit to the capturing player's store
			currentStore.getMarbleList().add(endPit.getMarbleList().get(0));
			endPit.getMarbleList().remove(0);

		}
	}

	/**
	 * Returns a list of pits that the board is made of.
	 * 
	 * @return a list of pits that the board is made of
	 */
	public List<Pit> getStoreList() {
		return storeList;
	}

	/**
	 * Returns the current player.
	 * 
	 * @return the current player
	 */
	public int getCurrentPlayer() {
		return currentPlayer;
	}
	
	/**
	 * Sets the current player.
	 * @param currentPlayer
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * Executes the text version game.
	 */
	public void playGame() {
		int getSelectedIndex = -1;
		Scanner s = new Scanner(System.in);
		// run the game until there is a winner
		while (!hasWinner()) {
			System.out.println("Player " + currentPlayer + " is up.");
			System.out.println((storeList.toString()));
			System.out.println("Enter selected pit you want to move");
			// get the index of the pit they want to move the marbles from
			getSelectedIndex = s.nextInt();
			checkCapture(getSelectedIndex);

			// if that is not a valid move, get a valid move
			while (!move(getSelectedIndex)) {
				if (capturedMarbles) {
					moveCapturedMarbles(getSelectedIndex);
					break;
				}
				System.out.println((storeList.toString()));
				System.out.println("Select a valid pit!");
				getSelectedIndex = s.nextInt();
			}

			// if they player gets another move, they get another chance to play
			while (playerGetsAnotherMove) {
				System.out.println("Nice! Player " + currentPlayer + " gets another turn.");
				System.out.println((storeList.toString()));
				System.out.println("Enter selected pit you want to move");
				getSelectedIndex = s.nextInt();
				checkCapture(getSelectedIndex);

				// if that is not a valid move, get a valid move
				while (!move(getSelectedIndex)) {
					moveCapturedMarbles(getSelectedIndex);
					if (capturedMarbles) {
						break;
					}
					System.out.println("Select a valid pit!");
					System.out.println((storeList.toString()));

					getSelectedIndex = s.nextInt();
				}
			}
			switchPlayer();
			System.out.println("______________________");
		}
		endGame();
		s.close();

	}

	/**
	 * Runs the text based game.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game g = new Game();
		g.playGame();

	}

}