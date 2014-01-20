/*
 Copyright 2006-2011 Abdulla Abdurakhmanov (abdulla@latestbit.com)
 Original sources are available at www.latestbit.com

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
package org.universAAL.lddi.lib.ieeex73std.org.bn.annotations;

import java.lang.annotation.*;

import org.universAAL.lddi.lib.ieeex73std.org.bn.coders.TagClass;

@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD })
public @interface ASN1Element {
    String name() default "";
    boolean isOptional() default true;
    boolean hasTag() default false;    
    boolean isImplicitTag() default true;
    int tagClass() default TagClass.ContextSpecific;
    int tag() default 0;
    boolean hasDefaultValue() default false;
    /*
     * Used to get fields in declaration order. Not all java virtual machines
     * returns fields in order in getDeclaredFields method.
     * Next annotation is desirable to be automatically set by BNCompiler
     */
    boolean hasExplicitOrder () default false;
    int declarationOrder() default -1;
}