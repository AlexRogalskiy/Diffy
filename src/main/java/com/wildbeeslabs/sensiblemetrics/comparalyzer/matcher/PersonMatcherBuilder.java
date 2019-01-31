///*
// * The MIT License
// *
// * Copyright 2019 WildBees Labs, Inc.
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher;
//
//public class PersonMatcherBuilder extends BaseMatcher<Person> {
//    private List<Matcher<? super Person>> matchers = new ArrayList<>();
//
//    @Override
//    protected boolean matchesSafely(Person item) {
//        return AllOf.allOf(matchers).matches(item);
//    }
//
//    @Override
//    public void describeTo(Description description) {
//        description.appendList("Person which matches the following: ", ", ", ".", matchers);
//    }
//
//    public static PersonMatcherBuilder isAPerson() {
//        return new PersonMatcherBuilder();
//    }
//
//    private PersonMatcherBuilder() {
//        matchers.add(Matchers.isA(Person.class));
//    }
//
//    public PersonMatcherBuilder withHomeAddressZipCode(final String zipCode) {
//        matchers.add(new TypeSafeMatcher<Person>() {
//            @Override
//            protected boolean matchesSafely(Person item) {
//                return item.getHomeAddress().getZipCode().equals(zipCode);
//            }
//
//        }
//        @Override
//        public void describeTo (Description description){
//            description.appendText(String.format("Home address zip code equals", zipCode));
//        }
//    });
//        return this;
//}
//
//    public PersonMatcherBuilder withOfficeAddressZipCode(final String zipCode) {
//        // etc
//    }
//
//    //Assert.assertThat(person, PersonMatcherBuilder.isAPerson().withHomeAddressZipCode("12345").withOfficeAddressZipCode("12346"));
//}