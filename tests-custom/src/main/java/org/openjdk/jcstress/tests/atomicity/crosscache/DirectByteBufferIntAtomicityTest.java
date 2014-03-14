/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jcstress.tests.atomicity.crosscache;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.ConcurrencyStressTest;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.ByteResult4;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Random;

@ConcurrencyStressTest
@State
public class DirectByteBufferIntAtomicityTest {

    /**
     * We don't have the alignment information, so we would try to read/write to the
     * random offset within the byte array.
     */

    /** Array size: 256 bytes inevitably crosses the cache line on most implementations */
    public static final int SIZE = 256;

    public static final Random RANDOM = new Random();
    public static final int COMPONENT_SIZE = 4;

    /** Alignment constraint: 4-bytes is default, for integers */
    public static final int ALIGN = Integer.getInteger("align", COMPONENT_SIZE);

    public final ByteBuffer bytes;
    public final int offset;

    public DirectByteBufferIntAtomicityTest() {
        bytes = ByteBuffer.allocateDirect(SIZE);
        bytes.order(ByteOrder.nativeOrder());
        offset = RANDOM.nextInt((SIZE - COMPONENT_SIZE)/ALIGN)*ALIGN;
    }

    @Actor
    public void actor1() {
        bytes.putInt(offset, 0xFFFFFFFF);
    }

    @Actor
    public void actor2(ByteResult4 r) {
        int t = bytes.getInt(offset);
        r.r1 = (byte) ((t >> 0) & 0xFF);
        r.r2 = (byte) ((t >> 8) & 0xFF);
        r.r3 = (byte) ((t >> 16) & 0xFF);
        r.r4 = (byte) ((t >> 24) & 0xFF);
    }

}
