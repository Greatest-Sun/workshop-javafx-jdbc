package gui;

import db.DBException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class SellerFormController implements Initializable {
    private Seller dp;
    private SellerService service;
    private List<DataChangeListener> dcListeners = new ArrayList<>();
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpBirthDate;
    @FXML
    private TextField txtBaseSalary;
    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorBirthDate;
    @FXML
    private Label labelErrorBaseSalary;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (dp == null) {
            throw new IllegalStateException("Seller was null");
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
            mvc.onMenuItemSellerAction();
            */
        } catch (DBException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        }
    }

    private void notifyDataChangeListener() {
        for(DataChangeListener obj : dcListeners) {
            obj.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();
        ValidationException exception = new ValidationException("Validation error.");
        obj.setId(Utils.tryParsetoInt(txtId.getText()));
        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addErros("Name", "Field can't be empty.");
        }
        obj.setName(txtName.getText());
        if (exception.getErrors().size() > 0) {
            throw exception;
        }
        return obj;
    }

    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    public void setSeller(Seller dp) {
        this.dp = dp;
    }
    public void setSellerService(SellerService service) {
        this.service = service;
    }
    public void subscribeDCL(DataChangeListener dcl) {
        dcListeners.add(dcl);
    }

    public void updateFormData() {
        if (dp == null) {
            throw new IllegalStateException("Entity was null.");
        }
        Locale.setDefault(Locale.US);
        txtId.setText(String.valueOf(dp.getId()));
        txtName.setText(dp.getName());
        txtEmail.setText(dp.getEmail());
        txtBaseSalary.setText(String.format("%.2f", dp.getBaseSalary()));
        if (dp.getBirthDate() != null) {
            dpBirthDate.setValue(LocalDate.ofInstant(dp.getBirthDate().toInstant(), ZoneId.systemDefault()));
        }
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        if (fields.contains("Name")) {
            labelErrorName.setText(errors.get("Name"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNotes();
    }

    private void initializeNotes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldDouble(txtBaseSalary);
        Constraints.setTextFieldMaxLength(txtEmail, 60);
        Utils.formatDatePicker(dpBirthDate, "dd/MM/yyyy");
    }
}
