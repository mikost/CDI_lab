package eap63class.bean;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import eap63class.MikkosLogger;
import eap63class.domain.SimpleProperty;
import eap63class.ejb.ServiceBean;
import eap63class.producer.Producer;

@RequestScoped
@Named("manager")
public class BeanManager {

	static {
		MikkosLogger.log("BeanManager init\n");
	}
	private static int instanceID = 0;

	private int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("beanManager"+ myInstanceID +"#init\n");		
	}

    private ServiceBean ejb;

    @Inject
    void setEjb(ServiceBean newEjb) {
		MikkosLogger.log("beanManager"+ myInstanceID +".setEjb()\n");		
    	this.ejb = newEjb;
    }

    private SimpleProperty myProperty;

    @Produces
    @Named
    SimpleProperty getProperty() {
		MikkosLogger.log("beanManager"+ myInstanceID +".getProperty()\n");		
    	return myProperty;
    }

    Producer producer;

    @Inject
    void setProducer(Producer newProducer) {
		MikkosLogger.log("beanManager"+ myInstanceID +".setProducer()\n");		
		producer = newProducer;
    }

    @PostConstruct
    public void initNewProperty() {
		MikkosLogger.log("beanManager"+ myInstanceID +".initNewProperty()\n");
        myProperty = new SimpleProperty();
		MikkosLogger.log("beanManager"+ myInstanceID +" INIT COMPLETE!!\n");
		producer.getPropertyList();
		MikkosLogger.log("beanManager"+ myInstanceID +" INIT REALLY COMPLETE!\n");
    }

    public void save() {
		MikkosLogger.log("beanManager"+ myInstanceID +".save property = " + myProperty);
        System.out.println("Calling ServiceBean.put operation.  Property = " + myProperty);
        ejb.put(myProperty);
        initNewProperty();
    }

}
