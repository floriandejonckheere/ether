package be.thalarion.ether.network;

import be.thalarion.ether.gui.ApplicationController;
import be.thalarion.ether.network.Host.TYPE;
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
                        se.getInfo().getPort(),
                        TYPE.valueOf(se.getInfo().getPropertyString("type"))
                )
        );
    }

    @Override
    public void serviceRemoved(ServiceEvent se) {
        ApplicationController.getInstance().removeHost(
                new Host(
                        se.getInfo().getName(),
                        se.getInfo().getInetAddresses(),
                        se.getInfo().getPort(),
                        TYPE.valueOf(se.getInfo().getPropertyString("type"))
                )
        );
    }

    @Override
    public void serviceResolved(ServiceEvent se) {
        ApplicationController.getInstance().addHost(
                new Host(
                        se.getInfo().getName(),
                        se.getInfo().getInetAddresses(),
                        se.getInfo().getPort(),
                        TYPE.valueOf(se.getInfo().getPropertyString("type"))
                )
        );
    }
    
}
