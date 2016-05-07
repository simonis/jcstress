/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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
package org.openjdk.jcstress.tests.tearing.arrays.small.plain;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.*;

// -- This file was mechanically generated: Do not edit! -- //

/**
 * Tests if fields writes are not word-teared.
 */
@JCStressTest
@Outcome(id = "[1.39067116124321E-309, 1.39067116124321E-309]", expect = Expect.ACCEPTABLE, desc = "Seeing the set value.")
@Outcome(id = "[0.0, 0.0]", expect = Expect.FORBIDDEN, desc = "Cannot see default values.")
@Outcome(id = "[1.39067116124321E-309, 0.0]", expect = Expect.FORBIDDEN, desc = "Cannot see default values.")
@Outcome(id = "[0.0, 1.39067116124321E-309]", expect = Expect.FORBIDDEN, desc = "Cannot see default values.")
@Outcome(expect = Expect.ACCEPTABLE_SPEC, desc = "Non-atomic access detected, allowed by spec")
@Ref("http://docs.oracle.com/javase/specs/jls/se7/html/jls-17.html#jls-17.7")
@State
public class DoubleTest {

    double[] arr = new double[2];

    @Actor
    public void actor1() {
        arr[0] = 1.39067116124321E-309;
    }

    @Actor
    public void actor2() {
        arr[1] = 1.39067116124321E-309;
    }

    @Arbiter
    public void arbiter(DoubleResult2 r) {
        r.r1 = arr[0];
        r.r2 = arr[1];
    }

}
