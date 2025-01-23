package Controller;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Controller.Controller;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import utils.DatabaseUtil;

public class MainController {
    public final static String username = Controller.username;
    public final static int customer_id = DatabaseUtil.getUserId(username);

    @FXML
    private Label moneyLabel;

    @FXML
    private TextField depositMoneyLabel;

    @FXML
    private Button depositButton;

    @FXML
    private Label nameLabel;


    public void initialize() {
        nameLabel.setText(username.substring(0, 1).toUpperCase() + username.substring(1));
        moneyLabel.setText(String.valueOf(DatabaseUtil.getAmountFromDB(customer_id)));

    }

    public void updateBalanceMoney() {
        float getMoneyAmount;
        float amount = Float.parseFloat(depositMoneyLabel.getText());
        getMoneyAmount = DatabaseUtil.deposit(customer_id, amount);
        if (getMoneyAmount != -1) {
            System.out.println("Money deposited successfully");
            moneyLabel.setText(String.valueOf(DatabaseUtil.getAmountFromDB(customer_id)));
        } else {
            System.out.println("Money not deposited");
        }

    }


}
