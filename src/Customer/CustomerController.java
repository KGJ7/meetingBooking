package Customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerController {

    @FXML
    private Button openRoomBookerButton;


    @FXML
    public void openRoomBooker() {
        try {
            Stage old = (Stage) this.openRoomBookerButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/RoomBooker/RoomBooker.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/RoomBooker.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Admin Dashboard");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
