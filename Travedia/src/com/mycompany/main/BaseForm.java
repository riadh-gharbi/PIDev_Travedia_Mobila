/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.mycompany.main;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.Container;

/**
 * Base class for the forms with common functionality
 *
 * @author Shai Almog
 */
public class BaseForm extends Form {
    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }
    
    
    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }
    
    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        new Label(res.getImage("profile-pic.jpg"), "PictureWhiteBackgrond"))
        ));
        
        tb.addMaterialCommandToSideMenu("Newsfeed", FontImage.MATERIAL_UPDATE, e -> new NewsfeedForm(res).show());
        tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res).show());
        tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new WalkthruForm(res).show());
        tb.addMaterialCommandToSideMenu("Destination", FontImage.MATERIAL_PLACE, e -> new ListDestinationForm(res).show());
        tb.addMaterialCommandToSideMenu("Region", FontImage.MATERIAL_TRAVEL_EXPLORE, e -> new ListRegionForm(res).show());

        
       
    }}
   
    // new
    
//public void installSidemenu(Resources res) {
//        Image selection = res.getImage("line.png");
//          Image inboxImage = null;
//        if(isCurrentInbox()) inboxImage = selection;
//
//        Image trendingImage = null;
//        if(isCurrentTrending()) trendingImage = selection;
//        
//        Image calendarImage = null;
//        if(isCurrentCalendar()) calendarImage = selection;
//        
//        Image statsImage = null;
//        if(isCurrentStats()) statsImage = selection;
        
        
         // spacer
        
//        getToolbar().addComponentToSideMenu(new Label(res.getImage("profile_image.png"), "Container"));
//        getToolbar().addComponentToSideMenu(new Label("Detra Mcmunn", "SideCommandNoPad"));
//        getToolbar().addComponentToSideMenu(new Label(" ", "SideCommand"));
        
//        Button inboxButton = new Button("Home");
//        inboxButton.setUIID("SideCommand");
//        inboxButton.getAllStyles().setPaddingBottom(0);
//        Container inbox = FlowLayout.encloseMiddle(inboxButton);
//        inbox.setLeadComponent(inboxButton);
//        inbox.setUIID("SideCommand");
//        inboxButton.addActionListener(e -> new InboxForm().show());
//        getToolbar().addComponentToSideMenu(inbox);
        
       // getToolbar().addCommandToSideMenu("Stat", statsImage, e -> new StatsForm(res).show());
       // getToolbar().addCommandToSideMenu("Calendar", calendarImage, e -> new CalendarForm(res).show());
         
//getToolbar().addCommandToSideMenu("Region", null, e ->{
//    new RegionForm().show();
//        });
//getToolbar().addCommandToSideMenu("Restaurant", null, e ->{
//    new RestoForm().show();
//        });
//getToolbar().addCommandToSideMenu("Categorie", null, e ->{
//    new CategorieForm().show();
//        });
//getToolbar().addCommandToSideMenu("Stats", null, e ->{
//    new StatForm().show();
//        });
//getToolbar().addCommandToSideMenu("Affich Rest", null, e ->{
//    new AffichAdminRest().show();
//        });
//tb.addCommandToSideMenu("Affich Region", null, e ->{
//    new AfficherDestination().show();
//        });
//getToolbar().addCommandToSideMenu("Ajout Region", null, e ->{
//    new AjoutRegion().show();
//        });
//        //getToolbar().addCommandToSideMenu("Trending", trendingImage, e -> new TrendingForm(res).show());
//getToolbar().addCommandToSideMenu("Ajout Resto", null, e ->{
//    new AjoutResto().show();
//        });
//getToolbar().addCommandToSideMenu("Ajout Categorie", null, e ->{
//    new AjoutCategorieForm().show();
//        });
//getToolbar().addCommandToSideMenu("Ajout Transport", null, e ->{
//    new AjoutTransportForm().show();
//        });
//getToolbar().addCommandToSideMenu("Logout", null, e ->{
//    new SignInForm().show();
//        });
        
       
        
//    }
//
//        
//    protected boolean isCurrentInbox() {
//        return false;
//    }
//    
//    protected boolean isCurrentTrending() {
//        return false;
//    }
//
//    protected boolean isCurrentCalendar() {
//        return false;
//    }
//
//    protected boolean isCurrentStats() {
//        return false;
//    }

