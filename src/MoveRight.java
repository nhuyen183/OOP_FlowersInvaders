import java.util.ArrayList;

public class MoveRight implements Moveable {

    public void move(ArrayList<Flower> flowers) {
        for (Flower flower : flowers){
            flower.setXPosition(flower.getXPosition() + Constants.FLOWER_MOVEMENT_MODIFIER);
        }
    }

    @Override
    public void move() {

    }
}
