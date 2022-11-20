package sight.gui.javafx.shared;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractActiveImageStoreListener implements ActiveImageStoreListener {

	public Set<Object> ignoreSources = new HashSet<Object>();

	public AbstractActiveImageStoreListener(Collection<Object> ignoreSources) {
		this.ignoreSources.addAll(ignoreSources);
	}

	@Override
	public void onEvent(ActiveImageStoreEvent event) {
		if (ignoreSources == null || !ignoreSources.contains(event.getSource())) {
			handleEvent(event);
		}
	}
	
	public abstract void handleEvent(ActiveImageStoreEvent event);

}
