package sight.gui.javafx.controllers;

import java.util.Arrays;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sight.gui.javafx.shared.AbstractActiveImageStoreListener;
import sight.gui.javafx.shared.ActiveImageStoreEvent;

public class ApplicationActiveImageStoreListener extends AbstractActiveImageStoreListener {

	ApplicationControllerV2 controller;

	public ApplicationActiveImageStoreListener(ApplicationControllerV2 ignoreController) {
		super(Arrays.asList(ignoreController));
	}

	@Override
	public void handleEvent(ActiveImageStoreEvent event) {
		Image img = SwingFXUtils.toFXImage(event.getImage(), null);
		controller.getContentImageView().setImage(img);
	}
	
}
