package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import gdx.kapotopia.Screens.Game3;

import java.util.*;

public class Core {

    static int xOffSet = 0;
    static int yOffSet = 0;

    private Game3 parent;
    private boolean succeeded;

    private Pair [][] tiles;
    private int sizex;
    private int sizey;
    private int width;
    private int height;

    private int[] goals;//All goals at y = sizey-1
    private int correctGoal;
    private Stack<Pair> stack;
    private HashSet<Pair> set;
    private Random random;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;


    public Core(Game3 parent, int sizex, int sizey){
        this(parent, sizex, sizey, 1);
    }

    public Core(Game3 parent, int sizex, int sizey, int nbGoals){
        this.parent = parent;
        this.succeeded = false;
        this.sizex = sizex;
        this.sizey = sizey;

        Pair.tile_size = 96;

        if (sizex*Pair.tile_size > Gdx.graphics.getWidth() || sizey*Pair.tile_size > Gdx.graphics.getHeight()){
            Pair.tile_size = Math.min(Gdx.graphics.getWidth()/sizex,Gdx.graphics.getHeight()/sizey ) - 5;
        }

        random = new Random();
        tiles = new Pair[sizex][sizey];
        for (int x = 0; x < sizex; x++){
            for(int y = 0; y < sizey; y++){
                tiles[x][y] = new Pair(x, y);//new Tile(x,y);
            }
        }

        width = Pair.tile_size*sizex;
        height = Pair.tile_size*sizey;

        xOffSet = (Gdx.graphics.getWidth()-width)/2;
        yOffSet = (Gdx.graphics.getHeight()-height)/2;

        stack = new Stack<Pair>();
        set = new HashSet<Pair>(sizex*sizey);

        setGoal(nbGoals);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        //TODO : assign correct goal
        correctGoal = goals[nbGoals/2];

        for(int i = 0; i < goals.length; i++) {
            createPath(goals[i], sizey-1);
        }

        tiles[0][0].t1.lit = true;

        //updatePath(tiles[0][0]);

    }

    private void createPath(int xDest, int yDest){

        Pair t = tiles[0][0];
        boolean vert = true;

        int p = 25;

        while (! ((t.x == xDest) && (t.y == yDest))){
            if(random.nextBoolean()){//horizontal
                boolean dx = (xDest - t.x) < 0;
                if(random.nextInt(100) < p){
                    dx = !dx;
                    p-=1;
                }

                if (dx && t.x > 0) {
                    if (vert){
                        tiles[t.x - 1][t.y] = Pair.randomTurn(t.x - 1,t.y);
                    }
                    else {
                        tiles[t.x - 1][t.y] = Pair.randomLine(t.x - 1,t.y);
                    }
                    t = tiles[t.x - 1][t.y];
                }
                else if(t.x < sizex - 1){
                    if (vert){
                        tiles[t.x + 1][t.y] = Pair.randomTurn(t.x + 1,t.y);
                    }
                    else {
                        tiles[t.x + 1][t.y] = Pair.randomLine(t.x + 1,t.y);
                    }
                    t = tiles[t.x + 1][t.y];
                }

                vert = false;
            }
            else{
                boolean dy = (yDest - t.y) < 0;
                if(random.nextInt(100) < p){
                    dy = !dy;
                    p-=1;
                }

                if (dy && t.y > 0) {
                    if (vert){
                        tiles[t.x][t.y-1] = Pair.randomTurn(t.x,t.y-1);
                    }
                    else {
                        tiles[t.x][t.y-1] = Pair.randomLine(t.x,t.y-1);
                    }
                    t = tiles[t.x][t.y-1];
                }
                else if(t.y < sizey - 1){
                    if (vert){
                        tiles[t.x][t.y+1] = Pair.randomTurn(t.x,t.y+1);
                    }
                    else {
                        tiles[t.x][t.y+1] = Pair.randomLine(t.x,t.y+1);
                    }
                    t = tiles[t.x][t.y + 1];
                }

                vert = true;
            }
        }

        /*Tile t = tiles[0][0].t1;
        Pair g = tiles[xDest][yDest];

        t.connection[2] = true;
        int oldSide = 2;

        final int p = 25;
        while (t != g.t1 && t != g.t2){
            boolean dx = (xDest - t.x) < 0;
            boolean dy = (yDest - t.y) < 0;
            if(random.nextBoolean()){//horizontal move
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
        }*/
    }

    private void setGoal(int nbGoals){
        goals = new int[nbGoals];
        int part = sizex/(nbGoals+1);
        for (int i = 1; i <= nbGoals; i++){
            goals[i-1] = i*part;
        }

    }
    private boolean checkGoal(){
        return  tiles[correctGoal][sizey-1].connection(0) != null && tiles[correctGoal][sizey-1].lit();
    }

    private void updatePath(Pair moved){

        Pair origin = tiles[0][0];

        set.clear();
        Tile t = origin.connection(2);
        if (t != null){
            //downdatePath(set, origin);
            return;
        }

        t.lit = true;
        stack.add(origin);
        set.add(origin);

        while (!stack.isEmpty()){
            Pair p = stack.pop();
            boolean[] dir = t.connection;
            if(p.connection(0) != null && p.y < sizey-1){
                Pair p2 =  tiles[p.x][p.y+1];
                Tile t2 = p2.connection(2);
                if( t2 != null && ! set.contains(p2)){
                    stack.add(p2);
                    set.add(p2);
                    t2.lit = true;
                }
            }
            if(p.connection(1) != null && p.x > 0){
                Pair p2 = tiles[p.x-1][p.y];
                Tile t2 = p2.connection(3);
                if(t2 != null && !set.contains(p2)){
                    stack.add(p2);
                    set.add(p2);
                    t2.lit = true;
                }
            }
            if(p.connection(2) != null && p.y > 0){
                Pair p2 = tiles[p.x][p.y-1];
                Tile t2 = p2.connection(0);
                if(t2 != null && !set.contains(p2)){
                    stack.add(p2);
                    set.add(p2);
                    t2.lit = true;
                }
            }
            if(p.connection(3) != null && p.x < sizex-1){
                Pair p2 = tiles[p.x+1][p.y];
                Tile t2 = p2.connection(1);
                if(t2 != null && !set.contains(p2) ){
                    stack.add(p2);
                    set.add(p2);
                    t2.lit = true;
                }
            }
        }
        //downdatePath(set, moved);
    }
    /*private void downdatePath(Set<Tile> set, Tile moved){
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
    }*/

    void touchHandler(int x, int y){
        //Click inside puzzle
        if(x >= xOffSet && y >= yOffSet && x <= xOffSet+width && y <= yOffSet+height){
            int X = (x-xOffSet)/Pair.tile_size;
            int Y = (y-yOffSet)/Pair.tile_size;
            tiles[X][Y].rotate(1);

            updatePath(tiles[X][Y]);
            if(checkGoal()){
                // Game Over
                this.succeeded = true;
                parent.back();
            }
        }
    }

    public void draw(){
        batch.begin();
        for (Pair[] t : tiles){
            for(Pair tile : t){
                tile.draw(batch);
                //TODO: DRAW TILE BACKGROUND
            }
        }
        batch.end();

        shapeRenderer.begin(ShapeType.Filled);
        //TODO: replace with true goal indication
        shapeRenderer.setColor(Color.RED);
        for(int i = 0; i < goals.length; i++){
            shapeRenderer.rect(xOffSet+goals[i]*Pair.tile_size, yOffSet+ sizey*Pair.tile_size, Pair.tile_size, Pair.tile_size);
        }
        shapeRenderer.end();
    }

    /**
     * check if the player succeeded or not
     * @return true if the player succeeded, otherwise false
     */
    public boolean playerSucceeded() {
        return this.succeeded;
    }
}


class Pair{
    private static final Random r = new Random();
    static int tile_size;

    Tile t1;
    Tile t2;

    int x;
    int y;

    boolean line;
    boolean turn;

    private static Texture crossT = new Texture("game3/cross.png");
    private static Texture tcrossT = new Texture("game3/tcross.png");
    private static Texture lineT = new Texture("game3/line.png");
    private static Texture dlineT = new Texture("game3/dline.png");
    private static Texture turnT = new Texture("game3/turn.png");
    private static Texture dturnT = new Texture("game3/dturn.png");

    private Sprite sprite;

    public Pair(int x, int y){
        t1 = new Tile();
        t2 = null;
        line = false;
        turn = false;
        this.x = x;
        this.y = y;
    }

    public static Pair line(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{false, true, false, true};
        p.line = true;
        p.sprite = new Sprite(lineT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }
    public static Pair dline(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{true, false, true, false};
        p.t2 = new Tile(new boolean[]{false, true, false, true});
        p.line = true;
        p.sprite = new Sprite(dlineT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }
    public static Pair cross(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{true, true, true, true};
        p.line = true;
        p.turn = true;
        p.sprite = new Sprite(crossT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }
    public static Pair tcross(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{true, true, false, true};
        p.line = true;
        p.turn = true;
        p.sprite = new Sprite(tcrossT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }
    public static Pair turn(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{false, false, true, true};
        p.turn = true;
        p.sprite = new Sprite(turnT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }
    public static Pair dturn(int x, int y){
        Pair p = new Pair(x, y);
        p.t1.connection = new boolean[]{true, true, false, false};
        p.t2 = new Tile(new boolean[]{false, false, true, true});
        p.turn = true;
        p.sprite = new Sprite(dturnT);
        p.sprite.setPosition(Core.xOffSet + tile_size * x , Core.yOffSet + tile_size * y );
        p.sprite.setSize(Pair.tile_size, Pair.tile_size);
        p.sprite.setOriginCenter();
        return p;
    }

    public static Pair randomLine(int x, int y){
        int i = r.nextInt(3);
        switch (i){
            case 0: return line(x,y);
            case 1: return dline(x,y);
            default: return randomBoth(x,y);
        }
    }
    public static Pair randomTurn(int x, int y){
        int i = r.nextInt(3);
        switch (i){
            case 0: return turn(x,y);
            case 1: return dturn(x,y);
            default: return randomBoth(x,y);
        }
    }
    public static Pair randomBoth(int x, int y){
        int i = r.nextInt(2);
        if (i == 0){
            return cross(x,y);
        }
        else{
            return  tcross(x,y);
        }
    }
    boolean lit(){
        if(t2 != null){
            return t1.lit || t2.lit;
        }
        else{
            return t1.lit;
        }
    }

    Tile connection(int side){
        if (t2 != null) {
            if(t2.connection[side]){
                return t2;
            }
        }
        if( t1.connection[side]){
            return t1;
        }
        return null;
    }

    void rotate(int step) {
        if (t2 != null) {
            t2.rotate(step);
        }
        t1.rotate(step);

        //clock-wise
        if (sprite != null) {
            sprite.rotate(step * 90);
        }
    }

    void draw(SpriteBatch batch){

        /*if(lit()){
            shapeRenderer.setColor(Color.RED);
        }
        else {
            shapeRenderer.setColor(Color.BLACK);
        }
        for (int i = 0; i < 4; i++) {
            if (connection(i))
                shapeRenderer.rect(Core.xOffSet + tile_size * x + 3 * tile_size / 8, Core.yOffSet + tile_size * y + tile_size / 2, tile_size / 8, 0, tile_size / 4, tile_size / 2, 1, 1, 90 * i);
        }*/

        if(sprite != null) {
            if(lit()){
                sprite.setColor(Color.RED);
            }else{
                sprite.setColor(Color.WHITE);
            }
            sprite.draw(batch);
        }
    }

}

class Tile{


    //N,W,S,E
    boolean[] connection;
    boolean lit;

    Tile(){
        connection = new boolean[4];
        lit = false;
    }

    Tile(boolean[] connection){
        lit = false;
        this.connection = connection;
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
}