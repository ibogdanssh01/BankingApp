package Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Controller.Controller;
import javafx.scene.control.Label;

public class MainController {

    @FXML
    private ImageView imageView;

    @FXML
    private Circle clipCircle;

    @FXML
    private Label nameLabel;

    public void initialize() {
        System.out.println(Controller.username);
        nameLabel.setText(Controller.username.substring(0, 1).toUpperCase() + Controller.username.substring(1));
    }
}
