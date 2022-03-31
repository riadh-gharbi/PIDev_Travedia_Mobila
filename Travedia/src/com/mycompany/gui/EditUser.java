/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.services.ServiceUtilisateur;
import java.util.Vector;

/**
 *
 * @author Ibtihel
 */
public class EditUser extends BaseForm {

    public EditUser(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Salut, ", "WelcomeWhite"),
                new Label("Modifier Votr Compte", "WelcomeBlue")
        );

        getTitleArea().setUIID("Container");

        int eval = SessionManager.getEvaluation();
        String evalString = Integer.toString(eval);

        TextField nom = new TextField(SessionManager.getNom(), "Nom ...");
        TextField prenom = new TextField(SessionManager.getPrenom(), "Prenom ...");
        TextField email = new TextField(SessionManager.getEmail(), "Email ...");
        TextField image = new TextField(SessionManager.getImage(), "Image ...");
        TextField evaluation = new TextField(evalString, "Evaluation ...");
        TextField description = new TextField(SessionManager.getDescription(), "Description ...");
        TextField password = new TextField("", "Password...");

        //Role
        Vector<String> vectorRole;
        vectorRole = new Vector();

        vectorRole.add("Guide");
        vectorRole.add("Voyageur");

        ComboBox<String> roles;
        roles = new ComboBox<>(vectorRole);

        //Langue
        Vector<String> vectorLangue;
        vectorLangue = new Vector();

        vectorLangue.add("Arabe");
        vectorLangue.add("Anglais");
        vectorLangue.add("Français");

        ComboBox<String> langue;
        langue = new ComboBox<>(vectorLangue);

        nom.getAllStyles().setMargin(LEFT, 0);
        prenom.getAllStyles().setMargin(LEFT, 0);
        email.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);

        Label firstNameIcon = new Label("", "TextField");
        Label lastNameIcon = new Label("", "TextField");
        Label emailIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");

        firstNameIcon.getAllStyles().setMargin(RIGHT, 0);
        lastNameIcon.getAllStyles().setMargin(RIGHT, 0);
        emailIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);

        FontImage.setMaterialIcon(firstNameIcon, FontImage.MATERIAL_PERSON, 3);
        FontImage.setMaterialIcon(lastNameIcon, FontImage.MATERIAL_PERSON, 3);
        FontImage.setMaterialIcon(emailIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);

        Button confirmer = new Button("Confirmer");
        confirmer.setUIID("LoginButton");
        confirmer.addActionListener((l) -> {
            //nom.getText().toString(), prenom.getText().toString(), email.getText().toString(), password.getText().toString(), roles.getSelectedItem().toString(), langue.getSelectedItem().toString()
            //userService.getInstance().editUser(SessionManager.getId(), firstName.getText().toString(), lastName.getText().toString(), password.getText().toString());
            ServiceUtilisateur.getInstance().editUser(SessionManager.getId(), SessionManager.getProfileId(), nom, prenom, email, evaluation, description, image, password, roles, langue);
            // ServiceUtilisateur.getInstance().editUser(TOP, nom, prenom, password, email, roles, langue);

            Dialog.show("Success", "Welcome", "OK", null);
            new Login(theme).show();
            refreshTheme();

        });

        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        Container by = BoxLayout.encloseY(
                welcome,
                spaceLabel,
                BorderLayout.center(nom).
                        add(BorderLayout.WEST, firstNameIcon),
                BorderLayout.center(prenom).
                        add(BorderLayout.WEST, lastNameIcon),
                BorderLayout.center(email).
                        add(BorderLayout.WEST, emailIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                BorderLayout.center(roles),
                BorderLayout.center(langue),
                confirmer
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
