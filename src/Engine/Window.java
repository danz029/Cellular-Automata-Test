package Engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window {

    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;
    private Text[] textList = {};
    private float scale;

    public Window(GameContainer gc){
        image = new BufferedImage(gc.getWidth(),gc.getHeight(),BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
        canvas.setPreferredSize(s);
        canvas.setMaximumSize(s);
        canvas.setMinimumSize(s);

        frame = new JFrame(gc.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas,BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();

        scale = gc.getScale();
    }

    public void update(){
        g.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight(),null);
        drawText(textList);
        bs.show();

        textList = new Text[]{};
    }

    public BufferedImage getImage() {
        return image;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void drawText(Text[] textList){
        for(Text text : textList){
            g.drawString(text.getText(),text.getX() * (int)scale, text.getY() * (int)scale);
        }
    }

    public void addToTextList(Text text) {
        Text[] temp = new Text[textList.length + 1];
        for(int i = 0;i < textList.length;i++){
            temp[i] = textList[i];
        }
        temp[textList.length] = text;
        textList = temp;
    }
}
