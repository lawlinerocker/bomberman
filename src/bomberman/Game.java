package bomberman;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import bomberman.exceptions.BombermanException;
import bomberman.input.Keyboard;
import bomberman.gui.Frame;
import bomberman.graphics.Screen;
public class Game extends Canvas
{
	public static final double VERSION=1.0;
	public static final int TILES_SIZE=16,
							WIDTH=TILES_SIZE*(int)(31/2),
							HEIGHT=13*TILES_SIZE;
	public static int SCALE=3;
	static final String TITLE="Bomberman"+VERSION;

	// Configs
	private static final int BOMBRATE=1;
	private static final int BOMBRADIUS=1;
	private static final double PLAYERSPEED=1.0;
	public static final int TIME=300;
	public static final int POINTS=0;
	public static final int LIVES=3;
	protected static int SCREENDELAY=3;
	//Powerup modified
	protected static int bombRate=BOMBRATE;
	protected static int bombRadius=BOMBRADIUS;
	protected static double playerSpeed=PLAYERSPEED;

	//Time in the screen
	protected int _screenDelay=SCREENDELAY;
	private Keyboard _input;
	private boolean _running=false;
	private boolean _paused=true;
	private Board _board;
	private Screen screen;
	private Frame _frame;

	//Render Stored Image
	private BufferedImage image=new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private int[] pixels=((DataBufferInt)image.getRaster().getDataBuffer()).getData();

	public Game(Frame frame) throws BombermanException
	{
		_frame=frame;
		_frame.setTitle(TITLE);
		screen=new Screen(WIDTH,HEIGHT);
		_input=new Keyboard();
		_board=new Board(this,_input,screen);
		addKeyListener(_input);

	}
	// rendering game
	private void renderGame()
	{
		BufferStrategy bs=getBufferStrategy(); //create buffer to stores images by canvas
		if(bs==null)
		{
			createBufferStrategy(3); // triple buffer
			return;
		}
		screen.clear();
		_board.render(screen);
		for(int i=0;i<pixels.length;i++)
		{
			pixels[i]=screen._pixels[i];
		}
		Graphics g=bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(),null);
		_board.renderMessages(g);
		g.dispose(); // exposed resources
		bs.show(); //make it visible

	}
	//rendering Screen
	private void renderScreen()
	{
		BufferStrategy bs=getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		Graphics g=bs.getDrawGraphics();
		_board.drawScreen(g);
		g.dispose();
		bs.show();


	}
	public void update()
	{
		_input.update();
		_board.update();
	}
	public void start()
	{
		_running=true;
		long lastTime=System.nanoTime();
		long timer=System.currentTimeMillis();
		final double ns=1000000000.0 / 60.0; //nanosecond, 60 frames per second
		double delta=0;
		int frames=0;
		int updates=0;
		requestFocus();
		while(_running)
		{
			long now=System.nanoTime();
			delta+=(now-lastTime)/ns;
			lastTime=now;
			while(delta>=1)
			{
				update();
				updates++;
				delta--;
			}
			if(_paused)
			{
				if(_screenDelay<=0)
				{
					_board.setShow(-1);
					_paused=false;
				}
				renderScreen();
			}
			else
			{
				renderGame();
			}
			frames++;
			if(System.currentTimeMillis()-timer>1000) //once per second
			{
				_frame.setTime(_board.subtractTime());
				_frame.setPoints(_board.getPoints());
				_frame.setLives(_board.getLives());
				timer+=1000;
				_frame.setTitle(TITLE+" | "+updates+" rate, "+frames+" fps");
				updates=0;
				frames=0;
				if(_board.getShow()==2)
					--_screenDelay;
			}
		}
	}
	///////////////////////////////////////
	///////////////////////
	///////////////
	//////
	public static double getPlayerSpeed()
	{
		return playerSpeed;
	}
	public static int getBombRate()
	{
		return bombRate;
	}
	public static int getBombRadius()
	{
		return bombRadius;
	}
	public static void addPlayerSpeed(double i)
	{
		playerSpeed+=i;
	}
	public static void addBombRadius(int i)
	{
		bombRadius+=i;
	}
	public static void addBombRate(int i)
	{
		bombRate+=i;
	}
	///Delay
	public int getScreenDelay()
	{
		return _screenDelay;
	}
	public void decreaseScreenDelay()
	{
		_screenDelay--;
	}
	public void resetScreenDelay()
	{
		_screenDelay=SCREENDELAY;

	}
	public Board getBoard()
	{
		return _board;
	}
	public Keyboard getInput()
	{
		return _input;
	}
	public void run()
	{
		_running=true;
		_paused=false;
	}
	public boolean isRunning()
	{
		return _running;
	}
	public boolean isPaused()
	{
		return _paused;
	}
	public void pause()
	{
		_paused=true;
	}



}
