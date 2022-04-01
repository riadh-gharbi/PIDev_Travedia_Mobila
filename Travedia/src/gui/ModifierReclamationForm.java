/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.ReclamationService;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entities.Reclamation;
import entities.ReclamationReponse;
import group.diamonddev.Stream;
import java.util.Map;

/**
 *
 * @author riadh
 */
public class ModifierReclamationForm extends BaseForm{
    public ModifierReclamationForm(Form previous, int idRec) {
        setTitle("Modifier Reclamation");
        setLayout(BoxLayout.y());
        
        TextField tfSujet = new TextField("","Sujet");
        TextField tfContenu= new TextField("", "Contenu");
        ComboBox<String> cbEtatRec = new ComboBox<>("En Cours", "Résolue");
        
        Button btnValider = new Button("Modifier Reclamation");
        Reclamation r ;
        Map.Entry<Reclamation,ReclamationReponse> MapRec= Stream.of(ReclamationService.getInstance().getAllReclamation().entrySet()).filter(arg0->
                           ((Map.Entry<Reclamation,ReclamationReponse>)arg0).getKey().getId()==idRec).findFirst().orElse(null);
        System.out.println(MapRec);
        r =MapRec.getKey();
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfSujet.getText().length()==0)||(tfContenu.getText().length()==0) || (cbEtatRec.getSelectedItem().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        
                        r.setSujet(tfSujet.getText());
                        r.setContenu(tfContenu.getText());
                        r.setUser_id(null);
                        r.setEtat_reclamation(cbEtatRec.getSelectedItem());
                        if(ReclamationService.getInstance().modifierReclamation(r))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                           new ListeReclamationForm(new ReclamationHomeForm()).showBack();
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
