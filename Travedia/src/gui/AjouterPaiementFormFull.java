/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.PaiementService;
import com.Travedia.myapp.ReclamationService;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entities.Paiement;
import entities.Reclamation;
import java.util.Date;

/**
 *
 * @author riadh
 * This Form will appear on to the ADMIN
 */
public class AjouterPaiementFormFull extends BaseForm {
     public AjouterPaiementFormFull(Form previous) {
        setTitle("Ajouter paiement complete");
        setLayout(BoxLayout.y());
        
        //Get all planning
        TextField tfPlanning = new TextField("","Planning",4,TextArea.NUMERIC);
        //Get own session
        TextField tfClient= new TextField("", "Client",4,TextArea.NUMERIC);
        //Get owner from planning
        TextField tfOwner = new TextField("", "OwnerID",4,TextArea.NUMERIC);
        //set Date Creation as current time
        
        //set date paiement as null
        Date datePaiement=null;
        //get prix from planning
        TextField prix = new TextField("","Prix",5,TextArea.NUMERIC);
        //set payment type as choice label
         ComboBox<String> cb = new ComboBox<>("En Ligne","Cash");
        //set payment status as en cours
        String paymentStatus = "En Cours";
        //sessionID null (for obvious reasons, Stripe won't work here)
        String sessionID = null;
        //
        Button btnValider = new Button("Sauvegarder Paiement");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((cb.getSelectedItem().length()==0)||(tfPlanning.getText().length()==0)||tfOwner.getText().length()==0||tfClient.getText().length()==0
                        ||prix.getText().length()==0)
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Paiement p = new Paiement();
                        p.setDate_creation(new Date(System.currentTimeMillis()));
                        p.setClientId(Integer.parseInt(tfClient.getText()));
                        p.setOwnerId(Integer.parseInt(tfOwner.getText()));
                        //p.setDate_paiement(datePaiement);
                        p.setPlanningId(Integer.parseInt(tfPlanning.getText()));
                        p.setPrix(Float.parseFloat(prix.getText()));
                        //p.setSessionID(sessionID);
                        p.setStatut(paymentStatus);
                        p.setType_paiement(cb.getSelectedItem());
                        
                        if(PaiementService.getInstance().ajouterPaiement(p))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                                }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfPlanning,tfClient,tfOwner,prix,cb,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
}
