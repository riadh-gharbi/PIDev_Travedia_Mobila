/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author Ibtihel
 */
public class HomeForm extends Form {

    Form current;

    public HomeForm() {
        current = this;
        setTitle("Travedia");
        setLayout(BoxLayout.y());
        getToolbar().addCommandToSideMenu("My profile", null, ev -> {
            new ProfileHome().show();
        });
        //getToolbar().addCommandToSideMenu("Propriétaire code promo", null, ev -> {
        //    new PromoCodeOwnerHome().show();
        //});
        //getToolbar().addCommandToSideMenu("Domaine", null, ev -> {
        //    new DomaineHome().show();
        // });
        // getToolbar().addCommandToSideMenu("Quiz", null, ev -> {
        //   new quizHome().show();
        //});
        // getToolbar().addCommandToSideMenu("Reclamations", null, ev -> {
        //     new ReclamationHome().show();
        // });

    }

}
