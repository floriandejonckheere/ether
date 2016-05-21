package be.thalarion.ether.network;

import be.thalarion.ether.Ether;
import be.thalarion.ether.network.Host.TYPE;
import javafx.application.Platform;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * MDNS Listener
 * 
 * @author florian
 */
public class MDNSListener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent se) {
        System.out.println(String.format(
                "Added %s",
                se.getName()
        ));
        
        ServiceInfo si = se.getInfo();
        
        Platform.runLater(() ->  {
            Host host = Ether.getInstance().getHost(si.getName());
            host.setAddress(si.getInetAddresses());
            host.setPort(si.getPort());
        });
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
            Host host = Ether.getInstance().getHost(si.getName());
            host.setAddress(si.getInetAddresses());
            host.setPort(si.getPort());
            host.setType(TYPE.valueOf(si.getPropertyString("type").toUpperCase()));
            host.setName(si.getPropertyString("name"));
        });
    }
    
}
