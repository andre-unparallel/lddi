
package x73.p20601;
//
// This file was generated by the BinaryNotes compiler.
// See http://bnotes.sourceforge.net 
// Any modifications to this file will be lost upon recompilation of the source ASN.1. 
//

import org.bn.*;
import org.bn.annotations.*;
import org.bn.annotations.constraints.*;
import org.bn.coders.*;
import org.bn.types.*;




    @ASN1PreparedElement
    @ASN1Sequence ( name = "SetTimeInvoke", isSet = false )
    public class SetTimeInvoke implements IASN1PreparedElement {
            
        @ASN1Element ( name = "date-time", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private AbsoluteTime date_time = null;
                
  
        @ASN1Element ( name = "accuracy", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private FLOAT_Type accuracy = null;
                
  
        
        public AbsoluteTime getDate_time () {
            return this.date_time;
        }

        

        public void setDate_time (AbsoluteTime value) {
            this.date_time = value;
        }
        
  
        
        public FLOAT_Type getAccuracy () {
            return this.accuracy;
        }

        

        public void setAccuracy (FLOAT_Type value) {
            this.accuracy = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(SetTimeInvoke.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            