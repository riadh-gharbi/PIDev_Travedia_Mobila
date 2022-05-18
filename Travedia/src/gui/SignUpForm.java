/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

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
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.Vector;
import com.codename1.ui.Toolbar;
import com.Travedia.myapp.ServiceUtilisateur;
import com.google.gson.JsonArray;

//import Services.ServiceUtilisateur;
/**
 * Signup UI
 *
 * @author ibtihel
 */
public class SignUpForm extends BaseForm {

    public SignUpForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Salut!  ", "WelcomeWhite"),
                new Label("Creer Compte", "WelcomeBlue")
        );

        getTitleArea().setUIID("Container");

        TextField nom = new TextField("", "Nom");
        TextField prenom = new TextField("", "Prenom");
        TextField email = new TextField("", "email", 20, TextField.ANY);
        TextField password = new TextField("", "Mot De Passe", 20, TextField.PASSWORD);

        //Role
        Vector<String> vectorRole;
        vectorRole = new Vector();

        vectorRole.add("Guide");
        vectorRole.add("Voyageur");
        JsonArray arrayG = new JsonArray();
        arrayG.add("ROLE_GUIDE");
        JsonArray arrayV = new JsonArray();
        arrayV.add("ROLE_VOYAGEUR");

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

        Button createAccount = new Button("S'inscrire");
        createAccount.setUIID("LoginButton");
        createAccount.addActionListener((l) -> {
            //nom.getText().toString(), prenom.getText().toString(), email.getText().toString(), password.getText().toString(), roles.getSelectedItem().toString(), langue.getSelectedItem().toString()
            ServiceUtilisateur.getInstance().signup(nom, prenom, email, password, roles.getSelectedItem()=="Guide"?arrayG.toString():arrayV.toString(), langue);

            Dialog.show("Success", "Bienvenue", "OK", null);
            new Login(theme).show();
            refreshTheme();

        });

        
        Button login= new Button ("Se Connecter");
               //addStringValue("", login);
               login.addActionListener(e->{
            new Login(theme).show();
        });
               
       /* Button  = new Button("Se Connecter");
        login.addActionListener((l) -> {
            new ProfileForm(theme).show();
        });
        login.setUIID("CreateNewAccountButton"); */

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
                createAccount,
                login
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }
}
