package Caterer;

import DBUtil.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CatererController {

    @FXML
    private Button backButton;
    @FXML
    private TableView<CatererModel> CatererTable;
    @FXML
    private TableColumn<CatererModel, String> roomIDColumn;
    @FXML
    private TableColumn<CatererModel, String> refreshmentColumn;
    @FXML
    private TableColumn<CatererModel, String> RefreshmentTimeColumn;


    @FXML
    private void returnToAdmin() {
        try {
            Stage old = (Stage) this.backButton.getScene().getWindow();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("/Admin/AdminFXML.fxml").openStream());
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/Stylesheets/Admin.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Admin");
            stage.setResizable(false);
            old.close();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void initializeTable() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ObservableList<CatererModel> data = FXCollections.observableArrayList();
            String sql = "SELECT * FROM Refreshments";
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                data.add(new CatererModel(rs.getString(1), rs.getString(3), rs.getString(4)));
            }

            roomIDColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
            RefreshmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            refreshmentColumn.setCellValueFactory(new PropertyValueFactory<>("refreshment"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initialize() {
        initializeTable();
    }
}
