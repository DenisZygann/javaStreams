/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package de.zygann;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MILLISECONDS)
@Warmup(iterations = 2)
@Measurement(iterations = 1)
public class MyBenchmark {


    public static final int SIZE = 20;
    public static List<Person> PERSON_LIST;
    public static List<String> NAME_LIST;


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(MyBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();

    }

    @Setup(Level.Iteration)
    public void setup() {
        PERSON_LIST = PersonGenerator.getNewPersonList(SIZE);
        NAME_LIST = new ArrayList<>(SIZE);
        System.out.println("Setup call");
    }


    @Benchmark
    public void getForLoopNames(Blackhole blackhole) {
        for (Person person : PERSON_LIST) {
            if (person.getAge() >= 18) {
                NAME_LIST.add(person.getFirstName());
            }
        }

        Collections.sort(NAME_LIST, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        blackhole.consume(NAME_LIST);
    }

    @Benchmark
    public void getParallelStreamList(Blackhole blackhole) {
        blackhole.consume(PERSON_LIST.stream().parallel().filter(person ->
                person.getAge() >= 18)
                .map(Person::getFirstName)
                .sorted(String::compareTo)
                .collect(Collectors.toList()));
    }

    @Benchmark
    public void getParallelStreamListAndSortAfterwards(Blackhole blackhole) {
        List<String> list = PERSON_LIST.stream().parallel().filter(person ->
                person.getAge() >= 18)
                .map(Person::getFirstName)
                .collect(Collectors.toList());

        Collections.sort(list, String::compareTo);
        blackhole.consume(list);
    }

    @Benchmark
    public void getStreamList(Blackhole blackhole) {
        blackhole.consume(PERSON_LIST.stream().filter(person ->
                person.getAge() >= 18)
                .map(Person::getFirstName)
                .sorted(String::compareTo)
                .collect(Collectors.toList()));
    }

}
