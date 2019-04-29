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
package org.osscolib.indexmap.benchmarks.benchmark05;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class ShiftBasedPositionArrayAccessBenchmark extends BaseBenchmark05 {



    public ShiftBasedPositionArrayAccessBenchmark() {
        super();
    }

    public ShiftBasedPositionArrayAccessBenchmark(final int accessAttempts) {
        super(accessAttempts);
    }



    @Setup
    public void setup() throws Exception {

    }



    @Benchmark
    public String[] benchmark() throws Exception {

        final int nodeSize = getNodeSize();
        final int nodeLevels = getNodeLevels();
        final Node rootNode = getRootNode();
        final long[] attempts = getAttempts();

        final String[] results = new String[attempts.length];
        for (int i = 0; i < attempts.length; i++) {
            results[i] = get(rootNode, attempts[i], nodeSize, nodeLevels);
        }
        return results;

    }



    private static String get(final Node root, final long index, final int nodeSize, final int nodeLevels) {

        Node node = root;
        int pos;
        int level = nodeLevels -1;
        int bitMask = nodeSize - 1;

        while (node != null && node.value == null) {
            pos = ((int)index >> (level * 8)) & bitMask;
            node = node.children[pos];
            level--;
        }

        return (node != null && node.lowLimit == index) ? node.value : null;

    }





}
