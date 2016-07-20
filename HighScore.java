package dystopia;

import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * Structure for managing high scores as well as updating the table
 *
 * @author rohan
 */
public class HighScore implements Comparable<HighScore> {

	public String name;
	public int score;
	public static HighScore[] scores;

	static {
		String[] in = file.getWordsFromFile("highscore.txt");
		scores = new HighScore[10];
		for (int i = 0; i < scores.length; i++) {
			scores[i] = new HighScore(in[i]);
		}
	}

	public HighScore(String input) {
		String[] parsed = input.split(",");
		name = parsed[0];
		score = Integer.parseInt(parsed[1]);
	}

	@Override
	public String toString() {
		return name + "," + score;
	}

	@Override
	public int compareTo(HighScore h) {
		return -1 * (this.score - h.score);
	}

	public static void manageScore(int score) {
		if (score > scores[9].score) {
			HighScore[] temp = new HighScore[11];
			System.arraycopy(scores, 0, temp, 0, 10);
			temp[10] = new HighScore(JOptionPane.showInputDialog("Please enter a name\n"
											+ "You got a High Score:" + score) + "," + score);
			Arrays.sort(temp);
			System.arraycopy(temp, 0, scores, 0, 10);
			file.writeHighScoresToFile("highscore.txt", scores);
		}
	}
}
