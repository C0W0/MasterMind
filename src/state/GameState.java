package state;

import gfx.Assets;
import ui.UIButton;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameState extends State{

    //graphics
    protected BufferedImage[][] panel, allPegs;
    protected BufferedImage[] code;
    protected int cornerWidth, cornerHeight;

    //control
    protected boolean isGameActive;

    GameState(){
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[10][4];
        code = new BufferedImage[4];
        uiManager.addUIButton(new UIButton(30, 648, 90, 90, Assets.back_button, ()->{} /*back to menu*/));
        uiManager.addUIButton(new UIButton(135, 648, 90, 90, Assets.restart_button, this::init));
        cornerWidth = (int)(1020.f/2000*1024);
        cornerHeight = (int)(1170.f/1428*768);
        isGameActive = false;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(Assets.emptyGameBoard, 0, 0, 1024, 768, null);

        for(int y = 0; y < panel.length; y++){
            for(int x = 0; x < panel[y].length; x++){
                if(panel[y][x] == null)
                    graphics.drawImage(Assets.empty_slot, 690+50*x, (int)(81+54.5*y), 30, 30, null);
                else
                    graphics.drawImage(panel[y][x], 690+50*x, (int)(81+54.5*y), 30, 30, null);
            }
        }

        for(int y = 0; y < allPegs.length; y++){
            for(int x = 0; x < 2; x++){
                if(allPegs[y][x] != null)
                    graphics.drawImage(allPegs[y][x], 920+17*x, 81+54*y, 15, 15, null);
            }
            for(int x = 2; x < 4; x++){
                if(allPegs[y][x] != null)
                    graphics.drawImage(allPegs[y][x], 920+17*(x-2), 98+54*y, 15, 15, null);
            }
        }

        if(code[0] != null)
            for(int i = 0; i < code.length; i++)
                graphics.drawImage(code[i], 680+50*i, 690, 30, 30, null);

        postRender(graphics);
        uiManager.render(graphics);
    }

    @Override
    public void init() {
        panel = new BufferedImage[10][4];
        allPegs = new BufferedImage[10][4];
        code = new BufferedImage[4];
        isGameActive = true;

        start();
    }

    protected abstract void start();

    protected abstract void postRender(Graphics graphics);

    protected void showCode(int[] code){
        this.code = toColour(code);
    }

    protected BufferedImage[] toColour(int[] guess){
        BufferedImage[] colours = new BufferedImage[4];
        for(int i = 0; i < 4; i++){
            colours[i] = Assets.colours[guess[i]];
        }
        return colours;
    }
}
