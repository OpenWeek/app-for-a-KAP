package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import java.util.*;

public class Core {


    static int xOffSet = 0;
    static int yOffSet = 0;

    private Screen parent;

    private Tile[][] tiles;
    private int sizex;
    private int sizey;
    private int width;
    private int height;

    private int[] goals;//All goals at y = sizey-1
    private int correctGoal;
    private Stack<Tile> stack;
    private HashSet<Tile> set;
    private Random random;

    private ShapeRenderer shapeRenderer;


    public Core(Screen parent, int sizex, int sizey){
        this(parent, sizex, sizey, 1);
    }

    public Core(Screen parent, int sizex, int sizey, int nbGoals){
        this.parent = parent;
        this.sizex = sizex;
        this.sizey = sizey;

        Tile.tile_size = 96;

        if (sizex*Tile.tile_size > Gdx.graphics.getWidth() || sizey*Tile.tile_size > Gdx.graphics.getHeight()){
            Tile.tile_size = Math.min(Gdx.graphics.getWidth()/sizex,Gdx.graphics.getHeight()/sizey );
        }

        random = new Random();
        tiles = new Tile[sizex][sizey];
        for (int x = 0; x < sizex; x++){
            for(int y = 0; y < sizey; y++){
                tiles[x][y] = new Tile(x,y);
            }
        }

        width = Tile.tile_size*sizex;
        height = Tile.tile_size*sizey;

        xOffSet = (Gdx.graphics.getWidth()-width)/2;
        yOffSet = (Gdx.graphics.getHeight()-height)/2;

        stack = new Stack<Tile>();
        set = new HashSet<Tile>(sizex*sizey);

        setGoal(nbGoals);
        tiles[0][0].lit = true;

        shapeRenderer = new ShapeRenderer();

        //TODO : assign correct goal
        correctGoal = goals[nbGoals/2];

        for(int i = 0; i < goals.length; i++) {
            createPath(goals[i], sizey-1);
        }

        for(Tile[] til : tiles){
            for(Tile t : til){
                t.rotate(random.nextInt(3));
            }
        }

        updatePath(tiles[0][0]);

    }

    private void createPath(int xDest, int yDest){

        Tile t = tiles[0][0];
        Tile g = tiles[xDest][yDest];

        t.connection[2] = true;
        g.connection[0] = true;

        final int p = 25;
        while (t != g){
            boolean dx = (xDest - t.x) < 0;
            boolean dy = (yDest - t.y) < 0;
            if(random.nextBoolean()){
                if(random.nextInt(100) < p){
                    dx = !dx;
                }
                if (dx && t.x > 0) {
                    t.connection[1] = true;
                    t = tiles[t.x - 1][t.y];
                    t.connection[3] = true;
                }
                else if(t.x < sizex - 1){
                    t.connection[3] = true;
                    t = tiles[t.x + 1][t.y];
                    t.connection[1] = true;
                }
            }
            else{
                if(random.nextInt(100) < p){
                    dy = !dy;
                }
                if(dy && t.y > 0){
                    t.connection[2] = true;
                    t = tiles[t.x][t.y-1];
                    t.connection[0] = true;
                }
                else if(t.y < sizey-1){
                    t.connection[0] = true;
                    t = tiles[t.x][t.y+1];
                    t.connection[2] = true;
                }
            }
        }
    }

    private void setGoal(int nbGoals){
        goals = new int[nbGoals];
        int part = sizex/(nbGoals+1);
        for (int i = 1; i <= nbGoals; i++){
            goals[i-1] = i*part;
        }

    }
    private boolean checkGoal(){
        return  tiles[correctGoal][sizey-1].connection[0] && tiles[correctGoal][sizey-1].lit;
    }

    private void updatePath(Tile moved){

        Tile origin =tiles[0][0];

        set.clear();
        if (!origin.connection[2]){
            downdatePath(set, origin);
            return;
        }

        origin.lit = true;
        stack.add(origin);
        set.add(origin);

        while (!stack.isEmpty()){
            Tile t = stack.pop();
            boolean[] dir = t.connection;
            if(dir[0] && t.y < sizey-1){
                Tile t2 =  tiles[t.x][t.y+1];
                if(t2.connection[2] && ! set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = true;
                }
            }
            if(dir[1] && t.x > 0){
                Tile t2 = tiles[t.x-1][t.y];
                if(t2.connection[3] && !set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = true;
                }
            }
            if(dir[2] && t.y > 0){
                Tile t2 = tiles[t.x][t.y-1];
                if(t2.connection[0] && !set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = true;
                }
            }
            if(dir[3] && t.x < sizex-1){
                Tile t2 = tiles[t.x+1][t.y];
                if(t2.connection[1] && !set.contains(t2) ){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = true;
                }
            }
        }
        downdatePath(set, moved);
    }
    private void downdatePath(Set<Tile> set, Tile moved){
        if(!set.contains(moved)){
            set.add(moved);
            moved.lit = false;
        }
        boolean[] dir = moved.connection;
        if(moved.y < sizey-1){
            Tile t2 =  tiles[moved.x][moved.y+1];
            if(! set.contains(t2)){
                stack.add(t2);
                set.add(t2);
                t2.lit = false;
            }
        }
        if(moved.x > 0){
            Tile t2 = tiles[moved.x-1][moved.y];
            if(!set.contains(t2)){
                stack.add(t2);
                set.add(t2);
                t2.lit = false;
            }
        }
        if(moved.y > 0){
            Tile t2 = tiles[moved.x][moved.y-1];
            if(!set.contains(t2)){
                stack.add(t2);
                set.add(t2);
                t2.lit = false;
            }
        }
        if(moved.x < sizex-1){
            Tile t2 = tiles[moved.x+1][moved.y];
            if(!set.contains(t2) ){
                stack.add(t2);
                set.add(t2);
                t2.lit = false;
            }
        }

        while (!stack.isEmpty()){
            Tile t = stack.pop();
            dir = t.connection;
            if(dir[0] && t.y < sizey-1){
                Tile t2 =  tiles[t.x][t.y+1];
                if(t2.connection[2] && ! set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = false;
                }
            }
            if(dir[1] && t.x > 0){
                Tile t2 = tiles[t.x-1][t.y];
                if(t2.connection[3] && !set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = false;
                }
            }
            if(dir[2] && t.y > 0){
                Tile t2 = tiles[t.x][t.y-1];
                if(t2.connection[0] && !set.contains(t2)){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = false;
                }
            }
            if(dir[3] && t.x < sizex-1){
                Tile t2 = tiles[t.x+1][t.y];
                if(t2.connection[1] && !set.contains(t2) ){
                    stack.add(t2);
                    set.add(t2);
                    t2.lit = false;
                }
            }
        }
    }

    void touchHandler(int x, int y){
        //Click inside puzzle
        if(x >= xOffSet && y >= yOffSet && x <= xOffSet+width && y <= yOffSet+height){
            int X = (x-xOffSet)/Tile.tile_size;
            int Y = (y-yOffSet)/Tile.tile_size;
            tiles[X][Y].rotate(1);
            updatePath(tiles[X][Y]);
            if(checkGoal()){
                parent.dispose();
            }
        }
    }

    public void draw(){
        shapeRenderer.begin(ShapeType.Filled);
        for (Tile[] t : tiles){
            for(Tile tile : t){
                tile.draw(shapeRenderer);
                //TODO: DRAW TILE BACKGROUND
            }
        }

        //TODO: replace with true goal indication
        shapeRenderer.setColor(Color.RED);
        for(int i = 0; i < goals.length; i++){
            shapeRenderer.rect(xOffSet+goals[i]*Tile.tile_size, yOffSet+ sizey*Tile.tile_size, Tile.tile_size, Tile.tile_size);
        }
        shapeRenderer.end();
    }
}

class Tile{

    static int tile_size;

    //N,W,S,E
    boolean[] connection;
    boolean lit;
    int x;
    int y;

    Tile(int x, int y){
        connection = new boolean[4];
        lit = false;
        this.x= x;
        this.y = y;
    }


    private void rotateC(){
        boolean b = connection[0];
        for(int i = 0; i < 3; i++){
            connection[i] = connection[i+1];
        }
        connection[3] = b;
    }
    void rotate(int step){
        for(int i =0; i < step; i++){
            rotateC();
        }
    }

    void draw(ShapeRenderer shapeRenderer){
        if(lit){
            shapeRenderer.setColor(Color.RED);
        }
        else {
            shapeRenderer.setColor(Color.BLACK);
        }

        for (int i = 0; i < 4; i++){
            if(connection[i])
                shapeRenderer.rect(Core.xOffSet+tile_size*x+3*tile_size/8,Core.yOffSet+tile_size*y+tile_size/2,tile_size/8,0,tile_size/4,tile_size/2, 1, 1, 90*i);
        }
    }
}