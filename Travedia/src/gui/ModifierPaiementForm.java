/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.PaiementService;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import entities.Paiement;
import java.util.Date;

/**
 *
 * @author riadh
 */
public class ModifierPaiementForm extends Form {
    public ModifierPaiementForm(Form previous,Paiement p)
    {
        setTitle("Modifier Paiement");
        setLayout(BoxLayout.y());
        //Get all planning
        TextField tfPlanning = new TextField("","Planning",4,TextArea.NUMERIC);
        //Get own session
        TextField tfClient= new TextField("", "Client",4,TextArea.NUMERIC);
        //Get owner from planning
        TextField tfOwner = new TextField("", "OwnerID",4,TextArea.NUMERIC);
        //set Date Creation as current time
        
        
        Date datePaiement=null;
        Picker datePicker = new Picker();
        datePicker.setType(Display.PICKER_TYPE_DATE);
        //get prix from planning
        TextField tfPrix = new TextField("","Prix",5,TextArea.NUMERIC);
        //set payment type as choice label
         ComboBox<String> cb = new ComboBox<>("En Ligne","Cash");
        //set payment status as en cours
        ComboBox<String> cbStatus = new ComboBox<>("En Cours","Effectué","Annulé");
        
        //sessionID null (for obvious reasons, Stripe won't work here)
        
        String sessionID = null;
        //
        Button btnValider = new Button("Sauvegarder Paiement");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfPlanning.getText().length()==0||tfClient.getText().length()==0
                        || tfOwner.getText().length()==0||
                        datePicker.getDate().toString().length()==0||
                        tfPrix.getText().length()==0||
                        cb.getSelectedItem().length()==0||
                        cbStatus.getSelectedItem().length()==0)
                {
                     Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                }else
                {
                    int clientId= Integer.parseInt(tfClient.getText());
                    p.setClientId(clientId);
                    int ownerId= Integer.parseInt(tfOwner.getText());
                    p.setOwnerId(ownerId);
                    p.setDate_paiement(datePicker.getDate());
                    float prix = Float.parseFloat(tfPrix.getText());
                    p.setPrix(prix);
                    p.setType_paiement(cb.getSelectedItem());
                    p.setStatut(cbStatus.getSelectedItem());
                    if (PaiementService.getInstance().modifierPaiement(p))
                    {
                       Dialog.show("Success","Connection accepted",new Command("OK"));
                    }else
                    {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                }
            }
        });
        
        
        
        addAll(tfPlanning,tfClient,tfOwner,datePicker,tfPrix,cb,cbStatus,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
