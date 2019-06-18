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
package org.osscolib.atomichash.benchmarks;


import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.osscolib.atomichash.AtomicHashMap;
import org.osscolib.atomichash.benchmarks.utils.BenchmarkConstants;
import org.osscolib.atomichash.benchmarks.utils.BenchmarkMaps;
import org.osscolib.atomichash.benchmarks.utils.BenchmarkValues;
import org.osscolib.atomichash.benchmarks.utils.KeyValue;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Threads(BenchmarkConstants.THREADS4_TEST_NUM_THREADS)
public abstract class Threads4PutBenchmark {

    protected final BenchmarkValues benchmarkValues = new BenchmarkValues();
    protected final BenchmarkMaps benchmarkMaps;



    protected Threads4PutBenchmark(final Supplier<Map<String,String>> mapSupplier) {
        super();
        this.benchmarkMaps = BenchmarkMaps.createPool(
                100000, BenchmarkConstants.THREADS4_TEST_NUM_THREADS, 0, benchmarkValues, mapSupplier);
    }




    @Setup(value = Level.Iteration)
    public void setup() throws Exception {
        this.benchmarkValues.reset();
        this.benchmarkMaps.reset();
    }


    private static abstract class AtomicBenchmark extends Threads4PutBenchmark {

        protected AtomicBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public Map<String,String> atomic() throws Exception {
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            KeyValue<String, String> kv;
            for (int n = 0; n < BenchmarkConstants.THREADS4_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) { // Total inserted entries will be this * num_threads
                kv = this.benchmarkValues.produceKeyValue();
                m.put(kv.key, kv.value);
            }
            return m;
        }

    }


    private static abstract class SynchronizedBenchmark extends Threads4PutBenchmark {

        protected SynchronizedBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public Map<String,String> synch() throws Exception {
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            KeyValue<String, String> kv;
            for (int n = 0; n < BenchmarkConstants.THREADS4_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) {
                kv = this.benchmarkValues.produceKeyValue();
                synchronized (m) {
                    m.put(kv.key, kv.value);
                }
            }
            return m;
        }

    }




    public static class AtomicHashMapBenchmark extends AtomicBenchmark {
        public AtomicHashMapBenchmark() {
            super(() -> new AtomicHashMap<>());
        }
    }


    public static class ConcurrentHashMapBenchmark extends AtomicBenchmark {
        public ConcurrentHashMapBenchmark() {
            super(() -> new ConcurrentHashMap<>());
        }
    }


    public static class SynchronizedHashMapBenchmark extends AtomicBenchmark {
        public SynchronizedHashMapBenchmark() {
            super(() -> Collections.synchronizedMap(new HashMap<>()));
        }
    }


    public static class HashMapBenchmark extends SynchronizedBenchmark {
        public HashMapBenchmark() {
            super(() -> new HashMap<>());
        }
    }


    public static class LinkedHashMapBenchmark extends SynchronizedBenchmark {
        public LinkedHashMapBenchmark() {
            super(() -> new LinkedHashMap<>());
        }
    }


}
