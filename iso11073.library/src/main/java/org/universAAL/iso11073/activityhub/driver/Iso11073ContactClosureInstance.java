package org.universAAL.iso11073.activityhub.driver;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.device.Constants;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.universAAL.iso11073.activityhub.devicecategory.Iso11073ContactClosureSensor;
import org.universAAL.iso11073.activityhub.devicemodel.ContactClosureSensor;
import org.universAAL.iso11073.activityhub.driver.interfaces.ActivityHubDriver;
import org.universAAL.iso11073.activityhub.driver.interfaces.ActivityHubDriverClient;

/**
 * Working instance of the ActivityHub ContactClosure driver.
 * Tracks on the ContactClosureSensor device service passed in the attach method 
 * in Iso11073ContactClosureDriver class.
 * This instance is passed to the consuming client (e.g. uAAL exporter bundle).
 * When the ContactClosureSensor device service disappears, this driver is removed
 * from the consuming client.
 * 
 * @author Thomas Fuxreiter (foex@gmx.at)
 */
public class Iso11073ContactClosureInstance extends ActivityHubDriver 
	implements Iso11073ContactClosureSensor ,ServiceTrackerCustomizer, Constants {

	private BundleContext context;
	private LogService logger;

	/**
	 * @param c OSGi BundleContext
	 * @param sr Service reference of ISO device
	 * @param client Link to consumer of this driver (e.g. uAAL exporter bundle)
	 */
	public Iso11073ContactClosureInstance(BundleContext c, ServiceReference sr, 
			ActivityHubDriverClient client, LogService log) {
		super(client);

		this.context=c;
		this.logger=log;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.iso11073.activityhub.driver.interfaces.ActivityHubDriver#newMessageFromAbove(java.lang.String, byte[])
	 */
	@Override
	public void newMessageFromAbove(String deviceId, byte[] message) {
		// und wos tua ma jetzt??
		
		this.logger.log(LogService.LOG_INFO, "Incoming message " + message + " from address " + 
				deviceId);
				
	}

	/* (non-Javadoc)
	 * @see org.universAAL.iso11073.activityhub.driver.interfaces.ActivityHubDriver#specificConfiguration()
	 */
	@Override
	protected void specificConfiguration() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.universAAL.iso11073.activityhub.devicecategory.Iso11073ContactClosureSensor#receivePacket(long)
	 */
	public byte[] receivePacket(long timeout) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.universAAL.iso11073.activityhub.devicecategory.Iso11073ContactClosureSensor#sendPacket(byte[])
	 */
	public void sendPacket(byte[] data) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#addingService(org.osgi.framework.ServiceReference)
	 */
	public Object addingService(ServiceReference reference) {
		
		ContactClosureSensor ccs = (ContactClosureSensor) this.context.getService(reference);
		
		// register driver in client driverList
		// MAIN FUNCTION HERE !!!
		this.setDevice( (ContactClosureSensor) ccs);

		//return null; JavaDoc: @return The service object to be tracked for the ServiceReference object or null if the ServiceReference object should not be tracked.
		return ccs;
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#modifiedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	public void modifiedService(ServiceReference reference, Object service) {
		this.logger.log(LogService.LOG_INFO, "Tracked ActivityHub device service was modified. " +
				"Going to update the Iso11073ContactClosureInstance");
		removedService(reference, service);
		addingService(reference);		
	}

	/* (non-Javadoc)
	 * @see org.osgi.util.tracker.ServiceTrackerCustomizer#removedService(org.osgi.framework.ServiceReference, java.lang.Object)
	 */
	public void removedService(ServiceReference reference, Object service) {
		// removed device service
		this.context.ungetService(reference);
		this.removeDriver();		
	}

	
	
}