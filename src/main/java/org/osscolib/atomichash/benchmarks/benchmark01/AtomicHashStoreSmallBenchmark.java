/*
 * =============================================================================
 *
 *   Copyright (c) 2019, The OSSCOLIB team (http://www.osscolib.org)
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
package org.osscolib.atomichash.benchmarks.benchmark01;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.osscolib.atomichash.AtomicHashStore;
import org.osscolib.atomichash.AtomicHash;

public class AtomicHashStoreSmallBenchmark extends BaseBenchmark01 {

    private AtomicHashStore<String,String> store;


    @Setup
    public void setup() throws Exception {
        this.store = AtomicHash.<String,String>build().withSmallSize().asFluentMap();
    }


    @Benchmark
    public void benchmark() throws Exception {
        putAll(this.store);
    }

}
