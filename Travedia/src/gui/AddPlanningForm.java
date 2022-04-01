/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.PickerComponent;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import entities.Planning;
import com.Travedia.myapp.PlanningService;
import java.util.Date;

/**
 *
 * @author Razer
 */
public class AddPlanningForm extends BaseForm {
     public AddPlanningForm(Form previous) {
        setTitle("Add a new Planning");
        setLayout(BoxLayout.y());
        
        Picker datePickerD = new Picker();
        datePickerD.setType(Display.PICKER_TYPE_DATE);
        Picker datePickerF = new Picker();
        datePickerF.setType(Display.PICKER_TYPE_DATE);               
        TextField Voyageur = new TextField("", "Voyageur", 20, TextArea.ANY);
        TextField Description = new TextField("", "Description", 20, TextArea.ANY);
        Label Destination = new Label("Destination");
        ComboBox DestinationT = new ComboBox("1");
        Label Evenement = new Label("Evenement");
        ComboBox EvenementT = new ComboBox("1");
        Label Hotels = new Label("Hotels");
        ComboBox HotelsT = new ComboBox("2");
        Label TypePlan = new Label("Type Plan");
        ComboBox TypePlanT = new ComboBox("Premium","Standard");
        TextField prix = new TextField("","Prix",5,TextArea.NUMERIC);
        
        
        
        Button btnValider = new Button("Add Planning");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((datePickerD.toString().length()==0)||(datePickerF.toString().length()==0)||(Description.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Planning p = new Planning();
                        
                        
                        p.setDateDepart(datePickerD.getDate());
                        p.setDateFin(datePickerF.getDate());
                        p.setVoyageurId(Integer.parseInt(Voyageur.getText()));
                        p.setDescription(Description.getText());
                        p.setDestinationId(Integer.parseInt((String)DestinationT.getSelectedItem()));
                        p.setEvenementId(Integer.parseInt((String)EvenementT.getSelectedItem()));
                        p.setHotelId(Integer.parseInt((String)EvenementT.getSelectedItem()));
                        p.setTypePlan((String)TypePlanT.getSelectedItem());
                        p.setPrix(Integer.parseInt(prix.getText()));
                    
                    
                        
                        if( PlanningService.getInstance().ajouterPlanning(p))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        addAll(datePickerD,datePickerF,Description,Voyageur,Destination,DestinationT,Evenement,
              EvenementT,Hotels,HotelsT,TypePlan,TypePlanT,prix ,btnValider);
    
}
}
