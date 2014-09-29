package eap63class.ejb;

import eap63class.MikkosLogger;
import eap63class.bean.BeanManager;
import eap63class.domain.SimpleProperty;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class ServiceBean {

	static {
		MikkosLogger.log("Loading class \""+ ServiceBean.class.getSimpleName() +"\"\n");
	}
	private static int instanceID = 0;

	private int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("serviceBean"+ myInstanceID +"#init\n");		
	}

	private Event<SimpleProperty> propEventSrc;

	@Inject
    private void setPropEventSrc(Event<SimpleProperty> e) {
        MikkosLogger.log("serviceBean"+ myInstanceID +".setPropEventSrc()\n");
		propEventSrc = e;
	}

    private EntityManager em;

    @Inject
    private void setEm(EntityManager em) {
        MikkosLogger.log("serviceBean"+ myInstanceID +".setEm()\n");
    	this.em = em;
    }

    public void put(SimpleProperty p) {
        MikkosLogger.log("serviceBean"+ myInstanceID +".put("+p+")\n");
        em.persist(p);
        propEventSrc.fire(p);
    }

    public void delete(SimpleProperty p) {
        MikkosLogger.log("serviceBean"+ myInstanceID +".delete("+p+")\n");
        Query query = em.createQuery("delete From eap63class.domain.SimpleProperty p where p.key='" + p.getKey() + "'" );
        query.executeUpdate();
        propEventSrc.fire(p);
    }

    @PostConstruct
    public void initComplete() {
		MikkosLogger.log("serviceBean"+ myInstanceID +" INIT COMPLETE!\n");
    }

}