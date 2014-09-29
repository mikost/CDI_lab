package eap63class.repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import eap63class.MikkosLogger;
import eap63class.bean.BeanManager;
import eap63class.domain.SimpleProperty;
import java.util.List;

public class RepositoryManager {

	static {
		MikkosLogger.log("Loading class \""+ RepositoryManager.class.getSimpleName() +"\"\n");
	}
	private static int instanceID = 0;

	private int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("repositoryManager"+ myInstanceID +"#init\n");		
	}

	private EntityManager em;

    @Inject
    void setEm(EntityManager em) {
		MikkosLogger.log("repositoryManager"+ myInstanceID +".setEm\n");		
    	this.em = em;
    }

    public List<SimpleProperty> queryCache() {
		MikkosLogger.log("repositoryManager"+ myInstanceID +".queryCache\n");		
        Query query = em.createQuery("FROM eap63class.domain.SimpleProperty");

        @SuppressWarnings("unchecked")
        List <SimpleProperty> list = query.getResultList();
        return list;
    }

    @PostConstruct
    public void initComplete() {
		MikkosLogger.log("repositoryManager"+ myInstanceID +" INIT COMPLETE!\n");
    }
}
