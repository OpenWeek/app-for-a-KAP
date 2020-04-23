package gdx.kapotopia.Game3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import gdx.kapotopia.AssetsManaging.AssetDescriptors;
import gdx.kapotopia.Fonts.UseFont;
import gdx.kapotopia.Helpers.Builders.PopUpBuilder;
import gdx.kapotopia.Helpers.Builders.TextButtonBuilder;
import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;
import gdx.kapotopia.Screens.Game3;

import java.util.*;

public class Core {

    private final String TAG = this.getClass().getSimpleName();

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
    private int correct;
    private Stack<Pair> stack;
    private HashSet<Tile> set;
    private Random random;

    final Localisation loc = Localisation.getInstance();
    final int nbSafe = 5; //TODO make this dynamic without having to indicate the number of safe/unsafe practice manually
    final int nbUnSafe = 5;

    private final Texture crossT;
    private final Texture tcrossT;
    private final Texture lineT;
    private final Texture halflineT;
    private final Texture turnT;
    private final SpriteBatch batch;

    Sprite source ;


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

        width = Pair.tile_size*sizex;
        height = Pair.tile_size*sizey;

        xOffSet = (Gdx.graphics.getWidth()-width)/2;
        yOffSet = (Gdx.graphics.getHeight()-height)/2;

        stack = new Stack<Pair>();
        set = new HashSet<Tile>(sizex*sizey);

        // Textures Used For tiles
        final AssetManager ass = parent.getGame().ass;
        crossT = ass.get(AssetDescriptors.CROSS_T);
        tcrossT = ass.get(AssetDescriptors.TCROSS_T);
        lineT = ass.get(AssetDescriptors.LINE_T);
        halflineT = ass.get(AssetDescriptors.HALF_LINE_T);
        turnT = ass.get(AssetDescriptors.TURN_T);

        batch = new SpriteBatch();

        setGoal(nbGoals, sizey);

        Random r = new Random();
        ArrayList<String> names = new ArrayList<String>(nbUnSafe);
        for(int i = 0; i < nbUnSafe; i++){
            names.add("unsafe"+(i+1));
        }
        Collections.shuffle(names);
        for (int i = 0; i < nbGoals; i++){
            goals[i].popup.setTitle(loc.getPractice(names.get(i)));
        }
        correct = r.nextInt(nbGoals);
        goals[correct].popup.setTitle( loc.getPractice("safe"+(r.nextInt(nbSafe)+1)) );
        correctGoal = goals[correct].pos;

        for (int i = 0; i < nbGoals; i++){
            if(i == correct){
                continue;
            }
            createPath(goals[i].pos,sizey-1);
        }
        source = new Sprite(parent.getGame().ass.get(AssetDescriptors.BATTERY));
        source.setPosition(Core.xOffSet, Core.yOffSet - Pair.tile_size);
        source.setSize(Pair.tile_size, Pair.tile_size);

        createPath(correctGoal, sizey-1);

        updatePath(tiles[0][0]);
    }

    private void addLine(int x, int y){
        if(tiles[x][y] == null){
            tiles[x][y] = Pair.randomLine(lineT, halflineT, crossT, tcrossT, x ,y);
        }
        else if (!tiles[x][y].line){
            tiles[x][y] = Pair.randomBoth(crossT, tcrossT, x,y);
        }
    }
    private void addTurn(int x, int y){
        if(tiles[x][y] == null){
            tiles[x][y] = Pair.randomTurn(turnT, crossT, tcrossT, x, y);
        }
        else if (!tiles[x][y].turn){
            tiles[x][y] = Pair.randomBoth(crossT, tcrossT, x, y);
        }
    }
    private void addTo(int to, int x, int y, int from){
        Pair t = tiles[x][y];
        if(t != null && t.connection(from) != null && t.connection(from).connection[to]){
            return;//already in place
        }

        if((to+from)%2 == 0){//must add a line
            if(t != null){
                if(!t.turn){//single line
                    tiles[x][y] = Pair.dline(lineT, halflineT, x,y);
                }
                else if(!t.line){
                    if(t.t2 == null){//simple turn
                        int turnTo = t.t1.connection[(from+1)%4] ? (from+1)%4 : (from+3)%4;
                        Pair p = Pair.tcross(tcrossT, x,y);
                        Tile tcross = p.t1;
                        while (!(tcross.connection[from] && tcross.connection[to] && tcross.connection[turnTo])){
                            p.rotate(1);
                        }
                        tiles[x][y] = p;
                    }
                    else {//dturn
                        tiles[x][y] = Pair.cross(crossT, x,y);
                    }
                }
                else { //tcross
                    tiles[x][y] = Pair.cross(crossT, x,y);
                }
            }
            else {
                Pair p = Pair.line(lineT, x,y);
                while (p.connection(from) == null){
                    p.rotate(1);
                }
                tiles[x][y] = p;
            }
        }
        else if((to+from)%2 == 1){//must add a turn
            if(t != null){
                if(!t.turn){
                    if(t.t2 != null){//dline
                        tiles[x][y] = Pair.cross(crossT, x,y);
                    }
                    else {//simple line
                        int lineTo = t.t1.connection[(from+1)%4] ? (from+1)%4 : (from+3)%4;
                        Pair p = Pair.tcross(tcrossT, x,y);
                        Tile tcross = p.t1;
                        while (!(tcross.connection[from] && tcross.connection[to] && tcross.connection[lineTo])){
                            p.rotate(1);
                        }
                        tiles[x][y] = p;
                    }
                }
                else if(t.line){//tcross
                    tiles[x][y] = Pair.cross(crossT, x,y);
                }
                else {
                    if(t.t2 != null){//dturn
                        tiles[x][y] = Pair.cross(crossT, x,y);
                    }
                    else {//turn
                        Tile tile = t.t1;
                        if(tile.connection[from] || tile.connection[to]){
                            int lineTo = t.t1.connection[(from+1)%4] ? (from+1)%4 : (from+3)%4;
                            Pair p = Pair.tcross(tcrossT, x,y);
                            Tile tcross = p.t1;
                            while (!(tcross.connection[from] && tcross.connection[to] && tcross.connection[lineTo])){
                                p.rotate(1);
                            }
                            tiles[x][y] = p;
                        }
                        else {
                            Pair p = Pair.dturn(turnT, x,y);
                            while (!(p.t1.connection[from] && p.t1.connection[to])){
                                p.rotate(1);
                            }
                            tiles[x][y] = p;
                        }
                    }
                }
            }
            else {
                Pair p = Pair.turn(turnT, x,y);
                while (p.connection(from) == null && p.connection(to) == null){
                    p.rotate(1);
                }
                tiles[x][y] = p;
            }
        }
    }

    /**
     * builds a path from the input to the specified location
     * @param xDest  pixel horizontal position of the goal given in in-game coordinates
     * @param yDest  pixel vertical position of the goal given in in-game coordinates
     */
    private void createPath(int xDest, int yDest){

        int x = 0;
        int y = 0;
        boolean vert = true;
        boolean dx = false;
        boolean dy = false;

        //N,W,S,E
        int previous = 2;

        int p = 40;

        while (! ((x == xDest) && (y == yDest))){
            if(vert){
                if(random.nextBoolean()) {//horizontal
                    vert = false;
                    dx = (xDest - x) < 0;
                    if (random.nextInt(100) < p) {
                        dx = !dx;
                        p -= 1;
                    }

                    if (dx && x > 0) {
                        //addTurn(x,y);
                        addTo(1,x,y,previous);
                        x--;
                        previous = 3;
                    }
                    else if(x < sizex - 1){
                        //addTurn(x,y);
                        addTo(3,x,y,previous);
                        x++;
                        previous = 1;
                    }
                    else {
                        vert = true;
                    }
                }
                else{
                    dy = (yDest - y) < 0;
                    if(random.nextInt(100) < p){
                        dy = !dy;
                        p-=1;
                    }
                    if (previous != 2 && dy && y > 0) {
                        //addTurn(x,y);
                        addTo(2,x,y,previous);
                        y--;
                        previous = 0;
                    }
                    else if(previous != 0 && y < sizey - 1){
                        //addTurn(x,y);
                        addTo(0,x,y,previous);
                        y++;
                        previous = 2;
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
                    if (previous != 1 && dx && x > 0) {
                        //addTurn(x,y);
                        addTo(1,x,y,previous);
                        x--;
                        previous = 3;
                    }
                    else if(previous != 3 && x < sizex - 1){
                        //addTurn(x,y);
                        addTo(3,x,y,previous);
                        x++;
                        previous = 1;
                    }
                }
                else{
                    vert = true;
                    dy = (yDest - y) < 0;
                    if(random.nextInt(100) < p){
                        dy = !dy;
                        p-=1;
                    }

                    if (dy && y > 0) {
                        //addTurn(x,y);
                        addTo(2,x,y,previous);
                        y--;
                        previous = 0;
                    }
                    else if(y < sizey - 1){
                        //addTurn(x,y);
                        addTo(0,x,y,previous);
                        y++;
                        previous = 2;
                    }
                    else {
                        vert = false;
                    }
                }
            }
        }
        if(vert){
            addLine(x,y);
        }
        else {
            addTurn(x,y);
        }
    }

    /**
     * Creates a number of goals at a given game vertical position
     * @param nbGoals  number of goals to generate
     * @param y  vertical position, given in in-game coordinates
     */
    private void setGoal(int nbGoals, int y){
        Texture closedTexture1 = parent.getGame().ass.get(AssetDescriptors.CLOSED_LOCK1);
        Texture closedTexture2 = parent.getGame().ass.get(AssetDescriptors.CLOSED_LOCK2);

        Texture openTexture1 = parent.getGame().ass.get(AssetDescriptors.OPENED_LOCK1);
        Texture openTexture2 = parent.getGame().ass.get(AssetDescriptors.OPENED_LOCK2);

        Random r = new Random();
        goals = new Goal[nbGoals];
        int part = sizex/(nbGoals+1);
        for (int i = 1; i <= nbGoals; i++){
            Texture open;
            Texture closed;
            if(r.nextBoolean()){
                open = openTexture1;
                closed = closedTexture1;
            }
            else {
                open = openTexture2;
                closed = closedTexture2;
            }
            goals[i-1] = new Goal(closed, open ,i*part, y, parent.getGame(), parent.getPopStage());
        }

    }

    /**
     * Checks whethers the goal is connected to the input
     */
    private boolean checkGoal(){
        Tile t = tiles[correctGoal][sizey-1].connection(0);
        if(t != null && t.isLit()){
            goals[correct].open();
            return true;
        }
        return  false;
    }

    /**
     * Colors the whole path that's connected to the input.
     * @param moved Pair of tiles whose move has potentially changed the connected path
     */
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
                Gdx.app.log(TAG, "stap");
            }
            if(p.connection(0) != null && p.connection(0).isLit() && p.y < sizey-1){
                Pair p2 =  tiles[p.x][p.y+1];
                if(p2 != null){
                    Tile ClosedTexture1 = p2.connection(2);
                    if( ClosedTexture1 != null && ! set.contains(ClosedTexture1)){
                        stack.add(p2);
                        set.add(ClosedTexture1);
                        ClosedTexture1.lit();
                    }
                }
            }
            if(p.connection(1) != null && p.connection(1).isLit() && p.x > 0){
                Pair p2 = tiles[p.x-1][p.y];
                if(p2 != null){
                    Tile ClosedTexture1 = p2.connection(3);
                    if(ClosedTexture1 != null && !set.contains(ClosedTexture1)){
                        stack.add(p2);
                        set.add(ClosedTexture1);
                        ClosedTexture1.lit();
                    }
                }
            }
            if(p.connection(2) != null && p.connection(2).isLit() && p.y > 0){
                Pair p2 = tiles[p.x][p.y-1];
                if(p2 != null){
                    Tile ClosedTexture1 = p2.connection(0);
                    if(ClosedTexture1 != null && !set.contains(ClosedTexture1)){
                        stack.add(p2);
                        set.add(ClosedTexture1);
                        ClosedTexture1.lit();
                    }
                }
            }
            if(p.connection(3) != null  && p.connection(3).isLit() && p.x < sizex-1){
                Pair p2 = tiles[p.x+1][p.y];
                if(p2 != null){
                    Tile ClosedTexture1 = p2.connection(1);
                    if(ClosedTexture1 != null && !set.contains(ClosedTexture1) ){
                        stack.add(p2);
                        set.add(ClosedTexture1);
                        ClosedTexture1.lit();
                    }
                }
            }
        }
    }

    /**
     * This function handles the touch action
     * @param x  pixel horizontal position of the touch action
     * @param y  pixel vertical position of the touch action
     */
    void touchHandler(int x, int y){
        //Click inside puzzle
        int X = (x-xOffSet)/Pair.tile_size;
        int Y = (y-yOffSet)/Pair.tile_size;

        if(x >= xOffSet && y >= yOffSet && x <= xOffSet+width && y <= yOffSet+height){
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

        for (Goal g : goals){
            if (X == g.pos && Y == sizey){
                g.popup.show();
            }
        }

    }

    /**
     * Draws the puzzle parts and the goals
     */
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
            i.draw(batch);
        }
        source.draw(batch);
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


    public Pair(int x, int y){
        t1 = new Tile();
        t2 = null;
        line = false;
        turn = false;
        this.x = x;
        this.y = y;
    }

    //N,W,S,E
    static Pair line(final Texture textureLineT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{false, true, false, true}, textureLineT, x, y);
        p.line = true;
        return p;
    }
    static Pair dline(final Texture textureLineT, final Texture textureHalfLineT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{false, true, false, true}, textureLineT, x, y);
        p.t2 = new Tile(new boolean[]{true, false, true, false}, textureHalfLineT, x, y);
        p.line = true;
        return p;
    }
    static Pair cross(final Texture textureCrossT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, true, true, true}, textureCrossT, x, y);
        p.line = true;
        p.turn = true;
        return p;
    }
    static Pair tcross(final Texture textureTCrossT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, true, false, true}, textureTCrossT, x, y);
        p.line = true;
        p.turn = true;
        return p;
    }
    static Pair turn(final Texture textureTurnT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, false, false, true}, textureTurnT, x, y);
        p.turn = true;
        return p;
    }
    static Pair dturn(final Texture textureTurnT, int x, int y){
        Pair p = new Pair(x, y);
        p.t1 = new Tile(new boolean[]{true, false, false, true}, textureTurnT, x, y);
        p.t2 = new Tile(new boolean[]{true, false, false, true}, textureTurnT, x, y);
        p.t2.rotate(2);
        p.turn = true;
        return p;
    }

    static Pair randomLine(final Texture textureLineT, final Texture textureHalfLineT,
                           final Texture textureCrossT, final Texture textureTCrossT,
                           int x, int y){
        int i = r.nextInt(3);
        switch (i){
            case 0: return line(textureLineT, x,y);
            case 1: return dline(textureLineT, textureHalfLineT, x,y);
            default: return randomBoth(textureCrossT, textureTCrossT, x,y);
        }
    }
    static Pair randomTurn(final Texture turnT, final Texture textureCrossT, final Texture textureTCrossT,
                           int x, int y){
        int i = r.nextInt(3);
        switch (i){
            case 0: return turn(turnT, x,y);
            case 1: return dturn(turnT, x,y);
            default: return randomBoth(textureCrossT, textureTCrossT, x,y);
        }
    }
    public static Pair randomBoth(final Texture textureCrossT, final Texture textureTCrossT,
                                  int x, int y){
        int i = r.nextInt(2);
        if (i == 0){
            return cross(textureCrossT, x,y);
        }
        else{
            return tcross(textureTCrossT, x,y);
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
    final PopUpBuilder popup;

    private Sprite sprite;
    private Texture closed;
    private Texture open;

    Goal(Texture closed, Texture open, int x, int y, final Kapotopia game, Stage stage){
        pos = x;
        this.closed = closed;
        this.open = open;
        sprite = new Sprite(closed);
        sprite.setPosition(Core.xOffSet + Pair.tile_size * x , Core.yOffSet + Pair.tile_size * y );
        sprite.setSize(Pair.tile_size, Pair.tile_size);
        sprite.setOriginCenter();

        popup = new PopUpBuilder(game, stage);
        popup.setTitle("TEMPLATE");
        TextButton btnYes = new TextButtonBuilder("CANCEL").withStyle(UseFont.AESTHETIC_NORMAL_BLACK).build();
        btnYes.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Do whatever here for exit button
                popup.close();
                return true;
            }

        });
        popup.addButton(btnYes);
        popup.setPosition(0,500);
    }

    void open(){
        sprite.setTexture(open);
    }

    void draw(SpriteBatch batch){
        if(sprite != null) {
            sprite.draw(batch);
        }
    }
}