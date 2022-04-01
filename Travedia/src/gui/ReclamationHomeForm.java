/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author riadh
 */
public class ReclamationHomeForm extends BaseForm {
    Form current;
    public ReclamationHomeForm() {
        current=this; //Back 
        setTitle("Reclamation");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAjouterRec = new Button("Ajouter Reclamation");
        Button btnListRec = new Button("Liste des reclamations");
        
    
        
        
        
        btnAjouterRec.addActionListener(e-> new AjouterReclamationForm(current).show());
        btnListRec.addActionListener(e-> new ListeReclamationForm(current).show());
        addAll(btnAjouterRec,btnListRec);
        
        
    }
    
    
}
