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

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

public class FixedPositionArrayAccessBenchmark extends BaseBenchmark04 {



    public FixedPositionArrayAccessBenchmark() {
        super();
    }

    public FixedPositionArrayAccessBenchmark(final int accessAttempts) {
        super(accessAttempts);
    }



    @Setup
    public void setup() throws Exception {

    }


    @Benchmark
    public String[] benchmark() throws Exception {

        final Node rootNode = getRootNode();
        final IndexedAttempt[] indexedAttempts = getIndexedAttempts();
        final String[] results = new String[indexedAttempts.length];
        for (int i = 0; i < indexedAttempts.length; i++) {
            results[i] =
                    rootNode
                            .children[indexedAttempts[i].i]
                            .children[indexedAttempts[i].j]
                            .children[indexedAttempts[i].k]
                            .children[indexedAttempts[i].l]
                            .children[indexedAttempts[i].m]
                            .children[indexedAttempts[i].n]
                            .value;
        }
        return results;

    }

}
