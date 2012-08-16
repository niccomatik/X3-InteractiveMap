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

package com.jgoodies.binding.tests.event;

import java.util.LinkedList;
import java.util.List;

/**
 * The abstract superclass of the different change report implementations.
 *
 * @author  Karsten Lentzsch
 * @version $Revision: 1.9 $
 *
 * @param <E> the type of the events tracked by the report
 */
abstract class AbstractChangeReport<E> {

    /**
     * Holds a list of all received events.
     */
    protected List<E> events = new LinkedList<E>();

    /**
     * If true, property changes are logged to the System console.
     */
    private boolean logActive;


    protected boolean isLogActive() {
        return logActive;
    }


    public void setLogActive(boolean active) {
        this.logActive = active;
    }

    protected void addEvent(E event) {
        events.add(0, event);
    }

    public int eventCount() {
        return events.size();
    }

    public boolean hasEvents() {
        return !events.isEmpty();
    }

    protected E getLastEvent() {
        return events.isEmpty()
            ? null
            : events.get(0);
    }

}
