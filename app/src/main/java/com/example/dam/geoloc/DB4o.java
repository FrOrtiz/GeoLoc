package com.example.dam.geoloc;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.AndroidSupport;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import java.io.IOException;
import java.util.ArrayList;

public class DB4o {

    private ObjectContainer objectContainer;

    public EmbeddedConfiguration getDb4oConfig() throws IOException {
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
        configuration.common().add(new AndroidSupport());
        configuration.common().objectClass(Localizacion.class).
                objectField("date").indexed(true);
        return configuration;
    }

    private ObjectContainer openDataBase(String ruta) {
        try {
            objectContainer = Db4oEmbedded.openFile(getDb4oConfig(), ruta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectContainer;
    }

    public void addTracking(Localizacion trackingObject, String ruta){
        objectContainer = openDataBase(ruta);

        objectContainer.store(trackingObject);
        objectContainer.commit();

        objectContainer.close();
    }

//    public ArrayList<Localizacion> getAllLocation(String ruta){
//        objectContainer = openDataBase(ruta);
//        ArrayList<Localizacion> result = new ArrayList<Localizacion>();
//
//        Query consulta = objectContainer.query();
//        consulta.constrain(Localizacion.class);
//        ObjectSet<Localizacion> localizaciones = consulta.execute();
//        for(Localizacion localizacion : localizaciones){
//            result.add(localizacion);
//        }
//
//        objectContainer.close();
//
//        return result;
//    }

    public ArrayList<Localizacion> getLocationByDate(final String date, String ruta){
        objectContainer = openDataBase(ruta);
        ArrayList<Localizacion> result = new ArrayList<Localizacion>();

        ObjectSet<Localizacion> locs = objectContainer.query(
                new Predicate<Localizacion>() {
                    @Override
                    public boolean match(Localizacion loc) {
                        return loc.getDate().equals(date);
                    }
                });

        for(Localizacion localizacion: locs){
            result.add(localizacion);
        }

        objectContainer.close();

        return result;
    }


}