package state;

import gfx.Assets;
import ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameState extends State{

    protected BufferedImage[][] panel, allPegs;

    GameState(){
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[20][2];
        uiManager.addUIButton(new UIButton(150, 600, 80, 80, Assets.yes, this::init));
    }

    @Override
    public void render(Graphics graphics) {

        uiManager.render(graphics);
        for(int y = 0; y < panel.length; y++){
            for(int x = 0; x < panel[y].length; x++){
                if(panel[y][x] != null)
                    graphics.drawImage(panel[y][x], 500+50*x, 150+60*y, 30, 30, null);
            }
        }

        for(int y = 0; y < allPegs.length; y++){
            for(int x = 0; x < allPegs[y].length; x++){
                if(allPegs[y][x] != null){
                    graphics.drawImage(allPegs[y][x], 710+30*x, 150+60*y, 15, 15, null);
                }
            }
        }
        postRender(graphics);
    }

    @Override
    public void init() {
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[20][2];


        start();
    }

    protected abstract void start();

    protected abstract void postRender(Graphics graphics);



}
