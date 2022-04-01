package gui;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.mycompany.main;
//import com.codename1.components.ScaleImageLabel;
//import com.codename1.components.SpanLabel;
//import com.codename1.ui.Button;
//import com.codename1.ui.ButtonGroup;
//import com.codename1.ui.Component;
//import static com.codename1.ui.Component.BOTTOM;
//import static com.codename1.ui.Component.CENTER;
//import static com.codename1.ui.Component.LEFT;
//import static com.codename1.ui.Component.RIGHT;
//import com.codename1.ui.Container;
//import com.codename1.ui.Dialog;
//import com.codename1.ui.Display;
//import com.codename1.ui.EncodedImage;
//import com.codename1.ui.FontImage;
//import com.codename1.ui.Form;
//import com.codename1.ui.Graphics;
//import com.codename1.ui.Image;
//import com.codename1.ui.Label;
//import com.codename1.ui.RadioButton;
//import com.codename1.ui.Tabs;
//import com.codename1.ui.Toolbar;
//import com.codename1.ui.URLImage;
//import com.codename1.ui.layouts.BorderLayout;
//import com.codename1.ui.layouts.BoxLayout;
//import com.codename1.ui.layouts.FlowLayout;
//import com.codename1.ui.layouts.GridLayout;
//import com.codename1.ui.layouts.LayeredLayout;
//import com.codename1.ui.plaf.Style;
//import com.codename1.ui.util.Resources;
//import com.mycompany.entites.Destination;
//import com.mycopany.services.serviceDestination;
//import java.util.ArrayList;
///**
// *
// * @author Ameni
// */
//public class AfficheDestination extends BaseForm {
//    Container c = new Container(BoxLayout.x());
//Container cy = new Container(BoxLayout.y());
//Container cxx = new Container(BoxLayout.xCenter());
//public AfficheDestination() {
//
//                     super("Newsfeed",BoxLayout.y());   
//                       
//        //setLayout(BoxLayout.y());
//        for(int i=0;i<serviceDestination.getInstance().affichdestid().size();i++)
//        {
//        Label t = new Label("Nom : ");    
//        Label titre = new Label(""+serviceDestination.getInstance().affichdestid().get(i).getNom());
//        titre.setUIID("CENTER");
//        titre.setAlignment(CENTER);
//        titre.getAllStyles().setFgColor(0xff5866d3);
//        Label t1 = new Label("Lieu_arrivee : ");  
//        Label titre1 = new Label(serviceDestination.getInstance().affichdestid().get(i).getDescription());
//        titre1.setUIID("CENTER");
//        titre1.setAlignment(CENTER);
//        titre1.getAllStyles().setFgColor(0xff5866d3);
//        Label t2 = new Label("Lieu_depart : ");
//        Label titre2 = new Label(serviceDestination.getInstance().affichdestid().get(i).getId_region());
//        titre2.setUIID("CENTER");
//        titre2.setAlignment(CENTER);
//        titre2.getAllStyles().setFgColor(0xff5866d3);
//      
//
//
//
//        Container cy = new Container(BoxLayout.yCenter());
//        cy.add(t).add(titre).add(t1).add(titre1).add(t2).add(titre2);
//        cxx.add(cy);
//        cxx.setScrollableX(true);
//       
//       
//        }      
//               
//         this.add(cxx);    
//       
//     
//        gui_Label_5.setShowEvenIfBlank(true);
//        gui_Label_6.setShowEvenIfBlank(true);
//        gui_Label_7.setShowEvenIfBlank(true);
//        gui_Label_8.setShowEvenIfBlank(true);
//        gui_Label_9.setShowEvenIfBlank(true);
//       
//        gui_Text_Area_1.setRows(2);
//        gui_Text_Area_1.setColumns(100);
//        gui_Text_Area_1.setEditable(false);
//        gui_Text_Area_1_1.setRows(2);
//        gui_Text_Area_1_1.setColumns(100);
//        gui_Text_Area_1_1.setEditable(false);
//        gui_Text_Area_1_2.setRows(2);
//        gui_Text_Area_1_2.setColumns(100);
//        gui_Text_Area_1_2.setEditable(false);
//        gui_Text_Area_1_3.setRows(2);
//        gui_Text_Area_1_3.setColumns(100);
//        gui_Text_Area_1_3.setEditable(false);
//        gui_Text_Area_1_4.setRows(2);
//        gui_Text_Area_1_4.setColumns(100);
//        gui_Text_Area_1_4.setEditable(false);
//       
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//    private com.codename1.ui.Container gui_Container_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
//    private com.codename1.ui.Container gui_Container_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_1 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_4 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
//    private com.codename1.ui.Label gui_Label_3 = new com.codename1.ui.Label();
//    private com.codename1.ui.Label gui_Label_2 = new com.codename1.ui.Label();
//    private com.codename1.ui.TextArea gui_Text_Area_1 = new com.codename1.ui.TextArea();
//    private com.codename1.ui.Label gui_Label_6 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_1_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
//    private com.codename1.ui.Container gui_Container_2_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_1_1 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_4_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_4_1 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_3_1 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
//    private com.codename1.ui.Label gui_Label_3_1 = new com.codename1.ui.Label();
//    private com.codename1.ui.Label gui_Label_2_1 = new com.codename1.ui.Label();
//    private com.codename1.ui.TextArea gui_Text_Area_1_1 = new com.codename1.ui.TextArea();
//    private com.codename1.ui.Label gui_Label_7 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_1_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
//    private com.codename1.ui.Container gui_Container_2_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_1_2 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_4_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_4_2 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_3_2 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
//    private com.codename1.ui.Label gui_Label_3_2 = new com.codename1.ui.Label();
//    private com.codename1.ui.Label gui_Label_2_2 = new com.codename1.ui.Label();
//    private com.codename1.ui.TextArea gui_Text_Area_1_2 = new com.codename1.ui.TextArea();
//    private com.codename1.ui.Label gui_Label_8 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_1_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
//    private com.codename1.ui.Container gui_Container_2_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_1_3 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_4_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_4_3 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_3_3 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
//    private com.codename1.ui.Label gui_Label_3_3 = new com.codename1.ui.Label();
//    private com.codename1.ui.Label gui_Label_2_3 = new com.codename1.ui.Label();
//    private com.codename1.ui.TextArea gui_Text_Area_1_3 = new com.codename1.ui.TextArea();
//    private com.codename1.ui.Label gui_Label_9 = new com.codename1.ui.Label();
//     private com.codename1.ui.Label gui_Label_10 = new com.codename1.ui.Label();
//      private com.codename1.ui.Label gui_Label_11 = new com.codename1.ui.Label();
//       private com.codename1.ui.Label gui_Label_12 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_1_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BorderLayout());
//    private com.codename1.ui.Container gui_Container_2_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_1_4 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_4_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.FlowLayout());
//    private com.codename1.ui.Label gui_Label_4_4 = new com.codename1.ui.Label();
//    private com.codename1.ui.Container gui_Container_3_4 = new com.codename1.ui.Container(new com.codename1.ui.layouts.BoxLayout(com.codename1.ui.layouts.BoxLayout.Y_AXIS));
//    private com.codename1.ui.Label gui_Label_3_4 = new com.codename1.ui.Label();
//    private com.codename1.ui.Label gui_Label_2_4 = new com.codename1.ui.Label();
//    private com.codename1.ui.TextArea gui_Text_Area_1_4 = new com.codename1.ui.TextArea();
//    private com.codename1.ui.Label gui_Label_5 = new com.codename1.ui.Label();
//
//     private com.codename1.ui.Button gui_Button_2 = new com.codename1.ui.Button();
//       private com.codename1.ui.Button gui_Button_3 = new com.codename1.ui.Button();
//        private com.codename1.ui.Button gui_Button_4 = new com.codename1.ui.Button();
//       private com.codename1.ui.Button gui_Button_5 = new com.codename1.ui.Button();        
//
//}
