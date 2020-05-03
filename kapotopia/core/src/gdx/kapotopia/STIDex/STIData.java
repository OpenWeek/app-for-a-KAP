package gdx.kapotopia.STIDex;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;
import java.util.Collections;

import gdx.kapotopia.Kapotopia;
import gdx.kapotopia.Localisation;

public class STIData {
    private Localisation loc;
    private XmlReader.Element root;
    private ArrayList<STI> data;
    private ArrayList<STI> ists;
    private ArrayList<STI> fakeists;
    private ArrayList<STI> maybeists;

    /**
     * This function reads the data if needed and puts it in root.
     * It also builds all sti related arrays.
     */
    public STIData(Localisation loc) {
        this.loc = loc;
        this.data = new ArrayList<STI>();
        this.ists = new ArrayList<STI>();
        this.fakeists = new ArrayList<STI>();
        this.maybeists = new ArrayList<STI>();
        XmlReader xml = new XmlReader();
        this.root = xml.parse(Gdx.files.internal("sprite.xml"));

        Array<XmlReader.Element> ist = root.getChildByName("ist-l").getChildrenByName("ist");
        Array<XmlReader.Element> fakeIst = root.getChildByName("fakeist-l").getChildrenByName("fakeist");
        Array<XmlReader.Element> maybeIst = root.getChildByName("maybeist-l").getChildrenByName("maybeist");

        String tag = "ist";
        for(XmlReader.Element e : ist){

            final String nameKey = e.getAttribute("name");
            final String name = loc.getString(nameKey);
            final String texturePath = e.getAttribute("texture");
            final String descKey = e.getChildByName("explanation").getText();
            final String desc = loc.getString(descKey);

            data.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
            ists.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
        }

        tag = "fakeist";
        for(XmlReader.Element e : fakeIst){

            final String nameKey = e.getAttribute("name");
            final String name = loc.getString(nameKey);
            final String texturePath = e.getAttribute("texture");
            final String descKey = e.getChildByName("explanation").getText();
            final String desc = loc.getString(descKey);

            data.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
            fakeists.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
        }

        tag = "maybeist";
        for(XmlReader.Element e : maybeIst){

            final String nameKey = e.getAttribute("name");
            final String name = loc.getString(nameKey);
            final String texturePath = e.getAttribute("texture");
            final String descKey = e.getChildByName("explanation").getText();
            final String desc = loc.getString(descKey);

            data.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
            maybeists.add(new STI(nameKey, descKey, name, desc, texturePath, tag));
        }
    }

    public void updateLists() {
        for (STI sti : data) {
            sti.setName(loc.getString(sti.getNameKey()));
            sti.setDescription(loc.getString(sti.getDescriptionKey()));
        }
        for (STI sti : ists) {
            sti.setName(loc.getString(sti.getNameKey()));
            sti.setDescription(loc.getString(sti.getDescriptionKey()));
        }
        for (STI sti : fakeists) {
            sti.setName(loc.getString(sti.getNameKey()));
            sti.setDescription(loc.getString(sti.getDescriptionKey()));
        }
        for (STI sti : maybeists) {
            sti.setName(loc.getString(sti.getNameKey()));
            sti.setDescription(loc.getString(sti.getDescriptionKey()));
        }
    }

    public STI[] getData() {
        return data.toArray(new STI[0]);
    }

    public STI[] getIsts() {
        return ists.toArray(new STI[0]);
    }

    public STI[] getFakeists() {
        return fakeists.toArray(new STI[0]);
    }

    public STI[] getMaybeists() {
        return maybeists.toArray(new STI[0]);
    }

    public STI[] getIstAndMaybeIsts() {
        ArrayList<STI> tmp = new ArrayList<STI>();
        tmp.addAll(maybeists);
        tmp.addAll(ists);
        Collections.sort(tmp);
        return tmp.toArray(new STI[0]);
    }
}
