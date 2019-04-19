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
package org.osscolib.aimap.benchmarks.benchmark02;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.osscolib.aimap.AtomicIndexedMap;
import org.osscolib.aimap.benchmarks.BenchmarkUtils;
import org.osscolib.aimap.benchmarks.KeyValue;

public class AimapDefaultBenchmark extends BaseBenchmark02 {

    private AtomicIndexedMap<String,String> map;
    private KeyValue<String,String>[] entries;
    private int[] accessOrder;


    @Setup
    public void setup() throws Exception {
        this.map = AtomicIndexedMap.build();
        this.entries = BenchmarkUtils.generateEntries(30);
        this.accessOrder = BenchmarkUtils.generateAccessOrder(30);
        putAll(this.map, this.entries);
    }


    @Benchmark
    public void benchmark() throws Exception {
        getAll(this.map, this.entries, this.accessOrder);
    }

}
