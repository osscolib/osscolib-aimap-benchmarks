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
package org.osscolib.atomichash.benchmarks.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomUtils;
import org.osscolib.atomichash.benchmarks.utils.KeyValue;
import org.osscolib.atomichash.benchmarks.utils.TestUtils;

public final class BenchmarkValues {

    private static final int COMPONENTS_ARRAY_SIZE = 100000;
    private static final int COMPONENTS_STRING_SIZE = 20;

    private final String[] componentsArray;

    private final AtomicInteger i = new AtomicInteger(0);
    private final AtomicInteger j = new AtomicInteger(0);
    private final AtomicInteger k = new AtomicInteger(0);
    private final AtomicInteger l = new AtomicInteger(0);

    private final int inci = RandomUtils.nextInt(0, 7);
    private final int incj = RandomUtils.nextInt(0, 7);
    private final int inck = RandomUtils.nextInt(0, 7);
    private final int incl = RandomUtils.nextInt(0, 7);


    public BenchmarkValues() {
        super();
        this.componentsArray = new String[COMPONENTS_ARRAY_SIZE];
        for (int i = 0; i < this.componentsArray.length; i++) {
            this.componentsArray[i] = TestUtils.generateString(COMPONENTS_STRING_SIZE);
        }
        reset();
    }


    public void reset() {
        this.i.set(RandomUtils.nextInt(0, COMPONENTS_ARRAY_SIZE));
        this.j.set(RandomUtils.nextInt(0, COMPONENTS_ARRAY_SIZE));
        this.k.set(RandomUtils.nextInt(0, COMPONENTS_ARRAY_SIZE));
        this.l.set(RandomUtils.nextInt(0, COMPONENTS_ARRAY_SIZE));
    }



    public KeyValue<String,String> produceKeyValue() {
        final int ival = (this.i.getAndAdd(this.inci) % COMPONENTS_ARRAY_SIZE);
        final int jval = (this.j.getAndAdd(this.incj) % COMPONENTS_ARRAY_SIZE);
        final int kval = (this.k.getAndAdd(this.inck) % COMPONENTS_ARRAY_SIZE);
        final int lval = (this.l.getAndAdd(this.incl) % COMPONENTS_ARRAY_SIZE);
        final String key = this.componentsArray[ival] + this.componentsArray[jval];
        final String value = this.componentsArray[kval] + this.componentsArray[lval];
        return new KeyValue<>(key, value);
    }

}
