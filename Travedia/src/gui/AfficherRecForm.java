/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.ReclamationService;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import entities.Reclamation;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entities.ReclamationReponse;
import group.diamonddev.Stream;
import java.util.Collection;
import java.util.Map;


/**
 *
 * @author riadh
 */
public class AfficherRecForm extends BaseForm{
    Form current;
    public AfficherRecForm(Form previous, int recId)
    {
        super(new BorderLayout());
        current=this;
        setLayout(BoxLayout.y());
        //Missing: Afficher les réponses
        //Missing: Afficher Détails de reclamation
        //Missing: Better Layout 
        //Missing: check if admin connected, if yes, there is response text field and buttons (edit and delete)
        //Missing: if admin, drop down to make the reclamation Résolue
        Reclamation r ;
        ReclamationReponse rep ;
        //for (Reclamation rec : ReclamationService.getInstance().getAllReclamation().keySet())
        //{
         //   if (rec.getId()== recId)
           // {
             //   r=rec;
               // break;
            //}
        //}
        
        Map.Entry<Reclamation,ReclamationReponse> MapRec= Stream.of(ReclamationService.getInstance().getAllReclamation().entrySet()).filter(arg0->
                           ((Map.Entry<Reclamation,ReclamationReponse>)arg0).getKey().getId()==recId).findFirst().orElse(null);
        System.out.println(MapRec);
        r =MapRec.getKey();
        if (MapRec.getValue() ==null)
        {
            rep = null;
        }else
        rep = MapRec.getValue();
        Label sujetLabel= new Label(r.getSujet());
        Label contenuLabel = new Label(r.getContenu());
        Container repContainer = new Container(BoxLayout.y());
        
        if (rep!=null)
        {
            TextField repLabel = new TextField("",rep.getContenu(),20, TextField.ANY);
           
            
            repLabel.setActAsLabel(false);
            System.out.println(rep.getContenu());
            repLabel.setText(rep.getContenu());
            Button modifyRep = new Button("Modifier Réponse");
            Button deleteRep= new Button("Supprimer Réponse");
            modifyRep.addActionListener(l->
            {
                if (!repLabel.isEditable()){
                repLabel.setEditable(true);
                modifyRep.setText("Confirmer Modification");
                }else
                {
                    if (repLabel.getText().length()==0)
                    {
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    }else
                    {
                        rep.setContenu(repLabel.getText());
                        ReclamationService.getInstance().modifierReclamationResponse(rep);
                    }
                }
                
            });
            deleteRep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Reponse?");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cette réponse?");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    ReclamationService.getInstance().DeleteReponse(rep.getId());
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
        
            //System.out.println("rep container is being filled");
            repContainer.addAll(repLabel,modifyRep,deleteRep);
        }else
        {
            TextField repField = new TextField("","Repondre reclamation");
            Button reponseButton = new Button("Envoyer Réponse");
            repContainer.addAll(repField,reponseButton);
            
                ReclamationReponse reponse = new ReclamationReponse();
                reponseButton.addActionListener(l->{
                     if (repField.getText().length()==0)
                    {
                        Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                    }else{
                         
                         reponse.setContenu(repField.getText());
                        //System.out.println("Reponse:"+rep.getContenu());

                        reponse.setReclamationId(recId);
                         if (ReclamationService.getInstance().ajouterReclamationReponse(reponse)){
                          Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                                }
                        }
                    });
            
        }
        
        Button editButton = new Button("Modifier Reclamation");
        editButton.addActionListener(l->new ModifierReclamationForm(current, r.getId()).show());
        Button deleteButton = new Button("Supprimer Reclamation");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Reclamation");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cette reclamation?");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    ReclamationService.getInstance().Delete(recId);
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
        
        
        
        add(sujetLabel);
        add(contenuLabel);
        
        add(editButton);
        add(deleteButton);
        add(repContainer);
        //getToolbar().setEnabled(true);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
