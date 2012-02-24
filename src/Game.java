

import java.io.IOException;
import java.util.Random;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;


public class Game extends GameCanvas implements Runnable {

	private TiledLayer background; // фон
	private LayerManager layerManager;
	private Player player;
	public int cameraX = 0;
	public boolean gameOvered = false;
	private boolean runing = false; // запущена ли игра
	
	public Game() throws IOException{
		super(true);
		player = createPlayer();
		this.createGameExit();
		background = createBackground();
		layerManager = new LayerManager();
		layerManager.append(player);
		this.createPoints();
		layerManager.append(background);
	}

	private Player createPlayer() throws IOException {
		Image image = Image.createImage("/plane.png");
		Player pl = new Player(image,55,25,0,0);
		pl.setGeme(this);
		return pl;
	}

	public void createPoints() throws IOException
	{
		Points[] p = new Points[70];
		int x=100;
		Image image = Image.createImage("/points.png");
		Random r = new Random();
		int y=r.nextInt(getHeight()-45);
		for(int i=0;i<p.length;i++)
		{
			y=r.nextInt(getHeight()-45);
			x+= r.nextInt(300);
			p[i] = new Points(image,25,45,x,y,r.nextInt(150));
			layerManager.append(p[i]);
		}
		player.setPoints(p);
	}
	private void createGameExit() throws IOException {
		Image image = Image.createImage("/bg.png");
		int[] map = {
				0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0, 	0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 0,	 0, 0, 0, 0, 1
		};
		/*
		 * прорисовываем карту , используя массив (возможные значения 0-3)
		 */
		TiledLayer tiledLayer = new TiledLayer(60, 1 , image, 170, 291);
		for (int i = 0; i < 60; i++) 
		{
			tiledLayer.setCell(i, 0, map[i]);
		}
		this.player.setExit(tiledLayer);
	}
	
	
	private TiledLayer createBackground() throws IOException {
		Image image = Image.createImage("/bg.png");
		TiledLayer tiledLayer = new TiledLayer(60, 1 , image, 170, 291);
				for (int i = 0; i < 60; i++) 
				{
					tiledLayer.setCell(i, 0, 1);
				}
		return tiledLayer;
	}
	private TiledLayer createGameOver() throws IOException {
		Image image = Image.createImage("/game_over.png");
		TiledLayer tiledLayer = new TiledLayer(1, 1 , image, 240, 291);
				tiledLayer.setCell(0, 0, 1);
		tiledLayer.setPosition(cameraX, 0);
		return tiledLayer;
	}
	
	
	public void run() {
		Graphics g = getGraphics();
		int timeStep = 80;
	    while (runing) {
	      long start = System.currentTimeMillis();
	      
	      input();
	      render(g);
	      
	      long end = System.currentTimeMillis();
	      int duration = (int)(end - start);
	      
	      if (duration < timeStep) {
	        try { Thread.sleep(timeStep - duration); }
	        catch (InterruptedException ie) {}
	      }
	    }
	}
	
	private void render(Graphics g) {
	    player.changeSprite();
	    
	    layerManager.paint(g, 0, 0);
	    this.moveCamera();
	    
	    flushGraphics();
	}

	public void start()
	{
		/*
		 * создаем поток и запускаем его
		 */
		runing = true;
	    Thread t = new Thread(this);
	    t.start();
	}
	private void input() {
	    int keyStates = getKeyStates();
	    if ((keyStates & LEFT_PRESSED) != 0)
	    {
	    	player.moveLeft();
	    }
	    else if ((keyStates & RIGHT_PRESSED) != 0)
	    {
	    	player.moveRight(true);
	    }
	    else if ((keyStates & UP_PRESSED) != 0)
	    {
	    	player.moveUp();
	    }
	    else if ((keyStates & DOWN_PRESSED) != 0)
	    {
	    	player.moveDown();
	    }
	  }

	public void stop() {
		runing = false;
	}
	public void moveCamera()
	{
		if(!gameOvered)
		{
			layerManager.setViewWindow(cameraX, 0, getWidth(), getHeight());
			Graphics g = getGraphics();
			g.setColor(0xFFFFFF);
			g.drawString("points: "+player.points, getWidth(), getHeight(),
		        Graphics.RIGHT | Graphics.BOTTOM);
			paint(g);
			player.moveRight(false);
			cameraX+=6;
		}
	}
	public void gameOver()
	{
		this.gameOvered = true;
		this.layerManager.remove(background);
		this.layerManager.remove(player);
		try {
			this.layerManager.append(createGameOver());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
