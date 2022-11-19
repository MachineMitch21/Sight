package sight.image.pixel.operations;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PixelOperationImageViewListener implements PixelOperationListener {

	ImageView imgView;

	public PixelOperationImageViewListener(ImageView imgView) {
		this.imgView = imgView;
	}

	@Override
	public void handlePostOperation(PixelOperationEvent event) {
		Image i = SwingFXUtils.toFXImage(event.getBuff(), null);
		imgView.setImage(i);
	}

	public ImageView getImgView() {
		return imgView;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}
	
}
