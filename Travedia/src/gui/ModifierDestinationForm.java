/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entities.Destination;
import com.Travedia.myapp.serviceDestination;
import com.Travedia.myapp.serviceRegion;

/**
 *
 * @author user
 */
public class ModifierDestinationForm extends BaseForm {
    Form current;
    public ModifierDestinationForm(Resources res,Destination ev){
         //super("Newsfeed",BoxLayout.y());
   
        //*******
        Toolbar tb = new Toolbar (true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modiifier destination");
        getContentPane().setScrollVisible(false);
                Tabs swipe = new Tabs();
                Label s1= new Label();
                Label s2= new Label();
        super.addSideMenu(res);
        //********
         addTab(swipe, res.getImage("trav.jpg"), s1, "", "", "");
        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0x008B8B);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0x008B8B);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
       // rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        
        ButtonGroup barGroup = new ButtonGroup();
        
        RadioButton all = RadioButton.createToggle("", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("", barGroup);
       myFavorite.setUIID("SelectBar");
//       addStringValue("", myFavorite);
//               myFavorite.addActionListener(e->{
//            new ListDestinationForm(res).show();
//        });

             getToolbar().addCommandToSideMenu("Region", null, e ->{
     new ListRegionForm(res).show();
        });
                  getToolbar().addCommandToSideMenu("Destination", null, e ->{
     new ListDestinationForm(res).show();
        });

        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);
        
//****************
        TextField nom = new TextField(ev.getNom(),"nom",20,TextField.ANY);
        TextField image = new TextField(ev.getImage(),"image",20,TextField.ANY);
        TextField description = new TextField(ev.getDescription(),"description",20,TextField.ANY);
        //TextField datedeb = new TextField(ev.getDatedeb(),"datedeb",20,TextField.ANY);
        Picker datedeb = new Picker ();
        datedeb.setType(Display.PICKER_TYPE_DATE);
       // TextField datefin = new TextField(ev.getDatefin(),"datefin",20,TextField.ANY);
        Picker datefin = new Picker ();
        datefin.setType(Display.PICKER_TYPE_DATE);
     //   TextField categorie = new TextField(ev.getCategorie(),"categorie",20,TextField.ANY);
        
        nom.setUIID("NewsTopLine");
        image.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
     
        
        nom.setSingleLineTextArea(true);
        image.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
   
        
        Button btnModifier = new Button ("Modifier");
        btnModifier.setUIID("Button");
        //event click
        btnModifier.addPointerPressedListener(l-> {
            ev.setNom(nom.getText());
            ev.setImage(image.getText());
            if(serviceDestination.getInstance().modifierDestination(ev)){
                new ListDestinationForm(res).show();
            }
        });
        
         Button btnAnnuler= new Button ("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListDestinationForm(res).show();
        });
     addAll(nom,description,image,btnModifier,btnAnnuler);
        show();
    }
    
    
    
    
    
      private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT,btn.getX()+btn.getWidth()/2-l.getWidth()/2);
        l.getParent().repaint();
    }

    private void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e->{
            if (btn.isSelected()){
                updateArrowPosition(btn,l);}
            }
        );
    }

     private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
       // heartStyle.setFgColor(0xff2d55);
        //FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        //likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        //FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }

}