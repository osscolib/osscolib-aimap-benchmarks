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
package org.osscolib.indexmap.benchmarks.benchmark00;


import org.osscolib.indexmap.FluentIndexMap;
import org.osscolib.indexmap.IndexMap;
import org.osscolib.indexmap.benchmarks.BenchmarkUtils;
import org.osscolib.indexmap.benchmarks.KeyValue;

public class BaseBenchmark00 {

    public static final int NUM_ENTRIES = 100000;

    private final int numEntries;
    private final KeyValue<String,String>[] entries;
    private final int[] accessOrder;


    protected BaseBenchmark00() {
        super();
        this.numEntries = NUM_ENTRIES;
        this.entries = BenchmarkUtils.generateEntries(this.numEntries);
        this.accessOrder = BenchmarkUtils.generateAccessOrder(this.numEntries);
    }




    public FluentIndexMap<String,String> putAll(final FluentIndexMap<String,String> map) {

        FluentIndexMap<String,String> m = map;

        for (int i = 0; i < this.entries.length; i++) {
            m = m.put(this.entries[i].getKey(), this.entries[i].getValue());
        }

        return m;

    }



    public String[] getAll(final FluentIndexMap<String,String> map) {

        FluentIndexMap<String,String> m = map;
        final String[] result = new String[this.accessOrder.length];

        for (int i = 0; i < this.accessOrder.length; i++) {
            result[i] = m.get(this.entries[this.accessOrder[i]].getKey());
        }

        return result;

    }




    public void test() throws Exception{

        FluentIndexMap<String,String> map = IndexMap.<String,String>build().withMediumSize().asFluentMap();
        map = putAll(map);

        System.out.println("All initialised. Now pausing.");

        Thread.sleep(10000L);

        System.out.println("Read operations starting.");

        for (int i = 0; i < 10000; i++) {
            getAll(map);
        }

    }



    public static void main(String[] args) throws Exception{

        (new BaseBenchmark00()).test();

    }


}
