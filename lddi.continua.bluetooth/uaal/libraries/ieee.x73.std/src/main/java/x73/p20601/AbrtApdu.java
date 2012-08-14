
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
    @ASN1Sequence ( name = "AbrtApdu", isSet = false )
    public class AbrtApdu implements IASN1PreparedElement {
            
        @ASN1Element ( name = "reason", isOptional =  false , hasTag =  false  , hasDefaultValue =  false  )
    
	private Abort_reason reason = null;
                
  
        
        public Abort_reason getReason () {
            return this.reason;
        }

        

        public void setReason (Abort_reason value) {
            this.reason = value;
        }
        
  
                    
        
        public void initWithDefaults() {
            
        }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(AbrtApdu.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }

            
    }
            