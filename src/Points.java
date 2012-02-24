import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;


public class Points extends Sprite {
	public Points(Image image, int frameWidth, int frameHeight) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	  }
	public Points(Image image, int frameWidth, int frameHeight,int x,int y,int price) {
	    super(image, frameWidth, frameHeight);
	    defineReferencePixel(frameWidth / 2, frameHeight / 2);
	    this.setPosition(x, y);
	    this.price = price;
	  }
	public int price=0;
}
