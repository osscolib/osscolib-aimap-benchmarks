/*
 * =============================================================================
 *
 *   Copyright (c) 2019, The VEXPREL team (http://www.vexprel.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package org.osscolib.indexmap.benchmarks.benchmark04;

import org.junit.Assert;
import org.junit.Test;
import org.osscolib.indexmap.benchmarks.benchmark04.BaseBenchmark04.Node;

public class ExpectedOutputTest {

    private final static int EXPECTED_OUTPUT_ACCESS_ATTEMPTS = 100;


    private static String[] buildExpectedOutput(final Node rootNode, final long[] attempts) {
        final String[] result = new String[attempts.length];
        for (int a = 0; a < attempts.length; a++) {
            result[a] = findValue(rootNode, attempts[a]);
        }
        return result;
    }


    private static String findValue(final Node rootNode, final long index) {
        for (int i = 0; i < rootNode.children.length; i++) {
            final Node childi = rootNode.children[i];
            for (int j = 0; j < childi.children.length; j++) {
                final Node childij = childi.children[j];
                for (int k = 0; k < childij.children.length; k++) {
                    final Node childijk = childij.children[k];
                    for (int l = 0; l < childijk.children.length; l++) {
                        final Node childijkl = childijk.children[l];
                        for (int m = 0; m < childijkl.children.length; m++) {
                            final Node childijklm = childijkl.children[m];
                            for (int n = 0; n < childijklm.children.length; n++) {
                                final Node childijklmn = childijklm.children[n];
                                if (childijklmn.highLimit == index) {
                                    return childijklmn.value;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }



    @Test
    public void testFixedPosition() throws Exception {
        final FixedPositionArrayAccessBenchmark bench = new FixedPositionArrayAccessBenchmark(EXPECTED_OUTPUT_ACCESS_ATTEMPTS);
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getRootNode(), bench.getAttempts());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }



    @Test
    public void testComputedPosition() throws Exception {
        final ComputedPositionArrayAccessBenchmark bench = new ComputedPositionArrayAccessBenchmark(EXPECTED_OUTPUT_ACCESS_ATTEMPTS);
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getRootNode(), bench.getAttempts());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }



    @Test
    public void testShiftBasedPosition() throws Exception {
        final ShiftBasedPositionArrayAccessBenchmark bench = new ShiftBasedPositionArrayAccessBenchmark(EXPECTED_OUTPUT_ACCESS_ATTEMPTS);
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getRootNode(), bench.getAttempts());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }


}
