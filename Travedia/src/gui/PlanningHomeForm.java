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
public class PlanningHomeForm extends BaseForm {
    Form current;
    public PlanningHomeForm() {
        current=this; //Back 
        setTitle("Planning");
        setLayout(BoxLayout.y());
        
        add(new Label("Choose an option"));
        Button btnAjouterPlan = new Button("Ajouter Planning");
        Button btnListPlan = new Button("Liste des reclamations");
        
    
        
        
        
        btnAjouterPlan.addActionListener(e-> new AddPlanningForm(current).show());
        btnListPlan.addActionListener(e-> new ListePlanning(current).show());
        addAll(btnAjouterPlan,btnListPlan);
        
        
    }
    
    
}
