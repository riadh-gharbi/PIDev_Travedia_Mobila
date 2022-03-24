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
public class PaiementHomeForm extends Form {
     Form current;
    public PaiementHomeForm() {
        current=this; //Back 
        setTitle("Paiement");
        setLayout(BoxLayout.y());
        
        add(new Label("Choisir une option"));
        Button btnAjouterPay = new Button("Ajouter Paiement");
        Button btnListPay = new Button("Liste des paiements");
        
        
    
        
        
        
       
        btnListPay.addActionListener(e-> new ListePaiementForm(current).show());
        btnAjouterPay.addActionListener(e-> new AjouterPaiementFormFull(current).show());
        
        addAll(btnAjouterPay,btnListPay);
        
        
    }
    
}
