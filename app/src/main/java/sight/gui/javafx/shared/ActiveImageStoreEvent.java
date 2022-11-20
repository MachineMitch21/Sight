package sight.gui.javafx.shared;

import java.awt.image.BufferedImage;

/**
 * 
 * Simple class to pass data to active image store listeners
 * 
 */
public class ActiveImageStoreEvent {
	
	public Object source;
	public BufferedImage image;
	public ActiveImageStoreEventType type;

	public Object getSource() {
		return source;
	}

	public void setSource(Object source) {
		this.source = source;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public ActiveImageStoreEventType getType() {
		return type;
	}

	public void setType(ActiveImageStoreEventType type) {
		this.type = type;
	}

}
