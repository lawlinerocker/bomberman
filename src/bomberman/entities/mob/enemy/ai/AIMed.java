package bomberman.entities.mob.enemy.ai;
import bomberman.entities.mob.Player;
import bomberman.entities.mob.enemy.Enemy;
public class AIMed extends AI 
{
	Player _player;
	Enemy _e;
	public AIMed(Player player,Enemy e)
	{
		_player=player;
		_e=e;
	}
	public int calculateDirection()
	{
		if(_player==null)
			return random.nextInt(4);
		int vert=random.nextInt(2);
		if(vert==1)
		{
			int v=calculateRowDirection();
			if(v!=-1)
				return v;
			else
				return calculateColDirection();
		
		}
		else
		{
			int h=calculateColDirection();
			if(h!=-1)
				return h;
			else
				return calculateRowDirection();
		}
	}
	protected int calculateColDirection()
	{
		if(_player.getXTile()<_e.getXTile())
			return 3;
		else if(_player.getXTile()>_e.getXTile())
			return 1;
		return -1;
	}
	protected int calculateRowDirection()
	{
		if(_player.getYTile()<_e.getYTile())
			return 0;
		else if(_player.getYTile()>_e.getYTile())
			return 2;
		return -1;
	}
}
