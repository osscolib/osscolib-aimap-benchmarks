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
@Threads(BenchmarkConstants.CONCURRENT4_TEST_NUM_THREADS)
public abstract class GetConcurrent4Benchmark {

    protected final BenchmarkValues benchmarkValues = new BenchmarkValues();
    protected final BenchmarkMaps benchmarkMaps;



    protected GetConcurrent4Benchmark(final Supplier<Map<String,String>> mapSupplier) {
        super();
        this.benchmarkMaps = new BenchmarkMaps(
                100000, BenchmarkConstants.CONCURRENT4_TEST_NUM_THREADS, 1000, benchmarkValues, mapSupplier);
    }




    @Setup(value = Level.Iteration)
    public void setup() throws Exception {
        System.out.println("Setting up " + Thread.currentThread().getName());
        this.benchmarkValues.reset();
        this.benchmarkMaps.reset();
    }


    private static abstract class AtomicGetBenchmark extends GetConcurrent4Benchmark {

        protected AtomicGetBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public String[] atomicGet() throws Exception {
            final String[] results = new String[BenchmarkConstants.CONCURRENT4_TEST_NUM_EXECUTIONS_IN_BENCHMARK];
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            String key;
            for (int n = 0; n < BenchmarkConstants.CONCURRENT4_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) { // Total inserted entries will be this * num_threads
                key = this.benchmarkMaps.produceKey();
                results[n] = m.get(key);
            }
            return results;
        }

    }


    private static abstract class SynchronizedGetBenchmark extends GetConcurrent4Benchmark {

        protected SynchronizedGetBenchmark(final Supplier<Map<String, String>> mapSupplier) {
            super(mapSupplier);
        }

        @Benchmark
        public String[] synchronizedGet() throws Exception {
            final String[] results = new String[BenchmarkConstants.CONCURRENT4_TEST_NUM_EXECUTIONS_IN_BENCHMARK];
            final Map<String,String> m = this.benchmarkMaps.produceMap();
            String key;
            for (int n = 0; n < BenchmarkConstants.CONCURRENT4_TEST_NUM_EXECUTIONS_IN_BENCHMARK; n++) {
                key = this.benchmarkMaps.produceKey();
                synchronized (m) {
                    results[n] = m.get(key);
                }
            }
            return results;
        }

    }




    public static class AtomicHashMapGetBenchmark extends AtomicGetBenchmark {
        public AtomicHashMapGetBenchmark() {
            super(() -> new AtomicHashMap<>());
        }
    }


    public static class ConcurrentHashMapGetBenchmark extends AtomicGetBenchmark {
        public ConcurrentHashMapGetBenchmark() {
            super(() -> new ConcurrentHashMap<>());
        }
    }


    public static class HashMapGetBenchmark extends SynchronizedGetBenchmark {
        public HashMapGetBenchmark() {
            super(() -> new HashMap<>());
        }
    }


    public static class LinkedHashMapGetBenchmark extends SynchronizedGetBenchmark {
        public LinkedHashMapGetBenchmark() {
            super(() -> new LinkedHashMap<>());
        }
    }


}
