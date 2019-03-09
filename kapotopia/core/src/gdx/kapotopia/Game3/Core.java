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
    private ArrayDeque<Tile> queue;
    private Random random;

    private ShapeRenderer shapeRenderer;


    public Core(Screen parent, int sizex, int sizey){
        this(parent, sizex, sizey, 1);
    }

    public Core(Screen parent, int sizex, int sizey, int nbGoals){
        this.parent = parent;
        this.sizex = sizex;
        this.sizey = sizey;
        random = new Random();
        tiles = new Tile[sizex][sizey];
        for (int x = 0; x < sizex; x++){
            for(int y = 0; y < sizey; y++){
                tiles[x][y] = new Tile(x,y);
                for(int i =0; i < 4; i++){
                    int r = random.nextInt(8);
                    if(r < 4){
                        tiles[x][y].connection[r] = true;
                    }
                }
            }
        }

        width = Tile.tile_size*sizex;
        height = Tile.tile_size*sizey;

        xOffSet = (Gdx.graphics.getWidth()-width)/2;
        yOffSet = (Gdx.graphics.getHeight()-height)/2;

        queue = new ArrayDeque<Tile>();
        setGoal(nbGoals);
        tiles[0][0].lit = true;

        shapeRenderer = new ShapeRenderer();

        //TODO : assign correct goal
        correctGoal = goals[0];

        createPath();
    }

    private void createPath(){
        Tile t = tiles[0][0];
        Tile g = tiles[correctGoal][sizey-1];
        while (t != g){
            int dx = correctGoal-t.x;
            int dy = sizey -1 - t.y;
            if (dx < 0){
                dx=-dx;
                if(dx > dy){
                    t.connection[1] = true;
                    t.rotate(random.nextInt(3));
                    t = tiles[t.x-1][t.y];
                    t.connection[3] = true;
                }
                else{
                    t.connection[0] = true;
                    t.rotate(random.nextInt(3));
                    t = tiles[t.x][t.y+1];
                    t.connection[2] = true;
                }
            }
            else{
                if(dx > dy){
                    t.connection[3] = true;
                    t.rotate(random.nextInt(3));
                    t = tiles[t.x+1][t.y];
                    t.connection[1] = true;
                }
                else{
                    t.connection[0] = true;
                    t.rotate(random.nextInt(3));
                    t = tiles[t.x][t.y+1];
                    t.connection[2] = true;
                }
            }
        }
    }

    private void setGoal(int nbGoals){
        //if (nbGoals > tiles.length){
        //    throw new NotImplementedException();
        //}
        goals = new int[nbGoals];
        int part = sizex/(nbGoals+1);
        for (int i = 1; i <= nbGoals; i++){
            goals[i-1] = i*part;
        }

    }
    private boolean checkGoal(){
        return  tiles[correctGoal][sizey-1].lit;
    }

    private void updatePath(){

        /*TODO: update from rotated Tile,
        * TODO: store preceding lit in each tile
        * TODO: to both light up an down only the concerned tiles
        */
        queue.add(tiles[0][0]);
        HashSet<Tile> set = new HashSet<Tile>(sizex*sizey);

        while (!queue.isEmpty()){
            Tile t = queue.pop();
            t.lit = true;
            set.add(t);
            boolean[] dir = t.connection;
            if(dir[0] && t.y < sizey-1){
                Tile t2 =  tiles[t.x][t.y+1];
                if(t2.connection[2] && ! set.contains(t2)){
                    queue.add(t2);
                }
            }
            if(dir[1] && t.x > 0){
                Tile t2 = tiles[t.x-1][t.y];
                if(t2.connection[3] && !set.contains(t2)){
                    queue.add(t2);
                }
            }
            if(dir[2] && t.y > 0){
                Tile t2 = tiles[t.x][t.y-1];
                if(t2.connection[0] && !set.contains(t2)){
                    queue.add(t2);
                }
            }
            if(dir[3] && t.x < sizex-1){
                Tile t2 = tiles[t.x+1][t.y];
                if(t2.connection[1] && !set.contains(t2) ){
                    queue.add(t2);
                }
            }
        }
    }

    public void touchHandler(int x, int y){
        //Click inside puzzle
        if(x >= xOffSet && y >= yOffSet && x <= xOffSet+width && y <= yOffSet+height){
            int X = (x-xOffSet)/Tile.tile_size;
            int Y = (y-yOffSet)/Tile.tile_size;
            tiles[X][Y].rotate(1);
            updatePath();
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
        shapeRenderer.end();
    }
}

class Tile{

    static final int tile_size = 96;

    //N,W,S,E
    boolean[] connection;
    boolean lit;
    int x;
    int y;

    public Tile(int x, int y){
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
    public void rotate(int step){
        for(int i =0; i < step; i++){
            rotateC();
        }
    }

    public void draw(ShapeRenderer shapeRenderer){
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