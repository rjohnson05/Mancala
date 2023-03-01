package mancala;
import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Pit> storeList = new ArrayList<>();
	
	public Game() {
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();
			storeList.add(pit);
		}
		storeList.add(new Pit());
		for (int i = 0; i < 6; i++) {
			Pit pit = new Pit();
			storeList.add(pit);
		}
		storeList.add(new Pit());
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
}