# Compara

The Compara project

***Technical description:***

As a result represents an iterable structure of fields difference entries of any given object (by applying object custom comparator / fields custom comparators if provided).

```aidl
// First object to be compared by
Object object1 = new Object();
// Last object to be compared with
Object object2 = new Object();
...
// Difference comparator instance
DiffComparator<Object> diffComparator = DefaultDiffComparatorFactory.create(Object.class);
// ResultSet of objects comparison
Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(object1, object2);
```

***Examples:***

*Initial objects to compare:*

```aidl
DeliveryInfo deliveryInfo1 = new DeliveryInfo();
deliveryInfo1.setId(...);
deliveryInfo1.setType(...);
deliveryInfo1.setDescription(...);
deliveryInfo1.setCreatedAt(...);
deliveryInfo1.setUpdatedAt(...)

DeliveryInfo deliveryInfo2 = new DeliveryInfo();
deliveryInfo2.setId(...);
deliveryInfo2.setType(...);
deliveryInfo2.setDescription(...);
deliveryInfo2.setCreatedAt(...);
deliveryInfo2.setUpdatedAt(...)
```

*Compare two objects by default fields (all):*

```aidl
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
assertThat(changeList, not(empty()));
assertThat(changeList.size(), greaterThanOrEqualTo(0));
assertThat(changeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));
```

*Compare two equal objects by default fields (all):*

```aidl
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo1);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
assertEquals(changeList.size(), 0);
```

*Compare two objects with excluded fields ("id", "createdAt", "updatedAt"):*

```aidl
List<String> excludedProperties = Arrays.asList("id", "createdAt", "updatedAt");
...
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.excludeProperties(excludedProperties);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
         .builder()
         .propertyName("id")
         .first(deliveryInfo1.getId())
         .last(deliveryInfo2.getId())
         .build();
assertFalse(changeList.contains(entry));

entry = DefaultDiffEntry
         .builder()
         .propertyName("type")
         .first(deliveryInfo1.getType())
         .last(deliveryInfo2.getType())
         .build();
assertTrue(changeList.contains(entry));
```

*Compare two objects with included fields ("id", "type", "description"):*

```aidl
List<String> includedProperties = Arrays.asList("id", "type", "description");
...
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
        .builder()
        .propertyName("createdAt")
        .first(deliveryInfo1.getCreatedAt())
        .last(deliveryInfo2.getCreatedAt())
        .build();
assertFalse(changeList.contains(entry));

entry = DefaultDiffEntry
        .builder()
        .propertyName("id")
        .first(deliveryInfo1.getId())
        .last(deliveryInfo2.getId())
        .build();
assertTrue(changeList.contains(entry));
```

*Compare two objects with included fields ("id", "type", "description"):*

```aidl
List<String> includedProperties = Arrays.asList("id", "type", "description");
...
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
        .builder()
        .propertyName("createdAt")
        .first(deliveryInfo1.getCreatedAt())
        .last(deliveryInfo2.getCreatedAt())
        .build();
assertFalse(changeList.contains(entry));

entry = DefaultDiffEntry
        .builder()
        .propertyName("id")
        .first(deliveryInfo1.getId())
        .last(deliveryInfo2.getId())
        .build();
assertTrue(changeList.contains(entry));
```

*Compare two objects with included ("id", "createdAt", "updatedAt", "description") / excluded fields ("createdAt", "updatedAt", "type"):*

```aidl
List<String> includedProperties = Arrays.asList("id", "createdAt", "updatedAt", "description");
List<String> excludedProperties = Arrays.asList("createdAt", "updatedAt", "type");
...
DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
diffComparator.excludeProperties(excludedProperties);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
        .builder()
        .propertyName("id")
        .first(deliveryInfo1.getId())
        .last(deliveryInfo2.getId())
        .build();
assertTrue(changeList.contains(entry));

entry = DefaultDiffEntry
         .builder()
         .propertyName("type")
         .first(deliveryInfo1.getType())
         .last(deliveryInfo2.getType())
         .build();
assertFalse(changeList.contains(entry));
```

*Compare two objects with included ("id", "type") / excluded ("description", "createdAt", "updatedAt") fields (non-intersected):*

```aidl
List<String> includedProperties = Arrays.asList("id", "type");
List<String> excludedProperties = Arrays.asList("description", "createdAt", "updatedAt");
...
DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
diffComparator.excludeProperties(excludedProperties);
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
         .builder()
         .propertyName("id")
         .first(deliveryInfo1.getId())
         .last(deliveryInfo2.getId())
         .build();
assertTrue(changeList.contains(entry));

entry = DefaultDiffEntry
        .builder()
        .propertyName("description")
        .first(deliveryInfo1.getDescription())
        .last(deliveryInfo2.getDescription())
        .build();
assertFalse(changeList.contains(entry));
```

*Compare two objects with included fields ("id", "type", "createdAt") and custom string field comparator:*

```aidl
List<String> includedProperties = Arrays.asList("id", "type", "createdAt");
...
DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
diffComparator.setComparator("gid", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
        .builder()
        .propertyName("id")
        .first(deliveryInfo1.getId())
        .last(deliveryInfo2.getId())
        .build();
assertTrue(changeList.contains(entry));
assertNotEquals(deliveryInfo1.getId(), deliveryInfo2.getId());

entry = DefaultDiffEntry
        .builder()
        .propertyName("gid")
        .first(deliveryInfo1.getGid())
        .last(deliveryInfo2.getGid())
        .build();
assertFalse(changeList.contains(entry));
assertEquals(deliveryInfo1.getGid(), deliveryInfo2.getGid());
```

*Compare two objects with included fields ("createdAt", "updatedAt") and custom date field comparator:*

```aidl
List<String> includedProperties = Arrays.asList("createdAt", "updatedAt");
...
DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
diffComparator.includeProperties(includedProperties);
diffComparator.setComparator("createdAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getHourOfDay()));
diffComparator.setComparator("updatedAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getDayOfMonth()));
Iterable<DefaultDiffEntry> iterableDiff = diffComparator.diffCompare(deliveryInfo1, deliveryInfo2);
...
List<DefaultDiffEntry> changeList = Lists.newArrayList(iterableDiff);
...
DefaultDiffEntry entry = DefaultDiffEntry
        .builder()
        .propertyName("createdAt")
        .first(deliveryInfo1.getCreatedAt())
        .last(deliveryInfo2.getCreatedAt())
        .build();
assertFalse(changeList.contains(entry));
assertNotEquals(deliveryInfo1.getCreatedAt(), deliveryInfo2.getCreatedAt());

entry = DefaultDiffEntry
        .builder()
        .propertyName("updatedAt")
        .first(deliveryInfo1.getUpdatedAt())
        .last(deliveryInfo2.getUpdatedAt())
        .build();
assertTrue(changeList.contains(entry));
assertNotEquals(deliveryInfo1.getUpdatedAt(), deliveryInfo2.getUpdatedAt());
```

*Assert object by general matcher:*

```aidl
DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
        .withType(5)
        .withGid("TEST")
        .withCreatedDate(DateUtils.toDate("17/06/2013", "dd/MM/yyyy"))
        .withUpdatedDate(DateUtils.toDate("27/09/2018", "dd/MM/yyyy"));
assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
```

*Assert object by custom date matcher:*

```aidl
Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
      @Override
      public boolean matchesSafe(final DeliveryInfo value) {
            return LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                   && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;
      }
};
...
this.deliveryInfo.setCreatedAt(DateUtils.toDate("07/06/2013", "dd/MM/yyyy"));
this.deliveryInfo.setUpdatedAt(DateUtils.toDate("17/06/2018", "dd/MM/yyyy"));
assertTrue(deliveryInfoMatcher.matches(this.deliveryInfo));
...
this.deliveryInfo.setCreatedAt(DateUtils.toDate("17/06/2013", "dd/MM/yyyy"));
this.deliveryInfo.setUpdatedAt(DateUtils.toDate("27/06/2018", "dd/MM/yyyy"));
assertFalse(deliveryInfoMatcher.matches(this.deliveryInfo));
```

***Installation:***

Package the application with all the dependencies:
```aidl
mvn clean compile assembly:single
```
