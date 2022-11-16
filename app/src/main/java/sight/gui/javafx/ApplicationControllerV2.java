package sight.gui.javafx;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sight.utils.ImageUtils;

public class ApplicationControllerV2 implements Initializable {

	@FXML
	AnchorPane imageViewAnchorPane;

	@FXML
	ImageView contentImageView;

	@FXML
	MenuBar appMenuBar;

	@FXML
	MenuItem greyscaleMenuItem;

	@FXML
	MenuItem inverseMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		greyscaleMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BufferedImage buff = ImageUtils.greyscale(SwingFXUtils.fromFXImage(contentImageView.getImage(), null));
				contentImageView.setImage(SwingFXUtils.toFXImage(buff, null));
			}
			
		});

		inverseMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BufferedImage buff = ImageUtils.inverse(SwingFXUtils.fromFXImage(contentImageView.getImage(), null));
				contentImageView.setImage(SwingFXUtils.toFXImage(buff, null));
			}

		});

		Image img = new Image("img/spongebob_ripped_pants.jpg");
		contentImageView.setPreserveRatio(true);
		contentImageView.setManaged(false);
		contentImageView.setImage(img);

		imageViewAnchorPane.widthProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Anchor Pane width changed: Old <" + oldValue + "> -- New <" + newValue + ">");
				contentImageView.setFitWidth(newValue.doubleValue());
			}
			
		});

		imageViewAnchorPane.heightProperty().addListener((ChangeListener<? super Number>) new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Anchor Pane height changed: Old <" + oldValue + "> -- New <" + newValue + ">");
				contentImageView.setFitHeight(newValue.doubleValue());
			}

		});
	}
	
}
