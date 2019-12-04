package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

    private Goal[] goals;//All goals at y = sizey-1
    private int correctGoal;
    private Stack<Pair> stack;
    private HashSet<Tile> set;
    private Random random;

    private SpriteBatch batch;

    private Texture goalT;
    private Texture falseGoalT;

    public Core(Game3 parent, int sizex, int sizey){
        this(parent, sizex, sizey, 2);
    }

    public Core(Game3 parent, int sizex, int sizey, int nbGoals){
        this.parent = parent;
        this.succeeded = false;
        this.sizex = sizex;
        this.sizey = sizey;

        Pair.tile_size = Math.min(Gdx.graphics.getWidth()/sizex,Gdx.graphics.getHeight()/sizey ) - 5;

        random = new Random();
        tiles = new Pair[sizex][sizey];

        goalT = new Texture("game3/Serrure.png");
        falseGoalT = new Texture("game3/Serrure2.png");

        width = Pair.tile_size*sizex;
        height = Pair.tile_size*sizey;

        xOffSet = (Gdx.graphics.getWidth()-width)/2;
        yOffSet = (Gdx.graphics.getHeight()-height)/2;

        stack = new Stack<Pair>();
        set = new HashSet<Tile>(sizex*sizey);

        setGoal(nbGoals, sizey);

        batch = new SpriteBatch();

        //TODO : assign correct goal
        correctGoal = goals[nbGoals/2].pos;
        goals[nbGoals/2].sprite.setTexture(goalT);

        for (Goal g: goals) {
            createPath(g.pos,sizey-1);
        }

        updatePath(tiles[0][0]);

    }

    private void createPath(int xDest, int yDest){

        //tiles[0][0] = Pair.randomTurn(0,0);
        int x = 0;
        int y = 0;
        boolean vert = true;
        boolean dx = false;
        boolean dy = false;

        int p = 25;

        while (! ((x == xDest) && (y == yDest))){
            if(vert){
                if(random.nextBoolean()) {//horizontal
                    vert = false;
                    dx = (xDest - x) < 0;
                    if (random.nextInt(100) < p) {
                        dx = !dx;
                        p -= 1;
                    }
                    if(tiles[x][y] == null){
                        tiles[x][y] = Pair.randomTurn(x, y);
                    }
                    else if (!tiles[x][y].turn){
                        tiles[x][y] = Pair.randomBoth(x, y);
                    }

                    if (dx && x > 0) {
                        x--;
                    }
                    else if(x < sizex - 1){
                        x++;
                    }
                }
                else{

                    dy = (yDest - y) < 0;
                    if(random.nextInt(100) < p){
                        dy = !dy;
                        p-=1;
                    }
                    if(tiles[x][y] == null){
                        tiles[x][y] = Pair.randomLine(x ,y);
                    }
                    else if (!tiles[x][y].line){
                        tiles[x][y] = Pair.randomBoth(x,y);
                    }
                    if (dy && y > 0) {
                        y--;
                    }
                    else if(y < sizey - 1){
                        y++;
                    }
                }
            }
            else{
                if(random.nextBoolean()) {//horizontal

                    dx = (xDest - x) < 0;
                    if (random.nextInt(100) < p) {
                        dx = !dx;
                        p -= 1;
                    }
                    if(tiles[x][y] == null){
                        tiles[x][y] = Pair.randomLine(x, y);
                    }
                    else if (!tiles[x][y].turn)
                    {
                        tiles[x][y] = Pair.randomBoth(x, y);
                    }
                    if (dx && x > 0) {
                        x--;
                    }
                    else if(x < sizex - 1){
                        x++;
                    }
                }
                else{
                    vert = true;
                    dy = (yDest - y) < 0;
                    if(random.nextInt(100) < p){
                        dy = !dy;
                        p-=1;
                    }

                    if(tiles[x][y] == null){
                        tiles[x][y] = Pair.randomTurn(x ,y);
                    }
                    else if (!tiles[x][y].line)
                    {
                        tiles[x][y] = Pair.randomBoth(x,y);
                    }
                    if (dy && y > 0) {
                        y--;
                    }
                    else if(y < sizey - 1){
                        y++;
                    }
                }
            }
        }
        if(vert){
            if(tiles[x][y] == null){
                tiles[x][y] = Pair.randomLine(x ,y);
            }
            else if (!tiles[x][y].line){
                tiles[x][y] = Pair.randomBoth(x,y);
            }
        }
        else {
            if(tiles[x][y] == null){
                tiles[x][y] = Pair.randomTurn(x ,y);
            }
            else if (!tiles[x][y].line){
                tiles[x][y] = Pair.randomBoth(x,y);
            }
        }
    }

    private void setGoal(int nbGoals, int y){
        goals = new Goal[nbGoals];
        int part = sizex/(nbGoals+1);
        for (int i = 1; i <= nbGoals; i++){
            goals[i-1] = new Goal(falseGoalT ,i*part, y);
        }

    }

    private boolean checkGoal(){
        Tile t = tiles[correctGoal][sizey-1].connection(0);
        return  t != null && t.isLit();
    }

    private void updatePath(Pair moved){

        for(Pair[] pa : tiles){
            for(Pair p : pa)
            {
                if(p != null){
                    p.unlit();
                }
            }
        }
        Pair origin = tiles[0][0];

        set.clear();

        Tile t = origin.connection(2);
        if (t == null){
            return;
        }
        t.lit();

        stack.add(origin);
        set.add(t);

        //N,W,S,E
        while (!stack.isEmpty()){
            Pair p = stack.pop();
            if(p.line && !p.turn && p.t2 != null){
                System.out.println("stap");
            }
            if(p.connection(0) != null && p.connection(0).isLit() && p.y < sizey-1){
                Pair p2 =  tiles[p.x][p.y+1];
                if(p2 != null){
                    Tile t2 = p2.connection(2);
                    if( t2 != null && ! set.contains(t2)){
                        stack.add(p2);
                        set.add(t2);
                        t2.lit();
                    }
                }
            }
            if(p.connection(1) != null && p.connection(1).isLit() && p.x > 0){
                Pair p2 = tiles[p.x-1][p.y];
                if(p2 != null){
                    Tile t2 = p2.connection(3);
                    if(t2 != null && !set.contains(t2)){
                        stack.add(p2);
                        set.add(t2);
                        t2.lit();
                    }
                }
            }
            if(p.connection(2) != null && p.connection(2).isLit() && p.y > 0){
                Pair p2 = tiles[p.x][p.y-1];
                if(p2 != null){
                    Tile t2 = p2.connection(0);
                    if(t2 != null && !set.contains(t2)){
                        stack.add(p2);
                        set.add(t2);
                        t2.lit();
                    }
                }
            }
            if(p.connection(3) != null  && p.connection(3).isLit() && p.x < sizex-1){
                Pair p2 = tiles[p.x+1][p.y];
                if(p2 != null){
                    Tile t2 = p2.connection(1);
                    if(t2 != null && !set.contains(t2) ){
                        stack.add(p2);
                        set.add(t2);
                        t2.lit();
                    }
                }
            }
        }
    }

    void touchHandler(int x, int y){
        //Click inside puzzle
        if(x >= xOffSet && y >= yOffSet && x <= xOffSet+width && y <= yOffSet+height){
            int X = (x-xOffSet)/Pair.tile_size;
            int Y = (y-yOffSet)/Pair.tile_size;
            if(tiles[X][Y] != null){
                tiles[X][Y].rotate(1);

                updatePath(tiles[X][Y]);
                if(checkGoal()){
                    // Game Over
                    this.succeeded = true;
                    parent.back();
                }
            }

        }
    }

    public void draw(){
        batch.begin();
        for (Pair[] t : tiles){
            for(Pair tile : t){
                if(tile != null){
                    tile.draw(batch);
                }
            }
        }
        for(Goal i : goals){
            //shapeRenderer.rect(xOffSet+i*Pair.tile_size, yOffSet+ sizey*Pair.tile_size, Pair.tile_size, Pair.tile_size);
            i.draw(batch);
        }
        batch.end();

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

    public boolean line;
    public boolean turn;

    private static Texture crossT = new Texture("game3/cross.png");
    private static Texture tcrossT = new Texture("game3/tcross.png");
    private static Texture lineT = new Texture("game3/line.png");
    private static Texture halflineT = new Texture("game3/halfline.png");
    private static Texture turnT = new Texture("game3/turn.png");


    public Pair(int x, int y){
        t1 = new Tile();
        t2 = null;
        line = false;
        turn = false;
        this.x = x;
        this.y = y;
    }

    //N,W,S,E
    private static Pair line(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{false, true, false, true}, lineT, x, y);
        p.line = true;
        return p;
    }
    private static Pair dline(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{false, true, false, true}, lineT, x, y);
        p.t2 = new Tile(new boolean[]{true, false, true, false}, halflineT, x, y);
        p.line = true;
        return p;
    }
    private static Pair cross(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, true, true, true}, crossT, x, y);
        p.line = true;
        p.turn = true;
        return p;
    }
    private static Pair tcross(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, true, false, true}, tcrossT, x, y);
        p.line = true;
        p.turn = true;
        return p;
    }
    private static Pair turn(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, false, false, true}, turnT, x, y);
        p.turn = true;
        return p;
    }
    private static Pair dturn(int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, false, false, true}, turnT, x, y);
        p.t2 = new Tile(new boolean[]{true, false, false, true}, turnT, x, y);
        p.t2.rotate(2);
        p.turn = true;
        return p;
    }

    static Pair randomLine(int x, int y){
        int i = r.nextInt(3);
        switch (i){
            case 0: return line(x,y);
            case 1: return dline(x,y);
            default: return randomBoth(x,y);
        }
    }
    static Pair randomTurn(int x, int y){
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


    boolean isLit(){
        if(t2 != null){
            return t1.isLit() || t2.isLit();
        }
        else{
            return t1.isLit();
        }
    }
    void unlit()
    {
        if(t2 != null){
            t2.unlit();
        }
        t1.unlit();
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
    }

    void draw(SpriteBatch batch){
        if(t2 != null){
            t2.draw(batch);
        }
        t1.draw(batch);
    }

}

class Tile{

    //N,W,S,E
    boolean[] connection;
    Sprite sprite;
    private boolean lit;

    Tile(){
        connection = new boolean[4];
        lit = false;
    }

    Tile(boolean[] connection, Texture texture, int x, int y){
        lit = false;
        this.connection = connection;

        sprite = new Sprite(texture);
        sprite.setPosition(Core.xOffSet + Pair.tile_size * x , Core.yOffSet + Pair.tile_size * y );
        sprite.setSize(Pair.tile_size, Pair.tile_size);
        sprite.setOriginCenter();
    }

    void lit(){
        if(sprite != null){
            sprite.setColor(Color.RED);
        }

        lit = true;
    }
    void unlit(){
        lit = false;
        if(sprite != null){
            sprite.setColor(Color.WHITE);
        }
    }
    boolean isLit(){return lit;}

    void draw(SpriteBatch batch){
        if(sprite != null) {
            sprite.draw(batch);
        }
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
        //clock-wise
        if (sprite != null) {
            sprite.rotate(-step * 90);
        }
    }
}

class Goal{
    int pos;
    Sprite sprite;

    Goal(Texture texture, int x, int y){
        pos = x;
        sprite = new Sprite(texture);
        sprite.setPosition(Core.xOffSet + Pair.tile_size * x , Core.yOffSet + Pair.tile_size * y );
        sprite.setSize(Pair.tile_size, Pair.tile_size);
        sprite.setOriginCenter();
    }

    void draw(SpriteBatch batch){
        if(sprite != null) {
            sprite.draw(batch);
        }
    }
}