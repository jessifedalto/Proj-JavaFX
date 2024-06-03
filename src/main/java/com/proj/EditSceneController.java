package com.proj;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.proj.model.InvestmentData;
import com.proj.model.UserData;

public class EditSceneController {
    private UserData user;

    public EditSceneController() {}

    public static Scene CreateScene(UserData user) throws Exception {
        URL sceneUrl = EditSceneController.class.getResource("edit-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        EditSceneController controller = loader.getController();
        controller.user = user;
        controller.loadtInfo();

        return scene;
    }

    @FXML
    protected Button btRegister;
    @FXML
    protected TextField tfUsername;
    @FXML
    protected PasswordField pfUserpass;
    @FXML
    protected TextField tfUserEmail;
    @FXML
    protected DatePicker tfUserBirth;

    public void submit(ActionEvent e) throws Exception  {
        submit();
    }

    public void submit() throws Exception {
        if (tfUserEmail.getText().equals("") && pfUserpass.getText().equals("")) {
            Stage crrStage = (Stage)btRegister
            .getScene().getWindow();
            crrStage.close();

            Stage stage = new Stage();
            Scene scene = MainSceneController.CreateScene(user);
            stage.setScene(scene);
            stage.show();
        } else {
            if (!tfUserEmail.getText().equals(user.getUseremail()) && pfUserpass.getText().equals("")) {
                Session session = HibernateUtil
                .getSessionFactory()
                .getCurrentSession();
                Transaction transaction = session.beginTransaction();

                Query queryEdit = session.createQuery(
                    "UPDATE UserData SET UserEmail = :useremail WHERE IDUser = :user"
                );
                queryEdit.setParameter("useremail", tfUserEmail.getText());
                queryEdit.setParameter("user", user.getIDUser());
                
                queryEdit.executeUpdate();
                transaction.commit(); 
                
                Alert alert = new Alert(
                    AlertType.INFORMATION,
                    "Email alterado com sucesso!",
                    ButtonType.OK
                );

                alert.showAndWait();
                Stage crrStage = (Stage)btRegister
                .getScene().getWindow();
                crrStage.close();
    
                Stage stage = new Stage();
                Scene scene = MainSceneController.CreateScene(user);
                stage.setScene(scene);
                stage.show();

            } else if (tfUserEmail.getText().equals("") && !pfUserpass.getText().equals(user.getUserpass())) {
                Session session = HibernateUtil
                .getSessionFactory()
                .getCurrentSession();
                Transaction transaction = session.beginTransaction();

                Query queryEdit = session.createQuery(
                    "UPDATE UserData SET UserPass = :userpass WHERE IDUser = :user"
                );
                queryEdit.setParameter("userpass", pfUserpass.getText());
                queryEdit.setParameter("user", user.getIDUser());
                
                queryEdit.executeUpdate();
                transaction.commit(); 
                
                Alert alert = new Alert(
                    AlertType.INFORMATION,
                    "Senha alterada com sucesso!",
                    ButtonType.OK
                );

                alert.showAndWait();
                Stage crrStage = (Stage)btRegister
                .getScene().getWindow();
                crrStage.close();
    
                Stage stage = new Stage();
                Scene scene = MainSceneController.CreateScene(user);
                stage.setScene(scene);
                stage.show();
            } else if (!tfUserEmail.getText().equals(user.getUseremail()) && !pfUserpass.getText().equals(user.getUserpass())) {
                Session session = HibernateUtil
                .getSessionFactory()
                .getCurrentSession();
                Transaction transaction = session.beginTransaction();

                Query queryEdit = session.createQuery(
                    "UPDATE UserData SET UserEmail = :useremail WHERE IDUser = :user"
                );
                queryEdit.setParameter("useremail", tfUserEmail.getText());
                queryEdit.setParameter("user", user.getIDUser());
                
                queryEdit.executeUpdate();

                queryEdit = session.createQuery(
                    "UPDATE UserData SET UserPass = :userpass WHERE IDUser = :user"
                );
                queryEdit.setParameter("userpass", pfUserpass.getText());
                queryEdit.setParameter("user", user.getIDUser());

                queryEdit.executeUpdate();

                transaction.commit(); 
                
                Alert alert = new Alert(
                    AlertType.INFORMATION,
                    "Email e senha alterados com sucesso!",
                    ButtonType.OK
                );

                alert.showAndWait();
                Stage crrStage = (Stage)btRegister
                .getScene().getWindow();
                crrStage.close();
    
                Stage stage = new Stage();
                Scene scene = MainSceneController.CreateScene(user);
                stage.setScene(scene);
                stage.show();
            }
         }
    }

    public void loadtInfo() {
        tfUsername.setPromptText(user.getUsername());
        tfUserEmail.setPromptText(user.getUseremail());
        tfUserBirth.setPromptText((user.getUserbirth()).toString());
    }

}