package gdx.kapotopia.Helpers;

import com.badlogic.gdx.input.GestureDetector;

public class SimpleDirectionGestureDetector extends GestureDetector {
    public interface DirectionListener {
        void onLeft();

        void onRight();

        void onUp();

        void onDown();

        void onTouch();

        void onPan();
    }

    public SimpleDirectionGestureDetector(DirectionListener directionListener) {
        super(new DirectionGestureListener(directionListener));
    }

    private static class DirectionGestureListener extends GestureAdapter{
        DirectionListener directionListener;

        public DirectionGestureListener(DirectionListener directionListener){
            this.directionListener = directionListener;
        }

        @Override
        public boolean fling(float velocityX, float velocityY, int button) {
            if(Math.abs(velocityX)>Math.abs(velocityY)){
                if(velocityX>0){
                    directionListener.onRight();
                }else{
                    directionListener.onLeft();
                }
            }else{
                if(velocityY>0){
                    directionListener.onDown();
                }else{
                    directionListener.onUp();
                }
            }
            return super.fling(velocityX, velocityY, button);
        }

        @Override
        public boolean touchDown (float x, float y, int pointer, int button) {
            directionListener.onTouch();
            return super.touchDown(x, y, pointer, button);
        }

        @Override
        public boolean pan (float x, float y, float deltaX, float deltaY) {
            directionListener.onPan();
            return super.pan(x, y, deltaX, deltaY);
        }

    }

}

/* Use case */

/*Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(
        new SimpleDirectionGestureDetector.DirectionListener() {
            @Override
            public void onUp() {
            }

            @Override
            public void onRight() {
            }

            @Override
            public void onLeft() {
            }

            @Override
            public void onDown() {
            }
         }));
*/


