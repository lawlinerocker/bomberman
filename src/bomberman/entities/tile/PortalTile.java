package bomberman.entities.tile;
import bomberman.Board;
import bomberman.sfx.AudioPlayer;
import bomberman.entities.Entity;
import bomberman.graphics.Sprite;
import bomberman.entities.mob.Player;
public class PortalTile extends Tile
{
	AudioPlayer next=new AudioPlayer("new");
	protected Board _board;
	public PortalTile(int x,int y,Board board,Sprite sprite)
	{
		super(x,y,sprite);
		_board=board;
	}
	@Override
	public boolean collide(Entity e)
	{
		if(e instanceof Player)
		{
			if(_board.detectNoEnemies()==false)
				
				return false;

			if(e.getXTile()==getX() && e.getYTile()==getY())
			{
				if(_board.detectNoEnemies())
					next.run();
					_board.nextLevel();
			}

		return true;
		
		}

	return false;
	
	}
}
