import java.util.ArrayList;

public class MoveDown implements Moveable {

    public void move(ArrayList<Flower> flowers) {
        for (Flower flower : flowers){
            flower.setXPosition(flower.getYPosition() + Constants.FLOWER_MOVEMENT_MODIFIER);
        }
    }

    @Override
    public void move() {

    }
}
