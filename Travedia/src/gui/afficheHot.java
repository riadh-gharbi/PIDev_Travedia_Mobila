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
import entities.Hotel;
import com.Travedia.myapp.HotelService;
import group.diamonddev.Stream;


/**
 *
 * @author Razer
 */
public class afficheHot extends Form {
 Form current;
    public afficheHot(Form previous, int idHo)
    {
        current = this;
        Hotel p =Stream.of(HotelService.getInstance().affichageHotel()).filter(l->l.getId()==idHo).findFirst().orElse(null);
        setLayout(BoxLayout.y());
        setTitle("Hotel "+ p.getId());
        Label lbHotelID = new Label();
        lbHotelID.setText("HotelID: " + p.getId());
        Label lbNom = new Label();
        lbNom.setText("Nom: " + p.getNom());
        Label lbadresse = new Label();
        lbadresse.setText("Adresse: "+ p.getAdresse());
        Label lbEmail = new Label();
        lbEmail.setText("Email "+ p.getEmail());
        Label lbNumTel = new Label();
        lbNumTel.setText("NumTel: "+ p.getNumTel());
                
         Button editButton = new Button("Modifier Hotel");
        editButton.addActionListener(l->new modfierHotel(current, p).show());
        Button deleteButton = new Button("Supprimer Hotel");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Hotel?");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cet hotel");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    HotelService.getInstance().SupprimerHotel(idHo);
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
        
        
        
        addAll(lbHotelID,lbNom,lbEmail,lbNumTel,lbadresse,editButton,deleteButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}