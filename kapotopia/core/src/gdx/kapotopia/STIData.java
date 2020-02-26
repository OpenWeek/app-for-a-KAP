package gdx.kapotopia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;

import java.util.ArrayList;

public class STIData {
    private static XmlReader.Element root;
    private static ArrayMap<String, ObjectMap<String, String>> allSTIs;
    private static ArrayList<String> names;


    /**
     * This function reads the data if needed and puts it in root.
     * It also builds all sti related arrays.
     */
    private static void ensureData(){

        if(root != null) return;//data not loaded yet, load it

        names = new ArrayList<String>();
        XmlReader xml = new XmlReader();
        root = xml.parse(Gdx.files.internal("sprite.xml"));

        allSTIs = new ArrayMap<String, ObjectMap<String, String>>();

        Array<XmlReader.Element> ist = root.getChildrenByName("ist");
        //Gdx.app.log();
        Array<XmlReader.Element> fakeIst = root.getChildByName("fakeist-l").getChildrenByName("fakeist");
        Array<XmlReader.Element> maybeIst = root.getChildByName("maybeist-l").getChildrenByName("maybeist");

        for(XmlReader.Element e : ist){

            String tag = "ist";
            String name = e.getAttribute("name");
            names.add(name);
            String texturePath = e.getAttribute("texture");
            String desc = e.getChildByName("explanation").getText();

            ObjectMap<String, String> objectRep = new ObjectMap<String, String>();
            objectRep.put("name", name);
            objectRep.put("desc", desc);
            objectRep.put("path", texturePath);
            objectRep.put("type", tag);
            allSTIs.put(name, objectRep);

        }

        for(XmlReader.Element e : fakeIst){

            String tag = "fakeist";
            String name = e.getAttribute("name");
            names.add(name);
            String texturePath = e.getAttribute("texture");
            //TODO Add descriptions for fakeists?
            String desc = "";

            ObjectMap<String, String> objectRep = new ObjectMap<String, String>();
            objectRep.put("name", name);
            objectRep.put("desc", desc);
            objectRep.put("path", texturePath);
            objectRep.put("type", "fakeist");
            allSTIs.put(name, objectRep);
        }

        for(XmlReader.Element e : maybeIst){

            String tag = "maybeist";
            String name = e.getAttribute("name");
            names.add(name);
            String texturePath = e.getAttribute("texture");
            String desc = e.getChildByName("explanation").getText();

            ObjectMap<String, String> objectRep = new ObjectMap<String, String>();
            objectRep.put("name", name);
            objectRep.put("path", texturePath);
            objectRep.put("desc", desc);
            objectRep.put("type", "maybeist");
            allSTIs.put(name, objectRep);
        }

    }

    public static String getIstDesc(String name){
        ensureData();
        return allSTIs.get(name).get("desc");
    }

    public static String getIstType(String name){
        ensureData();
        return allSTIs.get(name).get("type");
    }

    public static String getIstSpritePath(String name){
        ensureData();
        return allSTIs.get(name).get("path");
    }

    public static Object[] getIstNames(){
        ensureData();
        return names.toArray();
    }

}
