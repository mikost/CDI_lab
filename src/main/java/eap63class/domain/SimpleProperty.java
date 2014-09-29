package eap63class.domain;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.Id;

import eap63class.MikkosLogger;
import eap63class.bean.BeanManager;


@Entity
public class SimpleProperty {

	static {
		MikkosLogger.log("Loading class \""+ SimpleProperty.class.getSimpleName() +"\"\n");
	}
	private static int instanceID = 0;

	private transient int myInstanceID;
	{
		synchronized (BeanManager.class) {
			instanceID++;
			myInstanceID = instanceID;
		}
		MikkosLogger.log("simpleProperty"+ myInstanceID +"#init\n");		
	}

    @Id
    private String key;
    private String value;

    public String getKey() {
        MikkosLogger.log("simpleProperty"+ myInstanceID +".getKey ==> \""+ key +"\"\n");		
        return key;
    }
    public void setKey(String key) {
        MikkosLogger.log("simpleProperty"+ myInstanceID +".setKey(\""+ key +"\")\n");		
        this.key = key;
    }
    public String getValue() {
        MikkosLogger.log("simpleProperty"+ myInstanceID +".getValue ==> \""+ value +"\"\n");		
        return value;
    }
    public void setValue(String value) {
        MikkosLogger.log("simpleProperty"+ myInstanceID +".setValue(\""+ value +"\")\n");		
        this.value = value;
    }
    @Override
    public String toString() {
        return "SimpleProperty [key=" + key + ", value=" + value
                + "]";
    }
    @PostConstruct
    public void initComplete() {
		MikkosLogger.log("simpleProperty"+ myInstanceID +" INIT COMPLETE!\n");
    }

}