/*
 * Copyright (c) 2002-2012 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jgoodies.binding.binder;



/**
 * Adds binding capabilities for Action names to its super interface
 * that can already bind Action instances.<p>
 *
 * The JGoodies Binding library doesn't ship an implementation for this
 * interface, because there's no popular and public standard that describes
 * <em>and</em> implements how to look up an Action for a name.
 * A popular approach is the JSR 296 (Swing Application Framework)
 * that enables developers to annotate methods with <code>&#x40;Action</code>
 * that then can be requested for a target object and an action name.<p>
 *
 * Since the JGoodies implementation of the JSR 296 (the JGoodies
 * "Application" library) is not available to the general public,
 * the JGoodies Application-based implementation of this interface
 * is not part of the Binding library.<p>
 *
 * However, this interface reduce the effort to integrate your custom
 * Action-lookup and binder mechanism that may be based on the JSR 296 or
 * any other Action name registry.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.1 $
 */
public interface ActionObjectBinder extends ObjectBinder {


    /**
     * Looks up an Action for this binder and the given action name.
     * Then creates and returns a binding builder that manages an Action that
     * can be operated on and that can be bound to a button or text field.<p>
     *
     * <strong>Examples:</strong><br>
     * <tt>binder.<b>bindAction</b>("edit")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;.to(editButton);</tt><br>
     * <tt>binder.<b>bindAction</b>(ACTION_EDIT).to(editButton);</tt><p>
     *
     * Implementations will typically look up an Action for a given action name
     * and hand it over to {@link ObjectBinder#bind(javax.swing.Action)}.
     *
     * @param actionName     the name of the Action to be bound
     *
     * @return the binding builder that holds the Action
     *
     * @throws NullPointerException  if {@code actionName} if {@code null}
     */
    ActionBindingBuilder bindAction(String actionName);


}

