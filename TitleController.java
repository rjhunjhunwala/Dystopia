package dystopia;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Controls all navigation through the main menu
 *
 * @author Rohans
 */
public class TitleController implements KeyListener {

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Navigation through menus r-Return to menu i-how to play p-play one game
	 *
	 * @param e
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (!TitleFrame.playing.get()) {
			switch (e.getKeyChar()) {
				case 'R':
				case 'r':
					if (TitleFrame.TitlePanel.screen.get() == Screens.highscore) {
						TitleFrame.TitlePanel.screen.set(Screens.title);
					}
					break;
				case 'i':
				case 'I':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title) {
						TitleFrame.TitlePanel.screen.set(Screens.help);
					}
					break;

				case 'p':
				case 'P':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title || TitleFrame.TitlePanel.screen.get() == Screens.help) {
					TitleFrame.TitlePanel.screen.set(Screens.title);
						TitleFrame.playing.set(true);
					}
					break;
				case 'h':
				case 'H':
					if (TitleFrame.TitlePanel.screen.get() == Screens.title||TitleFrame.TitlePanel.screen.get() == Screens.help) {
						TitleFrame.TitlePanel.screen.set(Screens.highscore);
					}

					break;
			}
		}
	}

}
