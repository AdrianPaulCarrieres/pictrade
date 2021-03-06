package ca.qc.cgmatane.pictrade.donnee;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import ca.qc.cgmatane.pictrade.modele.Photo;

public class PhotoDAO implements Dictionnaire {

    private static PhotoDAO instance = null;
    private BaseDeDonneesServeur accesseurBaseDeDonneesServeur;

    private PhotoHandlerXML photoHandlerXML;
    private List<Photo> listePhoto;

    public static PhotoDAO getInstance() {
        if (instance == null) {
            instance = new PhotoDAO();
        }
        return instance;
    }

    private PhotoDAO() {
        accesseurBaseDeDonneesServeur = BaseDeDonneesServeur.getInstance();
        photoHandlerXML = new PhotoHandlerXML();
        listePhoto = new ArrayList<>();
    }

    public List<Photo> listerPhotoParIdCommerce(HashMap<String, String> parametresPost) {

        if (listePhoto != null ){
            listePhoto.clear();
        }


        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            String xml = accesseurBaseDeDonneesServeur.recupererXML(PAGE_LISTER_PHOTO, parametresPost);
            if(xml != null){

                saxParser.parse(new InputSource(new StringReader(xml)), photoHandlerXML);
                listePhoto = photoHandlerXML.getListePhoto();
            }
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return listePhoto;
    }

    public String ajouterCommerce(HashMap<String, String> parametresPost) {
        String resultat = null;
        try {
            resultat = accesseurBaseDeDonneesServeur.recupererXML(PAGE_AJOUTER_PHOTO, parametresPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultat;
    }
}
