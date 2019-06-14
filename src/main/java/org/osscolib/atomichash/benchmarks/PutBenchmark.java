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
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomUtils;
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
import org.osscolib.atomichash.KeyValue;
import org.osscolib.atomichash.TestUtils;

@Fork(2)
@Warmup(iterations = 2, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@State(Scope.Benchmark)
@Threads(4)
public class PutBenchmark {

    public static final int NUM_ENTRIES = 100000;
    static final KeyValue<String,String>[] ENTRIES_ARRAY;

    Map<String,String> map = null;

    final AtomicInteger i = new AtomicInteger(0);


    static {
        ENTRIES_ARRAY = TestUtils.generateStringStringKeyValues(NUM_ENTRIES,0,0);
    }



    protected PutBenchmark() {
        super();
    }


    int i() {
        return i.getAndIncrement() % NUM_ENTRIES;
    }



    public static class AtomicHashMapPutBenchmark extends PutBenchmark {

        @Setup(value = Level.Iteration)
        public void setup() throws Exception {
            this.map = new AtomicHashMap<>();
            this.i.set(RandomUtils.nextInt(0, NUM_ENTRIES));
        }

        @Benchmark
        public Map<String,String> benchmarkPut() throws Exception {
            for (int n = 0; n < 10000; n++) {
                final KeyValue<String, String> kv = ENTRIES_ARRAY[i()];
                this.map.put(kv.getKey(), kv.getValue());
            }
            this.map.clear();
            return map;
        }

    }


    public static class ConcurrentHashMapPutBenchmark extends PutBenchmark {

        @Setup(value = Level.Iteration)
        public void setup() throws Exception {
            this.map = new ConcurrentHashMap<>();
            this.i.set(RandomUtils.nextInt(0, NUM_ENTRIES));
        }

        @Benchmark
        public Map<String,String> benchmarkPut() throws Exception {
            for (int n = 0; n < 10000; n++) {
                final KeyValue<String, String> kv = ENTRIES_ARRAY[i()];
                this.map.put(kv.getKey(), kv.getValue());
            }
            this.map.clear();
            return map;
        }

    }


    public static class HashMapPutBenchmark extends PutBenchmark {

        @Setup(value = Level.Iteration)
        public void setup() throws Exception {
            this.map = new HashMap<>();
            this.i.set(RandomUtils.nextInt(0, NUM_ENTRIES));
        }

        @Benchmark
        public Map<String,String> benchmarkPut() throws Exception {
            for (int n = 0; n < 10000; n++) {
                final KeyValue<String, String> kv = ENTRIES_ARRAY[i()];
                synchronized (this.map) {
                    this.map.put(kv.getKey(), kv.getValue());
                }
            }
            this.map.clear();
            return map;
        }

    }


    public static class LinkedHashMapPutBenchmark extends PutBenchmark {

        @Setup(value = Level.Iteration)
        public void setup() throws Exception {
            this.map = new LinkedHashMap<>();
            this.i.set(RandomUtils.nextInt(0, NUM_ENTRIES));
        }

        @Benchmark
        public Map<String,String> benchmarkPut() throws Exception {
            for (int n = 0; n < 10000; n++) {
                final KeyValue<String, String> kv = ENTRIES_ARRAY[i()];
                synchronized (this.map) {
                    this.map.put(kv.getKey(), kv.getValue());
                }
            }
            this.map.clear();
            return map;
        }

    }

    

}
