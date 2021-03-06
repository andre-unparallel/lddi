/*
    Copyright 2007-2014 TSB, http://www.tsbtecnologias.es
    Technologies for Health and Well-being - Valencia, Spain

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
package org.universAAL.lddi.lib.ieeex73std.x73.p104zz;

import java.util.LinkedList;

import org.universAAL.lddi.lib.ieeex73std.org.bn.IDecoder;
import org.universAAL.lddi.lib.ieeex73std.x73.p20601.dim.Attribute;

//Strength Fitness Equipment
//The Strength Fitness Equipment does not have any Standard Configuration

public class p10442Strength extends DeviceSpecialization {

	public LinkedList<Attribute> strengthattributes;

	public p10442Strength(IDecoder decoder) throws Exception {
		super(decoder);
		generateStrengtheAttributes();
	}

	private void generateStrengtheAttributes() throws Exception {

	}

	public String toString() {
		return "Strength fitness equipment";
	}

}
