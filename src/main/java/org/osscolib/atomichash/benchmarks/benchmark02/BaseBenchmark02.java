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
package org.osscolib.atomichash.benchmarks.benchmark02;


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
import org.osscolib.atomichash.benchmarks.testutil.KeyValue;
import org.osscolib.atomichash.benchmarks.testutil.TestUtils;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark02 {

    public static final int NUM_ENTRIES = 10000;
    public static final int NUM_ACCESES = 100000;

    private final KeyValue<String,String>[] entries;
    private final int[] accessOrder;


    protected BaseBenchmark02() {
        super();
        this.entries = TestUtils.generateStringStringKeyValues(NUM_ENTRIES);
        this.accessOrder = TestUtils.generateInts(NUM_ACCESES, 0, NUM_ENTRIES);
    }




    public KeyValue<String,String>[] getEntries() {
        return this.entries;
    }

    public int[] getAccessOrder() {
        return this.accessOrder;
    }



    public AtomicHashStore<String,String> putAll(final AtomicHashStore<String,String> store) {

        AtomicHashStore<String,String> st = store;

        for (int i = 0; i < this.entries.length; i++) {
            st = st.put(this.entries[i].getKey(), this.entries[i].getValue());
        }

        return st;

    }


    public Map<String,String> putAll(final Map<String,String> map) {

        for (int i = 0; i < entries.length; i++) {
            map.put(this.entries[i].getKey(), this.entries[i].getValue());
        }

        return map;
    }



    public String[] getAll(final AtomicHashStore<String,String> store) {

        AtomicHashStore<String,String> st = store;
        final String[] result = new String[this.accessOrder.length];

        for (int i = 0; i < this.accessOrder.length; i++) {
            result[i] = st.get(this.entries[this.accessOrder[i]].getKey());
        }

        return result;

    }



    public String[] getAll(final Map<String,String> map) {

        final String[] result = new String[this.accessOrder.length];

        for (int i = 0; i < this.accessOrder.length; i++) {
            result[i] = map.get(this.entries[this.accessOrder[i]].getKey());
        }

        return result;

    }

}
