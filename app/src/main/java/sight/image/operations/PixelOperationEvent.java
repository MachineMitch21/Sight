package sight.image.operations;

import java.awt.image.BufferedImage;

public class PixelOperationEvent {
	
	Object source;
	BufferedImage buff;

	public PixelOperationEvent(Object source, BufferedImage buff) {
		this.source = source;
		this.buff = buff;
	}

	public Object getSource() {
		return source;
	}

	public BufferedImage getBuff() {
		return buff;
	}

}
