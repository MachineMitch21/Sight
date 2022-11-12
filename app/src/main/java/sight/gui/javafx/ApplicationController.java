package sight.gui.javafx;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sight.image.Pixel;
import sight.utils.ImageUtils;

public class ApplicationController implements Initializable {

	@FXML
	AnchorPane anchorPane;

	@FXML
	VBox rootPane;

	@FXML
	ImageView contentImg;

	BufferedImage originalBuff = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rootPane.prefWidthProperty().bind(anchorPane.widthProperty());

		Image img = new Image("img/spongebob_ripped_pants.jpg");
		BufferedImage buff = SwingFXUtils.fromFXImage(img, null);
		originalBuff = buff;

		if ((buff = ImageUtils.inverse(buff)) != null) {
			img = SwingFXUtils.toFXImage(buff, null);
	
			contentImg.setImage(img);
		}
	}
}