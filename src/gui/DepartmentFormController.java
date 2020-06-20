package gui;

import db.DBException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentFormController implements Initializable {
    private Department dp;
    private DepartmentService service;
    private List<DataChangeListener> dcListeners = new ArrayList<>();
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private Label labelErrorName;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (dp == null) {
            throw new IllegalStateException("Department was null");
        }
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        try {
            dp = getFormData();
            service.saveOrUpdate(dp);
            Utils.currentStage(event).close();
            notifyDataChangeListener();
            /*Update screen (my solution)
            MainViewController mvc = new MainViewController();
            mvc.onMenuItemDepartmentAction();
            */
        } catch (DBException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListener() {
        for(DataChangeListener obj : dcListeners) {
            obj.onDataChanged();
        }
    }

    private Department getFormData() {
        Department obj = new Department();
        obj.setId(Utils.tryParsetoInt(txtId.getText()));
        obj.setName(txtName.getText());
        return obj;
    }

    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    public void setDepartment(Department dp) {
        this.dp = dp;
    }
    public void setDepartmentService(DepartmentService service) {
        this.service = service;
    }
    public void subscribeDCL(DataChangeListener dcl) {
        dcListeners.add(dcl);
    }

    public void updateFormData() {
        if (dp == null) {
            throw new IllegalStateException("Entity was null.");
        }
        txtId.setText(String.valueOf(dp.getId()));
        txtName.setText(dp.getName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNotes();
    }

    private void initializeNotes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 30);
    }
}
