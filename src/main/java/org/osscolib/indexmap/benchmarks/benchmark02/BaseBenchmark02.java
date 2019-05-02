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
import org.osscolib.indexmap.AtomicHashStore;
import org.osscolib.indexmap.benchmarks.BenchmarkUtils;
import org.osscolib.indexmap.benchmarks.KeyValue;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark02 {

    public static final int NUM_ENTRIES = 10000;

    private final int numEntries;
    private final KeyValue<String,String>[] entries;
    private final int[] accessOrder;


    protected BaseBenchmark02() {
        super();
        this.numEntries = NUM_ENTRIES;
        this.entries = BenchmarkUtils.generateEntries(this.numEntries);
        this.accessOrder = BenchmarkUtils.generateAccessOrder(this.numEntries);
    }




    public final int getNumEntries() {
        return this.numEntries;
    }

    public KeyValue<String,String>[] getEntries() {
        return this.entries;
    }

    public int[] getAccessOrder() {
        return this.accessOrder;
    }



    public AtomicHashStore<String,String> putAll(final AtomicHashStore<String,String> map) {

        AtomicHashStore<String,String> m = map;

        for (int i = 0; i < this.entries.length; i++) {
            m = m.put(this.entries[i].getKey(), this.entries[i].getValue());
        }

        return m;

    }


    public Map<String,String> putAll(final Map<String,String> map) {

        for (int i = 0; i < entries.length; i++) {
            map.put(this.entries[i].getKey(), this.entries[i].getValue());
        }

        return map;
    }



    public String[] getAll(final AtomicHashStore<String,String> map) {

        AtomicHashStore<String,String> m = map;
        final String[] result = new String[this.accessOrder.length];

        for (int i = 0; i < this.accessOrder.length; i++) {
            result[i] = m.get(this.entries[this.accessOrder[i]].getKey());
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
