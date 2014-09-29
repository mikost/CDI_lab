package eap63class.producer;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eap63class.MikkosLogger;
import eap63class.bean.BeanManager;

public class GenericProducer {
	static {
		MikkosLogger.log("Loading class \""+ GenericProducer.class.getSimpleName() +"\"\n");
	}
	private static int instanceID = 0;

	private int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("genericProducer"+ myInstanceID +"#init\n");		
	}

    @PersistenceContext
	private EntityManager em;

//    void setEm(EntityManager em) {
//		MikkosLogger.log("genericProducer"+ myInstanceID +".setEm\n");		
//    	this.em = em;
//    }

    @Produces
    EntityManager getEm() {
		MikkosLogger.log("genericProducer"+ myInstanceID +".getEm\n");		
    	return this.em;
    }

    @PostConstruct
    public void initComplete() {
		MikkosLogger.log("genericProducer"+ myInstanceID +" INIT COMPLETE!\n");
    }

}