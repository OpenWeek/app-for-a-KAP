package gdx.kapotopia.STIDex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;
import java.util.Collections;

public class STIData {
    private static XmlReader.Element root;
    private static ArrayList<STI> data;
    private static ArrayList<STI> ists;
    private static ArrayList<STI> fakeists;
    private static ArrayList<STI> maybeists;

    /**
     * This function reads the data if needed and puts it in root.
     * It also builds all sti related arrays.
     */
    private static void ensureData() {

        if(root != null) return;//data not loaded yet, load it

        data = new ArrayList<STI>();
        ists = new ArrayList<STI>();
        fakeists = new ArrayList<STI>();
        maybeists = new ArrayList<STI>();
        XmlReader xml = new XmlReader();
        root = xml.parse(Gdx.files.internal("sprite.xml"));

        Array<XmlReader.Element> ist = root.getChildByName("ist-l").getChildrenByName("ist");
        Array<XmlReader.Element> fakeIst = root.getChildByName("fakeist-l").getChildrenByName("fakeist");
        Array<XmlReader.Element> maybeIst = root.getChildByName("maybeist-l").getChildrenByName("maybeist");

        for(XmlReader.Element e : ist){

            String tag = "ist";
            String name = e.getAttribute("name");
            String texturePath = e.getAttribute("texture");
            String desc = e.getChildByName("explanation").getText();

            data.add(new STI(name, desc, texturePath, tag));
            ists.add(new STI(name, desc, texturePath, tag));
        }

        for(XmlReader.Element e : fakeIst){

            String tag = "fakeist";
            String name = e.getAttribute("name");
            String texturePath = e.getAttribute("texture");
            //TODO Add descriptions for fakeists?
            String desc = "";

            data.add(new STI(name, desc, texturePath, tag));
            fakeists.add(new STI(name, desc, texturePath, tag));
        }

        for(XmlReader.Element e : maybeIst){

            String tag = "maybeist";
            String name = e.getAttribute("name");
            String texturePath = e.getAttribute("texture");
            String desc = e.getChildByName("explanation").getText();

            data.add(new STI(name, desc, texturePath, tag));
            maybeists.add(new STI(name, desc, texturePath, tag));
        }

    }

    public static STI[] getData() {
        ensureData();
        return data.toArray(new STI[0]);
    }

    public static STI[] getIsts() {
        ensureData();
        return ists.toArray(new STI[0]);
    }

    public static STI[] getFakeists() {
        ensureData();
        return fakeists.toArray(new STI[0]);
    }

    public static STI[] getMaybeists() {
        ensureData();
        return maybeists.toArray(new STI[0]);
    }

    public static STI[] getIstAndMaybeIsts() {
        ensureData();
        ArrayList<STI> tmp = new ArrayList<STI>();
        tmp.addAll(maybeists);
        tmp.addAll(ists);
        Collections.sort(tmp);
        return tmp.toArray(new STI[0]);
    }
}
