package be.thalarion.ether.network.mDNS;

import be.thalarion.ether.Ether;
import be.thalarion.ether.network.mDNS.mDNSHost.TYPE;
import javafx.application.Platform;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * MDNS Listener
 * 
 * @author florian
 */
public class mDNSListener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent se) {
        System.out.println(String.format(
                "Added %s",
                se.getName()
        ));
        
        ServiceInfo si = se.getInfo();
        
        mDNS.jmdns.requestServiceInfo(si.getType(), si.getName());
    }

    @Override
    public void serviceRemoved(ServiceEvent se) {
        System.out.println(String.format(
                "Removed %s",
                se.getName()
        ));
        
        Platform.runLater(() -> {
            Ether.getInstance().removeHost(se.getInfo().getName());
        });
    }

    @Override
    public void serviceResolved(ServiceEvent se) {
        System.out.println(String.format(
                "Resolved %s",
                se.getName()
        ));
        
        ServiceInfo si = se.getInfo();
        
        Platform.runLater(() -> {
            mDNSHost host = Ether.getInstance().getHost(si.getName());
            host.setAddress(si.getInetAddresses());
            host.setPort(si.getPort());
            host.setType(TYPE.valueOf(si.getPropertyString("type").toUpperCase()));
            host.setName(si.getPropertyString("name"));
        });
    }
    
}
