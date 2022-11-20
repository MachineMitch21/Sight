package sight.gui.javafx.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import sight.gui.javafx.shared.ActiveImageStore;
import sight.utils.ImageUtils;

public class ApplicationControllerV2 implements Initializable {

	@FXML
	AnchorPane imageViewAnchorPane;

	@FXML
	ImageView contentImageView;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ActiveImageStore.getInstance().addListeners(Arrays.asList(
			new ApplicationActiveImageStoreListener(this)
		));

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

	public ImageView getContentImageView() {
		return contentImageView;
	}
	
}
