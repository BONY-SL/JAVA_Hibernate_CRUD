package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtilService {


    //create Registry for all Program methods
    private static StandardServiceRegistry registry;

    private static SessionFactory sessionFactory;


    // this method return sessionFactory object reuse
    public static SessionFactory getSessionFactory(){

        try {
            if(sessionFactory==null){

                registry=new StandardServiceRegistryBuilder().configure().build();

                MetadataSources metadataSources=new MetadataSources(registry);

                Metadata metadata=metadataSources.getMetadataBuilder().build();

                sessionFactory=metadata.getSessionFactoryBuilder().build();

            }
        }catch (Exception e){

            e.printStackTrace();

            if(registry != null){

                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
