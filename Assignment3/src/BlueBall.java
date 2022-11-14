import java.awt.*;

public class BlueBall extends Ball{
    public BlueBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        setXSpeed(-1 * getXSpeed());
        setYSpeed(-1 * getYSpeed());
    }

    @Override
    public void update(Ball whiteBallRandom) {;
        this.setVisible(isIntersect(whiteBallRandom));
    }

}
