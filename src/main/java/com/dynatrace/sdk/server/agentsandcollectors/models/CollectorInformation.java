/*
 * Dynatrace Server SDK
 * Copyright (c) 2008-2016, DYNATRACE LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  Neither the name of the dynaTrace software nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.dynatrace.sdk.server.agentsandcollectors.models;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = CollectorInformation.ROOT_ELEMENT_NAME)
public class CollectorInformation {
    public static final String ROOT_ELEMENT_NAME = "collectorinformation";

    @XmlAttribute
    private String href;

    private String name;
    private String host;
    private String version;

    private Boolean connected;
    private Boolean embedded;
    private Boolean local;

    public CollectorInformation(String href, String name, String host, String version, Boolean connected, Boolean embedded, Boolean local) {
        this.href = href;
        this.name = name;
        this.host = host;
        this.version = version;
        this.connected = connected;
        this.embedded = embedded;
        this.local = local;
    }

    public CollectorInformation() {
    }

    /* getters */
    public String getHref() { return this.href; }

    public Boolean isEmbedded() {
        return this.embedded;
    }

    public String getName() {
        return this.name;
    }

    public String getHost() {
        return this.host;
    }

    public Boolean isConnected() {
        return this.connected;
    }

    public Boolean isLocal() {
        return this.local;
    }

    public String getVersion() {
        return this.version;
    }


    @Override
    public String toString() {
        return "CollectorInformation{" +
                "href='" + this.href + '\'' +
                ", embedded=" + this.embedded +
                ", name='" + this.name + '\'' +
                ", host='" + this.host + '\'' +
                ", connected=" + this.connected +
                ", local=" + this.local +
                ", version='" + this.version + '\'' +
                '}';
    }
}