package sight.gui.javafx.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import sight.image.pixel.operations.PixelOperationEvent;
import sight.image.pixel.operations.PixelOperationImageViewListener;
import sight.image.pixel.operations.PixelOperationListener;
import sight.image.pixel.operations.PixelOperationMultiStep;
import sight.image.pixel.operations.basic.PixelOperationGreyscale;
import sight.image.pixel.operations.basic.PixelOperationInverse;
import sight.utils.ImageUtils;

public class ApplicationController implements Initializable {

	@FXML
	AnchorPane anchorPane;

	@FXML
	VBox rootPane;

	@FXML
	BorderPane contentPane;

	@FXML
	GridPane contentCenterGridPane;

	@FXML
	ImageView contentImg;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rootPane.prefWidthProperty().bind(anchorPane.widthProperty());

		Image img = new Image("img/cat-dragon.jpg");
		BufferedImage buff = SwingFXUtils.fromFXImage(img, null);
		contentImg.setImage(ImageUtils.performFullOperation(img, new PixelOperationInverse()));

		// ImageUtils.performLapsedOperation(
		// 	buff, 
		// 	0, 
		// 	buff.getWidth(), 
		// 	0, 
		// 	buff.getHeight(), 
		// 	10, 
		// 	new PixelOperationGreyscale(),
		// 	new PixelOperationImageViewListener(contentImg)
		// );

		ImageUtils.performLapsedOperation(
			buff, 
			0, 
			buff.getWidth(), 
			0, 
			buff.getHeight(), 
			1, 
			new PixelOperationInverse(), 
			new PixelOperationImageViewListener(contentImg)
		);
	}
}