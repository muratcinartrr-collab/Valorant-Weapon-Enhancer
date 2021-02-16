package ValorantWeaponEnhancer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.jnativehook.NativeHookException;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ValorantWeaponEnhancerController implements Initializable {

    private final static int COOLDOWN_MAX_VALUE = 600;
    private final static int COOLDOWN_MIN_VALUE = 10;

    private final static int COOLDOWN_INCREMENT_VALUE = 10;
    private final static int COOLDOWN_DECREMENT_VALUE = 10;
	
	private final static String STATUSTEXT_INIT_TEXT = "AWAITING";
	private final static String STATUSTEXT_ACTIV_TEXT = "ACTIVATED";
	private final static String STATUSTEXT_DEACTIV_TEXT = "DEACTIVATED";
	private final static String STARTBNT_START_TEXT = "START";
	private final static String STARTBNT_STOP_TEXT = "STOP";

    private static ValorantWeaponEnhancer vwe = null;
    private boolean isStartBtnClicked = false;

    @FXML
    private Button startBtn;

    @FXML
    private TextField statusTextFld;

    @FXML
    private TextField cooldownTextFld;


    public ValorantWeaponEnhancerController() throws AWTException, NativeHookException {
        new ValorantWeaponEnhancer().run();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusTextFld.setStyle("-fx-background-color: grey;");
        statusTextFld.setText(STATUSTEXT_INIT_TEXT);
        statusTextFld.setOpacity(1);
        startBtn.setStyle("-fx-text-fill: green");
        updateCooldownTextField(vwe.cooldownMillis);
    }

    public void start() {
        if (!isStartBtnClicked) {
            isStartBtnClicked = true;
            startBtn.setText(STARTBNT_STOP_TEXT);
            startBtn.setStyle("-fx-text-fill: red");
            statusTextFld.setStyle("-fx-background-color: green;");
            statusTextFld.setText(STATUSTEXT_ACTIV_TEXT);
            vwe.activate();
        } else {
            isStartBtnClicked = false;
            startBtn.setText(STARTBNT_START_TEXT);
            startBtn.setStyle("-fx-text-fill: green");
            statusTextFld.setStyle("-fx-background-color: red;");
            statusTextFld.setText(STATUSTEXT_DEACTIV_TEXT);
            vwe.deactivate();
        }
    }

    public void cooldownIncrement() {
        if (vwe.cooldownMillis >= COOLDOWN_MAX_VALUE) {
            return;
        }

        vwe.cooldownMillis += COOLDOWN_INCREMENT_VALUE;
        updateCooldownTextField(vwe.cooldownMillis);
    }

    public void cooldownDecrement() {
        if (vwe.cooldownMillis <= COOLDOWN_MIN_VALUE) {
            return;
        }

        vwe.cooldownMillis -= COOLDOWN_DECREMENT_VALUE;
        updateCooldownTextField(vwe.cooldownMillis);
    }

    private void updateCooldownTextField(int amount) {
        cooldownTextFld.setText(String.valueOf(amount));
    }
}
