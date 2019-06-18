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
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.osscolib.atomichash.AtomicHashMap;
import org.osscolib.atomichash.benchmarks.utils.BenchmarkMaps;
import org.osscolib.atomichash.benchmarks.utils.BenchmarkValues;

@Fork(2)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public abstract class GetBenchmark {


    @State(Scope.Benchmark)
    public static abstract class BaseState {

        final Supplier<Map<String,String>> mapSupplier;

        @Param({"10", "100", "1000"}) int mapInitialSize;
        BenchmarkMaps benchmarkMaps;

        protected BaseState(final Supplier<Map<String,String>> mapSupplier) {
            super();
            this.mapSupplier = mapSupplier;
        }

        @Setup(value = Level.Trial)
        public void initMaps() throws Exception {
            // We need to delay this until Trial (i.e. not do it in the constructor) because attributes annotated
            // with @Param are not guaranteed to have a value at State object constructor execution time.
            this.benchmarkMaps = BenchmarkMaps.createSingleMap(this.mapInitialSize, new BenchmarkValues(), this.mapSupplier);
        }

        @Setup(value = Level.Iteration)
        public void resetMaps() throws Exception {
            this.benchmarkMaps.reset();
        }

        // NOTE we are NOT using a @Setup method for providing a Map and/or a key to each operation (benchmark method
        // execution) because that would make JMH produce lots of timing requests to the system in order to subtract
        // the execution times of the setup methods from the total. This could cause wrong results as explained in
        // the Level.Invocation javadocs.

    }

    public static class AtomicHashMapState extends BaseState {
        public AtomicHashMapState() {
            super(AtomicHashMap::new);
        }
    }

    public static class ConcurrentHashMapState extends BaseState {
        public ConcurrentHashMapState() {
            super(ConcurrentHashMap::new);
        }
    }

    public static class HashMapState extends BaseState {
        public HashMapState() {
            super(HashMap::new);
        }
    }

    public static class SynchronizedHashMapState extends BaseState {
        public SynchronizedHashMapState() {
            super(() -> Collections.synchronizedMap(new HashMap<>()));
        }
    }

    public static class LinkedHashMapState extends BaseState {
        public LinkedHashMapState() {
            super(LinkedHashMap::new);
        }
    }



    @Benchmark
    public String getAtomicHashMap(final AtomicHashMapState state) {
        final Map<String,String> map = state.benchmarkMaps.produceMap();
        final String key = state.benchmarkMaps.produceKey();
        return map.get(key);
    }


    @Benchmark
    public String getConcurrentHashMap(final ConcurrentHashMapState state) {
        final Map<String,String> map = state.benchmarkMaps.produceMap();
        final String key = state.benchmarkMaps.produceKey();
        return map.get(key);
    }


    @Benchmark
    public String getHashMap(final HashMapState state) {
        final Map<String,String> map = state.benchmarkMaps.produceMap();
        final String key = state.benchmarkMaps.produceKey();
        synchronized (map) {
            return map.get(key);
        }
    }


    @Benchmark
    public String getSynchronizedHashMap(final SynchronizedHashMapState state) {
        final Map<String,String> map = state.benchmarkMaps.produceMap();
        final String key = state.benchmarkMaps.produceKey();
        return map.get(key);
    }


    @Benchmark
    public String getLinkedHashMap(final LinkedHashMapState state) {
        final Map<String,String> map = state.benchmarkMaps.produceMap();
        final String key = state.benchmarkMaps.produceKey();
        synchronized (map) {
            return map.get(key);
        }
    }


    @Benchmark
    public void control(final Blackhole blackhole, final HashMapState state) {
        // This control will allow us to subtract the time taken by the execution of the parts of the
        // benchmarks that were not what we were actually measuring, and could not be moved to a @Setup
        // method in the @State class because invocation-level setup methods are discouraged.
        // NOTE we are using a HashMapState for this purpose but we could be using any other one as
        // the code being executed here is exactly the same for all State classes.
        blackhole.consume(state.benchmarkMaps.produceMap());
        blackhole.consume(state.benchmarkMaps.produceKey());
    }





    @Threads(1)
    public static class Threads1GetBenchmark extends GetBenchmark { }
    @Threads(2)
    public static class Threads2GetBenchmark extends GetBenchmark { }
    @Threads(4)
    public static class Threads4GetBenchmark extends GetBenchmark { }



}
