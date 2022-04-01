/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
import entities.Planning;
import com.Travedia.myapp.PlanningService;
import group.diamonddev.Stream;

/**
 *
 * @author Razer
 */
public class afficherPla extends Form{
    Form current;
    public afficherPla(Form previous, int PlaId)
    {
        current = this;
        Planning p = Stream.of(PlanningService.getInstance().affichagePlanning()).filter(l->l.getId()==PlaId).findFirst().orElse(null);
        setLayout(BoxLayout.y());
        setTitle("Planning "+ p.getId());
        Label lbDateDepart = new Label();
        lbDateDepart.setText("Date Depart: " + p.getDateDepart());
        Label lbDateFin = new Label();
        lbDateFin.setText("Date Fin: " + p.getDateFin());
        Label lbVoyageur = new Label();
        lbVoyageur.setText("Voyageur: "+ p.getVoyageurId());
        Label lbDestination = new Label();
        lbDestination.setText("Destination"+ p.getDestinationId());
        Label lbEvenement = new Label();
        lbEvenement.setText("Evenement: "+ p.getEvenementId());
        Label lbTypePlan = new Label();
        lbTypePlan.setText("Type Plan: "+ p.getTypePlan());
        Label lbHotel = new Label();
        lbHotel.setText("Hotel"+ p.getHotelId());
        Label lbDescription = new Label();
        lbDescription.setText("Description"+ p.getDescription());
        Label lbPrix = new Label();
        lbPrix.setText("Prix"+ p.getPrix());
        
         Button editButton = new Button("Modifier Planning");
        editButton.addActionListener(l->new modfierPlanning(current, p).show());
        Button deleteButton = new Button("Supprimer Planning");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Planning?");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cette Planning?");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    PlanningService.getInstance().SupprimerPlanning(PlaId);
                    new HomeForm().showBack();
                            });
                closeButton.addActionListener((ee) -> d.dispose());
                Container buttonContainer = new Container(BoxLayout.x());
                buttonContainer.addAll(confirmButton,closeButton);
                d.add(BorderLayout.CENTER, dialogTextBody);
                d.add(BorderLayout.SOUTH,buttonContainer);
                d.show();
            }
        });
        
        
        
        addAll(lbDateDepart,lbDateFin,lbDescription,lbDestination,lbEvenement,lbHotel,lbPrix,lbTypePlan,lbVoyageur,editButton,deleteButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
    

