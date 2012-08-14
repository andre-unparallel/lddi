
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
    @ASN1BoxedType ( name = "SegmEntryElemList" )
    public class SegmEntryElemList implements IASN1PreparedElement {
                
            
            @ASN1SequenceOf( name = "SegmEntryElemList" , isSetOf = false)
	    private java.util.Collection<SegmEntryElem> value = null; 
    
            public SegmEntryElemList () {
            }
        
            public SegmEntryElemList ( java.util.Collection<SegmEntryElem> value ) {
                setValue(value);
            }
                        
            public void setValue(java.util.Collection<SegmEntryElem> value) {
                this.value = value;
            }
            
            public java.util.Collection<SegmEntryElem> getValue() {
                return this.value;
            }            
            
            public void initValue() {
                setValue(new java.util.LinkedList<SegmEntryElem>()); 
            }
            
            public void add(SegmEntryElem item) {
                value.add(item);
            }

	    public void initWithDefaults() {
	    }

        private static IASN1PreparedElementData preparedData = CoderFactory.getInstance().newPreparedElementData(SegmEntryElemList.class);
        public IASN1PreparedElementData getPreparedData() {
            return preparedData;
        }


    }
            