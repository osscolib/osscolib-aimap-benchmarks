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
package org.osscolib.indexmap.benchmarks.benchmark02;

import org.junit.Assert;
import org.junit.Test;
import org.osscolib.indexmap.benchmarks.testutil.KeyValue;

public class ExpectedOutputTest {


    private static String[] buildExpectedOutput(final KeyValue<String,String>[] entries, final int[] accessOrder) {
        final String[] result = new String[accessOrder.length];
        for (int i = 0; i < accessOrder.length; i++) {
            result[i] = entries[accessOrder[i]].getValue();
        }
        return result;
    }


    @Test
    public void testIndexSmallMap() throws Exception {
        final IndexMapSmallBenchmark bench = new IndexMapSmallBenchmark();
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getEntries(), bench.getAccessOrder());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }


    @Test
    public void testIndexMediumMap() throws Exception {
        final IndexMapMediumBenchmark bench = new IndexMapMediumBenchmark();
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getEntries(), bench.getAccessOrder());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }


    @Test
    public void testIndexLargeMap() throws Exception {
        final IndexMapLargeBenchmark bench = new IndexMapLargeBenchmark();
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getEntries(), bench.getAccessOrder());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }


    @Test
    public void testConcurrentHashMap() throws Exception {
        final ConcurrentHashMapBenchmark bench = new ConcurrentHashMapBenchmark();
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getEntries(), bench.getAccessOrder());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }


    @Test
    public void testHashMap() throws Exception {
        final HashMapBenchmark bench = new HashMapBenchmark();
        bench.setup();
        final String[] expectedOutput = buildExpectedOutput(bench.getEntries(), bench.getAccessOrder());
        Assert.assertArrayEquals(expectedOutput, bench.benchmark());
    }





}
