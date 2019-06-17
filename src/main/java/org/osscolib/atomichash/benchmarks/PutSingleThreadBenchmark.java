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
public abstract class PutSingleThreadBenchmark {

    protected final BenchmarkValues benchmarkValues = new BenchmarkValues();
    protected final BenchmarkMaps benchmarkMaps;



    protected PutSingleThreadBenchmark(final Supplier<Map<String,String>> mapSupplier) {
        super();
        this.benchmarkMaps = new BenchmarkMaps(
                100000, BenchmarkConstants.SINGLE_THREAD_TEST_NUM_THREADS, 0, benchmarkValues, mapSupplier);
    }




    @Setup(value = Level.Iteration)
    public void setup() throws Exception {
        this.benchmarkValues.reset();
        this.benchmarkMaps.reset();
    }


    private static abstract class AtomicPutBenchmark extends PutSingleThreadBenchmark {

        protected AtomicPutBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public Map<String,String> atomicPut() throws Exception {
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            KeyValue<String, String> kv;
            for (int n = 0; n < BenchmarkConstants.SINGLE_THREAD_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) { // Total inserted entries will be this * num_threads
                kv = this.benchmarkValues.produceKeyValue();
                m.put(kv.key, kv.value);
            }
            return m;
        }

    }


    private static abstract class SynchronizedPutBenchmark extends PutSingleThreadBenchmark {

        protected SynchronizedPutBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public Map<String,String> synchronizedPut() throws Exception {
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            KeyValue<String, String> kv;
            for (int n = 0; n < BenchmarkConstants.SINGLE_THREAD_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) {
                kv = this.benchmarkValues.produceKeyValue();
                synchronized (m) {
                    m.put(kv.key, kv.value);
                }
            }
            return m;
        }

    }




    public static class AtomicHashMapPutBenchmark extends AtomicPutBenchmark {
        public AtomicHashMapPutBenchmark() {
            super(() -> new AtomicHashMap<>());
        }
    }


    public static class ConcurrentHashMapPutBenchmark extends AtomicPutBenchmark {
        public ConcurrentHashMapPutBenchmark() {
            super(() -> new ConcurrentHashMap<>());
        }
    }


    public static class HashMapPutBenchmark extends SynchronizedPutBenchmark {
        public HashMapPutBenchmark() {
            super(() -> new HashMap<>());
        }
    }


    public static class LinkedHashMapPutBenchmark extends SynchronizedPutBenchmark {
        public LinkedHashMapPutBenchmark() {
            super(() -> new LinkedHashMap<>());
        }
    }


}
