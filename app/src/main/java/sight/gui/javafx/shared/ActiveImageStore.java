package sight.gui.javafx.shared;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ActiveImageStore {

	public static ActiveImageStore instance;

	public static ActiveImageStore getInstance() {
		if (instance == null) {
			instance = new ActiveImageStore();
		}

		return instance;
	}
	
	private Set<ActiveImageStoreListener> listeners = new HashSet<ActiveImageStoreListener>();

	// Stick with using BufferedImage so this could potentially be applied to other front ends...
	private BufferedImage activeImage;

	private ActiveImageStore() {

	}

	public BufferedImage getActiveImage() {
		return activeImage;
	}

	public void setActiveImage(BufferedImage activeImage) {
		this.activeImage = activeImage;
	}

	public void changeActiveImage(Object source, BufferedImage activeImage) {
		setActiveImage(activeImage);
		ActiveImageStoreEvent event = new ActiveImageStoreEvent();
		event.setImage(activeImage);
		event.setSource(source);
		event.setType(ActiveImageStoreEventType.IMAGE_CHANGED);
		fireEvent(event);
	}

	public void addListeners(Collection<ActiveImageStoreListener> listeners) {
		this.listeners.addAll(listeners);
	}

	public boolean removeListeners(Collection<ActiveImageStoreListener> listeners) {
		return this.listeners.removeAll(listeners);
	}

	public final Set<ActiveImageStoreListener> getListeners() {
		return listeners;
	}

	public void fireEvent(ActiveImageStoreEvent event) {
		listeners.stream().forEach(listener -> listener.onEvent(event));
	}
}
