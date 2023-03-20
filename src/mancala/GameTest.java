package mancala;

public class GameTest {
	private static boolean testGameInitialization() {
		Game newGame = new Game();
		// Verify that there are 14 spaces on the board (12 pits & 2 stores)
		if (newGame.getStoreList().size() != 14) {
			System.out.println("FAIL: The created board does not have the correct number of spaces");
			return false;
		}
		
		// Verify that Player 1 (Human Player in 1-Player Mode) begins the game
		if (newGame.getCurrentPlayer() != 0) {
			System.out.println("FAIL: Player 1 is not starting the game");
			return false;
		}
		
		// Making sure all pits have four marbles, while the stores have zero marbles upon initialization
		for (int i = 0; i < newGame.getStoreList().size(); i++) {
			if (i != 6 && i != 13) {
				// Verifies there are four marbles in each pit
				if (newGame.getStoreList().get(i).getMarbleCount() != 4) {
					System.out.println("FAIL: Not all pits have 4 marbles initially");
					return false;
				}
			} else {
				// Verifies there are zero marbles in each pit
				if (newGame.getStoreList().get(i).getMarbleCount() != 0) {
					System.out.println("FAIL: The stores are not empty initially");
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean testMove() {
		// Creates a new Mancala game and moves a space with four marbles
		Game newGame = new Game();
		newGame.move(0);
		
		// Verify that the marbles from the indicated space were moved to the correct spaces
		if (newGame.getStoreList().get(1).getMarbleCount() != 5) {
			System.out.println("FAIL: A marble was not moved to the next space");
			return false;
		}
		if (newGame.getStoreList().get(2).getMarbleCount() != 5) {
			System.out.println("FAIL: A marble was not moved to the second space");
			return false;
		}
		if (newGame.getStoreList().get(3).getMarbleCount() != 5) {
			System.out.println("FAIL: A marble was not moved to the third space");
			return false;
		}
		if (newGame.getStoreList().get(4).getMarbleCount() != 5) {
			System.out.println("FAIL: A marble was not moved to the fourth space");
			return false;
		}
		
		// Move a space containing 5 marbles
		newGame.move(4);
		if (newGame.getStoreList().get(5).getMarbleCount() != 5 || 
				newGame.getStoreList().get(6).getMarbleCount() != 1 ||
				newGame.getStoreList().get(7).getMarbleCount() != 5 ||
				newGame.getStoreList().get(8).getMarbleCount() != 5 ||
				newGame.getStoreList().get(9).getMarbleCount() != 5) {
			System.out.println("FAIL: The marbles from a 5-marble pit were not moved correctly");
			return false;
		}
		
		// Try moving an empty pit
		newGame.move(0);
		if (newGame.getStoreList().get(0).getMarbleCount() != 0 ||
				newGame.getStoreList().get(1).getMarbleCount() != 5) {
			System.out.println(newGame.getStoreList().get(0).getMarbleCount());
			System.out.println(newGame.getStoreList().get(1).getMarbleCount());
			System.out.println("FAIL: An empty pit resulted in a marble-count change");
			return false;
		}
		
		return true;
	}
	
	private static boolean testGetsAnotherMove() {
		Game newGame = new Game();
		
		// Test when last marble in store
		newGame.move(2);
		
		
		// Test when last marble in pit with marbles
		
		// Test when last marble in empty pit
		
		return true;
	}
	
	private static boolean testHasWinner() {
		Game newGame = new Game();
		
		// Test when only one pit is empty
		newGame.move(1);
		if (newGame.hasWinner()) {
			System.out.println("FAIL: After the first move, a winner is declared");
			return false;
		}		
		
		// Test when only end pits contain marbles
		for (int i = 1; i < 5; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		for (int i = 8; i < 12; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		if (newGame.hasWinner()) {
			System.out.println("FAIL: A winner is declared when both sides still have marbles in both end pits");
			return false;
		}
		
		// Test when one side is empty
		for (int i = 0; i < 6; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		if (!newGame.hasWinner()) {
			System.out.println("FAIL: A winner wasn't declared when one side of the board was empty");
			return false;
		}
		
		// Test when both sides are empty
		for (int i = 0; i < 13; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		if (!newGame.hasWinner()) {
			System.out.println("FAIL: A winner wasn't declared when both sides of the board was empty");
			return false;
		}
		
		return true;
	}
	
	private static boolean testSwitchPlayer() {
		Game newGame = new Game();
		
		// Switching to Player 2
		newGame.switchPlayer();
		if (newGame.getCurrentPlayer() != 1) {
			System.out.println("FAIL: Player 1 wasn't switched to Player 2");
			return false;
		}
		
		// Switching to Player 1
		newGame.switchPlayer();
		if (newGame.getCurrentPlayer() != 0) {
			System.out.println("FAIL: Player 2 wasn't switched to Player 1");
			return false;
		}
		
		return true;
	}
	
	private static boolean testResetBoard() {
		Game newGame = new Game();
		
		testGameInitialization();
		
		// Make sure that hasWinner() is false
		if (newGame.hasWinner()) {
			System.out.println("FAIL: The game has a winner directly after resetting");
			return false;
		}
		
		return true;
	}
	
	private static boolean testGetWinner() {
		Game newGame = new Game();
		
		// Test if Player 1 wins
		for (int i = 0; i < 6; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		newGame.getStoreList().get(6).setMarbleCount(3);
		if (newGame.getWinner() != 0) {
			System.out.println("FAIL: Player 1 wasn't recorded as the winner when it should have been");
			return false;
		}
		
		// Test if Player 2 wins
		newGame.getStoreList().get(13).setMarbleCount(5);
		if (newGame.getWinner() != 1) {
			System.out.println("FAIL: Player 2 wasn't recorded as the winner when it should have been");
			return false;
		}
		
		// Test if both player's sides are empty
		for (int i = 7; i < 13; i++) {
			newGame.getStoreList().get(i).setMarbleCount(0);
		}
		if (newGame.getWinner() != 1) {
			System.out.println("FAIL: Player 2 wasn't recorded as the winner when both sides of the board were empty");
			return false;
		}

		return true;
	}
	
	public static void main(String[] args) {
		testGameInitialization();
		testMove();
		//testGetsAnotherMove();
		testHasWinner();
		testSwitchPlayer();
		testResetBoard();
		testGetWinner();
	}
}
