/*
 * Copyright 2017 BNY Mellon.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bnymellon.codekatas.lambdakata.jdk;

import java.util.Arrays;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.Assert;
import org.junit.Test;

public class PrimitiveFunctionalInterfaceTest
{
    @Test
    public void IntConsumer()
    {
        var adder = new LongAdder();

        IntConsumer consumer = value -> adder.add((long) value);

        IntStream.rangeClosed(1, 5).forEach(consumer);
        Assert.assertEquals(15, adder.longValue());
    }

    @Test
    public void LongConsumer()
    {
        var adder = new LongAdder();

        LongConsumer consumer = adder::add;

        LongStream.rangeClosed(1, 5).forEach(consumer);
        Assert.assertEquals(15, adder.longValue());
    }

    @Test
    public void DoubleConsumer()
    {
        var adder = new DoubleAdder();

        DoubleConsumer consumer = adder::add;
        DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).forEach(consumer);
        Assert.assertEquals(15.0, adder.doubleValue(), 0.0);
    }

    @Test
    public void IntPredicate()
    {
        IntPredicate predicate = value -> value % 2 == 0;

        var evens = IntStream.rangeClosed(1, 5).filter(predicate).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(2, 4), evens);
        var odds = IntStream.rangeClosed(1, 5).filter(predicate.negate()).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1, 3, 5), odds);
        Assert.assertTrue(IntStream.rangeClosed(1, 5).anyMatch(predicate));
        Assert.assertFalse(IntStream.rangeClosed(1, 5).allMatch(predicate));
        Assert.assertFalse(IntStream.rangeClosed(1, 5).noneMatch(predicate));
    }

    @Test
    public void LongPredicate()
    {
        // TODO - Convert the anonymous inner class to a lambda
        var predicate = new LongPredicate()
        {
            @Override
            public boolean test(long value)
            {
                return value % 2 == 0;
            }
        };
        var evens = LongStream.rangeClosed(1, 5).filter(predicate).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(2L, 4L), evens);
        var odds = LongStream.rangeClosed(1, 5).filter(predicate.negate()).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1L, 3L, 5L), odds);
        Assert.assertTrue(LongStream.rangeClosed(1, 5).anyMatch(predicate));
        Assert.assertFalse(LongStream.rangeClosed(1, 5).allMatch(predicate));
        Assert.assertFalse(LongStream.rangeClosed(1, 5).noneMatch(predicate));
    }

    @Test
    public void DoublePredicate()
    {
        // TODO - Convert the anonymous inner class to a lambda
        var predicate = new DoublePredicate()
        {
            @Override
            public boolean test(double value)
            {
                return value > 3.0;
            }
        };
        var greaterThan =
                DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).filter(predicate).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(4.0d, 5.0d), greaterThan);
        var lessThanEqualTo =
                DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).filter(predicate.negate()).boxed().collect(Collectors.toList());
        Assert.assertEquals(Arrays.asList(1.0d, 2.0d, 3.0d), lessThanEqualTo);
        Assert.assertTrue(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).anyMatch(predicate));
        Assert.assertFalse(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).allMatch(predicate));
        Assert.assertFalse(DoubleStream.of(1.0, 2.0, 3.0, 4.0, 5.0).noneMatch(predicate));
    }
}
