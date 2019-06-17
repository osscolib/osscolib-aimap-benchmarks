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


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.osscolib.atomichash.AtomicHashStore;
import org.osscolib.atomichash.benchmarks.utils.KeyValue;
import org.osscolib.atomichash.benchmarks.utils.TestUtils;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark01 {

    public static final int NUM_ENTRIES = 10000;

    private final KeyValue<String,String>[] entriesArray;
    private final Map<String,String> entriesMap;


    protected BaseBenchmark01() {
        super();
        this.entriesArray = TestUtils.generateStringStringKeyValues(NUM_ENTRIES,0,0);
        this.entriesMap = new HashMap<>();
        for (int i = 0; i < this.entriesArray.length; i++) {
            this.entriesMap.put(this.entriesArray[i].getKey(), this.entriesArray[i].getValue());
        }
    }




    public KeyValue<String,String>[] getEntriesArray() {
        return this.entriesArray;
    }

    public Map<String, String> getEntriesMap() {
        return entriesMap;
    }



    public AtomicHashStore<String,String> put(final AtomicHashStore<String,String> store) {
        AtomicHashStore<String,String> st = store;
        for (int i = 0; i < this.entriesArray.length; i++) {
            st = st.put(this.entriesArray[i].getKey(), this.entriesArray[i].getValue());
        }
        return st;
    }


    public Map<String,String> put(final Map<String,String> map) {
        for (int i = 0; i < entriesArray.length; i++) {
            map.put(this.entriesArray[i].getKey(), this.entriesArray[i].getValue());
        }
        return map;
    }



    public AtomicHashStore<String,String> putAll(final AtomicHashStore<String,String> store) {
        AtomicHashStore<String,String> st = store;
        st = st.putAll(this.entriesMap);
        return st;
    }


    public Map<String,String> putAll(final Map<String,String> map) {
        map.putAll(this.entriesMap);
        return map;
    }

}
