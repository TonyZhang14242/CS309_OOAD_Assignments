import java.awt.*;
import java.util.*;
import java.util.List;

public class WhiteBall extends Ball{
    public WhiteBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        switch (keyChar) {
            case 'a':
                setXSpeed(-8);
                break;
            case 'd':
                setXSpeed(8);
                break;
            case 'w':
                setYSpeed(-8);
                break;
            case 's':
                setYSpeed(8);
                break;
        }

    }

    @Override
    public void update(Ball ball) {

    }

}
