/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.ui.BrowserComponent;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author riadh
 */
public class BrowserForm extends BaseForm {
    Form current;
    public BrowserForm(String url,Form previous) {
        current=this; //Back 
        setTitle("Browser");
        setLayout(new BorderLayout());
        BrowserComponent browser = new BrowserComponent();
         browser.setURL(url);
//    Container cont = new Container();
//    
//    cont.setLayout(new BorderLayout());
//    cont.addComponent(BorderLayout.CENTER, browser);
       
        
    
        
        
        add( BorderLayout.CENTER,browser);
       
                getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

        
    }
    
    
}

