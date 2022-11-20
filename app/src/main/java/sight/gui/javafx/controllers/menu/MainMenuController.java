package sight.gui.javafx.controllers.menu;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import sight.gui.javafx.shared.ActiveImageStore;
import sight.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenuController implements Initializable {

	@FXML
	MenuBar appMenuBar;

	@FXML
	MenuItem greyscaleMenuItem;

	@FXML
	MenuItem inverseMenuItem;

	@FXML
	MenuItem openMenuItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		greyscaleMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BufferedImage buff = ImageUtils.greyscale(ActiveImageStore.getInstance().getActiveImage());
				ActiveImageStore.getInstance().changeActiveImage(this, buff);
				event.consume();
			}

		});

		inverseMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				BufferedImage buff = ImageUtils.inverse(ActiveImageStore.getInstance().getActiveImage());
				ActiveImageStore.getInstance().changeActiveImage(this, buff);
				event.consume();
			}

		});
		openMenuItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				FileChooser openFileChooser = new FileChooser();
				openFileChooser.setTitle("Open Image");
				openFileChooser.getExtensionFilters().addAll(Arrays.asList(
						new ExtensionFilter("All Files", "*.jpg", "*.png", "*.bmp")));

				File f = openFileChooser.showOpenDialog(appMenuBar.getScene().getWindow());
				if (f != null) {
					try {
						ActiveImageStore.getInstance().changeActiveImage(this, SwingFXUtils.fromFXImage(new Image(new FileInputStream(f)), null));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("No file was chosen for open action...");
				}
			}

		});
	}
	
}
