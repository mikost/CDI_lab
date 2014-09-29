package eap63class.producer;

import java.util.List;

import eap63class.MikkosLogger;
import eap63class.bean.BeanManager;
import eap63class.domain.SimpleProperty;
import eap63class.repository.RepositoryManager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
public class Producer {

	static {
		MikkosLogger.log("Loading class \""+ Producer.class.getSimpleName() +"\"\n");
	}
	private static int instanceID = 0;

	private int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("producer"+ myInstanceID +"#init\n");		
	}

	RepositoryManager db;

	@Inject
	void setDb(RepositoryManager db) {
		MikkosLogger.log("producer"+ myInstanceID +".setDb\n");		
		this.db = db;
	}

    private List<SimpleProperty> propertyList;

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final SimpleProperty member) {
		MikkosLogger.log("producer"+ myInstanceID +".onMemberListChanged()\n");		
        System.out.println("Producer received the event..");
        retrieveAllProperties();
    }

    @Produces
    @Named
    public List<SimpleProperty> getPropertyList() {
		MikkosLogger.log("producer"+ myInstanceID +".getPropertyList()\n");		
        return propertyList;
    }

    public void setProperty(List<SimpleProperty> propertyList) {
		MikkosLogger.log("producer"+ myInstanceID +".setProperty()\n");		
        this.propertyList = propertyList;
    }

    public void retrieveAllProperties() {
		MikkosLogger.log("producer"+ myInstanceID +".retrieveAllProperties()\n");		
        propertyList = db.queryCache();
    }
    @PostConstruct
    public void initComplete() {
    	retrieveAllProperties();
		MikkosLogger.log("producer"+ myInstanceID +" INIT COMPLETE!\n");
    }

}
