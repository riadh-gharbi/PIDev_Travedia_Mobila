/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.ReclamationService;
import entities.Reclamation;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author riadh
 */
public class AjouterReclamationForm extends BaseForm {

    public AjouterReclamationForm(Form previous) {
        setTitle("Add a new task");
        setLayout(BoxLayout.y());
        
        TextField tfSujet = new TextField("","Sujet");
        TextField tfContenu= new TextField("", "Contenu");
        Button btnValider = new Button("Envoyer Reclamation");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfSujet.getText().length()==0)||(tfContenu.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Reclamation r = new Reclamation();
                        r.setSujet(tfSujet.getText());
                        r.setContenu(tfContenu.getText());
                        r.setUser_id(null);
                        r.setEtat_reclamation("En Cours");
                        if(ReclamationService.getInstance().ajouterReclamation(r))
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
        
        addAll(tfSujet,tfContenu,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
