package be.thalarion.ether.network;

import be.thalarion.ether.gui.ApplicationController;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceListener;

/**
 *
 * @author florian
 */
public class MDNSListener implements ServiceListener {

    @Override
    public void serviceAdded(ServiceEvent se) {
        ApplicationController.getInstance().addHost(
                new Host(
                        se.getInfo().getName(),
                        se.getInfo().getInetAddresses(),
                        se.getInfo().getPort()
                )
        );
    }

    @Override
    public void serviceRemoved(ServiceEvent se) {
        ApplicationController.getInstance().removeHost(
                new Host(
                        se.getInfo().getName(),
                        se.getInfo().getInetAddresses(),
                        se.getInfo().getPort()
                )
        );
    }

    @Override
    public void serviceResolved(ServiceEvent se) {
        System.out.println("Service resolved: " + se);
    }
    
}
