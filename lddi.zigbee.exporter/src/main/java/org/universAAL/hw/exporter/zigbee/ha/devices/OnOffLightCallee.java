/*
 Copyright 2008-2014 ITACA-TSB, http://www.tsb.upv.es
 Instituto Tecnologico de Aplicaciones de Comunicacion 
 Avanzadas - Grupo Tecnologias para la Salud y el 
 Bienestar (TSB)

 See the NOTICE file distributed with this work for additional 
 information regarding copyright ownership

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package org.universAAL.hw.exporter.zigbee.ha.devices;

import it.cnr.isti.zigbee.ha.cluster.glue.general.event.OnOffEvent;
import it.cnr.isti.zigbee.ha.cluster.glue.general.event.OnOffListener;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLight;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import java.util.Properties;

import org.universAAL.hw.exporter.zigbee.ha.Activator;
import org.universAAL.hw.exporter.zigbee.ha.services.OnOffLightService;
import org.universAAL.middleware.container.ModuleContext;
import org.universAAL.middleware.container.utils.LogUtils;
import org.universAAL.middleware.context.ContextEvent;
import org.universAAL.middleware.context.DefaultContextPublisher;
import org.universAAL.middleware.context.owl.ContextProvider;
import org.universAAL.middleware.context.owl.ContextProviderType;
import org.universAAL.middleware.owl.MergedRestriction;
import org.universAAL.middleware.service.CallStatus;
import org.universAAL.middleware.service.ServiceCall;
import org.universAAL.middleware.service.ServiceCallee;
import org.universAAL.middleware.service.ServiceResponse;
import org.universAAL.middleware.service.owls.process.ProcessInput;
import org.universAAL.middleware.service.owls.process.ProcessOutput;
import org.universAAL.middleware.service.owls.profile.ServiceProfile;
import org.universAAL.middleware.util.Constants;
import org.universAAL.ontology.lighting.LightSource;
import org.universAAL.ontology.location.indoor.Room;

/**
 * Exporter class that acts as wrapper towards uAAL. Connects interaction of the
 * device with the uAAL middleware through the service and context buses.
 * 
 * @author alfiva
 * 
 */
public class OnOffLightCallee extends ServiceCallee implements OnOffListener {

    static final String DEVICE_URI_PREFIX = OnOffLightService.LIGHTING_SERVER_NAMESPACE
	    + "zbLamp";
    static final String INPUT_DEVICE_URI = OnOffLightService.LIGHTING_SERVER_NAMESPACE
	    + "lampURI";

    private OnOffLight zbDevice;
    private DefaultContextPublisher cp;
    LightSource ontologyDevice;

    private ServiceProfile[] newProfiles = OnOffLightService.profiles;

    /**
     * Constructor to be used in the exporter, which sets up all the exporting
     * process.
     * 
     * @param context
     *            The OSGi context
     * @param serv
     *            The OSGi service backing the interaction with the device in
     *            the abstraction layer
     */
    public OnOffLightCallee(ModuleContext context, OnOffLight serv) {
	super(context, null);
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"OnOffLightCallee", new String[] { "Ready to subscribe" }, null);
	zbDevice = serv;
	// Commissioning
	String deviceSuffix = zbDevice.getZBDevice().getUniqueIdenfier()
		.replace("\"", "");
	String deviceURI = DEVICE_URI_PREFIX + deviceSuffix;
	ontologyDevice = new LightSource(deviceURI);
	String locationSuffix = Activator.getProperties().getProperty(
		deviceSuffix);
	if (locationSuffix != null
		&& !locationSuffix.equals(Activator.UNINITIALIZED_SUFFIX)) {
	    ontologyDevice
		    .setLocation(new Room(
			    Constants.uAAL_MIDDLEWARE_LOCAL_ID_PREFIX
				    + locationSuffix));
	} else {
	    Properties prop = Activator.getProperties();
	    prop.setProperty(deviceSuffix, Activator.UNINITIALIZED_SUFFIX);
	    Activator.setProperties(prop);
	}
	// Serv reg
	ServiceProfile[] newProfiles = OnOffLightService.profiles;

	ProcessInput input = new ProcessInput(OnOffLightService.INPUT_LAMP);
	input.setParameterType(LightSource.MY_URI);
	input.setCardinality(1, 0);

	MergedRestriction r = MergedRestriction.getFixedValueRestriction(
		OnOffLightService.PROP_CONTROLS, ontologyDevice);

	newProfiles[0].addInput(input);
	newProfiles[0].getTheService().addInstanceLevelRestriction(r,
		new String[] { OnOffLightService.PROP_CONTROLS });
	newProfiles[1].addInput(input);
	newProfiles[1].getTheService().addInstanceLevelRestriction(r,
		new String[] { OnOffLightService.PROP_CONTROLS });
	newProfiles[2].addInput(input);
	newProfiles[2].getTheService().addInstanceLevelRestriction(r,
		new String[] { OnOffLightService.PROP_CONTROLS });
	this.addNewRegParams(newProfiles);

	ContextProvider info = new ContextProvider(
		OnOffLightService.LIGHTING_SERVER_NAMESPACE
			+ "zbLightingContextProvider");
	info.setType(ContextProviderType.controller);
	cp = new DefaultContextPublisher(context, info);

	if (zbDevice.getOnOff().subscribe(this)) {
	    LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		    "OnOffLightCallee", new String[] { "Subscribed" }, null);
	} else {
	    LogUtils.logError(Activator.moduleContext, OnOffLightCallee.class,
		    "OnOffLightCallee",
		    new String[] { "Failed to Subscribe!!!" }, null);
	}
    }

    public void changedOnOff(OnOffEvent event) {
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"changedOnOff", new String[] { "Changed-Event received" }, null);
	LightSource ls = ontologyDevice;
	ls.setBrightness(event.getEvent() ? 100 : 0);
	cp.publish(new ContextEvent(ls, LightSource.PROP_SOURCE_BRIGHTNESS));
    }

    /**
     * Disconnects this exported device from the middleware.
     * 
     */
    public void unregister() {
	this.removeMatchingRegParams(newProfiles);
    }

    public void communicationChannelBroken() {
	unregister();
    }

    public ServiceResponse handleCall(ServiceCall call) {
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"handleCall", new String[] { "Received a call" }, null);
	ServiceResponse response;
	if (call == null) {
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR, "Null Call!"));
	    return response;
	}

	String operation = call.getProcessURI();
	if (operation == null) {
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Null Operation!"));
	    return response;
	}

	if (operation.startsWith(OnOffLightService.SERVICE_GET_ON_OFF)) {
	    return getOnOffStatus();
	} else if (operation.startsWith(OnOffLightService.SERVICE_TURN_ON)) {
	    return setOnStatus();
	} else if (operation.startsWith(OnOffLightService.SERVICE_TURN_OFF)) {
	    return setOffStatus();
	} else {
	    response = new ServiceResponse(CallStatus.serviceSpecificFailure);
	    response.addOutput(new ProcessOutput(
		    ServiceResponse.PROP_SERVICE_SPECIFIC_ERROR,
		    "Invlaid Operation!"));
	    return response;
	}
    }

    private ServiceResponse setOffStatus() {
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"setOffStatus",
		new String[] { "The service called was 'set the status OFF'" },
		null);
	try {
	    zbDevice.getOnOff().off();
	    return new ServiceResponse(CallStatus.succeeded);
	} catch (ZigBeeHAException e) {
	    e.printStackTrace();
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }

    private ServiceResponse setOnStatus() {
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"setOnStatus",
		new String[] { "The service called was 'set the status ON'" },
		null);
	try {
	    zbDevice.getOnOff().on();
	    return new ServiceResponse(CallStatus.succeeded);
	} catch (ZigBeeHAException e) {
	    e.printStackTrace();
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }

    private ServiceResponse getOnOffStatus() {
	LogUtils.logDebug(Activator.moduleContext, OnOffLightCallee.class,
		"getOnOffStatus",
		new String[] { "The service called was 'get the status'" },
		null);
	try {
	    ServiceResponse sr = new ServiceResponse(CallStatus.succeeded);
	    sr.addOutput(new ProcessOutput(
		    OnOffLightService.OUTPUT_LAMP_BRIGHTNESS, new Integer(
			    zbDevice.getOnOff().getOnOff() ? 100 : 0)));
	    return sr;
	} catch (ZigBeeHAException e) {
	    e.printStackTrace();
	    return new ServiceResponse(CallStatus.serviceSpecificFailure);
	}
    }

}