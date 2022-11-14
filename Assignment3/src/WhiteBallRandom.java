import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WhiteBallRandom extends Ball implements Subject<Ball>{
    private final List<Ball> observers = new ArrayList<>();
    public WhiteBallRandom(Color color, int xSpeed, int ySpeed, int ballSize) {
        super(color, xSpeed, ySpeed, ballSize);
    }

    @Override
    public void update(char keyChar) {

    }

    @Override
    public void update(Ball ball) {

    }

    @Override
    public void registerObserver(Ball ball) {
        observers.add(ball);
    }

    @Override
    public void removeObserver(Ball ball) {
        observers.remove(ball);
    }

    @Override
    public void notifyObservers(char keyChar) {
        observers.forEach(e -> e.update(keyChar));
    }

    @Override
    public void notifyObservers() {
        observers.forEach(e -> e.update(this));
    }

    @Override
    public void move() {
        super.move();
        notifyObservers();
    }
}
