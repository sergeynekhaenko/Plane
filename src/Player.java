

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class Player extends Sprite{
	private TiledLayer exit;
	private String DIRECTION;
	public int points=0;
	public int lives=100;
	private Game game;
	private Points[] points_;
	public Player(Image image, int frameWidth, int frameHeight) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	  }
	public Player(Image image, int frameWidth, int frameHeight,int x,int y) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	    this.setPosition(x, y);
	    this.setPosition(x, y);
	  }
	public void setExit(TiledLayer exit)
	{
		this.exit = exit;
	}
	public void setGeme(Game game)
	{
		this.game = game;
	}
	public void setPoints(Points[] points)
	{
		this.points_ = points;
	}
	public void moveLeft()
	{
		if(!this.collidesWith(exit, false))
		{
				this.move(-6, 0);
				this.setDirection("BACK");
		}
		else
		{
			game.gameOver();
		}
	}
	public void moveRight(boolean chanegSprite)
	{
		if(!this.collidesWith(exit, false))
		{
				this.move(+6, 0);
				if(chanegSprite)
				{
					this.setDirection("FORWARD");
				}
		}
		else
		{
			game.gameOver();
		}
	}
	public void setDirection(String direction)
	{
		if(direction == "FORWARD" || direction == "BACK" || direction =="UP" || direction == "DOWN")
		{
			this.DIRECTION = direction;
		}
			
	}
	public void changeSprite()
	{
		if(DIRECTION == "FORWARD")
		{
			this.setFrame(0);
		}
		if(DIRECTION == "BACK")
		{
			this.setFrame(1);
		}
		if(DIRECTION == "UP")
		{
			this.setFrame(1);
		}
		if(DIRECTION == "DOWN" )
		{
			this.setFrame(2);
		}
		for(int i=0;i<points_.length;i++)
		{
			if(collidesWith(points_[i], true))
			{
				points += points_[i].price;
				points_[i].setVisible(false);
			}
		}
	}
	public void moveUp()
	{
		if(!this.collidesWith(exit, false))
		{
				this.move(0, -6);
				this.setDirection("UP");
		}
		else
		{
			game.gameOver();
		}
	}
	public void moveDown()
	{
		if(!this.collidesWith(exit, false))
		{
				this.move(0, +6);
				this.setDirection("DOWN");
		}
		else
		{
			game.gameOver();
		}
	}
}