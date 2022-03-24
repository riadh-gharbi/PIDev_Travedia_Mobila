/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.PaiementService;
import com.Travedia.myapp.ReclamationService;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entities.Paiement;
import group.diamonddev.Stream;

/**
 *
 * @author riadh
 * Afficher détails de paiement ainsi qu'actions a faire
 * 
 */
public class AfficherPaiementForm extends Form {
    Form current;
    public AfficherPaiementForm(Form previous, int payID)
    {
        current = this;
        Paiement p = Stream.of(PaiementService.getInstance().getAllPaiement()).filter(l->l.getId()==payID).findFirst().orElse(null);
        setLayout(BoxLayout.y());
        setTitle("Paiement "+ p.getId());
        Label lbClientID = new Label();
        lbClientID.setText("ClientID: " + p.getClientId());
        Label lbOwnerID = new Label();
        lbOwnerID.setText("OwnerID: " + p.getOwnerId());
        Label lbDateCreation = new Label();
        lbDateCreation.setText("Date de Creation: "+ p.getDate_creation() );
        Label lbDatePaiement = new Label();
        lbDatePaiement.setText("Date de Paiement: "+ p.getDate_paiement());
        Label lbPlanningID = new Label();
        lbPlanningID.setText("PlanningID: "+ p.getPlanningId());
        Label lbSessionID = new Label();
        lbSessionID.setText("STRIPE Session ID: "+ p.getSessionID());
        Label lbPrix = new Label();
        lbPrix.setText("Prix: "+ p.getPrix());
        Label lbTypePaiement = new Label();
        lbTypePaiement.setText("Type Paiement: "+ p.getType_paiement());
        Label lbStatut = new Label();
        lbStatut.setText("Status du Paiement: "+ p.getStatut());
        
         Button editButton = new Button("Modifier Paiement");
        editButton.addActionListener(l->new ModifierPaiementForm(current, p).show());
        Button deleteButton = new Button("Supprimer Paiement");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Paiement?");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cette entrée de paiement?");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    PaiementService.getInstance().Delete(payID);
                    previous.show();
                            });
                closeButton.addActionListener((ee) -> d.dispose());
                Container buttonContainer = new Container(BoxLayout.x());
                buttonContainer.addAll(confirmButton,closeButton);
                d.add(BorderLayout.CENTER, dialogTextBody);
                d.add(BorderLayout.SOUTH,buttonContainer);
                d.show();
            }
        });
        
        
        
        addAll(lbClientID,lbOwnerID,lbDateCreation,lbDatePaiement,lbPrix,lbPlanningID,lbSessionID,lbTypePaiement,lbStatut,editButton,deleteButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
