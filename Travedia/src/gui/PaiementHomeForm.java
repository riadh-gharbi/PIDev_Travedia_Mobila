/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author riadh
 */
public class PaiementHomeForm extends BaseForm {
     Form current;
    public PaiementHomeForm() {
        current=this; //Back 
        setTitle("Paiement");
        setLayout(BoxLayout.y());
        
        add(new Label("Choisir une option"));
        Button btnAjouterPay = new Button("Ajouter Paiement");
        Button btnListPay = new Button("Liste des paiements");
         LocalNotification ln = new LocalNotification();
            ln.setId("LnMessage");
            ln.setAlertTitle("Welcome");
            ln.setAlertBody("Thanks for arriving!");
            Display.getInstance().scheduleLocalNotification(ln, System.currentTimeMillis()+1, LocalNotification.REPEAT_MINUTE);
        
    
        
        
        
       
        btnListPay.addActionListener(e-> new ListePaiementForm(current).show());
        
        //Check on the current user connected if they are an admin or not, to differentiate between Adding a payment with all its details
        if (SessionManager.getRoles() == "[ROLE_ADMIN]")
        btnAjouterPay.addActionListener(e-> new AjouterPaiementFormFull(current).show());
        else
            btnAjouterPay.addActionListener(e-> new AjouterPaiementForm(current).show());
        addAll(btnAjouterPay,btnListPay);
        
        
    }
    
}
