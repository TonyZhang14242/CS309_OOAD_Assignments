import java.awt.*;

public class RedBall extends Ball{
    public RedBall(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {
        switch (keyChar) {
            case 'a':
                setXSpeed(-random.nextInt(3) - 1);
                break;
            case 'd':
                setXSpeed(random.nextInt(3) + 1);
                break;
            case 'w':
                setYSpeed(-random.nextInt(3) - 1);
                break;
            case 's':
                setYSpeed(random.nextInt(3) + 1);
        }
    }

    @Override
    public void update(Ball whiteBallRandom) {
        this.setVisible(isIntersect(whiteBallRandom));
    }

}
