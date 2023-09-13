package bomberman.gui;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import bomberman.Game;
import bomberman.gui.menu.Menu;
import bomberman.sfx.AudioPlayer;
public class Frame extends JFrame
{
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private InfoPanel _infopanel;
	private Game _game;
	AudioPlayer player=new AudioPlayer("bgMusic1");
	AudioPlayer bgMusic;
	public Frame()
	{
		setJMenuBar(new Menu(this));
		_containerpane=new JPanel(new BorderLayout());
		_gamepane=new GamePanel(this);
		_infopanel=new InfoPanel(_gamepane.getGame());
		_containerpane.add(_infopanel,BorderLayout.PAGE_START);
		_containerpane.add(_gamepane,BorderLayout.PAGE_END);
		_game=_gamepane.getGame();
		add(_containerpane);
		
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		player.run();
		_game.start();
		
		
	}
	
	public void newGame()
	{
		_game.getBoard().newGame();
	}
	public void changeLevel(int i)
	{	
		_game.getBoard().changeLevel(i);
	}
	public void pauseGame()
	{
		_game.getBoard().gamePause();
	}
	public void resumeGame()
	{
		_game.getBoard().gameResume();
	}
	public boolean isRunning()
	{
		return _game.isRunning();
	}
	public void setTime(int time)
	{
		_infopanel.setTime(time);
	}
	public void setPoints(int points) 
	{
		_infopanel.setPoints(points);
	}
	public void setLives(int lives) 
	{
		_infopanel.setLives(lives);
	}
	public boolean validCode(String str)
	{
		return _game.getBoard().getLevel().validCode(str)!=-1;
	}
	public void changeLevelByCode(String str)
	{
		_game.getBoard().changeLevelByCode(str);
	}
	
	
	
}