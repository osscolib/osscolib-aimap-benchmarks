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
import org.osscolib.aimap.AtomicIndexedMap;
import org.osscolib.aimap.benchmarks.KeyValue;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class BaseBenchmark02 {



    public void putAll(final AtomicIndexedMap<String,String> map, final KeyValue<String,String>[] entries) {

        AtomicIndexedMap<String,String> m = map;

        for (int i = 0; i < entries.length; i++) {
            m = m.put(entries[i].getKey(), entries[i].getValue());
        }

    }



    public void putAll(final Map<String,String> map, final KeyValue<String,String>[] entries) {

        for (int i = 0; i < entries.length; i++) {
            map.put(entries[i].getKey(), entries[i].getValue());
        }

    }


    public void getAll(final AtomicIndexedMap<String,String> map, final KeyValue<String,String>[] entries, final int[] accessOrder) {

        AtomicIndexedMap<String,String> m = map;

        for (int i = 0; i < accessOrder.length; i++) {
            m.get(entries[accessOrder[i]].getKey());
        }

    }



    public void getAll(final Map<String,String> map, final KeyValue<String,String>[] entries, final int[] accessOrder) {

        for (int i = 0; i < accessOrder.length; i++) {
            map.get(entries[accessOrder[i]].getKey());
        }

    }

}
