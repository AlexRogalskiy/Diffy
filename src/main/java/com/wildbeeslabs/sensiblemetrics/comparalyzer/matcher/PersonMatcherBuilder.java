package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher;

public class PersonMatcherBuilder extends BaseMatcher<Person> {
    private List<Matcher<? super Person>> matchers = new ArrayList<>();

    @Override
    protected boolean matchesSafely(Person item) {
        return AllOf.allOf(matchers).matches(item);
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("Person which matches the following: ", ", ", ".", matchers);
    }

    public static PersonMatcherBuilder isAPerson() {
        return new PersonMatcherBuilder();
    }

    private PersonMatcherBuilder() {
        matchers.add(Matchers.isA(Person.class));
    }

    public PersonMatcherBuilder withHomeAddressZipCode(final String zipCode) {
        matchers.add(new TypeSafeMatcher<Person>() {
            @Override
            protected boolean matchesSafely(Person item) {
                return item.getHomeAddress().getZipCode().equals(zipCode);
            }

        }
        @Override
        public void describeTo (Description description){
            description.appendText(String.format("Home address zip code equals", zipCode));
        }
    });
        return this;
}

    public PersonMatcherBuilder withOfficeAddressZipCode(final String zipCode) {
        // etc
    }

    //Assert.assertThat(person, PersonMatcherBuilder.isAPerson().withHomeAddressZipCode("12345").withOfficeAddressZipCode("12346"));
}